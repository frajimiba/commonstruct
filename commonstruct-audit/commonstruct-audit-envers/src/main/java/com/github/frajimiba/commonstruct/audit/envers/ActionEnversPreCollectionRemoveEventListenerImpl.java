package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPreCollectionRemoveEventListenerImpl;
import org.hibernate.event.spi.PreCollectionRemoveEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.AuditContext;

public class ActionEnversPreCollectionRemoveEventListenerImpl extends EnversPreCollectionRemoveEventListenerImpl {

  private static final long serialVersionUID = -152057197708477019L;
  private AuditContext auditContext;
  
  protected ActionEnversPreCollectionRemoveEventListenerImpl(AuditConfiguration enversConfiguration, AuditContext auditContext) {
    super(enversConfiguration);
    this.auditContext = auditContext;
  }

  @Override
  public void onPreRemoveCollection(PreCollectionRemoveEvent event) {
    Action lastAction =  this.auditContext.getLastAction();
    if (lastAction != null) {
      super.onPreRemoveCollection(event);
    }
  }
}
