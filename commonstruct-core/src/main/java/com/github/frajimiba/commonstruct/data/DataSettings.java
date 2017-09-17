package com.github.frajimiba.commonstruct.data;

import com.github.frajimiba.commonstruct.configuration.ConfigurationEntry;

/**
 * Commons configuration entries for data access
 * 
 * @author Francisco José Jiménez
 *
 */
public enum DataSettings implements ConfigurationEntry {

  /**
   * Page size.
   */
  PAGE_SIZE(Integer.class);

  /**
   * The type of entry.
   */
  private Class<?> clazz;

  /**
   * Create a new configuration entry
   *  
   * @param clazz the type of entry
   */
  private DataSettings(Class<?> clazz) {
    this.clazz = clazz;
  }

  @Override
  public String getName() {
    return this.name();
  }

  @Override
  public Class<?> getType() {
    return this.clazz;
  }

}
