package net.blogfy.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告信息表
 * @author ZHANGZHENWEI 2020-8-24
 */
@TableName("t_ads_info")
public class AdsInfo extends BasicTableEntity {
	
	/** 广告ID（主键） */
	@Getter
	@Setter
	@TableId
	private String adsId;
	
	/** 图片URL */
	@Getter
	@Setter
	private String imgUrl;
	
	/** 链接URL */
	@Getter
	@Setter
	private String linkUrl;
	
	/** 展示开始时间 */
	@Getter
	@Setter
	private Date showBegin;
	
	/** 展示结束时间 */
	@Getter
	@Setter
	private Date showEnd;
	
	/** 是否独占 */
	@Getter
	@Setter
	private String exclusiveFlag;
	
}
