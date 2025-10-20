/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import java.util.List;

public interface WorkflowPublicParamService {
    public void addPublicParam(WorkflowPublicParamDTO var1);

    public void add(WorkflowPublicParamDTO var1, List<WorkflowPublicParamDO> var2);

    public void updatePublicParam(WorkflowPublicParamDTO var1);

    public void update(WorkflowPublicParamDTO var1);

    public void removePublicParam(WorkflowPublicParamDTO var1);

    public void updown(WorkflowPublicParamDTO var1);

    public void exchangeParamDO(WorkflowPublicParamDTO var1, WorkflowPublicParamDTO var2);

    public List<WorkflowPublicParamDTO> list(WorkflowPublicParamDTO var1);

    public boolean check(WorkflowPublicParamDTO var1);
}

