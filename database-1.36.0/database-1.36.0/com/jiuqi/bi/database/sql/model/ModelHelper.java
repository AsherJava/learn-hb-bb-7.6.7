/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import java.util.Collection;

public class ModelHelper {
    private ModelHelper() {
    }

    public static boolean isGroupQuery(ISQLTable table) {
        for (ISQLField field : table.fields()) {
            if (field.groupMode() != GroupMode.GROUP) continue;
            return true;
        }
        return false;
    }

    public static void printFieldNames(StringBuilder buffer, Collection<ISQLField> fields) {
        boolean started = false;
        for (ISQLField field : fields) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            buffer.append(field.fieldName());
        }
    }

    public static String printFieldNames(Collection<ISQLField> fields) {
        StringBuilder buffer = new StringBuilder();
        ModelHelper.printFieldNames(buffer, fields);
        return buffer.toString();
    }

    public static void printTableNames(StringBuilder buffer, Collection<ISQLTable> tables) {
        boolean started = false;
        for (ISQLTable table : tables) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            buffer.append(table.tableName());
        }
    }

    public static String printTableNames(Collection<ISQLTable> tables) {
        StringBuilder buffer = new StringBuilder();
        ModelHelper.printTableNames(buffer, tables);
        return buffer.toString();
    }
}

