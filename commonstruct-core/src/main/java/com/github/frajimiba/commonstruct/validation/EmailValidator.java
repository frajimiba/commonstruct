package com.github.frajimiba.commonstruct.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.frajimiba.commonstruct.specification.Specification;
import com.github.frajimiba.commonstruct.specification.common.EmailSpecification;

/**
 * Valida que un email introducido sea correcto.
 *
 * @author Francisco José Jiménez
 *
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Email email) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		Specification<String> specification = new EmailSpecification();
		return specification.isSatisfiedBy(value);
	}

}