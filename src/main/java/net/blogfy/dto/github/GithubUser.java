package net.blogfy.dto.github;

import lombok.Getter;
import lombok.Setter;

// access_token 返回的GitHub用户信息
public class GithubUser {

	@Getter
	@Setter
	private String login; // 登录账号

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String name; // 名称

	@Getter
	@Setter
	private String avatar_url; // 头像地址

	@Getter
	@Setter
	private String html_url; // github主页

	@Getter
	@Setter
	private String location; // 地区

	@Getter
	@Setter
	private String email;

}
