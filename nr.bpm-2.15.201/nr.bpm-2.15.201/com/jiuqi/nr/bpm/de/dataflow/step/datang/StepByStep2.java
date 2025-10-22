/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.step.datang;

import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.step.MultiUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.SingleUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StepByStep2 {
    private static final Logger logger = LoggerFactory.getLogger(StepByStep2.class);
    @Autowired
    private MultiUpload multiUpload;
    @Autowired
    private SingleUpload singleUpload;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;

    public StepByStepCheckResult stepByOpt(StepByOptParam stepByOptParam) {
        StepByStepCheckResult equal = new StepByStepCheckResult();
        equal = this.singleUpload.stepByOpt(stepByOptParam);
        if (equal.getItems().size() > 0) {
            return equal;
        }
        this.executeTask(stepByOptParam);
        return equal;
    }

    public CompleteMsg executeTask(StepByOptParam stepByOptParam) {
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setActionId(stepByOptParam.getActionId());
        executeParam.setComment(stepByOptParam.getContent());
        executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
        executeParam.setTaskId(stepByOptParam.getTaskId());
        ContextUser user = NpContextHolder.getContext().getUser();
        executeParam.setUserId(user.getId());
        executeParam.setDimSet(stepByOptParam.getDimensionValue());
        executeParam.setForceUpload(stepByOptParam.isForceUpload());
        executeParam.setSendEmaill(stepByOptParam.isSendEmail());
        executeParam.setFormKey(stepByOptParam.getFormKey());
        executeParam.setGroupKey(stepByOptParam.getGroupKey());
        executeParam.setNodeId(stepByOptParam.getTaskId());
        executeParam.setTaskContext(stepByOptParam.getContext());
        CompleteMsg executeTask = this.dataFlowService.executeTask(executeParam);
        return executeTask;
    }

    public BatchStepByStepResult batchStepByOpt(BatchStepByStepParam stepByOptParam) {
        BatchStepByStepResult batchStepByStepResult = new BatchStepByStepResult();
        if (stepByOptParam.getStepUnit() == null || stepByOptParam.getStepUnit().size() == 0) {
            logger.info("\u4e0a\u62a5\u5355\u4f4d\u4f20\u5165\u503c\u4e3a\u7a7a\uff0c\u8bf7\u6838\u5b9e\u4f20\u5165\u53c2\u6570");
            return batchStepByStepResult;
        }
        batchStepByStepResult = this.multiUpload.batchStepByOpt(stepByOptParam);
        return batchStepByStepResult;
    }

    public CompleteMsg stepRetrieve(StepByOptParam stepByOptParam) {
        return this.executeTask(stepByOptParam);
    }

    public CompleteMsg batchStepRetrieve(BatchStepByStepParam stepByOptParam) {
        CompleteMsg completeMsg = null;
        ArrayList msgList = new ArrayList();
        BatchExecuteParam executeParam = new BatchExecuteParam();
        String formSchemeKey = stepByOptParam.getFormSchemeKey();
        Map<String, LinkedHashSet<String>> formKeyMap = stepByOptParam.getFormKeys();
        Map<String, LinkedHashSet<String>> groupKeyMap = stepByOptParam.getGroupKeys();
        List<DimensionValueSet> stepUnits = stepByOptParam.getStepUnit();
        for (DimensionValueSet dim : stepUnits) {
            String unitId = dim.getValue(this.dimensionUtil.getDwMainDimName(formSchemeKey)).toString();
            HashSet<String> formKeys = (HashSet<String>)formKeyMap.get(unitId);
            HashSet<String> groupKeys = (HashSet<String>)groupKeyMap.get(unitId);
            if (formKeys == null) {
                formKeys = new HashSet<String>();
                formKeys.add(this.nrParameterUtils.getDefaultFormId(formSchemeKey));
            }
            if (groupKeys == null) {
                groupKeys = new HashSet<String>();
                groupKeys.add(this.nrParameterUtils.getDefaultFormId(formSchemeKey));
            }
            BusinessKeySet businessKeySet = this.businessGenerator.buildBusinessKeySet(stepByOptParam.getFormSchemeKey(), new ArrayList<DimensionValueSet>(Arrays.asList(dim)), formKeys, groupKeys);
            executeParam.setBusinessKeySet(businessKeySet);
            executeParam.setActionId(stepByOptParam.getActionId());
            executeParam.setUserId(NpContextHolder.getContext().getIdentityId());
            executeParam.setSendEmaill(stepByOptParam.isSendEmail());
            executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
            executeParam.setForceUpload(stepByOptParam.isForceUpload());
            executeParam.setTaskId(stepByOptParam.getTaskId());
            executeParam.setNodeId(stepByOptParam.getTaskCode());
            executeParam.setDimSet(dim);
            executeParam.setComment(stepByOptParam.getContent());
            executeParam.setMessageIds(stepByOptParam.getMessageIds());
            executeParam.setFormKey(this.nrParameterUtils.getDefaultFormId(formSchemeKey));
            executeParam.setTaskContext(stepByOptParam.getContext());
            completeMsg = this.dataFlowService.batchExecuteTask(executeParam);
        }
        if (msgList != null && msgList.size() > 0) {
            List msgListTemp = msgList.stream().filter(e -> !e.isSucceed()).collect(Collectors.toList());
            if (msgListTemp != null && msgListTemp.size() > 0) {
                completeMsg = (CompleteMsg)msgListTemp.get(0);
            } else if (msgList != null && msgList.size() > 0) {
                completeMsg = (CompleteMsg)msgList.get(0);
            }
        }
        return completeMsg;
    }
}

