/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.exception.OutOfQueueException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  org.springframework.web.bind.annotation.ControllerAdvice
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.ResponseBody
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.exception.OutOfQueueException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AsyncControllerAdvice {
    @ExceptionHandler(value={OutOfQueueException.class})
    @ResponseBody
    public AsyncTaskInfo publishTaskAdvice() {
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setState(TaskState.OUTOFQUEUE);
        asyncTaskInfo.setId(UUIDUtils.getKey());
        asyncTaskInfo.setResult("\u5f53\u524d\u6267\u884c{}\u4eba\u6570\u8fc7\u591a\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\uff01");
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

