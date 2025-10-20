/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule;

import com.jiuqi.bi.schedule.DaemonThread;
import com.jiuqi.bi.schedule.ITaskProvider;
import com.jiuqi.bi.schedule.MonitorThread;
import com.jiuqi.bi.schedule.Task;
import com.jiuqi.bi.schedule.ThreadFactory;
import java.util.concurrent.ArrayBlockingQueue;

public class TaskScheduler {
    private DaemonThread daemonThread = new DaemonThread(this);
    private MonitorThread monitorThread = new MonitorThread(this);
    private ArrayBlockingQueue<Task> queue = new ArrayBlockingQueue(60);
    private ThreadFactory factory = new ThreadFactory();
    private ITaskProvider provider = null;

    public void setTaskProvider(ITaskProvider provider) {
        this.provider = provider;
    }

    protected ITaskProvider getTaskProvider() {
        return this.provider;
    }

    protected ArrayBlockingQueue<Task> getTaskQueue() {
        return this.queue;
    }

    public ThreadFactory getThreadFactory() {
        return this.factory;
    }

    public void start() {
        this.daemonThread.start();
        this.monitorThread.start();
    }

    public void stop() {
        this.daemonThread.stopRunning();
        this.monitorThread.stopRunning();
        try {
            this.daemonThread.join();
            this.monitorThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startTask(Task task) {
        this.queue.add(task);
    }

    public void setSpaceTime(int spaceTime) {
        this.daemonThread.setSpaceTime(spaceTime);
    }
}

