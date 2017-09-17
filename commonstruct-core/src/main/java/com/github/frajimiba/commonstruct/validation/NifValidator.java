package com.github.frajimiba.commonstruct.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.frajimiba.commonstruct.specification.Specification;
import com.github.frajimiba.commonstruct.specification.common.NifSpecification;

/**
 * Valida que un cif introducido sea correcto.
 *
 * @author Francisco José Jiménez
 *
 */
public class NifValidator implements ConstraintValidator<Nif, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Nif nif) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || value.length() == 0) {
			return true;
		}
		Specification<String> specification = new NifSpecification();
		return specification.isSatisfiedBy(value);
	}

}
