/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.io.datacheck.param;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.io.params.base.TableContext;

public class TransferParam {
    private String taskKey;
    private String regionKey;
    private TableContext tableContext;
    private ExecutorContext executorContext;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public TableContext getTableContext() {
        return this.tableContext;
    }

    public void setTableContext(TableContext tableContext) {
        this.tableContext = tableContext;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }
}

