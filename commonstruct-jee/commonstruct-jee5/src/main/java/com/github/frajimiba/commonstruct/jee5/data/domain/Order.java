package com.github.frajimiba.commonstruct.jee5.data.domain;

/**
 * The Enum Order.
 */
public enum Order {

    /** The ascending. */
    ASCENDING("asc"), 
    /** The descending. */
    DESCENDING("desc");

    /** The jpa value. */
    private String jpaValue;


    /**
     * Creates a new instance of {@code Order}.
     *
     * @param jpaValue the jpa value
     */
    private Order(String jpaValue) {

        this.jpaValue = jpaValue;
    }


    /**
     * Returns the JPA specific value.
     * 
     * @return the JPA specific value
     */
    public String getJpaValue() {

        return jpaValue;
    }


    /**
     * Returns the {@link Order} enum for the given JPA value.
     *
     * @param value the value
     * @return the order
     */
    public static Order fromJpaValue(String value) {

        for (Order order : Order.values()) {
            if (order.getJpaValue().equalsIgnoreCase(value)) {
                return order;
            }
        }

        throw new IllegalArgumentException(
                String.format(
                        "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc'.",
                        value));
    }
}
