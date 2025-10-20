/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.util.Guid
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.dao.JobInstanceDetailDAO;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.imdiately.IImmediatelyJobStorageMiddleware;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobExecuteThread;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobManager;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobPostProcessor;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobThreadPool;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyRealTimeJobContext;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.util.Guid;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class ImmediatelyJobRunner {
    private final Map<String, ImmediatelyRealTimeJobContext> contextMap = new ConcurrentHashMap<String, ImmediatelyRealTimeJobContext>();
    private static final ImmediatelyJobRunner instance = new ImmediatelyJobRunner();

    private ImmediatelyJobRunner() {
    }

    public static ImmediatelyJobRunner getInstance() {
        return instance;
    }

    public String commit(AbstractRealTimeJob job) throws JobExecutionException {
        IImmediatelyJobStorageMiddleware storageMiddleware = ImmediatelyJobManager.getInstance().getStorageMiddleware();
        ImmediatelyJobInfo jobInfo = new ImmediatelyJobInfo();
        String instanceId = Guid.newGuid();
        job.getParams().put("_SYS_INSTANCE_ID", instanceId);
        ImmediatelyRealTimeJobContext context = new ImmediatelyRealTimeJobContext(job, jobInfo);
        jobInfo.setInstanceId(instanceId);
        jobInfo.setType(job.getClass().getAnnotation(RealTimeJob.class).group());
        jobInfo.setProgress(0);
        jobInfo.setNodeName(DistributionManager.getInstance().self().getName());
        storageMiddleware.put(jobInfo);
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> entry : job.getParams().entrySet()) {
                jsonObject.put(entry.getKey(), (Object)entry.getValue());
            }
            JobInstanceDetailDAO.addImmediatelyInstanceDetailWithParams(conn, instanceId, jsonObject.toString());
        }
        catch (SQLException e) {
            storageMiddleware.remove(jobInfo);
            throw new JobExecutionException("\u6dfb\u52a0\u4efb\u52a1\u8be6\u60c5\u5931\u8d25", e);
        }
        ImmediatelyJobExecuteThread runnable = new ImmediatelyJobExecuteThread();
        runnable.setContext(context);
        runnable.setJobInfo(jobInfo);
        runnable.setJob(job);
        ImmediatelyJobPostProcessor jobPostProcessor = ImmediatelyJobManager.getInstance().getJobPostProcessor();
        jobPostProcessor.beforeExecute(runnable);
        ImmediatelyJobThreadPool.getInstance().submit(runnable);
        this.contextMap.put(instanceId, context);
        return instanceId;
    }

    public void cancel(String instanceGuid) {
        ImmediatelyRealTimeJobContext immediatelyRealTimeJobContext = this.contextMap.get(instanceGuid);
        if (immediatelyRealTimeJobContext != null) {
            immediatelyRealTimeJobContext.getMonitor().cancel();
        }
    }

    public ImmediatelyJobInfo getJobInfo(String instanceGuid) {
        return ImmediatelyJobManager.getInstance().getStorageMiddleware().get(instanceGuid);
    }

    public Map<String, ImmediatelyJobInfo> getJobInfos(List<String> instanceGuids) {
        return ImmediatelyJobManager.getInstance().getStorageMiddleware().gets(instanceGuids);
    }

    public void remove(ImmediatelyJobInfo jobInfo) {
        IImmediatelyJobStorageMiddleware storageMiddleware = ImmediatelyJobManager.getInstance().getStorageMiddleware();
        storageMiddleware.remove(jobInfo);
        this.contextMap.remove(jobInfo.getInstanceId());
    }
}

