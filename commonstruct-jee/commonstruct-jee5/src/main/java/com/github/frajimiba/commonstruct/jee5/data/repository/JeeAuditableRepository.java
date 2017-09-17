package com.github.frajimiba.commonstruct.jee5.data.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;

import com.github.frajimiba.commonstruct.domain.BaseEntity;

/**
 * The Interface AuditJpaRepository.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
public interface JeeAuditableRepository
	<T extends BaseEntity<ID>, ID extends Serializable>
	extends JeeRepository<T,ID>{
	
	/**
	 * Gets the audit reader.
	 *
	 * @return the audit reader
	 */
	AuditReader getAuditReader();
	
	/**
	 * Gets the audit entity.
	 *
	 * @param entity the entity
	 * @param revision the revision
	 * @return the audit entity
	 */
	T getAuditEntity(T entity,Number revision);
	
	/**
	 * Gets the audit revisions.
	 *
	 * @param entity the entity
	 * @return the audit revisions
	 */
	List<Number> getAuditRevisions(T entity);
	
	/**
	 * Gets the audit query.
	 *
	 * @param entity the entity
	 * @param revision the revision
	 * @return the audit query
	 */
	AuditQuery getAuditQuery(T entity, Number revision);
	
	/**
	 * Gets the audit query.
	 *
	 * @param entity the entity
	 * @param selectEntitiesOnly the select entities only
	 * @param selectDeletedEntities the select deleted entities
	 * @return the audit query
	 */
	AuditQuery getAuditQuery(T entity, boolean selectEntitiesOnly,
			boolean selectDeletedEntities);

}
