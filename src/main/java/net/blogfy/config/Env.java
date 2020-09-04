package net.blogfy.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public final class Env implements InitializingBean {
	
	private static Logger logger = LoggerFactory.getLogger(Env.class);
	
	public static final String DEV = "dev";
	public static final String PRD = "prd";
	
	private static String ENV = null;
	private static String SERVER_HOST = null;
	private static String CONTEXT_PATH = null;
	
	@Setter
	@Value("${spring.profiles.active}")
	private String env; // 环境
	
	@Getter
	@Setter
	@Value("${server.host}")
	private String serverHost; // 域名
	
	@Getter
	@Setter
	@Value("${server.servlet.context-path}")
	private String contextPath; // 项目路径
	
	public static boolean isPrd() {
		return StringUtils.equalsIgnoreCase(ENV, PRD);
	}
	
	public static String getServerHost() {
		return SERVER_HOST;
	}
	
	public static String getContextPath() {
		return CONTEXT_PATH;
	}
	
	@Override
	public void afterPropertiesSet() {
		ENV = env;
		logger.info("env: {}", ENV);
		
		SERVER_HOST = serverHost;
		logger.info("server host: {}", SERVER_HOST);
		
		CONTEXT_PATH = contextPath;
		logger.info("context path: {}", CONTEXT_PATH);
	}
	
}
