/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.concurrent;

import com.jiuqi.bi.logging.LogManager;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.LoggerFactory;

public class Daemon {
    private final String name;
    private final ScheduledExecutorService scheduler;
    private final ForkJoinPool pool;
    private final AtomicInteger number;
    private volatile boolean shutdown;
    private static final Daemon GLOBAL = new Daemon("NVWA-D"){

        @Override
        public boolean shutdown(long timeout, TimeUnit unit) throws InterruptedException {
            throw new UnsupportedOperationException("\u7981\u6b62\u5173\u95ed\u5168\u5c40\u8c03\u5ea6\u5668");
        }
    };

    public Daemon(String name, int corePoolSize, int parallelism) {
        this.name = Objects.requireNonNull(name, "\u6ca1\u6709\u6307\u5b9a\u6709\u6548\u7684\u5b88\u62a4\u8fdb\u7a0b\u540d\u79f0");
        this.number = new AtomicInteger();
        this.scheduler = Executors.newScheduledThreadPool(corePoolSize, new DaemonThreadFactory());
        this.pool = new DaemonForkJoinPool(parallelism);
    }

    public Daemon(String name, int corePoolSize) {
        this(name, corePoolSize, Daemon.defaultParallelism());
    }

    public Daemon(String name) {
        this(name, Daemon.defaultCorePoolSize(), Daemon.defaultParallelism());
    }

    private static int defaultCorePoolSize() {
        String poolSize = System.getProperty("com.jiuqi.bi.daemon.poolSize");
        if (poolSize != null && !poolSize.isEmpty()) {
            try {
                int value = Integer.parseInt(poolSize);
                if (value >= 0) {
                    return value;
                }
                LogManager.getLogger(Daemon.class).error("\u5b88\u62a4\u8fdb\u7a0b\u9ed8\u8ba4\u7ebf\u7a0b\u6c60\u5927\u5c0f(com.jiuqi.bi.daemon.poolSize)\u503c\u6307\u5b9a\u9519\u8bef\uff1a{}", value);
            }
            catch (NumberFormatException e) {
                LogManager.getLogger(Daemon.class).error("\u5b88\u62a4\u8fdb\u7a0b\u9ed8\u8ba4\u7ebf\u7a0b\u6c60\u5927\u5c0f(com.jiuqi.bi.daemon.poolSize)\u683c\u5f0f\u9519\u8bef\uff1a{}", poolSize, e);
            }
        }
        return 1;
    }

    private static int defaultParallelism() {
        String parallelism = System.getProperty("com.jiuqi.bi.daemon.parallelism");
        if (parallelism != null && !parallelism.isEmpty()) {
            try {
                int value = Integer.parseInt(parallelism);
                if (value > 0) {
                    return value;
                }
                LogManager.getLogger(Daemon.class).error("\u5b88\u62a4\u8fdb\u7a0b\u9ed8\u8ba4\u5e76\u884c\u5ea6(com.jiuqi.bi.daemon.parallelism)\u503c\u6307\u5b9a\u9519\u8bef\uff1a{}", value);
            }
            catch (NumberFormatException e) {
                LogManager.getLogger(Daemon.class).error("\u5b88\u62a4\u8fdb\u7a0b\u9ed8\u8ba4\u5e76\u884c\u5ea6(com.jiuqi.bi.daemon.parallelism)\u683c\u5f0f\u9519\u8bef\uff1a{}", parallelism, e);
            }
        }
        int pSize = Runtime.getRuntime().availableProcessors();
        return Math.min(1024, pSize);
    }

    public static Daemon getGlobal() {
        return GLOBAL;
    }

    public ScheduledFuture<?> once(Runnable command) {
        return this.once(command, 0L, TimeUnit.NANOSECONDS);
    }

    public <V> ScheduledFuture<V> once(Callable<V> callable) {
        return this.once(callable, 0L, TimeUnit.NANOSECONDS);
    }

    public ScheduledFuture<?> once(Runnable command, long delay, TimeUnit unit) {
        return this.scheduler.schedule(command, delay, unit);
    }

    public <V> ScheduledFuture<V> once(Callable<V> callable, long delay, TimeUnit unit) {
        return this.scheduler.schedule(callable, delay, unit);
    }

    public ScheduledFuture<?> atFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return this.scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> withFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return this.scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public ForkJoinTask<?> fork(Runnable task) {
        return this.pool.submit(task);
    }

    public <V> ForkJoinTask<V> fork(Callable<V> task) {
        return this.pool.submit(task);
    }

    public <V> ForkJoinTask<V> fork(ForkJoinTask<V> task) {
        return this.pool.submit(task);
    }

    public synchronized boolean shutdown(long timeout, TimeUnit unit) throws InterruptedException {
        if (this.shutdown) {
            return true;
        }
        if (timeout > 0L) {
            this.shutdown = this.scheduler.awaitTermination(timeout, unit) && this.pool.awaitTermination(timeout, unit);
        } else {
            this.scheduler.shutdown();
            this.pool.shutdown();
            this.shutdown = true;
        }
        return this.shutdown;
    }

    public boolean isShutdown() {
        return this.shutdown;
    }

    public String toString() {
        return this.name;
    }

    private final class DaemonForkJoinPool
    extends ForkJoinPool {
        public DaemonForkJoinPool(int parallelism) {
            super(parallelism, new ForkJoinPool.ForkJoinWorkerThreadFactory(){

                @Override
                public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
                    ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
                    worker.setName(Daemon.this.name + "-" + Daemon.this.number.incrementAndGet());
                    return worker;
                }
            }, (t, e) -> LoggerFactory.getLogger(Daemon.class).error("\u5b88\u62a4\u7ebf\u7a0b[{}]\u6267\u884c\u51fa\u9519:{}", t.getName(), e.getMessage(), e), false);
        }
    }

    private final class DaemonThreadFactory
    implements ThreadFactory {
        private final ThreadGroup group;

        public DaemonThreadFactory() {
            SecurityManager sm = System.getSecurityManager();
            this.group = sm == null ? Thread.currentThread().getThreadGroup() : sm.getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r, Daemon.this.name + "-" + Daemon.this.number.incrementAndGet());
            t.setDaemon(true);
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }
}

