package net.blogfy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import net.blogfy.dto.relatedlinks.AddRelatedLinksReq;
import net.blogfy.dto.relatedlinks.DelRelatedLinksReq;
import net.blogfy.dto.relatedlinks.MoveRelatedLinksReq;
import net.blogfy.dto.user.FinishRegisterReq;
import net.blogfy.dto.user.UpdateUserBaseInfoReq;
import net.blogfy.dto.user.UserLoginReq;
import net.blogfy.dto.user.follow.UserFollowersReq;
import net.blogfy.dto.user.follow.UserFollowsDTO;
import net.blogfy.entity.RelatedLinks;
import net.blogfy.entity.UserBaseInfo;
import net.blogfy.entity.UserFollows;
import net.blogfy.exception.BlogfyException;
import net.blogfy.mapper.RelatedLinksMapper;
import net.blogfy.mapper.UserBaseInfoMapper;
import net.blogfy.mapper.UserFollowsMapper;
import net.blogfy.service.BasicService;
import net.blogfy.service.UserService;
import net.blogfy.util.AESCoder;
import net.blogfy.util.IdUtils;
import net.blogfy.util.MyStringUtils;
import net.blogfy.util.WebUtils;

@Service
public class UserServiceImpl extends BasicService implements UserService {
	
	private static final int MAX_RELATED_LINK = 6;
	
	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;
	@Resource
	private RelatedLinksMapper relatedLinksMapper;
	@Resource
	private UserFollowsMapper userFollowsMapper;
	
	@Override
	public UserBaseInfo login(UserLoginReq req) {
		String email = AESCoder.encrypt(req.getEmail()); // 邮箱AES加密
		String pwd = AESCoder.encrypt(DigestUtils.md5Hex(req.getPwd())); // MD5再AES加密
		
		UserBaseInfo user = userBaseInfoMapper.getUser(email, pwd);
		if (user == null) {
			throw new BlogfyException("账号或密码不正确");
		}
		return user;
	}
	
	@Override
	public void register(String email) {
		int countEmail = userBaseInfoMapper.existsEmail(email);
		if (countEmail != 0) {
			throw new BlogfyException("该邮箱已经被注册了。");
		}
		
		// 保存验证码
		String validateCode = IdUtils.newRandomStr(16);
		logger.info("User register validate code: {}", validateCode);
		stringRedisTemplate.opsForValue().set("user-register-code-" + email, validateCode, 2, TimeUnit.HOURS);
		
		// 发送邮件
//		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	}
	
	private int newUserId() {
		int userId;
		boolean exists;
		do {
			userId = IdUtils.newRandomUserId();
			int i = userBaseInfoMapper.existsUserId(userId);
			exists = (i != 0);
		} while (exists);
		return userId;
	}
	
	@Override
	public boolean validateEmailCode(String email, String emailValidateCode) {
		String validateCode = stringRedisTemplate.opsForValue().get("user-register-code-" + email);
		if (validateCode == null || !StringUtils.equals(emailValidateCode, validateCode)) {
			return false;
		}
		return true;
//
//		// 其它操作
//
//		return newUser.getUserId();
	}
	
	@Override
	public void updateUserBaseInfo(UpdateUserBaseInfoReq req) {
		UserBaseInfo recordUser = new UserBaseInfo();
		recordUser.setUserId(WebUtils.getLoginUserId(true));
		recordUser.setNickName(req.getNickName());
		userBaseInfoMapper.updateById(recordUser);
	}
	
	@Override
	public void finishRegister(FinishRegisterReq req) {
		boolean b = validateEmailCode(req.getEmail(), req.getCode());
		if (!b) {
			throw new BlogfyException("验证码已过期");
		}
		
		// 创建用户
		UserBaseInfo newUser = new UserBaseInfo();
		newUser.setUserId(newUserId());
		newUser.setEmail(AESCoder.encrypt(req.getEmail())); // 邮箱AES加密
		newUser.setPwd(AESCoder.encrypt(DigestUtils.md5Hex(req.getPwd()))); // MD5再AES加密
		userBaseInfoMapper.insert(newUser);
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
	
	
}
