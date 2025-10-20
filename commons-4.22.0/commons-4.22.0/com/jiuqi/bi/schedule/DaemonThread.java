/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.ITaskProvider;
import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.TaskScheduler;
import com.jiuqi.bi.schedule.matcher.IPeriodMatcher;
import com.jiuqi.bi.schedule.matcher.PeriodMatcherFactory;
import java.util.Calendar;
import java.util.Iterator;

public class DaemonThread
extends Thread {
    private int spaceTime = 10000;
    private TaskScheduler scheduler = null;
    private boolean running = true;

    public DaemonThread(TaskScheduler scheduler) {
        this.scheduler = scheduler;
        this.setDaemon(true);
        this.setName("\u4efb\u52a1\u8c03\u5ea6\u7ebf\u7a0b\u2014\u2014\u5b88\u62a4\u7ebf\u7a0b");
    }

    @Override
    public void run() {
        while (this.running) {
            Calendar now = Calendar.getInstance();
            ITaskProvider provider = this.scheduler.getTaskProvider();
            if (provider == null) {
                return;
            }
            Iterator<Task> tasks = provider.getTasks();
            while (tasks.hasNext()) {
                IPeriodMatcher matcher;
                Task task = tasks.next();
                if (this.inOnceMinute(now.getTimeInMillis(), task.getLastPeriodTime()) || !(matcher = PeriodMatcherFactory.getMatcher(task.getType())).match(now, task)) continue;
                this.scheduler.getTaskQueue().add(task);
            }
            for (int i = 0; i < this.spaceTime / 1000; ++i) {
                if (this.running) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                return;
            }
        }
    }

    public void stopRunning() {
        this.running = false;
    }

    public int getSpaceTime() {
        return this.spaceTime;
    }

    public void setSpaceTime(int spaceTime) {
        this.spaceTime = spaceTime;
    }

    private boolean inOnceMinute(long firstTime, long secondTime) {
        int firstMinute = (int)(firstTime / 60000L);
        int secondeMinute = (int)(secondTime / 60000L);
        return firstMinute == secondeMinute;
    }
}

