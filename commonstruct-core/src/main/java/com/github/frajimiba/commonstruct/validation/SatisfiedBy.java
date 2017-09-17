package com.github.frajimiba.commonstruct.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.frajimiba.commonstruct.specification.Specification;

/**
 * The annotated element must satisfied by the specification.
 * 
 * @author Francisco José Jiménez
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SatisfiedByValidator.class)
public @interface SatisfiedBy {
  /**
   * The specification class.
   */
  Class<? extends Specification<?>> value();

  /**
   * The error message template.
   */
  String message() default "{errors.specification.invalid}";

  /**
   * The groups the constraint belongs to.
   */
  Class<?>[] groups() default {};

  /**
   * The payload associated to the constraint.
   */
  Class<? extends Payload>[] payload() default {};
}
