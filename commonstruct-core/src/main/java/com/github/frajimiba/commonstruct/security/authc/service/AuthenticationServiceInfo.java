package com.github.frajimiba.commonstruct.security.authc.service;

import java.io.Serializable;

/**
 * Represents a Subject's stored account information relevant to the service of
 * authentication.
 *
 * @author Francisco José Jiménez
 *
 */
public interface AuthenticationServiceInfo extends Serializable {

  /**
   * The principal associated with the corresponding Subject.
   *
   * @return the principal associated with the corresponding Subject
   */
  Object getPrincipal();

  /**
   * The credentials associated with the corresponding Subject.
   *
   * @return the credentials associated with the corresponding Subject
   */
  Object getCredentials();
}