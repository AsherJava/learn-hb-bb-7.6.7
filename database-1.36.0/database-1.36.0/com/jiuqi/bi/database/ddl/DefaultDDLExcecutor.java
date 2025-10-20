/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.ddl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.ddl.DDLException;
import com.jiuqi.bi.database.ddl.IDDLExecutor;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DefaultDDLExcecutor
implements IDDLExecutor {
    private final Connection conn;

    public DefaultDDLExcecutor(Connection conn, IDatabase database) {
        this.conn = conn;
    }

    @Override
    public void execute(String sql) throws DDLException {
        try (Statement stmt = this.conn.createStatement();){
            stmt.execute(sql);
        }
        catch (SQLException e) {
            throw new DDLException(e);
        }
    }
}

