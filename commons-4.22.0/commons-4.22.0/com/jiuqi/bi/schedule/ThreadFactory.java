/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.TaskThread;

public class ThreadFactory {
    public TaskThread newThread(Task task) {
        return new TaskThread(task);
    }
}

