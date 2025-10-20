/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

public class DBSQLBuilder {
    public static String buildSelect(String tableName, String[] selectFields, String[] whereFields) {
        int i;
        StringBuilder buf = new StringBuilder();
        buf.append("SELECT ");
        for (i = 0; i < selectFields.length; ++i) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(selectFields[i]);
        }
        buf.append(" FROM ").append(tableName);
        if (whereFields != null && whereFields.length > 0) {
            buf.append(" WHERE ");
            for (i = 0; i < whereFields.length; ++i) {
                if (i > 0) {
                    buf.append(" AND ");
                }
                buf.append(whereFields[i]).append("=?");
            }
        }
        return buf.toString();
    }

    public static String buildDelete(String tableName, String[] whereFields) {
        StringBuilder buf = new StringBuilder();
        buf.append("DELETE FROM ").append(tableName);
        if (whereFields != null && whereFields.length > 0) {
            buf.append(" WHERE ");
            for (int i = 0; i < whereFields.length; ++i) {
                if (i > 0) {
                    buf.append(" AND ");
                }
                buf.append(whereFields[i]).append("=?");
            }
        }
        return buf.toString();
    }

    public static String buildInsert(String tableName, String[] insertFields) {
        int i;
        StringBuilder buf = new StringBuilder();
        buf.append("INSERT INTO ").append(tableName);
        buf.append("(");
        for (i = 0; i < insertFields.length; ++i) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append(insertFields[i]);
        }
        buf.append(")");
        buf.append(" VALUES(");
        for (i = 0; i < insertFields.length; ++i) {
            if (i > 0) {
                buf.append(", ");
            }
            buf.append("?");
        }
        buf.append(")");
        return buf.toString();
    }

    public static String buildCount(String tableName, String[] whereFields) {
        StringBuilder buf = new StringBuilder();
        buf.append("SELECT COUNT(1) FROM ").append(tableName);
        if (whereFields != null && whereFields.length > 0) {
            buf.append(" WHERE ");
            for (int i = 0; i < whereFields.length; ++i) {
                if (i > 0) {
                    buf.append(" AND ");
                }
                buf.append(whereFields[i]).append("=?");
            }
        }
        return buf.toString();
    }
}

