package com.github.frajimiba.commonstruct.test.constraints;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConstraintsValidationTest {

  private static Validator validator;
  private Dummy dummy;
 
  @BeforeClass
  public static void setUpClass() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }
  
  @Before
  public void setUp() {
    dummy = new Dummy();
  }
  
  @Test
  public void nifIsNull() {
    this.dummy.setNif(null);
    Set<ConstraintViolation<Dummy>> constraintViolations = validator.validate(dummy);
    assertEquals("null nif", 0, constraintViolations.size());
  }
  
  @Test
  public void nifIsEmpty() {
    this.dummy.setNif("");
    Set<ConstraintViolation<Dummy>> constraintViolations = validator.validate(dummy);
    assertEquals("Empty nif", 0, constraintViolations.size());
  }
  
  @Test
  public void nifNotValid() {
    this.dummy.setNif("1111111H");
    Set<ConstraintViolation<Dummy>> constraintViolations = validator.validate(dummy);
    assertEquals("not valid :" + dummy.getNif() , 1, constraintViolations.size());
  }
  
  @Test
  public void nifValid() {
    this.dummy.setNif("1111111H");
    Set<ConstraintViolation<Dummy>> constraintViolations = validator.validate(dummy);
    assertEquals("valid :" + dummy.getNif(), 1, constraintViolations.size());
  }
  
  @Test
  public void specificationNotValid(){
    this.dummy.setEmail("test");
    Set<ConstraintViolation<Dummy>> constraintViolations = validator.validate(dummy);
    assertEquals("not valid :" + dummy.getEmail(), 1, constraintViolations.size());
  }
  
  @Test
  public void specificationValid(){
    this.dummy.setEmail("test@test.com");
    Set<ConstraintViolation<Dummy>> constraintViolations = validator.validate(dummy);
    assertEquals("valid :" + dummy.getEmail(), 0, constraintViolations.size());
  }
  
  
    
}
