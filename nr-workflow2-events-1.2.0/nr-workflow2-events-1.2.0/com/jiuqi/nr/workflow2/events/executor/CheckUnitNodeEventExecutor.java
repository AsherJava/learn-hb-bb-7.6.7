/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.data.gather.bean.NodeCheckParam
 *  com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult
 *  com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor
 *  com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor
 *  com.jiuqi.nr.data.gather.refactor.service.NodeCheckService
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
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResult;
import com.jiuqi.nr.data.gather.refactor.monitor.IGatherServiceMonitor;
import com.jiuqi.nr.data.gather.refactor.monitor.impl.DefaultMonitor;
import com.jiuqi.nr.data.gather.refactor.service.NodeCheckService;
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
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.helper.CurrencyFilterCondition;
import com.jiuqi.nr.workflow2.events.helper.DimensionBuilderCondition;
import com.jiuqi.nr.workflow2.events.monitor.ProcessAsyncTaskMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CheckUnitNodeEventExecutor
extends AbstractActionEventExecutor {
    protected EventDependentServiceHelper helper;
    protected static final String attr_key_nodeCheckCurrency = "checkCurrency";
    protected static final String attr_key_nodeCheckCurrency_type = "type";
    protected static final String attr_key_nodeCheckCurrency_value = "currency";

    public CheckUnitNodeEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) {
        if (actionArgs.getBoolean("FORCE_REPORT")) {
            operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u5f00\u542f\u4e86\u5f3a\u5236\u4e0a\u62a5\uff0c\u4e0d\u6267\u884c\u8282\u70b9\u68c0\u67e5\uff01\uff01"));
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKey.getTask(), envParam.getPeriod());
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

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
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
        operateResultSet.setOperateResult((Object)nodeCheckResults);
        HashMap<String, WFMonitorCheckResult> checkResultMap = new HashMap<String, WFMonitorCheckResult>();
        for (IBusinessObject businessObject : businessObjects) {
            String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            if (checkResultMap.containsKey(unitId)) {
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo((WFMonitorCheckResult)checkResultMap.get(unitId), "nodeCheck"));
                continue;
            }
            WFMonitorCheckResult wfMonitorCheckResult = WFMonitorCheckResult.CHECK_PASS;
            NodeCheckResult nodeCheckResult = nodeCheckResults.stream().filter(e -> e.getUnitKey().equals(unitId)).findFirst().orElse(null);
            if (nodeCheckResult != null && !nodeCheckResult.isLeafUnit() && !nodeCheckResult.getResultItemInfos().isEmpty()) {
                wfMonitorCheckResult = WFMonitorCheckResult.CHECK_UN_PASS;
            }
            checkResultMap.put(unitId, wfMonitorCheckResult);
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(wfMonitorCheckResult, "nodeCheck", (Object)nodeCheckResult));
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK);
    }

    protected DimensionBuilderCondition getDimensionBuilderCondition(ProcessExecuteEnv envParam) {
        DimensionBuilderCondition builderCondition = new DimensionBuilderCondition();
        CurrencyType currencyType = this.getNodeCheckCurrencyType(this.eventJsonConfig);
        List<String> customCurrencies = this.getNodeCheckCurrencyCustomValues(this.eventJsonConfig);
        boolean isMdCurrencyReferEntity = this.helper.dimensionHelper.isMdCurrencyReferEntity(envParam.getTaskKey(), envParam.getPeriod());
        CurrencyFilterCondition currencyFilterCondition = new CurrencyFilterCondition();
        currencyFilterCondition.setCurrencyType(currencyType);
        currencyFilterCondition.setMdCurrencyReferEntity(isMdCurrencyReferEntity);
        currencyFilterCondition.setCustomCurrency(customCurrencies);
        builderCondition.setCurrencyFilterCondition(currencyFilterCondition);
        return builderCondition;
    }

    protected CurrencyType getNodeCheckCurrencyType(JSONObject eventConfig) {
        JSONObject nodeCheckCurrency;
        if (eventConfig.has(attr_key_nodeCheckCurrency) && (nodeCheckCurrency = eventConfig.getJSONObject(attr_key_nodeCheckCurrency)).has(attr_key_nodeCheckCurrency_type)) {
            return CurrencyType.valueOf(nodeCheckCurrency.getString(attr_key_nodeCheckCurrency_type));
        }
        return null;
    }

    protected List<String> getNodeCheckCurrencyCustomValues(JSONObject eventConfig) {
        JSONObject auditCurrency;
        ArrayList<String> auditCurrencyCustomValues = new ArrayList<String>();
        if (eventConfig.has(attr_key_nodeCheckCurrency) && (auditCurrency = eventConfig.getJSONObject(attr_key_nodeCheckCurrency)).has(attr_key_nodeCheckCurrency_value)) {
            JSONArray currencyJSONArray = auditCurrency.getJSONArray(attr_key_nodeCheckCurrency_value);
            for (int i = 0; i < currencyJSONArray.length(); ++i) {
                auditCurrencyCustomValues.add(currencyJSONArray.getString(i));
            }
        }
        return auditCurrencyCustomValues;
    }
}

