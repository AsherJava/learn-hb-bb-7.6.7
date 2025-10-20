/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.carryover.task.factory;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarryOverTaskExecutorFactory {
    @Autowired
    private List<GcCarryOverTaskExecutor> taskExecutors;

    public GcCarryOverTaskExecutor createTask(String taskCode) {
        if (StringUtils.isEmpty((String)taskCode)) {
            throw new BusinessRuntimeException("\u4efb\u52a1code\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Map<String, GcCarryOverTaskExecutor> taskExecutorMap = this.getTaskExecutorMap();
        if (!taskExecutorMap.containsKey(taskCode)) {
            throw new BusinessRuntimeException("\u672a\u521b\u5efa\u4efb\u52a1\u6267\u884c\u5668\uff1a" + taskCode);
        }
        return taskExecutorMap.get(taskCode);
    }

    private Map<String, GcCarryOverTaskExecutor> getTaskExecutorMap() {
        HashMap<String, GcCarryOverTaskExecutor> taskExecutorMap = new HashMap<String, GcCarryOverTaskExecutor>();
        for (GcCarryOverTaskExecutor taskExecutor : this.taskExecutors) {
            taskExecutorMap.put(taskExecutor.getName(), taskExecutor);
        }
        return taskExecutorMap;
    }
}

