package com.github.frajimiba.commonstruct.spring.audit;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostDeleteEventListenerImpl;
import org.hibernate.event.spi.PostDeleteEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class ActionEnversPostDeleteEventListenerImpl extends EnversPostDeleteEventListenerImpl {

  private static final long serialVersionUID = 2210981002762048761L;

  protected ActionEnversPostDeleteEventListenerImpl(AuditConfiguration enversConfiguration) {
    super(enversConfiguration);
  }

  @Override
  public void onPostDelete(PostDeleteEvent event) {
    boolean isDelete = false;
    Action lastAction = SpringUtils.getLastAction();
    if (lastAction != null) {
      isDelete = true;
    } else {
      Object entity = event.getEntity();
      if (entity instanceof SpringRevisionEntity<?>) {
        SpringRevisionEntity<?> entityRevision = (SpringRevisionEntity<?>) entity;
        if (entityRevision.getAction() != null) {
          isDelete = true;
        }
      } else if (entity instanceof SpringModifiedEntity<?>) {
        SpringModifiedEntity<?> modifyEntity = (SpringModifiedEntity<?>) entity;
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