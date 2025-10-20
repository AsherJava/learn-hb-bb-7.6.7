/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.domain.meta.MetaInfoDim
 *  com.jiuqi.va.domain.workflow.NodeView
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.va.workflow.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.domain.meta.MetaInfoDim;
import com.jiuqi.va.domain.workflow.NodeView;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.domain.WorkflowOptionDTO;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public interface WorkflowHelperService {
    public List<Map<String, Object>> listNodesByVersionUniqueCode(String var1, Long var2);

    public Map<String, Map<String, String>> listNextApproveInfo(TenantDO var1);

    public void dealAutoTaskParamBeforeExecute(WorkflowDTO var1, String var2);

    public String getWorkFlowLanguageFlag(Map<String, Object> var1, NodeView var2, ProcessDO var3);

    public ProcessDO getProcessDoByBizCode(String var1);

    public void getWorkflowNodes(ArrayNode var1, ArrayNode var2, List<Map<String, Object>> var3);

    public WorkflowOptionDTO getWorkFlowOptionDto();

    public void workflowBatchExport(List<MetaInfoDim> var1, SXSSFWorkbook var2) throws Exception;
}

