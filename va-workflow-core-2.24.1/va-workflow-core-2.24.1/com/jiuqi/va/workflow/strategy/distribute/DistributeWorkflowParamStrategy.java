/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowVariable
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.business.TargetObject;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowVariable;
import com.jiuqi.va.workflow.constants.DistributionTypeEnum;
import com.jiuqi.va.workflow.constants.WorkflowBusinessRelationConst;
import com.jiuqi.va.workflow.domain.BusinessWorkflowNodesInfoSaveProcess;
import com.jiuqi.va.workflow.service.WorkflowBusinessSaveService;
import com.jiuqi.va.workflow.strategy.distribute.DistributeStrategy;
import com.jiuqi.va.workflow.strategy.distribute.DistributeUtil;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowCacheUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class DistributeWorkflowParamStrategy
implements DistributeStrategy {
    private static final Logger log = LoggerFactory.getLogger(DistributeWorkflowParamStrategy.class);
    @Autowired
    private WorkflowBusinessSaveService workflowBusinessSaveService;

    @Override
    public int getDistributeType() {
        return DistributionTypeEnum.WORKFLOW_PARAM.getValue();
    }

    @Override
    public void executeDistribute(WorkflowBusinessDistributeDTO distributionDTO) {
        BusinessWorkflowNodesInfoSaveProcess processInfo = DistributeUtil.setProcessInfo(distributionDTO);
        List workflowVariableList = distributionDTO.getWorkflowVariableList();
        if (ObjectUtils.isEmpty(workflowVariableList)) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String currentUserId = ShiroUtil.getUser().getId();
        List targetObjectList = distributionDTO.getTargetObjectList();
        List<WorkflowBusinessDO> workflowBusinessDOList = DistributeUtil.getTargetBindingRelation(targetObjectList);
        Map<String, TargetObject> targetObjectMap = DistributeUtil.getTargetObjectMap(targetObjectList);
        Map<String, Object> processedParamMap = DistributeUtil.getProcessedParams(distributionDTO);
        String sourceWorkflowDefineKey = distributionDTO.getSourceWorkflowDefineKey();
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOList) {
            WorkflowBusinessDistributeDTO processedParam;
            String targetWorkflowDefineKey = workflowBusinessDO.getWorkflowdefinekey();
            TargetObject resultMap = targetObjectMap.get(workflowBusinessDO.getBusinesscode() + "_" + targetWorkflowDefineKey);
            if (DistributeUtil.shouldSkip(workflowBusinessDO, currentUserId, resultMap, processInfo, processedParam = (WorkflowBusinessDistributeDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)processedParamMap.get(workflowBusinessDO.getBusinesscode())), WorkflowBusinessDistributeDTO.class))) continue;
            try {
                Map<String, Object> designDataMap = DistributeUtil.getDesignDataMap(workflowBusinessDO);
                String resultMessage = this.updateWorkflowVariables(sourceWorkflowDefineKey, targetWorkflowDefineKey, designDataMap, processedParam.getWorkflowVariableList());
                workflowBusinessDO.setDesigndata(JSONUtil.toJSONString(designDataMap));
                this.workflowBusinessSaveService.save(workflowBusinessDO);
                if (VaWorkFlowI18nUtils.getInfo("va.workflow.success").equals(resultMessage)) {
                    resultMap.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.SUCCESS.getValue());
                } else {
                    resultMap.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.SUCCESS_WITH_SKIP.getValue());
                }
                resultMap.setSaveResult(resultMessage);
            }
            catch (Exception e) {
                log.error("\u4e0b\u53d1\u53c2\u6570\u53d6\u503c\u65f6\u5f02\u5e38", e);
                resultMap.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.FAILED.getValue());
                resultMap.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.failed") + e.getMessage());
            }
            processInfo.setCurrIndex(processInfo.getCurrIndex() + 1);
            VaWorkflowCacheUtils.setImportDataResult(processInfo.getRsKey(), JSONUtil.toJSONString((Object)processInfo));
        }
        processInfo.setNodes(targetObjectList);
        VaWorkflowCacheUtils.setImportDataResult(distributionDTO.getResultKey(), JSONUtil.toJSONString((Object)processInfo));
    }

    private String updateWorkflowVariables(String sourceWorkflowDefineKey, String targetWorkflowDefineKey, Map<String, Object> designDataMap, List<WorkflowVariable> newWorkflowVariables) {
        StringBuilder successMessage = new StringBuilder(VaWorkFlowI18nUtils.getInfo("va.workflow.success"));
        List oldWorkflowVariables = (List)designDataMap.computeIfAbsent("workflowvariables", k -> new ArrayList());
        HashMap<String, Map> nameMap = new HashMap<String, Map>();
        for (Map oldWorkflowVariable : oldWorkflowVariables) {
            Object paramNameObject = oldWorkflowVariable.get("paramName");
            if (paramNameObject == null) continue;
            nameMap.put(String.valueOf(paramNameObject).toUpperCase(), oldWorkflowVariable);
        }
        StringBuilder sb = new StringBuilder();
        for (WorkflowVariable newWorkflowVariable : newWorkflowVariables) {
            String paramName = String.valueOf(newWorkflowVariable.getParamName()).toUpperCase();
            Map oldWorkflowVariable = (Map)nameMap.get(paramName);
            if (oldWorkflowVariable == null && !targetWorkflowDefineKey.equals(sourceWorkflowDefineKey)) {
                sb.append(newWorkflowVariable.getParamTitle()).append("(").append(newWorkflowVariable.getParamName()).append(")\u3001");
                continue;
            }
            if (oldWorkflowVariable == null) {
                oldWorkflowVariables.add(JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)newWorkflowVariable)));
                continue;
            }
            oldWorkflowVariable.put("valueformula", newWorkflowVariable.getFormula());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            successMessage.append("\uff0c\u76ee\u6807\u7ed1\u5b9a\u5173\u7cfb\u53c2\u6570\u53d6\u503c\u6ca1\u6709").append((CharSequence)sb).append("\uff0c\u5df2\u8df3\u8fc7");
        }
        return successMessage.toString();
    }
}

