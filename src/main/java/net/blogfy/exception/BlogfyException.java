package net.blogfy.exception;

public class BlogfyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BlogfyException(String errorMessage) {
		super(errorMessage);
	}
	
	public BlogfyException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
}
