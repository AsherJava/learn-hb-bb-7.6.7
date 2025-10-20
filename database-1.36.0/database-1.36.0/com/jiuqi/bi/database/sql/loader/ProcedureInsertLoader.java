/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.AbstractTableLoader;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class ProcedureInsertLoader
extends AbstractTableLoader {
    public ProcedureInsertLoader(Connection conn, IDatabase database) {
        super(conn, database);
    }

    @Override
    public final int execute() throws TableLoaderException {
        int n;
        this.validate();
        String procName = ProcedureInsertLoader.createName();
        this.createProcedure(procName);
        try {
            n = this.execProcedure(procName);
        }
        catch (Throwable throwable) {
            try {
                this.deleteProcedure(procName);
                throw throwable;
            }
            catch (SQLException e) {
                throw new TableLoaderException(e);
            }
        }
        this.deleteProcedure(procName);
        return n;
    }

    protected abstract void createProcedure(String var1) throws SQLException, TableLoaderException;

    protected abstract int execProcedure(String var1) throws SQLException;

    protected abstract void deleteProcedure(String var1) throws SQLException;
}

