/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.intf;

import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;

public interface ITaskMonitor
extends ITaskProgressMonitor {
    public String getTaskLogItemId();

    public boolean beforeStart();

    public void start();

    public void finish(TaskHandleResult var1);

    public void error(String var1, Throwable var2);
}

