package net.blogfy.web.helper;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public abstract class BasicController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	protected RestTemplate restTemplate;
	
}
