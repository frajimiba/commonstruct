package com.github.frajimiba.commonstruct.security.auth;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.security.auth.Permission;

/**
 * Permissiom for Base entity.
 *
 * @author Francisco José Jiménez
 * @param <PK>
 *          the generic type
 */
@MappedSuperclass
public abstract class BasePermission<PK extends Serializable> extends BaseEntity<PK> implements Permission<PK> {

  /**
   * Serial Version UID.
   */
  private static final long serialVersionUID = 6539372820736537925L;

  @Override
  public abstract BasePermissionDomain<?> getDomain();

  @Override
  public abstract Collection<? extends BasePermissionAction<?>> getActions();

  /**
   * Instantiates a new base permission.
   */
  public BasePermission() {
  }

}
