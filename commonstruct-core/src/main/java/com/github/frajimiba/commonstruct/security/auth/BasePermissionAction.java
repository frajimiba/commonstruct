package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.security.auth.PermissionAction;

@MappedSuperclass
public abstract class BasePermissionAction<PK extends Serializable> extends BaseEntity<PK> implements PermissionAction<PK> {

  private static final long serialVersionUID = 364328318170058623L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  public BasePermissionAction() {
  }

  /**
   * Instantiates a new jpa permission action.
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public BasePermissionAction(String name, String description) {
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
