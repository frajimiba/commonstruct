package com.github.frajimiba.commonstruct.domain.service;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.data.Repository;
import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * The Interface DomainService.
 *
 * @author Francisco José Jiménez
 * 
 * @param <T>
 *          the generic type
 * @param <I>
 *          Identity type of entity
 */
public interface DomainService<T extends Entity<I>, I extends Serializable> {

  /**
   * Gets the repository.
   *
   * @return the repository
   */
  Repository<T, I> getRepository();
  T getTypeInstance();

}