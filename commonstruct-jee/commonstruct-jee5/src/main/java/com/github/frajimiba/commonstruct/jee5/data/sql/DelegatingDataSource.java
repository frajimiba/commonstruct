package com.github.frajimiba.commonstruct.jee5.data.sql;

import javax.sql.DataSource;

public interface DelegatingDataSource extends DataSource {
	DataSource getTargetDataSource();
}