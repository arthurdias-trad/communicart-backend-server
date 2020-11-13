package br.com.communicart.backendserver.exception;

public class UserNotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotAuthorizedException(String message) {
		super(message);
	}
}
