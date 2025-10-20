/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.trace;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class JVMMonitor {
    private final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private final OperatingSystemMXBean systemBean = ManagementFactory.getOperatingSystemMXBean();
    private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    private long intervalTime = 1L;
    private TimeUnit intervalUnit = TimeUnit.SECONDS;
    private int maxSnapshotSize = -1;
    private Consumer<StatusInfo> snapshotHandler;
    private Deque<StatusInfo> snapshots = new ConcurrentLinkedDeque<StatusInfo>();
    private ScheduledExecutorService scheduleService;
    private ScheduledFuture<?> monitor;
    private long preTime;
    private long preUsedTime;
    private static final List<String> SPACE_UNITS = Arrays.asList("", "KB", "MB", "GB", "TB", "PB", "EB", "ZB");

    public JVMMonitor setInterval(long intervalTime, TimeUnit intervalUnit) {
        this.intervalTime = intervalTime;
        this.intervalUnit = intervalUnit;
        return this;
    }

    public JVMMonitor setSnapshotHandler(Consumer<StatusInfo> snapshotHandler) {
        this.snapshotHandler = snapshotHandler;
        return this;
    }

    public JVMMonitor setMaxSnapshotSize(int maxSnapshotSize) {
        this.maxSnapshotSize = maxSnapshotSize;
        return this;
    }

    public synchronized JVMMonitor start() {
        if (this.scheduleService != null || this.monitor != null) {
            throw new IllegalStateException("\u865a\u62df\u673a\u76d1\u63a7\u5df2\u7ecf\u542f\u52a8");
        }
        this.createSnapshot();
        this.scheduleService = Executors.newScheduledThreadPool(1, new ThreadFactory(){

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "JVMMonitor");
                t.setDaemon(true);
                return t;
            }
        });
        this.monitor = this.scheduleService.scheduleAtFixedRate(() -> this.snapshot(), this.intervalTime, this.intervalTime, this.intervalUnit);
        return this;
    }

    private void snapshot() {
        StatusInfo status = this.createSnapshot();
        this.snapshots.addLast(status);
        Consumer<StatusInfo> handler = this.snapshotHandler;
        if (handler != null) {
            handler.accept(status);
        }
        if (this.maxSnapshotSize > 0 && this.snapshots.size() > this.maxSnapshotSize) {
            this.snapshots.removeFirst();
        }
    }

    private StatusInfo createSnapshot() {
        StatusInfo status = new StatusInfo();
        MemoryUsage heapUsage = this.memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = this.memoryBean.getNonHeapMemoryUsage();
        long usedMemory = heapUsage.getUsed() + nonHeapUsage.getUsed();
        long totalTime = 0L;
        for (long id : this.threadBean.getAllThreadIds()) {
            totalTime += this.threadBean.getThreadUserTime(id);
        }
        long currentTime = System.nanoTime();
        long usedTime = totalTime - this.preUsedTime;
        long passedTime = currentTime - this.preTime;
        this.preTime = currentTime;
        this.preUsedTime = totalTime;
        double cpuLoad = (double)usedTime / (double)(passedTime * (long)this.systemBean.getAvailableProcessors());
        return status.setUsedMemory(usedMemory).setThreadSize(this.threadBean.getThreadCount()).setCPULoad(cpuLoad);
    }

    public List<StatusInfo> stat() {
        StatusInfo curStatus;
        int count = 0;
        long minMemory = 0L;
        long maxMemory = 0L;
        long sumMemory = 0L;
        double minCPU = 0.0;
        double maxCPU = 0.0;
        double sumCPU = 0.0;
        int minThreads = 0;
        int maxThreads = 0;
        int sumThreads = 0;
        long curTime = System.currentTimeMillis();
        while ((curStatus = this.snapshots.peekFirst()) != null && curStatus.getTime() <= curTime) {
            ++count;
            this.snapshots.removeFirst();
            if (minMemory == 0L || minMemory > curStatus.getUsedMemory()) {
                minMemory = curStatus.getUsedMemory();
            }
            if (maxMemory == 0L || maxMemory < curStatus.getUsedMemory()) {
                maxMemory = curStatus.getUsedMemory();
            }
            sumMemory += curStatus.getUsedMemory();
            if (minCPU == 0.0 || minCPU > curStatus.getCPULoad()) {
                minCPU = curStatus.getCPULoad();
            }
            if (maxCPU == 0.0 || maxCPU < curStatus.getCPULoad()) {
                maxCPU = curStatus.getCPULoad();
            }
            sumCPU += curStatus.getCPULoad();
            if (minThreads == 0 || minThreads > curStatus.getThreadSize()) {
                minThreads = curStatus.getThreadSize();
            }
            if (maxThreads == 0 || maxThreads < curStatus.getThreadSize()) {
                maxThreads = curStatus.getThreadSize();
            }
            sumThreads += curStatus.getThreadSize();
        }
        if (count == 0) {
            return Collections.emptyList();
        }
        StatusInfo minStatus = new StatusInfo(curTime).setUsedMemory(minMemory).setCPULoad(minCPU).setThreadSize(minThreads);
        StatusInfo avgStatus = new StatusInfo(curTime).setUsedMemory(sumMemory / (long)count).setCPULoad(sumCPU / (double)count).setThreadSize(sumThreads / count);
        StatusInfo maxStatus = new StatusInfo(curTime).setUsedMemory(maxMemory).setCPULoad(maxCPU).setThreadSize(maxThreads);
        return Arrays.asList(minStatus, avgStatus, maxStatus);
    }

    public synchronized void shutdown() {
        if (this.monitor != null) {
            this.monitor.cancel(true);
            this.monitor = null;
        }
        if (this.scheduleService != null) {
            this.scheduleService.shutdown();
            this.scheduleService = null;
        }
    }

    public static String formatSpaceSize(long size) {
        int unitIndex;
        if (size < 1024L) {
            DecimalFormat fmt = new DecimalFormat("#,##0");
            return fmt.format(size);
        }
        double value = size;
        for (unitIndex = 0; value > 1024.0 && unitIndex < SPACE_UNITS.size() - 1; value /= 1024.0, ++unitIndex) {
        }
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        return fmt.format(value) + " " + SPACE_UNITS.get(unitIndex);
    }

    public static final class StatusInfo
    implements Cloneable {
        private final long time;
        private long usedMemory;
        private double cpuLoad;
        private int threadSize;

        public StatusInfo() {
            this.time = System.currentTimeMillis();
        }

        public StatusInfo(long time) {
            this.time = time;
        }

        public long getTime() {
            return this.time;
        }

        public long getUsedMemory() {
            return this.usedMemory;
        }

        StatusInfo setUsedMemory(long usedMemory) {
            this.usedMemory = usedMemory;
            return this;
        }

        public double getCPULoad() {
            return this.cpuLoad;
        }

        StatusInfo setCPULoad(double cpuLoad) {
            this.cpuLoad = cpuLoad;
            return this;
        }

        public int getThreadSize() {
            return this.threadSize;
        }

        StatusInfo setThreadSize(int threadSize) {
            this.threadSize = threadSize;
            return this;
        }

        public Object clone() {
            try {
                return super.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new UnsupportedOperationException(e);
            }
        }

        public String toString() {
            StringBuilder buffer = new StringBuilder();
            SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = new Date(this.time);
            DecimalFormat percentFmt = new DecimalFormat("0.00%");
            buffer.append('(').append(timeFmt.format(date)).append(" Memory: ").append(JVMMonitor.formatSpaceSize(this.usedMemory)).append(", CPU: ").append(percentFmt.format(this.cpuLoad)).append(", Threads: ").append(this.threadSize).append(')');
            return buffer.toString();
        }
    }
}

