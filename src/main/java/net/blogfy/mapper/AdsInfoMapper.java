package net.blogfy.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.blogfy.entity.AdsInfo;

public interface AdsInfoMapper extends BaseMapper<AdsInfo> {
	
	List<AdsInfo> queryEnabledAdsList();
}
