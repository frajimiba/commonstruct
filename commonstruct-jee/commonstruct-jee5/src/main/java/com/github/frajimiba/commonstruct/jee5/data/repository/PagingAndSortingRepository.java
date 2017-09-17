package com.github.frajimiba.commonstruct.jee5.data.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.frajimiba.commonstruct.data.Repository;
import com.github.frajimiba.commonstruct.domain.Entity;
import com.github.frajimiba.commonstruct.jee5.data.domain.Page;
import com.github.frajimiba.commonstruct.jee5.data.domain.Pageable;
import com.github.frajimiba.commonstruct.jee5.data.domain.Sort;

/**
 * The Interface PagingAndSortingRepository.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
public interface PagingAndSortingRepository
		<T extends Entity<ID>,ID extends Serializable>
		extends Repository<T,ID>{
	
	/**
	 * Gel paging all entities of
	 * <code>T</code> in the storage.
	 *
	 * @param pageable the pageable
	 * @return A list <code>T</code> entities or null if no objects match
	 */
    Page<T> getAll(Pageable pageable);
    
    /**
     * Gel sorting all entities of
     * <code>T</code> in the storage.
     *
     * @param sort the sort
     * @return A list <code>T</code> entities or null if no objects match
     */
    List<T> getAll(Sort sort);
    
    /**
     * Gets the by named query.
     *
     * @param pageable the pageable
     * @param queryName the query name
     * @param args the args
     * @return the by named query
     */
    Page<T> getByNamedQuery(Pageable pageable, String queryName, 
    		Map<String, ? extends Object> args);
    /**
     * Gets the by named query.
     *
     * @param sort the sort
     * @param queryName the query name
     * @param args the args
     * @return the by named query
     */
    List<T> getByNamedQuery(Sort sort, String queryName,  
    		Map<String, ? extends Object> args);


}
