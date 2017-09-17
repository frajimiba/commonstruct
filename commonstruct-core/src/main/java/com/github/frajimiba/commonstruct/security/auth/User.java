package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * User entity.
 *
 * @author Francisco José Jiménez
 *
 * @param <I>
 *          Identity type of Entity
 */
public interface User<I extends Serializable> extends Entity<I> {
  /**
   * The identity of the principal being authenticated.
   *
   * @return the Principal being authenticated in string format
   */
  String getPrincipal();

  /**
   * The credentials that prove the principal is correct.
   *
   * @return the credentials that prove the identity of the Principal in
   *         string(B64) format
   */
  String getCredentials();

  /**
   * The salt used to salt the user's credentials.
   *
   * @return the salt used to salt the user's credentials in string(B64) format
   */
  String getCredentialsSalt();

  /**
   * Indicates whether the is expired or not.
   * 
   * @param expirationTime
   *          The expiration time
   * @return <code>true</code> if the is expired
   */
  boolean isCredentialsExpired(long expirationTime);

  /**
   * The last modification credentials timeStamp.
   * 
   * @return timeStamp
   */
  Long getCredentialsTimestamp();

  /**
   * Login attempts.
   *
   * @return The number of login attempts
   */
  Integer getAttempts();

  /**
   * Indicates whether the user is locked or not.
   *
   * @return <code>true</code> if the is locked
   */
  boolean isLocked();

  /**
   * The last modification locked timeStamp.
   * 
   * @return timeStamp
   */
  Long getDisabledTimestamp();

  /**
   * Indicates whether the is expired or not.
   * 
   * @param expirationTime
   *          The expiration time
   * @return <code>true</code> if the is expired
   */
  boolean isDisabledExpired(long expirationTime);

  /**
   * Indicates whether the has accepted conditions or not.
   *
   * @return <code>true</code> if the has accepted the conditions
   */
  boolean isAcceptedConditions();

  /**
   * Indicates whether the is the system.
   *
   * @return <code>true</code> if the is the system
   */
  boolean isSystem();

  /**
   * The roles of this .
   *
   * @return An List of roles of this
   */
  Collection<? extends Role<?>> getRoles();

  /**
   * The profile of this .
   *
   * @return An Profile object
   */
  UserProfile getProfile();

  /**
   * Indicates whether the is disabled or not.
   *
   * @return <code>true</code> if the is disabled
   */
  boolean isDisabled();

  /**
   * The last access timeStamp.
   * 
   * @return timeStamp
   */
  Long getAccessTimestamp();
  
  /**
   * The last access date.
   * 
   * @return date
   */
  Date getLastAccess();

}
