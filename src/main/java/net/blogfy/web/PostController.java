package net.blogfy.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.blogfy.service.AdsService;
import net.blogfy.service.PostService;
import net.blogfy.web.helper.BasicController;

@Controller
public class PostController extends BasicController {
	
	@Resource
	private PostService postService;
	@Resource
	private AdsService adsService;
	
	@RequestMapping(value = "/p/{postId}", method = RequestMethod.GET)
	public String postDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("postId") String postId, Model model) {
		model.addAttribute("postId", postId);
		System.out.println(adsService.getShowAds());
		return "post_detail";
	}
	
}
