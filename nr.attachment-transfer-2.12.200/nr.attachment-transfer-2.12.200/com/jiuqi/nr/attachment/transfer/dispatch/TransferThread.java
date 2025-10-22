/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.attachment.transfer.exception.TaskException;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.TaskStatus;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;

public class TransferThread
extends Thread {
    private final TransferTask task;
    private final TaskMonitor monitor;
    private NpContext npContext;

    public TransferThread(TransferTask task, TaskMonitor monitor, NpContext npContext) {
        this.task = task;
        this.monitor = monitor;
        this.npContext = npContext;
        super.setName("\u9644\u4ef6\u5bfc\u51fa" + task.getId());
    }

    @Override
    public void run() {
        NpContextHolder.setContext((NpContext)this.npContext);
        this.monitor.setStatus(TaskStatus.RUNNING);
        AttachmentLogHelper.debug("RUN" + this.task.getId());
        try {
            this.task.run(this.monitor);
        }
        catch (Exception e) {
            if (e instanceof TaskException) {
                this.monitor.error(e.getMessage());
            } else if (e instanceof InterruptedException) {
                AttachmentLogHelper.debug(this.task.getId() + "\u7ebf\u7a0b\u88ab\u5f3a\u5236\u4e2d\u65ad");
                this.monitor.error(this.task.getId() + "\u7ebf\u7a0b\u6267\u884c\u4e2d\u88ab\u5f3a\u5236\u4e2d\u65ad");
            } else {
                this.monitor.error("\u6267\u884c\u4efb\u52a1:" + this.task.getId() + "\u65f6,\u5f02\u5e38\uff1a" + e.getMessage());
            }
            AttachmentLogHelper.error(this.task.getId() + "\u751f\u6210JIO\u65f6\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        finally {
            AttachmentLogHelper.debug(this.task.getId() + "\u5524\u9192\u8c03\u5ea6\u7ebf\u7a0b");
            this.monitor.getParent().wakeUp();
            if (this.monitor.getStatus() == TaskStatus.RUNNING) {
                this.monitor.finish();
            }
        }
    }

    public void stopThead() {
        this.monitor.cancel();
        super.interrupt();
    }

    public TaskMonitor getMonitor() {
        return this.monitor;
    }

    public String getTaskId() {
        return this.task.getId();
    }

    public void setStatus(TaskStatus status) {
        this.monitor.setStatus(status);
        if (status == TaskStatus.INTERRUPT || status == TaskStatus.CANCEL) {
            super.interrupt();
        }
    }
}

