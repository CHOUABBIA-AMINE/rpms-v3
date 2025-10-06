package dz.mdn.rpms.exception.domain.model;

public class TokenRefreshException extends RuntimeException {

	private static final long serialVersionUID = 8406998311126514918L;

	public TokenRefreshException(String message) {
        super(message);
    }

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}