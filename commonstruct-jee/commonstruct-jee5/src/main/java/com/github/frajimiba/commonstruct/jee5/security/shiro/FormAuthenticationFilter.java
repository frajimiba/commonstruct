package com.github.frajimiba.commonstruct.jee5.security.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import org.apache.shiro.web.util.WebUtils;

public class FormAuthenticationFilter extends BaseAuthenticationFilter {

	public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";

	public static final String DEFAULT_USERNAME_PARAM = "username";
	public static final String DEFAULT_PASSWORD_PARAM = "password";
	public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";

	private String usernameParam = DEFAULT_USERNAME_PARAM;
	private String passwordParam = DEFAULT_PASSWORD_PARAM;
	private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;

	private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

	public FormAuthenticationFilter() {
		setLoginUrl(DEFAULT_LOGIN_URL);
	}

	@Override
	public void setLoginUrl(String loginUrl) {
		String previous = getLoginUrl();
		if (previous != null) {
			this.appliedPaths.remove(previous);
		}
		super.setLoginUrl(loginUrl);
		this.appliedPaths.put(getLoginUrl(), null);
	}

	public String getUsernameParam() {
		return usernameParam;
	}

	public void setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
	}

	public String getPasswordParam() {
		return passwordParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}

	public String getRememberMeParam() {
		return rememberMeParam;
	}

	public void setRememberMeParam(String rememberMeParam) {
		this.rememberMeParam = rememberMeParam;
	}

	public String getFailureKeyAttribute() {
		return failureKeyAttribute;
	}

	public void setFailureKeyAttribute(String failureKeyAttribute) {
		this.failureKeyAttribute = failureKeyAttribute;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			saveRequestAndRedirectToLogin(request, response);
			return false;
		}
	}

	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
		return (request instanceof HttpServletRequest)
				&& WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		return createToken(username, password, request, response);
	}

	protected boolean isRememberMe(ServletRequest request) {
		return WebUtils.isTrue(request, getRememberMeParam());
	}

	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,ServletResponse response) throws Exception {
		super.onLoginSuccess(token, subject, request, response);
		issueSuccessRedirect(request, response);
		return false;
	}

	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		String className = ae.getClass().getName();
		request.setAttribute(getFailureKeyAttribute(), className);
	}

	protected String getUsername(ServletRequest request) {
		return WebUtils.getCleanParam(request, getUsernameParam());
	}

	protected String getPassword(ServletRequest request) {
		return WebUtils.getCleanParam(request, getPasswordParam());
	}

	@Override
	protected boolean OnAuthenticationError(AuthenticationException exception, ServletRequest request, ServletResponse response) {
		setFailureAttribute(request, exception);
		return true;
	}

}
