package com.github.frajimiba.commonstruct.spring.audit;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPreCollectionRemoveEventListenerImpl;
import org.hibernate.event.spi.PreCollectionRemoveEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class ActionEnversPreCollectionRemoveEventListenerImpl extends EnversPreCollectionRemoveEventListenerImpl {

  private static final long serialVersionUID = -152057197708477019L;

  protected ActionEnversPreCollectionRemoveEventListenerImpl(AuditConfiguration enversConfiguration) {
    super(enversConfiguration);
  }

  @Override
  public void onPreRemoveCollection(PreCollectionRemoveEvent event) {
    Action lastAction = SpringUtils.getLastAction();
    if (lastAction != null) {
      super.onPreRemoveCollection(event);
    }
  }
}
