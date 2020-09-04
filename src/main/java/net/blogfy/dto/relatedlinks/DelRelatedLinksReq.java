package net.blogfy.dto.relatedlinks;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.restful.BaseReq;

public class DelRelatedLinksReq implements BaseReq {
	
	@Getter
	@Setter
	@NotNull(message = "链接ID不能为空")
	private String relatedLinksId;
	
}
