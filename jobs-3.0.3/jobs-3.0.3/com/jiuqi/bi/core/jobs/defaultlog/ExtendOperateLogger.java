/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.model.JobModel;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import java.util.List;
import java.util.Map;

public interface ExtendOperateLogger {
    default public void addJob(JobModel jobModel) {
    }

    default public void updateJobBaseinfo(JobModel oldJobModel, JobModel newJobModel) {
    }

    default public void deleteJob(JobModel jobModel) {
    }

    default public void executeJob(JobModel jobModel, String instanceId, Map<String, String> params, String userguid, String username) {
    }

    default public void updateJobEnable(JobModel jobModel, boolean enable) {
    }

    default public void updateJobScheduleConf(JobModel jobModel, JobModel model) {
    }

    default public void updateJobTitle(JobModel job, String title) {
    }

    default public void updateJobConfig(JobModel job, String config) {
    }

    default public void updateJobParameter(JobModel job, List<JobParameter> parameters) {
    }
}

