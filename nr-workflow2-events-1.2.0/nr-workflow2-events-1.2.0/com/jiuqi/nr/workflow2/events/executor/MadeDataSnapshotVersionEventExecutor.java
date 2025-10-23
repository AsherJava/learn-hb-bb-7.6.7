/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext
 *  com.jiuqi.nr.snapshot.input.CreateSnapshotContext
 *  com.jiuqi.nr.snapshot.input.CreateSnapshotInfo
 *  com.jiuqi.nr.snapshot.output.SnapshotInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.CreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.CreateSnapshotInfo;
import com.jiuqi.nr.snapshot.output.SnapshotInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.events.executor.AbstractActionEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.helper.CurrencyFilterCondition;
import com.jiuqi.nr.workflow2.events.helper.DimensionBuilderCondition;
import com.jiuqi.nr.workflow2.events.helper.UnitFilterCondition;
import com.jiuqi.nr.workflow2.events.monitor.ProcessAsyncTaskMonitor;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class MadeDataSnapshotVersionEventExecutor
extends AbstractActionEventExecutor {
    protected EventDependentServiceHelper helper;
    protected static final String attr_key_filter_formula = "unitRangeFormula";

    public MadeDataSnapshotVersionEventExecutor(JSONObject eventJsonConfig, EventDependentServiceHelper helper) {
        super(eventJsonConfig);
        this.helper = helper;
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        if (!this.canCreateVersion(envParam, businessKey)) {
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        List<String> processRangeFormKeys = this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKey);
        List<String> snapShotRangeFormKeys = this.getRangeFormKeys(businessKey.getTask(), envParam.getPeriod(), processRangeFormKeys);
        if (!snapShotRangeFormKeys.isEmpty()) {
            FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKey.getTask(), envParam.getPeriod());
            DimensionCollectionBuilder dimensionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKey, new DimensionBuilderCondition(this.getCurrencyFilterCondition(envParam)));
            DimensionCollection collection = dimensionBuilder.getCollection();
            CreateSnapshotContext snapshotParam = new CreateSnapshotContext();
            snapshotParam.setDimensionCollection(collection);
            snapshotParam.setFormSchemeKey(formScheme.getKey());
            snapshotParam.setFormKeys(snapShotRangeFormKeys);
            snapshotParam.setDescribe(this.getDescription(envParam));
            snapshotParam.setTitle(this.getSnapShotTitle(businessKey.getTask(), businessKey.getBusinessObject().getDimensions(), envParam));
            snapshotParam.setIsAutoCreate(true);
            ProcessAsyncTaskMonitor asyncTaskMonitor = new ProcessAsyncTaskMonitor(monitor);
            this.helper.dataSnapshotService.createSnapshot(snapshotParam, (AsyncTaskMonitor)asyncTaskMonitor);
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
    }

    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        ProcessExecuteEnv envParam = this.getEnvParam(actionArgs);
        UnitFilterCondition unitFilterCondition = this.getUnitFilterCondition(envParam, businessKeyCollection);
        if (unitFilterCondition != null && unitFilterCondition.getUnitRangeKeys().isEmpty()) {
            return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
        }
        List<String> processRangeFormKeys = this.helper.eventExecuteDimensionBuilder.getProcessRangeFormKeys(businessKeyCollection);
        List<String> snapShotRangeFormKeys = this.getRangeFormKeys(businessKeyCollection.getTask(), envParam.getPeriod(), processRangeFormKeys);
        FormSchemeDefine formScheme = this.helper.runtimeParamHelper.getFormScheme(businessKeyCollection.getTask(), envParam.getPeriod());
        DimensionBuilderCondition dimensionBuilderCondition = new DimensionBuilderCondition(unitFilterCondition, this.getCurrencyFilterCondition(envParam));
        DimensionCollectionBuilder dimensionBuilder = this.helper.eventExecuteDimensionBuilder.toDimensionCollectionBuilder(envParam, businessKeyCollection, dimensionBuilderCondition);
        DimensionCollection collection = dimensionBuilder.getCollection();
        ArrayList<CreateSnapshotInfo> createSnapshotInfos = new ArrayList<CreateSnapshotInfo>();
        List dimensionCombinations = collection.getDimensionCombinations();
        for (DimensionCombination combination : dimensionCombinations) {
            CreateSnapshotInfo snapshotInfo = new CreateSnapshotInfo();
            snapshotInfo.setFormSchemeKey(formScheme.getKey());
            snapshotInfo.setFormKeys(snapShotRangeFormKeys);
            snapshotInfo.setDescribe(this.getDescription(envParam));
            snapshotInfo.setTitle(this.getSnapShotTitle(formScheme.getTaskKey(), combination, envParam));
            snapshotInfo.setDimensionCombination(combination);
            snapshotInfo.setAutoCreate(true);
            createSnapshotInfos.add(snapshotInfo);
        }
        BatchCreateSnapshotContext batchCreateSnapshotContext = new BatchCreateSnapshotContext();
        batchCreateSnapshotContext.setTaskKey(formScheme.getTaskKey());
        batchCreateSnapshotContext.setCreateSnapshotInfos(createSnapshotInfos);
        ProcessAsyncTaskMonitor asyncTaskMonitor = new ProcessAsyncTaskMonitor(monitor);
        this.helper.dataSnapshotService.batchCreateSnapshot(batchCreateSnapshotContext, (AsyncTaskMonitor)asyncTaskMonitor);
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.NOT_IMPACT_REPORTING_CHECK);
    }

    private boolean canCreateVersion(ProcessExecuteEnv envParam, IBusinessKey businessKey) throws Exception {
        String entityRowFilter = this.getEntityRowFilter(this.eventJsonConfig);
        if (entityRowFilter != null) {
            ExecutorContext executorContext = this.helper.entityQueryHelper.makeExecuteContext(envParam.getTaskKey(), envParam.getPeriod());
            IEntityQuery entityQuery = this.helper.entityQueryHelper.makeIEntityQuery(envParam.getTaskKey(), envParam.getPeriod());
            entityQuery.setExpression(entityRowFilter);
            IEntityTable entityTable = this.helper.entityQueryHelper.getIEntityTable(entityQuery, executorContext);
            String entityRowKey = businessKey.getBusinessObject().getDimensions().getDWDimensionValue().getValue().toString();
            return entityTable.findByEntityKey(entityRowKey) != null;
        }
        return true;
    }

    protected CurrencyFilterCondition getCurrencyFilterCondition(ProcessExecuteEnv envParam) throws Exception {
        CurrencyType currencyType = CurrencyType.ALL;
        ArrayList<String> customCurrencies = new ArrayList<String>();
        boolean isMdCurrencyReferEntity = this.helper.dimensionHelper.isMdCurrencyReferEntity(envParam.getTaskKey(), envParam.getPeriod());
        CurrencyFilterCondition currencyFilterCondition = new CurrencyFilterCondition();
        currencyFilterCondition.setCurrencyType(currencyType);
        currencyFilterCondition.setMdCurrencyReferEntity(isMdCurrencyReferEntity);
        currencyFilterCondition.setCustomCurrency(customCurrencies);
        return currencyFilterCondition;
    }

    protected UnitFilterCondition getUnitFilterCondition(ProcessExecuteEnv envParam, IBusinessKeyCollection businessKeyCollection) throws Exception {
        String entityRowFilter = this.getEntityRowFilter(this.eventJsonConfig);
        if (entityRowFilter != null) {
            Map<String, Set<String>> mergeDimensionCombinations = this.helper.eventExecuteDimensionBuilder.mergeDimensionCombinations(businessKeyCollection);
            String unitDimensionName = this.helper.dimensionHelper.getUnitDimensionName(envParam.getTaskKey());
            ArrayList unitRangeKeys = new ArrayList(mergeDimensionCombinations.get(unitDimensionName));
            IEntityQuery entityQuery = this.helper.entityQueryHelper.makeIEntityQuery(envParam.getTaskKey(), envParam.getPeriod());
            entityQuery.getMasterKeys().setValue(unitDimensionName, unitRangeKeys);
            entityQuery.setExpression(entityRowFilter);
            ExecutorContext executorContext = this.helper.entityQueryHelper.makeExecuteContext(envParam.getTaskKey(), envParam.getPeriod());
            IEntityTable entityTable = this.helper.entityQueryHelper.getIEntityTable(entityQuery, executorContext);
            List allRows = entityTable.getAllRows();
            List<String> filterUnitRangeKeys = allRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            UnitFilterCondition unitFilterCondition = new UnitFilterCondition();
            unitFilterCondition.setUnitRangeKeys(filterUnitRangeKeys);
            unitFilterCondition.setFilterType(UnitFilterCondition.FilterType.by_unit_range);
            return unitFilterCondition;
        }
        return null;
    }

    protected List<String> getRangeFormKeys(String taskKey, String period, List<String> processRangeFormKeys) {
        ArrayList<String> formKeys = new ArrayList<String>();
        List formDefines = processRangeFormKeys.isEmpty() ? this.helper.runtimeParamHelper.listFormByFormScheme(taskKey, period) : this.helper.runtimeParamHelper.listFormByFormScheme(taskKey, period, processRangeFormKeys);
        for (FormDefine formDefine : formDefines) {
            if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)) continue;
            formKeys.add(formDefine.getKey());
        }
        return formKeys;
    }

    private String getDescription(ProcessExecuteEnv envParam) {
        String processActionTitle = envParam.getActionTitle();
        return "\u6d41\u7a0b\u81ea\u52a8\u751f\u6210" + processActionTitle + "\u5feb\u7167 ";
    }

    private String getSnapShotTitle(String taskKey, DimensionCombination dimensionCombination, ProcessExecuteEnv envParam) {
        String processActionTitle = envParam.getActionTitle();
        List snapshotInfoList = this.helper.dataSnapshotService.querySnapshot(dimensionCombination, taskKey);
        HashMap<Integer, Integer> TitleMap = new HashMap<Integer, Integer>();
        String titleHalf = processActionTitle + "_V";
        StringBuilder title = new StringBuilder().append(titleHalf);
        for (SnapshotInfo dataSnapshotInfo : snapshotInfoList) {
            if (!dataSnapshotInfo.getSnapshot().getTitle().startsWith(titleHalf)) continue;
            int nums = Integer.parseInt(dataSnapshotInfo.getSnapshot().getTitle().replace(titleHalf, ""));
            TitleMap.put(nums, 1);
        }
        for (int i = 1; i < Integer.MAX_VALUE; ++i) {
            if (TitleMap.containsKey(i)) continue;
            title.append(i);
            break;
        }
        return title.toString();
    }

    private String getEntityRowFilter(JSONObject eventJsonConfig) {
        if (eventJsonConfig.has(attr_key_filter_formula)) {
            String formula = eventJsonConfig.getString(attr_key_filter_formula);
            return StringUtils.isNotEmpty((String)formula) ? formula : null;
        }
        return null;
    }
}

