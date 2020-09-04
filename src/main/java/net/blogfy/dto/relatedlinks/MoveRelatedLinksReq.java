package net.blogfy.dto.relatedlinks;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.restful.BaseReq;

public class MoveRelatedLinksReq implements BaseReq {
	
	@Getter
	@Setter
	@NotNull(message = "链接ID不能为空")
	private String relatedLinksId;
	
	@Getter
	@Setter
	@NotEmpty(message = "移动类型不能为空")
	private String type;
	
}
