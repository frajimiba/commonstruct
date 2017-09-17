package com.github.frajimiba.commonstruct.spring.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.action.ActionAttributes;
import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;
import com.github.frajimiba.commonstruct.spring.security.service.SpringUserDetail;

public final class SpringUtils {

  private SpringUtils() {
  }

  public static String getLastPrincipal() {

    String principal = SecurityAttributes.SYSTEM_USER_PRINCIPAL;

    if (getHttpSession() != null) {
      principal = (String) getHttpSession().getAttribute(SecurityAttributes.AUTHENTICATION_LAST_PRINCIPAL);
    }

    if (SecurityContextHolder.getContext() != null) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
          SpringUserDetail user = (SpringUserDetail) authentication.getPrincipal();
          principal = user.getPrincipal();
        }

      }
    }

    return principal;
  }

  public static Action getLastAction() {
    Action action = null;
    if (getHttpSession() != null) {
      action = (Action) getHttpSession().getAttribute(ActionAttributes.ACTION_ATTRIBUTE);
    }
    return action;
  }

  public static void removeLastAction() {
    if (getHttpSession() != null) {
      getHttpSession().removeAttribute(ActionAttributes.ACTION_ATTRIBUTE);
    }
  }

  public static HttpSession getHttpSession() {
    HttpSession session = null;

    if (RequestContextHolder.getRequestAttributes() != null) {
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      session = attr.getRequest().getSession(false);
    }

    return session;

  }

}
