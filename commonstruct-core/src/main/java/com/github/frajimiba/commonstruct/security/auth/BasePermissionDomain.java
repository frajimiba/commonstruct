package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.security.auth.PermissionDomain;

@MappedSuperclass
public abstract class BasePermissionDomain<PK extends Serializable> extends BaseEntity<PK> implements PermissionDomain<PK> {

  private static final long serialVersionUID = -1470059548299238780L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  public BasePermissionDomain() {

  }

  /**
   * Instantiates a new jpa permission domain.
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public BasePermissionDomain(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Sets the name.
   *
   * @param name
   *          the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description.
   *
   * @param description
   *          the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
