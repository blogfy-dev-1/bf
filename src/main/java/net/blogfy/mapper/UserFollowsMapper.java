package net.blogfy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import net.blogfy.dto.user.follow.UserFollowersReq;
import net.blogfy.dto.user.follow.UserFollowsDTO;
import net.blogfy.entity.UserFollows;

public interface UserFollowsMapper extends BaseMapper<UserFollows> {
	
	IPage<UserFollowsDTO> queryFollowsList(@Param("req") UserFollowersReq req);
	
	UserFollows getUserFollow(@Param("followerFromUserId") Integer followerFromUserId, @Param("followerToUserId") Integer followerToUserId);
	
	/**
	 * 查询我关注的用户列表
	 * @author ZHANGZHENWEI 2020-8-27
	 * @param followerFromUserId
	 * @return
	 */
	List<Integer> queryMyFollowsUserIdList(@Param("followerFromUserId") Integer followerFromUserId);
}
