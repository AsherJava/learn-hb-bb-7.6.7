/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDO
 *  com.jiuqi.va.domain.workflow.business.ConditionGroupRow
 *  com.jiuqi.va.domain.workflow.business.ConditionView
 *  com.jiuqi.va.domain.workflow.business.Formula
 *  com.jiuqi.va.domain.workflow.business.GroupInfo
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 */
package com.jiuqi.va.workflow.strategy.distribute;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDO;
import com.jiuqi.va.domain.workflow.business.ConditionGroupRow;
import com.jiuqi.va.domain.workflow.business.ConditionView;
import com.jiuqi.va.domain.workflow.business.Formula;
import com.jiuqi.va.domain.workflow.business.GroupInfo;
import com.jiuqi.va.domain.workflow.business.TargetObject;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.workflow.constants.DistributionTypeEnum;
import com.jiuqi.va.workflow.constants.WorkflowBusinessRelationConst;
import com.jiuqi.va.workflow.domain.BusinessWorkflowNodesInfoSaveProcess;
import com.jiuqi.va.workflow.service.WorkflowBusinessSaveService;
import com.jiuqi.va.workflow.strategy.distribute.DistributeStrategy;
import com.jiuqi.va.workflow.strategy.distribute.DistributeUtil;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowCacheUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class DistributeAdaptConditionStrategy
implements DistributeStrategy {
    private static final Logger log = LoggerFactory.getLogger(DistributeAdaptConditionStrategy.class);
    @Autowired
    private WorkflowBusinessSaveService workflowBusinessSaveService;

    @Override
    public int getDistributeType() {
        return DistributionTypeEnum.ADAPT_CONDITION.getValue();
    }

    @Override
    public void executeDistribute(WorkflowBusinessDistributeDTO distributionDTO) {
        BusinessWorkflowNodesInfoSaveProcess processInfo = DistributeUtil.setProcessInfo(distributionDTO);
        Formula adaptCondition = distributionDTO.getAdaptCondition();
        ConditionView conditionView = distributionDTO.getConditionView();
        if (ObjectUtils.isEmpty(adaptCondition) && ObjectUtils.isEmpty(conditionView)) {
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        }
        String currentUserId = ShiroUtil.getUser().getId();
        String distributeMode = conditionView.getDistributeMode();
        List targetObjectList = distributionDTO.getTargetObjectList();
        List<WorkflowBusinessDO> workflowBusinessDOList = DistributeUtil.getTargetBindingRelation(targetObjectList);
        Map<String, TargetObject> targetObjectMap = DistributeUtil.getTargetObjectMap(targetObjectList);
        Map<String, Object> processedParamMap = DistributeUtil.getProcessedParams(distributionDTO);
        for (WorkflowBusinessDO workflowBusinessDO : workflowBusinessDOList) {
            WorkflowBusinessDistributeDTO processedParam;
            String businessCode;
            TargetObject targetObject;
            if (DistributeUtil.shouldSkip(workflowBusinessDO, currentUserId, targetObject = targetObjectMap.get((businessCode = workflowBusinessDO.getBusinesscode()) + "_" + workflowBusinessDO.getWorkflowdefinekey()), processInfo, processedParam = (WorkflowBusinessDistributeDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)processedParamMap.get(workflowBusinessDO.getBusinesscode())), WorkflowBusinessDistributeDTO.class))) continue;
            try {
                Map<String, Object> designDataMap = DistributeUtil.getDesignDataMap(workflowBusinessDO);
                designDataMap.put("adaptcondition", processedParam.getAdaptCondition());
                this.updateConditionGroup(designDataMap, processedParam.getConditionView(), distributeMode);
                workflowBusinessDO.setDesigndata(JSONUtil.toJSONString(designDataMap));
                this.workflowBusinessSaveService.save(workflowBusinessDO);
                targetObject.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.SUCCESS.getValue());
                targetObject.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.success"));
            }
            catch (Exception e) {
                log.error("\u4e0b\u53d1\u9002\u5e94\u6761\u4ef6\u6216\u6761\u4ef6\u7ec4\u65f6\u5f02\u5e38", e);
                targetObject.setDistributeStatus(WorkflowBusinessRelationConst.DistributeStatus.FAILED.getValue());
                targetObject.setSaveResult(VaWorkFlowI18nUtils.getInfo("va.workflow.failed") + e.getMessage());
            }
            processInfo.setCurrIndex(processInfo.getCurrIndex() + 1);
            VaWorkflowCacheUtils.setImportDataResult(processInfo.getRsKey(), JSONUtil.toJSONString((Object)processInfo));
        }
        processInfo.setNodes(targetObjectList);
        VaWorkflowCacheUtils.setImportDataResult(distributionDTO.getResultKey(), JSONUtil.toJSONString((Object)processInfo));
    }

    private void updateConditionGroup(Map<String, Object> designDataMap, ConditionView newConditionView, String distributeMode) {
        Map<String, Object> oldConditionViewMap = VaWorkflowUtils.getMap(designDataMap.get("conditionView"));
        if (designDataMap.containsKey("conditionView") && !oldConditionViewMap.isEmpty()) {
            ConditionView oldConditionView = (ConditionView)JSONUtil.parseObject((String)JSONUtil.toJSONString(oldConditionViewMap), ConditionView.class);
            DistributeAdaptConditionStrategy.updateExistData(newConditionView, oldConditionView, distributeMode);
            designDataMap.put("conditionView", oldConditionView);
        } else if ("group".equals(distributeMode)) {
            designDataMap.put("conditionView", newConditionView);
        }
    }

    private static void updateExistData(ConditionView newConditionView, ConditionView oldConditionView, String distributeMode) {
        List oldGroupList = oldConditionView.getGroupInfoList();
        List newGroupList = newConditionView.getGroupInfoList();
        if ("group".equals(distributeMode)) {
            oldGroupList.addAll(newGroupList);
        }
        if ("line".equalsIgnoreCase(distributeMode)) {
            int minSize = Math.min(oldGroupList.size(), newGroupList.size());
            for (int i = 0; i < minSize; ++i) {
                List oldInfoList = ((GroupInfo)oldGroupList.get(i)).getInfo();
                List newInfoList = ((GroupInfo)newGroupList.get(i)).getInfo();
                for (ConditionGroupRow rowData : newInfoList) {
                    String lineDistributeType = rowData.getDistributeType();
                    if ("add".equalsIgnoreCase(lineDistributeType)) {
                        oldInfoList.add(rowData);
                    }
                    if (!"delete".equalsIgnoreCase(lineDistributeType)) continue;
                    oldInfoList.removeIf(o -> Objects.equals(o.getBizField().getName(), rowData.getBizField().getName()));
                }
            }
        }
    }
}

