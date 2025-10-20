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
import java.util.List;

public class AlterTableStatement
extends AlterStatement {
    private String tableName;
    private String rename;
    private boolean isJudgeExists;

    public AlterTableStatement(String sql, String tableName, AlterType alterType) {
        super(sql, alterType);
        this.tableName = tableName;
    }

    public AlterTableStatement(String sql, String tableName) {
        this(sql, tableName, null);
    }

    public AlterTableStatement(String tableName, AlterType alterType) {
        this(null, tableName, alterType);
    }

    public AlterTableStatement clone() {
        try {
            return (AlterTableStatement)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setJudgeExists(boolean isJudgeExists) {
        this.isJudgeExists = isJudgeExists;
    }

    public boolean isJudgeExists() {
        return this.isJudgeExists;
    }

    public String getRename() {
        return this.rename;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.alterTable(this);
    }
}

