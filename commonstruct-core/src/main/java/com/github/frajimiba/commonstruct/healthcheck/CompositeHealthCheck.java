package com.github.frajimiba.commonstruct.healthcheck;

import java.util.List;

/**
 * The composite health check definition.
 * Define the tree of helthCheck to checked
 * 
 * @author Francisco José Jiménez
 *
 */
public interface CompositeHealthCheck extends HealthCheck {
  /**
   * Add a HealthCheck to checked.
   * 
   * @param healthCheck the HealthCheck
   */
  void addHealthCheck(HealthCheck healthCheck);
  /**
   * The list of HealthCheck to checked.
   * 
   * @return The list of HealthCheck
   */
  List<HealthCheck> getHealthChecks();
}