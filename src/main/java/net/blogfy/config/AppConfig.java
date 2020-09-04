package net.blogfy.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;

import net.blogfy.config.ext.MyVersionResourceResolver;
import net.blogfy.interceptor.WebInterceptor;
import net.blogfy.util.CommonConstants;

@Configuration
@EnableAsync
@EnableScheduling
@MapperScan(CommonConstants.PROJECT_BASE_PKG + ".mapper")
@EnableConfigurationProperties(OOSProperties.class)
public class AppConfig implements WebMvcConfigurer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private Env env;
	@Resource
	private WebInterceptor webInterceptor;
	
	/**
	 * SpringBoot会通过RedisAutoConfiguration自动创建redisTemplate的Bean。
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setValueSerializer(RedisSerializer.json());
		redisTemplate.setHashValueSerializer(RedisSerializer.json());
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webInterceptor).addPathPatterns("/**/*");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		boolean cacheResources = false;
		CacheControl cacheControl = CacheControl.noStore();
		// 生产环境缓存
		if (Env.isPrd()) {
			cacheResources = true;
			cacheControl = CacheControl.maxAge(10, TimeUnit.DAYS).cachePrivate();
		}
		registry.addResourceHandler("/static/**")
				.addResourceLocations("classpath:/templates/static/")
				.setCacheControl(cacheControl)
				.resourceChain(cacheResources)
				.addResolver(new MyVersionResourceResolver().addContentVersionStrategy("/**"));
	}
	
	// 跨域
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		String origins = "*";
		if (Env.isPrd()) {
			origins = "*.blogfy.net";
		}
		registry.addMapping("/**").allowedOrigins(origins).allowedMethods(HttpMethod.GET.toString(), HttpMethod.POST.toString(), HttpMethod.OPTIONS.toString());
	}
	
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 兼容IE下返回“application/json”会下载的问题，如果请求是HTML则返回也是HTML。
		MappingJackson2HttpMessageConverter htmlMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		htmlMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		converters.add(htmlMessageConverter);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//		设置请求的页面大于最大页后操作，true调回到首页，false 继续请求 默认false
//		paginationInterceptor.setOverflow(false);
//		设置最大单页限制数量，默认 500 条，-1 不受限制
//		paginationInterceptor.setLimit(500);
//		开启 count 的 join 优化, 只针对部分 left join
		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		return paginationInterceptor;
	}
	
	
}
