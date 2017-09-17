package com.github.frajimiba.commonstruct.specification;

/**
 * Specification disjunction operation with multiple operands.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public final class DisjunctionSpecification<T> extends MultiOperandsSpecification<T> {

  /**
   * The multiple operands specification constructor.
   *
   * @param operands
   *          the group operands
   * @throws IllegalArgumentException
   *           if any operand are null
   */
  public DisjunctionSpecification(Specification<T>... operands) {
    super(operands);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(T candidate) {
    boolean result = false;
    for (Specification<T> specification : getOperands()) {
      if (specification.isSatisfiedBy(candidate)) {
        result = true;
      }
    }
    return result;
  }
}