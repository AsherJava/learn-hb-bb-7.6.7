/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.service;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskQueryInfo;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;

public interface GcFetchService {
    public AsyncTaskInfo fetchData(EfdcInfo var1);

    public AsyncTaskInfo queryFetchTask(String var1);

    public AsyncTaskInfo batchFetchData(EfdcInfo var1);

    public AsyncTaskInfo queryFetchTaskInfo(AsyncTaskQueryInfo var1);

    public AsyncTaskInfo queryBatchFetchTaskInfo(AsyncTaskQueryInfo var1);
}

