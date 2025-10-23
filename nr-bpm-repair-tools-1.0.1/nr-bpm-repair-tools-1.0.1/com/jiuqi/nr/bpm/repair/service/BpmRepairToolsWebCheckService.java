/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bpm.repair.service;

import com.jiuqi.nr.bpm.repair.db.utils.DBTableUtils;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.sql.StateTableSqlBuilder;
import com.jiuqi.nr.bpm.repair.web.param.BpmRepairToolsParam;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmRepairToolsWebCheckService {
    @Autowired
    private DBTableUtils dbTableUtils;
    @Autowired
    private IRunTimeViewController defineService;
    @Autowired
    private DataModelService dataModelService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getErrorRecords(BpmRepairToolsParam envParam) {
        StringBuilder logDetail = new StringBuilder();
        TaskDefine taskDefine = this.defineService.getTask(envParam.getTaskId());
        if (taskDefine == null) {
            logDetail.append("\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a" + envParam.getTaskId());
            return logDetail.toString();
        }
        FormSchemeDefine formScheme = this.getFormScheme(taskDefine, envParam.getPeriod());
        if (formScheme == null) {
            logDetail.append("\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a" + envParam.getTaskId() + "," + envParam.getPeriod());
            return logDetail.toString();
        }
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if (flowsSetting.getFlowsType() != FlowsType.DEFAULT) {
            logDetail.append("\u5f53\u524d\u4efb\u52a1\u4e0d\u662f\u9ed8\u8ba4\u6d41\u7a0b\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return logDetail.toString();
        }
        String stateTableName = String.format("%s%s", "SYS_UP_ST_", formScheme.getFormSchemeCode());
        TableModelDefine stateTableDefine = this.dataModelService.getTableModelDefineByCode(stateTableName);
        if (stateTableDefine == null) {
            logDetail.append("\u72b6\u6001\u8868\u6a21\u578b\u4e3a\u7a7a\uff01\uff01");
            return logDetail.toString();
        }
        String stateHiTableName = String.format("%s%s", "SYS_UP_HI_", formScheme.getFormSchemeCode());
        TableModelDefine stateHiTableDefine = this.dataModelService.getTableModelDefineByCode(stateHiTableName);
        if (stateHiTableDefine == null) {
            logDetail.append("\u5386\u53f2\u72b6\u6001\u8868\u6a21\u578b\u4e3a\u7a7a\uff01\uff01");
            return logDetail.toString();
        }
        BpmRepairToolsEnv env = new BpmRepairToolsEnv();
        env.setTaskDefine(taskDefine);
        env.setPeriod(envParam.getPeriod());
        env.setFormScheme(formScheme);
        env.setFlowsSetting(flowsSetting);
        env.setStateTableDefine(stateTableDefine);
        env.setStateHiTableDefine(stateHiTableDefine);
        Connection connection = null;
        try {
            Map<String, String> formId2PrintTitle = this.formId2PrintTitle(formScheme, env);
            connection = this.dbTableUtils.createConnection();
            this.printStateErrorRecords(connection, env, logDetail, formId2PrintTitle);
            this.dbTableUtils.releaseConnection(connection);
        }
        catch (SQLException e) {
            try {
                logDetail.append(e.getMessage());
                this.dbTableUtils.releaseConnection(connection);
            }
            catch (Throwable throwable) {
                this.dbTableUtils.releaseConnection(connection);
                throw throwable;
            }
        }
        return logDetail.toString();
    }

    protected void printStateErrorRecords(Connection connection, BpmRepairToolsEnv env, StringBuilder logDetail, Map<String, String> formId2PrintTitle) throws SQLException {
        int errorRecordCount = this.selectErrorRecordCount(connection, env);
        if (errorRecordCount == 0) {
            logDetail.append("\u65e0\u6d41\u7a0b\u72b6\u6001\u9519\u8bef\u7684\u6570\u636e\uff01\uff01\uff01");
            return;
        }
        logDetail.append(">>>> \u603b\u5171\u6709").append(errorRecordCount).append("\u6761\u9519\u8bef\u8bb0\u5f55").append("\n");
        logDetail.append("-------------------- \u72b6\u6001\u9519\u8bef\u8bb0\u5f55 ------------------").append("\n");
        ArrayList<String> printColumns = new ArrayList<String>();
        printColumns.add("MDCODE");
        logDetail.append("-- \u5355\u4f4d --");
        WorkFlowType workFlowType = env.getFlowsSetting().getWordFlowType();
        int formOrGroupIndex = -1;
        if (WorkFlowType.FORM == workFlowType || WorkFlowType.GROUP == workFlowType) {
            printColumns.add("FORMID");
            logDetail.append(" | ").append("-- \u8868\u5355\u6216\u5206\u7ec4 --");
            formOrGroupIndex = printColumns.size() - 1;
        }
        printColumns.add("PREVEVENT");
        logDetail.append(" | ").append("-- \u524d\u7f6e\u52a8\u4f5c --");
        printColumns.add("CURNODE");
        logDetail.append(" | ").append("-- \u5f53\u524d\u8282\u70b9 --");
        logDetail.append("\n");
        String sql = StateTableSqlBuilder.selectErrorRecordSQL(env, printColumns);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                for (int i = 0; i < printColumns.size(); ++i) {
                    logDetail.append(i == formOrGroupIndex ? formId2PrintTitle.get(resultSet.getString(i + 1)) : resultSet.getString(i + 1)).append(" | ");
                }
                logDetail.append("\n");
            }
        }
    }

    protected FormSchemeDefine getFormScheme(TaskDefine taskDefine, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.defineService.getSchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
        if (schemePeriodLinkDefine != null) {
            return this.defineService.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        }
        return null;
    }

    protected int selectErrorRecordCount(Connection connection, BpmRepairToolsEnv env) throws SQLException {
        String countFieldName = "count";
        String sql = StateTableSqlBuilder.selectErrorCountSQL(env, countFieldName);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int n = resultSet.getInt(countFieldName);
            return n;
        }
    }

    protected Map<String, String> formId2PrintTitle(FormSchemeDefine formScheme, BpmRepairToolsEnv env) {
        HashMap<String, String> printTitle = new HashMap<String, String>();
        WorkFlowType workFlowType = env.getFlowsSetting().getWordFlowType();
        if (WorkFlowType.FORM == workFlowType) {
            List formDefines = this.defineService.listFormByFormScheme(formScheme.getKey());
            formDefines.forEach(formDefine -> printTitle.put(formDefine.getKey(), "\u3010" + formDefine.getFormCode() + "\u3011" + formDefine.getTitle()));
        } else if (WorkFlowType.GROUP == workFlowType) {
            List formGroupDefines = this.defineService.listFormGroupByFormScheme(formScheme.getKey());
            formGroupDefines.forEach(group -> printTitle.put(group.getKey(), "\u3010" + group.getCode() + "\u3011" + group.getTitle()));
        }
        return printTitle;
    }
}

