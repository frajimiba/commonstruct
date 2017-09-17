package com.github.frajimiba.commonstruct.jee5.security.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

import com.github.frajimiba.commonstruct.configuration.BaseConfiguration;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.jee5.security.data.JeeCredentialsMatcher;
import com.github.frajimiba.commonstruct.jee5.security.service.JeeSessionInformationService;
import com.github.frajimiba.commonstruct.jee5.security.service.ShiroUserService;
import com.github.frajimiba.commonstruct.security.auth.BasePermission;
import com.github.frajimiba.commonstruct.security.auth.BasePermissionAction;
import com.github.frajimiba.commonstruct.security.auth.BaseRole;
import com.github.frajimiba.commonstruct.security.auth.BaseSessionInformation;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;

public class JpaRealm extends AuthorizingRealm  {

	private boolean concurrentAccess = false;
	private boolean expiredCredentials = false;

	private ConfigurationService<BaseConfiguration<?>, ?> configurationService;
	private ShiroUserService<BaseUser<?>,?>  userService;
	private JeeSessionInformationService<?, ?> sessionInformationService;

	@Override
	public String getName(){
		return "JpaRealm";
	}
	
	@Override
	public boolean supports(AuthenticationToken token) {
		boolean result = false;
		if (token != null) {
			result = (token instanceof HostAuthenticationToken);
		}
		return result;
	}

	/**
	 * Check user info.
	 *
	 * @param user
	 *            the user
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	protected void checkUserInfo(final BaseUser<?> user) throws AuthenticationException {
		if (user.isLocked()) {
			String message = "Account [" + user.getPrincipal() + "] is locked.";
			throw new LockedAccountException(message);
		} else if (user.isDisabled()) {
			String message = "Account [" + user.getPrincipal() + "] is disabled.";
			throw new DisabledAccountException(message);
		}
	}

	/**
	 * Check concurrent access.
	 *
	 * @param user
	 *            the user
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	protected void checkConcurrentAccess(final BaseUser<?> user) throws AuthenticationException {
		List<BaseSessionInformation> sessions = this.getSessionInformationService().getAllSessions(user.getPrincipal(), true);
		if (sessions != null && sessions.size() > 0) {
			String message = "No concurrent access for account [" + user.getPrincipal() + "]";
			throw new ConcurrentAccessException(message);
		}

	}

	/**
	 * Check credentials expired.
	 *
	 * @param token
	 *            the token
	 * @param user
	 *            the user
	 * @param info
	 *            the info
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	protected void checkCredentialsExpired(final AuthenticationToken token, final BaseUser<?> user,
			final AuthenticationInfo info) throws AuthenticationException {

		long credentialExpirationTime = (Long) this.getConfigurationService()
				.get(UserServiceSettings.CREDENTIAL_EXPIRATION_TIME);

		if (this.getCredentialsMatcher().doCredentialsMatch(token, info)
				&& user.isCredentialsExpired(credentialExpirationTime)) {
			String message = "The credentials for account [" + user.getPrincipal() + "] have expired.";
			throw new ExpiredCredentialsException(message);
		}

	}

	/**
	 * Check credentials expired.
	 *
	 * @param token
	 *            the token
	 * @param user
	 *            the user
	 * @param info
	 *            the info
	 * @throws AuthenticationException
	 *             the authentication exception
	 */
	protected void checkAcceptedConditions(final AuthenticationToken token, final BaseUser<?> user,
			final AuthenticationInfo info) throws AuthenticationException {

		if (this.getCredentialsMatcher().doCredentialsMatch(token, info) && !user.isAcceptedConditions()) {
			String message = "The account [" + user.getPrincipal() + "] not accepted conditions.";
			throw new NotAcceptedConditionsException(message);
		}

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		BaseUser<?> user = (BaseUser<?>) principals.fromRealm(getName()).iterator().next();
		SimpleAuthorizationInfo info = null;
		user = this.getUserService().findByPrincipal(user.getPrincipal());
		if (user != null) {
			info = new SimpleAuthorizationInfo();
			for (BaseRole<?> role : user.getRoles()) {
				info.addRole(role.getName());
				for (BasePermission<?> permission : role.getPermissions()) {
					List<String> actions = new ArrayList<String>();
					for (BasePermissionAction<?> action : permission.getActions()) {
						actions.add(action.getName());
					}

					info.addStringPermission(permission.getDomain().getName() + ":" + StringUtils.join(actions, ','));
				}
			}
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo result = null;
		BaseUser<?> user = this.getUserService().findByPrincipal(token.getPrincipal().toString());

		if (user == null) {
			String message = "No account found for principal [" + token.getPrincipal() + "]";
			throw new UnknownAccountException(message);
		} else {

			result = new SimpleAuthenticationInfo(user, user.getCredentials(), getName());

			if (this.getCredentialsMatcher() instanceof JeeCredentialsMatcher){
				JeeCredentialsMatcher matcher = (JeeCredentialsMatcher) this.getCredentialsMatcher();
				if (matcher.getCredentialsHashConfig() != null) {
					ByteSource salt = new SimpleByteSource(user.getCredentialsSalt());
					result.setCredentialsSalt(salt);
				}
			}
			
			checkUserInfo(user);
			if (isConcurrentAccess()){
				checkConcurrentAccess(user);
			}
			if (isExpiredCredentials()){
				checkCredentialsExpired(token, user, result);
			}
			checkAcceptedConditions(token, user, result);

		}

		return result;
	}


	public boolean isExpiredCredentials() {
		return expiredCredentials;
	}

	public void setExpiredCredentials(boolean expiredCredentials) {
		this.expiredCredentials = expiredCredentials;
	}
	
	public boolean isConcurrentAccess() {
		return concurrentAccess;
	}

	public void setConcurrentAccess(boolean concurrentAccess) {
		this.concurrentAccess = concurrentAccess;
	}

	public ConfigurationService<BaseConfiguration<?>, ?> getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService<BaseConfiguration<?>, ?> configurationService) {
		this.configurationService = configurationService;
	}

	public ShiroUserService<BaseUser<?>,?> getUserService() {
		return userService;
	}

	public void setUserService(ShiroUserService<BaseUser<?>,?> userService) {
		this.userService = userService;
	}

	public JeeSessionInformationService<?, ?> getSessionInformationService() {
		return sessionInformationService;
	}

	public void setSessionInformationService(JeeSessionInformationService<?, ?> sessionInformationService) {
		this.sessionInformationService = sessionInformationService;
	}
	
}
