package com.github.frajimiba.commonstruct.security.service;

import java.util.Locale;

import com.github.frajimiba.commonstruct.configuration.ConfigurationEntry;

/**
 * Commons configuration entries for user authentication.
 * 
 * @author Francisco José Jiménez
 *
 */
public enum UserServiceSettings implements ConfigurationEntry {

  /**
   * The credentials expiration time.
   */
  CREDENTIAL_EXPIRATION_TIME(Long.class), 
  /**
   * The max repeated credentials number.
   */
  MAX_REPEATED_CREDENTIALS(Integer.class), 
  /**
   * The login attemps number.
   */
  LOGIN_ATTEMPS(Integer.class), 
  /**
   * The disabled expiration time.
   */
  DISABLED_EXPIRATION_TIME(Long.class), 
  /**
   * The default locale.
   */
  LOCALE(Locale.class);

  /**
   * The entry type.
   */
  private Class<?> clazz;
  /**
  * Create a new configuration entry
  *  
  * @param clazz the type of entry
  */
  private UserServiceSettings(Class<?> clazz) {
    this.clazz = clazz;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return this.name();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getType() {
    return this.clazz;
  }

}
