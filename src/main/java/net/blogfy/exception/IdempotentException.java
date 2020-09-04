package net.blogfy.exception;

public class IdempotentException extends BlogfyException {
	
	public IdempotentException(String errorMessage) {
		super(errorMessage);
	}
	
	public IdempotentException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
}
