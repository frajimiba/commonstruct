package com.github.frajimiba.commonstruct.jee5.data.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import com.github.frajimiba.commonstruct.jee5.data.domain.Sort;
import com.github.frajimiba.commonstruct.jee5.data.domain.Sort.Property;


/**
 * The Class QueryUtil.
 */
public final class QueryUtil {

    /** The Constant COUNT_QUERY_STRING. */
    public static final String COUNT_QUERY_STRING = "select count(%s) from %s x";
    
    /** The Constant DELETE_ALL_QUERY_STRING. */
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x";
    
    /** The Constant READ_ALL_QUERY. */
    public static final String READ_ALL_QUERY = "select x from %s x";
    
    /** The Constant DEFAULT_ALIAS. */
    private static final String DEFAULT_ALIAS = "x";
    
    /** The Constant COUNT_REPLACEMENT. */
    private static final String COUNT_REPLACEMENT = "select count($3$5) $4$5$6";

    /** The Constant ALIAS_MATCH. */
    private static final Pattern ALIAS_MATCH;
    
    /** The Constant COUNT_MATCH. */
    private static final Pattern COUNT_MATCH;

    /** The Constant IDENTIFIER. */
    private static final String IDENTIFIER = "[\\p{L}._$]+";
    
    /** The Constant IDENTIFIER_GROUP. */
    private static final String IDENTIFIER_GROUP = String.format("(%s)",IDENTIFIER);

    static {

        StringBuilder builder = new StringBuilder();
        builder.append("(?<=from)"); // from as starting delimiter
        builder.append("(?:\\s)+"); // at least one space separating
        builder.append(IDENTIFIER_GROUP); // Entity name, can be qualified (any
        builder.append("(?:\\sas)*"); // exclude possible "as" keyword
        builder.append("(?:\\s)+"); // at least one space separating
        builder.append("(\\w*)"); // the actual alias

        ALIAS_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);

        builder = new StringBuilder();
        builder.append("(select\\s+((distinct )?.+?)\\s+)?(from\\s+");
        builder.append(IDENTIFIER);
        builder.append("(?:\\s+as)?\\s+)");
        builder.append(IDENTIFIER_GROUP);
        builder.append("(.*)");

        COUNT_MATCH = Pattern.compile(builder.toString(), Pattern.CASE_INSENSITIVE);
    }
    
    /**
     * Private constructor prevents construction outside of this class.
     */
    private QueryUtil() {

    }

    /**
     * Gets the query string.
     *
     * @param template the template
     * @param clazz the clazz
     * @return the query string
     */
    public static String getQueryString(String template, Class<?> clazz) {

        if (null == clazz) {
            throw new IllegalArgumentException("Class must not be null!");
        }

        Entity entity = clazz.getAnnotation(Entity.class);
        
        boolean hasName = null != entity &&  (entity.name() != null && entity.name().length()> 0);
        String clazzName =  hasName ? entity.name() : clazz.getSimpleName();
        
        return getQueryString(template,clazzName);
    }

    /**
     * Gets the query string.
     *
     * @param template the template
     * @param clazzName the clazz name
     * @return the query string
     */
    public static String getQueryString(String template, String clazzName) {
        return String.format(template, clazzName);
    }

    /**
     * Apply sorting.
     *
     * @param query the query
     * @param sort the sort
     * @return the string
     */
    public static String applySorting(String query, Sort sort) {
        return applySorting(query, sort, DEFAULT_ALIAS);
    }

    /**
     * Apply sorting.
     *
     * @param query the query
     * @param sort the sort
     * @param alias the alias
     * @return the string
     */
    public static String applySorting(String query, Sort sort, String alias) {

    	String result = query;
    	
        if (null != sort) {
	        StringBuilder builder = new StringBuilder(query);
	        builder.append(" order by");
	
	        for (Property property : sort) {
	            builder.append(String.format(" %s.", alias));
	            builder.append(property.getName());
	            builder.append(" ");
	            builder.append(property.getOrder().getJpaValue());
	            builder.append(",");
	        }
	
	        builder.deleteCharAt(builder.length() - 1);
	        result =  builder.toString();
        }
        
        return result;
    }

    /**
     * Detect alias.
     *
     * @param query the query
     * @return the string
     */
    public static String detectAlias(String query) {

        Matcher matcher = ALIAS_MATCH.matcher(query);

        return matcher.find() ? matcher.group(2) : null;
    }

    /**
     * Apply and bind.
     *
     * @param <T> the generic type
     * @param queryString the query string
     * @param entities the entities
     * @param entityManager the entity manager
     * @return the query
     */
    public static <T> Query applyAndBind(String queryString,
            Collection<T> entities, EntityManager entityManager) {

        Iterator<T> iterator = entities.iterator();

        Query result = null;
        if (!iterator.hasNext()) {
        	result =  entityManager.createQuery(queryString);
        }
        else{
        	 String alias = detectAlias(queryString);
             StringBuilder builder = new StringBuilder(queryString);
             builder.append(" where");

             for (int i = 0; i < entities.size(); i++) {
                 builder.append(String.format(" %s = ?%d", alias, i + 1));
                 if (i < entities.size() - 1) {
                     builder.append(" or");
                 }
             }

             Query query = entityManager.createQuery(builder.toString());
             
             for (int i = 0; i < entities.size(); i++) {
                 query.setParameter(i + 1, iterator.next());
             }
             
             result = query;
        }
        
        return result;       
    }

    /**
     * Creates the count query for.
     *
     * @param originalQuery the original query
     * @return the string
     */
    public static String createCountQueryFor(String originalQuery) {
        Matcher matcher = COUNT_MATCH.matcher(originalQuery);
        return matcher.replaceFirst(COUNT_REPLACEMENT);
    }
    
    
    /**
     * Gets the named query code.
     *
     * @param clazz the clazz
     * @param namedQueryKey the named query key
     * @return the named query code
     */
    public static String getNamedQueryCode(Class<? extends Object> clazz, String namedQueryKey) {
    	
    	String query = getNamedQueriesAnnotationCode(clazz,namedQueryKey);
    	
        NamedQuery namedQueryAnnotation = clazz.getAnnotation(NamedQuery.class);
        
        if (namedQueryAnnotation!=null){
        	if (namedQueryAnnotation.name().equals(namedQueryKey)) {
				query = namedQueryAnnotation.query();
        	}
        }
        
        if (query == null) {
            if (clazz.getSuperclass().getAnnotation(MappedSuperclass.class) != null) {
                query = getNamedQueryCode(clazz.getSuperclass(), namedQueryKey);
            }
        }

        return query;
    }
    
    private static String getNamedQueriesAnnotationCode(Class<? extends Object> clazz, String namedQueryKey) {
    	String query = null;
    	NamedQueries namedQueriesAnnotation = clazz.getAnnotation(NamedQueries.class);
        if (namedQueriesAnnotation!=null){
        	NamedQuery[] namedQueryAnnotations = namedQueriesAnnotation.value();
			boolean result = false;
			for (int i = 0; i < namedQueryAnnotations.length && result == false; i++) {
				NamedQuery namedQuery = namedQueryAnnotations[i];
				if (namedQuery.name().equals(namedQueryKey)) {
					query = namedQuery.query();
					result = true;
				}
			}
        }
        return query;
    }
    
}