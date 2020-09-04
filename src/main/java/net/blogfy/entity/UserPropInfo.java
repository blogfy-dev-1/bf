package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户属性信息表
 */
@TableName("t_user_prop_info")
public class UserPropInfo extends BasicTableEntity {

	/** 用户ID */
	@Getter
	@Setter
	@TableId
	private Integer userId;
	
	/** 总积分 */
	@Getter
	@Setter
	private int totalScore;
	
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
	
}
