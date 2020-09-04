package net.blogfy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.blogfy.entity.RelatedLinks;

public interface RelatedLinksMapper extends BaseMapper<RelatedLinks> {
	
	int countUserRelatedLinks(@Param("userId") int userId);
	
	double maxRelatedLinksOrder(@Param("userId") int userId);
	
	List<RelatedLinks> queryList(@Param("userId") int userId);

}
