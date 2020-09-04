package net.blogfy.dto.user;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.restful.BaseReq;

public class UserLoginReq implements BaseReq {
	
	@Getter
	@Setter
	@NotEmpty(message = "邮箱不能为空")
	private String email;
	
	@Getter
	@Setter
	@NotEmpty(message = "密码不能为空")
	private String pwd;
	
}
