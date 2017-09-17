package com.github.frajimiba.commonstruct.spring.security.auth;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;

import com.github.frajimiba.commonstruct.security.auth.Permission;
import com.github.frajimiba.commonstruct.spring.domain.SpringEntity;

/**
 * Permissiom for JPA entity.
 *
 * @author Francisco José Jiménez
 * @param <PK>
 *          the generic type
 */
@MappedSuperclass
@Audited
public abstract class SpringPermission<PK extends Serializable> extends SpringEntity<PK>
    implements Permission<PK>, GrantedAuthority {

  /**
   * Serial Version UID.
   */
  private static final long serialVersionUID = 6539372820736537925L;

  @Transient
  private String authority;

  @Override
  public abstract SpringPermissionDomain<?> getDomain();

  @Override
  public abstract Collection<? extends SpringPermissionAction<?>> getActions();

  /**
   * Instantiates a new jpa permission.
   */
  public SpringPermission() {
  }

  @Override
  public String getAuthority() {
    String result = "PERMISSION_";
    result = result + this.getDomain().getName() + ":";
    for (SpringPermissionAction<?> action : this.getActions()) {
      result = result + action.getName() + ",";
    }
    return result;
  }

}
