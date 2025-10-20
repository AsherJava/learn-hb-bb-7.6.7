/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.offset.thread;

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
@PropertySource(value={"classpath:fincubes-offset-threadpool.properties"})
public class FinancialCubesOffsetMonitorAsyncConfig {
    @Value(value="${core-pool-size}")
    private int corePoolSize;
    @Value(value="${max-pool-size}")
    private int maxPoolSize;
    @Value(value="${keep-alive-seconds}")
    private int keepAliveSeconds;

    @Bean(value={"fincubes-offset-executor"})
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.corePoolSize);
        taskExecutor.setMaxPoolSize(this.maxPoolSize);
        taskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
        taskExecutor.setThreadNamePrefix("financial-cubes-offset-monitor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.initialize();
        return taskExecutor;
    }
}

