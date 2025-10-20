/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.command.SQLCommand;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import java.sql.Connection;
import java.sql.SQLException;

public class TableExistCommand
extends SQLCommand {
    private String tableName;

    public TableExistCommand(String tableName) {
        this.tableName = tableName;
        this.type = SQLCommandType.SELECT;
    }

    @Override
    public boolean execute(Connection conn) throws SQLException {
        ISQLMetadata meta = DatabaseManager.getInstance().createMetadata(conn);
        try {
            return meta.getTableByName(this.tableName) != null;
        }
        catch (SQLMetadataException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String toString() {
        return "-- check " + this.tableName + " exist";
    }
}

