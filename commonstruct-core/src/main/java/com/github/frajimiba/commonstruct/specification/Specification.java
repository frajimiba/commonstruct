package com.github.frajimiba.commonstruct.specification;

/**
 * Specification interface.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public interface Specification<T> {

  /**
   * Check if {@code candicate} is satisfied by the specification.
   *
   * @param candidate
   *          Object to test
   * @return {@code true} if {@code candidate} satisfies the specification
   */
  boolean isSatisfiedBy(T candidate);
}