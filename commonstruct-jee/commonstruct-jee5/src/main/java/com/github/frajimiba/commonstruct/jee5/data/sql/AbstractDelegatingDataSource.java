package com.github.frajimiba.commonstruct.jee5.data.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

public abstract class AbstractDelegatingDataSource implements DelegatingDataSource {
   
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Connection getConnection() throws SQLException {
        return this.getTargetDataSource().getConnection();
    }

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Connection getConnection(String username, String password)
                    throws SQLException {
           return this.getTargetDataSource().getConnection(username, password);
    }

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PrintWriter getLogWriter() throws SQLException {
            return getTargetDataSource().getLogWriter();
    }

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void setLogWriter(PrintWriter out) throws SQLException {
            getTargetDataSource().setLogWriter(out);
    }

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int getLoginTimeout() throws SQLException {
            return getTargetDataSource().getLoginTimeout();
    }
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void setLoginTimeout(int seconds) throws SQLException {
            getTargetDataSource().setLoginTimeout(seconds);
    }
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public <T> T unwrap(Class<T> iface) throws SQLException {
            return getTargetDataSource().unwrap(iface);
    }
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return getTargetDataSource().isWrapperFor(iface);
    }

}