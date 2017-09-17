package com.github.frajimiba.commonstruct.healthcheck;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.security.auth.User;
import com.github.frajimiba.commonstruct.security.service.UserService;

/**
 * The authetication HealthCheck
 * 
 * @author Francisco José Jiménez
 *
 */
public interface AuthenticationHealthCheck extends HealthCheck {
  /**
   * The service authentication to check.
   * 
   * @return the user authentication service.
   */
  UserService<? extends User<?>, ? extends Serializable> getUserService();
}