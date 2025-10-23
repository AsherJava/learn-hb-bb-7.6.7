/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatusTemplate
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatusTemplate;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.CompleteDependentType;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionAffect;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.WFMonitorCheckResult;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.form.reject.ext.event.FormRejectCheckItem;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEventBeanUtils;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEventExecutor;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventFinishedResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateInfo;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StepByStepFormRejectEventExecutor
implements IFormRejectEventExecutor {
    private FormSchemeDefine currFormSchemeDefine;
    protected IProcessExecutePara envParam;
    private final Map<IFormObject, FormRejectStatus> formRecordEntityMap = new HashMap<IFormObject, FormRejectStatus>();

    public StepByStepFormRejectEventExecutor(IProcessExecutePara envParam) {
        this.envParam = envParam;
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKey businessKey) throws Exception {
        IBusinessObject businessObject = businessKey.getBusinessObject();
        this.currFormSchemeDefine = IFormRejectEventBeanUtils.getIProcessRuntimeParamHelper().getFormScheme(this.envParam.getTaskKey(), this.envParam.getPeriod());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)this.envParam, AuthorityType.None);
        List<FormRejectCheckItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)this.envParam, operateResultSet, businessObject, entityTable);
        if (!uploadCheckItems.isEmpty()) {
            operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_UN_PASS, uploadCheckItems.get(0).getDetailMessage(), uploadCheckItems));
            return new EventFinishedResult(EventExecutionStatus.STOP, EventExecutionAffect.IMPACT_REPORTING_CHECK, CompleteDependentType.PARENT_EXECUTION_FIRST);
        }
        operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(WFMonitorCheckResult.CHECK_PASS));
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK, CompleteDependentType.PARENT_EXECUTION_FIRST);
    }

    @Override
    public IEventFinishedResult executionEvent(IProcessAsyncMonitor monitor, IOperateResultSet operateResultSet, IActionArgs actionArgs, IBusinessKeyCollection businessKeyCollection) throws Exception {
        this.currFormSchemeDefine = IFormRejectEventBeanUtils.getIProcessRuntimeParamHelper().getFormScheme(this.envParam.getTaskKey(), this.envParam.getPeriod());
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)this.envParam, AuthorityType.None);
        this.updateLevelAndParentRowIndex(businessKeyCollection, entityTable, operateResultSet);
        Map level2RowIndexes = operateResultSet.getLevel2RowIndexes();
        List<Integer> sortLevelByAsc = this.getSortLevelList(level2RowIndexes.keySet());
        for (Integer level : sortLevelByAsc) {
            Set rowIndexes = (Set)level2RowIndexes.get(level);
            for (Integer rowIndex : rowIndexes) {
                IBusinessObject businessObject = operateResultSet.findBusinessObject(rowIndex);
                List<FormRejectCheckItem> uploadCheckItems = this.getBusinessObjectCheckResult((IProcessRunPara)this.envParam, operateResultSet, businessObject, entityTable);
                WFMonitorCheckResult checkResult = uploadCheckItems.isEmpty() ? WFMonitorCheckResult.CHECK_PASS : WFMonitorCheckResult.CHECK_UN_PASS;
                operateResultSet.setOperateResult(businessObject, (IEventOperateInfo)new EventOperateInfo(checkResult, "stepByStepUpload", uploadCheckItems));
            }
        }
        return new EventFinishedResult(EventExecutionStatus.FINISH, EventExecutionAffect.IMPACT_REPORTING_CHECK, CompleteDependentType.PARENT_EXECUTION_FIRST);
    }

    protected List<FormRejectCheckItem> getBusinessObjectCheckResult(IProcessRunPara runEnvPara, IOperateResultSet operateResultSet, IBusinessObject businessObject, IEntityTable entityTable) throws Exception {
        FormRejectStatus formRejectStatus;
        ArrayList<FormRejectCheckItem> checkErrorItems = new ArrayList<FormRejectCheckItem>();
        IEntityRow entityRow = entityTable.findByEntityKey(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
        IEntityRow parentEntityRow = entityTable.findByEntityKey(entityRow.getParentEntityKey());
        IBusinessObject parentBusinessObject = this.getParentBusinessObject(businessObject, parentEntityRow);
        if (parentBusinessObject == null) {
            return checkErrorItems;
        }
        boolean hasCheckPassed = operateResultSet.hasCheckPassed(parentBusinessObject);
        WFMonitorCheckResult checkStatus = operateResultSet.getCheckStatus(parentBusinessObject);
        if (operateResultSet.containsBusinessObject(parentBusinessObject) && hasCheckPassed && checkStatus != WFMonitorCheckResult.UN_CHECK) {
            return checkErrorItems;
        }
        if (operateResultSet.containsBusinessObject(parentBusinessObject) && !hasCheckPassed && checkStatus != WFMonitorCheckResult.UN_CHECK) {
            checkErrorItems.add(this.buildCheckItemInfo(parentEntityRow, "", "\u4e0a\u7ea7\u672a\u9000\u56de\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
            return checkErrorItems;
        }
        IProcessQueryService processQueryService = IFormRejectEventBeanUtils.getIProcessQueryService();
        DimensionObject unitBusinessObject = new DimensionObject(parentBusinessObject.getDimensions());
        BusinessKey unitBusinessKey = new BusinessKey(runEnvPara.getTaskKey(), (IBusinessObject)unitBusinessObject);
        IProcessStatus parentStatus = processQueryService.queryInstanceState(runEnvPara, (IBusinessKey)unitBusinessKey);
        if (parentStatus == null) {
            return checkErrorItems;
        }
        if (IProcessStatus.DataReportStatus.REPORTED == parentStatus.getDataReportStatus() || IProcessStatus.DataReportStatus.CONFIRMED == parentStatus.getDataReportStatus()) {
            checkErrorItems.add(this.buildCheckItemInfo(parentEntityRow, parentStatus.getAlias(), "\u4e0a\u7ea7\u672a\u9000\u56de\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
            return checkErrorItems;
        }
        if (ProcessStatusTemplate.REJECTED.getCode().equals(parentStatus.getCode()) && FormRejectStatus.locked == (formRejectStatus = this.getFormRejectStatus((IFormObject)parentBusinessObject))) {
            checkErrorItems.add(this.buildCheckItemInfo(parentEntityRow, parentStatus.getAlias(), "\u4e0a\u7ea7\u5df2\u4fdd\u62a4\uff0c\u672c\u7ea7\u4e0d\u53ef\u9000\u56de\uff01\uff01"));
            return checkErrorItems;
        }
        return checkErrorItems;
    }

    protected FormRejectCheckItem buildCheckItemInfo(IEntityRow entityRow, String workflowState, String detailMessage) {
        FormRejectCheckItem checkItemInfo = new FormRejectCheckItem();
        checkItemInfo.setUnitId(entityRow.getEntityKeyData());
        checkItemInfo.setUnitCode(entityRow.getCode());
        checkItemInfo.setUnitTitle(entityRow.getTitle());
        checkItemInfo.setWorkflowState(workflowState);
        checkItemInfo.setDetailMessage(detailMessage);
        return checkItemInfo;
    }

    protected IBusinessObject getParentBusinessObject(IBusinessObject businessObject, IEntityRow parentEntityRow) throws Exception {
        if (parentEntityRow == null) {
            return null;
        }
        IProcessDimensionsBuilder processDimensionsBuilder = IFormRejectEventBeanUtils.getIProcessDimensionsBuilder();
        IFormObject parentBusinessObject = this.toBusinessObject(businessObject, parentEntityRow);
        ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(parentBusinessObject.getDimensions());
        ArrayList<String> formKeys = new ArrayList<String>();
        formKeys.add(parentBusinessObject.getFormKey());
        IDimensionObjectMapping dimensionObjectMapping = processDimensionsBuilder.processDimToFormConditionMap(this.currFormSchemeDefine, (DimensionCollection)dimensionCollection, formKeys);
        if (dimensionObjectMapping.getObject(parentBusinessObject.getDimensions()).contains(parentBusinessObject.getFormKey())) {
            return parentBusinessObject;
        }
        return null;
    }

    protected List<Integer> getSortLevelList(Set<Integer> levels) {
        ArrayList<Integer> sortLevelByDesc = new ArrayList<Integer>(levels);
        sortLevelByDesc.sort(Comparator.naturalOrder());
        return sortLevelByDesc;
    }

    protected void updateLevelAndParentRowIndex(IBusinessKeyCollection businessKeyCollection, IEntityTable entityTable, IOperateResultSet operateResultSet) {
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            operateResultSet.setLevel(businessObject, this.getBusinessObjectLevel(businessObject, entityTable));
            IEntityRow entityRow = entityTable.findByEntityKey(businessObject.getDimensions().getDWDimensionValue().getValue().toString());
            IEntityRow parentRow = entityTable.findByEntityKey(entityRow.getParentEntityKey());
            if (parentRow == null) continue;
            IFormObject parentBusinessObject = this.toBusinessObject(businessObject, parentRow);
            operateResultSet.setParentRowIndex(businessObject, operateResultSet.findBusinessObjectIndex((IBusinessObject)parentBusinessObject));
        }
    }

    protected Integer getBusinessObjectLevel(IBusinessObject businessObject, IEntityTable entityTable) {
        String unitId = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
        IEntityRow entityRow = entityTable.findByEntityKey(unitId);
        if (entityRow != null) {
            return entityRow.getParentsEntityKeyDataPath().length;
        }
        return null;
    }

    protected IFormObject toBusinessObject(IBusinessObject businessObject, IEntityRow entityRow) {
        IFormObject formObject = (IFormObject)businessObject;
        DimensionCombination dimensions = formObject.getDimensions();
        FixedDimensionValue dwDimensionValue = dimensions.getDWDimensionValue();
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        combination.setDWValue(dwDimensionValue.getName(), dwDimensionValue.getEntityID(), (Object)entityRow.getEntityKeyData());
        for (String dimensionName : dimensions.getNames()) {
            if (dimensionName.equals(dwDimensionValue.getName())) continue;
            FixedDimensionValue dimensionValue = dimensions.getFixedDimensionValue(dimensionName);
            combination.setValue(dimensionValue.getName(), dimensionValue.getEntityID(), dimensionValue.getValue());
        }
        return new FormObject((DimensionCombination)combination, formObject.getFormKey());
    }

    protected IEntityTable getEntityTable(IProcessRunPara runEnvPara, AuthorityType authorityType) throws Exception {
        String taskKey = runEnvPara.getTaskKey();
        String period = runEnvPara.getPeriod();
        IProcessRuntimeParamHelper runtimeParamHelper = IFormRejectEventBeanUtils.getIProcessRuntimeParamHelper();
        IProcessEntityQueryHelper processEntityQueryHelper = IFormRejectEventBeanUtils.getIProcessEntityQueryHelper();
        IEntityDefine entityDefine = runtimeParamHelper.getProcessEntityDefinition(taskKey);
        IEntityQuery entityQuery = processEntityQueryHelper.makeIEntityQuery(taskKey, period);
        entityQuery.setAuthorityOperations(authorityType);
        EntityViewDefine entityViewDefine = runtimeParamHelper.buildEntityView(taskKey, period, entityDefine.getId());
        entityQuery.setEntityView(entityViewDefine);
        ExecutorContext executorContext = processEntityQueryHelper.makeExecuteContext(taskKey, period);
        return processEntityQueryHelper.getIEntityTable(entityQuery, executorContext);
    }

    protected FormRejectStatus getFormRejectStatus(IFormObject formObject) {
        if (this.formRecordEntityMap.containsKey(formObject)) {
            return this.formRecordEntityMap.get(formObject);
        }
        Map<IFormObject, IRejectFormRecordEntity> recordEntityMap = IFormRejectEventBeanUtils.getIFormRejectQueryService().queryRejectFormRecordsMap(this.envParam.getTaskKey(), this.envParam.getPeriod(), formObject.getDimensions());
        for (Map.Entry<IFormObject, IRejectFormRecordEntity> entry : recordEntityMap.entrySet()) {
            this.formRecordEntityMap.put(entry.getKey(), entry.getValue().getStatus());
        }
        if (!this.formRecordEntityMap.containsKey(formObject)) {
            this.formRecordEntityMap.put(formObject, FormRejectStatus.locked);
        }
        return this.formRecordEntityMap.get(formObject);
    }
}

