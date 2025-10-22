/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 *  org.springframework.data.redis.core.ListOperations
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskComsumer;
import com.jiuqi.nr.parallel.IParallelTaskExeInfoHandler;
import com.jiuqi.nr.parallel.impl.ParallelTaskAsyncRunner;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Deprecated
public class RedisComsumer
implements IParallelTaskComsumer {
    private static final Logger logger = LoggerFactory.getLogger(RedisComsumer.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ParallelTaskAsyncRunner runner;
    @Resource
    private DataSource dataSource;
    private String dbuuid;
    private int parallelSize = 5;
    private Set<BatchParallelExeTask> runningTasks = new HashSet<BatchParallelExeTask>();
    private Set<String> quenes = new HashSet<String>();
    @Autowired
    private IParallelTaskExeInfoHandler infoHandler;

    @Override
    public BatchParallelExeTask getTask(String queueId) {
        ListOperations listOperations = this.redisTemplate.opsForList();
        BatchParallelExeTask task = (BatchParallelExeTask)listOperations.rightPop((Object)queueId);
        if (task != null) {
            logger.info(" consume task " + task.getKey());
        }
        return task;
    }

    @Override
    public void consume() throws Exception {
        this.tryDispatch();
    }

    public void tryDispatch() {
        for (String queue : this.quenes) {
            BatchParallelExeTask task;
            if (this.runningTasks.size() >= this.parallelSize || (task = this.getTask(queue)) == null) continue;
            this.infoHandler.taskRunning(task.getKey(), task.getParentTaskID());
            this.runningTasks.add(task);
            this.runner.runTask(task);
        }
        Iterator<BatchParallelExeTask> taskIterator = this.runningTasks.iterator();
        while (taskIterator.hasNext()) {
            BatchParallelExeTask task = taskIterator.next();
            if (task.isFinished()) {
                taskIterator.remove();
                continue;
            }
            this.infoHandler.taskRunning(task.getKey(), task.getParentTaskID());
        }
    }

    public void regQueue(String type) {
    }
}

