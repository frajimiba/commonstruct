package com.github.frajimiba.commonstruct.audit.envers;

import java.io.Serializable;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;

import com.github.frajimiba.commonstruct.audit.AuditContext;

public abstract class EnversAuditRevisionListener implements EntityTrackingRevisionListener {


	@Override
	public void newRevision(Object revisionEntity) {
		EnversRevisionEntity<?> entity = (EnversRevisionEntity<?>) revisionEntity;
		entity.setAction(getAuditContext().getLastAction().name());
		entity.setPrincipal(getAuditContext().getLastPrincipal());
		getAuditContext().removeLastAction();
	}

	@Override
	public void entityChanged(@SuppressWarnings("rawtypes") Class entityClass, String entityName, Serializable entityId,
			RevisionType revisionType, Object revisionEntity) {
		String type = entityClass.getName();
		EnversRevisionEntity<?> entity = (EnversRevisionEntity<?>) revisionEntity;
		entity.addModifiedEntityType(type, entityId);
	}

	public abstract AuditContext getAuditContext();

}
