/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.intf.value.ValueKind;
import com.jiuqi.va.biz.utils.Env;
import java.util.Collection;
import java.util.Iterator;

class DataSqlBuilder {
    private static boolean multiTenant;
    private static String schemePrefix;

    DataSqlBuilder() {
    }

    public static void setMultiTenant(boolean multiTenant) {
        DataSqlBuilder.multiTenant = multiTenant;
    }

    public static void setSchemePrefix(String schemePrefix) {
        DataSqlBuilder.schemePrefix = schemePrefix;
    }

    public static String select(String table, Collection<String> fields, Collection<String> keyFields, Collection<ValueKind> keyValueKinds) {
        StringBuilder sb = new StringBuilder();
        DataSqlBuilder.appendSchema(sb);
        sb.append("select ");
        fields.forEach(o -> sb.append((String)o).append(","));
        sb.setLength(sb.length() - 1);
        sb.append(" from").append(' ').append(table).append(' ');
        DataSqlBuilder.appendCondition(keyFields, keyValueKinds, sb);
        return sb.toString();
    }

    public static String select(String table, Collection<String> fields, String condition) {
        StringBuilder sb = new StringBuilder();
        DataSqlBuilder.appendSchema(sb);
        sb.append("select ");
        fields.forEach(o -> sb.append((String)o).append(","));
        sb.setLength(sb.length() - 1);
        sb.append(" from").append(' ').append(table).append(' ');
        sb.append(" where ").append(condition);
        return sb.toString();
    }

    public static String insert(String table, Collection<String> fields) {
        StringBuilder sb = new StringBuilder();
        DataSqlBuilder.appendSchema(sb);
        sb.append("insert into").append(' ').append(table).append('(');
        fields.forEach(o -> sb.append((String)o).append(","));
        sb.setLength(sb.length() - 1);
        sb.append(")values(");
        fields.forEach(o -> sb.append("?,"));
        sb.setLength(sb.length() - 1);
        sb.append(')');
        return sb.toString();
    }

    public static String delete(String table, Collection<String> keyFields, Collection<ValueKind> keyValueKinds) {
        StringBuilder sb = new StringBuilder();
        DataSqlBuilder.appendSchema(sb);
        sb.append("delete from ").append(table).append(' ');
        DataSqlBuilder.appendCondition(keyFields, keyValueKinds, sb);
        return sb.toString();
    }

    public static String update(String table, Collection<String> fields, Collection<String> keyFields, Collection<ValueKind> keyValueKinds) {
        StringBuilder sb = new StringBuilder();
        DataSqlBuilder.appendSchema(sb);
        sb.append("update ").append(table).append(" set ");
        fields.forEach(o -> sb.append((String)o).append("=?,"));
        sb.setLength(sb.length() - 1);
        DataSqlBuilder.appendCondition(keyFields, keyValueKinds, sb);
        return sb.toString();
    }

    private static void appendSchema(StringBuilder sb) {
        if (multiTenant) {
            sb.append("/*!mycat:schema=").append(schemePrefix).append(Env.getTenantName()).append("*/ ");
        }
    }

    private static void appendCondition(Collection<String> keyFields, Collection<ValueKind> keyValueKinds, StringBuilder sb) {
        if (keyFields.size() == 0) {
            return;
        }
        sb.append(" where");
        Iterator<ValueKind> i = keyValueKinds.iterator();
        keyFields.forEach(o -> {
            sb.append(' ').append((String)o);
            switch ((ValueKind)((Object)((Object)i.next()))) {
                case SINGLE: {
                    sb.append("=? and");
                    break;
                }
                case RANGE: {
                    sb.append(" between ? and ? and");
                    break;
                }
                case LIST: {
                    sb.append(" in(select V from BIZ_TEMP_VALUE where ID=?)and");
                    break;
                }
                case RANGE_GREATER: {
                    sb.append(">=? and");
                    break;
                }
                case RANGE_LESSER: {
                    sb.append("<=? and");
                }
            }
        });
        sb.setLength(sb.length() - 3);
    }

    static {
        schemePrefix = "";
    }
}

