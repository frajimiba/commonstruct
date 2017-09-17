package com.github.frajimiba.commonstruct.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Email interface.
 * 
 * @author Francisco José Jiménez
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {
	/**
	 * The error message template.
	 */
	String message() default "{errors.email.invalid}";

	/**
	 * The groups the constraint belongs to.
	 */
	Class<?>[] groups() default {};

	/**
	 * The payload associated to the constraint.
	 */
	Class<? extends Payload>[] payload() default {};
}