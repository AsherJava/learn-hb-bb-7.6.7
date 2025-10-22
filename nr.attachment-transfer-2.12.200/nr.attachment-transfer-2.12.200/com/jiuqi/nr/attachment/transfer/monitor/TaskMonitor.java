/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import com.jiuqi.nr.attachment.transfer.monitor.TaskStatus;

public interface TaskMonitor {
    public void sleep(long var1) throws InterruptedException;

    public void wakeUp();

    public TaskMonitor getParent();

    public void setStatus(TaskStatus var1);

    public void pause();

    public void resume();

    public void finish();

    public void error(String var1);

    public void cancel();

    public TaskStatus getStatus();

    public IStatusModifier getStatusModifier();
}

