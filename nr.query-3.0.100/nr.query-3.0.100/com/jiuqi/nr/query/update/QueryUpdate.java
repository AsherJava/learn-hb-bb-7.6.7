/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.query.update;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class QueryUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(QueryUpdate.class);
    private static final String TABLE_NAME_QUERYMODELDEFINE = "SYS_QUERYMODELDEFINE";
    private static final String TABLE_NAME_QUERYBLOCKDEFINE = "SYS_QUERYBLOCKDEFINE";
    private static final String TABLE_NAME_DATAWARNINGDEFINE = "SYS_DATAWARNINGDEFINE";
    private static String[] tableNames = new String[]{"SYS_QUERYMODELDEFINE", "SYS_QUERYBLOCKDEFINE", "SYS_DATAWARNINGDEFINE"};
    private static Map<String, List<String>> fieldNameMap = new HashMap<String, List<String>>(){
        {
            this.put(QueryUpdate.TABLE_NAME_QUERYMODELDEFINE, new ArrayList<String>(){
                {
                    this.add("QMD_LAYOUT");
                    this.add("QMD_CONDITIONS");
                }
            });
            this.put(QueryUpdate.TABLE_NAME_QUERYBLOCKDEFINE, new ArrayList<String>(){
                {
                    this.add("QBD_BLOCKINFO");
                    this.add("QBD_GRIDDATA");
                    this.add("QBD_PRINT");
                    this.add("QBD_MASTERINFOR");
                    this.add("QMD_EXTENSION");
                    this.add("QBD_FORMDATA");
                }
            });
            this.put(QueryUpdate.TABLE_NAME_DATAWARNINGDEFINE, new ArrayList<String>(){
                {
                    this.add("DWN_PROPERTIES");
                }
            });
        }
    };
    private static Map<String, String> primaryKeyMap = new HashMap<String, String>(){
        {
            this.put(QueryUpdate.TABLE_NAME_QUERYMODELDEFINE, "QMD_ID");
            this.put(QueryUpdate.TABLE_NAME_QUERYBLOCKDEFINE, "QBD_ID");
            this.put(QueryUpdate.TABLE_NAME_DATAWARNINGDEFINE, "DWN_ID");
        }
    };

    private String getString(ResultSet rs, String fieldName) throws SQLException {
        byte[] bytes = rs.getBytes(fieldName);
        String s = null;
        if (bytes != null && bytes.length > 0) {
            s = new String(bytes, StandardCharsets.UTF_8);
        }
        return s;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        this.logger.info("\u67e5\u8be2\u6a21\u5757-\u5347\u7ea7\u5f00\u59cb\uff01");
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        ISQLMetadata metadata = database.createMetadata(connection);
        try {
            for (int i = 0; i < tableNames.length; ++i) {
                String tableName = tableNames[i];
                for (int j = 0; j < fieldNameMap.get(tableName).size(); ++j) {
                    String fieldName = fieldNameMap.get(tableName).get(j);
                    String primaryKeyName = primaryKeyMap.get(tableName);
                    String tempFieldName = fieldName + "_";
                    if (!this.isBlob(metadata, tableName, fieldName)) continue;
                    StringBuilder querySql = new StringBuilder();
                    querySql.append("SELECT ").append(fieldName).append(",").append(primaryKeyName);
                    querySql.append(" FROM ").append(tableName);
                    preparedStatement = connection.prepareStatement(querySql.toString());
                    resultSet = preparedStatement.executeQuery();
                    List<ColumnData> data = this.getData(resultSet, primaryKeyName, fieldName);
                    resultSet.close();
                    preparedStatement.close();
                    ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                    this.addColumn(connection, sqlInterpreter, tableName, fieldName);
                    if (!CollectionUtils.isEmpty(data)) {
                        StringBuilder updateSQL = new StringBuilder();
                        updateSQL.append("UPDATE ").append(tableName).append(" SET ").append(tempFieldName).append("= ? WHERE ").append(primaryKeyName).append("=?");
                        preparedStatement = connection.prepareStatement(updateSQL.toString());
                        this.setParams(preparedStatement, data);
                        preparedStatement.executeBatch();
                        preparedStatement.close();
                    }
                    this.dropColumn(connection, sqlInterpreter, tableName, fieldName);
                    this.renameColumn(connection, sqlInterpreter, tableName, fieldName);
                }
            }
            this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01");
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            this.close(preparedStatement);
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private boolean isBlob(ISQLMetadata sqlMetadata, String tableName, String fieldName) throws SQLException, SQLMetadataException {
        List fieldsByTableName = sqlMetadata.getFieldsByTableName(tableName);
        if (CollectionUtils.isEmpty(fieldsByTableName)) {
            return false;
        }
        List collect = fieldsByTableName.stream().filter(a -> a.getFieldName().equals(fieldName)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return ((LogicField)collect.get(0)).getDataType() == 9;
        }
        return false;
    }

    private List<ColumnData> getData(ResultSet resultSet, String primaryKeyName, String fieldName) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        while (resultSet.next()) {
            ColumnData r = new ColumnData();
            r.setValue(resultSet.getString(primaryKeyName));
            String blob = this.getString(resultSet, fieldName);
            if (!StringUtils.hasLength(blob)) continue;
            r.setData(blob);
            result.add(r);
        }
        return result;
    }

    private void setParams(PreparedStatement preparedStatement, List<ColumnData> data) throws SQLException {
        int batchCount = 0;
        for (ColumnData columnData : data) {
            preparedStatement.setString(1, columnData.getData());
            preparedStatement.setObject(2, columnData.getValue());
            preparedStatement.addBatch();
            if (++batchCount % 1000 != 0) continue;
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }
    }

    private void addColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName, String fieldName) throws Exception {
        String tempFieldName = fieldName + "_";
        AlterColumnStatement addStatement = new AlterColumnStatement(tableName, AlterType.ADD);
        addStatement.setColumnName(fieldName);
        LogicField newField = new LogicField();
        newField.setFieldName(tempFieldName);
        newField.setDataType(12);
        newField.setNullable(true);
        addStatement.setNewColumn(newField);
        List addSql = sqlInterpreter.alterColumn(addStatement);
        for (String sql : addSql) {
            this.executeSql(conn, sql);
        }
    }

    private void dropColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName, String fieldName) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement(tableName, AlterType.DROP);
        delStatement.setColumnName(fieldName);
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(conn, sql);
        }
    }

    private void renameColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName, String fieldName) throws Exception {
        String tempFieldName = fieldName + "_";
        AlterColumnStatement renameStatement = new AlterColumnStatement(tableName, AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(fieldName);
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(tempFieldName);
        renameStatement.setReColumnName(fieldName);
        renameStatement.setNewColumn(newField);
        List renameSql = sqlInterpreter.alterColumn(renameStatement);
        for (String sql : renameSql) {
            this.executeSql(conn, sql);
        }
    }

    private void executeSql(Connection conn, String sql) throws SQLException {
        PreparedStatement prep = null;
        try {
            this.logger.debug(sql);
            prep = conn.prepareStatement(sql);
            prep.execute();
            this.close(prep);
        }
        catch (Exception e) {
            try {
                this.logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                this.close(prep);
                throw throwable;
            }
        }
    }

    private void close(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    static class ColumnData {
        private Object value;
        private String data;

        ColumnData() {
        }

        public Object getValue() {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

