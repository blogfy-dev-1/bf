package net.blogfy.interceptor;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.blogfy.config.Env;
import net.blogfy.util.MyStringUtils;
import net.blogfy.util.CommonConstants;
import net.blogfy.util.WebUtils;

@Component
public class WebInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 本项目使用的HTTP请求方式，其它的都不被接受。
	 */
	private static final Set<String> ALLOWED_HTTP_METHOD = Sets.newHashSet(
			HttpMethod.GET.toString(),
			HttpMethod.POST.toString(),
			HttpMethod.OPTIONS.toString()
	);
	
	@Resource
	private ObjectMapper objectMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();
		
		// 一些安全方面的检查
		
		// 为了抵御不安全的HTTP请求方式的攻击，通过Filter限定只能支持某些HTTP请求方法。
		String httpMethod = request.getMethod();
		if (!ALLOWED_HTTP_METHOD.contains(StringUtils.upperCase(httpMethod))) {
			logger.error("unsupported http method: {}", httpMethod);
			response.getWriter().write("Return from filter, unsupported http method: " + httpMethod);
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return false;
		}
		
		// CSRF 跨站点请求伪造
		if (Env.isPrd()) { // 开发环境忽略
			String referer = request.getHeader(HttpHeaders.REFERER);
			if (referer != null && referer.indexOf("blogfy.net") == -1) { // 只允许自己域名访问
				logger.info("seemingly csrf, referer: {}", referer);
				response.getWriter().write("Return from security filter, seemingly csrf.");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
		}
		
		// 静态文件不拦截
		if (isExcludePath(requestUri)) {
			return true;
		}
		
		// 设置MDC
		String thread = MyStringUtils.IdMarker();
		MDC.put("T", thread);
		
		// 打印日志
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		logger.info("Request uri: {}, remote address: {}, user agent: {}", requestUri, request.getRemoteAddr(), userAgent);
		
		// 如果登录态生效的话，将用户ID设置到MDC中。
		Integer loginUserId = WebUtils.getLoginUserIdFromCookie(request);
		if (loginUserId != null) {
			WebUtils.setMDCUser(loginUserId);
		}
		
		// 设置一些响应头
		response.setHeader("t", thread);
		response.setHeader("x-frame-options", "SAMEORIGIN");
		
		return true;
	}
	
	// 不拦截的请求
	private boolean isExcludePath(String requestUri) {
		int i = requestUri.lastIndexOf(".");
		if (i == -1) {
			return false;
		}
		String postfix = requestUri.substring(i + 1);
		return CommonConstants.STATIC_POSTFIX.contains(postfix);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		String requestUri = request.getRequestURI();
		if (!isExcludePath(requestUri)) {
			logger.info(">>> Complete uri: {}.", requestUri);
		}
		MDC.clear(); // 清除MDC
	}
}
