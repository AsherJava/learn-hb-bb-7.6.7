/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.pool;

import com.jiuqi.gcreport.inputdata.function.sumhb.pool.SumHbThreadPoolProperties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class SumHbThreadPoolConfiguration {
    private Logger LOGGER = LoggerFactory.getLogger(SumHbThreadPoolConfiguration.class);
    @Autowired
    private SumHbThreadPoolProperties properties;

    @Bean(value={"sumHbBatchTaskExecutor"})
    public Executor dataExpImpTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.properties.getCorePoolSize());
        executor.setMaxPoolSize(this.properties.getMaxPoolSize());
        executor.setQueueCapacity(this.properties.getQueueCapacity());
        executor.setKeepAliveSeconds(this.properties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("SumHbBatchTaskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}

