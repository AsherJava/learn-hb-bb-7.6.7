/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.dao.impl;

import com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum;
import com.jiuqi.nr.dynamic.temptable.dao.IDynamicTempTableDao;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableType;
import com.jiuqi.nr.dynamic.temptable.domain.MonitorConfigInfo;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicTempTableDaoImpl
implements IDynamicTempTableDao {
    private static final Logger logger = LoggerFactory.getLogger(DynamicTempTableDaoImpl.class);
    private final ConnectionManager connectionManager;
    private static final String DB_TABLE_NAME_MONITOR = "NR_DYNAMIC_TEMP_TABLE_MONITOR";
    private static final String DB_FIELD_ID = "ID";
    private static final String DB_FIELD_CHECK_INTERVAL = "CHECK_INTERVAL";
    private static final String DB_FIELD_LEAK_DETECTION_THRESHOLD = "LEAK_DETECTION_THRESHOLD";
    private static final String DB_TABLE_NAME_POOL = "NR_DYNAMIC_TEMP_TABLE_POOL";
    private static final String DB_FIELD_TABLE_NAME = "TABLE_NAME";
    private static final String DB_FIELD_COLUMN_COUNT = "COLUMN_COUNT";
    private static final String DB_FIELD_TABLE_ORDER_NUMBER = "TABLE_ORDER_NUMBER";
    private static final String DB_FIELD_STATUS = "STATUS";
    private static final String DB_FIELD_ACQUIRE_USER = "ACQUIRE_USER";
    private static final String DB_FIELD_ACQUIRE_TIME = "ACQUIRE_TIME";
    private static final String DB_FIELD_LAST_USE_TIME = "LAST_USE_TIME";
    private static final String DB_FIELD_CREATE_TIME = "CREATE_TIME";
    private static final String DB_ALIAS_TEMPTABLE_COUNT = "COUNT";
    private static final String DB_ALIAS_TEMPTABLE_AVAILABLE_COUNT = "AVAILABLECOUNT";
    private static final String DB_ALIAS_MAX_TABLEORDERNUMBER = "TON";
    private static final String SELECT_MONITOR_CONFIG = String.format("SELECT %s, %s FROM %s WHERE %s = %s", "CHECK_INTERVAL", "LEAK_DETECTION_THRESHOLD", "NR_DYNAMIC_TEMP_TABLE_MONITOR", "ID", 97);
    private static final String SELECT_TABLE_ORDER_NUMBER = String.format("SELECT %s, MAX(%s) AS %s FROM %s GROUP BY %s ORDER BY %s", "COLUMN_COUNT", "TABLE_ORDER_NUMBER", "TON", "NR_DYNAMIC_TEMP_TABLE_POOL", "COLUMN_COUNT", "COLUMN_COUNT");
    private static final String SELECT_TEMPTABLE_TYPES = String.format("SELECT %s, COUNT(%s) AS %s, COUNT(IF(%s = '%s', 1, NULL)) AS %s FROM %s GROUP BY %s", "COLUMN_COUNT", "TABLE_NAME", "COUNT", "STATUS", DynamicTempTableStatusEnum.AVAILABLE.getStatus(), "AVAILABLECOUNT", "NR_DYNAMIC_TEMP_TABLE_POOL", "COLUMN_COUNT");
    private static final String SELECT_TEMPTABLE_INFOS = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM %s", "TABLE_NAME", "COLUMN_COUNT", "STATUS", "ACQUIRE_USER", "ACQUIRE_TIME", "LAST_USE_TIME", "CREATE_TIME", "NR_DYNAMIC_TEMP_TABLE_POOL");
    private static final String UPDATE_MONITOR_CONFIG = String.format("UPDATE %s SET ", "NR_DYNAMIC_TEMP_TABLE_MONITOR");
    private static final String ACQUIRE_TEMPTABLE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?", "NR_DYNAMIC_TEMP_TABLE_POOL", "STATUS", "ACQUIRE_USER", "ACQUIRE_TIME", "TABLE_NAME", "STATUS");
    private static final String RELEASE_TEMPTABLE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?", "NR_DYNAMIC_TEMP_TABLE_POOL", "STATUS", "ACQUIRE_USER", "ACQUIRE_TIME", "LAST_USE_TIME", "TABLE_NAME", "STATUS");

    public DynamicTempTableDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /*
     * Exception decompiling
     */
    @Override
    public MonitorConfigInfo getMonitorConfig() throws SQLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int updateMonitorConfig(MonitorConfigInfo monitorConfigInfo) throws SQLException {
        int result;
        block20: {
            result = -1;
            Connection conn = null;
            try {
                conn = this.connectionManager.getConnection();
                StringBuilder sql = new StringBuilder(UPDATE_MONITOR_CONFIG);
                ArrayList<Integer> args = new ArrayList<Integer>();
                boolean updateCheckInterval = false;
                if (monitorConfigInfo.getCheckInterval() > 0) {
                    updateCheckInterval = true;
                    sql.append(DB_FIELD_CHECK_INTERVAL).append(" = ?");
                    args.add(monitorConfigInfo.getCheckInterval());
                }
                if (monitorConfigInfo.getLeakDetectionThreshold() > 0) {
                    if (updateCheckInterval) {
                        sql.append(" ,").append(DB_FIELD_LEAK_DETECTION_THRESHOLD).append(" = ?");
                        args.add(monitorConfigInfo.getLeakDetectionThreshold());
                    } else {
                        sql.append(DB_FIELD_LEAK_DETECTION_THRESHOLD).append(" = ?");
                        args.add(monitorConfigInfo.getLeakDetectionThreshold());
                    }
                }
                if (sql.length() <= UPDATE_MONITOR_CONFIG.length()) break block20;
                sql.append(" WHERE ").append(DB_FIELD_ID).append(" = ").append(97);
                try (PreparedStatement prep = conn.prepareStatement(sql.toString());){
                    for (int i = 0; i < args.size(); ++i) {
                        prep.setObject(i + 1, args.get(i));
                    }
                    result = prep.executeUpdate();
                }
            }
            finally {
                this.connectionManager.releaseConnection(conn);
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<Integer, Integer> getMaxTableOrderNumber(Set<Integer> columnCounts) throws SQLException {
        HashMap<Integer, Integer> maxTableOrderNumber = new HashMap<Integer, Integer>();
        Connection conn = null;
        try {
            conn = this.connectionManager.getConnection();
            try (PreparedStatement prep = conn.prepareStatement(SELECT_TABLE_ORDER_NUMBER);
                 ResultSet resultSet = prep.executeQuery();){
                while (resultSet.next()) {
                    maxTableOrderNumber.put(resultSet.getInt(DB_FIELD_COLUMN_COUNT), resultSet.getInt(DB_ALIAS_MAX_TABLEORDERNUMBER));
                }
            }
        }
        finally {
            this.connectionManager.releaseConnection(conn);
        }
        return maxTableOrderNumber;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DynamicTempTableType> getTempTableTypes() throws SQLException {
        ArrayList<DynamicTempTableType> tempTableTypes = new ArrayList<DynamicTempTableType>();
        Connection conn = null;
        try {
            conn = this.connectionManager.getConnection();
            try (PreparedStatement prep = conn.prepareStatement(SELECT_TEMPTABLE_TYPES);
                 ResultSet resultSet = prep.executeQuery();){
                while (resultSet.next()) {
                    DynamicTempTableType tempTableType = new DynamicTempTableType();
                    tempTableType.setColumnCount(resultSet.getInt(DB_FIELD_COLUMN_COUNT));
                    tempTableType.setTableCount(resultSet.getInt(DB_ALIAS_TEMPTABLE_COUNT));
                    tempTableType.setAvaliableCount(resultSet.getInt(DB_ALIAS_TEMPTABLE_AVAILABLE_COUNT));
                    tempTableTypes.add(tempTableType);
                }
            }
        }
        finally {
            this.connectionManager.releaseConnection(conn);
        }
        return tempTableTypes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<DynamicTempTableInfo> getTempTableInfos(ITableQueryInfo tableQueryInfo) throws SQLException {
        ArrayList<DynamicTempTableInfo> tempTableInfos = new ArrayList<DynamicTempTableInfo>();
        Connection conn = null;
        try {
            conn = this.connectionManager.getConnection();
            StringBuilder sql = new StringBuilder(SELECT_TEMPTABLE_INFOS);
            boolean filterByColumnCount = false;
            boolean filterStatus = false;
            StringBuilder columnCountWhere = new StringBuilder("COLUMN_COUNT in (");
            Iterator<Integer> columnCountIterator = tableQueryInfo.ColumnCountIterator();
            while (columnCountIterator.hasNext()) {
                filterByColumnCount = true;
                Integer columnCount = columnCountIterator.next();
                columnCountWhere.append(columnCount).append(",");
            }
            if (filterByColumnCount) {
                columnCountWhere.deleteCharAt(columnCountWhere.length() - 1);
                columnCountWhere.append(") ");
            }
            String statusWhere = "STATUS = '";
            DynamicTempTableStatusEnum statusEnum = tableQueryInfo.getQueryStatus();
            if (!statusEnum.equals((Object)DynamicTempTableStatusEnum.All)) {
                filterStatus = true;
                statusWhere = statusEnum.equals((Object)DynamicTempTableStatusEnum.AVAILABLE) ? statusWhere + DynamicTempTableStatusEnum.AVAILABLE.getStatus() + "' " : statusWhere + DynamicTempTableStatusEnum.IN_USE.getStatus() + "' ";
            }
            if (filterByColumnCount || filterStatus) {
                sql.append(" WHERE ");
                if (filterByColumnCount) {
                    sql.append((CharSequence)columnCountWhere);
                }
                if (filterStatus) {
                    if (filterByColumnCount) {
                        sql.append(" AND ");
                    }
                    sql.append(statusWhere);
                }
            }
            Iterator<Map.Entry<String, String>> orderColumnIterator = tableQueryInfo.orderColumnIterator();
            StringBuilder orderSql = new StringBuilder();
            boolean isOrder = false;
            while (orderColumnIterator.hasNext()) {
                isOrder = true;
                Map.Entry<String, String> entry = orderColumnIterator.next();
                orderSql.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
            }
            if (isOrder) {
                orderSql.deleteCharAt(orderSql.length() - 1);
                sql.append(" ORDER BY ").append((CharSequence)orderSql);
            }
            try (PreparedStatement prep = conn.prepareStatement(sql.toString());
                 ResultSet resultSet = prep.executeQuery();){
                while (resultSet.next()) {
                    DynamicTempTableInfo tempTableInfo = this.mapToDynamicTempTableInfo(resultSet);
                    tempTableInfos.add(tempTableInfo);
                }
            }
        }
        finally {
            this.connectionManager.releaseConnection(conn);
        }
        return tempTableInfos;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public DynamicTempTableInfo getAndAcquireTempTale(Set<Integer> columnCounts, String currentUserID) throws SQLException {
        block48: {
            Connection conn = null;
            DynamicTempTableInfo tempTableInfo = null;
            try {
                block49: {
                    DynamicTempTableInfo dynamicTempTableInfo;
                    Throwable throwable;
                    PreparedStatement prep;
                    block46: {
                        block47: {
                            conn = this.connectionManager.getConnection();
                            conn.setAutoCommit(false);
                            StringBuilder sql = new StringBuilder(SELECT_TEMPTABLE_INFOS);
                            sql.append(" WHERE ").append(DB_FIELD_STATUS).append(" = '").append(DynamicTempTableStatusEnum.AVAILABLE.getStatus()).append("' ");
                            if (!columnCounts.isEmpty()) {
                                sql.append(" AND ").append(DB_FIELD_COLUMN_COUNT).append(" in (");
                                for (Integer columnCount : columnCounts) {
                                    sql.append(columnCount).append(",");
                                }
                                sql.deleteCharAt(sql.length() - 1);
                                sql.append(")");
                            }
                            sql.append(" ORDER BY ").append(DB_FIELD_COLUMN_COUNT).append(", ").append(DB_FIELD_TABLE_ORDER_NUMBER);
                            prep = conn.prepareStatement(sql.toString());
                            throwable = null;
                            try (ResultSet resultSet = prep.executeQuery();){
                                if (resultSet.next()) {
                                    tempTableInfo = this.mapToDynamicTempTableInfo(resultSet);
                                }
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                            finally {
                                if (prep != null) {
                                    if (throwable != null) {
                                        try {
                                            prep.close();
                                        }
                                        catch (Throwable throwable3) {
                                            throwable.addSuppressed(throwable3);
                                        }
                                    } else {
                                        prep.close();
                                    }
                                }
                            }
                            if (tempTableInfo == null) break block49;
                            prep = conn.prepareStatement(ACQUIRE_TEMPTABLE);
                            throwable = null;
                            prep.setString(1, DynamicTempTableStatusEnum.IN_USE.getStatus());
                            prep.setString(2, currentUserID);
                            prep.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                            prep.setString(4, tempTableInfo.getTableName());
                            prep.setString(5, DynamicTempTableStatusEnum.AVAILABLE.getStatus());
                            prep.executeUpdate();
                            conn.commit();
                            dynamicTempTableInfo = tempTableInfo;
                            if (prep == null) break block46;
                            if (throwable == null) break block47;
                            try {
                                prep.close();
                            }
                            catch (Throwable throwable4) {
                                throwable.addSuppressed(throwable4);
                            }
                            break block46;
                        }
                        prep.close();
                    }
                    return dynamicTempTableInfo;
                    catch (Throwable throwable5) {
                        try {
                            try {
                                throwable = throwable5;
                                throw throwable5;
                            }
                            catch (Throwable throwable6) {
                                if (prep != null) {
                                    if (throwable != null) {
                                        try {
                                            prep.close();
                                        }
                                        catch (Throwable throwable7) {
                                            throwable.addSuppressed(throwable7);
                                        }
                                    } else {
                                        prep.close();
                                    }
                                }
                                throw throwable6;
                            }
                        }
                        catch (SQLException e) {
                            conn.rollback();
                            logger.error("\u5360\u7528\u52a8\u6001\u4e34\u65f6\u8868\u3010{}\u3011\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", tempTableInfo.getTableName(), e.getMessage(), e);
                        }
                    }
                    break block48;
                }
                conn.commit();
            }
            finally {
                this.connectionManager.releaseConnection(conn);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public int truncateAndReleaseTempTable(String tempTableName) throws SQLException {
        int result;
        block34: {
            if (!tempTableName.matches("^T_(\\d+)C_(\\d+)$")) {
                throw new IllegalArgumentException("Invalid table name format: " + tempTableName);
            }
            Connection conn = null;
            result = -1;
            try {
                int n;
                Throwable throwable;
                Statement prep;
                block35: {
                    block36: {
                        conn = this.connectionManager.getConnection();
                        conn.setAutoCommit(false);
                        String deleteSql = String.format("DELETE FROM %s", tempTableName);
                        try {
                            prep = conn.createStatement();
                            throwable = null;
                            try {
                                result = prep.executeUpdate(deleteSql);
                            }
                            catch (Throwable throwable2) {
                                throwable = throwable2;
                                throw throwable2;
                            }
                            finally {
                                if (prep != null) {
                                    if (throwable != null) {
                                        try {
                                            prep.close();
                                        }
                                        catch (Throwable throwable3) {
                                            throwable.addSuppressed(throwable3);
                                        }
                                    } else {
                                        prep.close();
                                    }
                                }
                            }
                        }
                        catch (SQLException e) {
                            logger.error("\u6e05\u7a7a\u52a8\u6001\u4e34\u65f6\u8868\u3010{}\u3011\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", tempTableName, e.getMessage(), e);
                            conn.rollback();
                        }
                        if (result == -1) break block34;
                        prep = conn.prepareStatement(RELEASE_TEMPTABLE);
                        throwable = null;
                        prep.setString(1, DynamicTempTableStatusEnum.AVAILABLE.getStatus());
                        prep.setNull(2, 12);
                        prep.setNull(3, 93);
                        prep.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                        prep.setString(5, tempTableName);
                        prep.setString(6, DynamicTempTableStatusEnum.IN_USE.getStatus());
                        conn.commit();
                        n = result += prep.executeUpdate();
                        if (prep == null) break block35;
                        if (throwable == null) break block36;
                        try {
                            prep.close();
                        }
                        catch (Throwable throwable4) {
                            throwable.addSuppressed(throwable4);
                        }
                        break block35;
                    }
                    prep.close();
                }
                return n;
                catch (Throwable throwable5) {
                    try {
                        try {
                            throwable = throwable5;
                            throw throwable5;
                        }
                        catch (Throwable throwable6) {
                            if (prep != null) {
                                if (throwable != null) {
                                    try {
                                        prep.close();
                                    }
                                    catch (Throwable throwable7) {
                                        throwable.addSuppressed(throwable7);
                                    }
                                } else {
                                    prep.close();
                                }
                            }
                            throw throwable6;
                        }
                    }
                    catch (SQLException e) {
                        conn.rollback();
                        logger.error("\u91ca\u653e\u52a8\u6001\u4e34\u65f6\u8868\u3010{}\u3011\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", tempTableName, e.getMessage(), e);
                    }
                }
            }
            finally {
                this.connectionManager.releaseConnection(conn);
            }
        }
        return result;
    }

    private DynamicTempTableInfo mapToDynamicTempTableInfo(ResultSet resultSet) throws SQLException {
        DynamicTempTableInfo tempTableInfo = new DynamicTempTableInfo();
        tempTableInfo.setTableName(resultSet.getString(DB_FIELD_TABLE_NAME));
        tempTableInfo.setColumnCount(resultSet.getInt(DB_FIELD_COLUMN_COUNT));
        String status = resultSet.getString(DB_FIELD_STATUS);
        if (status.equals(DynamicTempTableStatusEnum.IN_USE.getStatus())) {
            tempTableInfo.setStatus(DynamicTempTableStatusEnum.IN_USE);
        } else {
            tempTableInfo.setStatus(DynamicTempTableStatusEnum.AVAILABLE);
        }
        tempTableInfo.setAcquireUser(resultSet.getString(DB_FIELD_ACQUIRE_USER));
        Timestamp acquireTime = resultSet.getTimestamp(DB_FIELD_ACQUIRE_TIME);
        tempTableInfo.setAcquireTime(acquireTime == null ? null : new Date(acquireTime.getTime()));
        Timestamp lastUseTime = resultSet.getTimestamp(DB_FIELD_LAST_USE_TIME);
        tempTableInfo.setLastUseTime(lastUseTime == null ? null : new Date(lastUseTime.getTime()));
        Timestamp createTime = resultSet.getTimestamp(DB_FIELD_CREATE_TIME);
        tempTableInfo.setCreateTime(createTime == null ? null : new Date(createTime.getTime()));
        return tempTableInfo;
    }
}

