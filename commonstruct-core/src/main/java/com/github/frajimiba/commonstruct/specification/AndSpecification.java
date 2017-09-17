package com.github.frajimiba.commonstruct.specification;

/**
 * Specification "and" operation.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public final class AndSpecification<T> extends BinarySpecification<T> {

  /**
   * The binary specification constructor.
   *
   * @param left
   *          left operand of the specification
   * @param right
   *          right operand of the specification
   * @throws IllegalArgumentException
   *           if left or right operand are null
   */
  public AndSpecification(Specification<T> left, Specification<T> right) {
    super(left, right);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(T candidate) {
    return this.leftIsSatisfied(candidate)  && super.rightIsSatisfied(candidate);
  }
}