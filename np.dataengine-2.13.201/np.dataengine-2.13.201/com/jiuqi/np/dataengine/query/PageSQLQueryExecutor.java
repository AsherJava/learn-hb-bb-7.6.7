/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.SQLQueryContext
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.sql.SQLQueryContext;
import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.intf.IMonitor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageSQLQueryExecutor
extends SQLQueryExecutor
implements AutoCloseable {
    private IMonitor monitor;
    private static final Logger logger = LoggerFactory.getLogger(PageSQLQueryExecutor.class);
    private String sql;

    public PageSQLQueryExecutor(Connection connection, IMonitor monitor, SQLQueryContext context) {
        super(connection, context);
        this.monitor = monitor;
    }

    public PageSQLQueryExecutor(Connection connection, IMonitor monitor) {
        super(connection);
        this.monitor = monitor;
    }

    public PageSQLQueryExecutor(Connection connection) {
        super(connection);
    }

    protected void logDebug(String msg) {
        if (this.monitor != null) {
            this.monitor.debug(msg, DataEngineConsts.DebugLogType.SQL);
        } else {
            logger.debug(msg);
        }
    }

    public ResultSet executeQuery(String sql) throws SQLQueryException {
        this.sql = sql;
        return super.executeQuery(sql);
    }

    protected void logError(String msg, Throwable e) {
        if (this.monitor != null) {
            this.monitor.exception(new SQLException(msg, e));
        } else {
            logger.error(msg, e);
        }
    }

    public String getSql() {
        return this.sql;
    }
}

