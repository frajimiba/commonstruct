package com.github.frajimiba.commonstruct.specification;

/**
 * Specification unary operation.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public abstract class UnarySpecification<T> extends CompositeSpecification<T> {

  /**
   * The specification to evaluate.
   */
  private Specification<T> specification;

  /**
   * Constructor with specification to evaluate.
   *
   * @param specification
   *          the specification to evaluate
   * @throws IllegalArgumentException
   *           if the operand is null
   */
  protected UnarySpecification(Specification<T> specification) {
    if (specification == null) {
      throw new IllegalArgumentException();
    } else {
      this.specification = specification;
    }
  }

  /**
   * Get the specification to evaluate.
   *
   * @return the specification to evaluate
   */
  protected Specification<T> getSpecification() {
    return this.specification;
  }
}
