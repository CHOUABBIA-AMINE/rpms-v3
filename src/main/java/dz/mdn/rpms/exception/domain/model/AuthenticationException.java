package dz.mdn.rpms.exception.domain.model;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -2308349596118457204L;

	public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}