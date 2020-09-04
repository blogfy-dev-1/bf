package net.blogfy.exception;

public class BlogfySessionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BlogfySessionException(String errorMessage) {
		super(errorMessage);
	}
	
	public BlogfySessionException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
}
