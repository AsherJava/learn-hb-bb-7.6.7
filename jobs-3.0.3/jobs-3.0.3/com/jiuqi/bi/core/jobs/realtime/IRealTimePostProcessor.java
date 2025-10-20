/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobDataMap
 */
package com.jiuqi.bi.core.jobs.realtime;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import org.quartz.JobDataMap;

public interface IRealTimePostProcessor {
    public void beforePost(JobDataMap var1);

    public void afterPost(JobDataMap var1);

    default public void finishPost() {
    }

    public void beforePost(AbstractRealTimeJob var1);

    public void afterPost(AbstractRealTimeJob var1);
}

