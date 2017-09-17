package com.github.frajimiba.commonstruct.test.healthcheck;

import java.util.Properties;
import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSourceFactory;

import com.github.frajimiba.commonstruct.healthcheck.AbstractDatasourceHealthCheck;

public class OkDataSourceHealthCheck extends AbstractDatasourceHealthCheck {

  @Override
  public DataSource getDataSource() {
    DataSource datasource=null;
    
    Properties properties = new Properties();
    properties.put("url", "jdbc:hsqldb:mem:dbtest");
    properties.put("user", "sa");
    properties.put("password", "");
    
    try {
      datasource = JDBCDataSourceFactory.createDataSource(properties);
    } catch (Exception e) {
      datasource = null;
    }
    
    return datasource;
  }

  @Override
  public String getName() {
    return "TestDataSourceHealthCheck";
  }

}
