/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertLoader;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultMergeLoader;
import java.sql.Connection;
import java.sql.SQLException;

public class TableLoaderFactory {
    private TableLoaderFactory() {
    }

    public static ITableLoader createInsertLoader(Connection conn) throws TableLoaderException {
        return TableLoaderFactory.createInsertLoader(conn, TableLoaderFactory.typeOfDB(conn));
    }

    public static ITableLoader createDefaultInsertLoader(Connection conn) throws TableLoaderException {
        return new DefaultInsertLoader(conn, TableLoaderFactory.typeOfDB(conn));
    }

    private static IDatabase typeOfDB(Connection conn) throws TableLoaderException {
        try {
            return DatabaseManager.getInstance().findDatabaseByConnection(conn);
        }
        catch (SQLException e) {
            throw new TableLoaderException(e);
        }
    }

    public static ITableLoader createUpdateLoader(Connection conn) throws TableLoaderException {
        return TableLoaderFactory.createMergeLoader(conn, TableLoaderFactory.typeOfDB(conn), false);
    }

    public static ITableLoader createDefaultUpdateLoader(Connection conn) throws TableLoaderException {
        return new DefaultMergeLoader(conn, TableLoaderFactory.typeOfDB(conn), false);
    }

    public static ITableLoader createMergeLoader(Connection conn) throws TableLoaderException {
        return TableLoaderFactory.createMergeLoader(conn, TableLoaderFactory.typeOfDB(conn), true);
    }

    public static ITableLoader createDefaultMergeLoader(Connection conn) throws TableLoaderException {
        return new DefaultMergeLoader(conn, TableLoaderFactory.typeOfDB(conn), true);
    }

    private static ITableLoader createInsertLoader(Connection conn, IDatabase database) throws TableLoaderException {
        return database.createInsertLoader(conn);
    }

    private static ITableLoader createMergeLoader(Connection conn, IDatabase database, boolean needInsert) throws TableLoaderException {
        return database.createMergeLoader(conn, !needInsert);
    }
}

