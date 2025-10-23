/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserManagerService
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder
 *  com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam
 *  com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult
 *  com.jiuqi.nr.bpm.setting.pojo.ShowResult
 *  com.jiuqi.nr.bpm.setting.service.impl.ShowProcess
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.exception.UserTaskNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateFormResultSet
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper
 *  com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.dataentry.track;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserManagerService;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.service.impl.ShowProcess;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.exception.UserTaskNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateFormResultSet;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.form.reject.ext.service.IFormRejectJudgeHelper;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class WorkflowTrackConverter
extends ShowProcess {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected IFormRejectJudgeHelper judgeHelper;
    @Autowired
    protected IFormRejectQueryService formRejectQueryService;
    @Autowired
    private Workflow2EngineCompatibleCollector workflow2EngineCompatibleCollector;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public ShowResult showWorkflow(ShowNodeParam nodeParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(nodeParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.showWorkflow(nodeParam);
        }
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : formSchemeDefine.getDw();
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(formSchemeDefine.getTaskKey());
        ShowResult result = new ShowResult();
        if (workflowEngine.equals("jiuqi.nr.default")) {
            List processOperations;
            result.setDefaultWorkflow(true);
            String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
            WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
            DimensionValueSet dimensionValueSet = this.buildDimensionValueSet(nodeParam.getDimensionSetMap(), formSchemeDefine);
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
                formOneDim.setDimensionValue(nodeParam.getFormKey());
                oneDims.add(formOneDim);
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                ProcessOneDim formGroupOneDim = new ProcessOneDim();
                formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
                formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
                formGroupOneDim.setDimensionValue(nodeParam.getGroupKey());
                oneDims.add(formGroupOneDim);
            }
            ProcessOneRunPara oneRunPara = new ProcessOneRunPara();
            oneRunPara.setTaskKey(formSchemeDefine.getTaskKey());
            oneRunPara.setPeriod(period);
            oneRunPara.setReportDimensions(oneDims);
            JSONObject envVariable = new JSONObject();
            envVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)JavaBeanUtils.toJSONStr((Object)new FormRejectExecuteParam(nodeParam.getFormKey())));
            envVariable.put(IProcessFormRejectAttrKeys.use_form_reject_query.attrKey, true);
            oneRunPara.setEnvVariables(envVariable);
            IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneRunPara);
            try {
                processOperations = this.processQueryService.queryProcessOperations((IProcessRunPara)oneRunPara, businessKey);
            }
            catch (InstanceNotFoundException e2) {
                return new ShowResult();
            }
            ArrayList<ShowNodeResult> nodeList = new ArrayList<ShowNodeResult>();
            for (IProcessOperation processOperation : processOperations) {
                IUserTask userTask = this.processMetaDataService.getUserTaskOrDefault(formSchemeDefine.getTaskKey(), processOperation.getFromNode());
                IUserAction userAction = this.processMetaDataService.getActionOrDefault(formSchemeDefine.getTaskKey(), processOperation.getFromNode(), processOperation.getAction());
                ShowNodeResult nodeResult = new ShowNodeResult();
                nodeResult.setNodeId(processOperation.getFromNode());
                nodeResult.setNodeName(userTask.getAlias());
                nodeResult.setUser(this.getUserName(processOperation.getOperator()));
                nodeResult.setActionCode(processOperation.getAction());
                nodeResult.setActionName(userAction.getAlias());
                nodeResult.setReturnType(this.getReturnTypeTitle(nodeParam.getFormSchemeKey(), processOperation.getOperateType()));
                nodeResult.setDesc(processOperation.getComment());
                nodeResult.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(processOperation.getOperateTime().getTime()));
                nodeList.add(nodeResult);
            }
            if (workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR)) {
                nodeList.addAll(this.buildFormRejectOptRecords((IProcessRunPara)oneRunPara, businessKey, formSchemeDefine));
            }
            IProcessInstance instance = this.processQueryService.queryInstances((IProcessRunPara)oneRunPara, businessKey);
            IUserTask userTask = this.processMetaDataService.getUserTaskOrDefault(formSchemeDefine.getTaskKey(), instance.getCurrentUserTask());
            if (userTask != null) {
                List<Object> actorList;
                try {
                    actorList = this.processQueryService.queryMatchingActors((IProcessRunPara)oneRunPara, businessKey).stream().map(this::getUserLoginName).filter(e -> this.userManagerService.findByUsername(e).isPresent()).map(e -> {
                        Optional targetUser = this.userManagerService.findByUsername(e);
                        return targetUser.isPresent() ? ((User)targetUser.get()).getFullname() : "\u672a\u627e\u5230\u8be5\u7528\u6237\u4fe1\u606f";
                    }).collect(Collectors.toList());
                }
                catch (UserTaskNotFoundException e3) {
                    actorList = new ArrayList();
                }
                String actorShowStr = String.join((CharSequence)",", actorList);
                ShowNodeResult waitForExecuteNode = new ShowNodeResult();
                waitForExecuteNode.setNodeId(userTask.getCode());
                waitForExecuteNode.setNodeName(userTask.getAlias());
                if (userTask.getCode().equals("tsk_audit_after_confirm")) {
                    IUserTask auditNode = this.processMetaDataService.getUserTask(formSchemeDefine.getTaskKey(), "tsk_audit");
                    waitForExecuteNode.setNodeId(auditNode.getCode());
                    waitForExecuteNode.setNodeName(auditNode.getAlias());
                }
                waitForExecuteNode.setActors(actorShowStr);
                nodeList.add(waitForExecuteNode);
            }
            result.setNodeList(nodeList);
        }
        return result;
    }

    public List<ShowNodeResult> buildFormRejectOptRecords(IProcessRunPara runEnvPara, IBusinessKey businessKey, FormSchemeDefine formSchemeDefine) {
        ArrayList<ShowNodeResult> nodeList = new ArrayList<ShowNodeResult>();
        IUserTask userTask = this.processMetaDataService.getUserTask(businessKey.getTask(), "tsk_audit");
        IUserAction userAction = this.processMetaDataService.queryAction(businessKey.getTask(), userTask.getCode(), "act_reject");
        List resultSets = this.formRejectQueryService.queryUnitOperateForms(runEnvPara.getTaskKey(), runEnvPara.getPeriod(), businessKey.getBusinessObject().getDimensions());
        List formDefines = this.runTimeViewController.listFormByFormScheme(formSchemeDefine.getKey());
        HashMap formDefineMap = new HashMap();
        formDefines.forEach(formDefine -> formDefineMap.put(formDefine.getKey(), formDefine));
        for (IRejectOperateFormResultSet resultSet : resultSets) {
            ShowNodeResult nodeResult = new ShowNodeResult();
            nodeResult.setNodeId(userTask.getCode());
            nodeResult.setNodeName(userTask.getAlias());
            nodeResult.setUser(this.getUserName(resultSet.getOptUser()));
            nodeResult.setActionCode(userAction.getCode());
            nodeResult.setActionName("\u5355\u8868\u9000\u56de");
            nodeResult.setDesc(resultSet.getOptComment());
            nodeResult.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getOptTime().getTime()));
            nodeResult.setRejectForms(resultSet.getOptFromIds().stream().map(formId -> ((FormDefine)formDefineMap.get(formId)).getTitle()).collect(Collectors.toList()));
            nodeList.add(nodeResult);
        }
        return nodeList;
    }

    public String getUserName(String operatorId) {
        Optional user = this.userManagerService.find(operatorId);
        if (!user.isPresent()) {
            Optional systemUser = this.systemUserService.find(operatorId);
            if (systemUser.isPresent()) {
                return ((SystemUser)systemUser.get()).getName();
            }
            return operatorId;
        }
        return ((User)user.get()).getFullname();
    }

    public String getUserLoginName(String operatorId) {
        Optional user = this.userManagerService.find(operatorId);
        if (!user.isPresent()) {
            Optional systemUser = this.systemUserService.find(operatorId);
            if (systemUser.isPresent()) {
                return ((SystemUser)systemUser.get()).getName();
            }
            return operatorId;
        }
        return ((User)user.get()).getName();
    }

    private DimensionValueSet buildDimensionValueSet(Map<String, DimensionValue> dimensionSetMap, FormSchemeDefine formSchemeDefine) {
        DimensionCollection dimensionCollection = this.workFlowDimensionBuilder.buildDimensionCollection(formSchemeDefine.getTaskKey(), dimensionSetMap);
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dims.add(dimensionCombination.toDimensionValueSet());
        }
        DimensionValueSet dimension = this.dimensionUtil.mergeDimensionValueSet(dims, formSchemeDefine.getKey());
        return this.dimensionUtil.fliterDimensionValueSet(dimension, formSchemeDefine);
    }

    public String getReturnTypeTitle(String formSchemeKey, String returnType) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.getReturnTypeTitle(formSchemeKey, returnType);
        }
        String title = "";
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(formSchemeDefine.getTaskKey());
        Workflow2EngineCompatibleExtend extensionByEngine = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
        String returnTypeEntityId = extensionByEngine.getReturnType(formSchemeDefine.getTaskKey());
        if (returnTypeEntityId != null) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(returnTypeEntityId);
            IEntityTable entityTable = this.getBaseDataEntityTable(entityDefine.getId());
            List allRows = entityTable.getAllRows();
            for (IEntityRow row : allRows) {
                if (!row.getCode().equals(returnType)) continue;
                title = row.getTitle();
                break;
            }
        }
        return title;
    }

    private IEntityTable getBaseDataEntityTable(String entityId) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception var8) {
            Exception e = var8;
            throw new RuntimeException(e);
        }
    }
}

