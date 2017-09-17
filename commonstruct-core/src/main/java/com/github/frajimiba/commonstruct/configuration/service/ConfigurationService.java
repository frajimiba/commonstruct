package com.github.frajimiba.commonstruct.configuration.service;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.configuration.Configuration;
import com.github.frajimiba.commonstruct.configuration.ConfigurationEntry;
import com.github.frajimiba.commonstruct.domain.service.DomainService;

/**
 * The Configuration Service.
 * 
 * @author Francisco José Jiménez
 *
 * @param <T> The configuration entity.
 * @param <I> Identity type of configuration entity.
 */
public interface ConfigurationService<T extends Configuration<I>, I extends Serializable>
    extends DomainService<T, I> {
  /**
   * Get element of Configuration Entry.
   * @param entry the Configuration Entry.
   * @return The Object of Configuration Entry.
   */
  Object get(ConfigurationEntry entry);
  /**
   * Put object into Configuration Entry.
   * 
   * @param key the Configuration Entry.
   * @param value The Object of Configuration Entry.
   */
  void put(ConfigurationEntry key, Object value);
}
