package com.github.frajimiba.commonstruct.specification;

/**
 * Composite specification of basics logical operators.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public abstract class CompositeSpecification<T> implements Specification<T> {

  /**
   * Generates a "AndSpecification" on the left operand is himself.
   *
   * @param other
   *          the right operand of the specification
   * @return the specification
   */
  public AndSpecification<T> and(Specification<T> other) {
    return new AndSpecification<T>(this, other);
  }

  /**
   * Generates a "OrSpecification" on the left operand is himself.
   *
   * @param other
   *          the right operand of the specification
   * @return the specification
   */
  public OrSpecification<T> or(Specification<T> other) {
    return new OrSpecification<T>(this, other);
  }

  /**
   * Generates a "InverseSpecification" of himself.
   *
   * @return the specification
   */
  public InverseSpecification<T> not() {
    return new InverseSpecification<T>(this);
  }
}