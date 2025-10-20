/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 *  com.lmax.disruptor.EventHandler
 *  com.lmax.disruptor.RingBuffer
 *  com.lmax.disruptor.SleepingWaitStrategy
 *  com.lmax.disruptor.WaitStrategy
 *  com.lmax.disruptor.dsl.Disruptor
 *  com.lmax.disruptor.dsl.ProducerType
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.query.monitor.LogEventHandler;
import com.jiuqi.va.query.monitor.QueryLogEvent;
import com.jiuqi.va.query.monitor.QueryLogEventFactory;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorConfig {
    @Bean
    public QueryLogEventFactory logEventFactory() {
        return new QueryLogEventFactory();
    }

    @Bean
    public LogEventHandler logEventHandler() {
        return new LogEventHandler();
    }

    @Bean
    public RingBuffer<QueryLogEvent> vaQueryRingBuffer(QueryLogEventFactory logEventFactory, LogEventHandler logEventHandler) {
        Disruptor disruptor = new Disruptor((EventFactory)logEventFactory, 1024, (ThreadFactory)new DaemonThreadFactory("vaquery-monitor-thread"), ProducerType.SINGLE, (WaitStrategy)new SleepingWaitStrategy());
        disruptor.handleEventsWith(new EventHandler[]{logEventHandler});
        CompletableFuture.runAsync(() -> ((Disruptor)disruptor).start());
        return disruptor.getRingBuffer();
    }

    private static class DaemonThreadFactory
    implements ThreadFactory {
        private final AtomicInteger threadCount = new AtomicInteger(1);
        private final String namePrefix;

        DaemonThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, this.namePrefix + "-" + this.threadCount.getAndIncrement());
        }
    }
}

