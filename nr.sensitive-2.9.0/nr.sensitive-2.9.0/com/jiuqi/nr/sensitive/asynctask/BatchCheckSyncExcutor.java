/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 */
package com.jiuqi.nr.sensitive.asynctask;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.nr.sensitive.service.SensitiveWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchCheckSyncExcutor
implements NpAsyncTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckSyncExcutor.class);
    @Autowired
    SensitiveWordService sensitiveWordService;
    public static final String ASYNCTASK_BATCH_CHECK = "ASYNCTASK_BATCH_CHECK";

    public void execute(Object args, AsyncTaskMonitor monitor) {
        if (args instanceof String) {
            String downloadKey = (String)args;
            try {
                this.sensitiveWordService.batchCheckSensitiveWord(downloadKey, monitor);
                if (monitor.isCancel()) {
                    String retStr = "\u4efb\u52a1\u53d6\u6d88";
                    monitor.canceled(retStr, (Object)retStr);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public String getTaskPoolType() {
        return ASYNCTASK_BATCH_CHECK;
    }
}

