/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.dc.taskscheduling.core.intf.impl;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import java.util.Date;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTaskMonitor
implements ITaskMonitor {
    private String taskName;
    private String taskLogItemId;
    private TaskLogService logService;
    private double lastProgress = 0.0;
    private StringBuilder logBuilder;
    private Logger logger;

    public BaseTaskMonitor(String taskName, String taskLogItemId, TaskLogService logService) {
        this.taskName = taskName;
        this.taskLogItemId = taskLogItemId;
        this.logService = logService;
        this.logBuilder = new StringBuilder();
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public String getTaskLogItemId() {
        return this.taskLogItemId;
    }

    @Override
    public boolean beforeStart() {
        return !this.logService.isCancel(this.taskLogItemId);
    }

    @Override
    public void start() {
        String queueName = (String)ThreadContext.get((Object)"QUEUENAME_KEY");
        this.logService.updateTaskItemStartTimeById(this.taskLogItemId, queueName);
    }

    @Override
    public void progressAndLog(double progress, String log) {
        this.logBuilder.append(this.getLogWithTime(log));
        double changeProgress = NumberUtils.sub((double)progress, (double)this.lastProgress);
        if (changeProgress > 0.0 && progress <= 1.0) {
            this.logService.updateTaskItemProgress(this.taskLogItemId, progress, this.logBuilder.toString());
            this.lastProgress = progress;
        }
    }

    @Override
    public void progressAndLogByStepSize(double stepSize, String log) {
        this.logBuilder.append(this.getLogWithTime(log));
        double newProgress = NumberUtils.sum((double)this.lastProgress, (double)stepSize);
        if (stepSize > 0.0 && newProgress <= 1.0) {
            this.logService.updateTaskItemProgress(this.taskLogItemId, newProgress, this.logBuilder.toString());
            this.lastProgress = newProgress;
        }
    }

    @Override
    public void finish(TaskHandleResult result) {
        this.logBuilder.append(result.getLog());
        this.logService.updateTaskItemResultById(this.taskLogItemId, result.isSuccess() != false ? DataHandleState.SUCCESS : DataHandleState.FAILURE, this.logBuilder.toString());
    }

    @Override
    public void error(String log, Throwable t) {
        this.logBuilder.append(LogUtil.getExceptionStackStr((Throwable)t));
        this.logService.updateTaskItemResultById(this.taskLogItemId, DataHandleState.FAILURE, this.logBuilder.toString());
        this.logger.error(String.format("%1$s\u6570\u636e\u5904\u7406\u4efb\u52a1\u5f02\u5e38\uff0ctaskId\uff1a%2$s\uff1a%3$s", this.taskName, this.taskLogItemId, log), t);
    }

    protected String getLogWithTime(String log) {
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss")).append(" ").append(log);
        return sb.toString();
    }

    @Override
    public String getLog() {
        return this.logBuilder.toString();
    }

    @Override
    public StringBuilder getLogger() {
        return this.logBuilder;
    }
}

