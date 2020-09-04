package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户点赞的文章
 */
@TableName("t_agree_posts")
public class PostAgree extends BasicTableEntity {
	
	@Getter
	@Setter
	@TableId
	private String postAgreeId;
	
	/** 用户ID */
	@Getter
	@Setter
	private int userId;
	
	/** 文章ID */
	@Getter
	@Setter
	private int postId;
	
}
