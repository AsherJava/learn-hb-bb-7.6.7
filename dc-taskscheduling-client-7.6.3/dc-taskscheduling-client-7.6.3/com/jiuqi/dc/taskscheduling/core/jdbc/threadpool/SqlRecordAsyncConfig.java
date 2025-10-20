/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.jdbc.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
@PropertySource(value={"classpath:sql-threadpool.properties"})
public class SqlRecordAsyncConfig {
    @Value(value="${core-pool-size}")
    private int corePoolSize;
    @Value(value="${max-pool-size}")
    private int maxPoolSize;
    @Value(value="${queue-capacity}")
    private int queueCapacity;
    @Value(value="${keep-alive-seconds}")
    private int keepAliveSeconds;

    @Bean(value={"sql-record-executor"})
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.corePoolSize);
        taskExecutor.setMaxPoolSize(this.maxPoolSize);
        taskExecutor.setQueueCapacity(this.queueCapacity);
        taskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
        taskExecutor.setThreadNamePrefix("sql-record-executor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}

