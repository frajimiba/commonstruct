package com.github.frajimiba.commonstruct.spring.audit;

import java.io.Serializable;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.spring.domain.SpringEntity;

@MappedSuperclass
public abstract class SpringModifiedEntity<PK extends Serializable> extends SpringEntity<PK> {

  private static final long serialVersionUID = -2930480306387441446L;

  private String entityClassName;

  @Lob
  private Serializable entityId;

  public SpringModifiedEntity() {

  }

  public SpringModifiedEntity(String entityClassName, Serializable entityId) {
    this.entityClassName = entityClassName;
    this.entityId = entityId;
  }

  public String getEntityClassName() {
    return entityClassName;
  }

  public void setEntityClassName(String entityClassName) {
    this.entityClassName = entityClassName;
  }

  public Serializable getEntityId() {
    return entityId;
  }

  public void setEntityId(Serializable entityId) {
    this.entityId = entityId;
  }

  public abstract SpringRevisionEntity<?> getRevision();

}
