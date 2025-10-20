/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener.lifecycle;

import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;
import net.ttddyy.dsproxy.listener.lifecycle.JdbcLifecycleEventListener;

public class JdbcLifecycleEventListenerAdapter
implements JdbcLifecycleEventListener {
    @Override
    public void beforeMethod(MethodExecutionContext executionContext) {
    }

    @Override
    public void afterMethod(MethodExecutionContext executionContext) {
    }

    @Override
    public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
    }

    @Override
    public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
    }
}

