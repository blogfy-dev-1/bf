package net.blogfy.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 所有表都有的字段
 * @author ZHANGZHENWEI 2020-7-27
 */
public class BasicTableEntity {
	
	@Getter
	@Setter
	private String createBy;
	
	@Getter
	@Setter
	private Date createDate;
	
	@Getter
	@Setter
	private String updateBy;
	
	@Getter
	@Setter
	private Date updateDate;
	
}
