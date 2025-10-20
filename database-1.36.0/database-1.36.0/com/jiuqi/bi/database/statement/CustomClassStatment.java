/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.SqlStatement;
import java.sql.Connection;
import java.util.List;

public class CustomClassStatment
extends SqlStatement {
    private final String className;

    public CustomClassStatment(String className) {
        super(null);
        this.className = className;
    }

    public CustomClassStatment clone() {
        try {
            return (CustomClassStatment)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClassName() {
        return this.className;
    }

    @Override
    public List<String> interpret(IDatabase database, Connection conn) throws SQLInterpretException {
        throw new SQLInterpretException("\u5916\u90e8\u7c7b\u63cf\u8ff0\u8bed\u6cd5\u4e0d\u652f\u6301\u7ffb\u8bd1\u4e3aSQL");
    }
}

