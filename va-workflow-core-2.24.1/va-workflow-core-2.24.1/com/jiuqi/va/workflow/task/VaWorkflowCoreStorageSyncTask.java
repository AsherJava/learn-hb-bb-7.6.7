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
import com.jiuqi.va.workflow.storage.WorkflowCommentInfoStorage;
import com.jiuqi.va.workflow.storage.WorkflowCommonUserStorage;
import com.jiuqi.va.workflow.storage.WorkflowMetaRelationStorage;
import com.jiuqi.va.workflow.storage.WorkflowPlusApprovalInfoStorage;
import com.jiuqi.va.workflow.storage.WorkflowPlusApprovalUserStorage;
import com.jiuqi.va.workflow.storage.WorkflowProcessInfoStorage;
import com.jiuqi.va.workflow.storage.WorkflowProcessNodeStorage;
import com.jiuqi.va.workflow.storage.WorkflowProcessRejectNodeStorage;
import com.jiuqi.va.workflow.storage.WorkflowProcessReviewStorage;
import org.springframework.stereotype.Component;

@Component
public class VaWorkflowCoreStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        WorkflowCommentInfoStorage.init(tenantName);
        WorkflowMetaRelationStorage.init(tenantName);
        WorkflowProcessInfoStorage.init(tenantName);
        WorkflowProcessNodeStorage.init(tenantName);
        WorkflowProcessRejectNodeStorage.init(tenantName);
        WorkflowPlusApprovalInfoStorage.init(tenantName);
        WorkflowPlusApprovalUserStorage.init(tenantName);
        WorkflowProcessReviewStorage.init(tenantName);
        WorkflowCommonUserStorage.init(tenantName);
    }

    public String getVersion() {
        return "20250617-1000";
    }
}

