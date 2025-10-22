/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.np.asynctask.impl.service.SimpleAsyncTaskDispatcher;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncTaskDispatcherThread {
    @Autowired
    private SimpleAsyncTaskDispatcher simpleAsyncTaskDispatcher;
    private final Logger logger = LoggerFactory.getLogger(AsyncTaskDispatcherThread.class);
    private static volatile Map<String, AtomicInteger> dispatcherMap = new ConcurrentHashMap<String, AtomicInteger>();
    private static Map<String, Thread> dispatcherThreadStore = new ConcurrentHashMap<String, Thread>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean notifyDispather(String taskPoolType) {
        try {
            String string = taskPoolType.intern();
            synchronized (string) {
                if (!dispatcherThreadStore.containsKey(taskPoolType)) {
                    this.initDispatcherThread(taskPoolType);
                }
            }
            Thread disPatcherThread = dispatcherThreadStore.get(taskPoolType);
            this.setDispatcherState(taskPoolType, 1);
            LockSupport.unpark(disPatcherThread);
            return true;
        }
        catch (RuntimeException e) {
            this.logger.error(String.format("\u5f02\u6b65\u4efb\u52a1\u5206\u53d1\u901a\u77e5\u5f02\u5e38-\u5f85\u5206\u53d1{%s}", taskPoolType));
            return false;
        }
    }

    public Optional<AtomicInteger> getDispatcherPoolState(String taskPoolType) {
        if (dispatcherMap.containsKey(taskPoolType)) {
            return Optional.of(dispatcherMap.get(taskPoolType));
        }
        return Optional.of(new AtomicInteger(0));
    }

    public void setDispatcherState(String taskPoolType, int state) {
        if (dispatcherMap.containsKey(taskPoolType)) {
            dispatcherMap.get(taskPoolType).getAndSet(state);
        } else {
            dispatcherMap.put(taskPoolType, new AtomicInteger(state));
        }
    }

    public void initDispatcherThreads(Set<String> taskPoolTypes) {
        ThreadGroup group = new ThreadGroup("ASYNCTASK_DISPATCHER_GROUP");
        for (String taskPoolType : taskPoolTypes) {
            DispatcherThread dispatcherThread = new DispatcherThread(String.format("%s_DISPATCHER_THREAD", taskPoolType), taskPoolType, group);
            dispatcherThread.start();
        }
    }

    public void initDispatcherThread(String taskPoolType) {
        ThreadGroup group = new ThreadGroup("ASYNCTASK_DISPATCHER_GROUP");
        DispatcherThread dispatcherThread = new DispatcherThread(String.format("%s_DISPATCHER_THREAD", taskPoolType), taskPoolType, group);
        dispatcherThread.start();
        dispatcherThreadStore.put(taskPoolType, dispatcherThread);
    }

    public void clearDispatcherThread() {
        this.logger.info("==========begin=============clearDispatcherThread=");
        dispatcherThreadStore.clear();
        dispatcherMap.clear();
        this.logger.info("==========end=============clearDispatcherThread=");
    }

    public void dispatcher(String taskPoolType) {
        this.simpleAsyncTaskDispatcher.dispatch(taskPoolType);
    }

    public class DispatcherThread
    extends Thread {
        private String taskPoolType;

        public DispatcherThread(String name, String taskPoolType, ThreadGroup group) {
            super(group, name);
            this.taskPoolType = taskPoolType;
        }

        @Override
        public void run() {
            while (true) {
                if (AsyncTaskDispatcherThread.this.getDispatcherPoolState(this.taskPoolType).get().get() <= 0) {
                    LockSupport.park();
                    continue;
                }
                AsyncTaskDispatcherThread.this.setDispatcherState(this.taskPoolType, 0);
                AsyncTaskDispatcherThread.this.dispatcher(this.taskPoolType);
            }
        }
    }
}

