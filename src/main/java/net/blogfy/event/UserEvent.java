package net.blogfy.event;

public class UserEvent extends BlogfyEvent {
	
	public enum UserEventType {
		CREATE,
		UPDATE,
		DELETE
	}
	
	private UserEventType userEventType;
	
	public UserEvent(UserEventType userEventType, Object source) {
		super(source);
		this.userEventType = userEventType;
	}
}
