/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable
 *  com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateColumn
 *  com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult
 *  com.jiuqi.nr.workflow2.service.result.ProcessExecuteResult
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.form.reject.ext.service;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.EventExecutionStatus;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventFinishedResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IEventOperateInfo;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IOperateResultSet;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEvent;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEventExecutor;
import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEventExecutorFactory;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectRunTimeService;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.common.IProcessCustomVariable;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateColumn;
import com.jiuqi.nr.workflow2.service.execute.runtime.EventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateColumn;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;
import com.jiuqi.nr.workflow2.service.result.ProcessExecuteResult;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormRejectExecuteService {
    @Autowired
    protected IProcessMetaDataService processMetaDataService;
    @Autowired
    private IFormRejectQueryService rejectQueryService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessRuntimeParamHelper processRuntimeParamHelper;
    @Autowired
    private IFormRejectRunTimeService rejectRunTimeService;

    public IProcessExecuteResult executeProcess(IProcessExecutePara executePara, DimensionCombination combination, IProcessAsyncMonitor monitor) throws Exception {
        FormRejectExecuteParam rejectExecuteParam = IFormRejectJudgeHelper.getFormRejectExecuteParam(executePara.getCustomVariable());
        BusinessKey businessKey = new BusinessKey(executePara.getTaskKey(), (IBusinessObject)new FormObject(combination, rejectExecuteParam.getFormId()));
        IUserAction userAction = this.processMetaDataService.queryAction(executePara.getTaskKey(), "tsk_audit", "act_reject");
        EventOperateResult resultManager = new EventOperateResult(businessKey.getBusinessObject());
        List<IFormRejectEvent> allEnableEvents = IFormRejectEventExecutorFactory.getAllEnableEvents(executePara);
        for (IFormRejectEvent event : allEnableEvents) {
            EventOperateColumn eventOperateColumn = new EventOperateColumn(event.getEventId(), event.getEventTitle());
            IOperateResultSet operateResultSet = resultManager.getOperateResultSet((IEventOperateColumn)eventOperateColumn);
            IFormRejectEventExecutor eventExecutor = event.getEventExecutor(executePara);
            IEventFinishedResult eventFinishedResult = eventExecutor.executionEvent(monitor, operateResultSet, (IActionArgs)executePara.getCustomVariable(), (IBusinessKey)businessKey);
            eventOperateColumn.setAffectStatus(eventFinishedResult.getAffectStatus());
            eventOperateColumn.setCompleteDependentType(eventFinishedResult.getCompleteDependentType());
            if (EventExecutionStatus.STOP != eventFinishedResult.getFinishStatus()) continue;
            IEventOperateInfo operateResult = operateResultSet.getOperateResult(businessKey.getBusinessObject());
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, operateResult.getCheckResultMessage());
        }
        this.executeFormRejectProcess((IProcessRunPara)executePara, (IBusinessKeyCollection)new OperateBusinessKeyCollection(executePara.getTaskKey(), resultManager.allCheckPassBusinessObjects()));
        return new ProcessExecuteResult(ProcessExecuteStatus.SUCCESS, userAction);
    }

    public IProcessExecuteResult executeProcess(IProcessExecutePara executePara, DimensionCollection dimensionCollection, IProcessAsyncMonitor monitor) throws Exception {
        FormRejectExecuteParam rejectExecuteParam = IFormRejectJudgeHelper.getFormRejectExecuteParam(executePara.getCustomVariable());
        IUserAction userAction = this.processMetaDataService.queryAction(executePara.getTaskKey(), "tsk_audit", "act_reject");
        IBusinessKeyCollection businessKeyCollection = this.toBusinessKeyCollection(executePara, rejectExecuteParam, dimensionCollection);
        EventOperateResult resultManager = new EventOperateResult(businessKeyCollection.getBusinessObjects());
        List<IFormRejectEvent> allEnableEvents = IFormRejectEventExecutorFactory.getAllEnableEvents(executePara);
        for (IFormRejectEvent event : allEnableEvents) {
            EventOperateColumn eventOperateColumn = new EventOperateColumn(event.getEventId(), event.getEventTitle());
            IOperateResultSet operateResultSet = resultManager.getOperateResultSet((IEventOperateColumn)eventOperateColumn);
            IFormRejectEventExecutor eventExecutor = event.getEventExecutor(executePara);
            IEventFinishedResult eventFinishedResult = eventExecutor.executionEvent(monitor, operateResultSet, (IActionArgs)executePara.getCustomVariable(), businessKeyCollection);
            eventOperateColumn.setAffectStatus(eventFinishedResult.getAffectStatus());
            eventOperateColumn.setCompleteDependentType(eventFinishedResult.getCompleteDependentType());
            if (EventExecutionStatus.STOP != eventFinishedResult.getFinishStatus()) continue;
            return new ProcessExecuteResult(ProcessExecuteStatus.ENV_CHECK_ERROR, event.getEventTitle() + "\u5931\u8d25\uff01\uff01");
        }
        this.executeFormRejectProcess((IProcessRunPara)executePara, (IBusinessKeyCollection)new OperateBusinessKeyCollection(executePara.getTaskKey(), resultManager.allCheckPassBusinessObjects()));
        return new ProcessExecuteResult(ProcessExecuteStatus.SUCCESS, userAction);
    }

    protected IBusinessKeyCollection toBusinessKeyCollection(IProcessExecutePara executePara, FormRejectExecuteParam rejectExecuteParam, DimensionCollection dimensionCollection) {
        List<String> rangeFormKeys = this.getRejectFormIds(rejectExecuteParam);
        FormSchemeDefine formScheme = this.processRuntimeParamHelper.getFormScheme(executePara.getTaskKey(), executePara.getPeriod());
        IDimensionObjectMapping toFormMapInfo = this.processDimensionsBuilder.processDimToFormDefinesMap(formScheme, dimensionCollection, rangeFormKeys);
        return new BusinessKeyCollection(executePara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)toFormMapInfo));
    }

    private List<String> getRejectFormIds(FormRejectExecuteParam rejectExecuteParam) {
        List<String> rejectFormIds;
        ArrayList<String> rangeFormIds = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)rejectExecuteParam.getFormId())) {
            rangeFormIds.add(rejectExecuteParam.getFormId());
        }
        if ((rejectFormIds = rejectExecuteParam.getRejectFormIds()) != null && !rejectFormIds.isEmpty()) {
            for (String formId : rejectFormIds) {
                if (rangeFormIds.contains(formId)) continue;
                rangeFormIds.add(formId);
            }
        }
        return rangeFormIds;
    }

    protected void executeFormRejectProcess(IProcessRunPara runPara, IBusinessKeyCollection businessKeyCollection) {
        if (businessKeyCollection.getBusinessObjects().size() > 0) {
            ArrayList<IFormObject> optFormObjects = new ArrayList<IFormObject>();
            List combinations = businessKeyCollection.getBusinessObjects().getDimensions().getDimensionCombinations();
            for (IBusinessObject businessObject : businessKeyCollection.getBusinessObjects()) {
                optFormObjects.add((IFormObject)businessObject);
            }
            Map<IFormObject, IRejectFormRecordEntity> recordEntityMap = this.rejectQueryService.queryRejectFormRecordsMap(runPara.getTaskKey(), runPara.getPeriod(), combinations);
            String taskKey = runPara.getTaskKey();
            String period = runPara.getPeriod();
            IProcessCustomVariable actionArgs = runPara.getCustomVariable();
            ArrayList<IFormObject> updateFormObjects = new ArrayList<IFormObject>();
            ArrayList<IFormObject> insertFormObjects = new ArrayList<IFormObject>();
            ArrayList<RejectOperateRecordEntity> operateRecordEntities = new ArrayList<RejectOperateRecordEntity>();
            String operateId = UUID.randomUUID().toString();
            for (IFormObject formObject : optFormObjects) {
                if (!recordEntityMap.containsKey(formObject)) {
                    insertFormObjects.add(formObject);
                } else {
                    updateFormObjects.add(formObject);
                }
                RejectOperateRecordEntity operateRecordEntity = new RejectOperateRecordEntity();
                operateRecordEntity.setFormObject(formObject);
                operateRecordEntity.setOptId(operateId);
                operateRecordEntity.setOptUser(NpContextHolder.getContext().getUserId());
                operateRecordEntity.setOptTime(new Date());
                operateRecordEntity.setOptComment(actionArgs.getString("COMMENT"));
                operateRecordEntities.add(operateRecordEntity);
            }
            this.rejectRunTimeService.insertFormRejectRecords(taskKey, period, insertFormObjects, FormRejectStatus.rejected.value);
            this.rejectRunTimeService.updateFormRejectRecords(taskKey, period, updateFormObjects, FormRejectStatus.rejected.value);
            this.rejectRunTimeService.insertOperateFormRecords(taskKey, period, operateRecordEntities);
        }
    }
}

