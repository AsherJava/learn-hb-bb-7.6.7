/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.va.query.common.dao.impl;

import com.jiuqi.va.query.common.dao.UserDefinedBaseDao;
import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDefinedBaseDaoImpl
implements UserDefinedBaseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    @Override
    public void closeConnection(Connection conn) {
        DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
}

