/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.model.JobModel;
import org.quartz.JobDetail;
import org.quartz.JobKey;

public class KeyBuilder {
    private KeyBuilder() {
    }

    public static String buildTriggerGroup(String jobId, String category) {
        return category + "." + jobId;
    }

    public static String buildTriggerGroup(JobModel job) {
        return KeyBuilder.buildTriggerGroup(job.getGuid(), job.getCategory());
    }

    public static String buildTriggerGroup(JobDetail jd) {
        return KeyBuilder.buildTriggerGroup(jd.getKey());
    }

    public static String buildTriggerGroup(JobKey key) {
        return KeyBuilder.buildTriggerGroup(key.getName(), key.getGroup());
    }
}

