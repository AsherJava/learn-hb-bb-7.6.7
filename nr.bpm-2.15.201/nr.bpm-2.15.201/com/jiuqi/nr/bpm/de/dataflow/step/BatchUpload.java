/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageEventListener;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.BatchMessageThreadLocalStrategy;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchUploadBean;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BatchUpload {
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Value(value="${jiuqi.nr.workflow.todo-value:5000}")
    private int maxValue;
    @Autowired
    private MessageEventListener messageEventListener;

    public List<CompleteMsg> batchUpload(BatchUploadBean batchUploadBean) {
        ArrayList<CompleteMsg> msgList = new ArrayList<CompleteMsg>();
        CompleteMsg completeMsg = null;
        List<DimensionValueSet> canUploadUnit = batchUploadBean.getCanUploadUnit();
        String defaultFormKey = batchUploadBean.getDefaultFormKey();
        String formSchemeKey = batchUploadBean.getStepByOptParam().getFormSchemeKey();
        Map<String, LinkedHashSet<String>> formKeyMap = batchUploadBean.getStepByOptParam().getFormKeys();
        Map<String, LinkedHashSet<String>> groupKeyMap = batchUploadBean.getStepByOptParam().getGroupKeys();
        BatchMessageThreadLocalStrategy.setMessageEventListener(this.messageEventListener);
        BatchMessageThreadLocalStrategy.setSumTotal(canUploadUnit.size());
        BatchMessageThreadLocalStrategy.setUpperLimitValue(this.maxValue);
        for (DimensionValueSet dim : canUploadUnit) {
            String unitId = dim.getValue(batchUploadBean.getDimName()).toString();
            Set<String> formKeys = new HashSet();
            if (formKeyMap != null && ((formKeys = (Set)formKeyMap.get(unitId)) == null || formKeys.size() == 0)) {
                formKeys = new HashSet();
                formKeys.add(defaultFormKey);
            }
            Set<String> groupKeys = new HashSet();
            if (groupKeyMap != null && ((groupKeys = (Set)groupKeyMap.get(unitId)) == null || groupKeys.size() == 0)) {
                groupKeys = new HashSet();
                groupKeys.add(defaultFormKey);
            }
            BusinessKeySet businessKeySet = this.businessGenerator.buildBusinessKeySet(formSchemeKey, new ArrayList<DimensionValueSet>(Arrays.asList(dim)), formKeys, groupKeys);
            BatchExecuteParam executeParam = new BatchExecuteParam();
            executeParam.setBusinessKeySet(businessKeySet);
            executeParam.setActionId(batchUploadBean.getStepByOptParam().getActionId());
            executeParam.setUserId(NpContextHolder.getContext().getIdentityId());
            executeParam.setSendEmaill(batchUploadBean.getStepByOptParam().isSendEmail());
            executeParam.setFormSchemeKey(formSchemeKey);
            executeParam.setForceUpload(batchUploadBean.getStepByOptParam().isForceUpload());
            executeParam.setTaskId(batchUploadBean.getStepByOptParam().getTaskId());
            executeParam.setNodeId(batchUploadBean.getStepByOptParam().getNodeId());
            executeParam.setDimSet(dim);
            executeParam.setComment(batchUploadBean.getStepByOptParam().getContent());
            executeParam.setMessageIds(batchUploadBean.getStepByOptParam().getMessageIds());
            executeParam.setFormKey(defaultFormKey);
            executeParam.setCanUploadDimensionSets(canUploadUnit);
            executeParam.setConditionCache(batchUploadBean.getConditionCache());
            executeParam.setTaskContext(batchUploadBean.getTaskContext());
            if (WorkFlowType.ENTITY.equals((Object)batchUploadBean.getWorkFlowType())) {
                completeMsg = this.dataentryFlowService.batchExecuteTask(executeParam);
                msgList.add(completeMsg);
                continue;
            }
            if ((groupKeys == null || groupKeys.size() <= 0) && (formKeys == null || formKeys.size() <= 0)) continue;
            completeMsg = this.dataentryFlowService.batchExecuteTask(executeParam);
            msgList.add(completeMsg);
        }
        BatchMessageThreadLocalStrategy.clear();
        return msgList;
    }
}

