/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.listener;

import java.util.List;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;

public interface QueryExecutionListener {
    default public void beforeQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
    }

    default public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
    }
}

