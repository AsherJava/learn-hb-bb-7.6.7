/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor;

import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CommonDataSyncExecutorGather {
    @Autowired(required=false)
    private List<CommonDataSyncExecutor> dataSyncExecutors = Collections.emptyList();

    public List<CommonDataSyncExecutor> getDataSyncExecutors() {
        if (CollectionUtils.isEmpty(this.dataSyncExecutors)) {
            return Collections.emptyList();
        }
        List orderDataSyncExecutors = this.dataSyncExecutors.stream().sorted(Comparator.comparingInt(CommonDataSyncExecutor::order)).collect(Collectors.toList());
        return Collections.unmodifiableList(orderDataSyncExecutors);
    }
}

