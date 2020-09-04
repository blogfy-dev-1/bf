package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 评论信息表
 */
@TableName("t_post_discuss")
public class PostDiscuss extends BasicTableEntity {
	
	/** 评论ID（主键） */
	@Getter
	@Setter
	@TableId
	private String discussId;
	
	/** 父评论ID */
	@Getter
	@Setter
	private String parentDiscussId;
	
	/** 评论用户ID */
	@Getter
	@Setter
	private int userId;
	
	/** 评论文章ID */
	@Getter
	@Setter
	private int postId;
	
	/** 评论内容 */
	@Getter
	@Setter
	private String discussContent;
	
}
