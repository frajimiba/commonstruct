package com.github.frajimiba.commonstruct.specification;

/**
 * Specification binary operation.
 *
 * @author Francisco José Jiménez
 *
 * @param <T>
 *          Specification entity type
 */
public abstract class BinarySpecification<T> extends CompositeSpecification<T> {

  /**
   * Left operand of the specification.
   */
  private final Specification<T> left;
  /**
   * Right operand of the specification.
   */
  private final Specification<T> right;

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
  protected BinarySpecification(Specification<T> left, Specification<T> right) {

    if (left == null || right == null) {
      throw new IllegalArgumentException();
    }

    this.left = left;
    this.right = right;
  }

  protected boolean leftIsSatisfied(T candidate){
    return this.left.isSatisfiedBy(candidate);
  }
    
  protected boolean rightIsSatisfied(T candidate){
    return this.right.isSatisfiedBy(candidate);
  }
  
  
}