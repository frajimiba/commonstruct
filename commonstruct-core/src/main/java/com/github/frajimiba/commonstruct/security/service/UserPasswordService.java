package com.github.frajimiba.commonstruct.security.service;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.security.auth.User;

/**
 * The user password service definition.
 * 
 * @author Francisco José Jiménez
 *
 * @param <E> The user entity
 * @param <I> The Identity type of user
 */
public interface UserPasswordService<T extends User<I>, I extends Serializable> extends UserService<T, I> {
  /**
   * Encode the password.
   * 
   * @param user the user
   * @param password the password
   */
  void encodePassword(T user, String password);
  /**
   * Change the password.
   * 
   * @param user the user
   * @param password the password
   */  
  void changePassword(T user, String password);
}