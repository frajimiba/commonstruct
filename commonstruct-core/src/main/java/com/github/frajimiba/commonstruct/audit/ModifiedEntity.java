package com.github.frajimiba.commonstruct.audit;

import java.io.Serializable;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.github.frajimiba.commonstruct.domain.BaseEntity;

@MappedSuperclass
public abstract class ModifiedEntity<PK extends Serializable> extends BaseEntity<PK> {

  private static final long serialVersionUID = -2930480306387441446L;

  private String entityClassName;

  @Lob
  private Serializable entityId;

  public ModifiedEntity(){};
  
  public ModifiedEntity(String entityClassName, Serializable entityId) {
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

  public abstract RevisionEntity<?> getRevision();

}