/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.monitor.State
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo
 *  com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.DataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.bpm.upload.utils.I18nUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IExecuteTimeSetting
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessExecuteTimeHelper
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRunPara
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.dataentry;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobRunner;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.DataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.I18nUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nr.workflow2.converter.entity.IProcessActionButton;
import com.jiuqi.nr.workflow2.converter.entity.ProcessActionButton;
import com.jiuqi.nr.workflow2.converter.todo.ApplyreturnOperateJob;
import com.jiuqi.nr.workflow2.converter.unittree.DataEntryUnitTreeStateConverter;
import com.jiuqi.nr.workflow2.converter.utils.ConverterUtil;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.execute.runtime.IExecuteTimeSetting;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessExecuteTimeHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DataEntryFlowServiceConverter
extends DataentryFlowService {
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    public DeSetTimeProvide deSetTimeProvide;
    @Autowired
    public SingleRejectFormActions singleRejectFormActions;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IProcessExecuteTimeHelper executeTimeHelper;
    @Autowired
    private ConverterUtil converterUtil;
    @Autowired
    private Workflow2EngineCompatibleCollector workflow2EngineCompatibleCollector;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public WorkflowConfig queryWorkflowConfig(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.queryWorkflowConfig(formSchemeKey);
        }
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(formSchemeDefine.getTaskKey());
        WorkflowConfig config = new WorkflowConfig();
        config.setFlowStarted(workflowSettingsDO.isWorkflowEnable());
        config.setWorkFlowType(this.transferWorkflowObjectType(workflowSettingsDO.getWorkflowObjectType()));
        config.setWorkflowEntities(this.formSchemeService.getReportEntityKeys(formSchemeKey));
        return config;
    }

    public List<WorkflowDataInfo> queryWorkflowDataInfo(WorkflowDataBean workflowData) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(workflowData.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.queryWorkflowDataInfo(workflowData);
        }
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : formSchemeDefine.getDw();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(workflowData.getDimSet(), formSchemeDefine);
        String unitKey = dimensionValueSet.getValue(dimensionName).toString();
        String period = dimensionValueSet.getValue("DATATIME").toString();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dimensionName);
        unitOneDim.setDimensionKey(entityId);
        unitOneDim.setDimensionValue(unitKey);
        oneDims.add(unitOneDim);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(workflowData.getFormKey());
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(workflowData.getFormGroupKey());
            oneDims.add(formGroupOneDim);
        }
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        oneRunPara.setPeriod(period);
        oneRunPara.setReportDimensions(oneDims);
        JSONObject envVariable = new JSONObject();
        envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(workflowData.getFormKey())));
        oneRunPara.setEnvVariables(envVariable);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
        List<IProcessActionButton> actionButtons = this.queryActionButtons((IProcessRunPara)oneRunPara, businessKey, formSchemeDefine);
        HashMap<String, List> filterMap = new HashMap<String, List>();
        for (IProcessActionButton processActionButton : actionButtons) {
            List buttonList = filterMap.computeIfAbsent(processActionButton.getTaskId(), k -> new ArrayList());
            buttonList.add(processActionButton);
        }
        ArrayList<WorkflowDataInfo> result = new ArrayList<WorkflowDataInfo>();
        WorkFlowType workFlowType = this.transferWorkflowObjectType(workflowObjectType);
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(formSchemeDefine.getTaskKey());
        Workflow2EngineCompatibleExtend extensionByEngine = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
        IProcessInstance instance = this.processQueryService.queryInstances((IProcessRunPara)oneRunPara, businessKey);
        String workflowNode = instance == null ? "" : instance.getCurrentUserTask();
        for (Map.Entry entry : filterMap.entrySet()) {
            String taskId = (String)entry.getKey();
            List buttonList = (List)entry.getValue();
            boolean disabled = buttonList.isEmpty() || !((IProcessActionButton)buttonList.get(0)).isEnable();
            WorkflowDataInfo workflowDataInfo = new WorkflowDataInfo();
            workflowDataInfo.setTaskId(taskId);
            workflowDataInfo.setTaskCode(workflowNode);
            workflowDataInfo.setWorkFlowType(workFlowType);
            workflowDataInfo.setActions(this.buildActionList(buttonList, extensionByEngine, workflowNode, formSchemeDefine));
            workflowDataInfo.setDisabled(disabled);
            result.add(workflowDataInfo);
        }
        return result;
    }

    private List<IProcessActionButton> queryActionButtons(IProcessRunPara runEnvPara, IBusinessKey businessKey, FormSchemeDefine formSchemeDefine) {
        ArrayList<IProcessActionButton> actionButtons = new ArrayList<IProcessActionButton>();
        List processTasks = this.processQueryService.queryCurrentTask(runEnvPara, businessKey);
        IProcessInstance instance = this.processQueryService.queryInstances(runEnvPara, businessKey);
        IExecuteTimeSetting executeTimeSetting = this.executeTimeHelper.getExecuteTimeSetting(formSchemeDefine, businessKey.getBusinessObject().getDimensions());
        for (IProcessTask task : processTasks) {
            List userActions = task.getActions();
            for (IUserAction action : userActions) {
                actionButtons.add(this.getProcessActionButton(instance, task, action, executeTimeSetting));
            }
        }
        return actionButtons;
    }

    private ProcessActionButton getProcessActionButton(IProcessInstance instance, IProcessTask task, IUserAction action, IExecuteTimeSetting executeTimeSetting) {
        ProcessActionButton actionButton = new ProcessActionButton();
        actionButton.setTaskId(task.getId());
        actionButton.setCode(action.getCode());
        actionButton.setTitle(action.getAlias());
        actionButton.setIcon(action.getIcon());
        actionButton.setProperties(action.getProperties());
        actionButton.setEnable(executeTimeSetting.isInTimeRange());
        actionButton.setPreviousEvents(action.getPreviousEvents());
        actionButton.setPostEvents(action.getPostEvents());
        return actionButton;
    }

    public Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo(BatchWorkflowDataBean workflowData) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(workflowData.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.batchWorkflowDataInfo(workflowData);
        }
        return super.batchWorkflowDataInfo(workflowData);
    }

    public ActionStateBean queryUnitState(DataEntryParam dataEntryParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.queryUnitState(dataEntryParam);
        }
        ProcessRunPara processRunPara = new ProcessRunPara();
        processRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        processRunPara.setPeriod(dataEntryParam.getDim().getValue("DATATIME").toString());
        IProcessStatus processStatus = this.processQueryService.queryUnitState((IProcessRunPara)processRunPara, this.buildUnitBusinessKey(formSchemeDefine, dataEntryParam.getDim()));
        if (processStatus == null) {
            return null;
        }
        ActionStateBean actionStateBean = new ActionStateBean();
        actionStateBean.setCode(DataEntryUnitTreeStateConverter.transferToOldStateCode(processStatus.getCode()));
        actionStateBean.setTitile(processStatus.getAlias());
        actionStateBean.setIcon(processStatus.getIcon());
        return actionStateBean;
    }

    public ActionStateBean queryReportState(DataEntryParam dataEntryParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.queryReportState(dataEntryParam);
        }
        IProcessStatus processStatus = this.getFormOrFormGroupProcessStatus(dataEntryParam);
        if (processStatus == null) {
            return null;
        }
        ActionStateBean actionStateBean = new ActionStateBean();
        actionStateBean.setCode(DataEntryUnitTreeStateConverter.transferToOldStateCode(processStatus.getCode()));
        actionStateBean.setTitile(processStatus.getAlias());
        actionStateBean.setIcon(processStatus.getIcon());
        return actionStateBean;
    }

    public DeWorkflowBean getDeWorkflow(DataEntryParam dataEntryParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.getDeWorkflow(dataEntryParam);
        }
        DeWorkflowBean deWorkflowBean = new DeWorkflowBean();
        WorkflowDataBean workflowDataBean = new WorkflowDataBean();
        workflowDataBean.setFormSchemeKey(dataEntryParam.getFormSchemeKey());
        workflowDataBean.setDimSet(dataEntryParam.getDim());
        workflowDataBean.setFormKey(dataEntryParam.getFormKey());
        workflowDataBean.setFormGroupKey(dataEntryParam.getGroupKey());
        workflowDataBean.setFormKeys(dataEntryParam.getFormKeys());
        workflowDataBean.setFormGroupKeys(dataEntryParam.getGroupKeys());
        List<WorkflowDataInfo> workflowDataInfos = this.queryWorkflowDataInfo(workflowDataBean);
        deWorkflowBean.setWorkflowDataInfoList(workflowDataInfos);
        deWorkflowBean.setActionState(this.queryState(dataEntryParam));
        deWorkflowBean.setReadOnlyBean(this.buildReadOnlyBean(dataEntryParam));
        return deWorkflowBean;
    }

    public ActionState queryState(DataEntryParam dataEntryParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.queryState(dataEntryParam);
        }
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        ActionState actionState = new ActionState();
        actionState.setUnitState(this.queryUnitState(dataEntryParam));
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            actionState.setFormState(this.queryReportState(dataEntryParam));
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            actionState.setGroupState(this.queryReportState(dataEntryParam));
        }
        return actionState;
    }

    public ActionParam actionParam(BatchExecuteParam executeParam, Map<String, DimensionValue> dimensionSet) {
        return super.actionParam(executeParam, dimensionSet);
    }

    public boolean isExistData(String taskKey) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return super.isExistData(taskKey);
        }
        return super.isExistData(taskKey);
    }

    private WorkFlowType transferWorkflowObjectType(WorkflowObjectType workflowObjectType) {
        switch (workflowObjectType) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                return WorkFlowType.ENTITY;
            }
            case FORM: {
                return WorkFlowType.FORM;
            }
            case FORM_GROUP: {
                return WorkFlowType.GROUP;
            }
        }
        return null;
    }

    private List<WorkflowAction> buildActionList(List<IProcessActionButton> buttonList, Workflow2EngineCompatibleExtend workflow2EngineCompatibleExtend, String workflowNode, FormSchemeDefine formSchemeDefine) {
        ArrayList<WorkflowAction> actionList = new ArrayList<WorkflowAction>();
        for (IProcessActionButton button : buttonList) {
            WorkflowAction action = new WorkflowAction();
            action.setCode(button.getCode());
            action.setTitle(button.getTitle());
            action.setIcon(button.getIcon());
            action.setActionParam(this.converterUtil.buildActionParam(button.getCode(), button.getProperties(), workflowNode, workflow2EngineCompatibleExtend, formSchemeDefine));
            actionList.add(action);
        }
        return actionList;
    }

    private IBusinessKey buildUnitBusinessKey(FormSchemeDefine formSchemeDefine, DimensionValueSet dimSet) {
        DimensionCombinationImpl dimensionCombination = new DimensionCombinationImpl();
        List reportDimensions = this.reportDimensionHelper.getAllReportDimensions(formSchemeDefine.getTaskKey());
        for (DataDimension dimension : reportDimensions) {
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionCombination.setDWValue(dimensionName, dimension.getDimKey(), dimSet.getValue(dimensionName));
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionCombination.setValue(dimensionName, dimension.getDimKey(), dimSet.getValue(dimensionName));
                continue;
            }
            dimensionCombination.setValue(dimensionName, dimension.getDimKey(), dimSet.getValue(dimensionName));
        }
        return new BusinessKey(formSchemeDefine.getTaskKey(), (IBusinessObject)new DimensionObject((DimensionCombination)dimensionCombination));
    }

    private IProcessStatus getFormOrFormGroupProcessStatus(DataEntryParam dataEntryParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(dataEntryParam.getFormSchemeKey());
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : formSchemeDefine.getDw();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dataEntryParam.getDim(), formSchemeDefine);
        String unitKey = dimensionValueSet.getValue(dimensionName).toString();
        String period = dimensionValueSet.getValue("DATATIME").toString();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dimensionName);
        unitOneDim.setDimensionKey(entityId);
        unitOneDim.setDimensionValue(unitKey);
        oneDims.add(unitOneDim);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(dataEntryParam.getFormKey());
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(dataEntryParam.getGroupKey());
            oneDims.add(formGroupOneDim);
        }
        ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
        oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
        oneRunPara.setPeriod(period);
        oneRunPara.setReportDimensions(oneDims);
        JSONObject envVariable = new JSONObject();
        envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(dataEntryParam.getFormKey())));
        oneRunPara.setEnvVariables(envVariable);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
        return this.processQueryService.queryInstanceState((IProcessRunPara)oneRunPara, businessKey);
    }

    private ReadOnlyBean buildReadOnlyBean(DataEntryParam dataEntryParam) {
        MsgReturn compareSetTime;
        IProcessStatus processStatus = this.getFormOrFormGroupProcessStatus(dataEntryParam);
        ReadOnlyBean readOnlyBean = new ReadOnlyBean();
        boolean isChinese = I18nUtil.isChinese();
        String msg = "";
        boolean readOnly = false;
        if (processStatus == null || processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.WRITEABLE)) {
            msg = isChinese ? "\u53ef\u5199" : "writable";
            readOnly = true;
        } else if (processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.READONLY)) {
            msg = "\u53ea\u8bfb";
        } else if (processStatus.getDataAccessStatus().equals((Object)IProcessStatus.DataAccessStatus.UNREADABLE)) {
            msg = "\u4e0d\u53ef\u8bfb";
        }
        if (readOnly && (compareSetTime = this.deSetTimeProvide.compareSetTime(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim())).isDisabled()) {
            readOnly = false;
            msg = compareSetTime.getMsg();
        }
        readOnlyBean.setMsg(msg);
        readOnlyBean.setReadOnly(readOnly);
        return readOnlyBean;
    }

    private String i18nDes(String code, boolean chinese) {
        String msg = "";
        if (chinese) {
            if (code.equals(UploadState.UPLOADED.toString())) {
                msg = "\u6570\u636e\u5df2\u4e0a\u62a5\u4e0d\u53ef\u7f16\u8f91";
            } else if (code.equals(UploadState.CONFIRMED.toString())) {
                msg = "\u6570\u636e\u5df2\u786e\u8ba4\u4e0d\u53ef\u7f16\u8f91";
            } else if (code.equals(UploadState.SUBMITED.toString())) {
                msg = "\u6570\u636e\u5df2\u9001\u5ba1\u4e0d\u53ef\u7f16\u8f91";
            }
        } else if (code.equals(UploadState.UPLOADED.toString())) {
            msg = "Data has been uploaded and cannot be edited";
        } else if (code.equals(UploadState.CONFIRMED.toString())) {
            msg = "Data has been confirmed and cannot be edited";
        } else if (code.equals(UploadState.SUBMITED.toString())) {
            msg = "Data has been submited and cannot be edited";
        }
        return msg;
    }

    public CompleteMsg batchApplyReturnExecuteTask(BatchExecuteParam executeParam) {
        CompleteMsg result;
        block12: {
            ImmediatelyJobInfo jobInfo;
            if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(executeParam.getTaskId())) {
                return super.batchApplyReturnExecuteTask(executeParam);
            }
            result = new CompleteMsg();
            if (executeParam.getTaskId() == null || executeParam.getTaskId() == "" || executeParam.getPeriod() == null || executeParam.getPeriod() == "" || executeParam.getUnits() == null || executeParam.getUnits().isEmpty()) {
                result.setMsg("\u8bf7\u6c42\u53c2\u6570\u9519\u8bef\u3002");
                return result;
            }
            TaskDefine task = this.runTimeViewController.getTask(executeParam.getTaskId());
            if (task == null) {
                result.setMsg("\u4efb\u52a1\u4e0d\u5b58\u5728\uff1a" + executeParam.getTaskId());
                return result;
            }
            ProcessOneRunPara runPara = new ProcessOneRunPara();
            runPara.setTaskKey(executeParam.getTaskId());
            runPara.setPeriod(executeParam.getPeriod());
            HashSet<ProcessOneDim> dims = new HashSet<ProcessOneDim>();
            ProcessOneDim mdDim = new ProcessOneDim(this.getDimensionName(task.getDw()), task.getDw(), (String)executeParam.getUnits().get(0));
            dims.add(mdDim);
            if (executeParam.getFormKeys() != null && !executeParam.getFormKeys().isEmpty()) {
                ProcessOneDim formDim = new ProcessOneDim(EProcessDimensionName.PROCESS_FORM.dimName, null, (String)executeParam.getFormKeys().get(0));
                dims.add(formDim);
            }
            if (executeParam.getGroupKeys() != null && !executeParam.getGroupKeys().isEmpty()) {
                ProcessOneDim formGroupDim = new ProcessOneDim(EProcessDimensionName.PROCESS_GROUP.dimName, null, (String)executeParam.getGroupKeys().get(0));
                dims.add(formGroupDim);
            }
            runPara.setReportDimensions(dims);
            IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(runPara);
            ApplyreturnOperateJob.ApplyreturnOperateJobParameter jobParameter = new ApplyreturnOperateJob.ApplyreturnOperateJobParameter();
            jobParameter.setTaskKey(executeParam.getTaskId());
            jobParameter.setBusinessObject(businessKey.getBusinessObject());
            jobParameter.setAgree(executeParam.isAgreed());
            jobParameter.setComment(executeParam.getComment());
            ApplyreturnOperateJob job = new ApplyreturnOperateJob(jobParameter);
            String jobId = null;
            try {
                jobId = ImmediatelyJobRunner.getInstance().commit((AbstractRealTimeJob)job);
            }
            catch (JobExecutionException e) {
                result.setMsg(e.getMessage());
                return result;
            }
            do {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException e) {
                    result.setMsg(e.getMessage());
                    break block12;
                }
            } while ((jobInfo = ImmediatelyJobRunner.getInstance().getJobInfo(jobId)).getState() != State.FINISHED.getValue());
            if (jobInfo.getResult() == 100) {
                result.setSucceed(true);
            } else {
                result.setMsg(jobInfo.getResultMessage());
            }
        }
        return result;
    }

    private String getDimensionName(String entityId) {
        return this.entityMetaService.getDimensionName(entityId);
    }
}

