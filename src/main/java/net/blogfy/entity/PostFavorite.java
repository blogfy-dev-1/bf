package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户喜爱的文章表
 */
@TableName("t_post_favorite")
public class PostFavorite extends BasicTableEntity {
	
	@Getter
	@Setter
	@TableId
	private String postFavoriteId;
	
	@Getter
	@Setter
	private int userId;
	
	@Getter
	@Setter
	private int postId;
	
}
