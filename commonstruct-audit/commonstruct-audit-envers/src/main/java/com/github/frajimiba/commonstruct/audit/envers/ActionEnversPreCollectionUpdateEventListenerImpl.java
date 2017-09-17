package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPreCollectionUpdateEventListenerImpl;
import org.hibernate.event.spi.PreCollectionUpdateEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.AuditContext;

public class ActionEnversPreCollectionUpdateEventListenerImpl extends EnversPreCollectionUpdateEventListenerImpl {

  private static final long serialVersionUID = -8038754172209419357L;
  private AuditContext auditContext;
  
  protected ActionEnversPreCollectionUpdateEventListenerImpl(AuditConfiguration enversConfiguration, AuditContext auditContext) {
    super(enversConfiguration);
    this.auditContext = auditContext;
  }

  @Override
  public void onPreUpdateCollection(PreCollectionUpdateEvent event) {
    Action lastAction =  this.auditContext.getLastAction();
    if (lastAction != null) {
      super.onPreUpdateCollection(event);
    }
  }
}
