package dz.mdn.rpms.exception.domain.model;

public class RateLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = -1451995346481666031L;

	public RateLimitExceededException(String message) {
        super(message);
    }
	
}