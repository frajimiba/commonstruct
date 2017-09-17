package com.github.frajimiba.commonstruct.jee5.data.converter;

import org.apache.commons.beanutils.Converter;

public interface ConverterManager {
	String convert(Object value);
	Object convert(String value, Class<?> clazz);
	Object convert(String[] values, Class<?> clazz);
	Object convert(Object value, Class<?> targetType);
	void deregister();
	void deregister(Class<?> clazz);
	Converter lookup(Class<?> clazz);
	Converter lookup(Class<?> sourceType, Class<?> targetType);
	void register(Converter converter, Class<?> clazz);
}
