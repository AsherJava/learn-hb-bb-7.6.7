/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.runner;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;

public interface BdeBatchFetchRunnerAfterExtension {
    public void afterExecute(JobContext var1, EfdcInfo var2, boolean var3, AsyncTaskInfo var4);
}

