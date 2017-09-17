package com.github.frajimiba.commonstruct.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.frajimiba.commonstruct.specification.CompositeSpecification;
import com.github.frajimiba.commonstruct.specification.common.CifSpecification;
import com.github.frajimiba.commonstruct.specification.common.NifSpecification;

/**
 * Valida que un nif/cif introducido sea correcto.
 *
 * @author Francisco José Jiménez
 *
 */
public class NifCifValidator implements ConstraintValidator<NifCif, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(NifCif nif) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		CompositeSpecification<String> nifSpecification = new NifSpecification();
		CompositeSpecification<String> cifSpecification = new CifSpecification();
		return nifSpecification.or(cifSpecification).isSatisfiedBy(value);	
	}

}