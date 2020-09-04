package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户关注关系表
 * @author ZHANGZHENWEI 2020-8-21
 */
@TableName("t_user_base_info")
public class UserFollows extends BasicTableEntity {
	
	@Getter
	@Setter
	@TableId
	private String followId;
	
	/** 关注者（粉丝） */
	@Getter
	@Setter
	private int followerFromUserId;
	
	/** 被关注者 */
	@Getter
	@Setter
	private int followerToUserId;
	
}
