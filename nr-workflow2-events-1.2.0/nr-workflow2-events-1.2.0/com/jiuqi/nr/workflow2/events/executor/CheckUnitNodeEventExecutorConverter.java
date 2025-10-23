/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContextHolder
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.gather.bean.NodeCheckParam
 *  com.jiuqi.nr.data.gather.bean.NodeCheckResultItem
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo
 *  com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor
 *  com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor
 *  com.jiuqi.nr.data.gather.refactor.service.NodeCheckService
 *  com.jiuqi.nr.dataentry.bean.NodeCheckFieldMessage
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultItem
 *  com.jiuqi.nr.dataentry.internal.service.BatchDataSumServiceImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContextHolder;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
import com.jiuqi.nr.dataentry.bean.NodeCheckFieldMessage;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.internal.service.BatchDataSumServiceImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.events.executor.CheckUnitNodeEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.sub.BatchNodeCheckDataBuildTaskExecutor;
import com.jiuqi.nr.workflow2.events.monitor.ProcessAsyncTaskMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class CheckUnitNodeEventExecutorConverter
extends CheckUnitNodeEventExecutor {
    public static final String KEY_OF_NODE_CHECK_RESULT = "nodeCheckResult";
    public static final String KEY_OF_ASYNC_TASK_ID = "asyncTaskId";
    private final BatchDataSumServiceImpl batchDataSumService;

    public CheckUnitNodeEventExecutorConverter(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig, helper);
        this.helper = helper;
        this.batchDataSumService = (BatchDataSumServiceImpl)SpringBeanUtils.getBean(BatchDataSumServiceImpl.class);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) {
        if (actionArgs.getBoolean("FORCE_REPORT")) {
            operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5f00\u542f\u4e86\u5f3a\u5236\u4e0a\u62a5\uff0c\u4e0d\u6267\u884c\u8282\u70b9\u68c0\u67e5\uff01\uff01"));
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        String period = businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue().getValue().toString();
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKey.getTask(), period);
        NodeCheckParam nodeCheckParam = new NodeCheckParam();
        nodeCheckParam.setTaskKey(businessKey.getTask());
        nodeCheckParam.setFormSchemeKey(formScheme.getKey());
        List<String> checkRangeFormKeys = this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKey);
        nodeCheckParam.setFormKeys(String.join((CharSequence)";", checkRangeFormKeys));
        DimensionCollectionBuilder dimensionCollectionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKey, this.getDimensionBuilderCondition(envParam));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        nodeCheckParam.setDimensionCollection(dimensionCollection);
        NodeCheckService nodeCheckService = this.helper.dataGatherServiceFactory.getNodeCheckService();
        NodeCheckResult nodeCheckResult = nodeCheckService.nodeCheck(nodeCheckParam);
        WFMonitorCheckResult wfMonitorCheckResult = nodeCheckResult != null && !nodeCheckResult.getResultItemInfos().isEmpty() ? WFMonitorCheckResult.CHECK_UN_PASS : WFMonitorCheckResult.CHECK_PASS;
        operateResultSet.setOperateResult((Object)nodeCheckResult);
        operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(wfMonitorCheckResult, "nodeCheck", (Object)nodeCheckResult));
        EventExecutionStatus finishStatus = WFMonitorCheckResult.CHECK_PASS == wfMonitorCheckResult ? EventExecutionStatus.FINISH : EventExecutionStatus.STOP;
        return new EventFinishedResult(finishStatus, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        String asyncTaskId;
        if (actionArgs.getBoolean("FORCE_REPORT")) {
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKeyCollection.getTask(), envParam.getPeriod());
        NodeCheckService nodeCheckService = this.helper.dataGatherServiceFactory.getNodeCheckService();
        NodeCheckParam nodeCheckParam = new NodeCheckParam();
        nodeCheckParam.setTaskKey(businessKeyCollection.getTask());
        nodeCheckParam.setFormSchemeKey(formScheme.getKey());
        List<String> checkRangeFormKeys = this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKeyCollection);
        nodeCheckParam.setFormKeys(String.join((CharSequence)";", checkRangeFormKeys));
        DimensionCollectionBuilder dimensionCollectionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKeyCollection, this.getDimensionBuilderCondition(envParam));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        nodeCheckParam.setDimensionCollection(dimensionCollection);
        DefaultMonitor nodeCheckMonitor = new DefaultMonitor((AsyncTaskMonitor)new ProcessAsyncTaskMonitor(monitor));
        List nodeCheckResults = nodeCheckService.batchNodeCheck(nodeCheckParam, (IGatherServiceMonitor)nodeCheckMonitor);
        BatchNodeCheckDataBuildTaskExecutor subRealTimeJob = new BatchNodeCheckDataBuildTaskExecutor();
        Map params = subRealTimeJob.getParams();
        params.put("NR_ARGS", CheckUnitNodeEventExecutorConverter.transferObjectToJsonString(this.transferResultToNodeCheckResultInfo(nodeCheckResults)));
        try {
            asyncTaskId = JobContextHolder.getJobContext().executeRealTimeSubJob((AbstractRealTimeJob)subRealTimeJob);
        }
        catch (JobExecutionException e2) {
            throw new RuntimeException(e2);
        }
        operateResultSet.setOperateResult((Object)asyncTaskId);
        HashMap<String, WFMonitorCheckResult> checkResultMap = new HashMap<String, WFMonitorCheckResult>();
        HashMap<String, NodeCheckResult> checkResultNodeInfo = new HashMap<String, NodeCheckResult>();
        for (IBusinessObject businessObject : businessObjects) {
            String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            if (checkResultMap.containsKey(unitId)) {
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo((WFMonitorCheckResult)checkResultMap.get(unitId), "nodeCheck", checkResultNodeInfo.get(unitId)));
                continue;
            }
            WFMonitorCheckResult wfMonitorCheckResult = WFMonitorCheckResult.CHECK_PASS;
            NodeCheckResult nodeCheckResult = nodeCheckResults.stream().filter(e -> e.getUnitKey().equals(unitId)).findFirst().orElse(null);
            if (nodeCheckResult != null && !nodeCheckResult.isLeafUnit() && !nodeCheckResult.getResultItemInfos().isEmpty()) {
                wfMonitorCheckResult = WFMonitorCheckResult.CHECK_UN_PASS;
            }
            checkResultMap.put(unitId, wfMonitorCheckResult);
            checkResultNodeInfo.put(unitId, nodeCheckResult);
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(wfMonitorCheckResult, "nodeCheck", (Object)nodeCheckResult));
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    private NodeCheckResultInfo transferResultToNodeCheckResultInfo(List<NodeCheckResult> nodeCheckResults) {
        int totalCheckUnit = 0;
        int unPassUnit = 0;
        HashMap<String, List> nodeCheckResult = new HashMap<String, List>();
        ArrayList<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();
        for (NodeCheckResult checkResult : nodeCheckResults) {
            NodeCheckResultInfo nodeCheckResultInfo = this.batchDataSumService.convertNodeCheckResultToNodeCheckResultInfo(checkResult);
            totalCheckUnit += nodeCheckResultInfo.getTotalCheckUnit();
            unPassUnit += nodeCheckResultInfo.getUnPassUnit();
            List listInfos = nodeCheckResultInfo.getDimensionList();
            Map mapInfos = nodeCheckResultInfo.getNodeCheckResult();
            Map<Integer, Integer> oldToNewIndexMap = this.getOldToNewIndexMap(dimensionList, listInfos);
            for (Map.Entry entry : mapInfos.entrySet()) {
                String unitId = (String)entry.getKey();
                List nodeCheckResultItems = nodeCheckResult.computeIfAbsent(unitId, k -> new ArrayList());
                for (com.jiuqi.nr.dataentry.bean.NodeCheckResultItem nodeCheckResultItem : (List)entry.getValue()) {
                    nodeCheckResultItem.setDimensionIndex(oldToNewIndexMap.get(nodeCheckResultItem.getDimensionIndex()).intValue());
                    nodeCheckResultItems.add(nodeCheckResultItem);
                }
            }
        }
        NodeCheckResultInfo newNodeCheckResultInfo = new NodeCheckResultInfo();
        newNodeCheckResultInfo.setTotalCheckUnit(totalCheckUnit);
        newNodeCheckResultInfo.setUnPassUnit(unPassUnit);
        newNodeCheckResultInfo.setNodeCheckResult(nodeCheckResult);
        newNodeCheckResultInfo.setDimensionList(dimensionList);
        return newNodeCheckResultInfo;
    }

    private Map<Integer, Integer> getOldToNewIndexMap(List<Map<String, DimensionValue>> dimensionList, List<Map<String, DimensionValue>> targetDimensionList) {
        HashMap<Integer, Integer> oldToNewIndexMap = new HashMap<Integer, Integer>();
        for (int oldIndex = 0; oldIndex < targetDimensionList.size(); ++oldIndex) {
            int newIndex = this.indexOf(dimensionList, targetDimensionList.get(oldIndex));
            if (newIndex == -1) {
                dimensionList.add(targetDimensionList.get(oldIndex));
                oldToNewIndexMap.put(oldIndex, dimensionList.size() - 1);
                continue;
            }
            oldToNewIndexMap.put(oldIndex, newIndex);
        }
        return oldToNewIndexMap;
    }

    private int indexOf(List<Map<String, DimensionValue>> dimensionList, Map<String, DimensionValue> dimInfo) {
        int indexOf = -1;
        for (int index = 0; index < dimensionList.size(); ++index) {
            Map<String, DimensionValue> newDimInfo = dimensionList.get(index);
            if (!newDimInfo.equals(dimInfo)) continue;
            return index;
        }
        return indexOf;
    }

    private NodeCheckResultInfo _transferResultToNodeCheckResultInfo(List<NodeCheckResult> nodeCheckResults) {
        int UnPassUnitNum = 0;
        HashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>> nodeCheckResult = new HashMap<String, List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>>();
        ArrayList<Map> dimensionList = new ArrayList<Map>();
        for (NodeCheckResult checkResult : nodeCheckResults) {
            if (checkResult == null) continue;
            if (!checkResult.isLeafUnit() && !checkResult.getResultItemInfos().isEmpty()) {
                ++UnPassUnitNum;
            }
            Set resultItemInfos = checkResult.getResultItemInfos();
            for (NodeCheckResultItemInfo itemInfo : resultItemInfos) {
                if (dimensionList.contains(itemInfo.getDimensionValue()) || nodeCheckResult.containsKey(itemInfo.getUnitTitle())) continue;
                nodeCheckResult.put(itemInfo.getUnitTitle(), this.transferToTargetNodeCheckResultItems(itemInfo.getNodeCheckResultItems()));
                dimensionList.add(itemInfo.getDimensionValue());
            }
        }
        NodeCheckResultInfo newNodeCheckResultInfo = new NodeCheckResultInfo();
        newNodeCheckResultInfo.setTotalCheckUnit(nodeCheckResults.size());
        newNodeCheckResultInfo.setNodeCheckResult(nodeCheckResult);
        newNodeCheckResultInfo.setUnPassUnit(UnPassUnitNum);
        newNodeCheckResultInfo.setDimensionList(dimensionList);
        return newNodeCheckResultInfo;
    }

    private List<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem> transferToTargetNodeCheckResultItems(List<NodeCheckResultItem> checkResultList) {
        ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem> targetNodeCheckResultItems = new ArrayList<com.jiuqi.nr.dataentry.bean.NodeCheckResultItem>();
        for (NodeCheckResultItem checkResultItem : checkResultList) {
            com.jiuqi.nr.dataentry.bean.NodeCheckResultItem targetResultItem = new com.jiuqi.nr.dataentry.bean.NodeCheckResultItem();
            targetResultItem.setFieldTitle(checkResultItem.getFieldTitle());
            targetResultItem.setFieldCode(checkResultItem.getFieldCode());
            targetResultItem.setParentValue(checkResultItem.getParentValue());
            targetResultItem.setChildValue(checkResultItem.getChildValue());
            targetResultItem.setMinusValue(checkResultItem.getMinusValue());
            NodeCheckFieldMessage targetFieldMessage = new NodeCheckFieldMessage();
            targetFieldMessage.setFieldKey(checkResultItem.getNodeCheckFieldMessage().getFieldKey());
            targetFieldMessage.setFormKey(checkResultItem.getNodeCheckFieldMessage().getFormKey());
            targetFieldMessage.setRegionKey(checkResultItem.getNodeCheckFieldMessage().getRegionKey());
            targetFieldMessage.setDataLinkKey(checkResultItem.getNodeCheckFieldMessage().getDataLinkKey());
            targetFieldMessage.setFormTitle(checkResultItem.getNodeCheckFieldMessage().getFormTitle());
            targetFieldMessage.setFormOrder(checkResultItem.getNodeCheckFieldMessage().getFormOrder());
            targetResultItem.setNodeCheckFieldMessage(targetFieldMessage);
            targetResultItem.setUnitTitle(checkResultItem.getUnitTitle());
            targetResultItem.setUnitKey(checkResultItem.getUnitKey());
            targetResultItem.setDimensionIndex(checkResultItem.getDimensionIndex());
            targetNodeCheckResultItems.add(targetResultItem);
        }
        return targetNodeCheckResultItems;
    }

    private static String transferObjectToJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

