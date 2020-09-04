package net.blogfy.service;

import java.util.List;

import net.blogfy.entity.AdsInfo;

public interface AdsService {
	
	/**
	 * 获取当前需要展示的广告
	 * @author ZHANGZHENWEI 2020-8-25
	 * @return
	 */
	List<AdsInfo> getShowAds();
	
}
