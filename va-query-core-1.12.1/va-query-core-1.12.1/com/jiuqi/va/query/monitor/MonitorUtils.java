/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.RingBuffer
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.query.monitor.MonitorConfig;
import com.jiuqi.va.query.monitor.QueryLogEvent;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import com.lmax.disruptor.RingBuffer;
import java.util.Date;

public class MonitorUtils {
    private static RingBuffer<QueryLogEvent> ringBuffer;
    private static MonitorConfig monitorConfig;

    private MonitorUtils() {
    }

    public static void execute(QueryLogEvent logEvent, Runnable runnable) {
        long start = System.currentTimeMillis();
        Exception exception = null;
        logEvent.setEventTime(new Date());
        try {
            runnable.run();
        }
        catch (Exception t) {
            exception = t;
            throw t;
        }
        finally {
            long duration = System.currentTimeMillis() - start;
            MonitorUtils.publishLogEvent(logEvent, duration, exception);
        }
    }

    public static void execute(String code, String bizName, Object[] args, Runnable runnable) {
        long start = System.currentTimeMillis();
        Exception exception = null;
        QueryLogEvent logEvent = new QueryLogEvent();
        logEvent.setEventTime(new Date());
        logEvent.setBizName(bizName);
        logEvent.setCode(code);
        logEvent.setArgs(args);
        try {
            runnable.run();
        }
        catch (Exception t) {
            exception = t;
            throw t;
        }
        finally {
            long duration = System.currentTimeMillis() - start;
            MonitorUtils.publishLogEvent(logEvent, duration, exception);
        }
    }

    public static QueryLogEvent eventStart(String code, String bizName, Object[] args) {
        long start = System.currentTimeMillis();
        QueryLogEvent logEvent = new QueryLogEvent();
        logEvent.setEventTime(new Date());
        logEvent.setBizName(bizName);
        logEvent.setCode(code);
        logEvent.setArgs(args);
        logEvent.setStartTime(start);
        return logEvent;
    }

    public static void eventEnd(QueryLogEvent logEvent) {
        MonitorUtils.publishLogEvent(logEvent, System.currentTimeMillis() - logEvent.getStartTime(), null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void publishLogEvent(QueryLogEvent logEvent, long duration, Throwable ex) {
        if (monitorConfig == null) {
            monitorConfig = DCQuerySpringContextUtils.getBean(MonitorConfig.class);
        }
        if (monitorConfig == null || !monitorConfig.isEnabled() || duration < (long)monitorConfig.getThreshold()) {
            return;
        }
        if (ex != null) {
            return;
        }
        if (ringBuffer == null) {
            ringBuffer = (RingBuffer)DCQuerySpringContextUtils.getBean("vaQueryRingBuffer");
        }
        long sequence = ringBuffer.next();
        try {
            QueryLogEvent event = (QueryLogEvent)ringBuffer.get(sequence);
            event.setCode(logEvent.getCode());
            event.setBizName(logEvent.getBizName());
            event.setArgs(logEvent.getArgs());
            event.setDuration(duration);
            event.setEventTime(logEvent.getEventTime());
        }
        finally {
            ringBuffer.publish(sequence);
        }
    }
}

