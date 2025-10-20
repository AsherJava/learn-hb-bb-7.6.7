/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import java.util.ArrayList;
import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;

public class ChainListener
implements QueryExecutionListener {
    private List<QueryExecutionListener> listeners = new ArrayList<QueryExecutionListener>();

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        for (QueryExecutionListener listener : this.listeners) {
            listener.beforeQuery(execInfo, queryInfoList);
        }
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
        for (QueryExecutionListener listener : this.listeners) {
            listener.afterQuery(execInfo, queryInfoList);
        }
    }

    public void addListener(QueryExecutionListener listener) {
        this.listeners.add(listener);
    }

    public List<QueryExecutionListener> getListeners() {
        return this.listeners;
    }

    public void setListeners(List<QueryExecutionListener> listeners) {
        this.listeners = listeners;
    }
}

