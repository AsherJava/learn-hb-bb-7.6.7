/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.grid2.ReadMemStream2
 *  com.jiuqi.np.grid2.Stream2
 *  com.jiuqi.np.grid2.StreamException2
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.grid2.ReadMemStream2;
import com.jiuqi.np.grid2.Stream2;
import com.jiuqi.np.grid2.StreamException2;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.exception.DefinitonException;
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

public class FormulaVariableUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(FormulaVariableUpdate.class);
    private static final String NR_PARAM_FORMULAVARIABLE = "NR_PARAM_FORMULAVARIABLE";
    private static final String NR_PARAM_FORMULAVARIABLE_DES = "NR_PARAM_FORMULAVARIABLE_DES";
    private static final String FA_INITVALUE = "FA_INITVALUE";
    private static final String FA_KEY = "FA_KEY";

    private String getTableName() {
        return NR_PARAM_FORMULAVARIABLE;
    }

    private String getDesTableName() {
        return NR_PARAM_FORMULAVARIABLE_DES;
    }

    private String getFieldName() {
        return FA_INITVALUE;
    }

    private String[] getPrimaryKeyNames() {
        return new String[]{FA_KEY};
    }

    public void execute(DataSource dataSource) throws Exception {
        this.modify(dataSource, this.getTableName());
        this.modify(dataSource, this.getDesTableName());
    }

    private String getString(ResultSet rs) throws SQLException {
        byte[] bytes = rs.getBytes(this.getFieldName());
        if (bytes == null) {
            return null;
        }
        String strData = null;
        try {
            ReadMemStream2 s = new ReadMemStream2();
            s.writeBuffer(bytes, 0, bytes.length);
            s.setPosition(0L);
            strData = FormulaVariableUpdate.loadFromStream((Stream2)s);
        }
        catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
        }
        return strData;
    }

    public static String loadFromStream(Stream2 stream) throws StreamException2 {
        int length = stream.readInt();
        return stream.readString(length);
    }

    public void modify(DataSource dataSource, String tableName) throws Exception {
        if (!StringUtils.hasText(this.getFieldName())) {
            return;
        }
        this.logger.info("\u5347\u7ea7\u5f00\u59cb\uff01");
        String tempFieldName = this.getFieldName() + "_";
        try (Connection connection = dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ISQLMetadata metadata = database.createMetadata(connection);
            if (!this.isBlob(metadata, tableName)) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
                this.logger.info(this.getTableName() + "\u4e0d\u5b58\u5728BLOB\u5b57\u6bb5\uff0c\u5347\u7ea7\u7ed3\u675f\uff01");
                return;
            }
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT ").append(this.getFieldName()).append(",");
            for (String primaryKey : this.getPrimaryKeyNames()) {
                querySql.append(primaryKey).append(",");
            }
            querySql.deleteCharAt(querySql.length() - 1);
            querySql.append(" FROM ").append(tableName);
            try (PreparedStatement preparedStatement = connection.prepareStatement(querySql.toString());
                 ResultSet resultSet = preparedStatement.executeQuery();){
                List<ColumnData> data = this.getData(resultSet);
                resultSet.close();
                ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                this.addColumn(connection, sqlInterpreter, tableName);
                if (!CollectionUtils.isEmpty(data)) {
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("UPDATE ").append(tableName).append(" SET ").append(tempFieldName).append("= ? WHERE ");
                    for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
                        if (i != this.getPrimaryKeyNames().length - 1) {
                            updateSQL.append(this.getPrimaryKeyNames()[i]).append("=? AND ");
                            continue;
                        }
                        updateSQL.append(this.getPrimaryKeyNames()[i]).append("=?");
                    }
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(updateSQL.toString());){
                        this.setParams(preparedStatement2, data);
                        preparedStatement2.executeBatch();
                    }
                }
                this.dropColumn(connection, sqlInterpreter, tableName);
                this.renameColumn(connection, sqlInterpreter, tableName);
                this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01");
            }
            catch (Exception e) {
                this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
            }
        }
    }

    private boolean isBlob(ISQLMetadata sqlMetadata, String tableName) throws SQLException, SQLMetadataException {
        List fieldsByTableName = sqlMetadata.getFieldsByTableName(tableName);
        if (CollectionUtils.isEmpty(fieldsByTableName)) {
            return false;
        }
        List collect = fieldsByTableName.stream().filter(a -> a.getFieldName().equalsIgnoreCase(this.getFieldName())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return ((LogicField)collect.get(0)).getDataType() == 9;
        }
        return false;
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

    private void addColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName) {
        String tempFieldName = this.getFieldName() + "_";
        AlterColumnStatement addStatement = new AlterColumnStatement(tableName, AlterType.ADD);
        addStatement.setColumnName(this.getFieldName());
        LogicField newField = new LogicField();
        newField.setFieldName(tempFieldName);
        newField.setDataType(12);
        newField.setNullable(true);
        this.setNewColumn(conn, sqlInterpreter, addStatement, newField);
    }

    private void setNewColumn(Connection conn, ISQLInterpretor sqlInterpreter, AlterColumnStatement addStatement, LogicField newField) {
        addStatement.setNewColumn(newField);
        try {
            List addSql = sqlInterpreter.alterColumn(addStatement);
            for (String sql : addSql) {
                this.executeSql(conn, sql);
            }
        }
        catch (SQLInterpretException | SQLException e) {
            throw new DefinitonException(e);
        }
    }

    private void dropColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName) {
        AlterColumnStatement delStatement = new AlterColumnStatement(tableName, AlterType.DROP);
        delStatement.setColumnName(this.getFieldName());
        try {
            List delSql = sqlInterpreter.alterColumn(delStatement);
            for (String sql : delSql) {
                this.executeSql(conn, sql);
            }
        }
        catch (SQLInterpretException | SQLException e) {
            throw new DefinitonException(e);
        }
    }

    private void renameColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName) {
        String tempFieldName = this.getFieldName() + "_";
        AlterColumnStatement renameStatement = new AlterColumnStatement(tableName, AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(this.getFieldName());
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(tempFieldName);
        renameStatement.setReColumnName(this.getFieldName());
        this.setNewColumn(conn, sqlInterpreter, renameStatement, newField);
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

