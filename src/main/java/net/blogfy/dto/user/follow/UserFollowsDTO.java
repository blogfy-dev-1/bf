package net.blogfy.dto.user.follow;

import lombok.Getter;
import lombok.Setter;

public class UserFollowsDTO {
	
	@Getter
	@Setter
	private Integer userId;
	
	@Getter
	@Setter
	private String nickName;
	
	@Getter
	@Setter
	private String sex;
	
	@Getter
	@Setter
	private String headImgUrlMax;
	
	@Getter
	@Setter
	private String headImgUrlMedium;
	
	@Getter
	@Setter
	private String headImgUrlMin;
	
	@Getter
	@Setter
	private String blogTitle;
	
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
	
	/** 当前用户是否关注了，默认没关注。 */
	@Getter
	@Setter
	private boolean followFlag = false;
}
