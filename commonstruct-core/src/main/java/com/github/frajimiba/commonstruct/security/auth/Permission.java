package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;
import java.util.Collection;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * Permission entity.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */
public interface Permission<I extends Serializable> extends Entity<I> {

  /**
   * Get the domain that is being operated
   *
   * @return the domain that is being operated
   */
  PermissionDomain<?> getDomain();

  /**
   * The actions of this.
   *
   * @return An Collection of actions of this
   */
  Collection<? extends PermissionAction<?>> getActions();

}
