/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.event;

import com.jiuqi.np.asynctask.AsyncTask;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class PublishTaskEvent
extends ApplicationEvent {
    private String taskPoolType;
    private List<AsyncTask> taskList;

    public PublishTaskEvent() {
        super(0);
    }

    public List<AsyncTask> getTaskList() {
        return this.taskList;
    }

    public void setTaskList(List<AsyncTask> taskList) {
        this.taskList = taskList;
    }

    public String getTaskPoolType() {
        return this.taskPoolType;
    }

    public void setTaskPoolType(String taskPoolType) {
        this.taskPoolType = taskPoolType;
    }
}

