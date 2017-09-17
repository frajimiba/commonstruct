package com.github.frajimiba.commonstruct.test.specification.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.frajimiba.commonstruct.specification.Specification;
import com.github.frajimiba.commonstruct.specification.common.CifSpecification;
import com.github.frajimiba.commonstruct.specification.common.NifSpecification;

public class StringSpecificationTest {
  @Test
  public void nifSpecificationTest() {

    Specification<String> validator = new NifSpecification();

    String validNif = "11111111H";
    String invalidNif = "1111111H";
    String validxNif = "X1111111G";
    String invalidxNif = "X1111111H";
    String specialCharsNif = "^+ñ:ñsf'000";

    assertTrue(validNif + " NIF es valido", validator.isSatisfiedBy(validNif));
    assertFalse(invalidNif + " NIF no es valido", validator.isSatisfiedBy(invalidNif));
    assertTrue(validxNif + " NIF es valido", validator.isSatisfiedBy(validxNif));
    assertFalse(specialCharsNif + " NIF no es valido", validator.isSatisfiedBy(specialCharsNif));
    assertFalse(invalidxNif + " NIF no es valido", validator.isSatisfiedBy(invalidxNif));
    assertFalse("NIF cadena vacia", validator.isSatisfiedBy(""));
    assertFalse("NIF nulo", validator.isSatisfiedBy(null));
  }

  @Test
  public void cifSpecificationTest() {
    Specification<String> validator = new CifSpecification();

    String validCif = "B60390192";
    String invalidCif = "K60390192";
    String validCifLetra = "Q2826011E";
    String invalidCifLetra = "Q2826011F";
    String specialCharsCif = "^+ñ:ñsf'000";

    assertTrue(validCif + " CIF is valid", validator.isSatisfiedBy(validCif));
    assertTrue(validCifLetra + " CIF is valid", validator.isSatisfiedBy(validCifLetra));
    assertFalse(invalidCif + " CIF is invalid", validator.isSatisfiedBy(invalidCif));
    assertFalse(invalidCifLetra + " CIF is invalid", validator.isSatisfiedBy(invalidCifLetra));
    assertFalse(specialCharsCif + " CIF no es valido", validator.isSatisfiedBy(specialCharsCif));
    assertFalse("CIF is empty", validator.isSatisfiedBy(""));
    assertFalse("CIF is null", validator.isSatisfiedBy(null));
  }
}
