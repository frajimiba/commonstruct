package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;
import com.github.frajimiba.commonstruct.spring.domain.service.AbstractSpringDomainService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;
import com.github.frajimiba.commonstruct.spring.security.data.SpringUserRepository;

public abstract class AuthenticationSuccessListener<E extends SpringUser<ID>, ID extends Serializable>
    extends AbstractSpringDomainService<E, ID> implements ApplicationListener<AuthenticationSuccessEvent> {

  @Override
  public abstract SpringUserRepository<E, ID> getRepository();

  @Inject
  private ConfigurationService<?, ?> configService;

  @Inject
  private HttpSession httpSession;

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_LOGIN, auditable = true)
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    SpringUserDetail userDetail = (SpringUserDetail) event.getAuthentication().getPrincipal();
    E user = this.getRepository().findByPrincipal(userDetail.getPrincipal());

    if (user != null) {

      httpSession.setAttribute(SecurityAttributes.AUTHENTICATION_LAST_PRINCIPAL, user.getPrincipal());

      if (configService.get(UserServiceSettings.LOGIN_ATTEMPS) != null) {
        user.setAttempts(0);
      }
      user.setLastAccessTimestamp(new Date().getTime());
      this.getRepository().save(user);
    }
  }
}