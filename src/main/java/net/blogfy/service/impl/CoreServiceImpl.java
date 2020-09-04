package net.blogfy.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.blogfy.util.CommonConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.blogfy.entity.AppServers;
import net.blogfy.mapper.AppServersMapper;
import net.blogfy.service.BasicService;
import net.blogfy.service.CoreService;
import net.blogfy.util.MyStringUtils;

@Service
public class CoreServiceImpl extends BasicService implements CoreService, InitializingBean {
	
	@Resource
	private AppServersMapper appServersMapper;
	
	@Override
	@Scheduled(cron = "0 10 * * * ?")
	public void refreshCache() {
		// 更新服务器IP数据
		updateServerInfo();
	}
	
	private void updateServerInfo() {
		String serverId = MyStringUtils.IdMarker();
		String serverIP = getServerIP();
		appServersMapper.updateServer(serverId, serverIP, CommonConstants.SYS_USER_ID);
	}
	
	@Override
	public void afterPropertiesSet() {
		refreshCache();
	}
}
