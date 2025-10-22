/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.nr.attachment.transfer.dispatch.TransferThread;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;
import com.jiuqi.nr.attachment.transfer.task.TransferTask;

public class TaskThreadFactory {
    public TransferThread createTaskThread(TransferTask task, TaskMonitor monitor, NpContext npContext) {
        return new TransferThread(task, monitor, npContext);
    }
}

