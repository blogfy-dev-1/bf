package net.blogfy.event;

public class PostEvent extends BlogfyEvent {
	
	public enum PostEventType {
		CREATE,
		UPDATE,
		DELETE,
		VIEW
	}
	
	private PostEventType postEventType;
	
	public PostEvent(PostEventType postEventType, Object source) {
		super(source);
		this.postEventType = postEventType;
	}
}
