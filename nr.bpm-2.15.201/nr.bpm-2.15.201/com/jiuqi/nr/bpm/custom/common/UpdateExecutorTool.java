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
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.bpm.custom.common;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.nio.charset.StandardCharsets;
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
import org.springframework.stereotype.Component;

@Component
public class UpdateExecutorTool {
    private final Logger logger = LoggerFactory.getLogger(UpdateExecutorTool.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void update(DataSource dataSource, String filed, List<String> primaryKeys, String tableName) {
        if (!StringUtils.hasText((String)filed) || !StringUtils.hasText((String)filed)) {
            return;
        }
        this.logger.info("======\u5347\u7ea7\u5f00\u59cb\uff01==========================");
        try {
            String tempFieldName = filed + "_";
            Connection connection = dataSource.getConnection();
            ResultSet resultSet = null;
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT ").append(filed).append(",");
            for (String primaryKey : primaryKeys) {
                querySql.append(primaryKey).append(",");
            }
            querySql.deleteCharAt(querySql.length() - 1);
            querySql.append(" FROM ").append(tableName);
            try (PreparedStatement preparedStatement = connection.prepareStatement(querySql.toString());){
                resultSet = preparedStatement.executeQuery();
                List<ColumnData> data = this.getData(resultSet, primaryKeys, filed);
                resultSet.close();
                preparedStatement.close();
                ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                this.addColumn(tableName, filed, connection, sqlInterpreter);
                StringBuilder updateSQL = new StringBuilder();
                if (!CollectionUtils.isEmpty(data)) {
                    updateSQL.append("UPDATE ").append(tableName).append(" SET ").append(tempFieldName).append("= ? WHERE ");
                    for (int i = 0; i < primaryKeys.size(); ++i) {
                        if (i != primaryKeys.size() - 1) {
                            updateSQL.append(primaryKeys.get(i)).append("=? AND ");
                            continue;
                        }
                        updateSQL.append(primaryKeys.get(i)).append("=?");
                    }
                }
                try (PreparedStatement prepareStatement = connection.prepareStatement(updateSQL.toString());){
                    this.setParams(prepareStatement, data);
                    prepareStatement.executeBatch();
                    prepareStatement.close();
                }
                catch (Exception e) {
                    this.logger.error("\u66f4\u65b0\u5931\u8d25" + tableName, e);
                }
                this.dropColumn(tableName, filed, connection, sqlInterpreter);
                this.renameColumn(tableName, filed, connection, sqlInterpreter);
                this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01\u8868\u4e3a" + tableName + ",\u66f4\u65b0\u6570\u636e\u4e2a\u6570:" + data.size());
            }
            catch (Exception e) {
                this.logger.error("\u5347\u7ea7\u5931\u8d25!\u8868\u4e3a" + tableName, e);
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
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
                this.logger.info("==========BLOB\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210\uff01=========");
            }
        }
        catch (Exception e) {
            this.logger.info("\u5347\u7ea7\u5931\u8d25!");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void batchUpdate(DataSource dataSource, List<String> fileds, List<String> primaryKeys, String tableName) {
        this.logger.info("======\u5347\u7ea7\u5f00\u59cb\uff01==========================");
        try {
            for (String filed : fileds) {
                String tempFieldName = filed + "_";
                Connection connection = dataSource.getConnection();
                ResultSet resultSet = null;
                IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                StringBuilder querySql = new StringBuilder();
                querySql.append("SELECT ").append(filed).append(",");
                for (String primaryKey : primaryKeys) {
                    querySql.append(primaryKey).append(",");
                }
                querySql.deleteCharAt(querySql.length() - 1);
                querySql.append(" FROM ").append(tableName);
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(querySql.toString());
                    Throwable throwable = null;
                    try {
                        resultSet = preparedStatement.executeQuery();
                        List<ColumnData> data = this.getData(resultSet, primaryKeys, filed);
                        resultSet.close();
                        preparedStatement.close();
                        ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                        this.addColumn(tableName, filed, connection, sqlInterpreter);
                        StringBuilder updateSQL = new StringBuilder();
                        if (!CollectionUtils.isEmpty(data)) {
                            updateSQL.append("UPDATE ").append(tableName).append(" SET ").append(tempFieldName).append("= ? WHERE ");
                            for (int i = 0; i < primaryKeys.size(); ++i) {
                                if (i != primaryKeys.size() - 1) {
                                    updateSQL.append(primaryKeys.get(i)).append("=? AND ");
                                    continue;
                                }
                                updateSQL.append(primaryKeys.get(i)).append("=?");
                            }
                        }
                        try (PreparedStatement prepareStatement = connection.prepareStatement(updateSQL.toString());){
                            this.setParams(prepareStatement, data);
                            prepareStatement.executeBatch();
                            prepareStatement.close();
                        }
                        catch (Exception e) {
                            this.logger.error("\u66f4\u65b0\u5931\u8d25" + tableName, e);
                        }
                        this.dropColumn(tableName, filed, connection, sqlInterpreter);
                        this.renameColumn(tableName, filed, connection, sqlInterpreter);
                        this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01\u8868\u4e3a" + tableName + ",\u66f4\u65b0\u6570\u636e\u4e2a\u6570:" + data.size());
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (preparedStatement == null) continue;
                        if (throwable != null) {
                            try {
                                preparedStatement.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        preparedStatement.close();
                    }
                }
                catch (Exception e) {
                    this.logger.error("\u5347\u7ea7\u5931\u8d25!\u8868\u4e3a" + tableName, e);
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
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
                    this.logger.info("==========BLOB\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210\uff01=========");
                }
            }
        }
        catch (Exception e) {
            this.logger.info("\u5347\u7ea7\u5931\u8d25!");
        }
    }

    private String getString(ResultSet rs, String filed) throws SQLException {
        byte[] bytes = rs.getBytes(filed);
        String s = null;
        if (bytes != null && bytes.length > 0) {
            s = new String(bytes, StandardCharsets.UTF_8);
        }
        return s;
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

