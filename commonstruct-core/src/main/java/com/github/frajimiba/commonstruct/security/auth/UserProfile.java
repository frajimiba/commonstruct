package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;

/**
 * The User profile.
 *
 * @author Francisco José Jiménez
 *
 */
public interface UserProfile extends Serializable {

  /**
   * Gets the locale.
   *
   * @return the locale
   */
  String getLocale();

  /**
   * Gets the theme.
   *
   * @return the theme
   */
  String getTheme();
}
