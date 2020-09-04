package net.blogfy.util;

import net.blogfy.config.Env;

public class PostUtils {
	
	public static String getPostUrl(int postId) {
		return Env.getServerHost() + Env.getContextPath() + "/p/" + postId;
	}
	
	/**
	 * 文章状态
	 * @author ZHANGZHENWEI 2020-8-25
	 */
	public static final class PostStatus {
		public static final int OPEN = 1;
		public static final int PRIVATE = 2;
		public static final int DELETE = 3;
	}
	
	
}
