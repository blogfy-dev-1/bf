package net.blogfy.dto.relatedlinks;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.restful.BaseReq;

public class AddRelatedLinksReq implements BaseReq {
	
	@Getter
	@Setter
	@NotBlank(message = "链接标题不能为空")
	private String linkTitle;
	
	@Getter
	@Setter
	@NotBlank(message = "链接URL不能为空")
	private String linkUrl;
	
}
