package com.github.frajimiba.commonstruct.healthcheck;

import javax.sql.DataSource;

/**
 * The dataSource HealthCheck.
 * 
 * @author Francisco José Jiménez
 *
 */
public interface DataSourceHealthCheck extends HealthCheck {
  /**
   * The dataSource to check.
   * 
   * @return the datasource.
   */
  DataSource getDataSource();
}