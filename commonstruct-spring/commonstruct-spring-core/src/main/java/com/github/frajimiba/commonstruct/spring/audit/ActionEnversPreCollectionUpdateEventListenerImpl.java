package com.github.frajimiba.commonstruct.spring.audit;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPreCollectionUpdateEventListenerImpl;
import org.hibernate.event.spi.PreCollectionUpdateEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class ActionEnversPreCollectionUpdateEventListenerImpl extends EnversPreCollectionUpdateEventListenerImpl {

  private static final long serialVersionUID = -8038754172209419357L;

  protected ActionEnversPreCollectionUpdateEventListenerImpl(AuditConfiguration enversConfiguration) {
    super(enversConfiguration);
  }

  @Override
  public void onPreUpdateCollection(PreCollectionUpdateEvent event) {
    Action lastAction = SpringUtils.getLastAction();
    if (lastAction != null) {
      super.onPreUpdateCollection(event);
    }
  }
}
