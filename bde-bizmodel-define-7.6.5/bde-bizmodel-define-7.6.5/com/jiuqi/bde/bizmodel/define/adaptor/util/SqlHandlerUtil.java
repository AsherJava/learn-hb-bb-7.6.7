/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.GaussSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.HanaSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Kingbase8SqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.MySQLSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OpenGaussSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OracleSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PolarDBSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.SqlServerSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.UxDBSqlHandler
 */
package com.jiuqi.bde.bizmodel.define.adaptor.util;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.GaussSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.HanaSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Kingbase8SqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.MySQLSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OpenGaussSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OracleSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PolarDBSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.PostgreSQLSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.SqlServerSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.UxDBSqlHandler;

public class SqlHandlerUtil {
    public static IDbSqlHandler getDbSqlHandler(String dbType) {
        if ("mysql".equalsIgnoreCase(dbType)) {
            return new MySQLSqlHandler();
        }
        if ("oracle".equalsIgnoreCase(dbType)) {
            return new OracleSqlHandler();
        }
        if ("dameng".equalsIgnoreCase(dbType)) {
            return new OracleSqlHandler();
        }
        if ("hana".equalsIgnoreCase(dbType)) {
            return new HanaSqlHandler();
        }
        if ("postgresql".equalsIgnoreCase(dbType)) {
            return new PostgreSQLSqlHandler();
        }
        if ("uxdb".equalsIgnoreCase(dbType)) {
            return new UxDBSqlHandler();
        }
        if ("polardb".equalsIgnoreCase(dbType)) {
            return new PolarDBSqlHandler();
        }
        if ("gauss".equalsIgnoreCase(dbType)) {
            return new GaussSqlHandler();
        }
        if ("sql_server".equalsIgnoreCase(dbType)) {
            return new SqlServerSqlHandler();
        }
        if ("kingbase".equalsIgnoreCase(dbType)) {
            return new Kingbase8SqlHandler();
        }
        if ("opengauss".equalsIgnoreCase(dbType)) {
            return new OpenGaussSqlHandler();
        }
        return new OracleSqlHandler();
    }
}

