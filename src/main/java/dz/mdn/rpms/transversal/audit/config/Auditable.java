/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: Auditable
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Configuration
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.transversal.audit.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String action();
    String resourceType();
    String idField() default ""; // Field name containing the resource ID in the return object
}