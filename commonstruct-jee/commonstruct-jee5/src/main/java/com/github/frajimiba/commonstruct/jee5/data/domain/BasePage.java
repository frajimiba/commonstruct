package com.github.frajimiba.commonstruct.jee5.data.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * The Class BasePage.
 *
 * @param <T> the generic type
 */
public class BasePage<T> implements Page<T> {

    /** The content. */
    private List<T> content = new ArrayList<T>();
    
    /** The pageable. */
    private Pageable pageable;
    
    /** The total. */
    private long total;

    /**
     * Instantiates a new base page.
     *
     * @param content the content
     * @param pageable the pageable
     * @param total the total
     */
    public BasePage(final List<T> content, final Pageable pageable,
            final long total) {

        if (null == content) {
            throw new IllegalArgumentException("Content must not be null!");
        }

        this.content.addAll(content);
        this.total = total;

        this.pageable =
                null == pageable ? new PageRequest(0, content.size())
                        : pageable;
    }

    /**
     * Instantiates a new base page.
     *
     * @param content the content
     */
    public BasePage(final List<T> content) {

        this(content, null, (null == content) ? 0 : content.size());
    }

    /**
	 * {@inheritDoc}
	 */
    public int getNumber() {

        return pageable.getPageNumber();
    }

    /**
	 * {@inheritDoc}
	 */
    public int getSize() {

        return pageable.getPageSize();
    }

    /**
	 * {@inheritDoc}
	 */
    public int getTotalPages() {

        return (int) Math.ceil((double) total / (double) getSize());
    }

    /**
	 * {@inheritDoc}
	 */
    public int getNumberOfElements() {

        return content.size();
    }

    /**
	 * {@inheritDoc}
	 */
    public long getTotalElements() {
        return total;
    }

    /**
	 * {@inheritDoc}
	 */
    public boolean hasPreviousPage() {
        return getNumber() > 0;
    }

    /**
	 * {@inheritDoc}
	 */
    public boolean hasNextPage() {

        return ((getNumber() + 1) * getSize()) < total;
    }

    /**
	 * {@inheritDoc}
	 */
    public Iterator<T> iterator() {

        return content.iterator();
    }

    /**
	 * {@inheritDoc}
	 */
    public List<T> asList() {

        return Collections.unmodifiableList(content);
    }

    /**
	 * {@inheritDoc}
	 */
    public Sort getSort() {
        return pageable.getSort();
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public String toString() {

        String contentType = "UNKNOWN";

        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances",
                getNumber(), getTotalPages(), contentType);
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public boolean equals(Object obj) {

    	boolean result = false;

    	if (obj instanceof BasePage<?>){
    		if (this == obj) {
                result =  true;
            }
            else{
            	BasePage<?> that = (BasePage<?>) obj;

                boolean totalEqual = this.total == that.total;
                boolean contentEqual = this.content.equals(that.content);
                boolean pageableEqual = this.pageable.equals(that.pageable);
                
                result = totalEqual && contentEqual && pageableEqual;
            }
    	}
    	
        return result;
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public int hashCode() {

    	HashCodeBuilder builder = new HashCodeBuilder();
    	builder.append(total);
    	builder.append(pageable);
    	builder.append(content);
  
        return builder.toHashCode();

    }
}