package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 应用服务器注册表
 * @author ZHANGZHENWEI 2020-8-27
 */
@TableName("t_app_servers")
public class AppServers extends BasicTableEntity {
	
	@Getter
	@Setter
	@TableId
	private String serverId;
	
	@Getter
	@Setter
	private String serverIp;
	
}
