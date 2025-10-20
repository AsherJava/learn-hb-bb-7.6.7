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
import com.jiuqi.va.workflow.storage.WorkflowPublicParamStorage;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowPublicParamStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        WorkflowPublicParamStorage.init(tenantName);
    }

    public String getVersion() {
        return "20240125-1630";
    }
}

