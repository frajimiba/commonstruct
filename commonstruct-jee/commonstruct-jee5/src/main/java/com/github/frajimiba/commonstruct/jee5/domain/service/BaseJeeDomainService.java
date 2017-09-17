package com.github.frajimiba.commonstruct.jee5.domain.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.domain.service.BaseDomainService;
import com.github.frajimiba.commonstruct.jee5.data.converter.ConverterManager;

public abstract class BaseJeeDomainService<T extends BaseEntity<ID>, ID extends Serializable>  
	extends BaseDomainService<T, ID> implements JeeDomainService<T,ID>{

	@EJB
	public ConverterManager converterManager;
	
	@PostConstruct
	public void init(){
		if (converterManager.lookup(getEntityType())==null) {
			converterManager.register(
				new AbstractConverter(){
					@Override
					protected <C> C convertToType(Class<C> type, Object value) throws Throwable {
						C result = null;
						try{
							ID id = getEntityIdType().cast(converterManager.convert(value,  getEntityIdType()));
							result = type.cast(getRepository().findOne(id));
						}catch(ClassCastException exception){
							throw new ConversionException("Formatter error: The id type of class must be '" + getEntityIdType().getName() + "'");
						}
						return result;
					}
					@Override
					protected Class<?> getDefaultType() {
						return getEntityType();
					}
				}, getEntityType()
			);
		}
		
	}
	
	@Override
	public ConverterManager getConverterManager(){
		return converterManager;
	}
}