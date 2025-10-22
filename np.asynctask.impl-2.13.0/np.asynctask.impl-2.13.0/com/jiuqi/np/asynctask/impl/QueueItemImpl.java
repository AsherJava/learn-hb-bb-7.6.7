/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.QueueItem
 */
package com.jiuqi.np.asynctask.impl;

import com.jiuqi.np.asynctask.QueueItem;
import java.time.Instant;

public class QueueItemImpl
implements QueueItem {
    private String id;
    private String taskId;
    private Instant joinTime;
    private Integer priority = 2;

    public String getId() {
        return this.id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public Instant getJoinTime() {
        return this.joinTime;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setJoinTime(Instant joinTime) {
        this.joinTime = joinTime;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}

