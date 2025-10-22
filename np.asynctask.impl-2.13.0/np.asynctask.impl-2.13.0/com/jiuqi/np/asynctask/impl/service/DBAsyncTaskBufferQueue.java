/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskBufferQueue
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.np.asynctask.AsyncTaskBufferQueue;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.dao.DBQueueDao;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

public class DBAsyncTaskBufferQueue
implements AsyncTaskBufferQueue {
    @Autowired
    private AsyncTaskDao dao;
    @Autowired
    private DBQueueDao queueDao;

    public String receive(String queueName, String serveCode) {
        String taskId = this.queueDao.receive(queueName, serveCode);
        if (Objects.nonNull(taskId)) {
            this.dao.updateEffectTime(serveCode, taskId);
        }
        return taskId;
    }

    public void publish(String queueName, String taskId, String taskPoolType, Integer priority) {
        if (queueName.equals("np_asynctask_simple_queue")) {
            this.queueDao.publishSimpleQueue(taskId, taskPoolType, priority);
        } else if (queueName.equals("np_asynctask_split_queue")) {
            this.queueDao.publishSplitQueue(taskId, taskPoolType, priority);
        }
    }
}

