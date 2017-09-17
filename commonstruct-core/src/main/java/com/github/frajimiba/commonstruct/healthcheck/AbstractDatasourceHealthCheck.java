package com.github.frajimiba.commonstruct.healthcheck;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Common DataSource HealthCheck.
 * 
 * @author Francisco José Jiménez
 *
 */
public abstract class AbstractDatasourceHealthCheck implements DataSourceHealthCheck {

  private static final Logger LOG = Logger.getLogger(AbstractDatasourceHealthCheck.class.getName());

  private HealthCheckStatus status;
  /**
   * {@inheritDoc}
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  public void check() {
    this.status = HealthCheckStatus.KO;
    try {
      Connection connection = this.getDataSource().getConnection();
      this.status = HealthCheckStatus.OK;
      connection.close();
    } catch (SQLException ex) {
      LOG.log(Level.SEVERE, "ERROR AL CHECKEAR DATASOURCE", ex);
    }
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public HealthCheckStatus getStatus() {
    return this.status;
  }

}
