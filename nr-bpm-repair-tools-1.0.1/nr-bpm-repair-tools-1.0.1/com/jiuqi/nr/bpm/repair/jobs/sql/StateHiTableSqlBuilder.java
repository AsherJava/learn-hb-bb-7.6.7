/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.repair.jobs.sql;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.bpm.repair.enumeration.submit_upload_audit_confirm_hi;
import com.jiuqi.nr.bpm.repair.enumeration.submit_upload_audit_hi;
import com.jiuqi.nr.bpm.repair.enumeration.upload_audit_confirm_hi;
import com.jiuqi.nr.bpm.repair.enumeration.upload_audit_hi;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateHiErrorBizKeysTempTable;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateHiErrorRecordsTempTable;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateHiTableSqlBuilder {
    public static final String mdCodeFiledName = "MDCODE";
    public static final String periodFieldName = "PERIOD";
    public static final String formIdFieldName = "FORMID";
    public static final String bizKeyFieldName = "BIZKEYORDER";
    public static final String curNodeFieldName = "CURNODE";
    public static final String curEventFieldName = "CUREVENT";
    public static final String updateTimeFieldName = "TIME_";

    public static String selectErrorCountSQL(BpmRepairToolsEnv env, String countFieldName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select COUNT(*) as ").append(countFieldName);
        sql.append(" from ").append(env.getStateHiTableDefine().getName()).append(" t0 ");
        StateHiTableSqlBuilder.whereCondition(env, sql);
        return sql.toString();
    }

    public static String selectRepeatErrorRecordsSQL(BpmRepairToolsEnv env, List<String> insCols, List<String> groupCols, List<String> selectCols, List<String> orderCols) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ").append(selectCols.stream().map(col -> "t2." + col).collect(Collectors.joining(",")));
        sql.append(" FROM ").append(env.getStateHiTableDefine().getName()).append(" t2 ");
        sql.append("     RIGHT JOIN ( ");
        sql.append("             SELECT ").append(insCols.stream().map(col -> "t1." + col).collect(Collectors.joining(",")));
        sql.append("             FROM ( ");
        sql.append("             SELECT ").append(insCols.stream().map(col -> "t0." + col).collect(Collectors.joining(",")));
        sql.append("             FROM ").append(env.getStateHiTableDefine().getName()).append(" t0 ");
        sql.append("             WHERE t0.").append(periodFieldName).append(" = '").append(env.getPeriod()).append("'");
        sql.append("             GROUP BY ").append(groupCols.stream().map(col -> "t0." + col).collect(Collectors.joining(",")));
        sql.append("             HAVING COUNT(*) > 1 ");
        sql.append("         ) t1 ");
        sql.append("         GROUP BY ").append(insCols.stream().map(col -> "t1." + col).collect(Collectors.joining(",")));
        sql.append("     ) t3 ");
        sql.append(" ON ").append(insCols.stream().map(col -> "t2." + col + "=t3." + col).collect(Collectors.joining(" AND ")));
        sql.append(" WHERE t2.").append(periodFieldName).append("='").append(env.getPeriod()).append("' ");
        sql.append(" ORDER BY ").append(orderCols.stream().map(col -> "t2." + col).collect(Collectors.joining(",")));
        return sql.toString();
    }

    public static String insertErrorRecordsSQL(BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi) {
        List insertColumns = errorRecordsHi.getAllLogicFields().stream().map(LogicField::getFieldName).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ").append(errorRecordsHi.getTableName());
        sql.append(" SELECT ").append(insertColumns.stream().map(colName -> "t0." + colName).collect(Collectors.joining(",")));
        sql.append(" FROM ").append(env.getStateHiTableDefine().getName()).append(" t0");
        StateHiTableSqlBuilder.whereCondition(env, sql);
        return sql.toString();
    }

    public static String groupByErrorRecordsSQL(BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi, List<String> groupColumns) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(groupColumns.stream().map(col -> "t0." + col).collect(Collectors.joining(",")));
        sql.append(" from ").append(errorRecordsHi.getTableName()).append(" t0 ");
        sql.append(" group by ");
        sql.append(groupColumns.stream().map(col -> "t0." + col).collect(Collectors.joining(",")));
        return sql.toString();
    }

    public static String updateStateByErrorRecordSQL(BpmRepairToolsEnv env, Map<String, String> errorPrevEventToNodeMap) {
        Map<String, String> actionToNodeMap = StateHiTableSqlBuilder.getActionToNodeMap(env.getFlowsSetting());
        StringBuilder sql = new StringBuilder();
        TableModelDefine stateTableDefine = env.getStateHiTableDefine();
        sql.append(" update ");
        sql.append(stateTableDefine.getName());
        sql.append(" set ");
        sql.append(curEventFieldName).append("='").append(errorPrevEventToNodeMap.get(curEventFieldName)).append("', ");
        sql.append(curNodeFieldName).append("='").append(actionToNodeMap.get(errorPrevEventToNodeMap.get(curEventFieldName))).append("' ");
        sql.append(" where ");
        sql.append(periodFieldName).append(" = '").append(env.getPeriod()).append("' ");
        sql.append(" and ").append(curNodeFieldName).append(" = '").append(errorPrevEventToNodeMap.get(curNodeFieldName)).append("' ");
        sql.append(" and ").append(curEventFieldName).append(" = '").append(errorPrevEventToNodeMap.get(curEventFieldName)).append("' ");
        return sql.toString();
    }

    public static String insertHiMaxTimeBizKeysSQL(BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi, StateHiErrorBizKeysTempTable errorBizKeys) {
        String MAX_TIME = "MAX_TIME";
        List groupColumns = errorRecordsHi.getPrimaryFields().stream().map(LogicField::getFieldName).filter(fieldName -> !bizKeyFieldName.equals(fieldName)).collect(Collectors.toList());
        groupColumns.add(curEventFieldName);
        groupColumns.add(curNodeFieldName);
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ").append(errorBizKeys.getTableName());
        sql.append(" SELECT t0. ").append(bizKeyFieldName);
        sql.append(" FROM ").append(env.getStateHiTableDefine().getName()).append(" t0 ");
        sql.append(" RIGHT JOIN ( ");
        sql.append("              SELECT ").append(groupColumns.stream().map(colName -> "c1." + colName).collect(Collectors.joining(","))).append(", MAX(TIME_) as ").append(MAX_TIME);
        sql.append("              FROM ").append(env.getStateHiTableDefine().getName()).append(" c1 ");
        sql.append("              RIGHT JOIN ").append(errorRecordsHi.getTableName()).append(" c2 ");
        sql.append("              ON c1.").append(bizKeyFieldName).append(" = c2.").append(bizKeyFieldName);
        sql.append("              GROUP BY ").append(groupColumns.stream().map(colName -> "c1." + colName).collect(Collectors.joining(",")));
        sql.append("              HAVING COUNT(*) > 1 ");
        sql.append("          ) t1 ");
        sql.append(" ON ");
        sql.append(groupColumns.stream().map(colName -> "t0." + colName + " = t1." + colName).collect(Collectors.joining(" AND ")));
        sql.append(" AND t0.").append(updateTimeFieldName).append(" = t1.").append(MAX_TIME);
        sql.append(" where t0.").append(periodFieldName).append(" = '").append(env.getPeriod()).append("' ");
        sql.append(" ORDER BY ").append(Stream.of(mdCodeFiledName, updateTimeFieldName).map(colName -> "t0." + colName).collect(Collectors.joining(",")));
        return sql.toString();
    }

    public static String deleteRepeatErrorRecordsSQL(BpmRepairToolsEnv env, StateHiErrorBizKeysTempTable errorBizKeys) {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE ");
        sql.append(" FROM ").append(env.getStateHiTableDefine().getName());
        sql.append(" WHERE ").append(bizKeyFieldName);
        sql.append(" IN ( ");
        sql.append(" SELECT ").append(bizKeyFieldName);
        sql.append(" FROM ").append(errorBizKeys.getTableName());
        sql.append(" ) ");
        return sql.toString();
    }

    public static String deleteRepeatErrorRecordsSQL(BpmRepairToolsEnv env, List<String> rowKeys) {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE ");
        sql.append(" FROM ").append(env.getStateHiTableDefine().getName());
        sql.append(" WHERE ").append(bizKeyFieldName);
        sql.append(" IN ( ");
        sql.append(rowKeys.stream().map(val -> "'" + val + "'").collect(Collectors.joining(",")));
        sql.append(" ) ");
        return sql.toString();
    }

    private static void whereCondition(BpmRepairToolsEnv env, StringBuilder sql) {
        sql.append(" where t0.").append(periodFieldName).append(" = '").append(env.getPeriod()).append("' ");
        List<Map<String, String>> actionToNodeMapGroup = StateHiTableSqlBuilder.getActionToNodeMapGroup(env.getFlowsSetting());
        sql.append(" and ").append("(");
        for (int i = 0; i < actionToNodeMapGroup.size(); ++i) {
            Map<String, String> actionToNodeMap = actionToNodeMapGroup.get(i);
            sql.append("( ");
            sql.append("t0.").append(curEventFieldName).append(" = '").append(actionToNodeMap.get(curEventFieldName)).append("'");
            sql.append(" and ");
            sql.append("t0.").append(curNodeFieldName).append(" <> '").append(actionToNodeMap.get(curNodeFieldName)).append("'");
            sql.append(" )");
            if (i == actionToNodeMapGroup.size() - 1) continue;
            sql.append(" or ");
        }
        sql.append(") ");
    }

    private static List<Map<String, String>> getActionToNodeMapGroup(TaskFlowsDefine flowsSetting) {
        if (flowsSetting.isUnitSubmitForCensorship() && flowsSetting.isDataConfirm()) {
            return submit_upload_audit_confirm_hi.getActionToNodeMapGroup();
        }
        if (flowsSetting.isUnitSubmitForCensorship()) {
            return submit_upload_audit_hi.getActionToNodeMapGroup();
        }
        if (flowsSetting.isDataConfirm()) {
            return upload_audit_confirm_hi.getActionToNodeMapGroup();
        }
        return upload_audit_hi.getActionToNodeMapGroup();
    }

    private static Map<String, String> getActionToNodeMap(TaskFlowsDefine flowsSetting) {
        if (flowsSetting.isUnitSubmitForCensorship() && flowsSetting.isDataConfirm()) {
            return submit_upload_audit_confirm_hi.getActionToNodeMap();
        }
        if (flowsSetting.isUnitSubmitForCensorship()) {
            return submit_upload_audit_hi.getActionToNodeMap();
        }
        if (flowsSetting.isDataConfirm()) {
            return upload_audit_confirm_hi.getActionToNodeMap();
        }
        return upload_audit_hi.getActionToNodeMap();
    }
}

