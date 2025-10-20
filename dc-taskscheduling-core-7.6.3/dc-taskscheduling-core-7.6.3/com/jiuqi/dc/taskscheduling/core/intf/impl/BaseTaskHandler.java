/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 */
package com.jiuqi.dc.taskscheduling.core.intf.impl;

import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;

public abstract class BaseTaskHandler
implements ITaskHandler {
    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public String getSpecialQueueFlag() {
        return null;
    }

    @Deprecated
    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        return this.handleTask(param);
    }
}

