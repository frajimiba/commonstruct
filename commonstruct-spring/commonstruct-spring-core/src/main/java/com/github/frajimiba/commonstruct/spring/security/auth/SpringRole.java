package com.github.frajimiba.commonstruct.spring.security.auth;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;

import com.github.frajimiba.commonstruct.security.auth.Role;
import com.github.frajimiba.commonstruct.spring.domain.SpringEntity;

/**
 * Role for JPA entity.
 *
 * @author Francisco José Jiménez
 * @param <PK>
 *          the generic type
 */
@MappedSuperclass
@Audited
public abstract class SpringRole<PK extends Serializable> extends SpringEntity<PK>
    implements Role<PK>, GrantedAuthority {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  @Transient
  private String authority;

  /**
   * Instantiates a new jpa role.
   */
  public SpringRole() {
  }

  /**
   * Instantiates a new jpa role.
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public SpringRole(String name, String description) {
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
  public abstract Collection<? extends SpringPermission<?>> getPermissions();

  @Override
  public String getAuthority() {
    return "ROLE_" + this.getName();
  }

}
