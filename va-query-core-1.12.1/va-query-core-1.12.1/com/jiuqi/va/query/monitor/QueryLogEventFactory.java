/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.query.monitor.QueryLogEvent;
import com.lmax.disruptor.EventFactory;

public class QueryLogEventFactory
implements EventFactory<QueryLogEvent> {
    public QueryLogEvent newInstance() {
        return new QueryLogEvent();
    }
}

