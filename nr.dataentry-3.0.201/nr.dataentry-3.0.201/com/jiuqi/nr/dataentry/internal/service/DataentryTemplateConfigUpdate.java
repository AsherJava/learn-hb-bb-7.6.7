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
package com.jiuqi.nr.dataentry.internal.service;

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
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DataentryTemplateConfigUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(DataentryTemplateConfigUpdate.class);

    private String getTableName() {
        return "DATAENTRY_TEMPLATE";
    }

    private String getFieldName() {
        return "TEMPLATE_CONFIG";
    }

    private String[] getPrimaryKeyNames() {
        return new String[]{"TEMPLATE_ID"};
    }

    private String getString(ResultSet rs) throws SQLException {
        byte[] bytes = rs.getBytes(this.getFieldName());
        String s = null;
        if (bytes != null && bytes.length > 0) {
            s = new String(bytes, StandardCharsets.UTF_8);
        }
        return s;
    }

    public void execute(DataSource dataSource) throws Exception {
        if (!StringUtils.hasText(this.getFieldName())) {
            return;
        }
        this.logger.info("\u5347\u7ea7\u5f00\u59cb\uff01");
        String tempFieldName = this.getFieldName() + "_";
        Connection connection = dataSource.getConnection();
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        ISQLMetadata metadata = database.createMetadata(connection);
        if (!this.isBlob(metadata)) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            return;
        }
        try {
            Throwable throwable;
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT ").append(this.getFieldName()).append(",");
            for (String primaryKey : this.getPrimaryKeyNames()) {
                querySql.append(primaryKey).append(",");
            }
            querySql.deleteCharAt(querySql.length() - 1);
            querySql.append(" FROM ").append(this.getTableName());
            List<ColumnData> data = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(querySql.toString());){
                throwable = null;
                try (ResultSet resultSet = preparedStatement.executeQuery();){
                    data = this.getData(resultSet);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
            }
            ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
            this.addColumn(connection, sqlInterpreter);
            if (!CollectionUtils.isEmpty(data)) {
                StringBuilder updateSQL = new StringBuilder();
                updateSQL.append("UPDATE ").append(this.getTableName()).append(" SET ").append(tempFieldName).append("= ? WHERE ");
                for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
                    if (i != this.getPrimaryKeyNames().length - 1) {
                        updateSQL.append(this.getPrimaryKeyNames()[i]).append("=? AND ");
                        continue;
                    }
                    updateSQL.append(this.getPrimaryKeyNames()[i]).append("=?");
                }
                throwable = null;
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL.toString());){
                    this.setParams(preparedStatement, data);
                    preparedStatement.executeBatch();
                }
                catch (Throwable throwable3) {
                    throwable = throwable3;
                    throw throwable3;
                }
            }
            this.dropColumn(connection, sqlInterpreter);
            this.renameColumn(connection, sqlInterpreter);
            this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01");
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
            throw e;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private List<ColumnData> getData(ResultSet resultSet) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        while (resultSet.next()) {
            ColumnData r = new ColumnData();
            Object[] values = new Object[this.getPrimaryKeyNames().length];
            for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
                values[i] = resultSet.getObject(this.getPrimaryKeyNames()[i]);
            }
            r.setValue(values);
            String blob = this.getString(resultSet);
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
            for (int i = 0; i < columnData.getValue().length; ++i) {
                preparedStatement.setObject(i + 2, columnData.getValue()[i]);
            }
            preparedStatement.addBatch();
            if (++batchCount % 1000 != 0) continue;
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }
    }

    private void addColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        String tempFieldName = this.getFieldName() + "_";
        AlterColumnStatement addStatement = new AlterColumnStatement(this.getTableName(), AlterType.ADD);
        addStatement.setColumnName(this.getFieldName());
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

    private void dropColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement(this.getTableName(), AlterType.DROP);
        delStatement.setColumnName(this.getFieldName());
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(conn, sql);
        }
    }

    private void renameColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        String tempFieldName = this.getFieldName() + "_";
        AlterColumnStatement renameStatement = new AlterColumnStatement(this.getTableName(), AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(this.getFieldName());
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(tempFieldName);
        renameStatement.setReColumnName(this.getFieldName());
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

    private boolean isBlob(ISQLMetadata sqlMetadata) throws SQLException, SQLMetadataException {
        List fieldsByTableName = sqlMetadata.getFieldsByTableName(this.getTableName());
        if (CollectionUtils.isEmpty(fieldsByTableName)) {
            return false;
        }
        List collect = fieldsByTableName.stream().filter(a -> a.getFieldName().equalsIgnoreCase(this.getFieldName())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return ((LogicField)collect.get(0)).getDataType() == 9;
        }
        return false;
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

