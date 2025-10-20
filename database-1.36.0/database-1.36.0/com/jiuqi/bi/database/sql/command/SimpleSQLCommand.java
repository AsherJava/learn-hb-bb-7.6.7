/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleSQLCommand
extends SQLCommand {
    private String sql;

    public SimpleSQLCommand(String sql, SQLCommandType type) {
        this.sql = sql;
        this.type = type;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean execute(Connection conn) throws SQLException {
        try (PreparedStatement ps = null;){
            ps = conn.prepareStatement(this.sql);
            boolean bl = ps.execute();
            return bl;
        }
    }

    @Override
    public String toString() {
        return this.sql;
    }
}

