/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.paging.OrderField
 *  com.jiuqi.bi.database.paging.UnsupportPagingException
 *  com.jiuqi.bi.util.Html
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.defaultlog.LogItem;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.bi.core.jobs.extension.LogType;
import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.bi.database.paging.UnsupportPagingException;
import com.jiuqi.bi.util.Html;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogDAO {
    private static Logger logger = LoggerFactory.getLogger(DefaultLogDAO.class);
    private static final String TABLE_LOGS = "BI_JOBS_LOGS";
    private static final String FIELD_LOGS_ID = "L_ID";
    private static final String FIELD_LOGS_TIMESTAMP = "L_TIMESTAMP";
    private static final String FIELD_LOGS_LEVEL = "L_LEVEL";
    private static final String FIELD_LOGS_MESSAGE = "L_MESSAGE";
    private static final String FIELD_LOGS_HASDETAIL = "L_HASDETAIL";
    private static final String FIELD_LOGS_INSTANCEID = "L_INSTANCEID";
    private static final String FIELD_LOGS_NODENAME = "L_NODENAME";
    private static final String TABLE_LOGDETAIL = "BI_JOBS_LOGDETAIL";
    private static final String FIELD_LOGDETAIL_ID = "L_ID";
    private static final String FIELD_LOGDETAIL_DETAIL = "L_DETAIL";
    private static final String SQL_INSERT_LOG = "INSERT INTO BI_JOBS_LOGS (L_ID, L_TIMESTAMP, L_NODENAME, L_LEVEL, L_MESSAGE, L_HASDETAIL, L_INSTANCEID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_LOGDETAIL = "INSERT INTO BI_JOBS_LOGDETAIL (L_ID, L_DETAIL) VALUES (?, ?)";
    private static final String SELECT_LOGS_BY_CONDITION = " SELECT T1.*, T2.L_DETAIL FROM BI_JOBS_LOGS T1  LEFT JOIN BI_JOBS_LOGDETAIL T2  ON T1.L_ID = T2.L_ID WHERE L_INSTANCEID IN ( ?INSTANCEIDS ) ";
    private static final String SELECT_LOG = " SELECT * FROM BI_JOBS_LOGS WHERE L_ID = ? ";
    private static final String SELECT_LOG_DETAIL = " SELECT * FROM BI_JOBS_LOGDETAIL WHERE L_ID = ? ";
    private static final String SELECT_LOGS = " SELECT * FROM BI_JOBS_LOGS T1  LEFT JOIN BI_JOBS_LOGDETAIL T2  ON T1.L_ID = T2.L_ID WHERE L_INSTANCEID IN ?INSTANCEIDS ";
    private static final String SQL_DELETE_BY_GUID = " DELETE FROM BI_JOBS_LOGS WHERE L_INSTANCEID IN ( ?IDS ) ";
    private static final String SQL_DELETE_DETAIL_BY_GUID = " DELETE FROM BI_JOBS_LOGDETAIL WHERE L_ID IN (SELECT L_ID FROM BI_JOBS_LOGS WHERE L_INSTANCEID IN ( ?IDS )  ) ";
    private static final String SQL_DELETE_NO_EXIST_IN_INSTANCE_TABLE = "SELECT L_ID FROM ( SELECT R.L_ID,I.BJI_INSTANCEID SOURCE_ID FROM BI_JOBS_LOGS R LEFT JOIN BI_JOBS_INSTANCES I ON R.L_INSTANCEID = I.BJI_INSTANCEID WHERE I.BJI_INSTANCEID IS NULL ) T ";

    public static void insertLog(Connection conn, LogItem item) throws SQLException {
        ArrayList<LogItem> items = new ArrayList<LogItem>();
        items.add(item);
        DefaultLogDAO.insertLogs(conn, items);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void insertLogs(Connection conn, List<LogItem> items) throws SQLException {
        if (items == null || items.isEmpty()) {
            return;
        }
        ArrayList<LogItem> detailItems = new ArrayList<LogItem>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_LOG);){
            for (LogItem item : items) {
                stmt.setLong(1, item.getId());
                stmt.setLong(2, item.getTimestamp());
                stmt.setString(3, item.getNodeName());
                stmt.setInt(4, item.getLevel());
                stmt.setString(5, item.getMessage());
                stmt.setInt(6, item.isHasDetail() ? 1 : 0);
                stmt.setString(7, item.getInstanceId());
                stmt.addBatch();
                if (!item.isHasDetail()) continue;
                detailItems.add(item);
            }
            stmt.executeBatch();
        }
        if (detailItems.isEmpty()) {
            return;
        }
        stmt = conn.prepareStatement(SQL_INSERT_LOGDETAIL);
        try {
            for (LogItem item : detailItems) {
                stmt.setLong(1, item.getId());
                stmt.setBytes(2, item.getDetail().getBytes(StandardCharsets.UTF_8));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        finally {
            stmt.close();
        }
    }

    public static List<LogItem> getLogsByCondition(Connection conn, List<String> instanceIds, Long startTimeStamp, Long endTimeStamp, LogType logType, Integer total) throws SQLException {
        ArrayList<LogItem> logItems = new ArrayList<LogItem>();
        if (instanceIds == null || instanceIds.isEmpty()) {
            return logItems;
        }
        StringBuilder instanceIdsBuilder = new StringBuilder();
        for (int i = 0; i < instanceIds.size(); ++i) {
            if (i > 0) {
                instanceIdsBuilder.append(",");
            }
            String str = instanceIds.get(i);
            str = Html.cleanName((String)str, (char[])new char[0]);
            instanceIdsBuilder.append("'").append(str).append("'");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(String.format(SELECT_LOGS_BY_CONDITION.replace("?INSTANCEIDS", "%s"), instanceIdsBuilder.toString()));
        if (startTimeStamp != null) {
            sqlBuilder.append(" AND ").append(FIELD_LOGS_TIMESTAMP).append(" > ").append(startTimeStamp);
        }
        if (endTimeStamp != null) {
            sqlBuilder.append(" AND ").append(FIELD_LOGS_TIMESTAMP).append(" < ").append(endTimeStamp);
        }
        if (logType != null) {
            sqlBuilder.append(" AND ").append("L_LEVEL >= ").append(logType.getValue());
        }
        try {
            IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            IPagingSQLBuilder pageBuilder = db.createPagingSQLBuilder();
            OrderField orderField = new OrderField(FIELD_LOGS_TIMESTAMP);
            if (startTimeStamp != null && total != null) {
                orderField.setOrderMode("ASC");
            } else {
                orderField.setOrderMode(" DESC ");
            }
            pageBuilder.getOrderFields().add(orderField);
            try {
                String buildSQL = sqlBuilder.toString();
                if (total == null) {
                    total = 100000000;
                }
                pageBuilder.setRawSQL(buildSQL);
                buildSQL = pageBuilder.buildSQL(0, total.intValue());
                try (PreparedStatement stmt = conn.prepareStatement(buildSQL);
                     ResultSet rs = stmt.executeQuery();){
                    while (rs.next()) {
                        LogItem item = DefaultLogDAO.fillBean(rs);
                        logItems.add(item);
                    }
                }
            }
            catch (DBException e) {
                SQLException sqle = new SQLException(e.getMessage());
                sqle.setStackTrace(e.getStackTrace());
                throw sqle;
            }
        }
        catch (UnsupportPagingException e) {
            logger.error("\u8be5\u6570\u636e\u5e93\u4e0d\u652f\u6301\u5206\u9875\uff0c\u5c06\u4f1a\u4ee5\u5168\u91cf\u67e5\u8be2\u7684\u65b9\u5f0f\u8fdb\u884c\u67e5\u8be2\uff0c\u6570\u636e\u91cf\u5927\u65f6\u5c06\u4f1a\u5f71\u54cd\u6027\u80fd");
            try (PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString());
                 ResultSet rs = ps.executeQuery();){
                int count = 0;
                while (rs.next()) {
                    if (++count > 0 && count <= total) {
                        LogItem item = DefaultLogDAO.fillBean(rs);
                        logItems.add(item);
                        continue;
                    }
                    if (count <= total) continue;
                }
            }
        }
        return logItems;
    }

    public static List<LogItem> getLastLogs(Connection conn, List<String> instanceIds, LogType logType, int total) throws SQLException {
        return DefaultLogDAO.getLogsByCondition(conn, instanceIds, null, null, logType, total);
    }

    public static List<LogItem> getLogsBefore(Connection conn, List<String> instanceIds, long beforeTimeStamp, LogType logType, int count) throws SQLException {
        return DefaultLogDAO.getLogsByCondition(conn, instanceIds, null, beforeTimeStamp, logType, count);
    }

    public static List<LogItem> getLastLogsAfter(Connection conn, List<String> instanceIds, LogType logType, long afterTimeStamp) throws SQLException {
        return DefaultLogDAO.getLogsByCondition(conn, instanceIds, afterTimeStamp, null, logType, null);
    }

    public static LogItem getLog(Connection conn, long logId) throws SQLException {
        LogItem logItem = null;
        try (PreparedStatement ps = conn.prepareStatement(SELECT_LOG);){
            ps.setLong(1, logId);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    logItem = DefaultLogDAO.fillBean(rs);
                }
            }
        }
        return logItem;
    }

    public static LogItem getLogDetail(Connection conn, long logId) throws SQLException {
        LogItem logItem = null;
        try (PreparedStatement ps = conn.prepareStatement(SELECT_LOG_DETAIL);){
            ps.setLong(1, logId);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    logItem = new LogItem();
                    logItem.setId(rs.getLong("L_ID"));
                    byte[] bytes = rs.getBytes(FIELD_LOGDETAIL_DETAIL);
                    if (bytes != null) {
                        try {
                            logItem.setDetail(new String(bytes, "UTF-8"));
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        return logItem;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void iterateAllLogs(Connection conn, List<String> instanceIds, LogType logType, ILogGenerator.LogItemHandle handler) throws SQLException {
        if (instanceIds == null || instanceIds.isEmpty()) {
            return;
        }
        StringBuilder instanceIdsBuilder = new StringBuilder();
        for (int i = 0; i < instanceIds.size(); ++i) {
            if (i > 0) {
                instanceIdsBuilder.append(",");
            }
            String s = instanceIds.get(i);
            s = Html.cleanName((String)s, (char[])new char[0]);
            instanceIdsBuilder.append("'").append(s).append("'");
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(String.format(SELECT_LOGS_BY_CONDITION.replace("?INSTANCEIDS", "%s"), instanceIdsBuilder.toString()));
        if (logType != null) {
            sqlBuilder.append(" AND ").append("L_LEVEL >= ").append(logType.getValue());
        }
        sqlBuilder.append(" ORDER BY ").append(FIELD_LOGS_TIMESTAMP).append(" ASC ");
        try (PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString(), 1003, 1007);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                LogItem logItem = DefaultLogDAO.fillBean(rs);
                byte[] bytes = rs.getBytes(FIELD_LOGDETAIL_DETAIL);
                if (bytes != null) {
                    logItem.setDetail(new String(bytes, StandardCharsets.UTF_8));
                }
                try {
                    handler.handleLogItem(logItem.getLogItem(), logItem.getLogDetail());
                }
                catch (Exception e) {
                    throw new SQLException(e);
                    return;
                }
            }
        }
    }

    private static LogItem fillBean(ResultSet rs) throws SQLException {
        LogItem logItem = new LogItem();
        logItem.setId(rs.getBigDecimal("L_ID").longValue());
        logItem.setTimestamp(rs.getLong(FIELD_LOGS_TIMESTAMP));
        logItem.setNodeName(rs.getString(FIELD_LOGS_NODENAME));
        logItem.setLevel(rs.getInt(FIELD_LOGS_LEVEL));
        logItem.setMessage(rs.getString(FIELD_LOGS_MESSAGE));
        logItem.setHasDetail(rs.getInt(FIELD_LOGS_HASDETAIL) == 1);
        logItem.setInstanceId(rs.getString(FIELD_LOGS_INSTANCEID));
        return logItem;
    }

    public static void delete(Connection conn, List<String> instanceIds) throws SQLException {
        if (instanceIds == null || instanceIds.size() == 0) {
            return;
        }
        StringBuilder idBuilder = new StringBuilder();
        instanceIds.forEach(id -> idBuilder.append("'").append((String)id).append("',"));
        String sql = SQL_DELETE_DETAIL_BY_GUID.replace("?IDS", idBuilder.substring(0, idBuilder.length() - 1));
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.execute();
        }
        sql = SQL_DELETE_BY_GUID.replace("?IDS", idBuilder.substring(0, idBuilder.length() - 1));
        ps = conn.prepareStatement(sql);
        try {
            ps.execute();
        }
        finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<String> getNoExistInstanceIds(Connection conn) throws SQLException {
        ArrayList<String> logIds = new ArrayList<String>();
        try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_NO_EXIST_IN_INSTANCE_TABLE);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                logIds.add(rs.getString(1));
            }
        }
        return logIds;
    }
}

