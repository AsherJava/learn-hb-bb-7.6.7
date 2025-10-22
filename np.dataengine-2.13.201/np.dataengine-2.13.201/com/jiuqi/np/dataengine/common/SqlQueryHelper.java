/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.DBResultSet;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlQueryHelper
implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(SqlQueryHelper.class);
    private PreparedStatement statement;

    public ResultSet executeQuery(Connection conn, String sql) throws SQLException {
        return this.executeQuery(conn, sql, null, null);
    }

    public ResultSet executeQuery(Connection conn, String sql, IMonitor monitor) throws SQLException {
        return this.executeQuery(conn, sql, null, monitor);
    }

    public ResultSet executeQuery(Connection conn, String sql, Object[] args) throws SQLException {
        return this.executeQuery(conn, sql, args, null);
    }

    public ResultSet executeQuery(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        try {
            this.sqlToLogger(sql, monitor);
            this.closeStatment();
            this.statement = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    this.setValue(args[i], this.statement, i);
                }
            }
            if (monitor == null || monitor.getDatabase() == null || !monitor.getDatabase().isDatabase("KINGBASE")) {
                this.statement.setFetchSize(100);
            }
            return this.statement.executeQuery();
        }
        catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("sql: ").append(sql).append("\n");
            msg.append("args: ").append(Arrays.toString(args)).append("\n");
            msg.append("operation: ").append("query").append("\n");
            msg.append(e.getMessage());
            logger.error(msg.toString(), e);
            throw new SQLException("\u6570\u636e\u67e5\u8be2\u51fa\u9519", e);
        }
    }

    public DBResultSet<QueryField> queryDBResultSet(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        ResultSet reset = null;
        try {
            this.sqlToLogger(sql, monitor);
            this.closeStatment();
            this.statement = conn.prepareStatement(sql);
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; ++i) {
                    this.setValue(args[i], this.statement, i);
                }
            }
            if (monitor == null || monitor.getDatabase() == null || !monitor.getDatabase().isDatabase("KINGBASE")) {
                this.statement.setFetchSize(100);
            }
            reset = this.statement.executeQuery();
            DBResultSet<QueryField> result = monitor != null ? new DBResultSet<QueryField>(reset, monitor.getDatabase(), null) : new DBResultSet(reset);
            return result;
        }
        catch (Exception e) {
            StringBuilder msg = new StringBuilder();
            msg.append("sql: ").append(sql).append("\n");
            msg.append("args: ").append(Arrays.toString(args)).append("\n");
            msg.append("operation: ").append("query").append("\n");
            msg.append(e.getMessage());
            logger.error(msg.toString(), e);
            throw new SQLException("\u6570\u636e\u67e5\u8be2\u51fa\u9519", e);
        }
    }

    private void sqlToLogger(String sql, IMonitor monitor) {
        if (monitor != null) {
            monitor.debug(sql, DataEngineConsts.DebugLogType.SQL);
        } else {
            logger.debug(sql);
        }
    }

    private void setValue(Object value, PreparedStatement prep, int i) throws SQLException {
        if (value instanceof UUID) {
            prep.setBytes(i + 1, Convert.toBytes((UUID)((UUID)value)));
        } else if (value instanceof Date) {
            Timestamp timestamp = new Timestamp(((Date)value).getTime());
            prep.setTimestamp(i + 1, timestamp);
        } else {
            prep.setObject(i + 1, value);
        }
    }

    public ResultSet executeQueryByScroll(Connection conn, String sql, Object[] args) throws SQLException {
        return this.executeQueryByScroll(conn, sql, args, null);
    }

    public ResultSet executeQueryByScroll(Connection conn, String sql, Object[] args, IMonitor monitor) throws SQLException {
        this.sqlToLogger(sql, monitor);
        this.closeStatment();
        this.statement = conn.prepareStatement(sql, 1004, 1007);
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                this.setValue(args[i], this.statement, i);
            }
        }
        this.statement.setFetchSize(100);
        return this.statement.executeQuery();
    }

    @Override
    public void close() throws Exception {
        this.closeStatment();
    }

    private void closeStatment() throws SQLException {
        if (this.statement != null) {
            this.statement.close();
            this.statement = null;
        }
    }
}

