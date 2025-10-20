/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 *  com.lmax.disruptor.ExceptionHandler
 *  com.lmax.disruptor.RingBuffer
 *  com.lmax.disruptor.Sequence
 *  com.lmax.disruptor.SequenceBarrier
 *  com.lmax.disruptor.WaitStrategy
 *  com.lmax.disruptor.WorkHandler
 *  com.lmax.disruptor.WorkerPool
 *  com.lmax.disruptor.dsl.ProducerType
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.config;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.config.MonitorConsumerProperties;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEvent;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEventExceptionHandler;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEventFactory;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEventProducer;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEventWorkHandler;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

@Configuration
@EnableAsync
@ComponentScan(value={"com.jiuqi.nvwa.sf.adapter.spring.monitor"})
@EnableConfigurationProperties(value={MonitorConsumerProperties.class})
public class MonitorAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorAutoConfiguration.class);
    @Autowired
    private MonitorConsumerProperties monitorConsumerProperties;

    @Bean
    public RingBuffer<MonitorEvent> monitorEventRingBuffer() throws Exception {
        Integer maximumPoolSizeFrom;
        MonitorEventFactory eventFactory = new MonitorEventFactory();
        String waitStrategyClassName = this.monitorConsumerProperties.getWaitStrategyClassName();
        WaitStrategy waitStrategy = null;
        if (!StringUtils.hasLength(waitStrategyClassName)) {
            waitStrategyClassName = "com.lmax.disruptor.BlockingWaitStrategy";
        }
        try {
            Class<?> waitStrategyClass = Class.forName(waitStrategyClassName);
            waitStrategy = (WaitStrategy)waitStrategyClass.newInstance();
        }
        catch (Exception e) {
            LOGGER.error("Api\u548cSQL\u76d1\u63a7\uff1a\u6d88\u8d39\u8005\u7b49\u5f85\u7b56\u7565\u521d\u59cb\u5316\u5931\u8d25\uff01", e);
            throw e;
        }
        Integer ringBufferSize = this.monitorConsumerProperties.getRingBufferSize();
        if (null == ringBufferSize || ringBufferSize <= 0) {
            ringBufferSize = 16384;
        }
        RingBuffer ringBuffer = RingBuffer.create((ProducerType)ProducerType.MULTI, (EventFactory)eventFactory, (int)ringBufferSize, (WaitStrategy)waitStrategy);
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier(new Sequence[0]);
        Integer corePoolSizeFrom = this.monitorConsumerProperties.getCorePoolSize();
        if (null == corePoolSizeFrom || corePoolSizeFrom <= 0) {
            corePoolSizeFrom = 2;
        }
        if (null == (maximumPoolSizeFrom = this.monitorConsumerProperties.getMaximumPoolSize()) || maximumPoolSizeFrom <= 0) {
            maximumPoolSizeFrom = 5;
        }
        int corePoolSize = corePoolSizeFrom;
        int maximumPoolSize = maximumPoolSizeFrom;
        int consumersCount = corePoolSize - 1;
        WorkHandler[] consumers = new MonitorEventWorkHandler[consumersCount];
        for (int i = 0; i < consumers.length; ++i) {
            consumers[i] = new MonitorEventWorkHandler();
        }
        WorkerPool workerPool = new WorkerPool(ringBuffer, sequenceBarrier, (ExceptionHandler)new MonitorEventExceptionHandler(), consumers);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
        workerPool.start((Executor)executor);
        return ringBuffer;
    }

    @Bean
    public MonitorEventProducer monitorEventProducer(@Autowired RingBuffer<MonitorEvent> ringBuffer) {
        return new MonitorEventProducer(ringBuffer);
    }
}

