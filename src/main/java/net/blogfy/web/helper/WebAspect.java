package net.blogfy.web.helper;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;

/**
 * 通过AOP打印接口返回
 * @author ZHANGZHENWEI 2018年9月20日
 */
@Aspect
@Component
public class WebAspect {
	
	/**
	 * 拦截所有WEB接口
	 */
	private static final String POINT_CUT_WEB_API = "@annotation(org.springframework.web.bind.annotation.ResponseBody)";

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private ObjectMapper objectMapper;
	
	/**
	 * 一些接口可能返回的数据很大，就不打印返回的JSON内容。
	 * @author ZHANGZHENWEI 2020-3-26
	 */
	private static final Set<String> IGNORE_LOG_RETURN = Sets.newHashSet(
			Arrays.asList(
			
			)
	);
	
	/**
	 * 打印接口返回
	 * @author ZHANGZHENWEI 2018年11月22日
	 * @param joinPoint
	 * @param result
	 * @throws Throwable
	 */
	@AfterReturning(pointcut = POINT_CUT_WEB_API, returning = "result")
	public void doLogReturn(JoinPoint joinPoint, Object result) throws Exception {
		boolean needLogResult = needLogResult(joinPoint);
		if (needLogResult) {
			logger.info(">>> Complete {}, return: {}", joinPoint.getSignature().toShortString(), objectMapper.writeValueAsString(result));
		} else {
			logger.info(">>> Complete {}", joinPoint.getSignature().toShortString());
		}
	}
	
	/**
	 * 是否需要打印返回内容
	 * @author ZHANGZHENWEI 2020-3-27
	 * @param joinPoint
	 * @return
	 */
	private boolean needLogResult(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		if (!(signature instanceof MethodSignature)) {
			return false;
		}
		
		MethodSignature methodSignature = (MethodSignature) signature;
		
		// 只打返回JSON的
		ResponseBody[] responseBodyAnnotations = methodSignature.getMethod().getAnnotationsByType(ResponseBody.class);
		if (responseBodyAnnotations == null) {
			return false;
		}
		
		RequestMapping[] annotationsByType = methodSignature.getMethod().getAnnotationsByType(RequestMapping.class);
		if (annotationsByType == null) {
			return false;
		}
		
		for (RequestMapping requestMapping : annotationsByType) {
			for (String url : requestMapping.value()) {
				if (IGNORE_LOG_RETURN.contains(url)) { // 在忽略集合中，则不打印返回内容。
					return false;
				}
			}
		}
		
		return true;
	}
	
}
