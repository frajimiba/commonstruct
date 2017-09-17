package com.github.frajimiba.commonstruct.domain.service;

import java.io.Serializable;
import java.util.List;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.util.GenericsUtil;

public abstract class BaseDomainService<T extends BaseEntity<ID>, ID extends Serializable> implements DomainService<T, ID> {

	private Class<T> entityType;
	private Class<ID> entityIdType;

	@Override
	public T getTypeInstance() {
		T instance = null;
		String errorMessage = "Generic Instantiation Error";
		try {
			instance = this.entityType.newInstance();
		} catch (InstantiationException ex) {
			throw new RuntimeException(errorMessage, ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(errorMessage, ex);
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public BaseDomainService() {
		List<Class<?>> typeArguments = GenericsUtil.getTypeArguments(BaseDomainService.class, getClass());
		this.entityType = (Class<T>) typeArguments.get(0);
		this.entityIdType = (Class<ID>) typeArguments.get(1);
	}

	protected Class<T> getEntityType() {
		return this.entityType;
	}

	protected Class<ID> getEntityIdType() {
		return this.entityIdType;
	}
}