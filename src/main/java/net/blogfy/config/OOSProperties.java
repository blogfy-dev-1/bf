package net.blogfy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "aliyun.oos")
public class OOSProperties {
	
	@Getter
	@Setter
	private String endpoint;
	
}
