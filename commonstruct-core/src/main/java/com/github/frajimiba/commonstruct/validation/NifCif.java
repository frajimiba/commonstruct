package com.github.frajimiba.commonstruct.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The NIF/CIF interface.
 * 
 * @author Francisco José Jiménez
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NifCifValidator.class)
public @interface NifCif {
	/**
	 * The error message template.
	 */
	String message() default "{errors.nifcif.invalid}";

	/**
	 * The groups the constraint belongs to.
	 */
	Class<?>[] groups() default {};

	/**
	 * The payload associated to the constraint.
	 */
	Class<? extends Payload>[] payload() default {};
}