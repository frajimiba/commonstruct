package com.github.frajimiba.commonstruct.jee5.data.repository;

import javax.persistence.Query;

/**
 * The Interface QueryExtractor.
 */
public interface QueryExtractor {
    
    /**
     * Extract query string.
     *
     * @param query the query
     * @return the string
     */
    String extractQueryString(Query query);
    
    /**
     * Can extract query.
     *
     * @return true, if successful
     */
    boolean canExtractQuery();
}