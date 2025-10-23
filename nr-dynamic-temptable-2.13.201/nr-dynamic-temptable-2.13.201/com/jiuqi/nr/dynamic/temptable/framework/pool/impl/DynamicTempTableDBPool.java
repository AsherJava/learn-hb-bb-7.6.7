/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.statement.CreateIndexStatement
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 */
package com.jiuqi.nr.dynamic.temptable.framework.pool.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum;
import com.jiuqi.nr.dynamic.temptable.dao.IDynamicTempTableDao;
import com.jiuqi.nr.dynamic.temptable.domain.CreatTableRule;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableInfo;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableMeta;
import com.jiuqi.nr.dynamic.temptable.domain.DynamicTempTableType;
import com.jiuqi.nr.dynamic.temptable.domain.MonitorConfigInfo;
import com.jiuqi.nr.dynamic.temptable.exception.NoAvailableTempTableException;
import com.jiuqi.nr.dynamic.temptable.framework.builder.ITableQueryInfo;
import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import com.jiuqi.nr.dynamic.temptable.framework.pool.IDynamicTempTablePool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicTempTableDBPool
implements IDynamicTempTablePool {
    private static DynamicTempTableDBPool instance;
    private static final Logger logger;
    private IDynamicTempTableDao dynamicTempTableDao;
    private ConnectionManager connectionManager;

    public static synchronized DynamicTempTableDBPool getInstance(IDynamicTempTableDao dynamicTempTableDao, ConnectionManager connectionManager) {
        if (instance == null) {
            instance = new DynamicTempTableDBPool();
            DynamicTempTableDBPool.instance.dynamicTempTableDao = dynamicTempTableDao;
            DynamicTempTableDBPool.instance.connectionManager = connectionManager;
        }
        return instance;
    }

    @Override
    public boolean isPoolUseCache() {
        return false;
    }

    @Override
    public void setCheckInterval(int checkInterval) {
        MonitorConfigInfo monitorConfigInfo = new MonitorConfigInfo();
        monitorConfigInfo.setCheckInterval(checkInterval);
        try {
            int result = this.dynamicTempTableDao.updateMonitorConfig(monitorConfigInfo);
            if (result == -1) {
                logger.error("\u66f4\u65b0\u52a8\u6001\u4e34\u65f6\u8868\u914d\u7f6e\u3010\u5e38\u89c4\u68c0\u6d4b\u95f4\u9694\u3011\u5931\u8d25\uff01\u8bbe\u7f6e\u503c\u4e3a\uff1a{}", (Object)checkInterval);
            }
        }
        catch (SQLException e) {
            logger.error("\u66f4\u65b0\u52a8\u6001\u4e34\u65f6\u8868\u914d\u7f6e\u3010\u5e38\u89c4\u68c0\u6d4b\u95f4\u9694\u3011\u65f6\u53d1\u751f\u5f02\u5e38\uff01\u8bbe\u7f6e\u503c\u4e3a\uff1a{}\u3002\u5f02\u5e38\u4fe1\u606f\uff1a{}", checkInterval, e.getMessage(), e);
        }
    }

    @Override
    public void setLeakDetectionThreshold(int leakDetectionThreshold) {
        MonitorConfigInfo monitorConfigInfo = new MonitorConfigInfo();
        monitorConfigInfo.setLeakDetectionThreshold(leakDetectionThreshold);
        try {
            int result = this.dynamicTempTableDao.updateMonitorConfig(monitorConfigInfo);
            if (result == -1) {
                logger.error("\u66f4\u65b0\u52a8\u6001\u4e34\u65f6\u8868\u914d\u7f6e\u3010\u6cc4\u6f0f\u5224\u5b9a\u9608\u503c\u3011\u5931\u8d25\uff01\u8bbe\u7f6e\u503c\u4e3a\uff1a{}", (Object)leakDetectionThreshold);
            }
        }
        catch (SQLException e) {
            logger.error("\u66f4\u65b0\u52a8\u6001\u4e34\u65f6\u8868\u914d\u7f6e\u3010\u6cc4\u6f0f\u5224\u5b9a\u9608\u503c\u3011\u65f6\u53d1\u751f\u5f02\u5e38\uff01\u8bbe\u7f6e\u503c\u4e3a\uff1a{}\u3002\u5f02\u5e38\u4fe1\u606f\uff1a{}", leakDetectionThreshold, e.getMessage(), e);
        }
    }

    @Override
    public List<String> addTempTable(List<CreatTableRule> creatTableRules) throws SQLException {
        ArrayList<String> ddlSqls = new ArrayList<String>();
        ArrayList<String> dmlSqls = new ArrayList<String>();
        HashSet<Integer> columnCounts = new HashSet<Integer>();
        creatTableRules.forEach(rule -> columnCounts.add(rule.getColumnCount()));
        Map<Integer, Integer> columnOrder = this.dynamicTempTableDao.getMaxTableOrderNumber(columnCounts);
        for (CreatTableRule creatTableRule : creatTableRules) {
            int columnCount = creatTableRule.getColumnCount();
            int tableCount = creatTableRule.getTableCount();
            int orderNumber = columnOrder.get(columnCount) == null ? 0 : columnOrder.get(columnCount);
            for (int i = 0; i < tableCount; ++i) {
                String tableName = "T_" + columnCount + "C_" + ++orderNumber;
                String tableComment = "\u9884\u5236" + columnCount + "\u5217\u4e34\u65f6\u8868" + orderNumber;
                ddlSqls.addAll(this.getCreateTempTableSql(columnCount, tableName, tableComment));
                dmlSqls.add(this.getInsertTempTableInfoSql(columnCount, orderNumber, tableName));
            }
        }
        ddlSqls.addAll(dmlSqls);
        return ddlSqls;
    }

    @Override
    public List<DynamicTempTableType> getTempTableTypes() throws SQLException {
        return this.dynamicTempTableDao.getTempTableTypes();
    }

    @Override
    public List<DynamicTempTableInfo> getTempTableInfo(ITableQueryInfo tableQueryInfo) throws SQLException {
        return this.dynamicTempTableDao.getTempTableInfos(tableQueryInfo);
    }

    @Override
    public DynamicTempTableMeta getAvailableTempTableMeta(int startColumnCount, int endColumnCount, String currentUseId) throws NoAvailableTempTableException, SQLException {
        HashSet<Integer> columnCounts = new HashSet<Integer>();
        for (int i = startColumnCount; i <= endColumnCount; ++i) {
            columnCounts.add(i);
        }
        DynamicTempTableInfo tempTableInfo = this.dynamicTempTableDao.getAndAcquireTempTale(columnCounts, currentUseId);
        if (tempTableInfo == null) {
            throw new NoAvailableTempTableException();
        }
        DynamicTempTableMeta tableMeta = new DynamicTempTableMeta(this);
        tableMeta.setTableName(tempTableInfo.getTableName());
        tableMeta.setPrimaryKey("ID");
        List<String> columns = tableMeta.getColumns();
        for (int i = 1; i <= tempTableInfo.getColumnCount(); ++i) {
            columns.add("COLUMN_" + i);
        }
        return tableMeta;
    }

    @Override
    public void releaseTempTable(String tempTableName) {
        try {
            int result = this.dynamicTempTableDao.truncateAndReleaseTempTable(tempTableName);
            if (result == -1) {
                logger.error("\u91ca\u653e\u4e34\u65f6\u8868\u3010{}\u3011\u5931\u8d25", (Object)tempTableName);
            }
        }
        catch (SQLException e) {
            logger.error("\u91ca\u653e\u4e34\u65f6\u8868\u3010{}\u3011\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a{}", tempTableName, e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<String> getCreateTempTableSql(int columnCount, String tableName, String tableComment) {
        if (columnCount > 0) {
            ArrayList<String> allDdlSqls = new ArrayList<String>();
            ArrayList indexDdlSqls = new ArrayList();
            Connection conn = null;
            try {
                conn = this.connectionManager.getConnection();
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                CreateTableStatement createTableStatement = new CreateTableStatement(null, tableName);
                for (int i = 0; i <= columnCount; ++i) {
                    LogicField logicField = new LogicField();
                    String columnName = "COLUMN_" + i;
                    if (i == 0) {
                        logicField.setFieldName("ID");
                        logicField.setDataType(5);
                        logicField.setRawType(4);
                        logicField.setSize(50);
                    } else {
                        logicField.setFieldName(columnName);
                        logicField.setDataType(6);
                        logicField.setRawType(12);
                        logicField.setSize(255);
                        logicField.setNullable(true);
                        CreateIndexStatement createIndexStatement = new CreateIndexStatement(null, "INDEX_" + tableName + "_" + columnName);
                        createIndexStatement.setTableName(tableName);
                        createIndexStatement.addIndexColumn(columnName);
                        indexDdlSqls.addAll(createIndexStatement.interpret(database, conn));
                    }
                    createTableStatement.addColumn(logicField);
                }
                String primaryKeyName = "PK_".concat(tableName);
                createTableStatement.setPkName(primaryKeyName);
                createTableStatement.getPrimaryKeys().add("ID");
                allDdlSqls.addAll(createTableStatement.interpret(database, conn));
                allDdlSqls.addAll(indexDdlSqls);
                ArrayList<String> arrayList = allDdlSqls;
                return arrayList;
            }
            catch (Exception e) {
                logger.error("\u901a\u8fc7CreateTableStatement\u751f\u6210\u521b\u5efa\u4e34\u65f6\u8868DDL\u811a\u672c\u65f6\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
            finally {
                this.connectionManager.releaseConnection(conn);
            }
        }
        return Collections.emptyList();
    }

    private String getInsertTempTableInfoSql(int columnCount, int tableOrderNumber, String tableName) {
        if (columnCount > 0) {
            return "INSERT INTO NR_DYNAMIC_TEMP_TABLE_POOL (TABLE_NAME, COLUMN_COUNT, TABLE_ORDER_NUMBER, STATUS) VALUES ('" + tableName + "', " + columnCount + ", " + tableOrderNumber + ", '" + DynamicTempTableStatusEnum.AVAILABLE.getStatus() + "')";
        }
        return "";
    }

    static {
        logger = LoggerFactory.getLogger(DynamicTempTableDBPool.class);
    }
}

