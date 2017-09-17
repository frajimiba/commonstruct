package com.github.frajimiba.commonstruct.jee5.data.domain;

import java.util.Iterator;
import java.util.List;

/**
 * The Interface Page.
 *
 * @param <T> the generic type
 */
public interface Page<T> extends Iterable<T> {
	
	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	int getNumber();
    
    /**
     * Gets the size.
     *
     * @return the size
     */
    int getSize();
    
    /**
     * Gets the total pages.
     *
     * @return the total pages
     */
    int getTotalPages();
    
    /**
     * Gets the number of elements.
     *
     * @return the number of elements
     */
    int getNumberOfElements();
    
    /**
     * Gets the total elements.
     *
     * @return the total elements
     */
    long getTotalElements();
    
    /**
     * Checks for previous page.
     *
     * @return true, if successful
     */
    boolean hasPreviousPage();
    
    /**
     * Checks for next page.
     *
     * @return true, if successful
     */
    boolean hasNextPage();
    
    /**
	 * {@inheritDoc}
	 */
    Iterator<T> iterator();
    
    /**
     * As list.
     *
     * @return the list
     */
    List<T> asList();
    
    /**
     * Gets the sort.
     *
     * @return the sort
     */
    Sort getSort();
}