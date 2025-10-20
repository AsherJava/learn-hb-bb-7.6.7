/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.sql.command.SQLCommandType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class SQLCommand {
    protected SQLCommandType type;

    public SQLCommandType getType() {
        return this.type;
    }

    public abstract boolean execute(Connection var1) throws SQLException;

    public abstract String toString();

    public String toString(String dbName) {
        return this.toString();
    }

    public void toString(List<String> dst) {
        dst.add(this.toString());
    }

    public void toString(String dbName, List<String> dst) {
        dst.add(this.toString(dbName));
    }
}

