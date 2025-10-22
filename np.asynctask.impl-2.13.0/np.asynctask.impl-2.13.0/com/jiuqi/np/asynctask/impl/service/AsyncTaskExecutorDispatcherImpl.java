/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskBufferQueue
 *  com.jiuqi.np.asynctask.AsyncTaskExecutorDispatcher
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.np.asynctask.NpContextParam
 *  com.jiuqi.np.asynctask.event.AsyncTaskReadyEvent
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.log.comm.LogTraceIDUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.zaxxer.hikari.HikariDataSource
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskBufferQueue;
import com.jiuqi.np.asynctask.AsyncTaskExecutorDispatcher;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.np.asynctask.NpContextParam;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.event.AsyncTaskReadyEvent;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.log.comm.LogTraceIDUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskExecutorDispatcherImpl
implements AsyncTaskExecutorDispatcher,
ApplicationEventPublisherAware {
    public static final String I18N_FORCE_FINISH = "ASYNC_FORCE_FINISH";
    @Value(value="${jiuqi.np.asynctask.executor-pool-size:20}")
    private Integer MAX_POOLSIZE;
    private static String LOG_NAME = "NP_ASYNCTASK_EXECUTOR_DISPATCHER";
    private static String SIMPLE_THTREADPOOL_NAME = "SIMPLE_ASYNCTASKPOOL";
    private ThreadPoolExecutor SIMPLE_THTREADPOOL_EXECUTOR;
    private static String SPLIT_THTREADPOOL_NAME = "SPLIT_ASYNCTASKPOOL";
    private ThreadPoolExecutor SPLIT_THTREADPOOL_EXECUTOR;
    @Autowired
    private AsyncTaskDao dao;
    @Autowired
    private AsyncTaskBufferQueue bufferQueue;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AsyncTaskTypeCollecter collecter;
    private ApplicationEventPublisher eventPublisher;
    private final Logger logger = LoggerFactory.getLogger(LOG_NAME);

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @EventListener
    protected void startThreadPool(AsyncTaskReadyEvent event) {
        if (this.MAX_POOLSIZE <= 0) {
            return;
        }
        this.SIMPLE_THTREADPOOL_EXECUTOR = new ThreadPoolExecutor(this.MAX_POOLSIZE + 1, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NpAsyncTaskThreadFactory(SIMPLE_THTREADPOOL_NAME));
        this.SPLIT_THTREADPOOL_EXECUTOR = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new NpAsyncTaskThreadFactory(SPLIT_THTREADPOOL_NAME));
        String serveCode = event.getServeCode();
        this.logger.info("\u542f\u52a8\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u5668\u3002\u6267\u884c\u5668ID\uff1a" + serveCode + "\uff0c\u6700\u5927\u5e76\u53d1\u4efb\u52a1\u6570\uff1a" + this.MAX_POOLSIZE);
        this.runExecutorDispatcher(this.SIMPLE_THTREADPOOL_EXECUTOR, "np_asynctask_simple_queue", serveCode, this.MAX_POOLSIZE);
        this.runExecutorDispatcher(this.SPLIT_THTREADPOOL_EXECUTOR, "np_asynctask_split_queue", serveCode, Integer.MAX_VALUE);
    }

    public void runExecutorDispatcher(ThreadPoolExecutor threadPoolExecutor, String queueName, String serveCode, Integer threadLimit) {
        threadPoolExecutor.execute(() -> this.lambda$runExecutorDispatcher$1(threadPoolExecutor, threadLimit, queueName, serveCode));
    }

    private void error(String taskId, String message, Throwable t) {
        this.dao.updateErrorInfo(taskId, message, t, null);
    }

    private String getThreadPoolStateInfo() {
        StringBuilder info = new StringBuilder();
        info.append(" POOL_INFO_: ActiveCount:").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getActiveCount());
        info.append("CorePoolSize::").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getCorePoolSize());
        info.append("TaskCount:").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getTaskCount());
        info.append("CompletedTaskCount:").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getCompletedTaskCount());
        info.append("MaximumPoolSize:").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getMaximumPoolSize());
        info.append("QueueSize:").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getQueue().size());
        info.append("LargestPoolSize:").append(this.SIMPLE_THTREADPOOL_EXECUTOR.getLargestPoolSize());
        return info.toString();
    }

    private NpContext getNpContext(NpContextParam contextParam) {
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        context.setTenant(contextParam.getTenant());
        context.setIdentity(contextParam.getContextIdentity());
        context.setUser(contextParam.getContextUser());
        context.setLoginDate(contextParam.getLoginDate());
        Locale local = contextParam.getLocal();
        if (local == null) {
            local = LocaleContextHolder.getLocale();
        }
        String ip = contextParam.getIp();
        context.setIp(ip);
        context.setLocale(local);
        context.setOrganization(contextParam.getOrganization());
        DsContext dsContext = contextParam.getDsContext();
        DsContextHolder.setDsContext((DsContext)dsContext);
        LogTraceIDUtil.setLogTraceId((String)contextParam.getTraceId());
        Map extensionMap = contextParam.getExtensionMap();
        if (extensionMap != null && extensionMap.size() > 0) {
            for (Map.Entry extensionItem : extensionMap.entrySet()) {
                final ContextExtension contextExtension = context.getExtension((String)extensionItem.getKey());
                Consumer<Map.Entry<String, Object>> consumer = new Consumer<Map.Entry<String, Object>>(){

                    @Override
                    public void accept(Map.Entry<String, Object> item) {
                        Serializable seriaValue = (Serializable)item.getValue();
                        contextExtension.put(item.getKey(), seriaValue);
                    }
                };
                ((ContextExtension)extensionItem.getValue()).apply((Consumer)consumer);
            }
        }
        return context;
    }

    /*
     * Unable to fully structure code
     */
    private /* synthetic */ void lambda$runExecutorDispatcher$1(ThreadPoolExecutor threadPoolExecutor, Integer threadLimit, String queueName, String serveCode) {
        block8: while (true) {
            try {
                while (true) {
                    if (threadPoolExecutor.getActiveCount() < threadLimit) {
                        taskId = this.bufferQueue.receive(queueName, serveCode);
                        if (null != taskId) {
                            task = this.dao.query(taskId);
                            taskPoolType = task.getTaskPoolType();
                            context = (NpContextParam)task.getContext();
                            executor = this.collecter.getExecutorByType(taskPoolType);
                            if (null != executor) {
                                try {
                                    threadPoolExecutor.execute((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$null$0(com.jiuqi.np.asynctask.NpContextParam java.lang.String java.lang.String com.jiuqi.np.asynctask.NpAsyncTaskExecutor com.jiuqi.np.asynctask.AsyncTask ), ()V)((AsyncTaskExecutorDispatcherImpl)this, (NpContextParam)context, (String)taskId, (String)taskPoolType, (NpAsyncTaskExecutor)executor, (AsyncTask)task));
                                    continue block8;
                                }
                                catch (Throwable e) {
                                    info = this.getThreadPoolStateInfo();
                                    this.logger.error(taskPoolType + "_" + taskId + "\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u8c03\u5ea6\u9519\u8bef: " + e.getMessage() + info, e);
                                    message = "\u4efb\u52a1\u6c60\"" + taskPoolType + "\"\u6267\u884c\u65f6\u7ebf\u7a0b\u6c60\u6ee1; " + e.getMessage() + info;
                                    this.error(taskId, message, e);
                                    continue;
                                }
                            }
                            this.error(taskId, "\u4efb\u52a1\u6c60\"" + taskPoolType + "\"\u65e0\u6267\u884c\u5668", null);
                            continue;
                        }
                        try {
                            Thread.sleep(1000L);
                            continue block8;
                        }
                        catch (InterruptedException e) {
                            this.logger.error(taskId + "\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u8c03\u5ea6\u9519\u8bef: " + e.getMessage(), e);
                            continue;
                        }
                    }
                    try {
                        Thread.sleep(1000L);
                        continue block8;
                    }
                    catch (InterruptedException e) {
                        this.logger.error("\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u8c03\u5ea6\u9519\u8bef: " + e.getMessage(), e);
                        continue;
                    }
                    break;
                }
            }
            catch (Exception e) {
                if (this.dataSource instanceof HikariDataSource && (hikariDataSource = (HikariDataSource)this.dataSource).isClosed()) ** break;
                continue;
                this.logger.error("\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u8c03\u5ea6\u9519\u8bef: " + e.getMessage(), e);
                threadPoolExecutor.shutdownNow();
                return;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private /* synthetic */ void lambda$null$0(NpContextParam context, String taskId, String taskPoolType, NpAsyncTaskExecutor executor, AsyncTask task) {
        try {
            NpContextHolder.setContext((NpContext)this.getNpContext(context));
            SimpleAsyncTaskMonitor monitor = new SimpleAsyncTaskMonitor(this.dao, this.eventPublisher, taskId, taskPoolType);
            this.logger.trace("\u3010\u5f02\u6b65\u4efb\u52a1\u6267\u884c\u3011: " + taskPoolType + "_" + taskId);
            executor.execute(task.getArgs(), (AsyncTaskMonitor)monitor);
            if (!monitor.isFinish()) {
                monitor.finish(I18N_FORCE_FINISH, null);
            }
        }
        catch (Throwable e) {
            this.logger.error(taskPoolType + "_" + taskId + "\u5f02\u6b65\u4efb\u52a1\u4efb\u52a1\u8c03\u5ea6\u9519\u8bef: " + e.getMessage(), e);
            this.error(taskId, e.getMessage(), e);
        }
        finally {
            NpContextHolder.clearContext();
            DsContextHolder.clearContext();
        }
    }

    static class NpAsyncTaskThreadFactory
    implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        NpAsyncTaskThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            this.group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != 5) {
                t.setPriority(5);
            }
            return t;
        }
    }
}

