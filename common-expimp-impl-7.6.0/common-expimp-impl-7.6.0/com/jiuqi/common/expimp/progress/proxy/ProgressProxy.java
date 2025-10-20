/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.lmax.disruptor.BlockingWaitStrategy
 *  com.lmax.disruptor.EventFactory
 *  com.lmax.disruptor.EventHandler
 *  com.lmax.disruptor.RingBuffer
 *  com.lmax.disruptor.WaitStrategy
 *  com.lmax.disruptor.dsl.Disruptor
 *  com.lmax.disruptor.dsl.ProducerType
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.common.expimp.progress.proxy;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.executor.ProgressExecutor;
import com.jiuqi.common.expimp.progress.properties.ProgressProperties;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressProxy<T extends ProgressData<E>, E> {
    private static Logger LOGGER = LoggerFactory.getLogger(ProgressProxy.class);
    private static RingBuffer<ProgressDataRefreshEvent> ringBuffer;
    @Autowired
    private ProgressProperties progressProperties;
    @Autowired
    private List<ProgressExecutor<T, E>> progressExecutors;

    @PostConstruct
    public void startDisruptor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ProgressDataRefreshEventFactory factory = new ProgressDataRefreshEventFactory();
        int bufferSize = 262144;
        Disruptor disruptor = new Disruptor((EventFactory)factory, bufferSize, (Executor)executor, ProducerType.SINGLE, (WaitStrategy)new BlockingWaitStrategy());
        disruptor.handleEventsWith(new EventHandler[]{new ProgressDataRefreshEventHandler()});
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    public void createProgressData(T progressData) {
        this.getProgressExecutor().createProgressData(progressData);
    }

    public void refreshProgressData(T progressData) {
        long sequence = ringBuffer.next();
        try {
            ProgressDataRefreshEvent progressDataRefreshEvent = (ProgressDataRefreshEvent)ringBuffer.get(sequence);
            progressDataRefreshEvent.setProgressData(progressData);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        finally {
            ringBuffer.publish(sequence);
        }
    }

    public ProgressData<E> queryProgressData(String sn) {
        return this.queryProgressData(sn, true);
    }

    public ProgressData<E> queryProgressData(String sn, boolean isAutoClearProgressDataWhenFinished) {
        return this.getProgressExecutor().queryProgressData(sn, isAutoClearProgressDataWhenFinished);
    }

    public void removeProgressData(String sn) {
        this.getProgressExecutor().removeProgressData(sn);
    }

    public ProgressExecutor<T, E> getProgressExecutor() {
        if (CollectionUtils.isEmpty(this.progressExecutors)) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u8fdb\u5ea6\u4efb\u52a1\u6267\u884c\u5668\u3002");
        }
        String executorName = this.progressProperties.getExecutorName();
        List matchExecutors = this.progressExecutors.stream().filter(progressExecutor -> progressExecutor.getExecutorName().equalsIgnoreCase(executorName)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(matchExecutors)) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u540d\u79f0\u4e3a\u201c" + executorName + "\u201d\u7684\u8fdb\u5ea6\u4efb\u52a1\u6267\u884c\u5668\uff0c\u8bf7\u68c0\u67e5\u914d\u7f6e:" + "custom.progress.executor.name");
        }
        if (matchExecutors.size() > 1) {
            String msg = matchExecutors.stream().map(matchExecutor -> matchExecutor.getClass().getName()).reduce("", (s1, s2) -> s1 + "\n" + s2);
            throw new BusinessRuntimeException("\u540d\u79f0\u4e3a\u201c" + executorName + "\u201d\u7684\u8fdb\u5ea6\u4efb\u52a1\u6267\u884c\u5668\u6ce8\u518c\u91cd\u590d\uff0c\u8bf7\u68c0\u67e5\u6ce8\u518c\u7c7b:" + msg);
        }
        return (ProgressExecutor)matchExecutors.get(0);
    }

    public static class ProgressDataRefreshEvent<T extends ProgressData<E>, E> {
        private T progressData;

        public T getProgressData() {
            return this.progressData;
        }

        public void setProgressData(T progressData) {
            this.progressData = progressData;
        }
    }

    public static class ProgressDataRefreshEventFactory
    implements EventFactory<ProgressDataRefreshEvent> {
        public ProgressDataRefreshEvent newInstance() {
            return new ProgressDataRefreshEvent();
        }
    }

    public static class ProgressDataRefreshEventHandler
    implements EventHandler<ProgressDataRefreshEvent> {
        public void onEvent(ProgressDataRefreshEvent progressDataRefreshEvent, long sequence, boolean endOfBatch) throws Exception {
            try {
                ProgressProxy progressProxy = (ProgressProxy)SpringContextUtils.getBean(ProgressProxy.class);
                progressProxy.getProgressExecutor().refreshProgressData(progressDataRefreshEvent.getProgressData());
            }
            catch (Throwable e) {
                LOGGER.error("\u8fdb\u5ea6\u6761\u6d88\u8d39\u8005\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }
}

