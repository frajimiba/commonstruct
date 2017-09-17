package com.github.frajimiba.commonstruct.jee5.security.service;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.NoResultException;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.envers.EnversRevisionEntity;
import com.github.frajimiba.commonstruct.configuration.service.ConfigurationService;
import com.github.frajimiba.commonstruct.jee5.domain.service.BaseJeeDomainService;
import com.github.frajimiba.commonstruct.security.auth.BaseUser;
import com.github.frajimiba.commonstruct.security.service.SecurityActionNames;
import com.github.frajimiba.commonstruct.security.service.UserServiceSettings;

public abstract class BaseJeeUserService<E extends BaseUser<ID>, ID extends Serializable>
		extends BaseJeeDomainService<E, ID> implements JeeUserService<E, ID> {

	public abstract ConfigurationService<?,?> getConfigurationService();
	
	@Override
	public E findByPrincipal(String principal) {
		return this.getRepository().findByPrincipal(principal);
	}

	@Override
	public Date getLastAccess(E user) {

		Date result = null;
		AuditQuery query = this.getRepository().getAuditQuery(user, false, true);
		query.add(AuditEntity.revisionProperty("action").eq(SecurityActionNames.USER_LOGIN));
		query.add(AuditEntity.property("principal").eq(user.getPrincipal()));
		query.addOrder(AuditEntity.revisionNumber().desc());
		query.setMaxResults(1);

		try {
			Object[] revision = (Object[]) query.getSingleResult();

			if (revision != null) {
				EnversRevisionEntity<?> revisionEntity = (EnversRevisionEntity<?>) revision[1];
				result = revisionEntity.getRevisionDate();
			}

		} catch (NoResultException ex) {
			result = null;
		}

		return result;
	}

	@Override
	@Action(name = SecurityActionNames.USER_DISABLED, auditable = true)
	public void disable(E user) {
		if (!user.isDisabled()) {
			user.setDisabled(true);
			user.setDisabledTimestamp(new Date().getTime());
			this.getRepository().save(user);
		}
	}

	@Override
	@Action(name = SecurityActionNames.USER_ENABLED, auditable = true)
	public void enable(E user) {
		if (user.isDisabled()) {
			user.setDisabled(false);
			this.getRepository().save(user);
		}
	}

	@Override
	@Action(name = SecurityActionNames.USER_UNLOCKED, auditable = true)
	public void unlock(E user) {
		if (user.isLocked()) {
			user.setAttempts(0);
			user.setLocked(false);
			this.getRepository().save(user);
		}
	}

	@Override
	@Action(name = SecurityActionNames.USER_ACEPTED_CONDITIONS, auditable = true)
	public void acceptConditions(E user) {
		if (!user.isAcceptedConditions()) {
			user.setAcceptedConditions(true);
			this.getRepository().save(user);
		}
	}

	public boolean isDisabledExpired(E user) {
		boolean result = false;
		Long disabledExpirationTime = (Long) this.getConfigurationService()
				.get(UserServiceSettings.DISABLED_EXPIRATION_TIME);
		if (disabledExpirationTime != null) {
			result = user.isDisabledExpired(disabledExpirationTime);
		}
		return result;
	}

	@Override
	@Action(name = SecurityActionNames.USER_REMOVED, auditable = true)
	public void remove(E user) {
		this.getRepository().delete(user);
	}


}