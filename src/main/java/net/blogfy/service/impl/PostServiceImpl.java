package net.blogfy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import net.blogfy.config.Env;
import net.blogfy.entity.PostBaseInfo;
import net.blogfy.mapper.PostBaseInfoMapper;
import net.blogfy.service.BasicService;
import net.blogfy.service.PostService;
import net.blogfy.util.PostUtils;
import net.blogfy.util.PostUtils.PostStatus;

@Service
public class PostServiceImpl extends BasicService implements PostService {
	
	@Resource
	private PostBaseInfoMapper postBaseInfoMapper;
	
	/**
	 * 定时推送文章到百度
	 * @author ZHANGZHENWEI 2020-8-27
	 * @return
	 */
	@Scheduled(cron = "${baidu.push.cron}")
	public void scheduleSubmitPost2Baidu() {
		// 判断是否定时任务机器
		if (!isJobServer()) {
			return;
		}
		
		List<PostBaseInfo> postBaseInfoList = postBaseInfoMapper.queryPostBaseInfoList(PostStatus.OPEN);
		if (CollectionUtils.isEmpty(postBaseInfoList)) {
			return;
		}
		
		List<String> postUrlList = new ArrayList<>(postBaseInfoList.size());
		for (PostBaseInfo postBaseInfo : postBaseInfoList) {
			postUrlList.add(PostUtils.getPostUrl(postBaseInfo.getPostId()));
		}
		String data = StringUtils.join(postUrlList.iterator(), "\r\n");
		
		Map<String, String> param = ImmutableMap.<String, String>builder()
				.put("site", Env.getServerHost() + Env.getContextPath())
				.put("token", baiduPushProperties.getToken())
				.build();
		
//		String result = restTemplate.postForObject(baiduPushProperties.getUrl(), data, String.class, param);
//		logger.info("baidu push result: {}", result);
	}
	
}
