package com.github.frajimiba.commonstruct.security.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.data.Repository;
import com.github.frajimiba.commonstruct.security.auth.User;

/**
 * The user repository definition.
 * 
 * @author Francisco José Jiménez
 *
 * @param <E> The user entity
 * @param <I> The Identity type of user
 */
public interface UserRepository<E extends User<I>, I extends Serializable> extends Repository<E, I> {
  /**
   * find a user by principal
   * 
   * @param principal the principal of user
   * @return the user
   */
  E findByPrincipal(String principal);
}
