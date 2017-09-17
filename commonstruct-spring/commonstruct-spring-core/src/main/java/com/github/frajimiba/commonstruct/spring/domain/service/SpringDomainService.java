package com.github.frajimiba.commonstruct.spring.domain.service;

import java.io.Serializable;

import org.springframework.format.Formatter;

import com.github.frajimiba.commonstruct.domain.Entity;
import com.github.frajimiba.commonstruct.domain.service.DomainService;
import com.github.frajimiba.commonstruct.spring.data.SpringRepository;

public interface SpringDomainService<T extends Entity<ID>, ID extends Serializable>
    extends DomainService<T, ID>, Formatter<T> {

  @Override
  SpringRepository<T, ID> getRepository();

  T getTypeInstance();
}
