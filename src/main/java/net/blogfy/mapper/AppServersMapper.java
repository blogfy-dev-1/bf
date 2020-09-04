package net.blogfy.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.blogfy.entity.AppServers;

public interface AppServersMapper extends BaseMapper<AppServers> {

	void updateServer(@Param("serverId") String serverId, @Param("serverIP") String serverIP, @Param("sysUserId") int sysUserId);
	
	String getFirstLiveServerIP();
}
