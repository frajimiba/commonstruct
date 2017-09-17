package com.github.frajimiba.commonstruct.jee5.data.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The Class Sort.
 */
public class Sort implements Iterable<Sort.Property> {

	/** The Constant DEFAULT_ORDER. */
	public static final Order DEFAULT_ORDER = Order.ASCENDING;

	/** The properties. */
	private List<Property> properties;
	
	/** The order. */
	private Order order;

	/**
	 * Instantiates a new sort.
	 *
	 * @param properties the properties
	 */
	public Sort(List<Property> properties) {

		if (null == properties || properties.isEmpty()) {
			String message="You have to provide at least one sort property"
					+ " to sort by!";
			throw new IllegalArgumentException(message);
		}

		this.properties = properties;
	}

	/**
	 * Instantiates a new sort.
	 *
	 * @param properties the properties
	 */
	public Sort(String... properties) {

		this(DEFAULT_ORDER, properties);
	}

	/**
	 * Instantiates a new sort.
	 *
	 * @param order the order
	 * @param properties the properties
	 */
	public Sort(Order order, String... properties) {

		if (null == properties || 0 == properties.length) {
			throw new IllegalArgumentException(
					"You have to provide at least one property to sort by!");
		}

		this.properties = new ArrayList<Property>(properties.length);
		this.order = null == order ? DEFAULT_ORDER : order;

		for (String propertyName : properties) {

			this.properties.add(new Property(order, propertyName));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<Property> iterator() {
		return this.properties.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {

		boolean result = false;

		if (obj instanceof Sort) {
			if (this == obj) {
				result = true;
			} 
			else {
				Sort that = (Sort) obj;

				boolean orderEqual = this.order.equals(that.order);
				boolean propertiesEqual = this.properties
						.equals(that.properties);

				result = orderEqual && propertiesEqual;
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
		builder.append(order);
		builder.append(properties);

		return builder.toHashCode();
	}

	/**
	 * The Class Property.
	 */
	public static class Property {

		/** The order. */
		private Order order;
		
		/** The property. */
		private String property;

		/**
		 * Instantiates a new property.
		 *
		 * @param order the order
		 * @param property the property
		 */
		public Property(Order order, String property) {

			if (property == null || "".equals(property.trim())) {
				throw new IllegalArgumentException(
						"Property must not null or empty!");
			}

			this.order = order == null ? DEFAULT_ORDER : order;
			this.property = property;
		}

		/**
		 * Instantiates a new property.
		 *
		 * @param property the property
		 */
		public Property(String property) {
			this(DEFAULT_ORDER, property);
		}

		/**
		 * Gets the order.
		 *
		 * @return the order
		 */
		public Order getOrder() {
			return order;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return property;
		}

		/**
		 * Checks if is ascending.
		 *
		 * @return true, if is ascending
		 */
		public boolean isAscending() {
			return this.order.equals(Order.ASCENDING);
		}

		/**
		 * With.
		 *
		 * @param order the order
		 * @return the property
		 */
		public Property with(Order order) {
			return new Property(order, this.property);
		}

		/**
		 * With properties.
		 *
		 * @param properties the properties
		 * @return the sort
		 */
		public Sort withProperties(String... properties) {
			return new Sort(this.order, properties);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {

			HashCodeBuilder builder = new HashCodeBuilder();
			builder.append(order);
			builder.append(property);

			return builder.toHashCode();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {

			boolean result = false;

			if (obj instanceof Property) {
				if (this == obj) {
					result = true;
				} 
				else {
					Property that = (Property) obj;
					result = this.order.equals(that.order)
							&& this.property.equals(that.property);
				}
			}

			return result;
		}
	}
}