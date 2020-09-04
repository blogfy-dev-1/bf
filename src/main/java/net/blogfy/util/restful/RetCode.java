package net.blogfy.util.restful;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码定义。
 */
public class RetCode {
	
	public static final int FLAG_SUCCESS = 0;
	public static final int FLAG_FAIL = -1;
	public static final int FLAG_UN_LOGIN = -2;
	/** 无权限 */
	public static final int FLAG_NO_AUTHORIZED = -3;
	
	private static Map<Object, String> flagMap = new HashMap<>();
	
	static {
		flagMap.put(FLAG_SUCCESS, "操作成功");
		flagMap.put(FLAG_FAIL, "操作失败");
		flagMap.put(FLAG_UN_LOGIN, "用户未登陆");
		flagMap.put(FLAG_NO_AUTHORIZED, "权限不足");
	}
	
	/**
	 * 获取返回码对应的MSG
	 * @author ZHANGZHENWEI 2016-8-5
	 * @param code
	 * @return
	 */
	public static String getMsg(Object code) {
		return flagMap.get(code);
	}
}
