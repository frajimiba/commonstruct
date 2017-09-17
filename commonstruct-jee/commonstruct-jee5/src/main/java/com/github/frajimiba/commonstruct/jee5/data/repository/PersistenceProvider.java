package com.github.frajimiba.commonstruct.jee5.data.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.ejb.HibernateQuery;

/**
 * The Enum PersistenceProvider.
 */
public enum PersistenceProvider implements QueryExtractor {

    /**
     * Hibernate persistence provider.
     */
    HIBERNATE("org.hibernate.ejb.HibernateEntityManager") {

    	/**
    	 * {@inheritDoc}
    	 */
        public String extractQueryString(Query query) {
            return ((HibernateQuery) query).getHibernateQuery()
                    .getQueryString();
        }
        /**
    	 * {@inheritDoc}
    	 */
        @Override
        protected String getCountQueryPlaceholder() {
            return "*";
        }
    },

    /**
     * Unknown special provider. Use standard JPA.
     */
    GENERIC_JPA("javax.persistence.EntityManager") {
    	/**
    	 * {@inheritDoc}
    	 */
    	public String extractQueryString(Query query) {
            return null;
        }
    	/**
    	 * {@inheritDoc}
    	 */
        @Override
        public boolean canExtractQuery() {
            return false;
        }
    };

    /** The entity manager class name. */
    private String entityManagerClassName;


    /**
     * Creates a new {@link PersistenceProvider}.
     * 
     * @param entityManagerClassName the name of the provider specific
     *            {@link EntityManager} implementation
     */
    private PersistenceProvider(String entityManagerClassName) {

        this.entityManagerClassName = entityManagerClassName;
    }


    /**
     * Determines the {@link PersistenceProvider} from the given
     * {@link EntityManager}. If no special one can be determined
     * {@value #GENERIC_JPA} will be returned.
     *
     * @param em the em
     * @return the persistence provider
     */
    public static PersistenceProvider fromEntityManager(EntityManager em) {

    	PersistenceProvider result = null;
    	
    	for (int i = 0 ; i<values().length && result==null; i++){
    		PersistenceProvider provider = values()[i];
            if (isEntityManagerOfType(em,
                    provider.entityManagerClassName)) {
            	result = provider;
            }
        }

        return (result!=null)?result:GENERIC_JPA;
    }
    
    /**
     * Checks if is entity manager of type.
     *
     * @param em the em
     * @param type the type
     * @return true, if is entity manager of type
     */
    @SuppressWarnings("unchecked")
	private static boolean isEntityManagerOfType(EntityManager em, String type) {

    	boolean result = true;
    	
        try {
            Class<? extends EntityManager> emType =
            		(Class<? extends EntityManager>) Class.forName(type);
            emType.cast(em);
        } 
        catch (ClassNotFoundException e) {
        	result = false;
        } 
        catch (ClassCastException e) {
        	result = false;
        } 
        catch (NoClassDefFoundError e) {
        	result = false;
        }
        
        return result;
    }


    /**
	 * {@inheritDoc}
	 */
    public boolean canExtractQuery() {
        return true;
    }

    /**
     * Gets the count query placeholder.
     *
     * @return the count query placeholder
     */
    protected String getCountQueryPlaceholder() {
        return "x";
    }
}