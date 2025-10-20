/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.ExceptionHandler
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.queue;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEvent;
import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorEventExceptionHandler
implements ExceptionHandler<MonitorEvent> {
    public static final Logger LOGGER = LoggerFactory.getLogger(MonitorEventExceptionHandler.class);

    public void handleEventException(Throwable ex, long sequence, MonitorEvent event) {
        LOGGER.error("MonitorEvent handleEventException", ex);
    }

    public void handleOnStartException(Throwable ex) {
        LOGGER.error("MonitorEvent handleOnStartException", ex);
    }

    public void handleOnShutdownException(Throwable ex) {
        LOGGER.error("MonitorEvent handleOnShutdownException", ex);
    }
}

