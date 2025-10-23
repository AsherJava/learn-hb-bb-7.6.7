/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 */
package com.jiuqi.nr.bpm.repair.jobs;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.nr.bpm.repair.db.utils.DBTableUtils;
import com.jiuqi.nr.bpm.repair.jobs.NrBpmDefaultWorkflowStateRepairJob;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.monitor.IBpmRepairTaskMonitor;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.DaTangFormTempTable;
import java.sql.Connection;
import java.sql.SQLException;

@RealTimeJob(group="bpm-repair-job-with_da-tang", groupTitle="\u5927\u5510\u9879\u76ee\u72b6\u6001\u9519\u4e71\u4fee\u590d", subject="\u62a5\u8868")
public class NrDaTangStateRepairJob
extends NrBpmDefaultWorkflowStateRepairJob {
    public static final String TASK_NAME = "bpm-repair-job-with_da-tang";
    public static final String TASK_TITLE = "\u5927\u5510\u9879\u76ee\u72b6\u6001\u9519\u4e71\u4fee\u590d";

    @Override
    protected void executeRepair(JobContext jobContext, IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env) {
        monitor.info("\u6b63\u5728\u521b\u5efa\u4e34\u65f6\u8868...");
        DBTableUtils dbTableUtils = this.getDBTableUtils();
        DaTangFormTempTable temp_form_table = new DaTangFormTempTable();
        Connection connection = null;
        connection = dbTableUtils.createConnection();
        try {
            dbTableUtils.createTable(connection, temp_form_table);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (SQLInterpretException e) {
            throw new RuntimeException(e);
        }
        monitor.info("\u6210\u529f\u521b\u5efa\u3010\u8868\u5355\u3011\u6620\u5c04\u4e34\u65f6\u8868\uff1a" + temp_form_table.getTableName(), 2.0);
        monitor.info("-- ****************************************               **************************************** --");
        monitor.info("-- **************************************** \u4e00\u3001\u4fee\u590d\u4ea7\u6743\u53e3\u5f84 **************************************** --");
        monitor.info("-- ****************************************               **************************************** --");
    }
}

