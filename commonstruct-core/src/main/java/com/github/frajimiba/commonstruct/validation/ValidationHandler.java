package com.github.frajimiba.commonstruct.validation;

public interface ValidationHandler {
	<T> void validate(T object);
}