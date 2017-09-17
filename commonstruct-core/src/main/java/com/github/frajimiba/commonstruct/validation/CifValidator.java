package com.github.frajimiba.commonstruct.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.frajimiba.commonstruct.specification.Specification;
import com.github.frajimiba.commonstruct.specification.common.CifSpecification;

/**
 * Valida que un cif introducido sea correcto según la Orden EHA/451/2008.
 *
 * Ver http://www.boe.es/buscar/doc.php?id=BOE-A-2008-3580
 *
 * @author Francisco José Jiménez
 *
 */
public class CifValidator implements ConstraintValidator<Cif, String> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Cif nif) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		Specification<String> specification = new CifSpecification();
		return specification.isSatisfiedBy(value);
	}
	
}