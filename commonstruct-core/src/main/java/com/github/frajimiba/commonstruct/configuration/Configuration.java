package com.github.frajimiba.commonstruct.configuration;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * Configuration entity.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */
public interface Configuration<I extends Serializable> extends Entity<I>, Serializable {

  /**
   * Get the configuration key.
   *
   * @return configuration key
   */
  String getKey();

  /**
   * Get the configuration value.
   *
   * @return the configuration value
   */
  String getValue();

  /**
   * Get the configuration type.
   *
   * @return the configuration type
   */
  String getType();
}