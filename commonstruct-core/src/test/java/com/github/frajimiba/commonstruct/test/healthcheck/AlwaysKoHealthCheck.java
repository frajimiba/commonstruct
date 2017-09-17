package com.github.frajimiba.commonstruct.test.healthcheck;

import com.github.frajimiba.commonstruct.healthcheck.HealthCheck;
import com.github.frajimiba.commonstruct.healthcheck.HealthCheckStatus;

public class AlwaysKoHealthCheck implements HealthCheck {

  @Override
  public HealthCheckStatus getStatus() {
    return HealthCheckStatus.KO;
  }

  @Override
  public String getName() {
    return "AlwaysKoHealthCheck";
  }

  @Override
  public void check() {
    
  }

}
