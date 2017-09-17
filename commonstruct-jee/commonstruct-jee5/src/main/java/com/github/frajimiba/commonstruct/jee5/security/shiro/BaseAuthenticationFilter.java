package com.github.frajimiba.commonstruct.jee5.security.shiro;

import javax.ejb.EJBException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import com.github.frajimiba.commonstruct.jee5.security.service.JeeSessionInformationService;
import com.github.frajimiba.commonstruct.jee5.security.service.ShiroUserService;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.service.SecurityAttributes;

public abstract class BaseAuthenticationFilter extends AuthenticatingFilter {

	private ShiroUserService<BaseUser<?>, ?> userService;
	private JeeSessionInformationService<?, ?> sessionInformationService;

	private String noacceptedconditionsUrl;

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			if (subject.isAuthenticated()) {
				sessionInformationService.refreshLastRequest(subject.getSession().getId().toString());
			}
		}
		return super.preHandle(request, response);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		BaseUser<?> user = (BaseUser<?>) subject.getPrincipal();
		this.getUserService().onLogin(user);
		sessionInformationService.registerNewSession(subject.getSession().getId().toString(), user.getPrincipal());
		return true;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException exception, ServletRequest request, ServletResponse response) {
		boolean result = false;

		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute(SecurityAttributes.AUTHENTICATION_LAST_PRINCIPAL, token.getPrincipal());

		try {
			if (exception instanceof UnknownAccountException) {
				result = OnAuthenticationError(exception, request, response);
			} else if (exception instanceof LockedAccountException) {
				result = OnAuthenticationError(exception, request, response);
			} else if (exception instanceof DisabledAccountException) {
				result = OnAuthenticationError(exception, request, response);
			} else if (exception instanceof ConcurrentAccessException) {
				result = OnAuthenticationError(exception, request, response);
			} else if (exception instanceof NotAcceptedConditionsException) {
				WebUtils.issueRedirect(request, response, noacceptedconditionsUrl);
			} else {
				result = OnAuthenticationError(exception, request, response);
			}
		} catch (Exception ex) {
			result = OnAuthenticationError(exception, request, response);
		}
		return result;
	}
	
	protected abstract boolean OnAuthenticationError(AuthenticationException exception, ServletRequest request, ServletResponse response);

	public Exception getRootCause(EJBException exception) {
		if (null == exception) {
			return null;
		}
		EJBException effect = exception;
		Exception cause = effect.getCausedByException();
		while (null != cause && cause instanceof EJBException) {
			effect = (EJBException) cause;
			cause = effect.getCausedByException();
		}
		return null == cause ? effect : cause;
	}

	public String getNoacceptedconditionsUrl() {
		return noacceptedconditionsUrl;
	}

	public void setNoacceptedconditionsUrl(String noacceptedconditionsUrl) {
		this.noacceptedconditionsUrl = noacceptedconditionsUrl;
	}

	public JeeSessionInformationService<?, ?> getSessionInformationService() {
		return sessionInformationService;
	}

	public void setSessionInformationService(JeeSessionInformationService<?, ?> sessionInformationService) {
		this.sessionInformationService = sessionInformationService;
	}

	public ShiroUserService<BaseUser<?>, ?> getUserService() {
		return userService;
	}

	public void setUserService(ShiroUserService<BaseUser<?>, ?> userService) {
		this.userService = userService;
	}

}
