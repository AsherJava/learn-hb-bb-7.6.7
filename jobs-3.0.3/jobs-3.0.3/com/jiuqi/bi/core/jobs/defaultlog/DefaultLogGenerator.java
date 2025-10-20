/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.defaultlog.DefaultLogManager;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.bi.core.jobs.extension.LogType;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogGenerator
implements ILogGenerator {
    private DefaultLogManager logManager = new DefaultLogManager();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String[] getLogCustomFields() {
        return new String[0];
    }

    @Override
    public List<ILogGenerator.LogItem> getLastLogs(String instanceId, int count, boolean includeSubLog) throws Exception {
        return this.getLastLogs(instanceId, null, count, includeSubLog);
    }

    @Override
    public List<ILogGenerator.LogItem> getLastLogs(String instanceId, LogType logType, int count, boolean includeSubLog) throws Exception {
        List<String> instanceIds = this.generateInstanceIds(instanceId, includeSubLog);
        return this.logManager.getLastLogs(instanceIds, logType, count);
    }

    @Override
    public List<ILogGenerator.LogItem> getLogsBefore(String instanceId, long beforeTimeStamp, int count, boolean includeSubLog) throws Exception {
        return this.getLogsBefore(instanceId, null, beforeTimeStamp, count, includeSubLog);
    }

    @Override
    public List<ILogGenerator.LogItem> getLogsBefore(String instanceId, LogType logType, long beforeTimeStamp, int count, boolean includeSubLog) throws Exception {
        List<String> instanceIds = this.generateInstanceIds(instanceId, includeSubLog);
        return this.logManager.getLogsBefore(instanceIds, beforeTimeStamp, logType, count);
    }

    @Override
    public List<ILogGenerator.LogItem> getLastLogsAfter(String instanceId, long afterTimeStamp, boolean includeSubLog) throws Exception {
        return this.getLastLogsAfter(instanceId, null, afterTimeStamp, includeSubLog);
    }

    @Override
    public List<ILogGenerator.LogItem> getLastLogsAfter(String instanceId, LogType logType, long afterTimeStamp, boolean includeSubLog) throws Exception {
        List<String> instanceIds = this.generateInstanceIds(instanceId, includeSubLog);
        return this.logManager.getLastLogsAfter(instanceIds, logType, afterTimeStamp);
    }

    @Override
    public void iterateAllLogs(String instanceId, boolean includeSubLog, ILogGenerator.LogItemHandle handler) throws Exception {
        this.iterateAllLogs(instanceId, null, includeSubLog, handler);
    }

    @Override
    public void iterateAllLogs(String instanceId, LogType logType, boolean includeSubLog, ILogGenerator.LogItemHandle handler) throws Exception {
        List<String> instanceIds = this.generateInstanceIds(instanceId, includeSubLog);
        this.logManager.iterateAllLogs(instanceIds, logType, handler);
    }

    @Override
    public ILogGenerator.LogItemDetail getLogItemDetail(long logId) {
        ILogGenerator.LogItemDetail logItemDetail = null;
        try {
            logItemDetail = this.logManager.getLogDetail(logId);
        }
        catch (JobsException e) {
            this.logger.error("\u83b7\u53d6\u8be6\u7ec6\u65e5\u5fd7\u5931\u8d25", e);
        }
        return logItemDetail;
    }

    private List<String> generateInstanceIds(String instanceId, boolean includeSubLog) throws Exception {
        List<JobInstanceBean> instanceBeans;
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(instanceId);
        if (includeSubLog && (instanceBeans = JobMonitorManager.getSubJobInstance(instanceId)) != null && !instanceBeans.isEmpty()) {
            for (JobInstanceBean instanceBean : instanceBeans) {
                ids.add(instanceBean.getInstanceId());
            }
        }
        return ids;
    }
}

