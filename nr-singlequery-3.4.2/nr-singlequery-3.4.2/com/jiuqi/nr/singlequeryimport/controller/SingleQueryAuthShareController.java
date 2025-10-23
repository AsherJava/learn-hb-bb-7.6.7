/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.singlequeryimport.auth.share.asynctask.SingleQueryAuthShareAsyncTaskExecutor;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareParams;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareUserParams;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ReturnObject;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.SingleQueryAuthShareCacheUserInfo;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@JQRestController
@Api(tags={"\u67e5\u8be2\u6a21\u7248\u6743\u9650\u5206\u4eab"})
@RequestMapping(value={"/bmjs/v1/singlequery/authshare"})
public class SingleQueryAuthShareController {
    @Autowired
    private AuthShareService authShareService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @RequestMapping(value={"/getShareUsers"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u53ef\u5206\u4eab\u7684\u7528\u6237\u5217\u8868")
    public ReturnObject getShareUsers(@RequestBody AuthShareParams params) {
        return this.authShareService.getChildUsers(params);
    }

    @RequestMapping(value={"/getGroupWithAuth"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6709\u6743\u9650\u7684\u6a21\u677f\u5206\u7ec4\u9644\u5e26\u6743\u9650")
    @ResponseBody
    public List<QueryModelNode> getGroupWithAuth(@RequestParam(value="key") String key) throws Exception {
        return this.authShareService.getModelGroupWithAuth(key);
    }

    @RequestMapping(value={"/getModelWithAuth"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6709\u6743\u9650\u7684\u6a21\u677f\u9644\u5e26\u6743\u9650")
    @ResponseBody
    public List<QueryModelNode> getModelWithAuth(@RequestParam String key, @RequestParam String formSchemeKey) throws Exception {
        return this.authShareService.getModelWithAuth(formSchemeKey, key);
    }

    @JLoggable(value="\u8bbe\u7f6e\u7528\u6237\u7684\u6a21\u677f\u6743\u9650")
    @RequestMapping(value={"/setUsersWithModelAuth"}, method={RequestMethod.POST})
    @ApiOperation(value="\u8bbe\u7f6e\u7528\u6237\u7684\u6a21\u677f\u6743\u9650")
    public boolean setUsersWithModelAuth(@RequestBody AuthShareUserParams params) {
        return this.authShareService.setUsersWithModelAuth(params);
    }

    @RequestMapping(value={"/getCurUserAuthByModals"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u9009\u62e9\u6a21\u7248 \u5206\u7ec4\u3001\u5f53\u524d\u7528\u6237\u6743\u9650\u7684\u5408\u8ba1")
    public SingleQueryAuthShareCacheUserInfo getCurUserAuthByModals(@RequestBody AuthShareUserParams params) {
        if (!params.getModelIds().isEmpty()) {
            return this.authShareService.getCurUserAuthByModals(params.getFormSchemeKey(), params.getModelIds());
        }
        if (!params.getGroupKeys().isEmpty()) {
            return this.authShareService.getCurUserAuthByGroup(params.getFormSchemeKey(), params.getGroupKeys());
        }
        return null;
    }

    @JLoggable(value="\u8bbe\u7f6e\u7528\u6237\u7684\u6a21\u677f\u6743\u9650")
    @RequestMapping(value={"/batchSetUsersWithModelAuth"}, method={RequestMethod.POST})
    @ApiOperation(value="\u8bbe\u7f6e\u7528\u6237\u7684\u6a21\u677f\u6743\u9650")
    public AsyncTaskInfo batchSetUsersWithModelAuth(@RequestBody AuthShareUserParams params) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)params));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new SingleQueryAuthShareAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_SINGLEQUERYAUTHSHARE");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

