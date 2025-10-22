/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.SQLMetadataException
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.asynctask.ParamConverter
 *  com.jiuqi.np.asynctask.exception.AsyncTaskUpgradeExecption
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.np.asynctask.ext;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.asynctask.ParamConverter;
import com.jiuqi.np.asynctask.exception.AsyncTaskUpgradeExecption;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class AsyncTaskMetaUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(AsyncTaskMetaUpdate.class);
    private ParamConverter converter;
    private static final Long RETENTION_DATA_DAY = 7L;
    private static final int GETDATA_COUNT = 10000;

    private String getTableName() {
        return "NP_ASYNCTASK";
    }

    private String getHisTableName() {
        return "NP_ASYNCTASK_HISTORY";
    }

    private String[] getFieldNames() {
        return new String[]{"ARGS", "DETAIL", "CONTEXT_"};
    }

    private String[] getPrimaryKeyNames() {
        return new String[]{"TASK_ID"};
    }

    private String getString(ResultSet rs, String fieldName) throws SQLException {
        byte[] bytes = rs.getBytes(fieldName);
        String str = null;
        try {
            Object obj = this.converter.fromParam(bytes);
            str = SimpleParamConverter.SerializationUtils.serializeToString(obj);
        }
        catch (Exception e) {
            this.logger.error("\u5b58\u5728\u5347\u7ea7\u9519\u8bef\u6570\u636e\uff01\u4f46\u65e0\u5f71\u54cd\uff0c\u53ef\u5ffd\u7565\uff01");
        }
        return str;
    }

    private String getTempFieldName(String fieldName) {
        String tempFieldName = fieldName + "_1";
        return tempFieldName;
    }

    public void execute(DataSource dataSource) throws Exception {
        this.converter = (ParamConverter)BeanUtils.getBean(ParamConverter.class);
        this.asyncTaskModify(dataSource);
        this.asyncTaskHisModify(dataSource);
    }

    private void asyncTaskModify(DataSource dataSource) throws Exception {
        if (this.getPrimaryKeyNames() == null || this.getPrimaryKeyNames().length == 0) {
            return;
        }
        this.logger.info("\u5347\u7ea7\u5f00\u59cb\uff01");
        Connection connection = null;
        try {
            String[] needChangeFieldNames;
            connection = dataSource.getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ISQLMetadata metadata = database.createMetadata(connection);
            this.deleteTableData(connection);
            String countSql = "select count(*) count from " + this.getTableName();
            int count = 0;
            String[] stringArray = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(countSql);
                 ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
            catch (Throwable object) {
                stringArray = object;
                throw object;
            }
            for (String fieldName : needChangeFieldNames = this.getFieldNames()) {
                if (this.isBlob(metadata, this.getTableName(), fieldName)) {
                    ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                    this.addColumn(connection, sqlInterpreter, this.getTableName(), fieldName);
                    if (count > 0) {
                        this.migrationTableData(connection, fieldName, count, sqlInterpreter);
                    }
                    this.dropColumn(connection, sqlInterpreter, this.getTableName(), fieldName);
                    this.renameColumn(connection, sqlInterpreter, this.getTableName(), fieldName);
                    this.logger.info("\u5347\u7ea7" + this.getTableName() + "-" + fieldName + "\u5b8c\u6210\uff01");
                    continue;
                }
                this.logger.info(this.getTableName() + "-" + fieldName + "\u4e3a\u975eBLOB\u5b57\u6bb5\uff0c\u65e0\u9700\u5347\u7ea7\uff01");
            }
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
            throw new AsyncTaskUpgradeExecption("\u5347\u7ea7\u5931\u8d25", (Throwable)e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    /*
     * WARNING - void declaration
     */
    private void migrationTableData(Connection connection, String fieldName, int count, ISQLInterpretor sqlInterpreter) throws Exception {
        try {
            int start = 0;
            int end = 10000;
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT ");
            querySql.append(fieldName);
            for (String string : this.getPrimaryKeyNames()) {
                querySql.append(",").append(string);
            }
            querySql.append(" FROM ").append(this.getTableName());
            while (start <= count) {
                List<ColumnData> data;
                String sql = this.appendPage(connection, querySql, start, end);
                Throwable throwable = null;
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                     ResultSet resultSet = preparedStatement.executeQuery();){
                    data = this.getData(resultSet, fieldName);
                }
                catch (Throwable throwable2) {
                    Throwable throwable3 = throwable2;
                    throw throwable2;
                }
                if (!CollectionUtils.isEmpty(data)) {
                    void var11_19;
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("UPDATE ").append(this.getTableName()).append(" SET ");
                    updateSQL.append(this.getTempFieldName(fieldName)).append("=?");
                    updateSQL.append(" WHERE ");
                    boolean bl = false;
                    while (var11_19 < this.getPrimaryKeyNames().length) {
                        if (var11_19 != this.getPrimaryKeyNames().length - 1) {
                            updateSQL.append(this.getPrimaryKeyNames()[var11_19]).append("=? AND ");
                        } else {
                            updateSQL.append(this.getPrimaryKeyNames()[var11_19]).append("=?");
                        }
                        ++var11_19;
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL.toString());){
                        this.setParams(preparedStatement, data);
                        preparedStatement.executeBatch();
                    }
                }
                start = end;
                end += 10000;
            }
        }
        catch (Exception e) {
            this.logger.error("\u8fc1\u79fb{}-{}\u6570\u636e\u5f02\u5e38\uff0c\u5347\u7ea7\u5931\u8d25\uff01", (Object)this.getTableName(), (Object)fieldName);
            this.dropTemColumn(connection, sqlInterpreter, this.getTableName(), fieldName);
            throw new AsyncTaskUpgradeExecption("\u8fc1\u79fb" + this.getTableName() + "-" + fieldName + "\u6570\u636e\u5f02\u5e38\uff0c\u5347\u7ea7\u5931\u8d25\uff01", (Throwable)e);
        }
    }

    private void asyncTaskHisModify(DataSource dataSource) throws Exception {
        if (this.getPrimaryKeyNames().length == 0) {
            return;
        }
        this.logger.info("\u5347\u7ea7\u5f00\u59cb\uff01");
        Connection connection = null;
        try {
            String[] needChangeFieldNames;
            connection = dataSource.getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ISQLMetadata metadata = database.createMetadata(connection);
            this.truncateTableData(connection);
            for (String fieldName : needChangeFieldNames = this.getFieldNames()) {
                if (this.isBlob(metadata, this.getHisTableName(), fieldName)) {
                    ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                    this.addColumn(connection, sqlInterpreter, this.getHisTableName(), fieldName);
                    this.dropColumn(connection, sqlInterpreter, this.getHisTableName(), fieldName);
                    this.renameColumn(connection, sqlInterpreter, this.getHisTableName(), fieldName);
                    this.logger.info("\u5347\u7ea7" + this.getHisTableName() + "-" + fieldName + "\u5b8c\u6210\uff01");
                    continue;
                }
                this.logger.info(this.getHisTableName() + "-" + fieldName + "\u4e3a\u975eBLOB\u5b57\u6bb5\uff0c\u65e0\u9700\u5347\u7ea7\uff01");
            }
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
            throw new AsyncTaskUpgradeExecption("\u5347\u7ea7\u5931\u8d25", (Throwable)e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private List<ColumnData> getData(ResultSet resultSet, String fieldName) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        while (resultSet.next()) {
            ColumnData r = new ColumnData();
            Object[] values = new Object[this.getPrimaryKeyNames().length];
            for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
                values[i] = resultSet.getObject(this.getPrimaryKeyNames()[i]);
            }
            r.setValue(values);
            String[] blobData = new String[1];
            String blob = this.getString(resultSet, fieldName);
            blobData[0] = StringUtils.hasLength(blob) ? blob : null;
            r.setData(blobData);
            result.add(r);
        }
        return result;
    }

    private void setParams(PreparedStatement preparedStatement, List<ColumnData> data) throws SQLException {
        for (ColumnData columnData : data) {
            int i;
            for (i = 0; i < columnData.getData().length; ++i) {
                preparedStatement.setString(i + 1, columnData.getData()[i]);
            }
            for (i = 0; i < columnData.getValue().length; ++i) {
                preparedStatement.setObject(i + columnData.getData().length + 1, columnData.getValue()[i]);
            }
            preparedStatement.addBatch();
        }
    }

    private void deleteTableData(Connection connection) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM " + this.getTableName() + " WHERE CREATE_TIME < ? OR COMPLETE_FLAG = 1");){
            deleteStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis() - RETENTION_DATA_DAY * 24L * 60L * 60L * 1000L));
            deleteStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new AsyncTaskUpgradeExecption("\u6e05\u9664np_asynctask\u8868\u6570\u636e\u5f02\u5e38", (Throwable)e);
        }
    }

    private void truncateTableData(Connection connection) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement("TRUNCATE TABLE " + this.getHisTableName());){
            deleteStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new AsyncTaskUpgradeExecption("\u6e05\u9664np_asynctask_history\u8868\u6570\u636e\u5f02\u5e38", (Throwable)e);
        }
    }

    public String appendPage(Connection connection, StringBuilder sql, int start, int end) {
        String pageSql;
        try {
            IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder sqlBuilder = iDatabase.createPagingSQLBuilder();
            sqlBuilder.setRawSQL(sql.toString());
            pageSql = sqlBuilder.buildSQL(start, end);
        }
        catch (Exception e) {
            throw new AsyncTaskUpgradeExecption("\u5206\u9875\u5904\u7406\u5f02\u5e38", (Throwable)e);
        }
        return pageSql;
    }

    private void addColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName, String fieldName) throws Exception {
        AlterColumnStatement addStatement = new AlterColumnStatement(tableName, AlterType.ADD);
        String tempFieldName = this.getTempFieldName(fieldName);
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

    private void dropTemColumn(Connection conn, ISQLInterpretor sqlInterpreter, String tableName, String fieldName) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement(tableName, AlterType.DROP);
        delStatement.setColumnName(this.getTempFieldName(fieldName));
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
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
        AlterColumnStatement renameStatement = new AlterColumnStatement(tableName, AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(fieldName);
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(this.getTempFieldName(fieldName));
        renameStatement.setReColumnName(fieldName);
        renameStatement.setNewColumn(newField);
        List renameSql = sqlInterpreter.alterColumn(renameStatement);
        for (String sql : renameSql) {
            this.executeSql(conn, sql);
        }
    }

    private boolean isBlob(ISQLMetadata sqlMetadata, String tableName, String fieldName) throws SQLException, SQLMetadataException {
        List fieldsByTableName = sqlMetadata.getFieldsByTableName(tableName);
        if (CollectionUtils.isEmpty(fieldsByTableName)) {
            return false;
        }
        for (LogicField logicField : fieldsByTableName) {
            if (!fieldName.equalsIgnoreCase(logicField.getFieldName()) || logicField.getDataType() != 9) continue;
            return true;
        }
        return false;
    }

    private void executeSql(Connection conn, String sql) throws SQLException {
        try (PreparedStatement prep = conn.prepareStatement(sql);){
            this.logger.debug(sql);
            prep.execute();
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw e;
        }
    }

    static class ColumnData {
        private Object[] value;
        private String[] data;

        ColumnData() {
        }

        public Object[] getValue() {
            return this.value;
        }

        public void setValue(Object[] value) {
            this.value = value;
        }

        public String[] getData() {
            return this.data;
        }

        public void setData(String[] data) {
            this.data = data;
        }
    }
}

