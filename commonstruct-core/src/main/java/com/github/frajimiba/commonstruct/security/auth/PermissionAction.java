package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * Permission action.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */         
public interface PermissionAction<I extends Serializable> extends Entity<I> {
  /**
   * Get the action name.
   *
   * @return the action name
   */
  String getName();

  /**
   * Get the action description.
   *
   * @return the action description
   */
  String getDescription();
}
