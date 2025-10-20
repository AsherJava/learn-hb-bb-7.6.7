/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.pool;

import com.jiuqi.gcreport.efdcdatacheck.impl.pool.EFDCDataCheckThreadPoolProperties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class EFDCDataCheckThreadPoolConfiguration {
    private Logger LOGGER = LoggerFactory.getLogger(EFDCDataCheckThreadPoolConfiguration.class);
    @Autowired
    private EFDCDataCheckThreadPoolProperties properties;

    @Bean(value={"efdcDataCheckTaskExecutor"})
    public Executor efdcDataCheckTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.properties.getCorePoolSize());
        executor.setMaxPoolSize(this.properties.getMaxPoolSize());
        executor.setQueueCapacity(this.properties.getQueueCapacity());
        executor.setKeepAliveSeconds(this.properties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("EFDCDataCheck-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        this.LOGGER.info("EFDCDataCheck \u7ebf\u7a0b\u6c60\u521d\u59cb\u5316\u5b8c\u6210\u3002[CorePoolSize\uff1a{}\uff0cMaxPoolSize\uff1a{}\uff0cQueueCapacity\uff1a{}]", this.properties.getCorePoolSize(), this.properties.getMaxPoolSize(), this.properties.getQueueCapacity());
        return executor;
    }
}

