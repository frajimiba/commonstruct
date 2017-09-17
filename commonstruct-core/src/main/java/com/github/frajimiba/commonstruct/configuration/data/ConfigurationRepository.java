package com.github.frajimiba.commonstruct.configuration.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.configuration.Configuration;
import com.github.frajimiba.commonstruct.data.Repository;

/**
 * The Configuration Repository
 * 
 * @author Francisco José Jiménez
 *
 * @param <E> The configuration entity
 * @param <I> Identity type of configuration entity
 */
public interface ConfigurationRepository<E extends Configuration<I>, I extends Serializable>
    extends Repository<E, I> {

  /**
   * Find a configuration entity by key. 
   * 
   * @param key The configuration key.
   * @return The configuration entity.
   */
  E findByKey(String key);

}