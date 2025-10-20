/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import java.sql.Connection;
import java.util.List;

public abstract class SqlStatement
implements Cloneable {
    private int line;
    private String errorMsg;
    private int errorLine;
    protected String orginSql;

    public SqlStatement(String sql) {
        this.orginSql = sql;
    }

    public final void setLine(int line) {
        this.line = line;
    }

    public final int getLine() {
        return this.line;
    }

    public final void setErrorMessage(String message) {
        this.errorMsg = message;
    }

    public final void setErrorLine(int errorLine) {
        this.errorLine = errorLine;
    }

    public final String getErrorMessage() {
        return this.errorMsg;
    }

    public final int getErrorLine() {
        return this.errorLine;
    }

    public final String getOrginSql() {
        return this.orginSql;
    }

    public abstract List<String> interpret(IDatabase var1, Connection var2) throws SQLInterpretException;
}

