package com.github.frajimiba.commonstruct.security.authc.service;

/**
 * The authentication service.
 * 
 * @author Francisco José Jiménez
 *
 * @param <T>
 *        The type of response to validate.
 */
public interface AuthenticationService<T> {
  /**
   * Validate the response.
   * 
   * @param response the response to validate.
   * @return the result of validation.
   */
  AuthenticationServiceInfo validate(T response);
}