package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.spring.domain.service.AbstractSpringDomainService;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringSessionInformation;
import com.github.frajimiba.commonstruct.spring.security.data.SpringSessionInformationRepository;

public abstract class SpringSessionInformationService<E extends SpringSessionInformation, PK extends Serializable>
    extends AbstractSpringDomainService<E, String>
    implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {

  @Override
  public abstract SpringSessionInformationRepository<E, String> getRepository();

  public abstract AbstractSpringUserService<?, ?> getUserService();

  @Override
  public List<Object> getAllPrincipals() {
    List<Object> allPrincipals = new ArrayList<Object>();
    List<E> list = (List<E>) getRepository().findAll();
    for (E sessionInformation : list) {
      SpringUserDetail detail = getUserService().loadUserByUsername(sessionInformation.getPrincipal());
      if (detail != null) {
        allPrincipals.add(detail);
      }

    }
    return allPrincipals;
  }

  @Override
  public void onApplicationEvent(SessionDestroyedEvent event) {
    if (event instanceof HttpSessionDestroyedEvent) {
      String sessionId = ((HttpSession) event.getSource()).getId();
      removeSessionInformation(sessionId);
    }
  }

  @Override
  public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
    List<SessionInformation> result = new ArrayList<SessionInformation>();
    if (includeExpiredSessions) {
      List<E> list = (List<E>) getRepository().findByPrincipal(((SpringUserDetail) principal).getPrincipal());
      if (list != null) {
        result.addAll(list);
      }
    } else {
      List<E> list = (List<E>) getRepository().findByPrincipalAndExpired(((SpringUserDetail) principal).getPrincipal(),
          false);
      if (list != null) {
        result.addAll(list);
      }
    }
    return result;
  }

  @Override
  public E getSessionInformation(String sessionId) {
    return getRepository().findOne(sessionId);
  }

  @Override
  @Transactional
  public void refreshLastRequest(String sessionId) {
    E sessionInformation = getSessionInformation(sessionId);
    if (sessionInformation != null) {
      sessionInformation.refreshLastRequest();
      this.getRepository().save(sessionInformation);
    }
  }

  @Override
  @Transactional
  public void registerNewSession(String sessionId, Object principal) {
    E sessionInformation = this.getTypeInstance();
    sessionInformation.setId(sessionId);
    sessionInformation.setPrincipal(((SpringUserDetail) principal).getPrincipal());
    sessionInformation.setLastRequest(new Date());
    this.getRepository().save(sessionInformation);
  }

  @Override
  @Transactional
  public void removeSessionInformation(String sessionId) {
    E sessionInformation = getSessionInformation(sessionId);
    if (sessionInformation != null) {
      this.getRepository().delete(sessionInformation);
    }
  }

}
