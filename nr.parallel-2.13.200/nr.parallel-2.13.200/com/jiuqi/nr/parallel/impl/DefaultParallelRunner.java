/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.BatchTaskExecuteFactoryMgr;
import com.jiuqi.nr.parallel.IBatchTaskExecuteFactory;
import com.jiuqi.nr.parallel.IParallelRunner;
import com.jiuqi.nr.parallel.IParallelTaskSplitStrategy;
import com.jiuqi.nr.parallel.ParallelExeInfo;
import com.jiuqi.nr.parallel.asynctask.ParallelAsyncTaskExecutor;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;
import com.jiuqi.nr.parallel.impl.BatchSerialMonitor;
import com.jiuqi.nr.parallel.impl.DefaultSplitStrategy;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultParallelRunner
implements IParallelRunner {
    private static final Logger logger = LoggerFactory.getLogger(DefaultParallelRunner.class);
    @Autowired
    private IParallelTaskSplitStrategy strategy;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    private static final long TIME_OUT = 300000L;
    private static final long SLEEP_TIME = 500L;

    @Override
    public void run(BatchParallelExeTask task, IMonitor monitor) throws Exception {
        this.run(task, monitor, false);
    }

    @Override
    public void run(BatchParallelExeTask task, IMonitor monitor, boolean splitByParallelSize) throws Exception {
        logger.debug("batch Task run");
        List<BatchParallelExeTask> subTasks = splitByParallelSize ? this.doSplit(task, new DefaultSplitStrategy(1)) : this.doSplit(task, this.strategy);
        if (subTasks.size() == 1) {
            IBatchTaskExecuteFactory executeFactory = BatchTaskExecuteFactoryMgr.getInstance().findFactory(task.getType());
            executeFactory.getIParallelTaskExecuter().doExecute(subTasks.get(0), monitor);
        } else {
            this.runTaskByParallel(task, monitor, subTasks);
        }
        logger.debug("batch Task finished");
    }

    protected List<BatchParallelExeTask> doSplit(BatchParallelExeTask task, IParallelTaskSplitStrategy strategy) {
        int listSize = 5;
        String option = this.nvwaSystemOptionService.get("nr-audit-group", "MAX_THREAD_COUNT");
        if (StringUtils.isNotEmpty((String)option)) {
            listSize = Integer.parseInt(option);
        }
        List<BatchParallelExeTask> subTasks = strategy.doSplit(task, listSize);
        return subTasks;
    }

    private void runTaskByParallel(BatchParallelExeTask task, IMonitor monitor, List<BatchParallelExeTask> subTasks) throws InterruptedException {
        logger.debug("parent-{}:task run", (Object)task.getKey());
        for (BatchParallelExeTask batchParallelExeTask : subTasks) {
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchParallelExeTask));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ParallelAsyncTaskExecutor());
            String taskId = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
            batchParallelExeTask.setKey(taskId);
        }
        List<ParallelExeInfo> states = this.initTasks(subTasks, task.getKey());
        for (ParallelExeInfo state : states) {
            logger.debug("parent-{}:task-{}- weight-{}", state.getParentKey(), state.getTaskKey(), state.getWeight());
        }
        double d = 0.0;
        double masterProgress = 0.0;
        boolean canCancel = true;
        while (states.size() > 0) {
            double progress = 0.0;
            Iterator<ParallelExeInfo> it = states.iterator();
            boolean hasTaskCompleted = false;
            while (it.hasNext()) {
                ParallelExeInfo subTaskState;
                ParallelExeInfo info;
                if (canCancel && monitor.isCancel()) {
                    this.cancelTasks(states);
                    canCancel = false;
                }
                if ((info = this.queryTaskInfo((subTaskState = it.next()).getTaskKey(), subTaskState.getParentKey())) == null) continue;
                if (info.getState() == ParallelExeInfo.TaskState.FINISH) {
                    it.remove();
                    d += subTaskState.getWeight();
                    hasTaskCompleted = true;
                    logger.debug("task {} finished", (Object)subTaskState.getTaskKey());
                    continue;
                }
                if (info.getState() == ParallelExeInfo.TaskState.RUNNING) {
                    progress += subTaskState.getWeight() * info.getProgress();
                    continue;
                }
                if (info.getState() != ParallelExeInfo.TaskState.CANCELED) continue;
                it.remove();
                d += subTaskState.getWeight();
                hasTaskCompleted = true;
                logger.debug("task {} canceled", (Object)subTaskState.getTaskKey());
            }
            if (hasTaskCompleted || progress > 0.0) {
                double curProgress = d + progress;
                if (curProgress >= masterProgress) {
                    masterProgress = curProgress;
                    logger.debug("parent-{}:task progress:{}", (Object)task.getKey(), (Object)masterProgress);
                    monitor.onProgress(masterProgress);
                } else {
                    logger.debug("progress error:parent-{}:master progress:{}-curProgress:{}", task.getKey(), masterProgress, curProgress);
                }
            }
            Thread.sleep(500L);
        }
        monitor.finish();
    }

    private void cancelTasks(List<ParallelExeInfo> states) {
        for (ParallelExeInfo subTask : states) {
            this.asyncThreadExecutor.cancelTask(subTask.getTaskKey());
        }
    }

    private void runTaskbySerial(BatchParallelExeTask task, IMonitor monitor, List<BatchParallelExeTask> subTasks) {
        IBatchTaskExecuteFactory executeFactory = BatchTaskExecuteFactoryMgr.getInstance().findFactory(task.getType());
        for (BatchParallelExeTask subTask : subTasks) {
            BatchSerialMonitor serialMonitor = new BatchSerialMonitor();
            serialMonitor.setProgressWeight(task.getWeight());
            AbstractMonitor abstractMonitor = (AbstractMonitor)monitor;
            serialMonitor.setStartProgress(abstractMonitor.getCurrentProgress());
            serialMonitor.setMainMonitor(abstractMonitor);
            BatchParallelMonitor errorMonitor = executeFactory.getMonitor(task);
            serialMonitor.setErrorMonitor(errorMonitor);
            executeFactory.getIParallelTaskExecuter().doExecute(subTask, serialMonitor);
        }
        monitor.finish();
    }

    private ParallelExeInfo queryTaskInfo(String taskKey, String parentKey) {
        ParallelExeInfo info = new ParallelExeInfo();
        info.setTaskKey(taskKey);
        AsyncTask ayncTaskInfo = this.asyncThreadExecutor.queryTask(taskKey);
        if (ayncTaskInfo != null) {
            logger.debug("parent-{}:task {}-{}-{}", parentKey, taskKey, ayncTaskInfo.getProcess(), ayncTaskInfo.getState().getTitle());
            info.setProgress(ayncTaskInfo.getProcess());
            Instant processTime = ayncTaskInfo.getProcessTime();
            if (processTime != null) {
                info.setUpdateTime(processTime.toEpochMilli());
            }
            info.setState(this.getParallelState(ayncTaskInfo.getState()));
        } else {
            logger.debug("parent-{}:task {} not found", (Object)parentKey, (Object)taskKey);
            info.setProgress(1.0);
            info.setState(ParallelExeInfo.TaskState.FINISH);
        }
        return info;
    }

    private ParallelExeInfo.TaskState getParallelState(TaskState state) {
        switch (state) {
            case WAITING: {
                return ParallelExeInfo.TaskState.WAITING;
            }
            case PROCESSING: {
                return ParallelExeInfo.TaskState.RUNNING;
            }
            case CANCELING: {
                return ParallelExeInfo.TaskState.CANCELING;
            }
            case CANCELED: {
                return ParallelExeInfo.TaskState.CANCELED;
            }
            case FINISHED: {
                return ParallelExeInfo.TaskState.FINISH;
            }
        }
        return ParallelExeInfo.TaskState.FINISH;
    }

    private List<ParallelExeInfo> initTasks(List<BatchParallelExeTask> subTasks, String key) {
        ArrayList<ParallelExeInfo> states = new ArrayList<ParallelExeInfo>();
        for (BatchParallelExeTask task : subTasks) {
            ParallelExeInfo state = new ParallelExeInfo();
            state.setTaskKey(task.getKey());
            state.setParentKey(task.getParentTaskID());
            state.setWeight(task.getWeight());
            states.add(state);
        }
        return states;
    }
}

