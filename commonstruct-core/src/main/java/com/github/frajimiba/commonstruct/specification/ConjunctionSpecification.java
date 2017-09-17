package com.github.frajimiba.commonstruct.specification;

/**
 * Specification conjunction operation with multiple operands.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public final class ConjunctionSpecification<T> extends MultiOperandsSpecification<T> {

  /**
   * The multiple operands specification constructor.
   *
   * @param operands
   *          the group operands
   * @throws IllegalArgumentException
   *           if any operand are null
   */
  public ConjunctionSpecification(Specification<T>... operands) {
    super(operands);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(T candidate) {
    boolean result = true;
    for (Specification<T> specification : getOperands()) {
      if (!specification.isSatisfiedBy(candidate)) {
        result = false;
      }
    }
    return result;
  }
}