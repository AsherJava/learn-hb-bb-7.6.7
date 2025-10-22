/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.task;

import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;

public abstract class TransferTask {
    protected String id;

    public abstract void run(TaskMonitor var1) throws InterruptedException;

    public String getId() {
        return this.id;
    }
}

