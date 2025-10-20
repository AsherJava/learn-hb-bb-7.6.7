/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.SQLModelException;

public enum UnionMode {
    UNION,
    UNIONALL,
    EXCEPT,
    INTERSECT;


    public static String toSQL(UnionMode mode, IDatabase database) throws SQLModelException {
        switch (mode) {
            case UNION: {
                return "UNION";
            }
            case UNIONALL: {
                return "UNION ALL";
            }
            case EXCEPT: {
                return database.isDatabase("ORACLE") ? "MINUS" : "EXCEPT";
            }
            case INTERSECT: {
                return "INTERSECT";
            }
        }
        throw new SQLModelException("\u672a\u77e5\u7684\u96c6\u5408\u8fd0\u7b97\u7c7b\u578b\uff1a" + mode.toString());
    }
}

