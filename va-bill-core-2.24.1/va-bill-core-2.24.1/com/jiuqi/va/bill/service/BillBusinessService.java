/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import java.util.List;
import java.util.Map;

public interface BillBusinessService {
    public Map<String, WorkflowBusinessDistributeDTO> handleDistributeParams(WorkflowBusinessDistributeDTO var1);

    public List<ProcessNodeDO> businessProcessNodeProcessed(List<ProcessNodeDO> var1);
}

