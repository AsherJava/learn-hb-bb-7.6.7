/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.common.base.datasource;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class GcBizJdbcTemplate
extends JdbcTemplate {
    private String dbType;

    public GcBizJdbcTemplate() {
    }

    public GcBizJdbcTemplate(DataSource dataSource, String dbType) {
        super(dataSource);
        this.dbType = dbType;
    }

    public IDbSqlHandler getIDbSqlHandler() {
        return SqlHandlerUtil.getDbSqlHandler(this.dbType);
    }

    public String getDbType() {
        return this.dbType;
    }
}

