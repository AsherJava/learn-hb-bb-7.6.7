/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILExtractCondition;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateLibraryTaskService;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntermediateLibraryTaskImpl
implements IntermediateLibraryTaskService {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    public AsyncTaskInfo asyncTask(String paramJson) {
        ILExtractCondition iLExtractCondition = (ILExtractCondition)JsonUtils.readValue((String)paramJson, ILExtractCondition.class);
        String asynTaskID = this.asyncTaskManager.publishTask((Object)paramJson, iLExtractCondition.getTaskPoolType());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

