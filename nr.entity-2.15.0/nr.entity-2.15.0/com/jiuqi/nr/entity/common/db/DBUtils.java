/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 */
package com.jiuqi.nr.entity.common.db;

import com.jiuqi.bi.database.IDatabase;

public class DBUtils {
    public static final int MAXINPARAM = 1000;

    public static int getMaxInSize(IDatabase database) {
        if (database.isDatabase("OSCAR")) {
            return 2000;
        }
        if (database.isDatabase("Informix")) {
            return 50;
        }
        return 1000;
    }
}

