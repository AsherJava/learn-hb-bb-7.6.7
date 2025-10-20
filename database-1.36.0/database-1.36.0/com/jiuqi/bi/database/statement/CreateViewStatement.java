/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CreateViewStatement
extends SqlStatement {
    private String viewName;
    private List<String> columns = new ArrayList<String>();
    private String asSQL;

    public CreateViewStatement(String sql, String viewName, String asSQL) {
        super(sql);
        this.viewName = viewName;
        this.asSQL = asSQL;
    }

    public CreateViewStatement clone() {
        try {
            CreateViewStatement cloned = (CreateViewStatement)super.clone();
            cloned.columns = new ArrayList<String>();
            for (String column : this.columns) {
                cloned.columns.add(column);
            }
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addColumn(String column) {
        this.columns.add(column);
    }

    public String getViewName() {
        return this.viewName;
    }

    public List<String> getColumns() {
        return this.columns;
    }

    public String getAsSQL() {
        return this.asSQL;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        ISQLInterpretor interpretor = database.createSQLInterpretor(conn);
        return interpretor.createView(this);
    }
}

