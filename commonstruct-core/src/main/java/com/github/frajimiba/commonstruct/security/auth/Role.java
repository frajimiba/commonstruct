package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;
import java.util.Collection;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * Role entity.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */
public interface Role<I extends Serializable> extends Entity<I> {

  /**
   * Get the role name.
   *
   * @return the role name
   */
  String getName();

  /**
   * Get the role description.
   *
   * @return the role description
   */
  String getDescription();

  /**
   * The permissions of this rol.
   *
   * @return the permissions collection
   */
  Collection<? extends Permission<?>> getPermissions();
}
