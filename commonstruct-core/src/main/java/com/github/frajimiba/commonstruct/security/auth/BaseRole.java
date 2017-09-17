package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.security.auth.Role;

/**
 * Role for JPA entity.
 *
 * @author Francisco José Jiménez
 * @param <PK>
 *          the generic type
 */
@MappedSuperclass
public abstract class BaseRole<PK extends Serializable> extends BaseEntity<PK>
    implements Role<PK> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /**
   * Instantiates a new jpa role.
   */
  public BaseRole() {
  }

  /**
   * Instantiates a new jpa role.
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public BaseRole(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
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

  @Override
  public abstract Collection<? extends BasePermission<?>> getPermissions();


}
