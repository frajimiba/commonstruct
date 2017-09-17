package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostInsertEventListenerImpl;
import org.hibernate.event.spi.PostInsertEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.AuditContext;

public class ActionEnversPostInsertEventListenerImpl extends EnversPostInsertEventListenerImpl {

  private static final long serialVersionUID = -2303826161169643470L;
  private AuditContext auditContext;
  
  public ActionEnversPostInsertEventListenerImpl(AuditConfiguration enversConfiguration, AuditContext auditContext) {
    super(enversConfiguration);
    this.auditContext = auditContext;
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    boolean isInsert = false;
    Action lastAction =  this.auditContext.getLastAction();

    if (lastAction != null) {
      isInsert = true;
    } else {
      Object entity = event.getEntity();
      if (entity instanceof EnversRevisionEntity<?>) {
    	  EnversRevisionEntity<?> entityRevision = (EnversRevisionEntity<?>) entity;
        if (entityRevision.getAction() != null) {
          isInsert = true;
        }
      } else if (entity instanceof EnversModifiedEntity<?>) {
        EnversModifiedEntity<?> modifyEntity = (EnversModifiedEntity<?>) entity;
        if (modifyEntity.getRevision().getAction() != null) {
          isInsert = true;
        }
      }
    }
    if (isInsert) {
      super.onPostInsert(event);
    }
  }
}
