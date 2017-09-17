package com.github.frajimiba.commonstruct.audit.envers;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostCollectionRecreateEventListenerImpl;
import org.hibernate.event.spi.PostCollectionRecreateEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.audit.AuditContext;

public class ActionEnversPostCollectionRecreateEventListenerImpl extends EnversPostCollectionRecreateEventListenerImpl {

  private static final long serialVersionUID = -8120458535325449173L;
  private AuditContext auditContext;

  protected ActionEnversPostCollectionRecreateEventListenerImpl(AuditConfiguration enversConfiguration, AuditContext auditContext) {
    super(enversConfiguration);
    this.auditContext = auditContext;
  }

  @Override
  public void onPostRecreateCollection(PostCollectionRecreateEvent event) {
    Action lastAction = this.auditContext.getLastAction();
    if (lastAction != null) {
      super.onPostRecreateCollection(event);
    }
  }

}
