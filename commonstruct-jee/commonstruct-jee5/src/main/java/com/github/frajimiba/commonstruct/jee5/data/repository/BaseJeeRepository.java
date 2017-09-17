package com.github.frajimiba.commonstruct.jee5.data.repository;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.jee5.data.domain.BasePage;
import com.github.frajimiba.commonstruct.jee5.data.domain.Page;
import com.github.frajimiba.commonstruct.jee5.data.domain.Pageable;
import com.github.frajimiba.commonstruct.jee5.data.domain.Sort;
import com.github.frajimiba.commonstruct.jee5.data.util.QueryUtil;
import com.github.frajimiba.commonstruct.specification.Specification;
import com.github.frajimiba.commonstruct.util.GenericsUtil;

/**
 * Basic JPA Repository Implementation.
 * 
 * @author Francisco José Jiménez
 * 
 * @param <T>
 *            Repository entity type
 * @param <ID>
 *            Identity type of entity repository
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BaseJeeRepository<T extends BaseEntity<ID>, ID extends Serializable> implements JeeRepository<T, ID> {

	/**
	 * The EntityManager.
	 */
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * The Entity type.
	 */
	private Class<T> type;

	/**
	 * Default Constructor.
	 */
	@SuppressWarnings("unchecked")
	public BaseJeeRepository() {
		List<Class<?>> typeArguments = GenericsUtil.getTypeArguments(
				BaseJeeRepository.class, getClass());
		this.type = (Class<T>) typeArguments.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getCountAll() {
		return (Long) getEntityManager().createQuery(getCountQueryString())
				.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T entity) {
		getEntityManager().remove(getEntityManager().contains(entity) ? entity
				: getEntityManager().merge(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(ID id) {
		T entity = findOne(id);
		getEntityManager().remove(getEntityManager().contains(entity) ? entity
				: getEntityManager().merge(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Collection<? extends T> entities) {
		if (null == entities || entities.isEmpty()) {
			return;
		}
		QueryUtil.applyAndBind(getDeleteAllQueryString(), entities,
				getEntityManager()).executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteAll() {
		getEntityManager().createQuery(getDeleteAllQueryString()).executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findOne(ID id) {
		return getEntityManager().find(this.type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(ID id) {
		return null != findOne(id);
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	protected Class<T> getType(){
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return getReadAllQuery().getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<T> getAll(Pageable pageable) {
		Page<T> result;
		if (null == pageable) {
			result = new BasePage<T>(findAll());
		}
		else {
			result = getPage(pageable, getReadAllQueryString());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Sort sort) {
		String queryString = QueryUtil.applySorting(getReadAllQueryString(),
				sort);
		Query query = getEntityManager().createQuery(queryString);
		return (null == sort) ? findAll() : (List<T>) query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(T entity) {
		getEntityManager().persist(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(T entity) {
		return getEntityManager().contains(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(T entity) {
		getEntityManager().merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T save(T entity) {
		T result;
		if (entity.getId() == null || findOne(entity.getId()) == null) {
			getEntityManager().persist(entity);
			result = entity;
		}
		else {
			result = getEntityManager().merge(entity);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByNamedQuery(String queryName,
			Map<String, ? extends Object> args) {
		Query namedQuery = getEntityManager().createNamedQuery(queryName);
		for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
			namedQuery.setParameter(parameter.getKey(), parameter.getValue());
		}
		return namedQuery.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByNamedQuery(String queryName, Object[] args) {
		Query namedQuery = getEntityManager().createNamedQuery(queryName);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				namedQuery.setParameter(i + 1, args[i]);
			}
		}
		return namedQuery.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getInstanceByNamedQuery(String queryName, Object[] args) {
		T result;
		Query namedQuery = getEntityManager().createNamedQuery(queryName);
		if (args != null) {
			int position = 1;
			for (Object value : args) {
				namedQuery.setParameter(position++, value);
			}
		}

		try {
			result = (T) namedQuery.getSingleResult();
		}
		catch (NoResultException nre) {
			result = null;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getInstanceByNamedQuery(String queryName,
			Map<String, ? extends Object> args) {
		T result;
		Query namedQuery = getEntityManager().createNamedQuery(queryName);
		if (args != null) {
			for (Map.Entry<String, ? extends Object> entry : args.entrySet()) {
				namedQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}

		try {
			result = (T) namedQuery.getSingleResult();
		}
		catch (NoResultException nre) {
			result = null;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() {
		getEntityManager().flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		getEntityManager().clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getBySpecification(Specification<T> specification) {
		List<T> result = new ArrayList<T>();
		for (T entity : this.findAll()) {
			if (specification.isSatisfiedBy(entity)) {
				result.add(entity);
			}
		}
		return result;
	}

	/**
	 * Gets the read all query string.
	 *
	 * @return the read all query string
	 */
	protected String getReadAllQueryString() {
		return QueryUtil.getQueryString(QueryUtil.READ_ALL_QUERY, this.type);
	}

	/**
	 * Gets the delete all query string.
	 *
	 * @return the delete all query string
	 */
	protected String getDeleteAllQueryString() {

		return QueryUtil.getQueryString(QueryUtil.DELETE_ALL_QUERY_STRING, this.type);
	}

	/**
	 * Gets the read all query.
	 *
	 * @return the read all query
	 */
	protected Query getReadAllQuery() {

		return getEntityManager().createQuery(getReadAllQueryString());
	}

	/**
	 * Gets the page.
	 *
	 * @param pageable the pageable
	 * @param query the query
	 * @return the page
	 */
	protected Page<T> getPage(final Pageable pageable, final String query) {

		String queryString = QueryUtil.applySorting(query, pageable.getSort());
		Query jpaQuery = getEntityManager().createQuery(queryString);

		return getPage(jpaQuery, pageable);
	}

	/**
	 * Gets the page.
	 *
	 * @param query the query
	 * @param pageable the pageable
	 * @return the page
	 */
	@SuppressWarnings("unchecked")
	private Page<T> getPage(final Query query, final Pageable pageable) {

		query.setFirstResult(pageable.getFirstItem());
		query.setMaxResults(pageable.getPageSize());

		return new BasePage<T>((List<T>) query.getResultList(), pageable,
				getCountAll());
	}
	
	/**
	 * Gets the page.
	 *
	 * @param query the query
	 * @param countQuery the count query
	 * @param pageable the pageable
	 * @return the page
	 */
	@SuppressWarnings("unchecked")
	public Page<T> getPage(final Query query, final Query countQuery, 
			final Pageable pageable) {

		query.setFirstResult(pageable.getFirstItem());
		query.setMaxResults(pageable.getPageSize());
		
		return new BasePage<T>((List<T>) query.getResultList(), pageable,
				(Long)countQuery.getSingleResult());
	}
	
	/**
	 * Gets the count query string.
	 *
	 * @return the count query string
	 */
	protected String getCountQueryString() {
		PersistenceProvider provider = PersistenceProvider
				.fromEntityManager(getEntityManager());
		String countQuery = String.format(QueryUtil.COUNT_QUERY_STRING,
				provider.getCountQueryPlaceholder(), "%s");

		return QueryUtil.getQueryString(countQuery, this.type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<T> getByNamedQuery(Pageable pageable, String queryName,
			Map<String, ? extends Object> args) {
		
		Page<T> result;

		if (null == pageable) {
			result = new BasePage<T>(getByNamedQuery(queryName,args));
		}
		else{
			
			String query = QueryUtil.getNamedQueryCode(this.getType(),queryName);
			
			String queryString = QueryUtil.applySorting(query, pageable.getSort(),
					QueryUtil.detectAlias(query));
			
			Query namedQuery = getEntityManager().createQuery(queryString);
			
			if (args!=null){
				for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
					namedQuery.setParameter(parameter.getKey(), parameter.getValue());
				}
			}

			String countQueryString = QueryUtil.createCountQueryFor(query);
			
			Query namedCountQuery = getEntityManager().createQuery(countQueryString);
			if (args!=null){
				for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
					namedCountQuery.setParameter(parameter.getKey(), 
							parameter.getValue());
				}
			}
			result = getPage(namedQuery,namedCountQuery,pageable);
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByNamedQuery(Sort sort, String queryName,
			Map<String, ? extends Object> args) {
		
		Query namedQuery = getEntityManager().createNamedQuery(queryName);
		for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
			namedQuery.setParameter(parameter.getKey(), parameter.getValue());
		}
		String queryString = QueryUtil.applySorting(namedQuery.toString(),
				sort);
		Query query = getEntityManager().createQuery(queryString);
		return (null == sort) ? findAll() : (List<T>) query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getCountNamedQuery(String queryName, Map<String, ? extends Object> args) {
		Query namedQuery = getEntityManager().createNamedQuery(queryName);
		for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
			namedQuery.setParameter(parameter.getKey(), parameter.getValue());
		}
		
		String countQuery = QueryUtil.createCountQueryFor(namedQuery.toString());

		return (Long) getEntityManager().createQuery(countQuery).getSingleResult();
	}

}