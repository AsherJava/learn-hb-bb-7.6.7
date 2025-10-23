/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 */
package com.jiuqi.nr.bpm.repair.jobs.sql;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableEntity;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.sql.StateTableSqlBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class StateErrorRecordSqlBuilder {
    private StateErrorRecordSqlBuilder() {
    }

    public static String selectErrorRecordsSQL(DBTableEntity errorRecords, List<String> selectColumns) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append(String.join((CharSequence)",", selectColumns));
        sql.append(" from ");
        sql.append(errorRecords.getTableName());
        return sql.toString();
    }

    public static String groupByErrorRecordsSQL(DBTableEntity errorRecords, List<String> selectColumns, List<String> groupColumns) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append(String.join((CharSequence)",", selectColumns));
        sql.append(" from ");
        sql.append(errorRecords.getTableName());
        sql.append(" group by ");
        sql.append(String.join((CharSequence)",", groupColumns));
        return sql.toString();
    }

    public static String insertErrorRecordsSQL(BpmRepairToolsEnv env, DBTableEntity errorRecords) {
        List<String> insertColumns = errorRecords.getAllLogicFields().stream().map(LogicField::getFieldName).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into ");
        sql.append(errorRecords.getTableName());
        sql.append("(");
        sql.append(String.join((CharSequence)",", insertColumns));
        sql.append(") ");
        sql.append(StateTableSqlBuilder.selectErrorRecordSQL(env, insertColumns));
        return sql.toString();
    }
}

