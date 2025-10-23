/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.jtable.params.base.JtableContext
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

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.jtable.params.base.JtableContext;
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
import com.jiuqi.nr.workflow2.events.monitor.ProcessCalculateEventMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class CompleteCalculationEventExecutor
extends AbstractActionEventExecutor {
    protected EventDependentServiceHelper helper;
    protected static final String attr_key_formulaScheme = "formulaScheme";

    public CompleteCalculationEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        ExecuteTaskParam executeTaskParam = this.getExecuteTaskParam(actionArgs);
        List<String> customCurrencies = this.getCustomCurrencies(envParam, executeTaskParam);
        DimensionCollectionBuilder dimensionCollectionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKey, this.getDimensionBuilderCondition(envParam, customCurrencies));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        CalculateParam calcParam = new CalculateParam();
        calcParam.setDimensionCollection(dimensionCollection);
        calcParam.setMode(Mode.FORM);
        calcParam.setRangeKeys(this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKey));
        List<String> calcFormulaSchemeKeys = this.getCalcFormulaSchemeKeys(this.eventJsonConfig);
        ProcessCalculateEventMonitor fmlMonitor = new ProcessCalculateEventMonitor(monitor);
        for (String formulaSchemeKey : calcFormulaSchemeKeys) {
            calcParam.setFormulaSchemeKey(formulaSchemeKey);
            this.helper.calculateService.batchCalculate(calcParam, (IFmlMonitor)fmlMonitor);
        }
        operateResultSet.setOperateResult(businessKey.getBusinessObject(), (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS, "\u8fd0\u7b97\u5b8c\u6210\uff01"));
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        BatchExecuteTaskParam batchExecuteTaskParam = this.getBatchExecuteTaskParam(actionArgs);
        List<String> customCurrencies = this.getCustomCurrencies(envParam, batchExecuteTaskParam);
        DimensionCollectionBuilder dimensionCollectionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKeyCollection, this.getDimensionBuilderCondition(envParam, customCurrencies));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        CalculateParam calcParam = new CalculateParam();
        calcParam.setDimensionCollection(dimensionCollection);
        calcParam.setMode(Mode.FORM);
        calcParam.setRangeKeys(this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKeyCollection));
        List<String> calcFormulaSchemeKeys = this.getCalcFormulaSchemeKeys(this.eventJsonConfig);
        ProcessCalculateEventMonitor fmlMonitor = new ProcessCalculateEventMonitor(monitor);
        for (String formulaSchemeKey : calcFormulaSchemeKeys) {
            calcParam.setFormulaSchemeKey(formulaSchemeKey);
            this.helper.calculateService.batchCalculate(calcParam, (IFmlMonitor)fmlMonitor);
        }
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
    }

    protected DimensionBuilderCondition getDimensionBuilderCondition(ProcessExecuteEnv envParam, List<String> customCurrencies) {
        DimensionBuilderCondition builderCondition = new DimensionBuilderCondition();
        CurrencyType currencyType = customCurrencies.isEmpty() ? CurrencyType.ALL : CurrencyType.CUSTOM;
        boolean isMdCurrencyReferEntity = this.helper.dimensionHelper.isMdCurrencyReferEntity(envParam.getTaskKey(), envParam.getPeriod());
        CurrencyFilterCondition currencyFilterCondition = new CurrencyFilterCondition();
        currencyFilterCondition.setCurrencyType(currencyType);
        currencyFilterCondition.setMdCurrencyReferEntity(isMdCurrencyReferEntity);
        currencyFilterCondition.setCustomCurrency(customCurrencies);
        builderCondition.setCurrencyFilterCondition(currencyFilterCondition);
        return builderCondition;
    }

    protected List<String> getCustomCurrencies(ProcessExecuteEnv envParam, ExecuteTaskParam executeTaskParam) {
        String dimensionName;
        DimensionValue dimensionValue;
        ArrayList<String> customCurrencies = new ArrayList<String>();
        JtableContext context = executeTaskParam.getContext();
        Map dimensionSet = context.getDimensionSet();
        DataDimension currencyDataDimension = this.helper.dimensionHelper.getCurrencyDataDimension(envParam.getTaskKey());
        if (currencyDataDimension != null && (dimensionValue = (DimensionValue)dimensionSet.get(dimensionName = this.helper.dimensionHelper.getDimensionName(currencyDataDimension))) != null) {
            customCurrencies.add(dimensionValue.getValue());
        }
        return customCurrencies;
    }

    private List<String> getCustomCurrencies(ProcessExecuteEnv envParam, BatchExecuteTaskParam batchExecuteTaskParam) {
        String dimensionName;
        DimensionValue dimensionValue;
        ArrayList<String> customCurrencies = new ArrayList<String>();
        JtableContext context = batchExecuteTaskParam.getContext();
        Map dimensionSet = context.getDimensionSet();
        DataDimension currencyDataDimension = this.helper.dimensionHelper.getCurrencyDataDimension(envParam.getTaskKey());
        if (currencyDataDimension != null && (dimensionValue = (DimensionValue)dimensionSet.get(dimensionName = this.helper.dimensionHelper.getDimensionName(currencyDataDimension))) != null) {
            customCurrencies.add(dimensionValue.getValue());
        }
        return customCurrencies;
    }

    private List<String> getCalcFormulaSchemeKeys(JSONObject eventConfig) {
        ArrayList<String> calcFormulaSchemeKeys = new ArrayList<String>();
        if (eventConfig.has(attr_key_formulaScheme)) {
            JSONArray jsonArray = eventConfig.getJSONArray(attr_key_formulaScheme);
            for (int i = 0; i < jsonArray.length(); ++i) {
                calcFormulaSchemeKeys.add(jsonArray.getString(i));
            }
        }
        return calcFormulaSchemeKeys;
    }
}

