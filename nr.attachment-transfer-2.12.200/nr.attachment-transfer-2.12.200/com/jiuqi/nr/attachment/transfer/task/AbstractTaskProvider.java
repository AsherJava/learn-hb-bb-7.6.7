/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.task;

import com.jiuqi.nr.attachment.transfer.task.TransferTask;
import java.util.List;

public abstract class AbstractTaskProvider {
    private int cursor = 0;

    protected abstract List<TransferTask> getItems();

    public boolean hasNext() {
        return this.getItems().size() > this.cursor;
    }

    public TransferTask next() {
        return this.getItems().get(this.cursor++);
    }

    public abstract void append(TransferTask var1);
}

