/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableDBUtil;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntityData;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableInsertSqlBuilder;
import com.jiuqi.nr.batch.summary.service.dbutil.TableDBSqlBuilder;
import com.jiuqi.nr.batch.summary.service.dbutil.TableEntityData;
import com.jiuqi.nr.batch.summary.service.engine.BatchSummarySqlBuilder;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableEntity;
import com.jiuqi.nr.batch.summary.service.table.PowerTableEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TableDBUtil
implements ITableDBUtil {
    private static final Logger logger = LoggerFactory.getLogger(TableDBUtil.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTable(Connection connection, ITableEntity tableEntity) {
        Statement statement = null;
        try {
            TableDBSqlBuilder tableDBSqlBuilder = new TableDBSqlBuilder();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            List<String> createTableSql = tableDBSqlBuilder.createTableSql(tableEntity, database, connection);
            statement = connection.createStatement();
            for (String sql : createTableSql) {
                statement.execute(sql);
            }
            this.closeStatement(statement);
        }
        catch (SQLInterpretException | SQLException e) {
            try {
                throw new RuntimeException(e);
            }
            catch (Throwable throwable) {
                this.closeStatement(statement);
                throw throwable;
            }
        }
    }

    @Override
    public void dropTable(Connection connection, ITableEntity tableEntity) {
        Statement statement = null;
        try {
            TableDBSqlBuilder tableDBSqlBuilder = new TableDBSqlBuilder();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            List<String> dropTableSql = tableDBSqlBuilder.dropTableSql(tableEntity, database, connection);
            statement = connection.createStatement();
            for (String sql : dropTableSql) {
                statement.execute(sql);
            }
            this.closeStatement(statement);
        }
        catch (SQLInterpretException | SQLException e) {
            try {
                throw new RuntimeException(e);
            }
            catch (Throwable throwable) {
                this.closeStatement(statement);
                throw throwable;
            }
        }
    }

    @Override
    public void insertTableData(Connection connection, ITableEntity tableEntity, ITableEntityData tableData) {
        ITableInsertSqlBuilder insertSqlBuilder = new ITableInsertSqlBuilder(tableEntity.getTableName());
        List<LogicField> allColumns = tableEntity.getAllColumns();
        List<String> columnNames = allColumns.stream().map(LogicField::getFieldName).collect(Collectors.toList());
        insertSqlBuilder.addInsertColumns(columnNames);
        List<String> values = columnNames.stream().map(cn -> "?").collect(Collectors.toList());
        insertSqlBuilder.addValues(values);
        insertSqlBuilder.end();
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Iterator<Object[]> iterator = tableData.rowIterator();
        while (iterator.hasNext()) {
            batchValues.add(iterator.next());
        }
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)insertSqlBuilder.toString(), batchValues);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void insertTableData(Connection connection, ITableEntityData tableData, String tableName, List<String> columns) {
        ITableInsertSqlBuilder insertSqlBuilder = new ITableInsertSqlBuilder(tableName);
        insertSqlBuilder.addInsertColumns(columns);
        insertSqlBuilder.addValues(columns.stream().map(cn -> "?").collect(Collectors.toList()));
        insertSqlBuilder.end();
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        Iterator<Object[]> iterator = tableData.rowIterator();
        while (iterator.hasNext()) {
            batchValues.add(iterator.next());
        }
        try {
            DataEngineUtil.batchUpdate((Connection)connection, (String)insertSqlBuilder.toString(), batchValues);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void implementSQL(Connection connection, String sql) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.closeStatement(statement);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ITableEntityData selectSQLImplement(Connection connection, BatchSummarySqlBuilder summarySqlBuilder) {
        List<String> queryColumns = summarySqlBuilder.getQuerySelectColumns();
        String sql = summarySqlBuilder.toString();
        TableEntityData tableEntityData = new TableEntityData(queryColumns.toArray(new String[0]));
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            int rowIdx = 0;
            while (resultSet.next()) {
                for (int colIdx = 0; colIdx < queryColumns.size(); ++colIdx) {
                    String colName = queryColumns.get(colIdx);
                    if (colName.equalsIgnoreCase(BatchSummarySqlBuilder.bizkeyOrder)) {
                        tableEntityData.setCellValue(rowIdx, colName, (Object)UUID.randomUUID().toString());
                        continue;
                    }
                    tableEntityData.setCellValue(rowIdx, colIdx, resultSet.getObject(colIdx + 1));
                }
                ++rowIdx;
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.closeStatement(statement);
        }
        return tableEntityData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IPowerTableEntity selectSQLImplement(Connection connection, String tableName, List<String> columns, Map<String, Object> otherValues) {
        PowerTableEntity tableEntityData = new PowerTableEntity(columns.toArray(new String[0]));
        tableEntityData.appendCols(otherValues.keySet().toArray(new String[0]));
        StringBuilder selectSQL = new StringBuilder("SELECT ");
        selectSQL.append(String.join((CharSequence)",", columns));
        selectSQL.append(" FROM ").append(tableName);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(selectSQL.toString());
            ResultSet resultSet = statement.executeQuery();
            int rowIdx = 0;
            while (resultSet.next()) {
                for (int colIdx = 0; colIdx < columns.size(); ++colIdx) {
                    tableEntityData.setCellValue(rowIdx, colIdx, resultSet.getObject(colIdx + 1));
                }
                for (Map.Entry<String, Object> entry : otherValues.entrySet()) {
                    tableEntityData.setCellValue(rowIdx, entry.getKey(), entry.getValue());
                }
                ++rowIdx;
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.closeStatement(statement);
        }
        return tableEntityData;
    }

    @Override
    public Connection createConnection() {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        if (dataSource != null) {
            return DataSourceUtils.getConnection((DataSource)dataSource);
        }
        return null;
    }

    @Override
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    private void closeStatement(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

