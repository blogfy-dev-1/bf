package net.blogfy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

}
