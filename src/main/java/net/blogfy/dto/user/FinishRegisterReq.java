package net.blogfy.dto.user;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.restful.BaseReq;

public class FinishRegisterReq implements BaseReq {
	
	@Getter
	@Setter
	String email;
	
	@Getter
	@Setter
	String code;
	
	@Getter
	@Setter
	String pwd;
	
}
