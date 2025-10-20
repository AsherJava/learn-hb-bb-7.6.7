/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier
 *  com.jiuqi.bi.core.jobs.extension.IJobListener
 *  com.jiuqi.bi.core.jobs.extension.JobListenerContext
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 */
package com.jiuqi.common.plantask.extend.job;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobListener;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;
import com.jiuqi.common.plantask.extend.common.PlanTaskExtendI18Const;
import com.jiuqi.common.plantask.extend.job.PlanTaskRunner;
import com.jiuqi.common.plantask.extend.job.Runner;
import com.jiuqi.common.plantask.extend.job.RunnerJobClassifier;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import org.springframework.context.ApplicationContext;

public class RunnerJobFactory
extends JobFactory
implements IJobAdvanceConfiguration {
    private final ApplicationContext applicationContext;
    private final String jobCategoryId;
    private final String jobCategoryTitle;
    private final String modelName;
    private final String executorName;
    private final PlanTaskRunner taskRunner;

    public RunnerJobFactory(PlanTaskRunner taskRunner, ApplicationContext applicationContext) {
        this.jobCategoryId = taskRunner.id();
        this.jobCategoryTitle = taskRunner.title();
        this.modelName = taskRunner.settingPage();
        this.applicationContext = applicationContext;
        this.executorName = taskRunner.name();
        this.taskRunner = taskRunner;
    }

    public String getJobCategoryId() {
        return this.jobCategoryId;
    }

    public String getJobCategoryTitle() {
        return this.taskRunner.group() + "/" + this.jobCategoryTitle;
    }

    public JobExecutor createJobExecutor(String jobId) {
        final Runner runnerExecutor = (Runner)this.applicationContext.getBean(this.executorName);
        return new JobExecutor(){

            public void execute(JobContext jobContext) throws JobExecutionException {
                boolean execute = runnerExecutor.excute(jobContext);
                if (!execute) {
                    jobContext.getDefaultLogger().error(runnerExecutor.getLog());
                    throw new JobExecutionException(GcI18nUtil.getMessage((String)PlanTaskExtendI18Const.EXECUTE_FAILED_MESSAGE, (Object[])new Object[]{RunnerJobFactory.this.executorName}));
                }
                jobContext.getDefaultLogger().info(runnerExecutor.getLog());
            }
        };
    }

    public String getModelName() {
        return this.modelName;
    }

    public IJobClassifier getJobClassifier() {
        return new RunnerJobClassifier();
    }

    public IJobListener getJobListener() {
        return new IJobListener(){

            public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
            }

            public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
            }
        };
    }
}

