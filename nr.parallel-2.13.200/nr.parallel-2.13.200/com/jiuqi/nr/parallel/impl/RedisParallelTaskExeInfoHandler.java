/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.core.ValueOperations
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskExeInfoHandler;
import com.jiuqi.nr.parallel.ParallelExeInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Deprecated
public class RedisParallelTaskExeInfoHandler
implements IParallelTaskExeInfoHandler {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void recordProgress(String taskKey, double progress, String parentKey) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        ParallelExeInfo info = (ParallelExeInfo)opsForValue.get((Object)taskKey);
        if (info != null) {
            if (info.getState() == ParallelExeInfo.TaskState.WAITING) {
                info.setState(ParallelExeInfo.TaskState.RUNNING);
            }
            info.setProgress(progress);
            opsForValue.set((Object)taskKey, (Object)info);
        }
    }

    @Override
    public List<ParallelExeInfo> initTasks(List<BatchParallelExeTask> tasks, String parentKey) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        ArrayList<String> taskIds = new ArrayList<String>();
        ArrayList<ParallelExeInfo> states = new ArrayList<ParallelExeInfo>();
        for (BatchParallelExeTask task : tasks) {
            ParallelExeInfo state = new ParallelExeInfo();
            state.setTaskKey(task.getKey());
            state.setParentKey(task.getParentTaskID());
            state.setWeight(task.getWeight());
            opsForValue.set((Object)task.getKey(), (Object)state);
            taskIds.add(task.getKey());
            states.add(state);
        }
        opsForValue.set((Object)parentKey, taskIds);
        return states;
    }

    @Override
    public ParallelExeInfo queryTaskInfo(String taskKey, String parentKey) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        return (ParallelExeInfo)opsForValue.get((Object)taskKey);
    }

    public void clearTaskInfos(String parentKey) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        List taskIds = (List)opsForValue.get((Object)parentKey);
        for (String taskId : taskIds) {
            this.redisTemplate.delete((Object)taskId);
        }
        this.redisTemplate.delete((Object)parentKey);
    }

    public void finishTask(String taskKey, String parentKey) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        ParallelExeInfo info = (ParallelExeInfo)opsForValue.get((Object)taskKey);
        if (info != null) {
            info.setState(ParallelExeInfo.TaskState.FINISH);
            opsForValue.set((Object)taskKey, (Object)info);
        }
    }

    @Override
    public void taskRunning(String taskKey, String parentKey) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        ParallelExeInfo info = (ParallelExeInfo)opsForValue.get((Object)taskKey);
        if (info != null) {
            if (info.getState() == ParallelExeInfo.TaskState.WAITING) {
                info.setState(ParallelExeInfo.TaskState.RUNNING);
            }
            info.setUpdateTime(System.currentTimeMillis());
            opsForValue.set((Object)taskKey, (Object)info);
        }
    }

    @Override
    public void updateTask(ParallelExeInfo taskInfo) {
        ValueOperations opsForValue = this.redisTemplate.opsForValue();
        opsForValue.set((Object)taskInfo.getTaskKey(), (Object)taskInfo);
    }
}

