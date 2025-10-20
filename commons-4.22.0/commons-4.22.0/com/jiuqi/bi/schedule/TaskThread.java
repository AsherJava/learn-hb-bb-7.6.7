/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.Task;

public class TaskThread
extends Thread {
    private Task task;

    public TaskThread(Task task) {
        this.task = task;
        this.setDaemon(false);
        this.setName("\u4efb\u52a1\u8c03\u5ea6\u7ebf\u7a0b\u2014\u2014\u4efb\u52a1\u6267\u884c\u7ebf\u7a0b\uff08" + task.getName() + "\uff09");
    }

    @Override
    public void run() {
        this.task.execute(null);
    }
}

