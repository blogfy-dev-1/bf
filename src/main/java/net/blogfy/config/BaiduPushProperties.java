package net.blogfy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "baidu.push")
public class BaiduPushProperties {
	
	@Getter
	@Setter
	private String cron;
	
	@Getter
	@Setter
	private String token;
	
	@Getter
	@Setter
	private String url;
	
}
