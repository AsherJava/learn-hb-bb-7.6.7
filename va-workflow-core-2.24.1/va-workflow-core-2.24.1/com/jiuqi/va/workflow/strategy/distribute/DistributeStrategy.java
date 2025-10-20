/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;

public interface DistributeStrategy {
    public int getDistributeType();

    public void executeDistribute(WorkflowBusinessDistributeDTO var1);
}

