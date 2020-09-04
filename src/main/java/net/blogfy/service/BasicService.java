package net.blogfy.service;

import net.blogfy.config.BaiduPushProperties;
import net.blogfy.exception.BlogfyException;
import net.blogfy.mapper.AppServersMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class BasicService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	protected StringRedisTemplate stringRedisTemplate;
	@Resource
	protected RedisTemplate redisTemplate;
	@Resource
	protected RestTemplate restTemplate;
	@Resource
	protected BaiduPushProperties baiduPushProperties;
	@Resource
	private AppServersMapper appServersMapper;
	
	/**
	 * 获取IP地址
	 * @return
	 */
	protected String getServerIP() {
		String hostAddress = null;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new BlogfyException("获取本机IP地址异常");
		}
		return hostAddress;
	}

	/**
	 * 是否定时任务主机
	 * @return
	 */
	protected boolean isJobServer() {
		String localIP = getServerIP();
		String jobServerIP = appServersMapper.getFirstLiveServerIP();
		boolean result = StringUtils.equals(localIP, jobServerIP);
		if (result) {
			logger.info("current server is job server, ip: {}", localIP);
		} else {
			logger.info("Current server is not job server, current ip: {}, job server ip: {}", localIP, jobServerIP);
		}
		return result;
	}
	
}
