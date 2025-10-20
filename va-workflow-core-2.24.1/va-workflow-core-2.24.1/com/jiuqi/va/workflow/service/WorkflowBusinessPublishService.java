/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishItem
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishItem;

public interface WorkflowBusinessPublishService {
    public WorkflowBusinessDO doPublish(WorkflowBusinessPublishItem var1);

    public WorkflowBusinessDO doUpdate(WorkflowBusinessDO var1, Long var2);
}

