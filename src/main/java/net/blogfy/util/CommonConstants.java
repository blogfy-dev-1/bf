package net.blogfy.util;

import java.util.Set;

import org.apache.commons.compress.utils.Sets;

public class CommonConstants {
	
	public static final String PROJECT_BASE_PKG = "net.blogfy";
	
	// BlogFy官方用户
	public static final int OFFICIAL_USER_ID = 10000000;

	// 系统用户
	public static final int SYS_USER_ID = 99999999;
	
	public static final Set<String> STATIC_POSTFIX = Sets.newHashSet("html", "js", "css", "png", "jpg", "jpeg", "gif", "svg", "ico", "woff", "ttf", "map");
}
