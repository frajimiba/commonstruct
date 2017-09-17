package com.github.frajimiba.commonstruct.spring.configuration.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.configuration.ConfigurationEntry;
import com.github.frajimiba.commonstruct.configuration.ConfigurationException;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.spring.configuration.SpringConfiguration;
import com.github.frajimiba.commonstruct.spring.configuration.data.SpringConfigurationRepository;
import com.github.frajimiba.commonstruct.spring.domain.service.AbstractSpringDomainService;

public abstract class SpringConfigurationService<E extends SpringConfiguration<ID>, ID extends Serializable>
    extends AbstractSpringDomainService<E, ID> implements ConfigurationService<E, ID> {

  @Override
  public abstract SpringConfigurationRepository<E, ID> getRepository();

  @Override
  public Object get(ConfigurationEntry entry) {
    Object result = null;
    E configuration = this.getRepository().findByKey(entry.getName());
    if (configuration != null) {
      if (entry.getType().getName().equals(configuration.getType())) {
        result = this.getConversionService().convert(configuration.getValue(), entry.getType());
      } else {
        throw new ConfigurationException("Invalid configuraion type.");
      }
    }
    return result;
  }

  @Override
  @Transactional
  public void put(ConfigurationEntry entry, Object value) {
    E configuration = this.getTypeInstance();
    configuration.setKey(entry.getName());

    if (value.getClass().equals(entry.getType())) {
      configuration.setType(value.getClass().getName());
      configuration.setValue(this.getConversionService().convert(value, String.class));
      this.getRepository().save(configuration);
    } else {
      throw new ConfigurationException("Invalid configuraion type.");
    }
  }

}