/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult
 *  com.jiuqi.nr.dataentry.bean.StepByStepCheckItem
 *  com.jiuqi.nr.dataentry.bean.StepByStepCheckResult
 *  com.jiuqi.nr.dataentry.internal.service.BatchDataSumServiceImpl
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.events.response.StepByStepUploadItem
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn
 */
package com.jiuqi.nr.workflow2.converter.dataentry.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckItem;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckResult;
import com.jiuqi.nr.dataentry.internal.service.BatchDataSumServiceImpl;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.events.response.StepByStepUploadItem;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataEntrySingleExecResultManager
extends EventOperateResult {
    private final IBusinessObject businessObject;
    private final BatchDataSumServiceImpl batchDataSumService;

    public DataEntrySingleExecResultManager(IBusinessObject businessObject) {
        super(businessObject);
        this.businessObject = businessObject;
        this.batchDataSumService = (BatchDataSumServiceImpl)SpringBeanUtils.getBean(BatchDataSumServiceImpl.class);
    }

    public Object toOutputDetail() {
        for (IEventOperateColumn eventOperateColumn : this.operateResultSetMap.keySet()) {
            EventExecutionAffect affectStatus = eventOperateColumn.getAffectStatus();
            IOperateResultSet operateResultSet = this.getOperateResultSet(eventOperateColumn);
            if (operateResultSet == null || !affectStatus.equals((Object)EventExecutionAffect.IMPACT_REPORTING_CHECK) || !operateResultSet.getOperateResult(this.businessObject).getCheckResult().equals((Object)WFMonitorCheckResult.CHECK_UN_PASS)) continue;
            return this.getEventDetail(eventOperateColumn, operateResultSet);
        }
        return null;
    }

    public String toResultMessage() {
        for (IEventOperateColumn eventOperateColumn : this.operateResultSetMap.keySet()) {
            EventExecutionAffect affectStatus = eventOperateColumn.getAffectStatus();
            IOperateResultSet operateResultSet = this.getOperateResultSet(eventOperateColumn);
            if (operateResultSet == null || !affectStatus.equals((Object)EventExecutionAffect.IMPACT_REPORTING_CHECK) || !operateResultSet.getOperateResult(this.businessObject).getCheckResult().equals((Object)WFMonitorCheckResult.CHECK_UN_PASS)) continue;
            return operateResultSet.getOperateResult(this.businessObject).getCheckResultMessage();
        }
        return "";
    }

    private Object getEventDetail(IEventOperateColumn eventOperateColumn, IOperateResultSet operateResultSet) {
        String eventId = eventOperateColumn.getColumnName();
        if (eventId.contains("step-by-step-reject-event") || eventId.contains("step-by-step-upload-event")) {
            Map checkResultDetail = (Map)this.operateMergeResult.get(eventOperateColumn);
            String userActionAlias = checkResultDetail.get("userActionAlias").toString();
            StepByStepCheckResult stepByStepCheckResult = new StepByStepCheckResult();
            stepByStepCheckResult.setChild(eventId.equals("step-by-step-upload-event"));
            stepByStepCheckResult.setActionStateTitle(userActionAlias);
            stepByStepCheckResult.setDirectActionStateTitle(userActionAlias);
            IEventOperateInfo operateResult = operateResultSet.getOperateResult(this.businessObject);
            List uploadCheckItems = (List)operateResult.getCheckResultDetail();
            ArrayList<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
            for (StepByStepUploadItem result : uploadCheckItems) {
                StepByStepCheckItem item = new StepByStepCheckItem();
                item.setUnitId(result.getUnitId());
                item.setUnitCode(result.getUnitCode());
                item.setUnitTitle(result.getUnitTitle());
                item.setWorkflowState(result.getWorkflowState());
                items.add(item);
            }
            stepByStepCheckResult.setItems(items);
            return stepByStepCheckResult;
        }
        if (eventId.equals("complete-review-event")) {
            return this.operateMergeResult.get(eventOperateColumn);
        }
        if (eventId.equals("check-unit-node-event")) {
            NodeCheckResult nodeCheckResult = (NodeCheckResult)this.operateMergeResult.get(eventOperateColumn);
            return this.transferObjectToJsonString(this.batchDataSumService.convertNodeCheckResultToNodeCheckResultInfo(nodeCheckResult));
        }
        IEventOperateInfo operateResult = operateResultSet.getOperateResult(this.businessObject);
        if (operateResult != null) {
            Object checkResultDetail = operateResult.getCheckResultDetail();
            if (checkResultDetail instanceof String) {
                return checkResultDetail.toString();
            }
            return this.transferObjectToJsonString(operateResult.getCheckResultDetail());
        }
        return null;
    }

    private String transferObjectToJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

