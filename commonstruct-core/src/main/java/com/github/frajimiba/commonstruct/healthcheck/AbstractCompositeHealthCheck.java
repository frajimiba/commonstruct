package com.github.frajimiba.commonstruct.healthcheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Common composite health check.
 * Define the tree of helthCheck to checked
 * 
 * @author Francisco José Jiménez
 *
 */
public abstract class AbstractCompositeHealthCheck implements CompositeHealthCheck {

  /**
   * The childs healthChecks for this healthCheck.
   */
  private final List<HealthCheck> healthChecks = new ArrayList<HealthCheck>();
  /**
   * The status for his healthCheck.
   */
  private HealthCheckStatus status;
  /**
   * {@inheritDoc}
   */
  @Override
  public void check() {
    this.status = HealthCheckStatus.KO;
    boolean hasKO = false;
    
    for (HealthCheck checker : healthChecks) {
      checker.check();
      if (checker.getStatus() == HealthCheckStatus.KO) {
        hasKO = true;
      }
    }

    if (!hasKO) {
      this.status = HealthCheckStatus.OK;
    }
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public HealthCheckStatus getStatus() {
    return this.status;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void addHealthCheck(HealthCheck healthCheck) {
    this.healthChecks.add(healthCheck);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<HealthCheck> getHealthChecks() {
    return this.healthChecks;
  }

}