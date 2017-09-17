package com.github.frajimiba.commonstruct.spring.audit;

import java.io.Serializable;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;

import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class SpringRevisionListener implements EntityTrackingRevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {
    SpringRevisionEntity<?> entity = (SpringRevisionEntity<?>) revisionEntity;
    entity.setAction(SpringUtils.getLastAction().name());
    entity.setPrincipal(SpringUtils.getLastPrincipal());
    SpringUtils.removeLastAction();
  }

  @Override
  public void entityChanged(@SuppressWarnings("rawtypes") Class entityClass, String entityName, Serializable entityId,
      RevisionType revisionType, Object revisionEntity) {
    String type = entityClass.getName();
    SpringRevisionEntity<?> entity = (SpringRevisionEntity<?>) revisionEntity;
    entity.addModifiedEntityType(type, entityId);
  }

}
