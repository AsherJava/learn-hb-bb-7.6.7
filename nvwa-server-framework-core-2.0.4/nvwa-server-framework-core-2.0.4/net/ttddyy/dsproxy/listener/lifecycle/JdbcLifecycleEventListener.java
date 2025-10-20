/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener.lifecycle;

import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.MethodExecutionContext;

public interface JdbcLifecycleEventListener {
    public void beforeMethod(MethodExecutionContext var1);

    public void afterMethod(MethodExecutionContext var1);

    public void beforeQuery(ExecutionInfo var1, List<QueryInfo> var2);

    public void afterQuery(ExecutionInfo var1, List<QueryInfo> var2);
}

