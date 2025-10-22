/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task;

import com.jiuqi.nr.single.core.task.ISingleTaskEngine;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.service.ISingleTaskData;
import com.jiuqi.nr.single.core.task.service.impl.SingleTaskDataImpl;

public class SingleTaskDirEngine
implements ISingleTaskEngine {
    protected String taskDir;

    public SingleTaskDirEngine(String taskDir) {
        this.taskDir = taskDir;
    }

    @Override
    public String getTaskDir() {
        return this.taskDir;
    }

    @Override
    public ISingleTaskData openTask() throws SingleTaskException {
        SingleTaskDataImpl taskData = new SingleTaskDataImpl(this);
        taskData.init();
        return taskData;
    }

    @Override
    public void closeTask(ISingleTaskData taskDb) {
        taskDb.close();
    }
}

