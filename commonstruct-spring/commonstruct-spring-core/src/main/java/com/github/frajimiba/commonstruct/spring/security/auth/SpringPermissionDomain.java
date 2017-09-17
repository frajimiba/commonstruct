package com.github.frajimiba.commonstruct.spring.security.auth;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

import com.github.frajimiba.commonstruct.security.auth.PermissionDomain;
import com.github.frajimiba.commonstruct.spring.domain.SpringEntity;

@MappedSuperclass
@Audited
public abstract class SpringPermissionDomain<PK extends Serializable> extends SpringEntity<PK>
    implements PermissionDomain<PK> {

  private static final long serialVersionUID = -1470059548299238780L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  public SpringPermissionDomain() {

  }

  /**
   * Instantiates a new jpa permission domain.
   *
   * @param name
   *          the name
   * @param description
   *          the description
   */
  public SpringPermissionDomain(String name, String description) {
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
