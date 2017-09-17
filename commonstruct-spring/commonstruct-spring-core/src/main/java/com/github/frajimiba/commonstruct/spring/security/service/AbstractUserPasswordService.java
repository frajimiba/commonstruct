package com.github.frajimiba.commonstruct.spring.security.service;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.security.auth.User;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;
import com.github.frajimiba.commonstruct.spring.security.auth.SpringUser;

public abstract class AbstractUserPasswordService<E extends SpringUser<P>, P extends Serializable>
    extends AbstractSpringUserService<E, P> implements SpringUserPasswordService<E, P> {

  @Override
  public void encodePassword(E user, String password) {
    String salt = KeyGenerators.string().generateKey();
    user.setCredentialsSalt(salt);
    String passwordEncoding = this.getPasswordEncoder().encodePassword(password, salt);
    user.setCredentials(passwordEncoding);
  }

  @Override
  @Transactional
  @Action(name = SecurityActionNames.USER_CHANGED_PASSWORD, auditable = true)
  public void changePassword(E user, String password) {
    encodePassword(user, password);
    user.setCredentialsTimestamp(new Date().getTime());
    this.getRepository().save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isRepeatedPassword(E user, String newPassword) {

    boolean result = false;

    Integer maxNum = (Integer) this.getConfigurationService().get(UserServiceSettings.MAX_REPEATED_CREDENTIALS);

    if (maxNum != null) {
      AuditQuery query = this.getRepository().getAuditQuery(user, false, true);
      query.add(AuditEntity.revisionProperty("action").eq(SecurityActionNames.USER_CHANGED_PASSWORD));

      query.add(AuditEntity.property("principal").eq(user.getPrincipal()));
      query.addOrder(AuditEntity.property("credentialsTimestamp").desc());
      query.setMaxResults(maxNum);
      List<?> userRevisions = query.getResultList();

      for (int i = 0; i < userRevisions.size() && !result; i++) {
        Object[] revision = (Object[]) userRevisions.get(i);
        User<?> userRevision = (User<?>) revision[0];
        result = this.getPasswordEncoder().isPasswordValid(userRevision.getCredentials(), newPassword,
            userRevision.getCredentialsSalt());
      }
    }

    return result;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public Authentication refreshAuthenticated(Authentication auth, E user) {
    UserDetails userDetails = this.loadUserByUsername(user.getPrincipal());
    Authentication authentication = createToken(auth, userDetails, userDetails.getPassword(),
        userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }

  private Authentication createToken(Authentication auth, Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    Authentication result = null;
    Class<?> clazz = auth.getClass();
    Constructor<?> constructor;
    try {
      constructor = clazz.getConstructor(Object.class, Object.class, Collection.class);
      result = (Authentication) constructor.newInstance(principal, credentials, authorities);
    } catch (SecurityException e) {
    } catch (NoSuchMethodException e) {
    } catch (IllegalArgumentException e) {
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    } catch (InvocationTargetException e) {
    }

    if (result == null) {
      result = new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
    }

    return result;

  }

}