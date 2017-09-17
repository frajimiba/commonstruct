package com.github.frajimiba.commonstruct.spring.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.envers.RevisionTimestamp;

import com.github.frajimiba.commonstruct.spring.domain.SpringEntity;

@MappedSuperclass
public abstract class SpringRevisionEntity<PK extends Serializable> extends SpringEntity<PK> {

  private static final long serialVersionUID = 5723395621413828032L;

  /** The timestamp. */
  @RevisionTimestamp
  private long timestamp;

  /** The principal. */
  private String principal;

  /** The action. */
  private String action;

  /**
   * Gets the principal.
   *
   * @return the principal
   */
  public String getPrincipal() {
    return this.principal;
  }

  /**
   * Sets the principal.
   *
   * @param principal
   *          the new principal
   */
  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  /**
   * Gets the action.
   *
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * Sets the action.
   *
   * @param action
   *          the new action
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Gets the revision date.
   *
   * @return the revision date
   */
  @Transient
  public Date getRevisionDate() {
    return new Date(timestamp);
  }

  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the timestamp.
   *
   * @param timestamp
   *          the new timestamp
   */
  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public abstract void addModifiedEntityType(String entityClassName, Serializable id);

}