/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class AlterIndexStatement
extends AlterStatement {
    private String tableName;
    private String indexName;
    private String newIndexName;
    private boolean isJudgeExists;
    private List<String> columnNames = new ArrayList<String>();

    public AlterIndexStatement(String sql, String indexName, AlterType alterType) {
        super(sql, alterType);
        this.indexName = indexName;
    }

    public AlterIndexStatement(String sql, String indexName) {
        this(sql, indexName, null);
    }

    public AlterIndexStatement(String indexName, AlterType alterType) {
        this(null, indexName, alterType);
    }

    public AlterIndexStatement clone() {
        try {
            AlterIndexStatement cloned = (AlterIndexStatement)super.clone();
            cloned.columnNames = new ArrayList<String>();
            for (String column : this.columnNames) {
                cloned.columnNames.add(column);
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setNewIndexName(String newIndexName) {
        this.newIndexName = newIndexName;
    }

    public void setJudgeExists(boolean isJudgeExists) {
        this.isJudgeExists = isJudgeExists;
    }

    public boolean isJudgeExists() {
        return this.isJudgeExists;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public String getNewIndexName() {
        return this.newIndexName;
    }

    public void addIndexColumn(String columnName) {
        this.columnNames.add(columnName);
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.alterIndex(this);
    }
}

