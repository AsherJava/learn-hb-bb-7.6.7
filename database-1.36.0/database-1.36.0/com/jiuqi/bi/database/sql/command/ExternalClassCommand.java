/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.sql.command.IExternalClassExecutor;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import java.sql.Connection;
import java.sql.SQLException;

public class ExternalClassCommand
extends SQLCommand {
    private String className;

    public ExternalClassCommand(String className) {
        this.className = className;
    }

    @Override
    public boolean execute(Connection conn) throws SQLException {
        Object newInstance;
        assert (this.className != null) : "className\u4e3a\u7a7a";
        try {
            Class<?> clazz = Class.forName(this.className);
            newInstance = clazz.newInstance();
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
        if (!(newInstance instanceof IExternalClassExecutor)) {
            throw new UnsupportedOperationException("\u4e0d\u652f\u6301execute\u64cd\u4f5c");
        }
        return ((IExternalClassExecutor)newInstance).execute(conn);
    }

    public String getClassName() {
        return this.className;
    }

    @Override
    public String toString() {
        return "call class " + this.className;
    }
}

