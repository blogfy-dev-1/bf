package net.blogfy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.blogfy.entity.PostBaseInfo;

public interface PostBaseInfoMapper extends BaseMapper<PostBaseInfo> {

	List<PostBaseInfo> queryPostBaseInfoList(@Param("status") Integer status);
	
}
