/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishCheckResult
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishVO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.service;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishCheckResult;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessPublishVO;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.domain.BillWorkflowNodeVO;
import com.jiuqi.va.workflow.domain.BusinessWorkflowTreeVO;
import com.jiuqi.va.workflow.domain.workflowbusiness.WorkflowBusinessRelation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WorkflowBusinessService {
    public List<WorkflowBusinessDTO> list(WorkflowBusinessDTO var1);

    public R save(WorkflowBusinessDTO var1);

    public R batchSave(WorkflowBusinessDTO var1);

    public R saveBusiness(WorkflowBusinessDTO var1);

    public WorkflowBusinessDTO get(WorkflowBusinessDTO var1, boolean var2);

    public WorkflowBusinessDTO getBusiness(WorkflowBusinessDTO var1, boolean var2);

    public PageVO<WorkflowBusinessRelation> getBindingRelationByOrg(WorkflowBusinessDTO var1);

    public void delete(WorkflowBusinessDTO var1);

    public WorkflowBusinessDTO getVersions(WorkflowBusinessDTO var1, boolean var2);

    public WorkflowBusinessPublishVO update(WorkflowBusinessDTO var1);

    public List<WorkflowBusinessDTO> businesslist(WorkflowBusinessDTO var1);

    public List<BusinessWorkflowTreeVO> getBusinessWorkflowTree(TenantDO var1);

    public PageVO<BillWorkflowNodeVO> listBillWorkflowNode(TenantDO var1);

    public R distribute(WorkflowBusinessDistributeDTO var1);

    public R getDistributeResult(WorkflowBusinessDistributeDTO var1);

    public Set<String> extractBizFields(List<Map<String, Object>> var1);

    public Set<String> judgeSatisfyAdaptCondition(List<Map<String, Object>> var1, Map<String, Object> var2);

    public WorkflowBusinessPublishVO getWorkflowBusinessPublishData(WorkflowBusinessDTO var1);

    public WorkflowBusinessDTO getWorkflowBusinessUpdateData(WorkflowBusinessDTO var1);

    public WorkflowBusinessPublishCheckResult publishCheck(WorkflowBusinessDTO var1);

    public WorkflowBusinessPublishVO publish(WorkflowBusinessDTO var1);

    public void updateLockFlag(WorkflowBusinessDTO var1);

    public void reset(WorkflowBusinessDTO var1);

    public void publishWithoutDesign(WorkflowBusinessDTO var1);

    public List<Map<String, Object>> batchGetBusinessWorkflowBindingInfo(WorkflowBusinessDTO var1);
}

