/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.config;

import com.jiuqi.nr.bpm.config.ThreadPoolProperties;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableConfigurationProperties(value={ThreadPoolProperties.class})
public class BpmThreadPoolConfiguration {
    public static final String BEANNAME_THREADPOOL = "thread_pool_task_excutor_bpm";
    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    @Bean(name={"thread_pool_task_excutor_bpm"})
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.threadPoolProperties.getCorePoolSize());
        taskExecutor.setKeepAliveSeconds(this.threadPoolProperties.getKeepAliveSeconds());
        taskExecutor.setMaxPoolSize(this.threadPoolProperties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(this.threadPoolProperties.getQueueCapacity());
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }
}

