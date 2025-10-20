/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.jdbc.sqlhandler;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Db2SqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.GaussSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.HanaSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.HighGoSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.Kingbase8SqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.MySQLSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OpenGaussSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OracleSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.impl.OscarSqlHandler;
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
        if ("gauss".equalsIgnoreCase(dbType)) {
            return new GaussSqlHandler();
        }
        if ("sql_server".equalsIgnoreCase(dbType)) {
            return new SqlServerSqlHandler();
        }
        if ("polardb".equalsIgnoreCase(dbType)) {
            return new PolarDBSqlHandler();
        }
        if ("postgresql".equalsIgnoreCase(dbType)) {
            return new PostgreSQLSqlHandler();
        }
        if ("db2".equalsIgnoreCase(dbType)) {
            return new Db2SqlHandler();
        }
        if ("kingbase".equalsIgnoreCase(dbType)) {
            return new Kingbase8SqlHandler();
        }
        if ("uxdb".equalsIgnoreCase(dbType)) {
            return new UxDBSqlHandler();
        }
        if ("oscar".equalsIgnoreCase(dbType)) {
            return new OscarSqlHandler();
        }
        if ("highgo".equalsIgnoreCase(dbType)) {
            return new HighGoSqlHandler();
        }
        if ("opengauss".equalsIgnoreCase(dbType)) {
            return new OpenGaussSqlHandler();
        }
        return new OracleSqlHandler();
    }
}

