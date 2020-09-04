package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 友情链接表
 */
@TableName("t_related_links")
public class RelatedLinks extends BasicTableEntity {
	
	/** 主键 */
	@Getter
	@Setter
	@TableId
	private String relatedLinksId;
	
	/** 用户ID */
	@Getter
	@Setter
	private int userId;
	
	/** 友链标题 */
	@Getter
	@Setter
	private String linkTitle;
	
	/** 友链URL */
	@Getter
	@Setter
	private String linkUrl;
	
	/** 顺序 */
	@Getter
	@Setter
	private Double orderNum;
	
}
