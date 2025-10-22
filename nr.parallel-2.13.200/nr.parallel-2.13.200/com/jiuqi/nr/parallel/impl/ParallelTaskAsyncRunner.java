/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.BatchTaskExecuteFactoryMgr;
import com.jiuqi.nr.parallel.IBatchTaskExecuteFactory;
import com.jiuqi.nr.parallel.IParallelTaskExecuter;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ParallelTaskAsyncRunner {
    private static final Logger logger = LoggerFactory.getLogger(ParallelTaskAsyncRunner.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runTask(BatchParallelExeTask task) {
        IBatchTaskExecuteFactory factory = BatchTaskExecuteFactoryMgr.getInstance().findFactory(task.getType());
        if (factory != null) {
            IParallelTaskExecuter executer = factory.getIParallelTaskExecuter();
            BatchParallelMonitor monitor = factory.getMonitor(task);
            try {
                executer.doExecute(task, monitor);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            finally {
                task.setFinished(true);
                monitor.finish();
            }
        }
    }
}

