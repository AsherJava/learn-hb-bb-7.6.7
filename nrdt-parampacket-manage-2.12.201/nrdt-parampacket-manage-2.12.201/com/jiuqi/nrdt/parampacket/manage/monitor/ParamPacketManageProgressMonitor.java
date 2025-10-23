/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nvwa.transfer.TransferProgressMonitor
 */
package com.jiuqi.nrdt.parampacket.manage.monitor;

import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nvwa.transfer.TransferProgressMonitor;

public class ParamPacketManageProgressMonitor
extends TransferProgressMonitor {
    public CacheObjectResourceRemote cacheObjectResourceRemote;
    public String progressId;
    private static final double FINISHED_VALUE = 1.0;

    public ParamPacketManageProgressMonitor(String progressId, CacheObjectResourceRemote cacheObjectResourceRemote) {
        super(null);
        this.progressId = progressId;
        this.cacheObjectResourceRemote = cacheObjectResourceRemote;
        AsyncTaskInfo progressInfo = this.buildAsyncTaskInfo(0.0, TaskState.PROCESSING, null);
        cacheObjectResourceRemote.create((Object)progressId, (Object)progressInfo);
    }

    public void prompt(String s) {
        double position = this.getPosition();
        if (position >= 0.0 && position < 1.0) {
            AsyncTaskInfo asyncTaskInfo = this.buildAsyncTaskInfo(position, TaskState.PROCESSING, s);
            this.cacheObjectResourceRemote.create((Object)this.progressId, (Object)asyncTaskInfo);
        } else if (position < 0.0) {
            AsyncTaskInfo asyncTaskInfo = this.buildAsyncTaskInfo(position, TaskState.ERROR, s);
            this.cacheObjectResourceRemote.create((Object)this.progressId, (Object)asyncTaskInfo);
        }
    }

    protected void notify(double v) {
        if (v >= 1.0) {
            AsyncTaskInfo asyncTaskInfo = this.buildAsyncTaskInfo(v, TaskState.FINISHED, "\u6267\u884c\u5b8c\u6210");
            this.cacheObjectResourceRemote.create((Object)this.progressId, (Object)asyncTaskInfo);
        }
    }

    public void error(String detail, String message) {
        AsyncTaskInfo asyncTaskInfo = this.buildAsyncTaskInfo(-1.0, TaskState.ERROR, detail, message);
        this.cacheObjectResourceRemote.create((Object)this.progressId, (Object)asyncTaskInfo);
    }

    private AsyncTaskInfo buildAsyncTaskInfo(double process, TaskState state, String detail) {
        return this.buildAsyncTaskInfo(process, state, detail, null);
    }

    private AsyncTaskInfo buildAsyncTaskInfo(double process, TaskState state, String detail, String result) {
        AsyncTaskInfo progressInfo = new AsyncTaskInfo();
        progressInfo.setProcess(Double.valueOf(process));
        progressInfo.setId(this.progressId);
        progressInfo.setState(state);
        if (process >= 1.0) {
            progressInfo.setState(TaskState.FINISHED);
        } else {
            progressInfo.setState(TaskState.PROCESSING);
        }
        progressInfo.setDetail((Object)detail);
        progressInfo.setResult(result);
        progressInfo.setUrl("/api/v1/dataentry/actions/progress/query?progressId=");
        return progressInfo;
    }
}

