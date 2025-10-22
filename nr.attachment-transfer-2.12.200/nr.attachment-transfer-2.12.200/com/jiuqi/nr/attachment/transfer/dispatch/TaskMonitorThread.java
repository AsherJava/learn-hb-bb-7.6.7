/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.nr.attachment.transfer.check.ResourceCheckFactory;
import com.jiuqi.nr.attachment.transfer.context.TaskContext;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskDispatch;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskScheduler;
import com.jiuqi.nr.attachment.transfer.dispatch.TransferThread;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.TaskStatus;

public class TaskMonitorThread
extends TaskDispatch {
    public TaskMonitorThread(TaskScheduler scheduler, TaskContext taskContext) {
        super(scheduler, taskContext);
        this.setDaemon(true);
        this.setName("\u5927\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa-\u76d1\u63a7\u7ebf\u7a0b");
    }

    @Override
    public void run() {
        AttachmentLogHelper.info("\u76d1\u63a7\u7ebf\u7a0b\u542f\u52a8");
        ResourceCheckFactory check = this.taskContext.getCheck();
        TaskMonitor monitor = this.taskContext.getMonitor();
        monitor.setStatus(TaskStatus.RUNNING);
        int emptyGet = 0;
        while (this.isRunning()) {
            try {
                this.lock();
                AttachmentLogHelper.debug("\u83b7\u53d6\u4fe1\u53f7\u91cf");
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean hasTask = this.scheduler.hasTask();
            if (check.getDiskCheck() != null && !check.getDiskCheck().check()) {
                AttachmentLogHelper.info("\u68c0\u6d4b\u5230\u78c1\u76d8\u7a7a\u95f4\u4e0d\u8db3");
                if (!hasTask) {
                    AttachmentLogHelper.info("\u7a7a\u95f4\u4e0d\u8db3\uff0c\u505c\u6b62\u4efb\u52a1");
                    this.stopThread(false, true, true);
                    continue;
                }
                this.stopThread(false, true, false);
                this.unLock();
                AttachmentLogHelper.debug("\u91ca\u653e\u4fe1\u53f7\u91cf-\u7a7a\u95f4\u4e0d\u8db3");
                continue;
            }
            if (check.getMemoryCheck() != null && !check.getMemoryCheck().check()) {
                AttachmentLogHelper.info("\u68c0\u6d4b\u5230\u5185\u5b58\u7a7a\u95f4\u4e0d\u8db3");
                if (!hasTask) {
                    AttachmentLogHelper.info("\u5185\u5b58\u4e0d\u8db3\uff0c\u6682\u505c\u4efb\u52a1");
                    this.pauseThread();
                } else {
                    try {
                        AttachmentLogHelper.debug("\u5185\u5b58\u4e0d\u8db3\uff0c\u7b49\u5f85\u5524\u9192");
                        monitor.sleep(120000L);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    AttachmentLogHelper.debug("\u5185\u5b58\u4e0d\u8db3\u88ab\uff0c\u88ab\u5524\u9192");
                    this.unLock();
                    AttachmentLogHelper.debug("\u91ca\u653e\u4fe1\u53f7\u91cf-\u5185\u5b58\u963b\u585e\u5b8c\u6bd5");
                    continue;
                }
            }
            if (this.scheduler.fullWork()) {
                try {
                    AttachmentLogHelper.debug("\u7ebf\u7a0b\u961f\u5217\u6ee1\uff0c\u7b49\u5f85\u5524\u9192\u540e\u91cd\u65b0\u6392\u961f");
                    monitor.sleep(120000L);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.unLock();
                AttachmentLogHelper.debug("\u91ca\u653e\u4fe1\u53f7\u91cf-\u961f\u5217\u963b\u585e\u5b8c\u6bd5");
                continue;
            }
            try {
                TransferThread take = this.scheduler.take();
                if (take == null) {
                    if (emptyGet <= this.scheduler.thread()) {
                        ++emptyGet;
                        continue;
                    }
                    if (this.scheduler.getActiveCount() > 0L) {
                        AttachmentLogHelper.debug("\u7b49\u5f85\u4efb\u52a1\u6267\u884c\u5b8c\u6bd5\u540e\u7ed3\u675f\u76d1\u63a7");
                        continue;
                    }
                    AttachmentLogHelper.debug("\u65e0\u4efb\u52a1\uff0c\u4e2d\u6b62\u76d1\u63a7\u7ebf\u7a0b");
                    this.stopThread(false, false, false);
                    monitor.finish();
                    continue;
                }
                emptyGet = 0;
                AttachmentLogHelper.debug("\u6d88\u8d39\u4efb\u52a1", take.getName());
                this.scheduler.submitTask(take);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                this.unLock();
                AttachmentLogHelper.debug("\u91ca\u653e\u4fe1\u53f7\u91cf-\u6d88\u8d39\u5b8c\u6bd5");
            }
        }
        AttachmentLogHelper.info("\u76d1\u63a7\u7ebf\u7a0b\u7ed3\u675f");
    }

    public void stopThread(boolean cancel, boolean pause, boolean force) {
        super.stopThread(cancel, force);
        if (pause) {
            TaskMonitor monitor = this.taskContext.getMonitor();
            monitor.pause();
        }
    }

    @Override
    public boolean pauseThread() {
        boolean paused = super.pauseThread();
        TaskMonitor monitor = this.taskContext.getMonitor();
        monitor.pause();
        return paused;
    }

    @Override
    public boolean resumeThread() {
        boolean resumed = super.resumeThread();
        TaskMonitor monitor = this.taskContext.getMonitor();
        monitor.resume();
        return resumed;
    }
}

