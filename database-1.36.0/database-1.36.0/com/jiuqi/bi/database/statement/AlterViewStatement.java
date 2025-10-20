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

public class AlterViewStatement
extends AlterStatement {
    private String viewName;

    public AlterViewStatement(String sql, AlterType alterType, String viewName) {
        super(sql, alterType);
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.alterView(this);
    }

    public AlterViewStatement clone() {
        try {
            return (AlterViewStatement)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

