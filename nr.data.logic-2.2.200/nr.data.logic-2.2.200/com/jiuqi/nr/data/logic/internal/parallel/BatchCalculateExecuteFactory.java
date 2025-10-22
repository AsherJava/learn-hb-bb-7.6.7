/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.IBatchTaskExecuteFactory
 *  com.jiuqi.nr.parallel.IParallelTaskExecuter
 *  com.jiuqi.nr.parallel.impl.BatchParallelMonitor
 */
package com.jiuqi.nr.data.logic.internal.parallel;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.logic.monitor.CusBPMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IBatchTaskExecuteFactory;
import com.jiuqi.nr.parallel.IParallelTaskExecuter;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BatchCalculateExecuteFactory
implements IBatchTaskExecuteFactory {
    public static final String TYPE = "parallel.calc.new";
    @Autowired
    @Qualifier(value="parallelCalculateTaskExecuterNew")
    private IParallelTaskExecuter calcTaskExecuter;

    public String getType() {
        return TYPE;
    }

    public BatchParallelMonitor getMonitor(BatchParallelExeTask task) {
        return new CusBPMonitor(task);
    }

    public BatchParallelMonitor getMonitor(BatchParallelExeTask task, AsyncTaskMonitor monitor) {
        CusBPMonitor batchParallelMonitor = new CusBPMonitor(task);
        batchParallelMonitor.setAsyncTaskMonitor(monitor);
        return batchParallelMonitor;
    }

    public IParallelTaskExecuter getIParallelTaskExecuter() {
        return this.calcTaskExecuter;
    }
}

