/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.repair.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableEntity;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableUtils;
import com.jiuqi.nr.bpm.repair.jobs.BpmRepairTaskExecutor;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.monitor.AsyncJobResult;
import com.jiuqi.nr.bpm.repair.jobs.monitor.IBpmRepairTaskMonitor;
import com.jiuqi.nr.bpm.repair.jobs.sql.StateErrorRecordSqlBuilder;
import com.jiuqi.nr.bpm.repair.jobs.sql.StateTableSqlBuilder;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateErrorRecordsTempTable;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateHiErrorBizKeysTempTable;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateHiErrorRecordsTempTable;
import com.jiuqi.nr.bpm.repair.service.BpmRepairStateService;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
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
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

public class NrBpmDefaultWorkflowStateRepairJob
extends BpmRepairTaskExecutor {
    public static final String TASK_NAME = "bpm-repair-job-with_nr_default_workflow_state";
    public static final String TASK_TITLE = "\u3010\u9ed8\u8ba4\u6d41\u7a0b-\u72b6\u6001\u8868\u3011\u4fee\u590d\u4efb\u52a1";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void executeRepair(JobContext jobContext, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env) {
        TaskFlowsDefine flowsSetting = env.getFlowsSetting();
        if (!this.isDefaultWorkFlow(flowsSetting)) {
            monitor.setJobResult(AsyncJobResult.FAILURE, "\u5f53\u524d\u4efb\u52a1\u4e0d\u662f\u9ed8\u8ba4\u6d41\u7a0b\uff0c\u6267\u884c\u5931\u8d25\uff01\uff01");
            return;
        }
        monitor.info("\u9ed8\u8ba4\u6d41\u7a0b\u5b9a\u4e49\uff1a" + this.printWorkflowDefine(flowsSetting));
        Connection connection = null;
        DBTableUtils dbTableUtils = this.getDBTableUtils();
        StateErrorRecordsTempTable errorRecords = new StateErrorRecordsTempTable(flowsSetting.getWordFlowType());
        StateHiErrorRecordsTempTable errorRecordsHi = new StateHiErrorRecordsTempTable(flowsSetting.getWordFlowType());
        StateHiErrorBizKeysTempTable errorBizKeys = new StateHiErrorBizKeysTempTable();
        try {
            connection = dbTableUtils.createConnection();
            this.executeRepairState(connection, monitor, dbTableUtils, env, errorRecords);
            this.executeRepairStateHi(connection, monitor, env, dbTableUtils, errorRecordsHi, errorBizKeys);
            return;
        }
        catch (SQLInterpretException | SQLException e) {
            monitor.error(e.getMessage(), e);
            monitor.setJobResult(AsyncJobResult.FAILURE, e.getMessage());
            LoggerFactory.getLogger(NrBpmDefaultWorkflowStateRepairJob.class).error(e.getMessage(), e);
            return;
        }
        finally {
            try {
                if (errorRecords.isExist()) {
                    monitor.info("\u6b63\u5728\u5220\u9664\u4e34\u65f6\u8868\uff1a" + errorRecords.getTableName());
                    dbTableUtils.dropTable(connection, errorRecords);
                }
                if (errorRecordsHi.isExist()) {
                    monitor.info("\u6b63\u5728\u5220\u9664\u4e34\u65f6\u8868\uff1a" + errorRecordsHi.getTableName());
                    dbTableUtils.dropTable(connection, errorRecordsHi);
                }
                if (errorBizKeys.isExist()) {
                    monitor.info("\u6b63\u5728\u5220\u9664\u4e34\u65f6\u8868\uff1a" + errorBizKeys.getTableName());
                    dbTableUtils.dropTable(connection, errorBizKeys);
                }
            }
            catch (SQLInterpretException | SQLException e) {
                monitor.error(e.getMessage(), e);
                LoggerFactory.getLogger(NrBpmDefaultWorkflowStateRepairJob.class).error(e.getMessage(), e);
            }
            finally {
                monitor.info("\u6b63\u5728\u91ca\u653e\u6570\u636e\u5e93\u8fde\u63a5...");
                dbTableUtils.releaseConnection(connection);
            }
        }
    }

    protected void executeRepairState(Connection connection, IBpmRepairTaskMonitor monitor, DBTableUtils dbTableUtils, BpmRepairToolsEnv env, StateErrorRecordsTempTable errorRecords) throws SQLException, SQLInterpretException {
        monitor.info("------------ \u6b63\u5728\u4fee\u590d\u72b6\u6001\u8868 ------------ ", 5.0);
        monitor.info("\u6b63\u5728\u68c0\u67e5\u662f\u5426\u6709\u72b6\u6001\u9519\u8bef\u7684\u8bb0\u5f55...", 2.0);
        int errorRecordCount = this.selectErrorRecordCount(connection, env);
        if (errorRecordCount == 0) {
            monitor.setJobResult(AsyncJobResult.SUCCESS, "\u6ca1\u6709\u9519\u8bef\u8bb0\u5f55\uff0c\u4fee\u590d\u7ed3\u675f\uff01\uff01\uff01");
            return;
        }
        monitor.info(">>>> \u603b\u5171\u6709" + errorRecordCount + "\u6761\u9519\u8bef\u8bb0\u5f55");
        monitor.info("\u5f00\u59cb\u6267\u884c\u72b6\u6001\u4fee\u590d...");
        this.createErrorRecordsTempTable(connection, monitor, dbTableUtils, errorRecords);
        this.insertErrorRecords(connection, monitor, env, errorRecords);
        this.printErrorRecords(connection, monitor, errorRecords);
        this.repairErrorRecords(connection, monitor, errorRecords, env);
        this.resetUnitState(monitor, env, errorRecords);
    }

    protected void executeRepairStateHi(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, DBTableUtils dbTableUtils, StateHiErrorRecordsTempTable errorRecordsHi, StateHiErrorBizKeysTempTable errorBizKeys) throws SQLException, SQLInterpretException {
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

    protected void createErrorRecordsTempTable(Connection connection, IBpmRepairTaskMonitor monitor, DBTableUtils dbTableUtils, StateErrorRecordsTempTable errorRecords) throws SQLException, SQLInterpretException {
        this.dropErrorRecordsTempTable(connection, monitor, errorRecords, dbTableUtils);
        monitor.info("\u6b63\u5728\u521b\u5efa\u9519\u8bef\u8bb0\u5f55\u4e34\u65f6\u8868\uff1a" + errorRecords.getTableName(), 5.0);
        dbTableUtils.createTable(connection, errorRecords);
        errorRecords.setExist(true);
    }

    protected void dropErrorRecordsTempTable(Connection connection, IBpmRepairTaskMonitor monitor, DBTableEntity tempTable, DBTableUtils dbTableUtils) {
        try {
            dbTableUtils.dropTable(connection, tempTable);
        }
        catch (SQLInterpretException | SQLException throwable) {
            // empty catch block
        }
    }

    protected void insertErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, DBTableEntity errorRecords) throws SQLException {
        monitor.info("\u6b63\u5728\u7b5b\u9009\u9519\u8bef\u8bb0\u5f55\u5230\u4e34\u65f6\u8868...", 5.0);
        String sql = StateErrorRecordSqlBuilder.insertErrorRecordsSQL(env, errorRecords);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            statement.execute();
        }
    }

    protected void printErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, DBTableEntity errorRecords) throws SQLException {
        monitor.info("\u6b63\u5728\u6253\u5370\u9519\u8bef\u8bb0\u5f55...", 5.0);
        monitor.warn(">>>>>> = \u9519\u8bef\u8bb0\u5f55\u5217\u8868");
        List<String> printColumns = errorRecords.getAllLogicFields().stream().map(LogicField::getFieldName).collect(Collectors.toList());
        String sql = StateErrorRecordSqlBuilder.selectErrorRecordsSQL(errorRecords, printColumns);
        try (PreparedStatement statement = connection.prepareStatement(sql);){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String message = "";
                for (int colIdx = 0; colIdx < printColumns.size(); ++colIdx) {
                    message = message + resultSet.getString(colIdx + 1) + " | ";
                }
                monitor.warn(message);
            }
        }
        monitor.warn(">>>>>> = \u9519\u8bef\u8bb0\u5f55\u5217\u8868 ", 5.0);
    }

    protected void repairErrorRecords(Connection connection, IBpmRepairTaskMonitor monitor, DBTableEntity errorRecords, BpmRepairToolsEnv env) throws SQLException {
        monitor.info("\u6b63\u5728\u4fee\u590d\u72b6\u6001\u8868...", 5.0);
        List<Map<String, String>> errorPrevEventToNodeRecords = this.selectErrorPrevEventToNodeRecords(connection, errorRecords);
        for (Map<String, String> errorPrevEventToNodeMap : errorPrevEventToNodeRecords) {
            monitor.info("\u6b63\u5728\u4fee\u590d\uff1a\u524d\u7f6e\u52a8\u4f5c\u662f\u3010" + errorPrevEventToNodeMap.get("PREVEVENT") + "\u3011\u5f53\u524d\u8282\u70b9\u662f\u3010" + errorPrevEventToNodeMap.get("CURNODE") + "\u3011\u7684\u6240\u6709\u9519\u8bef\u8bb0\u5f55\uff01\uff01");
            String sql = StateTableSqlBuilder.updateStateByErrorRecordSQL(env, errorPrevEventToNodeMap);
            try (PreparedStatement statement = connection.prepareStatement(sql);){
                statement.execute();
            }
            monitor.setJobProgress(5.0);
        }
        monitor.info("\u72b6\u6001\u8868\u4fee\u590d\u7ed3\u675f!!!");
    }

    protected void resetUnitState(IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, StateErrorRecordsTempTable errorRecords) {
        if (env.getFlowsSetting().getWordFlowType() == WorkFlowType.FORM || env.getFlowsSetting().getWordFlowType() == WorkFlowType.GROUP) {
            monitor.info("\u6b63\u5728\u4fee\u590d\u5355\u4f4d\u72b6\u6001...");
            BpmRepairStateService repairStateService = this.getBpmRepairStateService();
            repairStateService.batchUpdateState(monitor, env, errorRecords);
        }
    }

    protected List<Map<String, String>> selectErrorPrevEventToNodeRecords(Connection connection, DBTableEntity errorRecords) throws SQLException {
        ArrayList<Map<String, String>> errorPrevEventToNodeRecords = new ArrayList<Map<String, String>>();
        List<String> groupColumns = Arrays.asList("PREVEVENT", "CURNODE");
        String sql = StateErrorRecordSqlBuilder.groupByErrorRecordsSQL(errorRecords, groupColumns, groupColumns);
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
}

