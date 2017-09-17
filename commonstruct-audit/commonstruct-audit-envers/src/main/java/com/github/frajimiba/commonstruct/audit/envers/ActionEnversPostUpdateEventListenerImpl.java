package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostUpdateEventListenerImpl;
import org.hibernate.event.spi.PostUpdateEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.AuditContext;

public class ActionEnversPostUpdateEventListenerImpl extends EnversPostUpdateEventListenerImpl {

  private static final long serialVersionUID = -9077129451315108576L;
  private AuditContext auditContext;
  
  protected ActionEnversPostUpdateEventListenerImpl(AuditConfiguration enversConfiguration, AuditContext auditContext) {
    super(enversConfiguration);
    this.auditContext = auditContext;
  }

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
    boolean isUpdate = false;
    Action lastAction =  this.auditContext.getLastAction();
    if (lastAction != null) {
      isUpdate = true;
    } else {
      Object entity = event.getEntity();
      if (entity instanceof EnversRevisionEntity<?>) {
    	  EnversRevisionEntity<?> entityRevision = (EnversRevisionEntity<?>) entity;
        if (entityRevision.getAction() != null) {
          isUpdate = true;
        }
      } else if (entity instanceof EnversModifiedEntity<?>) {
        EnversModifiedEntity<?> modifyEntity = (EnversModifiedEntity<?>) entity;
        if (modifyEntity.getRevision().getAction() != null) {
          isUpdate = true;
        }
      }
    }
    if (isUpdate) {
      super.onPostUpdate(event);
    }
  }

}
