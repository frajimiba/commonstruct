package com.github.frajimiba.commonstruct.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class BaseValidationHandler implements ValidationHandler {

	private final Validator validator;

	public BaseValidationHandler() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	public Validator getValidator() {
		return this.validator;
	}

}