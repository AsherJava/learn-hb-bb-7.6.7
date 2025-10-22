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
 *  com.jiuqi.xlib.utils.StringUtils
 *  module.manager.intf.CustomClassExecutor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.definitionext.taskExtConfig.config;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.xlib.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import module.manager.intf.CustomClassExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class TaskExtConfigClobUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(TaskExtConfigClobUpdate.class);

    public static String transBytes(byte[] bytes) {
        try {
            return new String(bytes, "GBK");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTableName() {
        return "SYS_FORMSCHEMA_EXTPARA";
    }

    private String getFieldName() {
        return "EXTDATA";
    }

    private String[] getPrimaryKeyNames() {
        return new String[]{"EXTKEY"};
    }

    private String getString(ResultSet rs) throws SQLException {
        byte[] bytes = rs.getBytes(this.getFieldName());
        String s = null;
        if (bytes != null && bytes.length > 0) {
            s = new String(bytes, StandardCharsets.UTF_8);
        }
        return s;
    }

    @Transactional
    public void execute(DataSource dataSource) throws Exception {
        if (!StringUtils.hasText((String)this.getFieldName()) || !StringUtils.hasText((String)this.getTableName())) {
            return;
        }
        if (this.getPrimaryKeyNames().length == 0) {
            return;
        }
        this.logger.info("\u5347\u7ea7\u5f00\u59cb\uff01");
        try (Connection connection = dataSource.getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            try (PreparedStatement preparedStatement = connection.prepareStatement(this.buildSelectSQL());
                 ResultSet resultSet = preparedStatement.executeQuery();){
                List<ColumnData> data = this.getData(resultSet);
                this.addColumn(connection, database.createSQLInterpretor(connection));
                if (!CollectionUtils.isEmpty(data)) {
                    try (PreparedStatement updatePreparedStatement = this.buildUpdatePreparedStatement(connection, data);){
                        updatePreparedStatement.executeBatch();
                    }
                }
                this.dropColumn(connection, database.createSQLInterpretor(connection));
                this.renameColumn(connection, database.createSQLInterpretor(connection));
            }
            catch (SQLException e) {
                this.logger.error("\u5904\u7406\u7ed3\u679c\u96c6\u6216\u9884\u7f16\u8bd1\u8bed\u53e5\u65f6\u53d1\u751f\u9519\u8bef", e);
            }
            this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01");
        }
        catch (SQLException e) {
            this.logger.error("\u83b7\u53d6\u6216\u64cd\u4f5c\u6570\u636e\u5e93\u8fde\u63a5\u65f6\u53d1\u751f\u9519\u8bef", e);
            throw e;
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
            throw e;
        }
    }

    private String buildSelectSQL() {
        StringBuilder querySql = new StringBuilder();
        querySql.append("SELECT ").append(this.getFieldName()).append(",");
        for (String primaryKey : this.getPrimaryKeyNames()) {
            querySql.append(primaryKey).append(",");
        }
        querySql.deleteCharAt(querySql.length() - 1);
        querySql.append(" FROM ").append(this.getTableName());
        return querySql.toString();
    }

    private PreparedStatement buildUpdatePreparedStatement(Connection connection, List<ColumnData> data) throws SQLException {
        StringBuilder updateSQL = new StringBuilder();
        updateSQL.append("UPDATE ").append(this.getTableName()).append(" SET ").append(this.getFieldName() + "_").append("= ? WHERE ");
        for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
            if (i != this.getPrimaryKeyNames().length - 1) {
                updateSQL.append(this.getPrimaryKeyNames()[i]).append("=? AND ");
                continue;
            }
            updateSQL.append(this.getPrimaryKeyNames()[i]).append("=?");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL.toString());
        this.setParams(preparedStatement, data);
        return preparedStatement;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ColumnData> getData(ResultSet resultSet) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        try {
            while (resultSet.next()) {
                ColumnData r = new ColumnData();
                Object[] values = new Object[this.getPrimaryKeyNames().length];
                for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
                    values[i] = resultSet.getObject(this.getPrimaryKeyNames()[i]);
                }
                r.setValue(values);
                String blob = TaskExtConfigClobUpdate.transBytes(resultSet.getBytes(this.getFieldName()));
                if (!StringUtils.hasLength((String)blob)) continue;
                r.setData(blob);
                result.add(r);
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
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

