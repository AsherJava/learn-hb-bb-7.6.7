/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.properties.FinancialCubesDataSourceProperties
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gc.financialcubes.common.dao.impl;

import com.jiuqi.common.financialcubes.properties.FinancialCubesDataSourceProperties;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.gc.financialcubes.common.dao.GcFinancialCubesBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class GcFinancialCubesBaseDaoImpl
implements GcFinancialCubesBaseDao {
    private IDbSqlHandler dbSqlHandler;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FinancialCubesDataSourceProperties dataSourceProperties;

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public IDbSqlHandler getDbSqlHandler() {
        if (this.dbSqlHandler == null) {
            this.dbSqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceProperties.getCurrentDbType());
        }
        return this.dbSqlHandler;
    }
}

