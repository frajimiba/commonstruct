package com.github.frajimiba.commonstruct.security.service;

import java.io.Serializable;
import java.util.Date;

import com.github.frajimiba.commonstruct.domain.service.DomainService;
import com.github.frajimiba.commonstruct.security.auth.User;

/**
 * An user service.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Service user type
 * @param <I> 
 *          The Identity type of user   
 */
public interface UserService<T extends User<I>, I extends Serializable> extends DomainService<T,I> {
  /**
   * Get the user <code>T</code> identified by <code>principal</code>.
   *
   * @param principal
   *          the principal of the user to get from the storage
   * @return the user with this principal
   */
  T findByPrincipal(String principal);
  
  Date getLastAccess(T user);

  void disable(T user);

  void enable(T user);

  void unlock(T user);

  void acceptConditions(T user);

  boolean isDisabledExpired(T user);

  void remove(T user);

}