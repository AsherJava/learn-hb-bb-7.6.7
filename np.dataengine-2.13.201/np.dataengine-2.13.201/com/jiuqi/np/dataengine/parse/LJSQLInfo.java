/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.interpret.DatabaseType
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.interpret.DatabaseType;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;

public class LJSQLInfo
implements ISQLInfo {
    public static final String PERIOD_SQL_FIELD = "option.periodSqlField";

    public DatabaseType getDBType() {
        return null;
    }

    public IDatabase getDatabase() {
        return null;
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 0;
    }

    public boolean isCondition() {
        return true;
    }

    public boolean isDatabase(String ... dbNames) {
        return false;
    }
}

