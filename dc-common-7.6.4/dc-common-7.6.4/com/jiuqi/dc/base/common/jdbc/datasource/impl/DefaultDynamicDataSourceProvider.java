/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.dc.base.common.jdbc.datasource.impl;

import com.jiuqi.dc.base.common.jdbc.datasource.IDynamicDataSourceProvider;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DefaultDynamicDataSourceProvider
implements IDynamicDataSourceProvider {
    private JdbcTemplate jdbcTemplate;
    private IDbSqlHandler dbSqlHandler;

    public DefaultDynamicDataSourceProvider(JdbcTemplate jdbcTemplate, IDbSqlHandler dbSqlHandler) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbSqlHandler = dbSqlHandler;
    }

    @Override
    public Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    @Override
    public void closeConnection(Connection conn) {
        DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public IDbSqlHandler getDbSqlHandler() {
        return this.dbSqlHandler;
    }
}

