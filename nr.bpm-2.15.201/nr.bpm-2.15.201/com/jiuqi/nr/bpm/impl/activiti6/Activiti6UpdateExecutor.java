/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class Activiti6UpdateExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(Activiti6UpdateExecutor.class);
    private static final String ACT_ID_INFO = "ACT_ID_INFO";
    private static final String ACT_HI_COMMENT = "ACT_HI_COMMENT";
    private static final String ACT_GE_BYTEARRAY = "ACT_GE_BYTEARRAY";
    private static final String ACT_EVT_LOG = "ACT_EVT_LOG";
    private static final String PASSWORD_FILED = "PASSWORD_";
    private static final String FULL_MSG_FILED = "FULL_MSG_";
    private static final String BYTES_FILED = "BYTES_";
    private static final String DATA_FILED = "DATA_";
    private static final String ID_FILED = "ID_";
    private static final String LOG_NR_FILED = "LOG_NR_";

    private List<String> getIdPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(ID_FILED);
        return primaryKeyNames;
    }

    private List<String> getLogPrimaryKeys() {
        ArrayList<String> primaryKeyNames = new ArrayList<String>();
        primaryKeyNames.add(LOG_NR_FILED);
        return primaryKeyNames;
    }

    public void execute(DataSource dataSource) throws Exception {
        this.update(dataSource, PASSWORD_FILED, this.getIdPrimaryKeys(), ACT_ID_INFO);
        this.update(dataSource, FULL_MSG_FILED, this.getIdPrimaryKeys(), ACT_HI_COMMENT);
        this.update(dataSource, BYTES_FILED, this.getIdPrimaryKeys(), ACT_GE_BYTEARRAY);
        this.update(dataSource, DATA_FILED, this.getLogPrimaryKeys(), ACT_EVT_LOG);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void update(DataSource dataSource, String filed, List<String> primaryKeys, String tableName) {
        if (!StringUtils.hasText((String)filed)) return;
        if (!StringUtils.hasText((String)filed)) {
            return;
        }
        this.logger.info("======\u5347\u7ea7\u5f00\u59cb\uff01==========================");
        try {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            Connection connection;
            block20: {
                String tempFieldName = filed + "_";
                connection = dataSource.getConnection();
                preparedStatement = null;
                resultSet = null;
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                try {
                    StringBuilder querySql = new StringBuilder();
                    querySql.append("SELECT ").append(filed).append(",");
                    for (String primaryKey : primaryKeys) {
                        querySql.append(primaryKey).append(",");
                    }
                    querySql.deleteCharAt(querySql.length() - 1);
                    querySql.append(" FROM ").append(tableName);
                    preparedStatement = connection.prepareStatement(querySql.toString());
                    resultSet = preparedStatement.executeQuery();
                    List<ColumnData> data = this.getData(resultSet, primaryKeys, filed);
                    resultSet.close();
                    preparedStatement.close();
                    ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                    this.addColumn(tableName, filed, connection, sqlInterpreter);
                    if (!CollectionUtils.isEmpty(data)) {
                        StringBuilder updateSQL = new StringBuilder();
                        updateSQL.append("UPDATE ").append(tableName).append(" SET ").append(tempFieldName).append("= ? WHERE ");
                        for (int i = 0; i < primaryKeys.size(); ++i) {
                            if (i != primaryKeys.size() - 1) {
                                updateSQL.append(primaryKeys.get(i)).append("=? AND ");
                                continue;
                            }
                            updateSQL.append(primaryKeys.get(i)).append("=?");
                        }
                        preparedStatement = connection.prepareStatement(updateSQL.toString());
                        this.setParams(preparedStatement, data);
                        preparedStatement.executeBatch();
                        preparedStatement.close();
                    }
                    this.dropColumn(tableName, filed, connection, sqlInterpreter);
                    this.renameColumn(tableName, filed, connection, sqlInterpreter);
                    this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01\u8868\u4e3a" + tableName + ",\u66f4\u65b0\u6570\u636e\u4e2a\u6570:" + data.size());
                    if (resultSet != null) {
                    }
                    break block20;
                }
                catch (Exception e) {
                    this.logger.error("\u5347\u7ea7\u5931\u8d25!\u8868\u4e3a" + tableName, e);
                    return;
                }
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            this.close(preparedStatement);
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            this.logger.info("==========BLOB\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210\uff01=========");
            return;
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
                this.logger.info("==========BLOB\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210\uff01=========");
            }
        }
        catch (Exception e) {
            this.logger.info("\u5347\u7ea7\u5931\u8d25!");
        }
    }

    private String getString(ResultSet rs, String filed) throws SQLException {
        Object object = rs.getObject(filed);
        if (object != null && object instanceof Blob) {
            byte[] bytes = rs.getBytes(filed);
            String s = null;
            if (bytes != null && bytes.length > 0) {
                s = new String(bytes, StandardCharsets.UTF_8);
            }
            return s;
        }
        return null;
    }

    private List<ColumnData> getData(ResultSet resultSet, List<String> primaryKeys, String filed) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        while (resultSet.next()) {
            ColumnData r = new ColumnData();
            Object[] values = new Object[primaryKeys.size()];
            for (int i = 0; i < primaryKeys.size(); ++i) {
                values[i] = resultSet.getObject(primaryKeys.get(i));
            }
            r.setValue(values);
            String blob = this.getString(resultSet, filed);
            if (!StringUtils.hasLength((String)blob)) continue;
            r.setData(blob);
            result.add(r);
        }
        return result;
    }

    private void setParams(PreparedStatement preparedStatement, List<ColumnData> data) throws SQLException {
        for (ColumnData columnData : data) {
            preparedStatement.setString(1, columnData.getData());
            for (int i = 0; i < columnData.getValue().length; ++i) {
                preparedStatement.setObject(i + 2, columnData.getValue()[i]);
            }
            preparedStatement.addBatch();
        }
    }

    private void addColumn(String tableName, String filedCode, Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        String tempFieldName = filedCode + "_";
        AlterColumnStatement addStatement = new AlterColumnStatement(tableName, AlterType.ADD);
        addStatement.setColumnName(filedCode);
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

    private void dropColumn(String tableName, String filed, Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement(tableName, AlterType.DROP);
        delStatement.setColumnName(filed);
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(conn, sql);
        }
    }

    private void renameColumn(String tableCode, String filed, Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        String tempFieldName = filed + "_";
        AlterColumnStatement renameStatement = new AlterColumnStatement(tableCode, AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(filed);
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(tempFieldName);
        renameStatement.setReColumnName(filed);
        renameStatement.setNewColumn(newField);
        List renameSql = sqlInterpreter.alterColumn(renameStatement);
        for (String sql : renameSql) {
            this.executeSql(conn, sql);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
                this.close(prep);
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
        private Object[] value;
        private String data;

        ColumnData() {
        }

        public Object[] getValue() {
            return this.value;
        }

        public void setValue(Object[] value) {
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

