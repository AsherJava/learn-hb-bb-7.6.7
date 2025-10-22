/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.nr.common.db.DatabaseInstance
 */
package com.jiuqi.nr.system.check.common;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.nr.common.db.DatabaseInstance;
import java.util.HashMap;
import java.util.Map;

public class DBUtils {
    private static final Map<String, String> DB_MAP = new HashMap<String, String>();

    public static String buildCondition(String column) {
        IDatabase database = DatabaseInstance.getDatabase();
        String name = database.getName();
        return String.format(DB_MAP.getOrDefault(name, "%s REGEXP '[a-z]'"), column);
    }

    static {
        DB_MAP.put("KINGBASE", "regexp_like(%s,'[a-z]')");
        DB_MAP.put("KINGBASE8", "regexp_like(%s,'[a-z]')");
        DB_MAP.put("MYSQL", "%s REGEXP BINARY '[a-z]");
        DB_MAP.put("POSTGRESQL", "%s ~ '[a-z]'");
        DB_MAP.put("ORACLE", "REGEXP_LIKE(%s, '[a-z]')");
        DB_MAP.put("SQLITE", "%s REGEXP '[a-z]'");
        DB_MAP.put("DM", "regexp_like(%s, '[a-z]')");
    }
}

