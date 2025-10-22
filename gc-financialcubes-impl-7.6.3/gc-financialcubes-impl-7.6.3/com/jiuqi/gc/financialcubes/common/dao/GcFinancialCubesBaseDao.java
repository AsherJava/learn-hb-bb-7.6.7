/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gc.financialcubes.common.dao;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import org.springframework.jdbc.core.JdbcTemplate;

public interface GcFinancialCubesBaseDao {
    public JdbcTemplate getJdbcTemplate();

    public IDbSqlHandler getDbSqlHandler();
}

