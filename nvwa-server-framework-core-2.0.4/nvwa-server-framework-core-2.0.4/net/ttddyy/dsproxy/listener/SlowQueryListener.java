/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.proxy.Stopwatch;
import net.ttddyy.dsproxy.proxy.StopwatchFactory;
import net.ttddyy.dsproxy.proxy.SystemStopwatchFactory;

public class SlowQueryListener
implements QueryExecutionListener {
    protected boolean useDaemonThread = true;
    protected ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory(){

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(SlowQueryListener.this.useDaemonThread);
            return thread;
        }
    });
    protected long threshold;
    protected TimeUnit thresholdTimeUnit;
    protected Map<String, RunningQueryContext> inExecution = new ConcurrentHashMap<String, RunningQueryContext>();
    protected StopwatchFactory stopwatchFactory = new SystemStopwatchFactory();

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        final String execInfoKey = this.getExecutionInfoKey(execInfo);
        Runnable check = new Runnable(){

            @Override
            public void run() {
                RunningQueryContext context = SlowQueryListener.this.inExecution.get(execInfoKey);
                if (context != null) {
                    long elapsedTime = context.stopwatch.getElapsedTime();
                    if (context.executionInfo.getElapsedTime() == 0L) {
                        context.executionInfo.setElapsedTime(elapsedTime);
                    }
                    SlowQueryListener.this.onSlowQuery(context.executionInfo, context.queryInfoList, context.startTimeInMills);
                }
            }
        };
        this.executor.schedule(check, this.threshold, this.thresholdTimeUnit);
        long now = System.currentTimeMillis();
        Stopwatch stopwatch = this.stopwatchFactory.create().start();
        RunningQueryContext context = new RunningQueryContext(execInfo, queryInfoList, now, stopwatch);
        this.inExecution.put(execInfoKey, context);
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        String executionInfoKey = this.getExecutionInfoKey(execInfo);
        this.inExecution.remove(executionInfoKey);
    }

    protected String getExecutionInfoKey(ExecutionInfo executionInfo) {
        int exeInfoKey = System.identityHashCode(executionInfo);
        return String.valueOf(exeInfoKey);
    }

    protected void onSlowQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList, long startTimeInMills) {
    }

    public void setThreshold(long threshHold) {
        this.threshold = threshHold;
    }

    public void setThresholdTimeUnit(TimeUnit thresholdTimeUnit) {
        this.thresholdTimeUnit = thresholdTimeUnit;
    }

    public ScheduledExecutorService getExecutor() {
        return this.executor;
    }

    public long getThreshold() {
        return this.threshold;
    }

    public TimeUnit getThresholdTimeUnit() {
        return this.thresholdTimeUnit;
    }

    public void setUseDaemonThread(boolean useDaemonThread) {
        this.useDaemonThread = useDaemonThread;
    }

    public void setStopwatchFactory(StopwatchFactory stopwatchFactory) {
        this.stopwatchFactory = stopwatchFactory;
    }

    protected static class RunningQueryContext {
        protected ExecutionInfo executionInfo;
        protected List<QueryInfo> queryInfoList;
        protected long startTimeInMills;
        protected Stopwatch stopwatch;

        public RunningQueryContext(ExecutionInfo executionInfo, List<QueryInfo> queryInfoList, long nowInMills, Stopwatch stopwatch) {
            this.executionInfo = executionInfo;
            this.queryInfoList = queryInfoList;
            this.startTimeInMills = nowInMills;
            this.stopwatch = stopwatch;
        }
    }
}

