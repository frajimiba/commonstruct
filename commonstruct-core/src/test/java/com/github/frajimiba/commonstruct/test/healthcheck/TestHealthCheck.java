package com.github.frajimiba.commonstruct.test.healthcheck;

import com.github.frajimiba.commonstruct.healthcheck.AbstractCompositeHealthCheck;

public class TestHealthCheck extends AbstractCompositeHealthCheck {
  
  @Override
  public String getName() {
    return "TestHealthCheck";
  }

}
