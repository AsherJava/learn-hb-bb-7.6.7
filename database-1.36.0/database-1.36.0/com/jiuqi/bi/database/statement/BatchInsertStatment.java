/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.SqlStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BatchInsertStatment
extends SqlStatement {
    private List<String> inserts = new ArrayList<String>();

    public BatchInsertStatment() {
        super(null);
    }

    public void addInsertSql(String sql) {
        this.inserts.add(sql);
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        return new ArrayList<String>(this.inserts);
    }
}

