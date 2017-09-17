package com.github.frajimiba.commonstruct.spring.healthcheck;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.healthcheck.AuthenticationHealthCheck;
import com.github.frajimiba.commonstruct.healthcheck.HealthCheckStatus;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;
import com.github.frajimiba.commonstruct.spring.security.service.SpringUserPasswordService;

public abstract class SpringUserPasswordAuthenticationHealthCheck<T extends SpringUser<ID>, ID extends Serializable>
    implements AuthenticationHealthCheck {

  private static final String HEALTH_CHECK_USER = "HEALTH_CHECK";

  private String tempCredentials = UUID.randomUUID().toString();

  private HealthCheckStatus status;

  public abstract AuthenticationManager getAuthenticationManager();

  @Override
  public abstract SpringUserPasswordService<T, ID> getUserService();

  @Override
  public void check() {
    createOrUpdateHealthCheckUser();
    this.status = HealthCheckStatus.KO;
    try {
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(HEALTH_CHECK_USER,
          tempCredentials);
      this.getAuthenticationManager().authenticate(token);
      this.status = HealthCheckStatus.OK;
    } catch (AuthenticationException ex) {

    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  private void createOrUpdateHealthCheckUser() {
    T heathCheck = this.getUserService().findByPrincipal(HEALTH_CHECK_USER);
    if (heathCheck == null) {
      heathCheck = this.getUserService().getTypeInstance();
      heathCheck.setPrincipal(HEALTH_CHECK_USER);
      heathCheck.setSystem(true);
      heathCheck.setCredentialsTimestamp(32503676400000L);
      this.getUserService().encodePassword(heathCheck, tempCredentials);
      this.getUserService().getRepository().save(heathCheck);
    } else {
      heathCheck.setCredentials(tempCredentials);
      this.getUserService().encodePassword(heathCheck, tempCredentials);
      this.getUserService().getRepository().save(heathCheck);
    }
  }

  @Override
  public HealthCheckStatus getStatus() {
    return this.status;
  }

  public void setStatus(HealthCheckStatus status) {
    this.status = status;
  }

}
