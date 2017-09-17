package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;
import com.github.frajimiba.commonstruct.spring.domain.service.AbstractSpringDomainService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;
import com.github.frajimiba.commonstruct.spring.security.data.SpringUserRepository;

public abstract class AuthenticationFailureBadCredentialsListener<E extends SpringUser<ID>, ID extends Serializable>
    extends AbstractSpringDomainService<E, ID>
    implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

  @Override
  public abstract SpringUserRepository<E, ID> getRepository();

  @Inject
  private HttpSession httpSession;

  @Inject
  private ConfigurationService<?, ?> configService;

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_LOGIN_ATTEMP, auditable = true)
  public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
    E user = this.getRepository().findByPrincipal(event.getAuthentication().getPrincipal().toString());
    if (user != null) {
      Integer loginAttemps = (Integer) configService.get(UserServiceSettings.LOGIN_ATTEMPS);
      if (loginAttemps != null) {
        user.setAttempts(user.getAttempts() + 1);
        if (user.getAttempts() == loginAttemps) {
          user.setLocked(true);
        }
        this.getRepository().save(user);
      }
    }
    httpSession.setAttribute(SecurityAttributes.AUTHENTICATION_LAST_PRINCIPAL,
        event.getAuthentication().getPrincipal());
  }
}