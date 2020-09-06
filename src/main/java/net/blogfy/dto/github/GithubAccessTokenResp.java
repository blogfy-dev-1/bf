package net.blogfy.dto.github;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class GithubAccessTokenResp {

	@Getter
	@Setter
	private String accessToken;

	@Getter
	@Setter
	private String expiresIn;

	@Getter
	@Setter
	private String refreshToken;

	@Getter
	@Setter
	private String refreshTokenExpiresIn;

	@Getter
	@Setter
	private String scope;

	@Getter
	@Setter
	private String tokenType;

	public void readMap(Map<String, String> map) {
		this.accessToken = map.get("access_token");
		this.expiresIn = map.get("expires_in");
		this.refreshToken = map.get("refresh_token");
		this.refreshTokenExpiresIn = map.get("refresh_token_expires_in");
		this.scope = map.get("scope");
		this.tokenType = map.get("token_type");
	}
}
