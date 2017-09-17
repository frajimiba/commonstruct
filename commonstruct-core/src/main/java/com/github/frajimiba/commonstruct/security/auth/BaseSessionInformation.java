package com.github.frajimiba.commonstruct.security.auth;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.frajimiba.commonstruct.domain.BaseEntity;

@MappedSuperclass
public abstract class BaseSessionInformation extends BaseEntity<String> {

  private static final long serialVersionUID = -6587408303225608226L;

  @Id
  @Column(name = "SESSION_ID")
  private String id;

  private String principal;
  private boolean expired;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastRequest;

  public BaseSessionInformation() {
	  this.principal = null;
	  this.id = UUID.randomUUID().toString();
	  this.lastRequest =  new Date();
  }

  public BaseSessionInformation(String principal, String sessionId, Date lastRequest) {
      this.principal = principal;
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

  public boolean isExpired() {
    return expired;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  public Date getLastRequest() {
    return lastRequest;
  }

  public void setLastRequest(Date lastRequest) {
    this.lastRequest = lastRequest;
  }

  public String getPrincipal() {
    return principal;
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }

  public void expireNow() {
    this.expired = true;
  }

  public String getSessionId() {
    return this.id;
  }

  public void refreshLastRequest() {
    this.lastRequest = new Date();
  }

}
