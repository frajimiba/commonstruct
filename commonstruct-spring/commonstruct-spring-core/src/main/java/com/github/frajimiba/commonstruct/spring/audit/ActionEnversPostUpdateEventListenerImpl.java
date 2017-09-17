package com.github.frajimiba.commonstruct.spring.audit;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostUpdateEventListenerImpl;
import org.hibernate.event.spi.PostUpdateEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class ActionEnversPostUpdateEventListenerImpl extends EnversPostUpdateEventListenerImpl {

  private static final long serialVersionUID = -9077129451315108576L;

  protected ActionEnversPostUpdateEventListenerImpl(AuditConfiguration enversConfiguration) {
    super(enversConfiguration);
  }

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
    boolean isUpdate = false;
    Action lastAction = SpringUtils.getLastAction();
    if (lastAction != null) {
      isUpdate = true;
    } else {
      Object entity = event.getEntity();
      if (entity instanceof SpringRevisionEntity<?>) {
        SpringRevisionEntity<?> entityRevision = (SpringRevisionEntity<?>) entity;
        if (entityRevision.getAction() != null) {
          isUpdate = true;
        }
      } else if (entity instanceof SpringModifiedEntity<?>) {
        SpringModifiedEntity<?> modifyEntity = (SpringModifiedEntity<?>) entity;
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
