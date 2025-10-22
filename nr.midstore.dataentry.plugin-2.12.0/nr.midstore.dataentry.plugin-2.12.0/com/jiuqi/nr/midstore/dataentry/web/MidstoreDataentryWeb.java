/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.midstore.dataentry.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.midstore.dataentry.asynctask.MidstorePullAsyncTaskExecutor;
import com.jiuqi.nr.midstore.dataentry.bean.MidstoreParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/midstore"})
public class MidstoreDataentryWeb {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @PostMapping(value={"/midstore-pull"})
    @ApiOperation(value="\u4e2d\u95f4\u5e93\u53d6\u6570")
    public AsyncTaskInfo midstorePull(@RequestBody MidstoreParam midstoreInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(midstoreInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(midstoreInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)midstoreInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MidstorePullAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_MIDSTORE_PULL");
        asyncTaskInfo.setId(asyncTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

