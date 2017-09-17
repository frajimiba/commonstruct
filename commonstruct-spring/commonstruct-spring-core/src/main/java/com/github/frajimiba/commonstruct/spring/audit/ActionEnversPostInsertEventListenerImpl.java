package com.github.frajimiba.commonstruct.spring.audit;

import org.hibernate.envers.configuration.AuditConfiguration;
import org.hibernate.envers.event.EnversPostInsertEventListenerImpl;
import org.hibernate.event.spi.PostInsertEvent;

import com.github.frajimiba.commonstruct.action.Action;
import com.github.frajimiba.commonstruct.spring.util.SpringUtils;

public class ActionEnversPostInsertEventListenerImpl extends EnversPostInsertEventListenerImpl {

  private static final long serialVersionUID = -2303826161169643470L;

  public ActionEnversPostInsertEventListenerImpl(AuditConfiguration enversConfiguration) {
    super(enversConfiguration);
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    boolean isInsert = false;
    Action lastAction = SpringUtils.getLastAction();

    if (lastAction != null) {
      isInsert = true;
    } else {
      Object entity = event.getEntity();
      if (entity instanceof SpringRevisionEntity<?>) {
        SpringRevisionEntity<?> entityRevision = (SpringRevisionEntity<?>) entity;
        if (entityRevision.getAction() != null) {
          isInsert = true;
        }
      } else if (entity instanceof SpringModifiedEntity<?>) {
        SpringModifiedEntity<?> modifyEntity = (SpringModifiedEntity<?>) entity;
        if (modifyEntity.getRevision().getAction() != null) {
          isInsert = true;
        }
      }
    }
    if (isInsert) {
      super.onPostInsert(event);
    }
  }
}
