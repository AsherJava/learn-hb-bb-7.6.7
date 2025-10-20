/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.util.SQLPrinter;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper {
    private static final int IN_ITEM_SIZE = 1000;

    private SQLHelper() {
    }

    @Deprecated
    public static void printName(String name, StringBuilder buffer) {
        int start = 0;
        while (start < name.length()) {
            int next = name.indexOf(46, start);
            String id = next == -1 ? name.substring(start) : name.substring(start, next);
            SQLHelper.printIdentifier(id, buffer);
            if (next == -1) break;
            buffer.append('.');
            start = next + 1;
        }
    }

    public static void printName(IDatabase database, String name, StringBuilder buffer) {
        SQLPrinter.printFullName(database, name, buffer);
    }

    private static void printIdentifier(String id, StringBuilder buffer) {
        if (StringUtils.isIdentifier((String)id)) {
            buffer.append(id);
        } else {
            id = id.replaceAll("\"", "\"\"");
            buffer.append('\"').append(id).append('\"');
        }
    }

    @Deprecated
    public static void printAlias(String alias, int options, StringBuilder buffer) {
        if (!StringUtils.isEmpty((String)alias) && (options & 1) == 0) {
            buffer.append(' ');
            SQLHelper.printIdentifier(alias, buffer);
        }
    }

    public static void printAlias(IDatabase database, String alias, int options, StringBuilder buffer) {
        if (!StringUtils.isEmpty((String)alias) && (options & 1) == 0) {
            buffer.append(' ');
            SQLPrinter.printName(database, alias, buffer);
        }
    }

    @Deprecated
    public static void printField(String tableName, String fieldName, StringBuilder buffer) {
        if (!StringUtils.isEmpty((String)tableName)) {
            SQLHelper.printName(tableName, buffer);
            buffer.append('.');
        }
        SQLHelper.printName(fieldName, buffer);
    }

    public static void printField(IDatabase database, String tableName, String fieldName, StringBuilder buffer) {
        if (StringUtils.isEmpty((String)tableName)) {
            SQLPrinter.printName(database, fieldName, buffer);
        } else {
            SQLPrinter.printFullName(database, tableName, buffer);
            buffer.append('.');
            SQLPrinter.printName(database, fieldName, buffer);
        }
    }

    public static String printField(IDatabase database, String tableName, String fieldName) {
        StringBuilder buffer = new StringBuilder();
        SQLHelper.printField(database, tableName, fieldName, buffer);
        return buffer.toString();
    }

    @Deprecated
    public static void in(ISQLField field, List<String> filterValues, StringBuilder buffer, int dataType) throws SQLModelException {
        SQLHelper.in(DatabaseManager.getInstance().getDefaultDatabase(), field, filterValues, buffer, dataType);
    }

    @Deprecated
    public static void in(IDatabase databse, ISQLField field, List<String> filterValues, StringBuilder buffer, int dataType) throws SQLModelException {
        SQLHelper.in(databse, null, field, filterValues, buffer, dataType);
    }

    public static void in(IDatabase databse, ISQLTable printer, ISQLField field, List<String> filterValues, StringBuilder buffer, int dataType) throws SQLModelException {
        if (filterValues.size() == 1) {
            SQLHelper.printEqual(databse, printer, field, buffer, dataType, filterValues.get(0));
            return;
        }
        List<String> values = SQLHelper.removeNull(filterValues);
        boolean hasNull = values.size() != filterValues.size();
        int secSize = (values.size() + 1000 - 1) / 1000;
        if (hasNull || secSize > 1) {
            buffer.append('(');
        }
        if (values.size() == 1) {
            SQLHelper.printEqual(databse, printer, field, buffer, dataType, values.get(0));
        } else {
            boolean started = false;
            for (int i = 0; i < secSize; ++i) {
                if (started) {
                    buffer.append(" OR ");
                } else {
                    started = true;
                }
                SQLPrinter.printField(databse, printer, field, buffer);
                buffer.append(" IN (");
                int count = Math.min(1000, values.size() - i * 1000);
                for (int j = 0; j < count; ++j) {
                    if (j > 0) {
                        buffer.append(',');
                    }
                    String item = values.get(i * 1000 + j);
                    SQLHelper.printValue(buffer, item, dataType);
                }
                buffer.append(")");
            }
        }
        if (hasNull) {
            if (!values.isEmpty()) {
                buffer.append(" OR ");
            }
            SQLHelper.printEqual(databse, printer, field, buffer, dataType, null);
        }
        if (hasNull || secSize > 1) {
            buffer.append(')');
        }
    }

    private static List<String> removeNull(List<String> values) {
        if (!values.contains(null)) {
            return values;
        }
        ArrayList<String> retValues = new ArrayList<String>(values.size());
        for (String value : values) {
            if (value == null) continue;
            retValues.add(value);
        }
        return retValues;
    }

    private static void printEqual(IDatabase database, ISQLTable printer, ISQLField field, StringBuilder buffer, int dataType, String value) throws SQLModelException {
        SQLPrinter.printField(database, printer, field, buffer);
        buffer.append(value == null ? " IS " : "=");
        SQLHelper.printValue(buffer, value, dataType);
    }

    public static void printValue(StringBuilder buffer, String itemValue, int dataType) {
        if (itemValue == null) {
            buffer.append("NULL");
        } else if (dataType == 6) {
            buffer.append("'").append(itemValue.replaceAll("'", "''")).append("'");
        } else if (dataType == 1) {
            boolean value = DataTypes.parseBoolean((String)itemValue);
            buffer.append(value ? (char)'1' : '0');
        } else {
            buffer.append(itemValue);
        }
    }

    public static void printRefField(ISQLField field, StringBuilder buffer) throws SQLModelException {
        if (field instanceof EvalField) {
            buffer.append(((EvalField)field).expression());
        } else {
            SQLHelper.printField(field.owner().tableName(), field.fieldName(), buffer);
        }
    }

    public static void printRefField(IDatabase database, ISQLField field, StringBuilder buffer) throws SQLModelException {
        if (field instanceof EvalField) {
            buffer.append(((EvalField)field).expression());
        } else {
            SQLHelper.printField(database, field.owner().tableName(), field.fieldName(), buffer);
        }
    }

    public static void joinStrings(StringBuilder buffer, List<String> strings, String delimeter) {
        if (strings.isEmpty()) {
            return;
        }
        boolean started = false;
        for (String item : strings) {
            if (started) {
                buffer.append(delimeter);
            } else {
                started = true;
            }
            buffer.append(item);
        }
    }

    public static String joinStrings(List<String> strings, String delimeter) {
        if (strings.isEmpty()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        SQLHelper.joinStrings(buffer, strings, delimeter);
        return buffer.toString();
    }

    public static boolean isIdentifier(String id) {
        return StringUtils.isIdentifier((String)id);
    }
}

