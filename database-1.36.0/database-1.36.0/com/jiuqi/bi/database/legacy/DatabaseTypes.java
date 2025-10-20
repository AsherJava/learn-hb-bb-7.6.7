/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DatabaseType
 */
package com.jiuqi.bi.database.legacy;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.sql.DatabaseType;

public class DatabaseTypes {
    private DatabaseTypes() {
    }

    public static IDatabase findDatabaseByType(int databastType) {
        String dbName = DatabaseType.nameOf((int)databastType);
        return DatabaseManager.getInstance().findDatabaseByName(dbName);
    }

    public static int getDatabaseType(IDatabase database) {
        String dbName = database.getName();
        return DatabaseType.valueOf((String)dbName);
    }
}

