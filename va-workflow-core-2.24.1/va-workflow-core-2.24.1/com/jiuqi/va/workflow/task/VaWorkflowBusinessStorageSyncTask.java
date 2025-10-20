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
import com.jiuqi.va.workflow.storage.business.WorkflowBusinessInfoStorage;
import com.jiuqi.va.workflow.storage.business.WorkflowBusinessRelDesignStorage;
import com.jiuqi.va.workflow.storage.business.WorkflowBusinessRelDraftStorage;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowBusinessStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        WorkflowBusinessInfoStorage.init(tenantName);
        WorkflowBusinessRelDesignStorage.init(tenantName);
        WorkflowBusinessRelDraftStorage.init(tenantName);
    }

    public String getVersion() {
        return "20240823-0931";
    }
}

