package com.github.frajimiba.commonstruct.jee5.security.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.service.SecurityActionNames;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;

public abstract class BaseShiroUserService<E extends BaseUser<P>, P extends Serializable>
		extends BaseJeeUserService<E, P> implements ShiroUserService<E, P> {

	@Override
	public void encodePassword(E user, String password) {	
		if (this.getCredentialsMatcher().getCredentialsHashConfig()!=null) {
			String hashAlgorithmName = this.getCredentialsMatcher().getCredentialsHashConfig().getHashAlgorithmName();
			int hashIteretions = this.getCredentialsMatcher().getCredentialsHashConfig().getIterations();
			RandomNumberGenerator rng = new SecureRandomNumberGenerator();
			String salt = rng.nextBytes().toHex();
			SimpleHash hash = new SimpleHash(hashAlgorithmName, password, salt, hashIteretions);
			user.setCredentialsSalt(salt);
			user.setCredentials(hash.toHex());
		}
	}

	@Override
	@Action(name = SecurityActionNames.USER_CHANGED_PASSWORD, auditable = true)
	public void changePassword(E user, String password) {
		encodePassword(user, password);
		user.setCredentialsTimestamp(new Date().getTime());
		this.getRepository().save(user);

	}
	
	@Override
	@SuppressWarnings("unchecked")
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
				E userRevision = (E) revision[0];
				result = checkCredentials(userRevision, newPassword);
			}
		}

		return result;
	}
	
	private boolean checkCredentials(E user, String newPassword) {

		UsernamePasswordToken token = new UsernamePasswordToken(user.getPrincipal(), newPassword);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getPrincipal(), user.getCredentials(), "");
		if (this.getCredentialsMatcher().getCredentialsHashConfig()!=null) {
			ByteSource salt = new SimpleByteSource(user.getCredentialsSalt());
			info.setCredentialsSalt(salt);
		}

		return this.getCredentialsMatcher().doCredentialsMatch(token, info);
	}
	
	@Override
	@Action(name = SecurityActionNames.USER_LOGIN_ATTEMP, auditable = true)
	public int addLoginAttempt(String username){
		E user = this.findByPrincipal(username);
		int newAttempts = user.getAttempts() + 1;
		int configLoginAttemps = (Integer)this.getConfigurationService().get(UserServiceSettings.MAX_REPEATED_CREDENTIALS);
		if (newAttempts <= configLoginAttemps) {
			user.setAttempts(newAttempts);
			if (newAttempts == configLoginAttemps) {
				user.setLocked(true);
			}
			this.getRepository().save(user);
		}
		return newAttempts;
	}	
	
	@Override
	@Action(name = SecurityActionNames.USER_LOGIN, auditable = true)
	public void onLogin(E user){
		Date lastAccess = this.getLastAccess(user);
		Date now = new Date();
		user.setAccessTimestamp(now.getTime());
		user.setLastAccess(lastAccess);
    	this.getRepository().save(user);
	}

}