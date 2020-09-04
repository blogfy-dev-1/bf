package net.blogfy.util.restful;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.blogfy.util.DateUtils;

/**
 * 接口统一返回格式。
 */
@Getter
@Setter
public class Result<T> {
	
	/** 返回码 */
	private int flag;
	
	/** 返回消息 */
	private String msg;
	
	/** 数据内容 */
	private T data;
	
	/** 服务器时间 */
	private Date serverTime = DateUtils.now();
	
	private Result(int flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}
	
	private Result(int flag, String msg, T data) {
		this(flag, msg);
		this.data = data;
	}
	
	public static <T> Result<T> respSuccess() {
		return new Result<>(RetCode.FLAG_SUCCESS, RetCode.getMsg(RetCode.FLAG_SUCCESS));
	}

	public static <T> Result<T> respSuccess(T data) {
		return new Result<>(RetCode.FLAG_SUCCESS, RetCode.getMsg(RetCode.FLAG_SUCCESS), data);
	}
	
	public static <T> Result<T> respFail() {
		return new Result<>(RetCode.FLAG_FAIL, RetCode.getMsg(RetCode.FLAG_FAIL));
	}
	
	public static <T> Result<T> respFail(String msg) {
		return new Result<>(RetCode.FLAG_FAIL, msg);
	}

	public static <T> Result<T> respFail(T data) {
		return new Result<>(RetCode.FLAG_FAIL, RetCode.getMsg(RetCode.FLAG_FAIL), data);
	}

	public static <T> Result<T> respFail(String msg, T data) {
		return new Result<>(RetCode.FLAG_FAIL, msg, data);
	}

	public static <T> Result<T> respFor(int flag, String msg, T data) {
		return new Result<>(flag, msg, data);
	}
	
	public static <T> Result<T> respUnLogin() {
		return new Result<>(RetCode.FLAG_UN_LOGIN, RetCode.getMsg(RetCode.FLAG_UN_LOGIN));
	}

	/**
	 * 返回无权限
	 * @param
	 * @return
	 */
	public static <T> Result<T> respNoAuthorized() {
		return new Result<>(RetCode.FLAG_NO_AUTHORIZED, RetCode.getMsg(RetCode.FLAG_NO_AUTHORIZED));
	}

	public static <T> Result<T> respNoAuthorized(String msg) {
		return new Result<>(RetCode.FLAG_NO_AUTHORIZED, msg);
	}
	
}
