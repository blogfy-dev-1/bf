package net.blogfy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import net.blogfy.config.GithubProperties;
import net.blogfy.dto.github.GithubAccessTokenResp;
import net.blogfy.dto.relatedlinks.AddRelatedLinksReq;
import net.blogfy.dto.relatedlinks.DelRelatedLinksReq;
import net.blogfy.dto.relatedlinks.MoveRelatedLinksReq;
import net.blogfy.dto.user.UpdateUserBaseInfoReq;
import net.blogfy.dto.user.follow.UserFollowersReq;
import net.blogfy.dto.user.follow.UserFollowsDTO;
import net.blogfy.entity.RelatedLinks;
import net.blogfy.entity.UserBaseInfo;
import net.blogfy.entity.UserFollows;
import net.blogfy.entity.UserGithubInfo;
import net.blogfy.entity.UserPropInfo;
import net.blogfy.exception.BlogfyException;
import net.blogfy.mapper.RelatedLinksMapper;
import net.blogfy.mapper.UserBaseInfoMapper;
import net.blogfy.mapper.UserFollowsMapper;
import net.blogfy.mapper.UserGithubInfoMapper;
import net.blogfy.mapper.UserPropInfoMapper;
import net.blogfy.service.BasicService;
import net.blogfy.service.UserService;
import net.blogfy.util.DateUtils;
import net.blogfy.util.IdUtils;
import net.blogfy.util.MyStringUtils;
import net.blogfy.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BasicService implements UserService {
	
	private static final int MAX_RELATED_LINK = 6;
	
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	@Resource
	private UserPropInfoMapper userPropInfoMapper;
	@Resource
	private RelatedLinksMapper relatedLinksMapper;
	@Resource
	private UserFollowsMapper userFollowsMapper;
	@Resource
	private UserGithubInfoMapper userGithubInfoMapper;
	@Resource
	private GithubProperties githubProperties;
	
	private int newUserId() {
		int userId;
		boolean exists;
		do {
			userId = IdUtils.newRandomUserId();
			int i = userBaseInfoMapper.existsUserId(userId);
			exists = (i != 0);
		} while (exists);
		logger.info("new user id: {}", userId);
		return userId;
	}
	
	@Override
	public void updateUserBaseInfo(UpdateUserBaseInfoReq req) {
		UserBaseInfo recordUser = new UserBaseInfo();
		recordUser.setUserId(WebUtils.getLoginUserId(true));
		recordUser.setNickName(req.getNickName());
		userBaseInfoMapper.updateById(recordUser);
	}
	
	@Override
	public List<RelatedLinks> queryRelatedLinksList(int userId) {
		return relatedLinksMapper.queryList(userId);
	}
	
	@Override
	public void addRelatedLink(AddRelatedLinksReq req) {
		Integer loginUserId = WebUtils.getLoginUserId(true);
		// 总数校验
		int count = relatedLinksMapper.countUserRelatedLinks(loginUserId);
		if (count >= MAX_RELATED_LINK) {
			throw new BlogfyException("最多添加" + MAX_RELATED_LINK + "个友链哦！");
		}
		
		// 排序最大值
		double maxOrder = relatedLinksMapper.maxRelatedLinksOrder(loginUserId);
		
		// 插入记录
		RelatedLinks relatedLink = new RelatedLinks();
		relatedLink.setRelatedLinksId(MyStringUtils.IdMarker());
		relatedLink.setUserId(loginUserId);
		relatedLink.setLinkTitle(req.getLinkTitle());
		relatedLink.setLinkUrl(req.getLinkUrl());
		relatedLink.setOrderNum(maxOrder + 10000); // 每步增加一万
		relatedLinksMapper.insert(relatedLink);
	}
	
	@Override
	public void delRelatedLink(DelRelatedLinksReq req) {
		Integer loginUserId = WebUtils.getLoginUserId(true);
		String relatedLinksId = req.getRelatedLinksId();
		RelatedLinks relatedLinks = relatedLinksMapper.selectById(relatedLinksId);
		if (relatedLinks == null) {
			throw new BlogfyException("链接不存在");
		}
		if (loginUserId != relatedLinks.getUserId()) {
			throw new BlogfyException("只能删除自己的链接");
		}
		
		// 执行删除
		relatedLinksMapper.deleteById(relatedLinksId);
	}
	
	@Override
	public void moveRelatedLinks(MoveRelatedLinksReq req) {
	
	}
	
	@Override
	public IPage<UserFollowsDTO> queryFollowsList(UserFollowersReq req) {
		IPage<UserFollowsDTO> followsList = userFollowsMapper.queryFollowsList(req);
		
		// 标记我已经关注了的
		Integer loginUserId = WebUtils.getLoginUserId(false);
		if (loginUserId != null) {
			List<Integer> list = userFollowsMapper.queryMyFollowsUserIdList(loginUserId);
			for (UserFollowsDTO record : followsList.getRecords()) {
				if (list.contains(record.getUserId())) {
					record.setFollowFlag(true);
				}
			}
		}
		
		return followsList;
	}
	
	@Override
	public void changeFollow(Integer loginUserId, Integer followToUserId) {
		UserBaseInfo followToUser = userBaseInfoMapper.selectById(followToUserId);
		if (followToUser == null) {
			throw new BlogfyException("用户不存在");
		}
		UserFollows userFollow = userFollowsMapper.getUserFollow(loginUserId, followToUserId);
		if (userFollow == null) {
			// 添加关注
			UserFollows record = new UserFollows();
			record.setFollowId(MyStringUtils.IdMarker());
			record.setFollowerFromUserId(loginUserId);
			record.setFollowerToUserId(followToUserId);
			record.setCreateDate(new Date());
			userFollowsMapper.insert(record);
		} else {
			// 取消关注
			userFollowsMapper.deleteById(userFollow.getFollowId());
		}
	}
	
	@Override
	public Integer githubLoginCallback(String code) throws Exception {
		// 获取access_token
		GithubAccessTokenResp githubAccessToken = getGithubAccessToken(code);
		// 获取用户信息
		UserGithubInfo userGithubInfo = getGithubUserInfo(githubAccessToken.getAccessToken());
		Integer githubId = userGithubInfo.getId();
		UserGithubInfo existsUserGithubInfo = userGithubInfoMapper.getGithubInfoByGithubId(githubId);
		Integer userId;
		if (existsUserGithubInfo == null) {
			// 创建用户
			userId = createUser(userGithubInfo);
		} else {
			userId = existsUserGithubInfo.getUserId();
		}

		// 更新用户信息
		// 头像

		return userId;
	}

	// 创建用户
	private Integer createUser(UserGithubInfo userGithubInfo) {
		int newUserId = newUserId();
		Date now = DateUtils.now();

		// 用户基本信息
		UserBaseInfo userBaseInfo = new UserBaseInfo();
		userBaseInfo.setUserId(newUserId);
		userBaseInfo.setEmail(userGithubInfo.getEmail());
		userBaseInfo.setNickName(userGithubInfo.getName());
		if (StringUtils.isNoneEmpty(userBaseInfo.getNickName())) {
			userBaseInfo.setBlogTitle(userBaseInfo.getNickName() + "的博客");
		}
		userBaseInfo.setCreateDate(now);
		userBaseInfoMapper.insert(userBaseInfo);

		// 用户属性信息
		UserPropInfo userPropInfo = new UserPropInfo();
		userPropInfo.setUserId(newUserId);
		userPropInfo.setCreateDate(now);
		userPropInfoMapper.insert(userPropInfo);

		// 用户github信息
		UserGithubInfo newUserGithubInfo = new UserGithubInfo();
		BeanUtils.copyProperties(userGithubInfo, newUserGithubInfo);
		newUserGithubInfo.setUserId(newUserId);
		newUserGithubInfo.setCreateDate(now);
		userGithubInfoMapper.insert(newUserGithubInfo);

		return newUserId;
	}

	private GithubAccessTokenResp getGithubAccessToken(String code) {
		String getAccessTokenUrl = String.format("%s?client_id=%s&client_secret=%s&code=%s",
				githubProperties.getAccessTokenUrl(),
				githubProperties.getClientId(),
				githubProperties.getClientSecret(),
				code
		);
		logger.info("get access token url: {}", getAccessTokenUrl);

		String accessTokenForStr = restTemplate.getForObject(getAccessTokenUrl, String.class);
		logger.info("get github access token resp str: {}", accessTokenForStr);

		// 处理返回格式，封装到Map中。
		Map<String, String> map = new HashMap<>();
		String[] strings = accessTokenForStr.split("&");
		for (String string : strings) {
			String[] split = string.split("=");
			map.put(split[0], split.length == 1 ? null : split[1]);
		}

		GithubAccessTokenResp resp = new GithubAccessTokenResp();
		resp.readMap(map);
		return resp;
	}

	private UserGithubInfo getGithubUserInfo(String accessToken) throws Exception {
		String getUserInfoUrl = String.format("%s?access_token=%s",
				githubProperties.getGetUserInfoUrl(),
				accessToken
		);
		logger.info("get user info url: {}", getUserInfoUrl);

		String userForStr = restTemplate.getForObject(getUserInfoUrl, String.class);
		logger.info("get github user info: {}", userForStr);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 没有映射到的字段忽略掉，不报错。
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); // 下划线转驼峰
		UserGithubInfo userGithubInfo = mapper.readValue(userForStr, UserGithubInfo.class);
		return userGithubInfo;
	}

}
