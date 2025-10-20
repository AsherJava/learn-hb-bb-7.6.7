/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.TaskScheduler;
import com.jiuqi.bi.schedule.TaskThread;

public class MonitorThread
extends Thread {
    private TaskScheduler scheduler = null;
    private boolean running = true;

    public MonitorThread(TaskScheduler scheduler) {
        this.scheduler = scheduler;
        this.setDaemon(true);
        this.setName("\u4efb\u52a1\u8c03\u5ea6\u7ebf\u7a0b\u2014\u2014\u76d1\u63a7\u7ebf\u7a0b");
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                Task task = this.scheduler.getTaskQueue().take();
                TaskThread thread = this.scheduler.getThreadFactory().newThread(task);
                thread.start();
            }
            catch (InterruptedException e) {
                if (this.running) {
                    e.printStackTrace();
                    continue;
                }
                return;
            }
        }
    }

    public void stopRunning() {
        this.running = false;
        this.interrupt();
    }
}

