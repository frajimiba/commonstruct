package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * Permission domain.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */         
public interface PermissionDomain<I extends Serializable> extends Entity<I> {
  /**
   * Get the domain name.
   *
   * @return the domain name
   */
  String getName();

  /**
   * Get the domain description.
   *
   * @return the domain description
   */
  String getDescription();
}
