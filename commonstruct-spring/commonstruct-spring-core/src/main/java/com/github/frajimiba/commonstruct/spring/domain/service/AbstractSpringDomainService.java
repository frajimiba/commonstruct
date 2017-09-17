package com.github.frajimiba.commonstruct.spring.domain.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;

import com.github.frajimiba.commonstruct.domain.Entity;
import com.github.frajimiba.commonstruct.util.GenericsUtil;

public abstract class AbstractSpringDomainService<E extends Entity<ID>, ID extends Serializable>
    implements SpringDomainService<E, ID> {

  @Inject
  private FormattingConversionService conversionService;

  private Class<E> entityType;
  private Class<ID> entityIdType;

  public E getTypeInstance() {
    E user = null;
    String errorMessage = "Generic Instantiation Error";
    try {
      user = this.entityType.newInstance();
    } catch (InstantiationException ex) {
      throw new RuntimeException(errorMessage, ex);
    } catch (IllegalAccessException ex) {
      throw new RuntimeException(errorMessage, ex);
    }
    return user;
  }

  @SuppressWarnings("unchecked")
  public AbstractSpringDomainService() {
    List<Class<?>> typeArguments = GenericsUtil.getTypeArguments(AbstractSpringDomainService.class, getClass());
    this.entityType = (Class<E>) typeArguments.get(0);
    this.entityIdType = (Class<ID>) typeArguments.get(1);
  }

  @PostConstruct
  public void init() {
    if (!conversionService.canConvert(String.class, entityType)) {
      conversionService.addFormatter(this);
    }
  }

  @Override
  public String print(E entity, Locale locale) {
    return entity.toString();
  }

  @Override
  public E parse(String entityString, Locale locale) throws ParseException {
    E result = null;
    String className = entityString.split("\\[")[0];
    if (this.entityType.getName().equals(className)) {
      String id = entityString.split("\\[")[1].split("=")[1].split("\\]")[0];
      ID value = conversionService.convert(id, this.entityIdType);
      result = this.getRepository().findOne(value);
    } else {
      throw new ParseException("Formatter error: The type of class must be '" + this.entityType.getName() + "'", 0);
    }
    return result;
  }

  protected ConversionService getConversionService() {
    return this.conversionService;
  }

  protected Class<E> getEntityType() {
    return this.entityType;
  }

  protected Class<ID> getEntityIdType() {
    return this.entityIdType;
  }

}