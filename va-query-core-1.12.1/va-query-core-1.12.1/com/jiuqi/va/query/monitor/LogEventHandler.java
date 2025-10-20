/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventHandler
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.query.monitor.QueryLogEvent;
import com.jiuqi.va.query.monitor.QueryMonitorExecutor;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogEventHandler
implements EventHandler<QueryLogEvent> {
    @Autowired
    private QueryMonitorExecutor executor;
    private static final Logger logger = LoggerFactory.getLogger(LogEventHandler.class);

    public void onEvent(QueryLogEvent event, long sequence, boolean endOfBatch) {
        this.processCalculate(event);
    }

    private void processCalculate(QueryLogEvent event) {
        try {
            this.executor.processCalculate(event);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

