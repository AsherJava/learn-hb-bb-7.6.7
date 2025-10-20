/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DCQueryJdbcUtils {
    private static final Logger logger = LoggerFactory.getLogger(DCQueryJdbcUtils.class);
    private static final String RELEASE_ERROR = "\u91ca\u653e\u8fde\u63a5\u5f02\u5e38";

    private DCQueryJdbcUtils() {
    }

    public static String getDatabaseProductName(Connection conn) {
        if (conn == null) {
            return null;
        }
        try {
            return conn.getMetaData().getDatabaseProductName();
        }
        catch (SQLException e) {
            throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u7c7b\u578b\u5931\u8d25\uff01", (Throwable)e);
        }
    }

    public static void colseResource(Connection conn, Statement st, ResultSet rs) {
        DCQueryJdbcUtils.closeResultSet(rs);
        DCQueryJdbcUtils.closeStatement(st);
        DCQueryJdbcUtils.closeConnection(conn);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                logger.warn(RELEASE_ERROR, e);
            }
        }
        conn = null;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            }
            catch (SQLException e) {
                logger.warn(RELEASE_ERROR, e);
            }
        }
        st = null;
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                logger.warn(RELEASE_ERROR, e);
            }
        }
        rs = null;
    }
}

