package com.github.frajimiba.commonstruct.jee5.data.domain;

/**
 * The Interface Pageable.
 */
public interface Pageable {
    
    /**
     * Gets the page number.
     *
     * @return the page number
     */
    int getPageNumber();
    
    /**
     * Gets the page size.
     *
     * @return the page size
     */
    int getPageSize();
    
    /**
     * Gets the first item.
     *
     * @return the first item
     */
    int getFirstItem();
    
    /**
     * Gets the sort.
     *
     * @return the sort
     */
    Sort getSort();
}