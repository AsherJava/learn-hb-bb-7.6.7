/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bpm.repair.jobs.sql;

import com.jiuqi.nr.bpm.repair.enumeration.submit_upload_audit;
import com.jiuqi.nr.bpm.repair.enumeration.submit_upload_audit_confirm;
import com.jiuqi.nr.bpm.repair.enumeration.upload_audit;
import com.jiuqi.nr.bpm.repair.enumeration.upload_audit_confirm;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import java.util.Map;

public class StateTableSqlBuilder {
    public static final String mdCodeFieldName = "MDCODE";
    public static final String formIdFieldName = "FORMID";
    public static final String periodFieldName = "PERIOD";
    public static final String curNodeFieldName = "CURNODE";
    public static final String prevEventFieldName = "PREVEVENT";
    public static final String formIdConst = "11111111-1111-1111-1111-111111111111";

    private StateTableSqlBuilder() {
    }

    public static String updateStateByErrorRecordSQL(BpmRepairToolsEnv env, Map<String, String> errorPrevEventToNodeMap) {
        Map<String, String> actionToNodeMap = StateTableSqlBuilder.getActionToNodeMap(env.getFlowsSetting());
        TableModelDefine stateTableDefine = env.getStateTableDefine();
        StringBuilder sql = new StringBuilder();
        sql.append(" update ");
        sql.append(stateTableDefine.getName());
        sql.append(" set ");
        sql.append(prevEventFieldName).append("='").append(errorPrevEventToNodeMap.get(prevEventFieldName)).append("', ");
        sql.append(curNodeFieldName).append("='").append(actionToNodeMap.get(errorPrevEventToNodeMap.get(prevEventFieldName))).append("' ");
        sql.append(" where ");
        sql.append(periodFieldName).append(" = '").append(env.getPeriod()).append("' ");
        sql.append(" and ").append(curNodeFieldName).append(" = '").append(errorPrevEventToNodeMap.get(curNodeFieldName)).append("' ");
        sql.append(" and ").append(prevEventFieldName).append(" = '").append(errorPrevEventToNodeMap.get(prevEventFieldName)).append("' ");
        if (env.getFlowsSetting().getWordFlowType() == WorkFlowType.FORM || env.getFlowsSetting().getWordFlowType() == WorkFlowType.GROUP) {
            sql.append(" and ").append(formIdFieldName).append("  <> '").append(formIdConst).append("'");
        }
        return sql.toString();
    }

    public static String selectErrorCountSQL(BpmRepairToolsEnv env, String countFieldName) {
        List<Map<String, String>> actionToNodeMapGroup = StateTableSqlBuilder.getActionToNodeMapGroup(env.getFlowsSetting());
        TableModelDefine stateTableDefine = env.getStateTableDefine();
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append("count(*) as ").append(countFieldName);
        sql.append(" from ");
        sql.append(stateTableDefine.getName());
        sql.append(" t1 ");
        StateTableSqlBuilder.whereCondition(env, actionToNodeMapGroup, sql);
        return sql.toString();
    }

    public static String selectErrorRecordSQL(BpmRepairToolsEnv env, List<String> selectColumns) {
        List<Map<String, String>> actionToNodeMapGroup = StateTableSqlBuilder.getActionToNodeMapGroup(env.getFlowsSetting());
        TableModelDefine stateTableDefine = env.getStateTableDefine();
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(String.join((CharSequence)",", selectColumns));
        sql.append(" from ");
        sql.append(stateTableDefine.getName());
        sql.append(" t1 ");
        StateTableSqlBuilder.whereCondition(env, actionToNodeMapGroup, sql);
        return sql.toString();
    }

    private static void whereCondition(BpmRepairToolsEnv env, List<Map<String, String>> actionToNodeMapGroup, StringBuilder sql) {
        sql.append(" where t1.").append(periodFieldName).append(" = '").append(env.getPeriod()).append("' ");
        sql.append(" and ").append("(");
        for (int i = 0; i < actionToNodeMapGroup.size(); ++i) {
            Map<String, String> actionToNodeMap = actionToNodeMapGroup.get(i);
            sql.append("( ");
            sql.append("t1.").append(prevEventFieldName).append(" = '").append(actionToNodeMap.get(prevEventFieldName)).append("'");
            sql.append(" and ");
            sql.append("t1.").append(curNodeFieldName).append(" <> '").append(actionToNodeMap.get(curNodeFieldName)).append("'");
            sql.append(" )");
            if (i == actionToNodeMapGroup.size() - 1) continue;
            sql.append(" or ");
        }
        sql.append(") ");
        if (env.getFlowsSetting().getWordFlowType() == WorkFlowType.FORM || env.getFlowsSetting().getWordFlowType() == WorkFlowType.GROUP) {
            sql.append("   and t1.").append(formIdFieldName).append("  <> '").append(formIdConst).append("'");
        }
    }

    private static List<Map<String, String>> getActionToNodeMapGroup(TaskFlowsDefine flowsSetting) {
        if (flowsSetting.isUnitSubmitForCensorship() && flowsSetting.isDataConfirm()) {
            return submit_upload_audit_confirm.getActionToNodeMapGroup();
        }
        if (flowsSetting.isUnitSubmitForCensorship()) {
            return submit_upload_audit.getActionToNodeMapGroup();
        }
        if (flowsSetting.isDataConfirm()) {
            return upload_audit_confirm.getActionToNodeMapGroup();
        }
        return upload_audit.getActionToNodeMapGroup();
    }

    private static Map<String, String> getActionToNodeMap(TaskFlowsDefine flowsSetting) {
        if (flowsSetting.isUnitSubmitForCensorship() && flowsSetting.isDataConfirm()) {
            return submit_upload_audit_confirm.getActionToNodeMap();
        }
        if (flowsSetting.isUnitSubmitForCensorship()) {
            return submit_upload_audit.getActionToNodeMap();
        }
        if (flowsSetting.isDataConfirm()) {
            return upload_audit_confirm.getActionToNodeMap();
        }
        return upload_audit.getActionToNodeMap();
    }
}

