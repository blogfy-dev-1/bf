package net.blogfy.event.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import net.blogfy.event.BlogfyEvent;

@Component
public class BlogfyEventPublish implements ApplicationEventPublisherAware {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void publishEvent(BlogfyEvent event) {
		logger.info("Publish event: {}", event.toString());
		applicationEventPublisher.publishEvent(event);
	}
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
}
