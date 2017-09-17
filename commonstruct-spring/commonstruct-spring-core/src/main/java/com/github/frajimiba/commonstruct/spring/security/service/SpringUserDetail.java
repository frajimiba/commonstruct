package com.github.frajimiba.commonstruct.spring.security.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SpringUserDetail extends User {

  private static final long serialVersionUID = 8767224293064105892L;

  public Date lastAccess;
  public String principal;
  public String credentials;
  public Object credentialsSalt;

  public SpringUserDetail(String principal, String credentials, Object credentialsSalt, boolean enabled,
      boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities, Date lastAccess) {
    super(principal, credentials, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.principal = principal;
    this.credentials = credentials;
    this.credentialsSalt = credentialsSalt;
    this.lastAccess = lastAccess;
  }

  public Date getLastAccess() {
    return this.lastAccess;
  }

  public String getPrincipal() {
    return this.principal;
  }

  public String getCredentials() {
    return this.credentials;
  }

  public Object getCredentialsSalt() {
    return this.credentialsSalt;
  }

}