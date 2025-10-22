/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.np.asynctask.TaskState;
import java.time.Instant;

public interface AsyncTask {
    public String getTaskId();

    public TaskState getState();

    public Instant getCreateTime();

    public Instant getWaitingTime();

    public Instant getProcessTime();

    public Instant getFinishTime();

    public Instant getCancelTime();

    public Instant getEffectTime();

    @Deprecated
    public Object getArgs();

    public String getDependTaskId();

    public Double getProcess();

    public String getResult();

    @Deprecated
    public Object getDetail();

    public String getTaskPoolType();

    public String getCreateUserId();

    public String getDimensionIdentify();

    public String getServeId();

    public Integer getPriority();

    public Object getContext();

    public long getEffectTimeLong();

    public String getTaskKey();

    public String getFormSchemeKey();

    public String getPublishType();
}

