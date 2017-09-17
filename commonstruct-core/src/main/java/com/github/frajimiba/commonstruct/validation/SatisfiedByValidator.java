package com.github.frajimiba.commonstruct.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import com.github.frajimiba.commonstruct.specification.Specification;

/**
 * Defines the logic to validate a constraint specification.
 * 
 * @author Francisco José Jiménez
 *
 */
public class SatisfiedByValidator implements ConstraintValidator<SatisfiedBy, Object> {
  /**
   * String to instantiate error.
   */
  private static final String INSTANTIATE_ERROR = "Instantiate specification class error";

  /**
   * The Specification Class.
   */
  private Specification<Object> specification;

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void initialize(SatisfiedBy specification) {
    Class<?> clazz = specification.value();
    try {
      this.specification = (Specification<Object>) clazz.newInstance();
    } catch (InstantiationException e) {
      throw new ValidationException(INSTANTIATE_ERROR, e);
    } catch (IllegalAccessException e) {
      throw new ValidationException(INSTANTIATE_ERROR, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    boolean result = false;

    if (value == null || (value instanceof String && ((String) value).length() == 0)) {
      result = true;
    } else {
      result = this.specification.isSatisfiedBy(value);
    }

    return result;
  }
}
