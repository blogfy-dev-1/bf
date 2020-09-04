package net.blogfy.util.restful;

/**
 * 封装请求参数的基类
 * @author ZHANGZHENWEI 2018年11月22日
 */
public interface BaseReq {
	
	/**
	 * 业务逻辑开始前的操作，一般用来处理参数转换，校验等。
	 * @author ZHANGZHENWEI 2020-3-19
	 * @return
	 */
	default void beforeDoBiz() {
		
	}
}
