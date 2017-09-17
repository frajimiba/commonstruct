package com.github.frajimiba.commonstruct.test.constraints;

import com.github.frajimiba.commonstruct.specification.common.EmailSpecification;
import com.github.frajimiba.commonstruct.validation.Nif;
import com.github.frajimiba.commonstruct.validation.SatisfiedBy;


public class Dummy {

  @Nif
  private String nif;
  @SatisfiedBy(EmailSpecification.class)
  private String email;
  
  public Dummy() {
   
  }

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
}
