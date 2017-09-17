package com.github.frajimiba.commonstruct.ws;

import java.net.URI;
import java.net.URISyntaxException;

import com.github.frajimiba.commonstruct.validation.DefaultValidationHandler;
import com.github.frajimiba.commonstruct.validation.ValidationHandler;

public abstract class BaseService implements Service {

	private final URI endPoint;
	private ValidationHandler validationHandler;

	public BaseService(String urlEndPoint) {
		URI uriEndpoint;
		try {
			uriEndpoint = new URI(urlEndPoint);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}

		this.endPoint = uriEndpoint;
	}

	public URI getEndPoint() {
		return endPoint;
	}

	public ValidationHandler getValidationHandler() {
		return validationHandler;
	}

	public void setValidationHandler(ValidationHandler validationHandler) {
		this.validationHandler = validationHandler;
	}
	
	public <T> void validate(T object){
		if (this.validationHandler==null){
			this.validationHandler = new DefaultValidationHandler();
		}
		this.validationHandler.validate(object);
	}
	

}