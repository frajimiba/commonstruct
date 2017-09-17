package com.github.frajimiba.commonstruct.healthcheck;


/**
 * The healthChek.
 * 
 * A healthChek must have a name, 
 * a check algorithm and the resulting status of implementation of the check algorithm.
 * 
 * @author Francisco José Jiménez
 *
 */
public interface HealthCheck {
  /**
   * Return the status of check algorithm
   * 
   * @return The status of check algorithm.
   */
  HealthCheckStatus getStatus();
  /**
   * Return the name of this healthCheck.
   * 
   * @return The name
   */
  String getName();
  /**
   * This method has to define the status to be displayed in the getStatus() method.
   * 
   * The check algorithm.
   */
  void check();
}