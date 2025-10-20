/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.tables;

public enum JoinMode {
    INNER("INNER JOIN"),
    LEFT("LEFT JOIN"),
    RIGHT("RIGHT JOIN"),
    FULL("FULL JOIN");

    private final String sql;

    private JoinMode(String sql) {
        this.sql = sql;
    }

    public String sql() {
        return this.sql;
    }
}

