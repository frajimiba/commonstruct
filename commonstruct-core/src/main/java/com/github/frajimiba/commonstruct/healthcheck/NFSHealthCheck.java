package com.github.frajimiba.commonstruct.healthcheck;

import com.github.frajimiba.commonstruct.io.ApplicationPath;

/**
 * The NFS HealthCheck
 * 
 * @author Francisco José Jiménez
 *
 */
public interface NFSHealthCheck extends HealthCheck {
  /**
   * The applicationPath to check.
   * 
   * @return the application path.
   */
  ApplicationPath getApplicationPath();
}