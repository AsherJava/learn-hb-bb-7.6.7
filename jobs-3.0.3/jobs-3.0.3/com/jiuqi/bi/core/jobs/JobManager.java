/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Html
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.type.GUID
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.IBaseFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInfo;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.bi.core.jobs.core.quartz.ScheduleInfoCheckUtil;
import com.jiuqi.bi.core.jobs.defaultlog.ExtendOperateLogger;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.manager.JobStorageManager;
import com.jiuqi.bi.core.jobs.message.MessageSender;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.IScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.jobs.model.schedulemethod.NoneScheduleMethod;
import com.jiuqi.bi.core.jobs.model.schedulemethod.SimpleScheduleMethod;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.jobs.monitor.JobType;
import com.jiuqi.bi.util.Html;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.type.GUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private IBaseFactory factory;
    private static ExtendOperateLogger operateLogger = null;

    public JobManager(IBaseFactory factory) {
        this.factory = factory;
    }

    public IBaseFactory getFactory() {
        return this.factory;
    }

    public static JobManager getInstance(String categoryId) {
        return JobFactoryManager.getInstance().getJobManager(categoryId);
    }

    public static JobManager getInstance(String categoryId, boolean includingRemote) {
        return JobFactoryManager.getInstance().getJobManager(categoryId, includingRemote);
    }

    public JobModel createJob(String title) {
        JobModel jobModel = new JobModel();
        jobModel.setCategory(this.factory.getJobCategoryId());
        jobModel.setGuid(GUID.newGUID());
        jobModel.setTitle(title);
        return jobModel;
    }

    public JobModel createJob(String title, String jobGuid) {
        JobModel jobModel = this.createJob(title);
        jobModel.setGuid(jobGuid);
        return jobModel;
    }

    public void addJobModel(JobModel job) throws JobsException {
        if (job == null || StringUtils.isEmpty((String)job.getGuid())) {
            throw new JobsException("\u4efb\u52a1\u548c\u4efb\u52a1guid\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        JobStorageManager manager = new JobStorageManager();
        manager.addJob(job);
        if (operateLogger != null) {
            operateLogger.addJob(job);
        }
    }

    public List<JobInfo> getJobsIncludeState(List<String> jobGuids) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        return jobStorageManager.getJobsIncludeState(jobGuids);
    }

    public JobInfo getJobsIncludeState(String jobGuid) throws JobsException {
        ArrayList<String> jobGuids = new ArrayList<String>();
        jobGuids.add(jobGuid);
        List<JobInfo> jobInfos = this.getJobsIncludeState(jobGuids);
        if (jobInfos != null && !jobInfos.isEmpty()) {
            return jobInfos.get(0);
        }
        return null;
    }

    public JobModel getJob(String jobGuid) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        JobModel jobModel = jobStorageManager.getJob(jobGuid);
        if (jobModel == null) {
            throw new JobsException("\u4efb\u52a1\u4e0d\u5b58\u5728");
        }
        return jobModel;
    }

    public JobModel copyJob(String jobGuid) throws JobsException {
        JobModel jobModel = this.getJob(jobGuid);
        jobModel.setGuid(GUID.newGUID());
        return jobModel;
    }

    public void saveJob(JobModel job) throws JobsException {
        if (job == null || StringUtils.isEmpty((String)job.getGuid())) {
            throw new JobsException("\u4efb\u52a1\u548c\u4efb\u52a1guid\u5747\u4e0d\u80fd\u4e3a\u7a7a");
        }
        JobStorageManager manager = new JobStorageManager();
        JobModel jobOld = manager.getJob(job.getGuid());
        if (jobOld == null) {
            throw new JobsException("\u4efb\u52a1\u4e0d\u5b58\u5728");
        }
        if (!this.factory.getJobCategoryId().equals(jobOld.getCategory())) {
            throw new JobsException("jobmanager\u548cjob\u5bf9\u5e94\u7684category\u4e0d\u4e00\u81f4\uff0c\u65e0\u6743\u9650\u4fee\u6539");
        }
        manager.updateJobBaseinfo(job);
        if (operateLogger != null) {
            operateLogger.updateJobBaseinfo(jobOld, job);
        }
    }

    public void jobEnable(String jobId, boolean enable) throws JobsException {
        JobStorageManager manager = new JobStorageManager();
        JobModel model = null;
        if (operateLogger != null) {
            model = manager.getJob(jobId);
        }
        manager.updateJobEnable(jobId, enable);
        if (operateLogger != null) {
            operateLogger.updateJobEnable(model, enable);
        }
    }

    public void deleteJob(String jobId) throws JobsException {
        JobStorageManager manager = new JobStorageManager();
        JobModel model = null;
        if (operateLogger != null) {
            model = manager.getJob(jobId);
        }
        manager.deleteJob(jobId, this.factory.getJobCategoryId());
        if (operateLogger != null) {
            operateLogger.deleteJob(model);
        }
    }

    public String manualExecuteJob(String jobId, Map<String, String> params, String userguid, String username) throws JobsException {
        return this.manualExecuteJob(jobId, params, userguid, username, null);
    }

    public String manualExecuteJob(String jobId, Map<String, String> params, String userguid, String username, List<String> linkSoueces) throws JobsException {
        JobStorageManager manager = new JobStorageManager();
        JobModel job = manager.getJob(jobId);
        if (job == null) {
            throw new JobsException("\u65e0\u6cd5\u627e\u5230ID\u4e3a[" + jobId + "]\u7684\u4efb\u52a1");
        }
        return this.executeJob(params, userguid, username, linkSoueces, job);
    }

    public String concurrentExecuteJob(String jobId, Map<String, String> params, String userguid, String username, List<String> linkSoueces) throws JobsException {
        JobStorageManager manager = new JobStorageManager();
        JobModel job = manager.getJob(jobId);
        if (job == null) {
            throw new JobsException("\u65e0\u6cd5\u627e\u5230ID\u4e3a[" + jobId + "]\u7684\u4efb\u52a1");
        }
        job.setConcurrency(true);
        return this.executeJob(params, userguid, username, linkSoueces, job);
    }

    private String executeJob(Map<String, String> params, String userguid, String username, List<String> linkSoueces, JobModel job) throws JobsException {
        String instanceId = GUID.newGUID();
        JobOperationManager om = new JobOperationManager();
        try {
            om.mainJobAdded(instanceId, job.getGuid(), userguid, username, job.getTitle(), JobType.MANUAL_JOB, job.getCategory(), JobFactoryManager.getInstance().getJobFactory(job.getCategory(), true).getJobCategoryTitle(), true, linkSoueces, null, null);
            AbstractJobBridge jobBridge = JobBridgeFactory.getInstance().getDefaultBridge();
            jobBridge.manualExecuteJob(job, instanceId, params, userguid, username);
            if (operateLogger != null) {
                operateLogger.executeJob(job, instanceId, params, userguid, username);
            }
            return instanceId;
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    public void cancelJob(String jobId) throws JobsException {
        JobMonitorManager monitorManager = new JobMonitorManager(this.factory.getJobCategoryId());
        String instanceID = monitorManager.getJobRunningInstanceID(jobId);
        if (instanceID == null) {
            this.logger.warn("\u4efb\u52a1[{}]\u5e76\u6ca1\u6709\u5728\u8fd0\u884c\u72b6\u6001\uff0c\u5ffd\u7565\u53d6\u6d88\u8bf7\u6c42", (Object)jobId);
            return;
        }
        try {
            MessageSender.sendJobCanceledMessage(jobId, this.factory.getJobCategoryId());
        }
        catch (Exception e) {
            throw new JobsException("\u53d1\u9001\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u5931\u8d25", e);
        }
    }

    public void cancelJobInstance(String instanceId) throws JobsException {
        try {
            MessageSender.sendJobCanceledMessage(instanceId);
        }
        catch (Exception e) {
            throw new JobsException("\u53d1\u9001\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u5931\u8d25", e);
        }
    }

    public void updateJobScheduleConf(JSONObject json) throws JobsException {
        String jobGuid = json.optString("jobGuid");
        if (StringUtils.isEmpty((String)jobGuid)) {
            throw new JobsException("jobGuid\u4e0d\u80fd\u4e3a\u7a7a");
        }
        jobGuid = Html.encodeText((String)jobGuid);
        JobModel jobModel = new JobModel();
        jobModel.setGuid(jobGuid);
        String scheduleType = json.optString("scheduleType");
        if ("noschedule".equals(scheduleType)) {
            jobModel.setScheduleMethod(new NoneScheduleMethod());
        } else {
            String startTimeStr = json.optString("startTime");
            String endTimeStr = json.optString("endTime");
            if (StringUtils.isEmpty((String)startTimeStr) || StringUtils.isEmpty((String)endTimeStr)) {
                throw new JobsException("\u8c03\u5ea6\u5f00\u59cb\u7ed3\u675f\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u683c\u5f0f\u4e3a\uff1a\u6beb\u79d2\u65f6\u95f4\u6233");
            }
            long startTime = 0L;
            long endTime = 0L;
            try {
                startTime = Long.parseLong(startTimeStr);
                endTime = Long.parseLong(endTimeStr);
            }
            catch (Exception e) {
                this.logger.error("\u8c03\u5ea6\u5f00\u59cb\u7ed3\u675f\u65f6\u95f4\u683c\u5f0f\u9519\u8bef\uff0c\u683c\u5f0f\u4e3a\uff1a\u6beb\u79d2\u65f6\u95f4\u6233:" + e.getMessage(), e);
                throw new JobsException("\u8c03\u5ea6\u5f00\u59cb\u7ed3\u675f\u65f6\u95f4\u683c\u5f0f\u9519\u8bef\uff0c\u683c\u5f0f\u4e3a\uff1a\u6beb\u79d2\u65f6\u95f4\u6233");
            }
            if (startTime > endTime) {
                throw new JobsException("\u8c03\u5ea6\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4");
            }
            long curMillis = System.currentTimeMillis();
            if (startTime < curMillis) {
                jobModel.setStartTime(curMillis);
            } else {
                jobModel.setStartTime(startTime);
            }
            jobModel.setEndTime(endTime);
            String scheduleInfoStr = json.optString("scheduleInfo");
            if (StringUtils.isEmpty((String)scheduleInfoStr)) {
                throw new JobsException("\u8c03\u5ea6\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
            }
            jobModel.setScheduleMethod(ScheduleInfoCheckUtil.buildScheduleMethod(scheduleType, scheduleInfoStr));
        }
        JobModel oldJobModel = null;
        if (operateLogger != null) {
            oldJobModel = this.getJob(jobGuid);
        }
        JobStorageManager jobStorageManager = new JobStorageManager();
        jobStorageManager.updateJobScheduleConf(jobModel);
        if (operateLogger != null) {
            operateLogger.updateJobScheduleConf(oldJobModel, jobModel);
        }
    }

    public void updateJobScheduleConf(String jobGuid, long startTime, long endTime, IScheduleMethod scheduleMethod) throws JobsException {
        if (StringUtils.isEmpty((String)jobGuid)) {
            throw new JobsException("jobGuid\u4e0d\u80fd\u4e3a\u7a7a");
        }
        jobGuid = Html.encodeText((String)jobGuid);
        JobModel jobModel = new JobModel();
        jobModel.setGuid(jobGuid);
        long curMillis = System.currentTimeMillis();
        if (startTime < curMillis) {
            jobModel.setStartTime(curMillis);
        } else {
            jobModel.setStartTime(startTime);
        }
        jobModel.setEndTime(endTime);
        if (scheduleMethod instanceof AbstractScheduleMethod) {
            long execTime;
            if (scheduleMethod instanceof SimpleScheduleMethod && (execTime = ((SimpleScheduleMethod)scheduleMethod).getExecuteTime()) < curMillis) {
                ((SimpleScheduleMethod)scheduleMethod).setExecuteTime(curMillis);
            }
            jobModel.setScheduleMethod((AbstractScheduleMethod)scheduleMethod);
        }
        JobStorageManager jobStorageManager = new JobStorageManager();
        JobModel oldJobModel = null;
        if (operateLogger != null) {
            oldJobModel = this.getJob(jobGuid);
        }
        jobStorageManager.updateJobScheduleConf(jobModel);
        if (operateLogger != null) {
            operateLogger.updateJobScheduleConf(oldJobModel, jobModel);
        }
    }

    public void updateJobTitle(String jobGuid, String title) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        JobModel oldJobModel = null;
        if (operateLogger != null) {
            oldJobModel = this.getJob(jobGuid);
        }
        jobStorageManager.updateJobTitle(jobGuid, title);
        if (operateLogger != null) {
            operateLogger.updateJobTitle(oldJobModel, title);
        }
    }

    public void updateJobConfig(String jobGuid, String config) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        JobModel oldJobModel = null;
        if (operateLogger != null) {
            oldJobModel = this.getJob(jobGuid);
        }
        jobStorageManager.updateJobConfig(jobGuid, config);
        if (operateLogger != null) {
            operateLogger.updateJobConfig(oldJobModel, config);
        }
    }

    public static void setJobOnlyInNode(String jobGuid, String[] nodeNames) throws JobsException {
        try {
            ConfigManager.getInstance().setJobOnlyInNode(jobGuid, nodeNames);
        }
        catch (JobsException e) {
            throw new JobsException("\u8bbe\u7f6e\u4efb\u52a1\u53ea\u53ef\u4ee5\u5728\u67d0\u4e9b\u8282\u70b9\u4e0a\u6267\u884c\u5931\u8d25" + e.getMessage(), e);
        }
    }

    public List<String> getSubInstanceGuid(String instanceGuid) throws JobsException {
        ArrayList<String> subInstanceGuids = new ArrayList<String>();
        List<JobInstanceBean> subInstances = JobMonitorManager.getSubJobInstance(instanceGuid);
        for (JobInstanceBean instance : subInstances) {
            subInstanceGuids.add(instance.getInstanceId());
        }
        return subInstanceGuids;
    }

    public String getParentInstanceGuid(String instanceGuid) throws JobsException {
        JobMonitorManager monitorManager = new JobMonitorManager(this.factory.getJobCategoryId());
        JobInstanceBean instance = monitorManager.getParentJobInstance(instanceGuid);
        String parentInstanceGuid = null;
        if (instance != null && StringUtils.isNotEmpty((String)instance.getInstanceId())) {
            parentInstanceGuid = instance.getInstanceId();
        }
        return parentInstanceGuid;
    }

    public void updateJobParameter(String jobGuid, List<JobParameter> parameters) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        JobModel oldJobModel = null;
        if (operateLogger != null) {
            oldJobModel = this.getJob(jobGuid);
        }
        jobStorageManager.updateJobParameter(jobGuid, parameters);
        if (operateLogger != null) {
            operateLogger.updateJobParameter(oldJobModel, parameters);
        }
    }

    public List<JobParameter> getJobParameter(String jobGuid) throws JobsException {
        JobStorageManager jobStorageManager = new JobStorageManager();
        return jobStorageManager.getJobParameter(jobGuid);
    }

    public static void setOperateLogger(ExtendOperateLogger operateLogger) {
        JobManager.operateLogger = operateLogger;
    }
}

