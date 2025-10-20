/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.core.QuartzJobThreadFactory
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.analysisreport.utils;

import com.jiuqi.bi.core.jobs.core.QuartzJobThreadFactory;
import com.jiuqi.nr.analysisreport.async.executor.ContextCopyingDecorator;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TaskExecutorUtils {
    public static final int keepAliveSeconds = 14400;

    public static ThreadPoolTaskExecutor initThreadPoolTaskExecutor(int maxThreadCount, String resourceName) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setKeepAliveSeconds(14400);
        threadPoolTaskExecutor.setMaxPoolSize(maxThreadCount);
        threadPoolTaskExecutor.setCorePoolSize(maxThreadCount);
        threadPoolTaskExecutor.setThreadFactory((ThreadFactory)new QuartzJobThreadFactory(resourceName));
        threadPoolTaskExecutor.setTaskDecorator(new ContextCopyingDecorator());
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        threadPoolTaskExecutor.getThreadPoolExecutor().allowCoreThreadTimeOut(true);
        return threadPoolTaskExecutor;
    }

    public static ThreadPoolTaskExecutor buildThreadPool(String declareKey, String optionKey) {
        INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
        int maxThreadNum = Integer.valueOf(systemOptionService.get(declareKey, optionKey));
        return TaskExecutorUtils.initThreadPoolTaskExecutor(maxThreadNum, "batchExportThreadNum");
    }
}

