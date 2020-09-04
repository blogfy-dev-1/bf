package net.blogfy.dto.user.follow;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import net.blogfy.exception.BlogfyException;
import net.blogfy.util.restful.BaseReq;

import lombok.Getter;
import lombok.Setter;

public class UserFollowersReq extends Page<UserFollowsDTO> implements BaseReq {
	
	@Getter
	@Setter
	private int userId;
	
	/** 1：关注的人，2：关注我的人 */
	@Getter
	@Setter
	private int type;
	
	@Override
	public void beforeDoBiz() {
		if (type != 1 && type != 2) {
			throw new BlogfyException("参数不正确");
		}
	}
	
}
