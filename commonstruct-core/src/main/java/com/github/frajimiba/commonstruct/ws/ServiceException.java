package com.github.frajimiba.commonstruct.ws;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 6417173448700480136L;
	
	public ServiceException(String descripcion) {
		super(descripcion);
	}
	
	public ServiceException(String descripcion, Throwable throwable) {
		super(descripcion, throwable);
	}

}