package net.blogfy.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import net.blogfy.dto.ads.AdsExt;
import net.blogfy.entity.AdsInfo;
import net.blogfy.mapper.AdsInfoMapper;
import net.blogfy.service.AdsService;
import net.blogfy.service.BasicService;

@Service
public class AdsServiceImpl extends BasicService implements AdsService {
	
	private static final int ADS_SHOW_MAX_NUM = 3;
	private static List<AdsInfo> CACHE_ENABLED_ADS_LIST = null;
	
	@Resource
	private AdsInfoMapper adsInfoMapper;
	
	@Scheduled(cron = "0 10 * * * ?")
	public void refreshAdsCache() {
		CACHE_ENABLED_ADS_LIST = adsInfoMapper.queryEnabledAdsList();
	}
	
	@Override
	public List<AdsInfo> getShowAds() {
		if (CACHE_ENABLED_ADS_LIST == null) {
			refreshAdsCache();
		}
		
		// 判断是否有独占的广告
		for (AdsInfo adsInfo : CACHE_ENABLED_ADS_LIST) {
			if ("Y".equals(adsInfo.getExclusiveFlag())) {
				return Lists.newArrayList(adsInfo);
			}
		}
		
		// 随机排序
		List<AdsExt> extList = new ArrayList<>();
		for (AdsInfo adsInfo : CACHE_ENABLED_ADS_LIST) {
			AdsExt adsExt = new AdsExt();
			BeanUtils.copyProperties(adsInfo, adsExt);
			adsExt.setOrderNum(new Random().nextDouble());
			extList.add(adsExt);
		}
		extList.sort(Comparator.comparing(AdsExt::getOrderNum)); // 排序
		
		// 截取前几个广告
		int size = Math.min(ADS_SHOW_MAX_NUM, CACHE_ENABLED_ADS_LIST.size());
		List<AdsInfo> resultList = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			resultList.add(extList.get(i));
		}
		return resultList;
	}
	
}
