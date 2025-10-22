/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task;

import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.service.ISingleTaskData;

public interface ISingleTaskEngine {
    public String getTaskDir();

    public ISingleTaskData openTask() throws SingleTaskException;

    public void closeTask(ISingleTaskData var1);
}

