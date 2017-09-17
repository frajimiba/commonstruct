package com.github.frajimiba.commonstruct.configuration;

/**
 * The configuration entry.
 *  
 * @author Francisco José Jiménez
 */
public interface ConfigurationEntry {
  /**
   * @return the configuration entry name.
   */
  String getName();
  /**
   * @return the configuration entry type.
   */
  Class<?> getType();
}
