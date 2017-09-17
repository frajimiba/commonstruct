package com.github.frajimiba.commonstruct.specification.common;

import org.apache.commons.validator.routines.EmailValidator;

import com.github.frajimiba.commonstruct.specification.CompositeSpecification;

/**
 * Valida que un email introducido sea correcto.
 *
 * @author Francisco José Jiménez
 *
 */
public class EmailSpecification extends CompositeSpecification<String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(String candidate) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(candidate);
	}

}
