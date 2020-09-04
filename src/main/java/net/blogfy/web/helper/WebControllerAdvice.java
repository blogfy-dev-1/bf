package net.blogfy.web.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.blogfy.exception.BlogfySessionException;
import net.blogfy.util.restful.Result;

@ControllerAdvice
public class WebControllerAdvice {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object errorHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		if (exception instanceof BlogfySessionException) {
			return Result.respUnLogin();
		}
		
		logger.error("Handle error: " + exception.getMessage(), exception);
		return Result.respFail("Operation exception: " + exception.getMessage());
	}
	
}
