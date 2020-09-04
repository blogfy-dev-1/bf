package net.blogfy.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.blogfy.entity.UserBaseInfo;

public interface UserBaseInfoMapper extends BaseMapper<UserBaseInfo> {
	
	int existsEmail(@Param("email") String email);
	
	UserBaseInfo getUser(@Param("email") String email, @Param("pwd") String pwd);
	
	int existsUserId(@Param("userId") int userId);
}
