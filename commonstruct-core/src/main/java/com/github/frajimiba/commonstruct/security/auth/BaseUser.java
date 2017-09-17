package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.security.auth.User;

@MappedSuperclass
public abstract class BaseUser<PK extends Serializable> extends BaseEntity<PK> implements User<PK> {

  private static final long serialVersionUID = -1947689224890967189L;

  /**
   * The identity of the principal being authenticated.
   */
  @Column(unique = true, nullable = false)
  private String principal;

  /**
   * The credentials that prove the principal is correct.
   */
  private String credentials;
  /**
   * The salt used to salt the account's credentials.
   */
  private String salt;
  /**
   * Indicates whether the account is locked or not.
   */
  private boolean locked;

  /** The locked timestamp. */
  private long disabledTimestamp;

  /** The credentials timestamp. */
  private long credentialsTimestamp;

  /** The attempts. */
  private int attempts;

  /** The ip. */
  private String ip;
  /**
   * Indicates whether the account is acceptedCond or not.
   */
  private boolean acceptedConditions;

  /**
   * Indicates whether the user is the system.
   */
  @Column(nullable = true)
  private boolean system;

  /**
   * Indicates whether the account is disable or not.
   */
  private boolean disabled;

  /** The access timestamp. */
  private long accessTimestamp;
  
  /** The last access date. */
  private Date lastAccess;

  /**
   * Default constructor.
   */
  public BaseUser() {
  }

  /**
   * The constructor that initializes the fields principal and credentials.
   *
   * @param principal
   *          the identity of the principal being authenticated
   * @param credentials
   *          the credentials that prove the principal is correct
   */
  public BaseUser(String principal, String credentials) {
    this.principal = principal;
    this.credentials = credentials;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPrincipal() {
    return principal;
  }

  /**
   * Set the identity of the principal being authenticated.
   *
   * @param principal
   *          the Principal being authenticated in string format
   */
  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCredentials() {
    return credentials;
  }

  /**
   * Set the credentials that prove the principal is correct.
   *
   * @param credentials
   *          the credentials that prove the identity of the Principal in
   *          string(B64) format
   */
  public void setCredentials(String credentials) {
    this.credentials = credentials;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCredentialsSalt() {
    return this.salt;
  }

  /**
   * Set the salt used to salt the account's credentials.
   *
   * @param salt
   *          the salt used to salt the account's credentials in string(B64)
   *          format
   */
  public void setCredentialsSalt(String salt) {
    this.salt = salt;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLocked() {
    return locked;
  }

  /**
   * Lock or unlock the user.
   *
   * @param locked
   *          lock or unlock the user
   */
  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getDisabledTimestamp() {
    return disabledTimestamp;
  }

  public void setDisabledTimestamp(long disabledTimestamp) {
    this.disabledTimestamp = disabledTimestamp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public boolean isDisabledExpired(long expirationTime) {
    long now = new Date().getTime();
    long usingTime = this.disabledTimestamp + expirationTime;
    return (usingTime < now);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  public boolean isCredentialsExpired(long expirationTime) {
    long now = new Date().getTime();
    long usingTime = this.credentialsTimestamp + expirationTime;
    return (usingTime < now);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Long getCredentialsTimestamp() {
    return this.credentialsTimestamp;
  }

  /**
   * Sets the credentials time stamp.
   *
   * @param timestamp
   *          the new credentials time stamp
   */
  public void setCredentialsTimestamp(long timestamp) {
    this.credentialsTimestamp = timestamp;
  }

  /**
   * {@inheritDoc}
   */
  public Integer getAttempts() {
    return attempts;
  }

  /**
   * Sets the attempts.
   *
   * @param attempts
   *          the new attempts
   */
  public void setAttempts(int attempts) {
    this.attempts = attempts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAcceptedConditions() {
    return acceptedConditions;
  }

  /**
   * Sets the accepted conditions.
   *
   * @param acceptedConditions
   *          the new accepted conditions
   */
  public void setAcceptedConditions(boolean acceptedConditions) {
    this.acceptedConditions = acceptedConditions;
  }

  /**
   * Gets the ip.
   *
   * @return the ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * Sets the ip.
   *
   * @param ip
   *          the new ip
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSystem() {
    return system;
  }

  /**
   * Sets the system user.
   *
   * @param systemUser
   *          the new system user
   */
  public void setSystem(boolean system) {
    this.system = system;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDisabled() {
    return disabled;
  }

  /**
   * Sets the disabled.
   *
   * @param disabled
   *          the new disabled
   */
  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  @Override
  public abstract Collection<? extends BaseRole<?>> getRoles();

  public Long getAccessTimestamp() {
    return accessTimestamp;
  }

  public void setAccessTimestamp(long accessTimestamp) {
    this.accessTimestamp = accessTimestamp;
  }
  
  public Date getLastAccess(){
	  return lastAccess;
  }
  
  public void setLastAccess(Date lastAccess) {
	    this.lastAccess = lastAccess;
  }

}