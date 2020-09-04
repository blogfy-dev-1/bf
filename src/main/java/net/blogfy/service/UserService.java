package net.blogfy.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import net.blogfy.dto.relatedlinks.AddRelatedLinksReq;
import net.blogfy.dto.relatedlinks.DelRelatedLinksReq;
import net.blogfy.dto.relatedlinks.MoveRelatedLinksReq;
import net.blogfy.dto.user.FinishRegisterReq;
import net.blogfy.dto.user.UpdateUserBaseInfoReq;
import net.blogfy.dto.user.UserLoginReq;
import net.blogfy.dto.user.follow.UserFollowersReq;
import net.blogfy.dto.user.follow.UserFollowsDTO;
import net.blogfy.entity.RelatedLinks;
import net.blogfy.entity.UserBaseInfo;

public interface UserService {
	
	UserBaseInfo login(UserLoginReq req);
	
	void register(String email);
	
	boolean validateEmailCode(String email, String emailValidateCode);
	
	void updateUserBaseInfo(UpdateUserBaseInfoReq req);
	
	void finishRegister(FinishRegisterReq req);
	
	void addRelatedLink(AddRelatedLinksReq req);
	
	void delRelatedLink(DelRelatedLinksReq req);
	
	void moveRelatedLinks(MoveRelatedLinksReq req);
	
	List<RelatedLinks> queryRelatedLinksList(int userId);
	
	IPage<UserFollowsDTO> queryFollowsList(UserFollowersReq req);
	
	void changeFollow(Integer loginUserId, Integer followToUserId);
	
}
