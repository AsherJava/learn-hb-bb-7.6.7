/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader.defaultdb;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.AbstractTableLoader;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.InsertSQLModel;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import java.sql.Connection;
import java.sql.SQLException;

public final class DefaultInsertLoader
extends AbstractTableLoader {
    public DefaultInsertLoader(Connection conn, IDatabase database) {
        super(conn, database);
    }

    @Override
    public int execute() throws TableLoaderException {
        String sql;
        this.validate();
        InsertSQLModel insertSQL = new InsertSQLModel(this.sourceTable, this.destTable);
        this.resetSourceFields();
        try {
            sql = insertSQL.toSQL(this.database, 0);
        }
        catch (SQLModelException e) {
            throw new TableLoaderException(e);
        }
        try {
            return this.execUpdateSQL(sql);
        }
        catch (SQLException e) {
            throw new TableLoaderException(e);
        }
    }
}

