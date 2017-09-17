package com.github.frajimiba.commonstruct.specification;

/**
 * Specification with multiple operands.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public abstract class MultiOperandsSpecification<T> extends CompositeSpecification<T> {

  /**
   * The group operands.
   */
  private final Specification<T>[] operands;

  /**
   * The multiple operands specification constructor.
   *
   * @param operands
   *          the group operands
   * @throws IllegalArgumentException
   *           if any operand are null
   */
  public MultiOperandsSpecification(Specification<T>... operands) {
    for (Specification<T> specification : operands) {
      if (specification == null) {
        throw new IllegalArgumentException();
      }
    }
    this.operands = operands;
  }

  /**
   * The specification operands.
   * 
   * @return the specification operands
   */
  public Specification<T>[] getOperands() {
    return this.operands.clone();
  }

}
