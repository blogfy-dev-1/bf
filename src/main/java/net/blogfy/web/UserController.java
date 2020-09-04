package net.blogfy.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.blogfy.config.GithubProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return "redirect:" + path;
	}

	@RequestMapping(value = "/user/github/loginCallback", method = RequestMethod.GET)
	public String githubLoginCallback(HttpServletRequest request, String code) {
		return null;
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> login(HttpServletRequest request, HttpServletResponse response, @Valid UserLoginReq req) {
		UserBaseInfo loginUser = userService.login(req);
		WebUtils.setLoginUserId(request, response, loginUser.getUserId());
		return Result.respSuccess();
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public Result<Object> register(String email) {
		userService.register(email);
		return Result.respSuccess();
	}
	
	// 验证邮箱里的注册码
	@RequestMapping(value = "/user/validateEmailCode", method = RequestMethod.GET)
	public String validateEmailCode(HttpServletRequest request, String email, String code) {
		boolean b = userService.validateEmailCode(email, code);
		return "user/user_set_pwd";
	}
	
	@RequestMapping(value = "/user/finishRegister", method = RequestMethod.POST)
	@ResponseBody
	public Result<Object> finishRegister(@Valid FinishRegisterReq req) {
		userService.finishRegister(req);
		return Result.respSuccess();
	}
	
	@RequestMapping(value = "/user/updateBaseInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result<Object> updateBaseInfo(HttpServletRequest request, @Valid UpdateUserBaseInfoReq req) {
		userService.updateUserBaseInfo(req);
		return Result.respSuccess();
	}
	
	@RequestMapping(value = "/md5", method = RequestMethod.GET)
	public String md5(HttpServletRequest request, UpdateUserBaseInfoReq req, Model model) {
		model.addAttribute("msg", "MSG");
		System.out.println(restTemplate);
		return "md5";
	}
	
	// 我的友链
	@RequestMapping(value = "/user/relatedLinks/list", method = RequestMethod.GET)
	public Result<List<RelatedLinks>> myRelatedLinkList(HttpServletRequest request) {
		List<RelatedLinks> list = relatedLinksMapper.queryList(10000000);
		return Result.respSuccess(list);
	}
	
	// 添加
	@RequestMapping(value = "/user/relatedLinks/add", method = RequestMethod.GET)
	public Result<Object> addRelatedLinks(HttpServletRequest request, @Valid AddRelatedLinksReq req) {
		userService.addRelatedLink(req);
		return Result.respSuccess();
	}
	
	// 删除
	@RequestMapping(value = "/user/relatedLinks/delete", method = RequestMethod.GET)
	public Result<Object> delRelatedLinks(HttpServletRequest request, @Valid DelRelatedLinksReq req) {
		userService.delRelatedLink(req);
		return Result.respSuccess();
	}
	
	@RequestMapping(value = "/user/relatedLinks/move", method = RequestMethod.GET)
	public Result<Object> moveRelatedLinks(HttpServletRequest request, @Valid MoveRelatedLinksReq req) {
		userService.moveRelatedLinks(req);
		return Result.respSuccess();
	}
	
	/**
	 * 查询我关注的或关注我的列表
	 * @author ZHANGZHENWEI845 2020-8-27
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
	 * @author ZHANGZHENWEI845 2020-8-27
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
