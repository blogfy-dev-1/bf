package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章详情表
 * @author ZHANGZHENWEI 2020-8-21
 */
@TableName("t_post_content_info")
public class PostContentInfo extends BasicTableEntity {
	
	/** 文章ID */
	@Getter
	@Setter
	@TableId
	private int postId;
	
	/** 文章内容 */
	@Getter
	@Setter
	private String postContent;
	
}
