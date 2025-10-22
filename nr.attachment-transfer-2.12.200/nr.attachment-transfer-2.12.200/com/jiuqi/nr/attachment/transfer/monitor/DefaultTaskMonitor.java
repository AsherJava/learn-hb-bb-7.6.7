/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.TaskStatus;

public class DefaultTaskMonitor
implements TaskMonitor {
    private final String id;
    private final Object lock = new Object();
    private TaskMonitor parent;
    private TaskStatus taskStatus = TaskStatus.NONE;
    private IStatusModifier statusModify;

    public DefaultTaskMonitor(String id) {
        this.id = id;
    }

    public DefaultTaskMonitor(String id, TaskMonitor parent) {
        this.id = id;
        this.parent = parent;
    }

    public void setStatusModifier(IStatusModifier statusModifier) {
        this.statusModify = statusModifier;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sleep(long time) throws InterruptedException {
        Object object = this.lock;
        synchronized (object) {
            this.lock.wait(time);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void wakeUp() {
        Object object = this.lock;
        synchronized (object) {
            this.lock.notify();
        }
    }

    @Override
    public TaskMonitor getParent() {
        return this.parent;
    }

    @Override
    public void setStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        this.modify();
    }

    @Override
    public void pause() {
        this.statusModify.pause(this.id);
    }

    @Override
    public void resume() {
        this.statusModify.resume(this.id);
    }

    @Override
    public void finish() {
        this.taskStatus = TaskStatus.FINISH;
        this.modify();
    }

    @Override
    public void error(String info) {
        this.taskStatus = TaskStatus.ERROR;
        this.statusModify.error(this.id, info);
    }

    @Override
    public void cancel() {
        this.taskStatus = TaskStatus.CANCEL;
        this.modify();
    }

    @Override
    public TaskStatus getStatus() {
        return this.taskStatus;
    }

    @Override
    public IStatusModifier getStatusModifier() {
        return this.statusModify;
    }

    private void modify() {
        if (this.statusModify == null) {
            return;
        }
        switch (this.taskStatus) {
            case RUNNING: {
                this.statusModify.start(this.id);
                break;
            }
            case FINISH: {
                this.statusModify.end(this.id, true);
                break;
            }
            case INTERRUPT: 
            case CANCEL: {
                this.statusModify.cancel(this.id);
                break;
            }
            default: {
                this.statusModify.ready(this.id);
            }
        }
        AttachmentLogHelper.debug("{}\u4fee\u6539\u72b6\u6001\u4f4d\uff1a{}", this.id, this.taskStatus.name());
    }
}

