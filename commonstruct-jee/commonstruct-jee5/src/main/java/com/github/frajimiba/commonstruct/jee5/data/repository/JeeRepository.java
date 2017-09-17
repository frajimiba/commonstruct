package com.github.frajimiba.commonstruct.jee5.data.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.github.frajimiba.commonstruct.domain.BaseEntity;
import com.github.frajimiba.commonstruct.specification.Specification;

/**
 * An JPA Repository.
 *
 * @author Francisco José Jiménez
 *
 * @param <T> Repository entity type
 * @param <ID> Identity type of entity repository
 */
public interface JeeRepository<T extends BaseEntity<ID>, ID extends Serializable> 
			extends PagingAndSortingRepository<T, ID> {

    /**
     * Get the EntityManager.
     *
     * @return The EntityManager object
     */
    EntityManager getEntityManager();

    /**
     * Save
     * <code>entity</code> in the storage. If the entity exists updated if not
     * inserted
     *
     * @param entity The entity to save in the storage
     * @return The entity save in the storage
     */
    T save(T entity);

    /**
     * Insert
     * <code>entity</code> in the storage.
     *
     * @param entity The entity to insert in the storage
     */
    void insert(T entity);

    /**
     * Update
     * <code>entity</code> in the storage.
     *
     * @param entity The entity to update in the storage
     */
    void update(T entity);

    /**
     * Remove
     * <code>entity</code> from the storage.
     *
     * @param entity The entity to be removed from the storage
     */
    void delete(T entity);

    /**
     * Remove
     * <code>entity</code> from the storage by identity.
     *
     * @param identity The identity of the entity to be removed from the storage
     */
    void delete(ID identity);
    
    /**
     * Remove a collection
     * <code>entity</code> from the storage .
     *
     * @param entities The entities to be removed from the storage
     */
    void delete(Collection<? extends T> entities);

    /**
     * Remove all
     * <code>entity</code> from the storage.
     */
    void deleteAll();
    
    /**
     * Get the entity
     * <code>T</code> identified by
     * <code>identity</code>.
     *
     * @param identity The identity of the entity to get from the storage
     * @return The entity identified by <code>identity</code> or null if no
     * objects match
     */
    T findOne(ID identity);
    /**
     * Indicates whether the entity exists.
     *
     * @param identity The identity of the entity to evaluate
     * @return <code>true</code> if the entity exists.
     */
    boolean exists(ID identity);

    /**
     * Gel all entities of
     * <code>T</code> in the storage.
     *
     * @return A list <code>T</code> entities or null if no objects match
     */
    List<T> findAll();
    
    /**
     * Get entities of
     * <code>T</code> in the storage by specification.
     *
     * @param specification the search results specificaciÃ³n
     * @return a list <code>T</code> entities or null if no objects match
     */
    List<T> getBySpecification(Specification<T> specification);
    
    /**
     * Find instances of
     * <code>T</code> that match the criteria defined by query
     * <code>queryName</code>.
     * <code>args</code> provide the values for any named parameters in the
     * query identified by
     * <code>queryName</code>.
     *
     * @param queryName the named query to execute
     * @param args the values used by the query
     * @return a list of <code>T</code> objects
     */
    List<T> getByNamedQuery(String queryName, Map<String, ? extends Object> args);

    /**
     * Find instances of
     * <code>T</code> that match the criteria defined by query
     * <code>queryName</code>.
     *
     * <code>args</code> provide values for positional arguments in the query
     * identified by
     * <code>queryName</code>.
     *
     * @param queryName the named query to execute
     * @param args the positional values used in the query
     * @return a list of <code>T</code> objects
     */
    List<T> getByNamedQuery(String queryName, Object[] args);

    /**
     * Find a single instance of
     * <code>T</code> using the query named
     * <code>queryName</code> and the arguments identified by
     * <code>args</code>.
     *
     * @param queryName the name of the query to use
     * @param args the arguments for the named query
     * @return T or null if no objects match the criteria if more than one
     * instance is returned.
     */
    T getInstanceByNamedQuery(String queryName, Object[] args);

    /**
     * Find a single instance of
     * <code>T</code> using the query named
     * <code>queryName</code> and the arguments identified by
     * <code>args</code>.
     *
     * @param queryName The name of the query to use
     * @param args A Map holding the named parameters of the query
     * @return T or null if no objects match the criteria if more than one
     * instance is returned.
     */
    T getInstanceByNamedQuery(String queryName, Map<String, ? extends Object> args);

    /**
     * Taken from the EntityManager documentation: Refresh the state of the
     * instance from the database, overwriting changes made to the entity, if
     * any.
     *
     * @param entity The entity to refresh
     */
    void refresh(T entity);

    /**
     * Check if the entity is available in the EntityManager.
     *
     * @param entity The entity to check
     * @return true if present
     */
    boolean contains(T entity);

    /**
     * Taken from the EntityManager documentation: Synchronize the persistence
     * context to the underlying database.
     */
    void flush();

    /**
     * Taken from the EntityManager documentation: Clear the persistence
     * context, causing all managed entities to become detached. Changes made to
     * entities that have not been flushed to the database will not be
     * persisted.
     */
    void clear();

    /**
     * Gets the count of all entities.
     * @return the count of entities
     * */
	Long getCountAll();
	
	/**
     * Gets the count of the resulting entities
     * using the query named
     * <code>queryName</code> and the arguments identified by
     * <code>args</code>.
     *
     * @param queryName The name of the query to use
     * @param args A Map holding the named parameters of the query
     * @return the count of entities.
     */
	Long getCountNamedQuery(String queryName, Map<String, ? extends Object> args);
}