package net.blogfy.event;

import org.springframework.context.ApplicationEvent;

public abstract class BlogfyEvent extends ApplicationEvent {
	
	public BlogfyEvent(Object source) {
		super(source);
	}
}
