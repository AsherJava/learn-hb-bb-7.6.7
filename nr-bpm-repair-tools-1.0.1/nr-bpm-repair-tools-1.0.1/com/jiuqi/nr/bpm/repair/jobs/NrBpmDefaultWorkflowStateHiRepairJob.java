/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.repair.jobs;

import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableUtils;
import com.jiuqi.nr.bpm.repair.jobs.NrBpmDefaultWorkflowStateRepairJob;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.monitor.IBpmRepairTaskMonitor;
import com.jiuqi.nr.bpm.repair.jobs.sql.StateHiTableSqlBuilder;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateHiErrorBizKeysTempTable;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateHiErrorRecordsTempTable;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RealTimeJob(group="bpm-repair-job-with_nr_default_workflow_state_hi", groupTitle="\u3010\u9ed8\u8ba4\u6d41\u7a0b-\u72b6\u6001\u8868-\u5386\u53f2\u8868\u3011\u4fee\u590d\u4efb\u52a1", subject="\u62a5\u8868")
public class NrBpmDefaultWorkflowStateHiRepairJob
extends NrBpmDefaultWorkflowStateRepairJob {
    public static final String TASK_NAME = "bpm-repair-job-with_nr_default_workflow_state_hi";
    public static final String TASK_TITLE = "\u3010\u9ed8\u8ba4\u6d41\u7a0b-\u72b6\u6001\u8868-\u5386\u53f2\u8868\u3011\u4fee\u590d\u4efb\u52a1";

    @Override
    protected void executeRepairStateHi(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, DBTableUtils dbTableUtils, StateHiErrorRecordsTempTable errorRecordsHi, StateHiErrorBizKeysTempTable errorBizKeys) throws SQLException, SQLInterpretException {
        monitor.info("----------- \u6b63\u5728\u4fee\u590d\u5386\u53f2\u72b6\u6001\u8868 ----------- ", 5.0);
        int errorRecordCount = this.selectHiErrorRecordCount(connection, env);
        if (errorRecordCount == 0) {
            monitor.info("\u5386\u53f2\u8868\u4e2d\u6ca1\u6709\u9519\u8bef\u8bb0\u5f55\uff0c\u4fee\u590d\u7ed3\u675f\uff01\uff01\uff01");
            return;
        }
        this.createHiErrorRecordsTempTable(connection, monitor, dbTableUtils, errorRecordsHi);
        this.createHiErrorBizKeysTempTable(connection, monitor, dbTableUtils, errorBizKeys);
        this.insertHiErrorRecords(connection, monitor, env, errorRecordsHi);
        this.repairHiErrorRecords(connection, monitor, env, errorRecordsHi);
        this.deleteRepeatErrorRecords(connection, monitor, env);
        monitor.info("----------- \u5386\u53f2\u72b6\u6001\u8868\u4fee\u590d\u7ed3\u675f ----------- ", 5.0);
    }

    protected int selectHiErrorRecordCount(Connection connection, BpmRepairToolsEnv env) throws SQLException {
        String countFieldName = "count";
        String sql = StateHiTableSqlBuilder.selectErrorCountSQL(env, countFieldName);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int n = resultSet.getInt(countFieldName);
            return n;
        }
    }

    protected void insertHiErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi) throws SQLException {
        monitor.info("\u6b63\u5728\u7b5b\u9009\u9519\u8bef\u8bb0\u5f55\u5230\u4e34\u65f6\u8868...", 5.0);
        String sql = StateHiTableSqlBuilder.insertErrorRecordsSQL(env, errorRecordsHi);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.execute();
        }
    }

    protected void repairHiErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi) throws SQLException {
        List<Map<String, String>> errorHiRecords = this.selectHiErrorPrevEventToNodeRecords(connection, env, errorRecordsHi);
        for (Map<String, String> errorPrevEventToNodeMap : errorHiRecords) {
            monitor.info("\u6b63\u5728\u4fee\u590d\uff1a\u5f53\u524d\u52a8\u4f5c\u662f\u3010" + errorPrevEventToNodeMap.get("CUREVENT") + "\u3011\u5f53\u524d\u8282\u70b9\u662f\u3010" + errorPrevEventToNodeMap.get("CURNODE") + "\u3011\u7684\u6240\u6709\u9519\u8bef\u8bb0\u5f55\uff01\uff01");
            String sql = StateHiTableSqlBuilder.updateStateByErrorRecordSQL(env, errorPrevEventToNodeMap);
            try (PreparedStatement statement = connection.prepareStatement(sql);){
                statement.execute();
            }
            monitor.setJobProgress(5.0);
        }
    }

    protected void deleteRepeatErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, StateHiErrorBizKeysTempTable errorBizKeys, StateHiErrorRecordsTempTable errorRecordsHi) throws SQLException {
        monitor.info("\u6b63\u5728\u68c0\u67e5\u91cd\u590d\u8bb0\u5f55\uff0c\u5e76\u5220\u9664\u6700\u540e\u4e00\u6b21\u65b0\u589e\u7684\u91cd\u590d\u8bb0\u5f55...");
        this.insertHiMaxTimeBizKeys(connection, monitor, env, errorRecordsHi, errorBizKeys);
        monitor.info("\u6b63\u5728\u5220\u9664\u91cd\u590d\u8bb0\u5f55...");
        String sql = StateHiTableSqlBuilder.deleteRepeatErrorRecordsSQL(env, errorBizKeys);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.execute();
        }
    }

    protected void deleteRepeatErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env) throws SQLException {
        monitor.info("\u6b63\u5728\u5220\u9664\u91cd\u590d\u8bb0\u5f55...");
        boolean entityUpload = WorkFlowType.ENTITY == env.getFlowsSetting().getWordFlowType();
        List<String> insCols = entityUpload ? Arrays.asList("MDCODE") : Arrays.asList("MDCODE", "FORMID");
        ArrayList<String> groupCols = new ArrayList<String>(insCols);
        groupCols.add("CUREVENT");
        groupCols.add("CURNODE");
        ArrayList<String> selectCols = new ArrayList<String>(groupCols);
        selectCols.add("BIZKEYORDER");
        ArrayList<String> orderCols = new ArrayList<String>(insCols);
        orderCols.add("TIME_");
        String sql = StateHiTableSqlBuilder.selectRepeatErrorRecordsSQL(env, insCols, groupCols, selectCols, orderCols);
        ArrayList<String> deleteRowBizKeys = new ArrayList<String>();
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            ResultSet resultSet = statement.executeQuery();
            String preRowKeys = null;
            while (resultSet.next()) {
                String currRowKeys = "";
                for (int colIdx = 0; colIdx < groupCols.size(); ++colIdx) {
                    currRowKeys = currRowKeys + resultSet.getString(colIdx + 1) + "&";
                }
                if (currRowKeys.equals(preRowKeys)) {
                    deleteRowBizKeys.add(resultSet.getString(selectCols.size()));
                    if (deleteRowBizKeys.size() == 1000) {
                        this.deleteRepeatErrorRecords(connection, env, deleteRowBizKeys);
                        deleteRowBizKeys.clear();
                    }
                }
                preRowKeys = currRowKeys;
            }
        }
        this.deleteRepeatErrorRecords(connection, env, deleteRowBizKeys);
    }

    protected void deleteRepeatErrorRecords(Connection connection, BpmRepairToolsEnv env, List<String> deleteRowBizKeys) throws SQLException {
        if (!deleteRowBizKeys.isEmpty()) {
            String delSql = StateHiTableSqlBuilder.deleteRepeatErrorRecordsSQL(env, deleteRowBizKeys);
            try (PreparedStatement statement2 = connection.prepareStatement(delSql);){
                statement2.execute();
            }
        }
    }

    protected void insertHiMaxTimeBizKeys(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi, StateHiErrorBizKeysTempTable errorBizKeys) throws SQLException {
        monitor.info("\u6b63\u5728\u7b5b\u9009\u9519\u8bef\u8bb0\u5f55\u5230\u4e34\u65f6\u8868...", 5.0);
        String sql = StateHiTableSqlBuilder.insertHiMaxTimeBizKeysSQL(env, errorRecordsHi, errorBizKeys);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.execute();
        }
    }

    protected List<Map<String, String>> selectHiErrorPrevEventToNodeRecords(Connection connection, BpmRepairToolsEnv env, StateHiErrorRecordsTempTable errorRecordsHi) throws SQLException {
        ArrayList<Map<String, String>> errorPrevEventToNodeRecords = new ArrayList<Map<String, String>>();
        List<String> groupColumns = Arrays.asList("CUREVENT", "CURNODE");
        String sql = StateHiTableSqlBuilder.groupByErrorRecordsSQL(env, errorRecordsHi, groupColumns);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int colIdx = 0; colIdx < groupColumns.size(); ++colIdx) {
                    map.put(groupColumns.get(colIdx), resultSet.getString(colIdx + 1));
                }
                errorPrevEventToNodeRecords.add(map);
            }
        }
        return errorPrevEventToNodeRecords;
    }

    protected void createHiErrorRecordsTempTable(Connection connection, IBpmRepairTaskMonitor monitor, DBTableUtils dbTableUtils, StateHiErrorRecordsTempTable errorRecordsHi) throws SQLException, SQLInterpretException {
        this.dropErrorRecordsTempTable(connection, monitor, errorRecordsHi, dbTableUtils);
        monitor.info("\u6b63\u5728\u521b\u5efa\u9519\u8bef\u8bb0\u5f55\u4e34\u65f6\u8868\uff1a" + errorRecordsHi.getTableName(), 5.0);
        dbTableUtils.createTable(connection, errorRecordsHi);
        errorRecordsHi.setExist(true);
    }

    protected void createHiErrorBizKeysTempTable(Connection connection, IBpmRepairTaskMonitor monitor, DBTableUtils dbTableUtils, StateHiErrorBizKeysTempTable errorBizKeys) throws SQLException, SQLInterpretException {
        this.dropErrorRecordsTempTable(connection, monitor, errorBizKeys, dbTableUtils);
        monitor.info("\u6b63\u5728\u521b\u5efa\u9519\u8bef\u8bb0\u5f55\u4e34\u65f6\u8868\uff1a" + errorBizKeys.getTableName(), 5.0);
        dbTableUtils.createTable(connection, errorBizKeys);
        errorBizKeys.setExist(true);
    }
}

