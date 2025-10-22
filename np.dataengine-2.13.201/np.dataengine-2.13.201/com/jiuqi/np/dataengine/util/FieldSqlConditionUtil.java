/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.np.dataengine.util;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.query.PageSQLQueryListener;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FieldSqlConditionUtil {
    public static void appendFieldValuesCondition(IDatabase database, Connection conn, StringBuilder sql, String fieldName, int dataType, List<Object> valueList, TempAssistantTable tempAssistantTable) {
        if (valueList.size() >= DataEngineUtil.getMaxInSize(database)) {
            if (tempAssistantTable != null) {
                sql.append(" exists ").append(tempAssistantTable.getExistsSelectSql(fieldName));
            } else {
                FieldSqlConditionUtil.printSplitedInSQL(database, conn, sql, fieldName, dataType, valueList, null, null, false);
            }
        } else {
            FieldSqlConditionUtil.printInSQL(database, conn, sql, fieldName, dataType, valueList, null, null, false);
        }
    }

    public static void printSplitedInSQL(IDatabase database, Connection conn, StringBuilder sql, String fieldName, int dataType, List<?> valueList, List<Object> argValues, List<Integer> argDataTypes, boolean doPage) {
        ArrayList subValues = new ArrayList();
        sql.append('(');
        boolean started = false;
        for (Object val : valueList) {
            subValues.add(val);
            if (subValues.size() < DataEngineUtil.getMaxInSize(database)) continue;
            if (started) {
                sql.append(" OR ");
            } else {
                started = true;
            }
            FieldSqlConditionUtil.printInSQL(database, conn, sql, fieldName, dataType, subValues, argValues, argDataTypes, doPage);
            subValues.clear();
        }
        if (!subValues.isEmpty()) {
            if (started) {
                sql.append(" OR ");
            } else {
                started = true;
            }
            FieldSqlConditionUtil.printInSQL(database, conn, sql, fieldName, dataType, subValues, argValues, argDataTypes, doPage);
            subValues.clear();
        }
        sql.append(')');
    }

    private static void printInSQL(IDatabase database, Connection conn, StringBuilder sql, String fieldName, int dataType, List<?> valueList, List<Object> argValues, List<Integer> argDataTypes, boolean doPage) {
        sql.append(fieldName);
        sql.append(" in (");
        for (Object value : valueList) {
            FieldSqlConditionUtil.appendValue(database, conn, sql, dataType, value, argValues, argDataTypes, doPage);
            sql.append(",");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
    }

    public static void appendFieldCondition(IDatabase database, Connection conn, StringBuilder sql, String fieldName, int dataType, Object value, boolean needAnd, TempAssistantTable tempAssistantTable, List<Object> argValues) {
        FieldSqlConditionUtil.appendFieldCondition(database, conn, sql, fieldName, dataType, value, needAnd, tempAssistantTable, argValues, null, false);
    }

    public static void appendFieldCondition(IDatabase database, Connection conn, StringBuilder sql, String fieldName, int dataType, Object value, boolean needAnd, TempAssistantTable tempAssistantTable, List<Object> argValues, List<Integer> argDataTypes, boolean doPage) {
        if (needAnd) {
            sql.append(" and ");
        }
        if (value instanceof List) {
            List valueList = (List)value;
            if (valueList.size() <= 0) {
                sql.append(" 1=0 ");
            } else if (tempAssistantTable != null) {
                sql.append(" exists ").append(tempAssistantTable.getExistsSelectSql(fieldName));
            } else if (valueList.size() >= DataEngineUtil.getMaxInSize(database)) {
                FieldSqlConditionUtil.printSplitedInSQL(database, conn, sql, fieldName, dataType, valueList, argValues, argDataTypes, doPage);
            } else {
                FieldSqlConditionUtil.printInSQL(database, conn, sql, fieldName, dataType, valueList, argValues, argDataTypes, doPage);
            }
        } else {
            sql.append(fieldName);
            sql.append("=");
            FieldSqlConditionUtil.appendValue(database, conn, sql, dataType, value, argValues, argDataTypes, doPage);
        }
    }

    public static void appendValue(IDatabase database, Connection conn, StringBuilder sql, int dataType, Object value, List<Object> argValues) {
        FieldSqlConditionUtil.appendValue(database, conn, sql, dataType, value, argValues, null, false);
    }

    public static void appendValue(IDatabase database, Connection conn, StringBuilder sql, int dataType, Object value, List<Object> argValues, List<Integer> argDataTypes, boolean doPage) {
        if (argValues != null) {
            FieldSqlConditionUtil.appendArgValue(sql, dataType, value, argValues, argDataTypes, doPage);
        } else {
            FieldSqlConditionUtil.appendConstValue(database, conn, sql, dataType, value);
        }
    }

    private static void appendArgValue(StringBuilder sql, int dataType, Object value, List<Object> argValues, List<Integer> argDataTypes, boolean doPage) {
        Object argValue = value;
        if (dataType == 6) {
            argValue = value.toString();
        } else if (dataType == 33) {
            argValue = Convert.toUUID((Object)value);
        } else if (dataType == 5) {
            argValue = new Date(Convert.toDate((Object)value));
        } else if (dataType == 2) {
            argValue = new Timestamp(Convert.toDate((Object)value));
        } else if (dataType == 4) {
            argValue = Convert.toInt((Object)value);
        }
        if (doPage) {
            sql.append("?" + PageSQLQueryListener.createParamName(argValues.size()));
        } else {
            sql.append("?");
        }
        argValues.add(argValue);
        if (argDataTypes != null) {
            argDataTypes.add(dataType);
        }
    }

    public static void appendConstValue(IDatabase database, Connection conn, StringBuilder sql, int dataType, Object value) {
        if (value == null) {
            sql.append(value);
        } else if (dataType == 6 || dataType == 36 || dataType == 34) {
            sql.append("'").append(value).append("'");
        } else if (dataType == 33) {
            UUID uuid = Convert.toUUID((Object)value);
            sql.append(DataEngineUtil.buildGUIDValueSql(database, uuid));
        } else if (dataType == 5 || dataType == 2) {
            sql.append(DataEngineUtil.buildDateValueSql(database, value, conn));
        } else {
            sql.append(value);
        }
    }
}

