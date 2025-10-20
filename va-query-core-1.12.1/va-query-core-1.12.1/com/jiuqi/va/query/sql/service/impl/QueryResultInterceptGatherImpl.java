/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.interceptor.QueryResultInterceptor
 */
package com.jiuqi.va.query.sql.service.impl;

import com.jiuqi.va.query.sql.interceptor.QueryResultInterceptor;
import com.jiuqi.va.query.sql.service.QueryResultInterceptGather;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryResultInterceptGatherImpl
implements QueryResultInterceptGather,
InitializingBean {
    private final Map<String, QueryResultInterceptor> processorNameMap = new ConcurrentHashMap<String, QueryResultInterceptor>();
    @Autowired(required=false)
    private List<QueryResultInterceptor> queryResultInterceptors;

    @Override
    public QueryResultInterceptor getQueryResultInterceptor(String processorName) {
        return this.processorNameMap.get(processorName);
    }

    @Override
    public void afterPropertiesSet() {
        this.registerQueryDataProcessorName();
    }

    private synchronized void registerQueryDataProcessorName() {
        if (this.queryResultInterceptors != null) {
            this.processorNameMap.clear();
            for (QueryResultInterceptor queryResultInterceptor : this.queryResultInterceptors) {
                this.processorNameMap.put(queryResultInterceptor.getProcessorName(), queryResultInterceptor);
            }
        }
    }
}

