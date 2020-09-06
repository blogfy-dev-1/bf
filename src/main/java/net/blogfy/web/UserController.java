package net.blogfy.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;

import net.blogfy.config.GithubProperties;
import net.blogfy.dto.relatedlinks.AddRelatedLinksReq;
import net.blogfy.dto.relatedlinks.DelRelatedLinksReq;
import net.blogfy.dto.relatedlinks.MoveRelatedLinksReq;
import net.blogfy.dto.user.UpdateUserBaseInfoReq;
import net.blogfy.dto.user.follow.UserFollowersReq;
import net.blogfy.dto.user.follow.UserFollowsDTO;
import net.blogfy.entity.RelatedLinks;
import net.blogfy.exception.BlogfyException;
import net.blogfy.mapper.RelatedLinksMapper;
import net.blogfy.service.UserService;
import net.blogfy.util.WebUtils;
import net.blogfy.util.restful.PaginationResp;
import net.blogfy.util.restful.Result;
import net.blogfy.web.helper.BasicController;

@Controller
public class UserController extends BasicController {
	
	@Resource
	private RelatedLinksMapper relatedLinksMapper;
	@Resource
	private UserService userService;
	@Resource
	private GithubProperties githubProperties;
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public String userHome(HttpServletRequest request, @PathVariable("userId") String userId, Model model) {
		model.addAttribute("userId", userId);
		return "user_home";
	}
	
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> logout(HttpServletRequest request) {
		WebUtils.removeSessionUser(request);
		return Result.respSuccess();
	}

	@RequestMapping(value = "/user/github/goLogin", method = RequestMethod.GET)
	public String githubGoLogin() {
		String path = githubProperties.getOauthUrl() + "?client_id=" + githubProperties.getClientId();
		logger.info("github go login, redirect to: {}", path);
		return "redirect:" + path;
	}

	@RequestMapping(value = "/user/github/loginCallback", method = RequestMethod.GET)
	public String githubLoginCallback(HttpServletRequest request, HttpServletResponse response, String code) throws Exception {
		logger.info("github login callback code: {}", code);
		if (StringUtils.isEmpty(code)) {
			throw new BlogfyException("code is empty.");
		}
		Integer userId = userService.githubLoginCallback(code);

		// 创建登录态
		WebUtils.setLoginUserId(request, response, userId);

		return "user_home"; // 先返回个人中心，后面应该是返回到登录前页面。
	}
	
	@RequestMapping(value = "/user/updateBaseInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> updateBaseInfo(HttpServletRequest request, @Valid UpdateUserBaseInfoReq req) {
		userService.updateUserBaseInfo(req);
		return Result.respSuccess();
	}
	
	// 我的友链
	@RequestMapping(value = "/user/relatedLinks/list", method = RequestMethod.GET)
	@ResponseBody
	public Result<List<RelatedLinks>> myRelatedLinkList(HttpServletRequest request) {
		List<RelatedLinks> list = relatedLinksMapper.queryList(WebUtils.getLoginUserId(true));
		return Result.respSuccess(list);
	}
	
	// 添加
	@RequestMapping(value = "/user/relatedLinks/add", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> addRelatedLinks(HttpServletRequest request, @Valid AddRelatedLinksReq req) {
		userService.addRelatedLink(req);
		return Result.respSuccess();
	}
	
	// 删除
	@RequestMapping(value = "/user/relatedLinks/delete", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> delRelatedLinks(HttpServletRequest request, @Valid DelRelatedLinksReq req) {
		userService.delRelatedLink(req);
		return Result.respSuccess();
	}
	
	@RequestMapping(value = "/user/relatedLinks/move", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> moveRelatedLinks(HttpServletRequest request, @Valid MoveRelatedLinksReq req) {
		userService.moveRelatedLinks(req);
		return Result.respSuccess();
	}
	
	/**
	 * 查询我关注的或关注我的列表
	 * @author ZHANGZHENWEI 2020-8-27
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/user/follows/list", method = RequestMethod.GET)
	@ResponseBody
	public Result<PaginationResp<UserFollowsDTO>> queryFollowsList(HttpServletRequest request, @Valid UserFollowersReq req) {
		req.beforeDoBiz();
		IPage<UserFollowsDTO> followsList = userService.queryFollowsList(req);
		return Result.respSuccess(new PaginationResp(followsList));
	}
	
	/**
	 * 添加或取消关注
	 * @author ZHANGZHENWEI 2020-8-27
	 * @param followToUserId
	 * @return
	 */
	@RequestMapping(value = "/user/follows/changeFollow", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> changeFollow(Integer followToUserId) {
		Integer loginUserId = WebUtils.getLoginUserId(true);
		userService.changeFollow(loginUserId, followToUserId);
		return Result.respSuccess();
	}
	
}
