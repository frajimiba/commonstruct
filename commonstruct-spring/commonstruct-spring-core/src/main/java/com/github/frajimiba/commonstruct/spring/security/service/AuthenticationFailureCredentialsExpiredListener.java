package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;

import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;
import com.github.frajimiba.commonstruct.spring.domain.service.AbstractSpringDomainService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;
import com.github.frajimiba.commonstruct.spring.security.data.SpringUserRepository;

public abstract class AuthenticationFailureCredentialsExpiredListener<E extends SpringUser<ID>, ID extends Serializable>
    extends AbstractSpringDomainService<E, ID>
    implements ApplicationListener<AuthenticationFailureCredentialsExpiredEvent> {

  @Override
  public abstract SpringUserRepository<E, ID> getRepository();

  @Inject
  private HttpSession httpSession;

  @Override
  public void onApplicationEvent(AuthenticationFailureCredentialsExpiredEvent event) {
    httpSession.setAttribute(SecurityAttributes.AUTHENTICATION_LAST_PRINCIPAL,
        event.getAuthentication().getPrincipal());
  }

}