/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  javax.annotation.Resource
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.task.api.controller;

import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.task.AsyncTaskInfo;
import com.jiuqi.nr.task.api.task.TaskCache;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/task/asyncTask"})
@Api(tags={"NR\u5f02\u6b65\u4efb\u52a1\u67e5\u8be2\u63a5\u53e3"})
public class NrAsyncTaskController {
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    private TaskCache taskCache;

    @RequestMapping(value={"query/processingTask"}, method={RequestMethod.GET})
    public List<AsyncTaskInfo> queryProcessAsyncTask() {
        List<String> ids = this.taskCache.getAsyncTask();
        ArrayList<AsyncTaskInfo> asyncTasks = new ArrayList<AsyncTaskInfo>();
        if (CollectionUtils.isEmpty(ids)) {
            return asyncTasks;
        }
        for (String id : ids) {
            Object result;
            TaskState state = this.asyncTaskManager.queryTaskState(id);
            if (state != TaskState.PROCESSING || (result = this.asyncTaskManager.queryDetail(id)) == null) continue;
            asyncTasks.add((AsyncTaskInfo)result);
        }
        return asyncTasks;
    }
}

