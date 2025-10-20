/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.JobContextHolder;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.core.AbstractRealTimeJobContext;
import com.jiuqi.bi.core.jobs.realtime.imdiately.IImmediatelyJobStorageMiddleware;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobManager;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobPostProcessor;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;

public class ImmediatelyJobExecuteThread
implements Runnable {
    private AbstractRealTimeJob job;
    private AbstractRealTimeJobContext context;
    private ImmediatelyJobInfo jobInfo;
    private Object userContext;

    public void setJob(AbstractRealTimeJob job) {
        this.job = job;
    }

    public void setContext(AbstractRealTimeJobContext context) {
        this.context = context;
    }

    public void setJobInfo(ImmediatelyJobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public void setUserContext(Object userContext) {
        this.userContext = userContext;
    }

    @Override
    public void run() {
        IImmediatelyJobStorageMiddleware storageMiddleware = ImmediatelyJobManager.getInstance().getStorageMiddleware();
        try {
            ImmediatelyJobPostProcessor jobPostProcessor = ImmediatelyJobManager.getInstance().getJobPostProcessor();
            jobPostProcessor.afterExecute(this);
            JobContextHolder.setJobContext(this.context);
            if (this.jobInfo.getState() == State.CANCELING.getValue()) {
                this.jobInfo.setState(State.FINISHED.getValue());
                this.jobInfo.setResult(2);
                storageMiddleware.patch(this.jobInfo);
                return;
            }
            this.jobInfo.setState(State.RUNNING.getValue());
            storageMiddleware.patch(this.jobInfo);
            try {
                this.job.execute(this.context);
            }
            finally {
                JobContextHolder.clear();
            }
            if (this.jobInfo.getState() == State.CANCELING.getValue()) {
                this.jobInfo.setState(State.FINISHED.getValue());
                this.jobInfo.setResult(2);
                storageMiddleware.patch(this.jobInfo);
                return;
            }
            this.jobInfo.setProgress(100);
            this.jobInfo.setState(State.FINISHED.getValue());
            if (this.context.getResult() == 0) {
                this.jobInfo.setResult(100);
            } else {
                this.jobInfo.setResult(this.context.getResult());
            }
            this.jobInfo.setResultMessage(this.context.getResultMesage());
            storageMiddleware.patch(this.jobInfo);
        }
        catch (Throwable e) {
            if (this.context.getResult() == 0) {
                this.jobInfo.setResult(4);
            } else {
                this.jobInfo.setResult(this.context.getResult());
            }
            this.jobInfo.setState(State.FINISHED.getValue());
            this.jobInfo.setResultMessage(e.getMessage());
            storageMiddleware.patch(this.jobInfo);
            throw new RuntimeException(e);
        }
        finally {
            ImmediatelyJobRunner.getInstance().remove(this.jobInfo);
        }
    }
}

