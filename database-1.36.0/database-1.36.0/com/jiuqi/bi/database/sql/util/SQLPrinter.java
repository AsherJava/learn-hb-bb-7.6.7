/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.util;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.util.StringUtils;

public class SQLPrinter {
    private SQLPrinter() {
    }

    public static void printFullName(IDatabase database, String fullName, StringBuilder buffer) {
        String[] stringArray;
        if (database == null) {
            String[] stringArray2 = new String[2];
            stringArray2[0] = "\"";
            stringArray = stringArray2;
            stringArray2[1] = "\"";
        } else {
            stringArray = database.getDescriptor().getKeywordQuotes();
        }
        String[] quotes = stringArray;
        int start = 0;
        while (start < fullName.length()) {
            String name;
            int next = fullName.indexOf(46, start);
            String string = name = next == -1 ? fullName.substring(start) : fullName.substring(start, next);
            if (name.startsWith(quotes[0]) && name.endsWith(quotes[1])) {
                buffer.append(name);
            } else {
                SQLPrinter.printName(database, name, buffer);
            }
            if (next == -1) break;
            buffer.append('.');
            start = next + 1;
        }
    }

    public static String printFullName(IDatabase database, String fullName) {
        StringBuilder sql = new StringBuilder();
        SQLPrinter.printFullName(database, fullName, sql);
        return sql.toString();
    }

    public static void printName(IDatabase database, String name, StringBuilder buffer) {
        if (SQLPrinter.isIdentifier(name)) {
            if (database.getDescriptor().isKeyword(name)) {
                String[] quotes = database.getDescriptor().getKeywordQuotes();
                buffer.append(quotes[0]);
                SQLPrinter.printKeyword(database, name, buffer);
                buffer.append(quotes[1]);
            } else {
                buffer.append(name);
            }
        } else {
            String[] quotes = database.getDescriptor().getKeywordQuotes();
            String id = quotes[0].equals(quotes[1]) ? name.replaceAll(quotes[0], quotes[0] + quotes[0]) : name;
            buffer.append(quotes[0]).append(id).append(quotes[1]);
        }
    }

    public static String printName(IDatabase database, String name) {
        StringBuilder sql = new StringBuilder();
        SQLPrinter.printName(database, name, sql);
        return sql.toString();
    }

    private static void printKeyword(IDatabase database, String name, StringBuilder buffer) {
        if (database == null) {
            buffer.append(name);
            return;
        }
        switch (database.getDescriptor().getCaseSensitity()) {
            case DEFAULT: {
                buffer.append(name);
                break;
            }
            case UPPERCASE: {
                buffer.append(name.toUpperCase());
                break;
            }
            case LOWERCASE: {
                buffer.append(name.toLowerCase());
            }
        }
    }

    public static boolean isIdentifier(String id) {
        return StringUtils.isIdentifier((String)id);
    }

    public static void printField(IDatabase database, ISQLTable printer, ISQLField field, StringBuilder buffer) throws SQLModelException {
        if (printer != null && field.owner() != null && printer != field.owner()) {
            SQLPrinter.printFullName(database, field.owner().tableName(), buffer);
            buffer.append('.');
            SQLPrinter.printName(database, field.fieldName(), buffer);
        } else {
            field.toSQL(buffer, database, 1);
        }
    }
}

