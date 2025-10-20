/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDO
 *  com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamDTO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDO;
import com.jiuqi.va.domain.workflow.publicparam.WorkflowPublicParamRelationDTO;
import java.util.List;

public interface WorkflowPublicParamRelationService {
    public String handleWorkflowPublicParam(String var1, MetaInfoDTO var2);

    public void handleAddPublicParamRelation(MetaInfoDTO var1, List<WorkflowPublicParamDTO> var2);

    public void handlePublishedPublicParamRel(MetaInfoDTO var1, List<WorkflowPublicParamDTO> var2);

    public void updatePublicParamRel(WorkflowPublicParamRelationDTO var1);

    public void deletePublicParamRel(WorkflowPublicParamRelationDTO var1);

    public void delete(WorkflowPublicParamRelationDTO var1);

    public void workflowPublishUpdate(List<String> var1, MetaInfoEditionDO var2);

    public void updateRel(String var1, List<WorkflowPublicParamRelationDO> var2, Long var3);
}

