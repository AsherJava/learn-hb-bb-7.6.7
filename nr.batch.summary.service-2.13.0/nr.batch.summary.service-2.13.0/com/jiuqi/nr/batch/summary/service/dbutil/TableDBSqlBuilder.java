/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.database.statement.AlterTableStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 */
package com.jiuqi.nr.batch.summary.service.dbutil;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.nr.batch.summary.service.dbutil.ITableEntity;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TableDBSqlBuilder {
    public List<String> createTableSql(ITableEntity tableEntity, IDatabase database, Connection connection) throws SQLInterpretException {
        List<LogicField> logicFields;
        String tableName = tableEntity.getTableName();
        CreateTableStatement createSqlStmt = new CreateTableStatement(null, tableName);
        List<LogicField> primaryFields = tableEntity.getPrimaryColumns();
        if (primaryFields != null) {
            primaryFields.forEach(field -> {
                createSqlStmt.addColumn(field);
                createSqlStmt.getPrimaryKeys().add(field.getFieldName());
            });
        }
        if ((logicFields = tableEntity.getLogicColumns()) != null) {
            logicFields.forEach(arg_0 -> ((CreateTableStatement)createSqlStmt).addColumn(arg_0));
        }
        ArrayList<String> creatTableSql = new ArrayList<String>();
        List createSQL = createSqlStmt.interpret(database, connection);
        if (createSQL != null && !createSQL.isEmpty()) {
            creatTableSql.addAll(createSQL);
        }
        return creatTableSql;
    }

    public List<String> dropTableSql(ITableEntity tableEntity, IDatabase database, Connection connection) throws SQLInterpretException {
        ArrayList<String> dropSQL = new ArrayList<String>();
        String tableName = tableEntity.getTableName();
        AlterTableStatement dropTableSqlStmt = new AlterTableStatement(tableName, AlterType.DROP);
        List dropTableSql = dropTableSqlStmt.interpret(database, connection);
        if (dropTableSql != null) {
            dropSQL.addAll(dropTableSql);
        }
        return dropSQL;
    }
}

