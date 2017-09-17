package com.github.frajimiba.commonstruct.jee5.data.converter;

import java.util.Locale;

import org.apache.commons.beanutils.converters.AbstractConverter;

public class LocaleConverter extends AbstractConverter {

	@Override
	protected Class<?> getDefaultType() {
		return Locale.class;
	}
	
	@Override
	protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
		T result = null;
		if (Locale.class.equals(type)){
			result = type.cast(new Locale(value.toString()));
		}else{
			throw conversionException(type, value);
		}
		return result;
	}


}
