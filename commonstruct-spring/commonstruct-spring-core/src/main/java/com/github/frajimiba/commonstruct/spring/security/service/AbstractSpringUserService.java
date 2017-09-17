package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;
import com.github.frajimiba.commonstruct.spring.audit.SpringRevisionEntity;
import com.github.frajimiba.commonstruct.spring.domain.service.AbstractSpringDomainService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringRole;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;

public abstract class AbstractSpringUserService<E extends SpringUser<ID>, ID extends Serializable>
    extends AbstractSpringDomainService<E, ID> implements SpringUserService<E, ID> {

  @Inject
  private ConfigurationService<?, ?> configService;

  public ConfigurationService<?, ?> getConfigurationService() {
    return this.configService;
  }

  @Override
  public E findByPrincipal(String principal) {
    return this.getRepository().findByPrincipal(principal);
  }

  @Override
  @Transactional
  public SpringUserDetail loadUserByUsername(String principal) throws UsernameNotFoundException {

    E springUser = this.getRepository().findByPrincipal(principal);

    if (springUser == null) {
      throw new UsernameNotFoundException(principal);
    }

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    Collection<? extends SpringRole<?>> jpaRoles = springUser.getRoles();
    if (jpaRoles != null) {
      authorities.addAll(jpaRoles);
      for (SpringRole<?> role : jpaRoles) {
        authorities.addAll(role.getPermissions());
      }
    }

    if (!springUser.isAcceptedConditions()) {
      authorities.add(new SimpleGrantedAuthority(SecurityAttributes.ACCEPT_TERMS_AND_CONDITIONS));
    }

    boolean credentialsNonExpired = true;

    Long credentialExpirationTime = (Long) this.getConfigurationService()
        .get(UserServiceSettings.CREDENTIAL_EXPIRATION_TIME);

    if (credentialExpirationTime != null) {
      credentialsNonExpired = !springUser.isCredentialsExpired(credentialExpirationTime);
    }

    SpringUserDetail userDetails = new SpringUserDetail(springUser.getPrincipal(), springUser.getCredentials(),
        springUser.getCredentialsSalt(), !springUser.isDisabled(), true, credentialsNonExpired, !springUser.isLocked(),
        authorities, getLastAccess(springUser));

    return userDetails;
  }

  @Override
  public Date getLastAccess(E user) {

    Date result = null;

    AuditQuery query = this.getRepository().getAuditQuery(user, false, true);
    query.add(AuditEntity.revisionProperty("action").eq(SecurityActionNames.USER_LOGIN));
    query.add(AuditEntity.property("principal").eq(user.getPrincipal()));
    query.addOrder(AuditEntity.revisionNumber().desc());
    query.setMaxResults(1);

    try {
      Object[] revision = (Object[]) query.getSingleResult();

      if (revision != null) {
        SpringRevisionEntity<?> revisionEntity = (SpringRevisionEntity<?>) revision[1];
        result = revisionEntity.getRevisionDate();
      }

    } catch (NoResultException ex) {
      result = null;
    }

    return result;
  }

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_DISABLED, auditable = true)
  public void disable(E user) {
    if (!user.isDisabled()) {
      user.setDisabled(true);
      user.setDisabledTimestamp(new Date().getTime());
      this.getRepository().save(user);
    }
  }

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_ENABLED, auditable = true)
  public void enable(E user) {
    if (user.isDisabled()) {
      user.setDisabled(false);
      this.getRepository().save(user);
    }
  }

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_UNLOCKED, auditable = true)
  public void unlock(E user) {
    if (user.isLocked()) {
      user.setAttempts(0);
      user.setLocked(false);
      this.getRepository().save(user);
    }
  }

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_ACEPTED_CONDITIONS, auditable = true)
  public void acceptConditions(E user) {
    if (!user.isAcceptedConditions()) {
      user.setAcceptedConditions(true);
      this.getRepository().save(user);
    }
  }

  public boolean isDisabledExpired(E user) {
    boolean result = false;
    Long disabledExpirationTime = (Long) this.getConfigurationService()
        .get(UserServiceSettings.DISABLED_EXPIRATION_TIME);
    if (disabledExpirationTime != null) {
      result = user.isDisabledExpired(disabledExpirationTime);
    }
    return result;
  }

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_REMOVED, auditable = true)
  public void remove(E user) {
    this.getRepository().delete(user);
  }

}