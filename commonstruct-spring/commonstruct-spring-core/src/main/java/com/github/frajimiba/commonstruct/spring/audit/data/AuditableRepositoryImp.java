package com.github.frajimiba.commonstruct.spring.audit.data;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class AuditableRepositoryImp<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements AuditableRepository<T, ID> {

  private final EntityManager entityManager;

  public AuditableRepositoryImp(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public AuditReader getAuditReader() {
    return AuditReaderFactory.get(this.entityManager);
  }

  @Override
  public AuditQuery getAuditQuery(T entity, Number revision) {
    return this.getAuditReader().createQuery().forEntitiesAtRevision(entity.getClass(), revision);
  }

  @Override
  public AuditQuery getAuditQuery(T entity, boolean selectEntitiesOnly, boolean selectDeletedEntities) {
    return this.getAuditReader().createQuery().forRevisionsOfEntity(entity.getClass(), selectEntitiesOnly,
        selectDeletedEntities);
  }

}
