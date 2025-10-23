/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.apache.shiro.util.StringUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.dafafill.config;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
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
import javax.sql.DataSource;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public class DataFillBlobUpdateExecuter
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(DataFillBlobUpdateExecuter.class);
    private static final String NR_DATAFILL_MODEL = "NR_DATAFILL_MODEL";
    private static final String DFM_DATA = "DFM_DATA";
    private static final String DFM_ID = "DFM_ID";

    private String getTableName() {
        return NR_DATAFILL_MODEL;
    }

    private String getFieldName() {
        return DFM_DATA;
    }

    private String[] getPrimaryKeyNames() {
        return new String[]{DFM_ID};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void execute(DataSource dataSource) throws Exception {
        ResultSet resultSet;
        PreparedStatement ps2;
        PreparedStatement ps1;
        Connection connection;
        block29: {
            if (!StringUtils.hasText((String)this.getTableName())) return;
            if (!StringUtils.hasText((String)this.getFieldName())) {
                return;
            }
            if (this.getPrimaryKeyNames().length == 0) {
                return;
            }
            this.logger.info("\u5347\u7ea7\u5f00\u59cb\uff01");
            String tempFieldName = this.getFieldName() + "_";
            connection = dataSource.getConnection();
            ps1 = null;
            ps2 = null;
            resultSet = null;
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            try {
                StringBuilder querySql = new StringBuilder();
                querySql.append("SELECT ").append(this.getFieldName()).append(",");
                for (String primaryKey : this.getPrimaryKeyNames()) {
                    querySql.append(primaryKey).append(",");
                }
                querySql.deleteCharAt(querySql.length() - 1);
                querySql.append(" FROM ").append(this.getTableName());
                List<ColumnData> data = null;
                try {
                    ps1 = connection.prepareStatement(querySql.toString());
                    resultSet = ps1.executeQuery();
                    data = this.getData(resultSet);
                }
                finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    this.close(ps1);
                }
                ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
                this.addColumn(connection, sqlInterpreter);
                if (!CollectionUtils.isEmpty(data)) {
                    StringBuilder updateSQL = new StringBuilder();
                    updateSQL.append("UPDATE ").append(this.getTableName()).append(" SET ").append(tempFieldName).append(" = ? WHERE ");
                    for (int i = 0; i < this.getPrimaryKeyNames().length; ++i) {
                        if (i != this.getPrimaryKeyNames().length - 1) {
                            updateSQL.append(this.getPrimaryKeyNames()[i]).append(" = ? AND ");
                            continue;
                        }
                        updateSQL.append(this.getPrimaryKeyNames()[i]).append(" = ? ");
                    }
                    boolean autoCommit = connection.getAutoCommit();
                    connection.setAutoCommit(false);
                    try {
                        ps2 = connection.prepareStatement(updateSQL.toString());
                        this.setParams(ps2, data);
                        ps2.executeBatch();
                        connection.commit();
                    }
                    catch (Exception e) {
                        connection.rollback();
                        throw e;
                    }
                    finally {
                        this.close(ps2);
                        connection.setAutoCommit(autoCommit);
                    }
                }
                this.dropColumn(connection, sqlInterpreter);
                this.renameColumn(connection, sqlInterpreter);
                connection.commit();
                this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01");
                if (resultSet == null) break block29;
            }
            catch (Exception e) {
                this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
                return;
            }
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        this.close(ps1);
        this.close(ps2);
        DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        return;
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            this.close(ps1);
            this.close(ps2);
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private String getString(ResultSet rs) throws SQLException {
        byte[] bytes = rs.getBytes(this.getFieldName());
        String s = null;
        if (bytes != null && bytes.length > 0) {
            s = new String(bytes, StandardCharsets.UTF_8);
        }
        return s;
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

    private void addColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws SQLException, SQLInterpretException {
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

    private void dropColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws SQLException, SQLInterpretException {
        AlterColumnStatement delStatement = new AlterColumnStatement(this.getTableName(), AlterType.DROP);
        delStatement.setColumnName(this.getFieldName());
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(conn, sql);
        }
    }

    private void renameColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws SQLException, SQLInterpretException {
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

