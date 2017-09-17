package com.github.frajimiba.commonstruct.spring.audit.data;

import java.io.Serializable;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditableRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

  /**
   * Gets the audit reader.
   *
   * @return the audit reader
   */
  AuditReader getAuditReader();

  /**
   * Gets the audit query.
   *
   * @param entity
   *          the entity
   * @param revision
   *          the revision
   * @return the audit query
   */
  AuditQuery getAuditQuery(T entity, Number revision);

  /**
   * Gets the audit query.
   *
   * @param entity
   *          the entity
   * @param selectEntitiesOnly
   *          the select entities only
   * @param selectDeletedEntities
   *          the select deleted entities
   * @return the audit query
   */
  AuditQuery getAuditQuery(T entity, boolean selectEntitiesOnly, boolean selectDeletedEntities);

}
