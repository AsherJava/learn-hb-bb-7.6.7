/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.service.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.asynctask.IntegrityCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"\u8868\u5b8c\u6574\u6027\u68c0\u67e5"})
@RequestMapping(value={"FinalIntegrityCheck/integritycheck"})
@RestController
public class IntegrityCheckService {
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @JLoggable(value="\u6267\u884c\u8868\u5b8c\u6574\u6027\u68c0\u67e5")
    @PostMapping(value={"/integrity-check"})
    @ApiOperation(value="\u8868\u5b8c\u6574\u6027\u68c0\u67e5", notes="\u53d1\u8d77\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7684\u5f02\u6b65\u4efb\u52a1")
    public AsyncTaskInfo integrityCheck(@Valid @RequestBody IntegrityCheckInfo integrityCheckInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(integrityCheckInfo.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(integrityCheckInfo.getFormSchemeKey());
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new IntegrityCheckAsyncTaskExecutor());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)integrityCheckInfo)));
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_INTEGRITYCHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

