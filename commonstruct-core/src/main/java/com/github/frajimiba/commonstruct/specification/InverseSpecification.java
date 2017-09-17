package com.github.frajimiba.commonstruct.specification;

/**
 * Specification inverse operation.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public final class InverseSpecification<T> extends UnarySpecification<T> {

  /**
   * Constructor with specification to evaluate.
   *
   * @param specification
   *          the specification to evaluate
   * @throws IllegalArgumentException
   *           if the operand is null
   */
  public InverseSpecification(Specification<T> specification) {
    super(specification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(T candidate) {
    return !(this.getSpecification().isSatisfiedBy(candidate));
  }
}
