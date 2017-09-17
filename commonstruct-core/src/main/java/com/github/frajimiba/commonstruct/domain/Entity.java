package com.github.frajimiba.commonstruct.domain;

import java.io.Serializable;

/**
 * An Entity.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */
public interface Entity<I extends Serializable> extends Serializable {

  /**
   * @return The identity of this entity
   */
  I getId();
}