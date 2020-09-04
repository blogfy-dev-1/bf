package net.blogfy.dto.user;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.restful.BaseReq;

public class UpdateUserBaseInfoReq implements BaseReq {
	
	@Getter
	@Setter
	private String nickName;
	
}
