/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.List;

public class AlterColumnStatement
extends AlterStatement {
    private final String tableName;
    private String columnName;
    private LogicField newColumn;
    private String reColumnName;

    public AlterColumnStatement(String sql, String tableName, AlterType alterType) {
        super(sql, alterType);
        this.tableName = tableName;
    }

    public AlterColumnStatement(String sql, String tableName) {
        this(sql, tableName, null);
    }

    public AlterColumnStatement(String tableName, AlterType alterType) {
        this(null, tableName, alterType);
    }

    public AlterColumnStatement clone() {
        try {
            AlterColumnStatement cloned = (AlterColumnStatement)super.clone();
            cloned.newColumn = this.newColumn.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setReColumnName(String reColumnName) {
        this.reColumnName = reColumnName;
    }

    public void setNewColumn(LogicField newColumn) {
        this.newColumn = newColumn;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public LogicField getNewColumn() {
        return this.newColumn;
    }

    public String getReColumnName() {
        return this.reColumnName;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.alterColumn(this);
    }
}

