package net.blogfy.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.blogfy.dto.user.UpdateUserBaseInfoReq;
import net.blogfy.web.helper.BasicController;

@Controller
public class DemoController extends BasicController {
	
	@RequestMapping(value = "/md5", method = RequestMethod.GET)
	public String md5(HttpServletRequest request, UpdateUserBaseInfoReq req, Model model) {
		model.addAttribute("msg", "MSG");
		System.out.println(restTemplate);
		return "md5";
	}
	
}
