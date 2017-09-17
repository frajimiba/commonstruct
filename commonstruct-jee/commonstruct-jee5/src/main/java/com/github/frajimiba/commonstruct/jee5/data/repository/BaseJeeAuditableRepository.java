package com.github.frajimiba.commonstruct.jee5.data.repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;

import com.github.frajimiba.commonstruct.domain.BaseEntity;

/**
 * The Class BaseAuditJpaRepository.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BaseJeeAuditableRepository
	<T extends BaseEntity<ID>, ID extends Serializable>
	extends BaseJeeRepository<T,ID>
	implements JeeAuditableRepository<T, ID>{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(this.getEntityManager());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getAuditEntity(T entity, Number revision){
		return this.getAuditReader().find(this.getType(),entity.getId(), revision);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Number> getAuditRevisions(T entity) {
		return this.getAuditReader().getRevisions(this.getType(), entity.getId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditQuery getAuditQuery(T entity, Number revision){
		return this.getAuditReader().createQuery().
				forEntitiesAtRevision(this.getType(), revision);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuditQuery getAuditQuery(T entity, boolean selectEntitiesOnly, 
			boolean selectDeletedEntities){
		return this.getAuditReader().createQuery().
				forRevisionsOfEntity(this.getType(),selectEntitiesOnly ,
						selectDeletedEntities);
	}
}