package com.github.frajimiba.commonstruct.jee5.data.converter;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

public class ConverterManagerImpl implements ConverterManager {
	
	private ConvertUtilsBean converterUtilBean;
	
	public ConverterManagerImpl() {
		converterUtilBean = new ConvertUtilsBean();
	}
	
	@Override
	public String convert(Object value) {
		return converterUtilBean.convert(value);
	}

	@Override
	public Object convert(String value, Class<?> clazz) {
		return converterUtilBean.convert(value, clazz);
	}

	@Override
	public Object convert(String[] values, Class<?> clazz) {
		return converterUtilBean.convert(values, clazz);
	}

	@Override
	public Object convert(Object value, Class<?> targetType) {
		return converterUtilBean.convert(value, targetType);
	}

	@Override
	public void deregister() {
		converterUtilBean.deregister();
		
	}

	@Override
	public void deregister(Class<?> clazz) {
		converterUtilBean.deregister(clazz);
	}

	@Override
	public Converter lookup(Class<?> clazz) {
		return converterUtilBean.lookup(clazz);
	}

	@Override
	public Converter lookup(Class<?> sourceType, Class<?> targetType) {
		return converterUtilBean.lookup(sourceType,targetType);
	}

	@Override
	public void register(Converter converter, Class<?> clazz) {
		converterUtilBean.register(converter, clazz);
	}
}