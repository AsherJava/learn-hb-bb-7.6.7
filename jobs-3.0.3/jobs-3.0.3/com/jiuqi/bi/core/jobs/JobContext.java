/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobExecResultBean;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.monitor.IProgressMonitor;
import java.util.List;
import java.util.Map;

public interface JobContext {
    public static final String SYSTEM_USER_GUID = "_system_";
    public static final String SYSTEM_USER_NAME = "_system_";

    public IProgressMonitor getMonitor();

    public JobModel getJob();

    public AbstractRealTimeJob getRealTimeJob();

    public String getParameterValue(String var1);

    public String executeSubJob(String var1, Class<? extends JobExecutor> var2, Map<String, String> var3) throws JobExecutionException;

    public String executeRealTimeSubJob(AbstractRealTimeJob var1) throws JobExecutionException;

    public boolean waitForSubJob(int var1) throws JobExecutionException;

    public boolean waitForSubJob() throws JobExecutionException;

    public Map<String, SubJobStatus> getSubJobStatus() throws JobExecutionException;

    public void updateProgress(int var1) throws JobExecutionException;

    public void updateProgress(int var1, String var2) throws JobExecutionException;

    public void setResult(int var1, String var2);

    public void setInstanceDetail(String var1) throws JobsException;

    public String getUserguid();

    public String getUsername();

    public String getInstanceId();

    public Logger getDefaultLogger();

    public String getAuthenticatedUsername();

    public List<JobExecResultBean> saveResultFile(List<AbstractJobResult> var1) throws JobsException;

    public static class SubJobStatus {
        private String subJobInstanceId;
        private String subJobName;
        private int progress;
        private String prompt;
        private int result;
        private String resultMessage;

        public String getSubJobName() {
            return this.subJobName;
        }

        public void setSubJobName(String subJobName) {
            this.subJobName = subJobName;
        }

        public int getProgress() {
            return this.progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public String getPrompt() {
            return this.prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public int getResult() {
            return this.result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getResultMessage() {
            return this.resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        public String getSubJobInstanceId() {
            return this.subJobInstanceId;
        }

        public void setSubJobInstanceId(String subJobInstanceId) {
            this.subJobInstanceId = subJobInstanceId;
        }
    }
}

