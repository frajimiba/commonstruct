package com.github.frajimiba.commonstruct.jee5.domain.service;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.domain.service.DomainService;
import com.github.frajimiba.commonstruct.jee5.data.converter.ConverterManager;
import com.github.frajimiba.commonstruct.jee5.data.repository.JeeRepository;


public interface JeeDomainService<T extends BaseEntity<ID>, ID extends Serializable> extends DomainService<T, ID> {
	
	 @Override
	 JeeRepository<T, ID> getRepository();
	 ConverterManager getConverterManager();
}
