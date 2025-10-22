/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.AlterPrimarykeyStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.CreateIndexStatement
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.interval.dao.TableCheckDao
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterPrimarykeyStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.interval.dao.TableCheckDao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatherAssistantTable {
    private static final Logger logger = LoggerFactory.getLogger(GatherAssistantTable.class);
    private static final String GATHER_TABLE = "TMP_GATHER_TABLE";
    private static final String NOT_GATHER_TABLE = "TMP_NOT_GATHER_TABLE";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void createTempTable(Connection connection, String gatherTable, boolean gather) throws SQLException, SQLInterpretException {
        CreateTableStatement sqlStmt = new CreateTableStatement(null, gatherTable);
        GatherAssistantTable.convertField(gather).forEach(col -> sqlStmt.addColumn(col));
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        List sqls = sqlStmt.interpret(database, connection);
        Statement statement = null;
        try {
            List<String> fileds;
            statement = connection.createStatement();
            statement.execute((String)sqls.get(0));
            AlterPrimarykeyStatement alterStmt = new AlterPrimarykeyStatement(null, gatherTable);
            if (gather) {
                alterStmt.addPrimaryKey("GT_ID");
                alterStmt.addPrimaryKey("GT_PID");
                alterStmt.addPrimaryKey("EXECUTION_ID");
                fileds = Arrays.asList("GT_PID", "EXECUTION_ID");
                GatherAssistantTable.addIndex(gatherTable, fileds, database, connection, statement);
            } else {
                alterStmt.addPrimaryKey("NT_ID");
                alterStmt.addPrimaryKey("NT_FID");
                alterStmt.addPrimaryKey("EXECUTION_ID");
                fileds = Arrays.asList("NT_FID", "EXECUTION_ID");
                GatherAssistantTable.addIndex(gatherTable, fileds, database, connection, statement);
            }
            alterStmt.setPrimarykeyName("Key_" + gatherTable);
            alterStmt.setAlterType(AlterType.ADD);
            List alterSql = alterStmt.interpret(database, connection);
            for (String sql : alterSql) {
                statement.execute(sql);
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            GatherAssistantTable.close(statement);
        }
    }

    private static void addIndex(String gatherTable, List<String> indexFields, IDatabase database, Connection connection, Statement statement) throws Exception {
        String indexName = String.format("%s_EXE_IDX", gatherTable);
        CreateIndexStatement alterIndexStatement = new CreateIndexStatement(null, indexName);
        for (String index : indexFields) {
            alterIndexStatement.addIndexColumn(index);
        }
        alterIndexStatement.setTableName(gatherTable);
        List sqls = alterIndexStatement.interpret(database, connection);
        for (String sql : sqls) {
            statement.execute(sql);
        }
    }

    public static boolean isExistTable(String gatherTable) {
        TableCheckDao checkDao = (TableCheckDao)BeanUtil.getBean(TableCheckDao.class);
        try {
            return checkDao.checkTableExist(gatherTable);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static void dropTempTable(Connection connection, String gatherTable) throws SQLException {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("Drop Table ").append(gatherTable);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(deleteSql.toString());
            statement.execute();
        }
        catch (SQLException e) {
            try {
                logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                GatherAssistantTable.close(statement);
                throw throwable;
            }
        }
        GatherAssistantTable.close(statement);
    }

    public static void clearTempTableById(Connection connection, String gatherTable, String executionId) throws SQLException {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from  ").append(gatherTable);
        deleteSql.append(" where ").append("EXECUTION_ID").append(" = ?");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(deleteSql.toString());
            statement.setString(1, executionId);
            statement.execute();
        }
        catch (SQLException e) {
            try {
                logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                GatherAssistantTable.close(statement);
                throw throwable;
            }
        }
        GatherAssistantTable.close(statement);
    }

    public static void clearTempNotGatherTableById(Connection connection, String gatherTable, String executionId) throws SQLException {
        if (null == gatherTable) {
            return;
        }
        boolean exist = GatherAssistantTable.isExistTable(gatherTable);
        if (!exist) {
            return;
        }
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from  ").append(gatherTable);
        deleteSql.append(" where ").append("EXECUTION_ID").append(" = ?");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(deleteSql.toString());
            statement.setString(1, executionId);
            statement.execute();
        }
        catch (SQLException e) {
            try {
                logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                GatherAssistantTable.close(statement);
                throw throwable;
            }
        }
        GatherAssistantTable.close(statement);
    }

    public static void clearTempErrorItemListGather(Connection connection, String gatherTable, String executionId) throws SQLException {
        if (null == gatherTable) {
            return;
        }
        boolean exist = GatherAssistantTable.isExistTable(gatherTable);
        if (!exist) {
            return;
        }
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from  ").append(gatherTable);
        deleteSql.append(" where ").append("EXECUTION_ID").append(" = ?");
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(deleteSql.toString());
            statement.setString(1, executionId);
            statement.execute();
        }
        catch (SQLException e) {
            try {
                logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                GatherAssistantTable.close(statement);
                throw throwable;
            }
        }
        GatherAssistantTable.close(statement);
    }

    public static void clearTempTable(Connection connection, String gatherTable) throws SQLException {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from  ").append(gatherTable);
        deleteSql.append(" where ").append("TIME_").append(" <?");
        long time = System.currentTimeMillis() - 172800000L;
        Date date = new Date(time);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(deleteSql.toString());
            statement.setDate(1, date);
            statement.execute();
        }
        catch (SQLException e) {
            try {
                logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                GatherAssistantTable.close(statement);
                throw throwable;
            }
        }
        GatherAssistantTable.close(statement);
    }

    public static void insertIntoTempTable(Connection connection, String gatherTable, GatherEntityValue entityValue, String executionId) throws SQLException {
        StringBuilder insertSql = new StringBuilder();
        StringBuilder valueSql = new StringBuilder();
        List<String> columns = entityValue.getAllTempColumns();
        insertSql.append(" Insert Into ").append(gatherTable).append("(");
        boolean addDot = false;
        for (String column : columns) {
            if (addDot) {
                insertSql.append(",");
                valueSql.append(",");
            }
            addDot = true;
            insertSql.append(column);
            valueSql.append("?");
        }
        insertSql.append(")").append(" values (").append((CharSequence)valueSql).append(")");
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Integer entityCount = entityValue.getIdValues().size();
        Integer index = 0;
        while (index < entityCount) {
            Object[] rowValue = entityValue.getColumnValues(index, false, executionId);
            batchValues.add(rowValue);
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
        DataEngineUtil.batchUpdate((Connection)connection, (String)insertSql.toString(), batchValues);
    }

    public static List<Object[]> buildTempValues(GatherEntityValue entityValue, String executionId) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Integer entityCount = entityValue.getIdValues().size();
        Integer index = 0;
        while (index < entityCount) {
            Object[] rowValue = entityValue.getColumnValues(index, false, executionId);
            batchValues.add(rowValue);
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
        return batchValues;
    }

    public static List<Object[]> buildNotTempValues(NotGatherEntityValue notGatherValues, String executionId) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Integer entityCount = notGatherValues.getIdValues().size();
        Integer index = 0;
        while (index < entityCount) {
            Object[] rowValue = notGatherValues.getColumnValues(index, false, executionId);
            batchValues.add(rowValue);
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
        return batchValues;
    }

    public static List<Object[]> buildErrorItemListGahterTempValues(Map<String, String> map, String executionId) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Set<String> strings = map.keySet();
        for (String key : strings) {
            Object[] rowvalue = new Object[]{key, map.get(key), executionId};
            batchValues.add(rowvalue);
        }
        return batchValues;
    }

    public static String getTempTableName() {
        return GATHER_TABLE;
    }

    public static ITempTable getGatherTempTable() {
        ITempTableManager tempTableManager = (ITempTableManager)BeanUtil.getBean(ITempTableManager.class);
        ITempTable tempTable = null;
        try {
            tempTable = tempTableManager.getTempTableByType("NR_GTT");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempTable;
    }

    public static ITempTable getErrorListGatherTempTable() {
        ITempTableManager tempTableManager = (ITempTableManager)BeanUtil.getBean(ITempTableManager.class);
        ITempTable tempTable = null;
        try {
            tempTable = tempTableManager.getTempTableByType("NR_GTLE");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempTable;
    }

    public static ITempTable getNotGatherTempTable() {
        ITempTableManager tempTableManager = (ITempTableManager)BeanUtil.getBean(ITempTableManager.class);
        ITempTable tempTable = null;
        try {
            tempTable = tempTableManager.getTempTableByType("NR_NGTT");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tempTable;
    }

    public static void releaseTempTable(ITempTable tempTable) {
        if (tempTable == null) {
            return;
        }
        try {
            tempTable.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNotGatherTempTableName() {
        return NOT_GATHER_TABLE;
    }

    private static List<LogicField> convertField(boolean isGather) {
        ArrayList<LogicField> res = new ArrayList<LogicField>();
        if (isGather) {
            GatherAssistantTable.getGatherField(res);
        } else {
            GatherAssistantTable.getNoGatherField(res);
        }
        return res;
    }

    public static void getGatherField(List<LogicField> res) {
        LogicField fieldID = new LogicField();
        fieldID.setFieldName("GT_ID");
        fieldID.setDataType(6);
        fieldID.setSize(60);
        fieldID.setNullable(false);
        fieldID.setRawType(-9);
        fieldID.setDefaultValue("");
        res.add(fieldID);
        LogicField fieldPid = new LogicField();
        fieldPid.setFieldName("GT_PID");
        fieldPid.setDataType(6);
        fieldPid.setSize(60);
        fieldPid.setNullable(false);
        fieldPid.setRawType(-9);
        fieldPid.setDefaultValue("");
        res.add(fieldPid);
        LogicField fieldMinusID = new LogicField();
        fieldMinusID.setFieldName("GT_MINUSID");
        fieldMinusID.setDataType(6);
        fieldMinusID.setSize(50);
        fieldMinusID.setRawType(-9);
        fieldMinusID.setNullable(true);
        res.add(fieldMinusID);
        LogicField fieldISMinus = new LogicField();
        fieldISMinus.setFieldName("GT_ISMINUS");
        fieldISMinus.setDataType(3);
        fieldISMinus.setNullable(true);
        res.add(fieldISMinus);
        LogicField fieldLevel = new LogicField();
        fieldLevel.setFieldName("GT_LEVEL");
        fieldLevel.setDataType(3);
        fieldLevel.setPrecision(5);
        fieldISMinus.setNullable(true);
        res.add(fieldLevel);
        LogicField executionID = new LogicField();
        executionID.setFieldName("EXECUTION_ID");
        executionID.setDataType(6);
        executionID.setSize(50);
        executionID.setNullable(false);
        executionID.setRawType(-9);
        executionID.setDefaultValue("");
        res.add(executionID);
        LogicField createDate = new LogicField();
        createDate.setFieldName("TIME_");
        createDate.setDataType(2);
        createDate.setDataTypeName("timestamp");
        createDate.setSize(50);
        createDate.setNullable(true);
        res.add(createDate);
        LogicField orderField = new LogicField();
        orderField.setFieldName("GT_ORDER");
        orderField.setDataType(3);
        orderField.setSize(50);
        orderField.setNullable(true);
        res.add(orderField);
    }

    public static void getNoGatherField(List<LogicField> res) {
        LogicField fieldID = new LogicField();
        fieldID.setFieldName("NT_ID");
        fieldID.setDataType(6);
        fieldID.setSize(60);
        fieldID.setNullable(false);
        fieldID.setRawType(-9);
        fieldID.setDefaultValue("");
        res.add(fieldID);
        LogicField fieldFid = new LogicField();
        fieldFid.setFieldName("NT_FID");
        fieldFid.setDataType(6);
        fieldFid.setSize(50);
        fieldFid.setNullable(false);
        fieldFid.setRawType(-9);
        fieldFid.setDefaultValue("");
        res.add(fieldFid);
        LogicField executionID = new LogicField();
        executionID.setFieldName("EXECUTION_ID");
        executionID.setDataType(6);
        executionID.setSize(50);
        executionID.setNullable(false);
        executionID.setRawType(-9);
        executionID.setDefaultValue("");
        res.add(executionID);
        LogicField createDate = new LogicField();
        createDate.setFieldName("TIME_");
        createDate.setDataType(2);
        createDate.setDataTypeName("timestamp");
        createDate.setSize(50);
        createDate.setNullable(true);
        res.add(createDate);
    }

    public static void close(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void getErrorItemListGatherFiled(List<LogicField> res) {
        LogicField fieldID = new LogicField();
        fieldID.setFieldName("CHILDREN_BIZKEY");
        fieldID.setDataType(6);
        fieldID.setSize(50);
        fieldID.setNullable(false);
        fieldID.setRawType(-9);
        fieldID.setDefaultValue("");
        res.add(fieldID);
        LogicField fieldFid = new LogicField();
        fieldFid.setFieldName("PARENT_BIEKEY");
        fieldFid.setDataType(6);
        fieldFid.setSize(50);
        fieldFid.setNullable(false);
        fieldFid.setRawType(-9);
        fieldFid.setDefaultValue("");
        res.add(fieldFid);
        LogicField executionID = new LogicField();
        executionID.setFieldName("EXECUTION_ID");
        executionID.setDataType(6);
        executionID.setSize(50);
        executionID.setNullable(false);
        executionID.setRawType(-9);
        executionID.setDefaultValue("");
        res.add(executionID);
    }
}

