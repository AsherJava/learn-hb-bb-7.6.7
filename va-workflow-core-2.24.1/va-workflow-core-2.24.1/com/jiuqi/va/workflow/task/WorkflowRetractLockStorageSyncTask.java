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
import com.jiuqi.va.workflow.storage.WorkflowRetractLockStorage;
import org.springframework.stereotype.Service;

@Service
public class WorkflowRetractLockStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        WorkflowRetractLockStorage.init(tenantName);
    }

    public String getVersion() {
        return "20250116-0934";
    }
}

