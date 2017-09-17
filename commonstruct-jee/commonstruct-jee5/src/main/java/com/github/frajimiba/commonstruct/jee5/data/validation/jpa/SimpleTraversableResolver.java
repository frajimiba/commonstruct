package com.github.frajimiba.commonstruct.jee5.data.validation.jpa;

import java.lang.annotation.ElementType;

import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;

/**
 * Traversable resolver that does always resolve.
 * 
 * @author Francisco José Jiménez
 *
 */
public class SimpleTraversableResolver implements TraversableResolver {

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean isReachable(Object traversableObject,
			Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, ElementType elementType) {
		
		return true;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean isCascadable(Object traversableObject,
			Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, ElementType elementType) {
		
		return true;
	}
	
}