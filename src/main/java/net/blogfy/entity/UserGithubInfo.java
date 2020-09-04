package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户Github信息表，用户授权OAuth登录，通过access_token调用得到。
 */
@TableName("t_user_github_info")
public class UserGithubInfo extends BasicTableEntity {

	/** 用户ID */
	@Getter
	@Setter
	@TableId
	private Integer userId;

	/** Github ID */
	@Getter
	@Setter
	private Integer githubId;

	/** 登录账号 */
	@Getter
	@Setter
	private String loginCode;

	/** 头像URL */
	@Getter
	@Setter
	private String avatarUrl;

	/** GitHub首页URL */
	@Getter
	@Setter
	private String htmlUrl;

	/** 昵称 */
	@Getter
	@Setter
	private String nickName;

	/** 公司 */
	@Getter
	@Setter
	private String company;

	/** 地区 */
	@Getter
	@Setter
	private String githubLocation;

	/** 邮箱 */
	@Getter
	@Setter
	private String email;

	/** GitHub access token */
	@Getter
	@Setter
	private String accessToken;

	/** Access token 到期时间 */
	@Getter
	@Setter
	private Date accessTokenExpireTime;

}
