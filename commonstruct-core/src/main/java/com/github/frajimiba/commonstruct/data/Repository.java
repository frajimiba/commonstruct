package com.github.frajimiba.commonstruct.data;

import java.io.Serializable;

import com.github.frajimiba.commonstruct.domain.Entity;

/**
 * An Repository.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Repository entity type
 * @param <I>
 *          Identity type of entity repository
 */
public interface Repository<T extends Entity<I>, I extends Serializable> {
	
}