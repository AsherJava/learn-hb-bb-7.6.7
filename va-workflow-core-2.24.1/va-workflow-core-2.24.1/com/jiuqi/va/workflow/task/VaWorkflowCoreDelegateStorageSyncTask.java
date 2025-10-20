/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.workflow.task;

import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.workflow.storage.WorkflowDelegateInfoStorage;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowCoreDelegateStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        WorkflowDelegateInfoStorage.init(tenantName);
    }

    public String getVersion() {
        return "20231230-1930";
    }
}

