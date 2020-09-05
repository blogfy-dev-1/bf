package net.blogfy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "github")
public class GithubProperties {
	
	@Getter
	@Setter
	private String appId;
	
	@Getter
	@Setter
	private String clientId;
	
	@Getter
	@Setter
	private String clientSecret;

	@Getter
	@Setter
	private String oauthUrl;
	
	@Getter
	@Setter
	private String accessTokenUrl;
	
	@Getter
	@Setter
	private String getUserInfoUrl;
	
}
