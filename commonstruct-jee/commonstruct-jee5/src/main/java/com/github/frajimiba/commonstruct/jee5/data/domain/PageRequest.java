package com.github.frajimiba.commonstruct.jee5.data.domain;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The Class PageRequest.
 */
public class PageRequest implements Pageable {

    /** The page. */
    private int page;
    
    /** The size. */
    private int size;
    
    /** The sort. */
    private Sort sort;

    /**
     * Instantiates a new page request.
     *
     * @param page the page
     * @param size the size
     */
    public PageRequest(final int page, final int size) {

        if (0 > page) {
            throw new IllegalArgumentException(
                    "Page index must not be less than zero!");
        }

        if (0 >= size) {
            throw new IllegalArgumentException(
                    "Page size must not be less than or equal to zero!");
        }

        this.page = page;
        this.size = size;
    }

    /**
     * Instantiates a new page request.
     *
     * @param page the page
     * @param size the size
     * @param order the order
     * @param properties the properties
     */
    public PageRequest(final int page, final int size, Order order,
            String... properties) {

        this(page, size, new Sort(order, properties));
    }

    /**
     * Instantiates a new page request.
     *
     * @param page the page
     * @param size the size
     * @param sort the sort
     */
    public PageRequest(final int page, final int size, final Sort sort) {

        this(page, size);
        this.sort = sort;
    }

    /**
	 * {@inheritDoc}
	 */
    public int getPageSize() {

        return size;
    }

    /**
	 * {@inheritDoc}
	 */
    public int getPageNumber() {

        return page;
    }

    /**
	 * {@inheritDoc}
	 */
    public int getFirstItem() {

        return page * size;
    }


    /**
	 * {@inheritDoc}
	 */
    public Sort getSort() {
        return sort;
    }
    /**
	 * {@inheritDoc}
	 */
    @Override
    public boolean equals(final Object obj) {

    	boolean result = false;
    	
    	if (obj instanceof PageRequest) {
    		if (this == obj) {
    			result = true;
            }
    		else{
            	 PageRequest that = (PageRequest) obj;

                 boolean pageEqual = this.page == that.page;
                 boolean sizeEqual = this.size == that.size;

                 boolean sortEqual =
                         this.sort == null ? that.sort == null : this.sort
                                 .equals(that.sort);
                 result =  pageEqual && sizeEqual && sortEqual;
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
    	builder.append(page);
    	builder.append(size);
    	builder.append(null == sort ? 0 : sort);
  
        return builder.toHashCode();
    }
}

