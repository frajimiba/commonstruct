package com.github.frajimiba.commonstruct.spring.audit;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostCollectionRecreateEventListenerImpl;
import org.hibernate.event.spi.PostCollectionRecreateEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class ActionEnversPostCollectionRecreateEventListenerImpl extends EnversPostCollectionRecreateEventListenerImpl {

  private static final long serialVersionUID = -8120458535325449173L;

  protected ActionEnversPostCollectionRecreateEventListenerImpl(AuditConfiguration enversConfiguration) {
    super(enversConfiguration);
  }

  @Override
  public void onPostRecreateCollection(PostCollectionRecreateEvent event) {
    Action lastAction = SpringUtils.getLastAction();
    if (lastAction != null) {
      super.onPostRecreateCollection(event);
    }
  }

}
