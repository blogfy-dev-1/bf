package net.blogfy.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.blogfy.entity.UserGithubInfo;

public interface UserGithubInfoMapper extends BaseMapper<UserGithubInfo> {
	
	UserGithubInfo getGithubInfoByGithubId(@Param("githubId") String githubId);
}
