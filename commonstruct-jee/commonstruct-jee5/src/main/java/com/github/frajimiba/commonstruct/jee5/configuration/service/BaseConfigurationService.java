package com.github.frajimiba.commonstruct.jee5.configuration.service;

import java.io.Serializable;

import org.apache.commons.beanutils.ConversionException;

import com.github.frajimiba.commonstruct.configuration.BaseConfiguration;
import com.github.frajimiba.commonstruct.configuration.ConfigurationEntry;
import com.github.frajimiba.commonstruct.configuration.ConfigurationException;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.jee5.configuration.data.JeeConfigurationRepository;
import com.github.frajimiba.commonstruct.jee5.domain.service.BaseJeeDomainService;


public abstract class BaseConfigurationService<E extends BaseConfiguration<ID>, ID extends Serializable>
		extends BaseJeeDomainService<E, ID> implements ConfigurationService<E, ID> {

	@Override
	public abstract JeeConfigurationRepository<E, ID> getRepository();
			
	@Override
	public Object get(ConfigurationEntry entry) {
		Object result = null;
		E configuration = this.getRepository().findByKey(entry.getName());
		if (configuration != null) {
			if (entry.getType().getName().equals(configuration.getType())) {
				if (this.getConverterManager().lookup(entry.getType())==null){
					throw new ConversionException("There is no converter for the type: " + entry.getType().toString());
				}
				result = this.getConverterManager().convert(configuration.getValue(), entry.getType());
			} else {
				throw new ConfigurationException("Invalid configuraion type.");
			}
		}
		return result;
	}

	@Override
	public void put(ConfigurationEntry entry, Object value) {
		E configuration = this.getTypeInstance();
		configuration.setKey(entry.getName());
		if (value.getClass().equals(entry.getType())) {
			configuration.setType(value.getClass().getName());
			if (this.getConverterManager().lookup(String.class, entry.getType())==null){
				throw new ConversionException("There is no converter for the type: " + entry.getType().toString());
			}
			configuration.setValue(this.getConverterManager().convert(value, String.class).toString());
			this.getRepository().save(configuration);
		} else {
			throw new ConfigurationException("Invalid configuraion type.");
		}
	}
	
}