package com.github.frajimiba.commonstruct.test.specification;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.frajimiba.commonstruct.specification.AndSpecification;
import com.github.frajimiba.commonstruct.specification.CompositeSpecification;
import com.github.frajimiba.commonstruct.specification.ConjunctionSpecification;
import com.github.frajimiba.commonstruct.specification.DisjunctionSpecification;
import com.github.frajimiba.commonstruct.specification.InverseSpecification;
import com.github.frajimiba.commonstruct.specification.OrSpecification;
import com.github.frajimiba.commonstruct.specification.Specification;

public class SpecificationTest {

  class AlwaysFalseSpec implements Specification<Object> {

    public boolean isSatisfiedBy(Object candidate) {
      return false;
    }

  }

  class AlwaysTrueSpec implements Specification<Object> {

    public boolean isSatisfiedBy(Object candidate) {
      return true;
    }

  }

  @Test
  @SuppressWarnings("unchecked")
  public void argumentsTest() {

    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();

    Throwable e = null;

    try {
      new AndSpecification<Object>(trueSpec, null);
    } catch (IllegalArgumentException ex) {
      e = ex;
    }
    assertTrue("right null argument", e instanceof IllegalArgumentException);

    e = null;
    try {
      new AndSpecification<Object>(null, null);
    } catch (IllegalArgumentException ex) {
      e = ex;
    }
    assertTrue("two null argument", e instanceof IllegalArgumentException);

    e = null;
    try {
      new AndSpecification<Object>(null, trueSpec);
    } catch (IllegalArgumentException ex) {
      e = ex;
    }
    assertTrue("left null argument", e instanceof IllegalArgumentException);

    e = null;
    try {
      new InverseSpecification<Object>(null);
    } catch (IllegalArgumentException ex) {
      e = ex;
    }
    assertTrue("null argument", e instanceof IllegalArgumentException);

    e = null;
    try {
      new ConjunctionSpecification<Object>(trueSpec, trueSpec, null);
    } catch (IllegalArgumentException ex) {
      e = ex;
    }
    assertTrue("some null argument", e instanceof IllegalArgumentException);

  }

  @Test
  public void compositeTest() {
    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
    CompositeSpecification<Object> specification = new InverseSpecification<Object>(trueSpec);
    boolean result = specification.and(trueSpec).or(trueSpec).not().isSatisfiedBy(new Object());
    assertFalse("not((false and true) or true) = false", result);
  }

  @Test
  public void and() {
    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
    AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();
    AndSpecification<Object> andSpecification = null;

    andSpecification = new AndSpecification<Object>(trueSpec, trueSpec);
    assertTrue("(true and true)=true", andSpecification.isSatisfiedBy(new Object()));

    andSpecification = new AndSpecification<Object>(falseSpec, trueSpec);
    assertFalse("(false and true)=false", andSpecification.isSatisfiedBy(new Object()));

    andSpecification = new AndSpecification<Object>(trueSpec, falseSpec);
    assertFalse("(true and fase)=false", andSpecification.isSatisfiedBy(new Object()));

    andSpecification = new AndSpecification<Object>(falseSpec, falseSpec);
    assertFalse("(false and fase)=false", andSpecification.isSatisfiedBy(new Object()));
  }

  @Test
  public void or() {
    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
    AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

    OrSpecification<Object> orSpecification = new OrSpecification<Object>(trueSpec, trueSpec);
    assertTrue("(true or true)=true", orSpecification.isSatisfiedBy(new Object()));

    orSpecification = new OrSpecification<Object>(falseSpec, trueSpec);
    assertTrue("(false or true)=true", orSpecification.isSatisfiedBy(new Object()));

    orSpecification = new OrSpecification<Object>(trueSpec, falseSpec);
    assertTrue("(true or false)=true", orSpecification.isSatisfiedBy(new Object()));

    orSpecification = new OrSpecification<Object>(falseSpec, falseSpec);
    assertFalse("(false or false)=false", orSpecification.isSatisfiedBy(new Object()));
  }

  @Test
  public void not() {
    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
    AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

    InverseSpecification<Object> notSpecification = new InverseSpecification<Object>(trueSpec);
    assertFalse("(not true)=false", notSpecification.isSatisfiedBy(new Object()));

    notSpecification = new InverseSpecification<Object>(falseSpec);
    assertTrue("(not false)=true", notSpecification.isSatisfiedBy(new Object()));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void conjunction() {

    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
    AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

    ConjunctionSpecification<Object> conj = new ConjunctionSpecification<Object>(trueSpec, trueSpec, trueSpec);
    assertTrue("Conjunction(true,true,true) = true", conj.isSatisfiedBy(new Object()));

    conj = new ConjunctionSpecification<Object>(trueSpec, falseSpec, trueSpec);
    assertFalse("Conjunction(true,false,true) = false", conj.isSatisfiedBy(new Object()));

  }

  @Test
  @SuppressWarnings("unchecked")
  public void disjunction() {

    AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
    AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();

    DisjunctionSpecification<Object> conj = new DisjunctionSpecification<Object>(falseSpec, falseSpec, falseSpec);
    assertFalse("Disjunction(false,false,false) = false", conj.isSatisfiedBy(new Object()));

    conj = new DisjunctionSpecification<Object>(trueSpec, falseSpec, trueSpec);
    assertTrue("Disjunction(true,false,true) = true", conj.isSatisfiedBy(new Object()));

  }

}