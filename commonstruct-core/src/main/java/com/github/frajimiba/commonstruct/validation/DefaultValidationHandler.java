package com.github.frajimiba.commonstruct.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

public class DefaultValidationHandler extends BaseValidationHandler {
	@Override
	public <T> void validate(T object) {
		Set<ConstraintViolation<T>> violations = this.getValidator().validate(object);
		
		boolean hasViolation = false;
		
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<T> violation : violations) {
			hasViolation = true;
			sb.append("[" + violation.getRootBeanClass().getName() + "." + violation.getPropertyPath() + "] : " 
			+ violation.getMessage() + " - ( " + violation.getInvalidValue().toString() + " ) \n");
		}
		
		if (hasViolation){
			throw new ValidationException(sb.toString());
		}
	}
}