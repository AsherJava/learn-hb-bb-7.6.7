/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.dc.base.common.jdbc.datasource;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.sql.Connection;
import org.springframework.jdbc.core.JdbcTemplate;

public interface IDynamicDataSourceProvider {
    public Connection getConnection();

    public void closeConnection(Connection var1);

    public JdbcTemplate getJdbcTemplate();

    public IDbSqlHandler getDbSqlHandler();
}

