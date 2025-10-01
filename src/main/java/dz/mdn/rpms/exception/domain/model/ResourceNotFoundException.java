package dz.mdn.rpms.exception.domain.model;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2986089396691476895L;

	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}