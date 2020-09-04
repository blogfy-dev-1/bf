package net.blogfy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户基本信息表
 */
@TableName("t_user_base_info")
public class UserBaseInfo extends BasicTableEntity {
	
	@Getter
	@Setter
	@TableId
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
	private String mobilePhone;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String pwd; // 密码（MD5再AES）
	
	@Getter
	@Setter
	private String blogTitle;
	
	@Getter
	@Setter
	private String blogSubTitle;
	
}
