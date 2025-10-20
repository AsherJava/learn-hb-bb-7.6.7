/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobExecutionContext
 */
package com.jiuqi.bi.core.jobs.combination;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.combination.CombinationExtendStageConfig;
import com.jiuqi.bi.core.jobs.combination.CombinationJobTools;
import com.jiuqi.bi.core.jobs.core.quartz.JobContextImpl;
import com.jiuqi.bi.core.jobs.model.JobModel;
import java.sql.SQLException;
import java.util.List;
import org.quartz.JobExecutionContext;

public class CombinationJobContextImpl
extends JobContextImpl {
    private int currectStage = 0;
    private List<CombinationExtendStageConfig> stageConfigs;

    public CombinationJobContextImpl(JobModel job, String userguid, String username, String instanceId, JobExecutionContext quartzContext) {
        super(job, userguid, username, instanceId, quartzContext);
    }

    public CombinationJobContextImpl(JobModel job, String userguid, String username, String instanceId, JobExecutionContext quartzContext, int currectStage) {
        super(job, userguid, username, instanceId, quartzContext);
        this.currectStage = currectStage;
        this.stageConfigs = CombinationJobTools.getStageConfigs(job.getExtendedConfig());
    }

    @Override
    public void updateProgress(int progress) throws JobExecutionException {
        try {
            int combinationProgress = progress / this.stageConfigs.size() + (this.currectStage - 1) * 100 / this.stageConfigs.size();
            this.jobOperationManager.updateJobProgress(this.getInstanceId(), combinationProgress);
        }
        catch (SQLException throwables) {
            throw new JobExecutionException(throwables);
        }
    }

    @Override
    public void updateProgress(int progress, String prompt) throws JobExecutionException {
        try {
            int combinationProgress = progress / this.stageConfigs.size() + (this.currectStage - 1) * 100 / this.stageConfigs.size();
            this.jobOperationManager.updateJobProgress(this.getInstanceId(), combinationProgress, prompt);
        }
        catch (SQLException throwables) {
            throw new JobExecutionException(throwables);
        }
    }
}

