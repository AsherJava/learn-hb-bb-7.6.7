/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  nr.single.data.bean.CheckParam
 *  nr.single.data.bean.CheckResultNode
 *  nr.single.data.treecheck.service.IEntityTreeCheckService
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.entitytreecheck.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.entitytreecheck.asynctask.EntityTreeCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.entitytreecheck.bean.QueryEntityTreeCheckResultInfo;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import nr.single.data.bean.CheckParam;
import nr.single.data.bean.CheckResultNode;
import nr.single.data.treecheck.service.IEntityTreeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u6811\u5f62\u7ed3\u6784\u68c0\u67e5"})
@RequestMapping(value={"api/v1/finalaccount"})
public class EntityTreeCheckController {
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IEntityTreeCheckService treeCheckSevice;

    @RequestMapping(value={"/checkEntityTree"}, method={RequestMethod.POST})
    @ApiOperation(value="\u7efc\u5408\u5ba1\u6838\u6811\u5f62\u7ed3\u6784\u68c0\u67e5")
    public AsyncTaskInfo checkEntityTree(@RequestBody CheckParam checkParam) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(checkParam.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(checkParam.getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)checkParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new EntityTreeCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_ENTITYTREECHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @JLoggable(value="\u67e5\u8be2\u6811\u5f62\u7ed3\u6784\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    @PostMapping(value={"/queryEntityTreeCheckResult"})
    @ApiOperation(value="\u67e5\u8be2\u6811\u5f62\u7ed3\u6784\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f", notes="\u67e5\u8be2\u6811\u5f62\u7ed3\u6784\u68c0\u67e5\u7ed3\u679c\u5206\u7ec4\u4fe1\u606f")
    public List<CheckResultNode> queryEntityTreeCheckResult(@RequestBody QueryEntityTreeCheckResultInfo info) throws Exception {
        List checkResult = this.treeCheckSevice.CheckTreeNodeByTask(info.getContext().getTaskKey(), ((DimensionValue)info.getContext().getDimensionSet().get("DATATIME")).getValue(), true, null);
        int fromIndex = info.getItemOffset() * info.getPagerCount();
        int toIndex = (info.getItemOffset() + 1) * info.getPagerCount();
        if (toIndex > checkResult.size()) {
            toIndex = checkResult.size();
        }
        List<CheckResultNode> result = checkResult.subList(fromIndex, toIndex);
        return result;
    }
}

