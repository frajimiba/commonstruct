package com.github.frajimiba.commonstruct.spring.security.auth;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.springframework.security.core.session.SessionInformation;

import com.github.frajimiba.commonstruct.domain.Entity;

@MappedSuperclass
@Audited
public abstract class SpringSessionInformation extends SessionInformation implements Entity<String> {

  private static final long serialVersionUID = -6587408303225608226L;

  @Id
  @Column(name = "SESSION_ID")
  private String id;

  private String principal;

  private boolean expired;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastRequest;

  public SpringSessionInformation() {
    super(false, UUID.randomUUID().toString(), new Date());
  }

  public SpringSessionInformation(Object principal, String sessionId, Date lastRequest) {
    super(principal, sessionId, lastRequest);
    this.principal = principal.toString();
    this.id = sessionId;
    this.lastRequest = lastRequest;
  }

  @Override
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean isExpired() {
    return expired;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  @Override
  public Date getLastRequest() {
    return lastRequest;
  }

  public void setLastRequest(Date lastRequest) {
    this.lastRequest = lastRequest;
  }

  @Override
  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public void expireNow() {
    this.expired = true;
  }

  @Override
  public String getSessionId() {
    return this.id;
  }

  @Override
  public void refreshLastRequest() {
    this.lastRequest = new Date();
  }

}
