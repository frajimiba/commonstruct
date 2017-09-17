package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostDeleteEventListenerImpl;
import org.hibernate.event.spi.PostDeleteEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.AuditContext;


public class ActionEnversPostDeleteEventListenerImpl extends EnversPostDeleteEventListenerImpl {

  private static final long serialVersionUID = 2210981002762048761L;
  private AuditContext auditContext;
  
  protected ActionEnversPostDeleteEventListenerImpl(AuditConfiguration enversConfiguration, AuditContext auditContext) {
    super(enversConfiguration);
    this.auditContext = auditContext;
  }

  @Override
  public void onPostDelete(PostDeleteEvent event) {
    boolean isDelete = false;
    Action lastAction =  this.auditContext.getLastAction();
    if (lastAction != null) {
      isDelete = true;
    } else {
      Object entity = event.getEntity();
      if (entity instanceof EnversRevisionEntity<?>) {
    	  EnversRevisionEntity<?> entityRevision = (EnversRevisionEntity<?>) entity;
        if (entityRevision.getAction() != null) {
          isDelete = true;
        }
      } else if (entity instanceof EnversModifiedEntity<?>) {
        EnversModifiedEntity<?> modifyEntity = (EnversModifiedEntity<?>) entity;
        if (modifyEntity.getRevision().getAction() != null) {
          isDelete = true;
        }
      }
    }
    if (isDelete) {
      super.onPostDelete(event);
    }
  }

}