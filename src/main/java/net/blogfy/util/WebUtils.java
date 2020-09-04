package net.blogfy.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.blogfy.exception.BlogfySessionException;

public class WebUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WebUtils.class);
	
	public static final String LOGIN_COOKIE_KEY = "blogfy_session_id";
	
	// 用户Cookie本地缓存
	private static Cache<String, Integer> LOGIN_COOKIE_CACHE = CacheBuilder.newBuilder()
			.expireAfterAccess(5, TimeUnit.MINUTES)
			.maximumSize(1000)
			.build();
	
	public static Integer getLoginUserIdFromCookie(HttpServletRequest request) {
		Cookie sessionCookie = getSessionCookie(request);
		if (sessionCookie == null) {
			return null;
		}
		String cookieValue = sessionCookie.getValue();
		Integer userId = LOGIN_COOKIE_CACHE.getIfPresent(cookieValue);
		if (userId != null) {
			logger.info("found session cookie from local guava cache, user id: {}", userId);
			return userId;
		}
		
		// 解密
		String decryptValue = null;
		try {
			decryptValue = AESCoder.decrypt(cookieValue);
			userId =  parseSessionCookie(decryptValue);
			logger.info("decrypt session user from cookie, user id: {}", userId);
			LOGIN_COOKIE_CACHE.put(cookieValue, userId); // 放入本地缓存
			return userId;
		} catch (Exception e) {
			logger.error("decrypt session cookie exception: " + e.getMessage(), e);
		}
		return null;
	}
	
	private static String formatSessionCookieValue(Integer userId, Long time) {
		return AESCoder.encrypt(userId + "-" + time);
	}
	
	private static Integer parseSessionCookie(String cookieValue) {
		return Integer.parseInt(cookieValue.split("\\-")[0]);
	}
	
	public static Cookie getSessionCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (LOGIN_COOKIE_KEY.equals(cookie.getName())) {
				return cookie;
			}
		}
		logger.info("request no login cookie.");
		return null;
	}
	
	/**
	 * 获取登录态的用户ID
	 * @author ZHANGZHENWEI 2020-8-13
	 * @return
	 */
	public static Integer getLoginUserId(boolean required) {
		String strUserId = MDC.get("U");
		Integer userId = (strUserId == null) ? null : Integer.parseInt(strUserId);
		if (userId == null && required) {
			throw new BlogfySessionException("未获取到用户登录态信息");
		}
		return userId;
	}
	
	public static void setLoginUserId(HttpServletRequest request, HttpServletResponse response, Integer userId) {
		// 设置cookie
		Cookie sessionCookie = getSessionCookie(request);
		if (sessionCookie == null) {
			sessionCookie = new Cookie(LOGIN_COOKIE_KEY, formatSessionCookieValue(userId, System.currentTimeMillis()));
			sessionCookie.setPath("/");
			sessionCookie.setMaxAge(getCookieAge());
			response.addCookie(sessionCookie);
			logger.info("add session cookie, user id: {}", userId);
		}
		
		setMDCUser(userId);
	}
	
	// cookie到期时间，后天的早上4点。
	private static int getCookieAge() {
		Date today = DateUtils.parse(DateUtils.format(new Date(), DateUtils.PAT_DATE), DateUtils.PAT_DATE);
		long endTime = new DateTime(today).plusDays(2).plusHours(4).toDate().getTime();
		return (int) (endTime - System.currentTimeMillis()) / 1000;
	}
	
	public static void setMDCUser(Integer userId) {
		// 设置MDC
		MDC.put("U", String.valueOf(userId));
		logger.info("set mdc user, user id: {}", userId);
	}
	
	public static void removeSessionUser(HttpServletRequest request) {
		// 移除Cookie
		Cookie sessionCookie = getSessionCookie(request);
		sessionCookie.setMaxAge(0);
		
		// MDC里面也清除
		MDC.remove("U");
	}
	
}
