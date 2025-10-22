/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.nr.attachment.transfer.context.TaskContext;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskDispatch;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskScheduler;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskThreadFactory;
import com.jiuqi.nr.attachment.transfer.dispatch.TransferThread;
import com.jiuqi.nr.attachment.transfer.log.AttachmentLogHelper;
import com.jiuqi.nr.attachment.transfer.monitor.DefaultTaskMonitor;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.task.AbstractTaskProvider;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;

public class TaskDaemonThread
extends TaskDispatch {
    private final TaskThreadFactory taskThreadFactory = new TaskThreadFactory();
    private final AbstractTaskProvider taskProvider;

    public TaskDaemonThread(TaskScheduler scheduler, AbstractTaskProvider taskProvider, TaskContext taskContext) {
        super(scheduler, taskContext);
        this.taskProvider = taskProvider;
        this.setDaemon(true);
        this.setName("\u5927\u9644\u4ef6\u5bfc\u5165\u5bfc\u51fa-\u8c03\u5ea6\u7ebf\u7a0b");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        AttachmentLogHelper.info("\u8c03\u5ea6\u7ebf\u7a0b\u542f\u52a8");
        TaskMonitor monitor = this.taskContext.getMonitor();
        try {
            while (this.isRunning()) {
                if (!this.taskProvider.hasNext()) continue;
                this.lock();
                TransferTask task = this.taskProvider.next();
                DefaultTaskMonitor childrenMonitor = new DefaultTaskMonitor(task.getId(), monitor);
                childrenMonitor.setStatusModifier(this.taskContext.getModifier());
                TransferThread taskThread = this.taskThreadFactory.createTaskThread(task, childrenMonitor, this.taskContext.getContext());
                AttachmentLogHelper.debug("\u63d0\u4ea4\u4efb\u52a1:{}", task.getId());
                this.scheduler.addTask(taskThread);
                this.unLock();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            AttachmentLogHelper.info("\u8c03\u5ea6\u7ebf\u7a0b\u7ed3\u675f");
        }
    }

    public AbstractTaskProvider getTaskProvider() {
        return this.taskProvider;
    }
}

