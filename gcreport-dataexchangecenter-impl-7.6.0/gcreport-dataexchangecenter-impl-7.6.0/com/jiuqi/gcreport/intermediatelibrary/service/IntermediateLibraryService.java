/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.gcreport.intermediatelibrary.service;

import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.Set;

public interface IntermediateLibraryService {
    public void programmeHandle(ILExtractCondition var1) throws Exception;

    public void createAsyncTask(AsyncTaskMonitor var1, String var2);

    public ILExtractCondition getAsyncData(Object var1, AsyncTaskMonitor var2) throws Exception;

    public Set<String> filterOrgId(ILExtractCondition var1, ILEntity var2);

    public void modifyAsyncTaskState(AsyncTaskMonitor var1, double var2, String var4);

    public void handleConnIsSuccess(String var1) throws Exception;
}

