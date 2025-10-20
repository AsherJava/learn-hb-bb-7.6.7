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
import com.jiuqi.va.workflow.storage.WorkflowBusinessSchemeStorage;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowBusinessSchemeStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        WorkflowBusinessSchemeStorage.init(tenantName);
    }

    public String getVersion() {
        return "20240820-1631";
    }
}

