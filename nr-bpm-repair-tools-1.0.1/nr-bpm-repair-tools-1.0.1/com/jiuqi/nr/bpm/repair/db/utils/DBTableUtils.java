/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.AlterTableStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.bpm.repair.db.utils;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DBTableUtils {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable(Connection connection, DBTableEntity tableEntity) throws SQLException, SQLInterpretException {
        try (Statement statement = connection.createStatement();){
            CreateTableStatement createStatement = this.toCreateTableStatement(tableEntity);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            List createSQL = createStatement.interpret(database, connection);
            for (String sql : createSQL) {
                statement.execute(sql);
            }
        }
    }

    public void dropTable(Connection connection, DBTableEntity tableEntity) throws SQLException, SQLInterpretException {
        try (Statement statement = connection.createStatement();){
            AlterTableStatement dropStatement = new AlterTableStatement(tableEntity.getTableName(), AlterType.DROP);
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            List createSQL = dropStatement.interpret(database, connection);
            for (String sql : createSQL) {
                statement.execute(sql);
            }
        }
    }

    public Connection createConnection() {
        DataSource dataSource = this.jdbcTemplate.getDataSource();
        if (dataSource != null) {
            return DataSourceUtils.getConnection((DataSource)dataSource);
        }
        return null;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    private CreateTableStatement toCreateTableStatement(DBTableEntity tableEntity) {
        List<LogicField> logicFields;
        String tableName = tableEntity.getTableName();
        CreateTableStatement createSqlStmt = new CreateTableStatement(null, tableName);
        List<LogicField> primaryFields = tableEntity.getPrimaryFields();
        if (primaryFields != null) {
            primaryFields.forEach(field -> {
                createSqlStmt.addColumn(field);
                createSqlStmt.getPrimaryKeys().add(field.getFieldName());
            });
        }
        if ((logicFields = tableEntity.getLogicFields()) != null) {
            logicFields.forEach(arg_0 -> ((CreateTableStatement)createSqlStmt).addColumn(arg_0));
        }
        return createSqlStmt;
    }
}

