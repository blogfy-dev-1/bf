package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章基本信息表
 * @author ZHANGZHENWEI 2020-8-21
 */
@TableName("t_post_base_info")
public class PostBaseInfo extends BasicTableEntity {
	
	/** 文章ID */
	@Getter
	@Setter
	@TableId
	private int postId;
	
	/** 发表者用户ID */
	@Getter
	@Setter
	private int userId;
	
	/** 标题 */
	@Getter
	@Setter
	private String title;
	
	/** 简介 */
	@Getter
	@Setter
	private String description;
	
	/** 标签 */
	@Getter
	@Setter
	private String tags;
	
	/** 访问数 */
	@Getter
	@Setter
	private int viewNum;
	
	/** 点赞数 */
	@Getter
	@Setter
	private int agreeNum;
	
	/** 收藏数 */
	@Getter
	@Setter
	private int favoriteNum;
	
	/** 转发数 */
	@Getter
	@Setter
	private int repeatNum;
	
	/** 评论数 */
	@Getter
	@Setter
	private int discussNum;
	
	/** 创作类型，1：原创，2：转载 */
	@Getter
	@Setter
	private int originalType;
	
	/** 转载链接 */
	@Getter
	@Setter
	private String reproductionUrl;
	
	/** 状态，1：公开，2：私有，3：删除 */
	@Getter
	@Setter
	private int status;
}
