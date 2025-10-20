/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime;

import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunner;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory;
import com.jiuqi.bi.core.jobs.realtime.local.RealTimeLocalJobRunner;

public class RealTimeJobManager {
    private static RealTimeJobManager manager = new RealTimeJobManager();
    private static RealTimeJobRunner runner = RealTimeJobRunnerFactory.getInstance().getDefaultRunner();
    private static JobMonitorManager monitorManager = new JobMonitorManager(new JobFactory(){

        @Override
        public String getJobCategoryId() {
            return "com.jiuqi.bi.jobs.realtime";
        }

        @Override
        public String getJobCategoryTitle() {
            return "\u5373\u65f6\u4efb\u52a1";
        }

        @Override
        public JobExecutor createJobExecutor(String jobId) throws JobsException {
            return null;
        }
    });

    public static RealTimeJobManager getInstance() {
        return manager;
    }

    public String commit(AbstractRealTimeJob job) throws JobsException {
        try {
            if (runner == null) {
                runner = new RealTimeLocalJobRunner();
                RealTimeJobRunnerFactory.getInstance().setRunner(runner);
            }
            String instanceId = runner.commit(job);
            return instanceId;
        }
        catch (JobsException var4) {
            throw new JobsException(var4);
        }
    }

    public void cancel(String instanceId) throws JobsException {
        runner.cancel(instanceId);
    }

    public JobMonitorManager getMonitorManager() {
        return monitorManager;
    }

    public boolean isLocalRealTimeJobMode() {
        return System.getProperty("JQBI.LOCALREALJOB") != null;
    }
}

