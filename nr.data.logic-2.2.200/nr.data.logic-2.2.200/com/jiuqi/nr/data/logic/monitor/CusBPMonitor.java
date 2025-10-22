/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 */
package com.jiuqi.nr.data.logic.monitor;

import com.jiuqi.nr.data.logic.monitor.base.BatchParallelBaseMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;

public class CusBPMonitor
extends BatchParallelBaseMonitor {
    public CusBPMonitor(BatchParallelExeTask task) {
        super(task);
    }

    public boolean isCancel() {
        if (this.asyncTaskMonitor == null) {
            return super.isCancel();
        }
        return this.asyncTaskMonitor.isCancel();
    }
}

