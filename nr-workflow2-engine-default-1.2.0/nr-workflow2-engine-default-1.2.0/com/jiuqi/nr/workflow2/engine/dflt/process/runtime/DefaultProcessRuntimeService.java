/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.DummyProgressMonitor
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.examine.facade.DataClearParamObj
 *  com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IInstanceIdOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.InstanceIdOperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.ProcessStatusWithOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.bi.monitor.DummyProgressMonitor;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearExtendService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatus;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.InstanceNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IActionArgs;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyDependencies;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IInstanceIdOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BizObjectOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.InstanceIdOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.OperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.ProcessStatusWithOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.BusinessKeyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ProcessInstance;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ProcessOperation;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ProcessTask;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ActionRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ApplyReturnActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.ApplyReturnActionRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.RetriveActionCollector;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.RetriveActionRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.StartActionRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.StartBatchActionRunner;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusBatchMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.QueryModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.RestructModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceJoinOperationQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceWithOperation;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessRepositoryUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoUtils;
import com.jiuqi.nr.workflow2.engine.dflt.utils.LogUtils;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DefaultProcessRuntimeService
implements IProcessRuntimeService,
IParamDeployFinishListener,
IDataSchemeDataClearExtendService {
    private static final String PROCESSENGINEID = "jiuqi.nr.default";
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IFormConditionAccessService formAccessService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultProcessDefinitionService processDefinitionService;
    @Autowired
    private ProcessInstanceLockManager instanceLockManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ITempTableManager tempTableManager;
    @Autowired
    private BusinessKeyUtil businessKeyUtil;
    @Autowired
    private TransactionUtil transactionUtil;
    public static final int TABLESELECT_IST = 1;
    public static final int TABLESELECT_OPT = 2;
    public static final int TABLESELECT_IST_OPT = 3;

    private ProcessInstanceQuery getInstanceQuery(TaskDefine task, FormSchemeDefine formScheme) {
        QueryModel.ProcessInstanceQueryModel istQueryModel = QueryModel.ProcessInstanceQueryModel.build(this.dataModelService, this.entityMetaService, this.dataSchemeService, task, formScheme);
        return new ProcessInstanceQuery(this.jdbcTemplate, this.tempTableManager, istQueryModel);
    }

    private ProcessOperationQuery getOperationQuery(TaskDefine task, FormSchemeDefine formScheme) {
        QueryModel.ProcessOperationQueryModel optQueryModel = QueryModel.ProcessOperationQueryModel.build(this.dataModelService, formScheme);
        return new ProcessOperationQuery(this.jdbcTemplate, optQueryModel);
    }

    private static boolean taskIsMatchVersion2_0(TaskDefine task) {
        return "2.0".equals(task.getVersion());
    }

    private boolean workflowEngineIsMatch(WorkflowSettingsDO workflowSettings) {
        return workflowSettings != null && workflowSettings.getWorkflowEngine().equals(PROCESSENGINEID);
    }

    @EventListener
    public void onWorkFlowSettingChanged(WorkflowSettingsSaveEvent event) {
        TaskDefine task = this.viewController.getTask(event.getTaskId());
        if (task == null) {
            return;
        }
        if (!DefaultProcessRuntimeService.taskIsMatchVersion2_0(task)) {
            return;
        }
        List fomrSchemes = this.viewController.listFormSchemeByTask(event.getTaskId());
        WorkflowSettingsDO newWorkflowSettings = this.workflowSettingsService.queryWorkflowSettings(event.getTaskId());
        if (!this.workflowEngineIsMatch(newWorkflowSettings)) {
            return;
        }
        this.processDefinitionService.onProcessChanged(newWorkflowSettings.getWorkflowDefine());
        boolean isWorkflowObjectChanged = false;
        for (FormSchemeDefine formScheme : fomrSchemes) {
            isWorkflowObjectChanged = isWorkflowObjectChanged || this.maintainDataModel(task, formScheme, newWorkflowSettings, 3);
        }
        if (isWorkflowObjectChanged) {
            TodoUtils.clearTodoByTask(task.getKey());
        }
    }

    private boolean maintainDataModel(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO newWorkflowSettings, int tableSelect) {
        boolean isWorkflowObjectChanged = false;
        if (tableSelect == 1 || tableSelect == 3) {
            try {
                DataModelService.ProcessInstanceModelService instanceModelService = new DataModelService.ProcessInstanceModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
                RestructModel restructModel = instanceModelService.compare(task, formScheme, newWorkflowSettings);
                if (restructModel.getRestructMode() == RestructModel.RestructMode.CREATE) {
                    instanceModelService.create(task, formScheme, newWorkflowSettings);
                } else {
                    DesignColumnModelDefine[] removeColumns;
                    DesignColumnModelDefine[] addColumns = restructModel.getColumnModelsToAdd();
                    if (addColumns != null) {
                        for (DesignColumnModelDefine column : addColumns) {
                            if (!column.getCode().equals("IST_FORMKEY") && !column.getCode().equals("IST_FORMGROUPKEY")) continue;
                            isWorkflowObjectChanged = true;
                            break;
                        }
                    }
                    if (!isWorkflowObjectChanged && (removeColumns = restructModel.getColumnModelsToRemove()) != null) {
                        for (DesignColumnModelDefine column : removeColumns) {
                            if (!column.getCode().equals("IST_FORMKEY") && !column.getCode().equals("IST_FORMGROUPKEY")) continue;
                            isWorkflowObjectChanged = true;
                            break;
                        }
                    }
                    if (isWorkflowObjectChanged) {
                        if (instanceModelService.exists(formScheme)) {
                            instanceModelService.drop(formScheme);
                        }
                        instanceModelService.create(task, formScheme, newWorkflowSettings);
                    } else {
                        instanceModelService.maintainBeforeRestruct(restructModel);
                        instanceModelService.maintainAfterRestruct(restructModel);
                    }
                }
            }
            catch (Exception e) {
                LogUtils.LOGGER.error("maintain instance DataModel fail.", e);
            }
        }
        if (tableSelect == 2 || tableSelect == 3) {
            try {
                DataModelService.ProcessOperationModelService operationModelService = new DataModelService.ProcessOperationModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
                RestructModel optRestructModel = operationModelService.compare(task, formScheme, newWorkflowSettings);
                if (optRestructModel.getRestructMode() == RestructModel.RestructMode.CREATE) {
                    operationModelService.create(task, formScheme, newWorkflowSettings);
                } else if (isWorkflowObjectChanged) {
                    if (operationModelService.exists(formScheme)) {
                        operationModelService.drop(formScheme);
                    }
                    operationModelService.create(task, formScheme, newWorkflowSettings);
                } else {
                    operationModelService.maintainBeforeRestruct(optRestructModel);
                    operationModelService.maintainAfterRestruct(optRestructModel);
                }
            }
            catch (Exception e) {
                LogUtils.LOGGER.error("maintain operation DataModel fail.", e);
            }
        }
        return isWorkflowObjectChanged;
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        TaskDefine task = this.viewController.getTask(define.getTaskKey());
        if (task == null) {
            throw new RuntimeException("\u7cfb\u7edf\u9519\u8bef\uff1a\u5728\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u540e\u4e8b\u4ef6\u4e2d\u65e0\u6cd5\u67e5\u8be2\u5230\u4efb\u52a1\u3002");
        }
        if (!DefaultProcessRuntimeService.taskIsMatchVersion2_0(task)) {
            return;
        }
        WorkflowSettingsDO newWorkflowSettings = this.workflowSettingsService.queryWorkflowSettings(define.getTaskKey());
        if (!this.workflowEngineIsMatch(newWorkflowSettings)) {
            return;
        }
        this.createDataModel(task, define, newWorkflowSettings);
    }

    private void createDataModel(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO newWorkflowSettings) {
        try {
            DataModelService.ProcessInstanceModelService instanceModelService = new DataModelService.ProcessInstanceModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
            DataModelService.ProcessOperationModelService operationModelService = new DataModelService.ProcessOperationModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
            instanceModelService.create(task, formScheme, newWorkflowSettings);
            operationModelService.create(task, formScheme, newWorkflowSettings);
        }
        catch (Exception e) {
            LogUtils.LOGGER.error("maintain DataModel fail.", e);
        }
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        DataModelService.ProcessOperationModelService operationModelService;
        DataModelService.ProcessInstanceModelService instanceModelService = new DataModelService.ProcessInstanceModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
        if (instanceModelService.exists(define)) {
            instanceModelService.drop(define);
        }
        if ((operationModelService = new DataModelService.ProcessOperationModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService)).exists(define)) {
            operationModelService.drop(define);
        }
        TodoUtils.clearTodoByFormScheme(define.getKey());
    }

    public void doClear(DataClearParamObj clearParam) {
        for (String taskKey : clearParam.getTaskKey()) {
            TaskDefine task = this.viewController.getTask(taskKey);
            List fomrSchemes = this.viewController.listFormSchemeByTask(taskKey);
            LogUtils.LOGGER.info("\u4e0a\u62a5\u6d41\u7a0b\u6570\u636e\u5220\u9664\u3002\u4efb\u52a1\uff1a" + taskKey + "|" + task.getTaskCode() + "\uff0c\u7528\u6237\uff1a" + NpContextHolder.getContext().getUserId() + "|" + NpContextHolder.getContext().getUserName());
            for (FormSchemeDefine formScheme : fomrSchemes) {
                ProcessOperationQuery optQuery;
                ProcessInstanceQuery istQuery = this.getInstanceQuery(task, formScheme);
                if (istQuery.getQueryModel() != null) {
                    istQuery.deleteAll();
                }
                if ((optQuery = this.getOperationQuery(task, formScheme)).getQueryModel() == null) continue;
                optQuery.deleteAll();
            }
            TodoUtils.clearTodoByTask(task.getKey());
        }
    }

    @Transactional
    public IProcessInstance startProcessInstance(String processDefinitionId, IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        try (StartActionRunner starter = new StartActionRunner();){
            starter.setActor(IActor.fromContext());
            starter.setBusinessKey(rtBizKey);
            starter.setInstanceQuery(this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme()));
            starter.setOperationQuery(this.getOperationQuery(rtBizKey.getTask(), rtBizKey.getFormScheme()));
            starter.setProcessDefinitionId(processDefinitionId);
            starter.setProcessDefinitionService(this.processDefinitionService);
            starter.setTransactionUtil(this.transactionUtil);
            starter.setRunTimeViewController(this.viewController);
            starter.setFormConditionAccessService(this.formAccessService);
            starter.setProcessInstanceLockManager(this.instanceLockManager);
            ProcessInstanceDO instanceDO = starter.run();
            ProcessInstance processInstance = new ProcessInstance(instanceDO);
            return processInstance;
        }
    }

    public IBizObjectOperateResult<Void> batchStartProcessInstance(String processDefinitionId, IBusinessKeyCollection businessKeys) {
        return this.batchStartProcessInstance(processDefinitionId, businessKeys, (IProgressMonitor)new DummyProgressMonitor());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional
    public IBizObjectOperateResult<Void> batchStartProcessInstance(String processDefinitionId, IBusinessKeyCollection businessKeys, IProgressMonitor monitor) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        try (StartBatchActionRunner starter = new StartBatchActionRunner();){
            starter.setActor(IActor.fromContext());
            starter.setBusinessKeys(rtBizKeys);
            starter.setInstanceQuery(this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme()));
            starter.setOperationQuery(this.getOperationQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme()));
            starter.setProcessDefinitionId(processDefinitionId);
            starter.setProcessDefinitionService(this.processDefinitionService);
            starter.setTransactionUtil(this.transactionUtil);
            starter.setProgressMonitor(monitor);
            starter.setFormConditionAccessService(this.formAccessService);
            starter.setRunTimeViewController(this.viewController);
            starter.setProcessInstanceLockManager(this.instanceLockManager);
            IBizObjectOperateResult<Void> iBizObjectOperateResult = starter.run();
            return iBizObjectOperateResult;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void completeTask(IBusinessKey businessKey, String taskId, String actionCode, IActor actor, IActionArgs args) {
        ActionRunner actionRunner;
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = istQuery.queryInstance(businessKey);
        if (instance == null) {
            throw new InstanceNotFoundException(PROCESSENGINEID, businessKey);
        }
        switch (actionCode) {
            case "act_retrieve": {
                actionRunner = new RetriveActionRunner();
                break;
            }
            case "act_apply_reject": {
                actionRunner = new ApplyReturnActionRunner();
                break;
            }
            default: {
                actionRunner = new ActionRunner();
            }
        }
        try {
            actionRunner.setInstanceId(instance.getId());
            actionRunner.setTaskId(taskId);
            actionRunner.setBusinessKey(rtBizKey);
            actionRunner.setActor(actor);
            actionRunner.setActionCode(actionCode);
            actionRunner.setActionArgs(args);
            actionRunner.setInstanceQuery(istQuery);
            actionRunner.setOperationQuery(this.getOperationQuery(rtBizKey.getTask(), rtBizKey.getFormScheme()));
            actionRunner.setProcessDefinitionService(this.processDefinitionService);
            actionRunner.setProcessInstanceLockManager(this.instanceLockManager);
            actionRunner.setTransactionUtil(this.transactionUtil);
            actionRunner.setFormConditionAccessService(this.formAccessService);
            actionRunner.setRunTimeViewController(this.viewController);
            actionRunner.run();
        }
        finally {
            actionRunner.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void completeTask(String taskKey, String period, String instanceId, String taskId, String actionCode, IActor actor, IActionArgs args) {
        ActionRunner actionRunner;
        TaskDefine task = this.viewController.getTask(taskKey);
        if (task == null) {
            throw new IllegalArgumentException("\u4efb\u52d9 " + taskKey + " \u4e0d\u5b58\u5728\u3002");
        }
        SchemePeriodLinkDefine schemePeriodLink = null;
        try {
            schemePeriodLink = this.viewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("\u6642\u671f " + period + " \u4e0a\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002");
        }
        if (schemePeriodLink == null) {
            throw new IllegalArgumentException("\u6642\u671f " + period + " \u4e0a\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002");
        }
        FormSchemeDefine formScheme = this.viewController.getFormScheme(schemePeriodLink.getSchemeKey());
        if (formScheme == null) {
            throw new IllegalArgumentException("\u62a5\u8868\u65b9\u6848 " + schemePeriodLink.getSchemeKey() + " \u4e0d\u5b58\u5728\u3002");
        }
        ProcessInstanceQuery istQuery = this.getInstanceQuery(task, formScheme);
        ProcessInstanceDO instance = istQuery.queryInstance(instanceId);
        if (instance == null) {
            throw new InstanceNotFoundException(PROCESSENGINEID, instanceId);
        }
        WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(taskId);
        RuntimeBusinessKey rtBusinessKey = new RuntimeBusinessKey(instance.getBusinessKey(), task, formScheme, workflowSettings);
        switch (actionCode) {
            case "act_retrieve": {
                actionRunner = new RetriveActionRunner();
                break;
            }
            case "act_apply_reject": {
                actionRunner = new ApplyReturnActionRunner();
                break;
            }
            default: {
                actionRunner = new ActionRunner();
            }
        }
        try {
            actionRunner.setInstanceId(instanceId);
            actionRunner.setTaskId(taskId);
            actionRunner.setBusinessKey(rtBusinessKey);
            actionRunner.setActor(actor);
            actionRunner.setActionCode(actionCode);
            actionRunner.setActionArgs(args);
            actionRunner.setInstanceQuery(istQuery);
            actionRunner.setOperationQuery(this.getOperationQuery(task, formScheme));
            actionRunner.setProcessDefinitionService(this.processDefinitionService);
            actionRunner.setProcessInstanceLockManager(this.instanceLockManager);
            actionRunner.setTransactionUtil(this.transactionUtil);
            actionRunner.setFormConditionAccessService(this.formAccessService);
            actionRunner.setRunTimeViewController(this.viewController);
            actionRunner.run();
        }
        finally {
            actionRunner.close();
        }
    }

    public IBizObjectOperateResult<Void> batchCompleteTask(IBusinessKeyCollection bizKeyCollection, String userTaskCode, String actionCode, IActor actor, IActionArgs args, IBusinessKeyDependencies dependencies) {
        return this.batchCompleteTask(bizKeyCollection, userTaskCode, actionCode, actor, args, dependencies, (IProgressMonitor)new DummyProgressMonitor());
    }

    /*
     * Exception decompiling
     */
    public IBizObjectOperateResult<Void> batchCompleteTask(IBusinessKeyCollection businessKeys, String userTaskCode, String actionCode, IActor actor, IActionArgs args, IBusinessKeyDependencies dependencies, IProgressMonitor monitor) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public IBizObjectOperateResult<Void> queryCanCompleteInstance(IBusinessKeyCollection businessKeys, String userTaskCode, String actionCode, IActor actor, IActionArgs args) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public IInstanceIdOperateResult<Void> batchCompleteTask(String taskKey, String period, Collection<String> instanceIds, String userTaskCode, String actionCode, IActor actor, IActionArgs args) {
        return this.batchCompleteTask(taskKey, period, instanceIds, userTaskCode, actionCode, actor, args, (IProgressMonitor)new DummyProgressMonitor());
    }

    public IInstanceIdOperateResult<Void> batchCompleteTask(String taskKey, String period, Collection<String> instanceIds, String userTaskCode, String actionCode, IActor actor, IActionArgs args, IProgressMonitor monitor) {
        Object actionPath;
        if (instanceIds == null || instanceIds.size() == 0) {
            return IInstanceIdOperateResult.emptyResult();
        }
        String taskName = "batchcomplete-workflow-instance";
        monitor.startTask("batchcomplete-workflow-instance", new int[]{20, 15, 15, 20, 20, 10});
        TaskDefine task = this.viewController.getTask(taskKey);
        if (task == null) {
            throw new IllegalArgumentException("\u4efb\u52d9 " + taskKey + " \u4e0d\u5b58\u5728\u3002");
        }
        SchemePeriodLinkDefine schemePeriodLink = null;
        try {
            schemePeriodLink = this.viewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("\u6642\u671f " + period + " \u4e0a\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002");
        }
        if (schemePeriodLink == null) {
            throw new IllegalArgumentException("\u6642\u671f " + period + " \u4e0a\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002");
        }
        FormSchemeDefine formScheme = this.viewController.getFormScheme(schemePeriodLink.getSchemeKey());
        if (formScheme == null) {
            throw new IllegalArgumentException("\u62a5\u8868\u65b9\u6848 " + schemePeriodLink.getSchemeKey() + " \u4e0d\u5b58\u5728\u3002");
        }
        ProcessInstanceQuery istQuery = this.getInstanceQuery(task, formScheme);
        ProcessOperationQuery optQuery = this.getOperationQuery(task, formScheme);
        Map<String, ProcessInstanceDO> instancesFromId = istQuery.queryInstances(taskKey, instanceIds, ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
        if (monitor.isCanceled()) {
            return new InstanceIdCancelResult(instanceIds);
        }
        InstanceIdOperateResult operateResult = new InstanceIdOperateResult();
        ArrayList<ProcessInstanceDO> instancesToModify = new ArrayList<ProcessInstanceDO>();
        for (String id : instanceIds) {
            ProcessInstanceDO instanceDO = instancesFromId.get(id);
            if (instanceDO == null) {
                operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                break;
            }
            ProcessDefinition procDef = (ProcessDefinition)this.processDefinitionService.queryProcess(instanceDO.getProcessDefinitionId());
            if (procDef == null) {
                operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.PROCESSDEFINITION_NOT_FOUND));
                break;
            }
            UserTask userTask = procDef.getUserTask(instanceDO.getCurNode());
            if (userTask == null) {
                operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.USERTASK_NOT_FOUND));
                break;
            }
            actionPath = userTask.getActionPath(actionCode);
            if (actionPath == null) {
                operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.USERACTION_NOT_FOUND));
                break;
            }
            instancesToModify.add(instanceDO);
        }
        monitor.stepIn();
        if (monitor.isCanceled()) {
            return new InstanceIdCancelResult(instanceIds);
        }
        HashedMap<String, String> orignalTaskIds = new HashedMap<String, String>(instancesToModify.size());
        ArrayList<ProcessInstanceDO> successModifiedInstances = new ArrayList<ProcessInstanceDO>(instancesToModify.size());
        List<String> lockInstanceIds = instancesToModify.stream().map(t -> t.getId()).collect(Collectors.toList());
        try (ProcessInstanceLockManager.ProcessInstanceLock lockObj = this.instanceLockManager.createLock(istQuery.getQueryModel(), lockInstanceIds);){
            this.instanceLockManager.lock(lockObj);
            if (monitor.isCanceled()) {
                actionPath = new InstanceIdCancelResult(instanceIds);
                return actionPath;
            }
            monitor.stepIn();
            Map<String, ProcessInstanceDO> instanceLocked = istQuery.queryInstances(taskKey, lockObj.getLockedInstanceIds(), ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
            ArrayList<ProcessInstanceDO> instancesToUpdate = new ArrayList<ProcessInstanceDO>();
            ArrayList<ProcessOperationDO> operationDOs = new ArrayList<ProcessOperationDO>();
            for (ProcessInstanceDO processInstanceDO : instancesToModify) {
                String id = processInstanceDO.getId();
                ProcessInstanceDO instanceDO = instanceLocked.get(id);
                if (instanceDO == null) {
                    operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                    break;
                }
                ProcessDefinition procDef = (ProcessDefinition)this.processDefinitionService.queryProcess(instanceDO.getProcessDefinitionId());
                if (procDef == null) {
                    operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.PROCESSDEFINITION_NOT_FOUND));
                    break;
                }
                UserTask userTask = procDef.getUserTask(instanceDO.getCurNode());
                if (userTask == null) {
                    operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.USERTASK_NOT_FOUND));
                    break;
                }
                UserActionPath actionPath2 = userTask.getActionPath(actionCode);
                if (actionPath2 == null) {
                    operateResult.appendResult(id, OperateResult.newFailResult((ErrorCode)ErrorCode.USERACTION_NOT_FOUND));
                    break;
                }
                orignalTaskIds.put(instanceDO.getId(), instanceDO.getCurTaskId());
                instancesToUpdate.add(instanceDO);
                ProcessOperationDO operationDO = ProcessRepositoryUtil.operate(instanceDO, actionPath2, IActor.fromContext(), args);
                operationDOs.add(operationDO);
            }
            monitor.stepIn();
            if (monitor.isCanceled()) {
                InstanceIdCancelResult instanceIdCancelResult = new InstanceIdCancelResult(instanceIds);
                return instanceIdCancelResult;
            }
            Set<String> successModifiedIds = istQuery.modifyInstances(instancesToUpdate, lockObj.getLockId());
            for (ProcessInstanceDO instanceDO : instancesToUpdate) {
                if (successModifiedIds.contains(instanceDO.getId())) {
                    successModifiedInstances.add(instanceDO);
                    operateResult.appendResult(instanceDO.getId(), OperateResult.newSuccessResult());
                    continue;
                }
                operateResult.appendResult(instanceDO.getId(), OperateResult.newFailResult((ErrorCode)ErrorCode.UNKNOW));
            }
            monitor.stepIn();
            List<ProcessOperationDO> list = operationDOs.stream().filter(t -> successModifiedIds.contains(t.getInstanceId())).collect(Collectors.toList());
            optQuery.insertOperations(list);
            monitor.stepIn();
        }
        monitor.stepIn();
        monitor.finishTask("batchcomplete-workflow-instance");
        return operateResult;
    }

    @Transactional
    public void deleteProcessInstance(IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = istQuery.queryInstance(businessKey);
        if (instance == null) {
            return;
        }
        istQuery.deleteInstance(instance.getId());
        ProcessOperationQuery optQuery = this.getOperationQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        optQuery.deleteOperation(instance.getId());
        WorkflowObjectType objectType = rtBizKey.getWorkflowSettings().getWorkflowObjectType();
        if (objectType == WorkflowObjectType.FORM || objectType == WorkflowObjectType.FORM_GROUP) {
            try (UnitStatusMaintainer maintainer = new UnitStatusMaintainer();){
                maintainer.setFormOrGroupbusinessKey(instance.getBusinessKey());
                maintainer.setActor(IActor.fromContext());
                maintainer.setTaskKey(rtBizKey.getTaskKey());
                maintainer.setFormSchemeKey(rtBizKey.getFormSchemeKey());
                maintainer.setWorkflowSettings(rtBizKey.getWorkflowSettings());
                maintainer.setInstanceQuery(istQuery);
                maintainer.setProcessInstanceLockManager(this.instanceLockManager);
                maintainer.setFormConditionAccessService(this.formAccessService);
                maintainer.setRunTimeViewController(this.viewController);
                maintainer.setTransactionUtil(this.transactionUtil);
                maintainer.run();
            }
        }
        TodoUtils.onInstanceDeleted(instance.getId());
    }

    public IBizObjectOperateResult<Void> batchDeleteProcessInstance(IBusinessKeyCollection bizKeyCollection) {
        return this.batchDeleteProcessInstance(bizKeyCollection, (IProgressMonitor)new DummyProgressMonitor());
    }

    @Transactional
    public IBizObjectOperateResult<Void> batchDeleteProcessInstance(IBusinessKeyCollection businessKeys, IProgressMonitor monitor) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        String taskName = "batchdelete-workflow-instance";
        monitor.startTask("batchdelete-workflow-instance", new int[]{20, 25, 25, 25, 5});
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        Map<IBusinessObject, ProcessInstanceDO> existsInstances = istQuery.queryInstances(businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
        if (monitor.isCanceled()) {
            return new BizObjectCancelResult(businessKeys.getBusinessObjects());
        }
        monitor.stepIn();
        List<String> instanceIdsToDelete = existsInstances.values().stream().map(ProcessInstanceDO::getId).collect(Collectors.toList());
        istQuery.deleteInstances(instanceIdsToDelete);
        monitor.stepIn();
        ProcessOperationQuery optQuery = this.getOperationQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        optQuery.deleteOperations(instanceIdsToDelete);
        monitor.stepIn();
        WorkflowObjectType objectType = rtBizKeys.getWorkflowSettings().getWorkflowObjectType();
        if (objectType == WorkflowObjectType.FORM || objectType == WorkflowObjectType.FORM_GROUP) {
            Collection modifiedBusinessKeys = existsInstances.values().stream().map(t -> t.getBusinessKey()).collect(Collectors.toList());
            try (UnitStatusBatchMaintainer maintainer = new UnitStatusBatchMaintainer();){
                maintainer.setFormOrGroupbusinessKeys(modifiedBusinessKeys);
                maintainer.setActor(IActor.fromContext());
                maintainer.setTaskKey(rtBizKeys.getTaskKey());
                maintainer.setFormSchemeKey(rtBizKeys.getFormSchemeKey());
                maintainer.setWorkflowSettings(rtBizKeys.getWorkflowSettings());
                maintainer.setInstanceQuery(istQuery);
                maintainer.setProcessInstanceLockManager(this.instanceLockManager);
                maintainer.setFormConditionAccessService(this.formAccessService);
                maintainer.setRunTimeViewController(this.viewController);
                maintainer.setTransactionUtil(this.transactionUtil);
                maintainer.run();
            }
        }
        monitor.stepIn();
        TodoUtils.onBatchInstanceDeleted(instanceIdsToDelete);
        BizObjectOperateResult operateResult = new BizObjectOperateResult();
        for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
            if (existsInstances.containsKey(businessObject)) {
                operateResult.appendResult(businessObject, OperateResult.newSuccessResult());
                continue;
            }
            operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
        }
        monitor.stepIn();
        monitor.finishTask("batchdelete-workflow-instance");
        return operateResult;
    }

    public void refreshProcessInstance(IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = query.queryInstance(businessKey);
        if (instance == null) {
            return;
        }
        TodoUtils.onInstanceRefresh(rtBizKey, instance);
    }

    public void batchRefreshProcessInstance(IBusinessKeyCollection businessKeyCollection) {
        this.batchRefreshProcessInstance(businessKeyCollection, (IProgressMonitor)new DummyProgressMonitor());
    }

    public void batchRefreshProcessInstance(IBusinessKeyCollection businessKeys, IProgressMonitor monitor) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return;
        }
        String taskName = "batchrefresh-workflow-instance";
        monitor.startTask("batchrefresh-workflow-instance", new int[]{99, 1});
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        Map<IBusinessObject, ProcessInstanceDO> instances = query.queryInstances(businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_ALL_FIELD);
        if (monitor.isCanceled()) {
            return;
        }
        monitor.stepIn();
        if (instances.isEmpty()) {
            return;
        }
        TodoUtils.onBatchInstanceRefresh(rtBizKeys, instances.values());
        monitor.stepIn();
        monitor.finishTask("batchrefresh-workflow-instance");
    }

    public IProcessInstance queryProcessInstance(IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = query.queryInstance(businessKey);
        if (instance != null) {
            return new ProcessInstance(instance);
        }
        return null;
    }

    public IBizObjectOperateResult<IProcessInstance> batchQueryProcessInstances(IBusinessKeyCollection businessKeys) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        Map<IBusinessObject, ProcessInstanceDO> instances = query.queryInstances(businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_ALL_FIELD);
        BizObjectOperateResult operateResult = new BizObjectOperateResult();
        for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
            ProcessInstanceDO instanceDO = instances.get(businessObject);
            if (instanceDO == null) {
                operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                continue;
            }
            operateResult.appendResult(businessObject, OperateResult.newSuccessResult((Object)new ProcessInstance(instanceDO)));
        }
        return operateResult;
    }

    public List<IProcessTask> queryCurrentTask(IBusinessKey businessKey, IActor actor) {
        UserTask currentUserTask;
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instanceDO = istQuery.queryInstance(businessKey);
        if (instanceDO != null && (currentUserTask = (UserTask)this.processDefinitionService.queryUserTask(instanceDO.getProcessDefinitionId(), instanceDO.getCurNode())) != null) {
            ArrayList<IUserAction> userActions = new ArrayList<IUserAction>(3);
            ActionCollector actionCollector = new ActionCollector();
            actionCollector.setActor(actor);
            actionCollector.setCurrentUserTask(currentUserTask);
            actionCollector.collectActions(userActions, rtBizKey);
            RetriveActionCollector retriveActionCollector = new RetriveActionCollector();
            retriveActionCollector.setActor(actor);
            retriveActionCollector.setCurrentUserTask(currentUserTask);
            retriveActionCollector.setOperationQuery(this.getOperationQuery(rtBizKey.getTask(), rtBizKey.getFormScheme()));
            retriveActionCollector.setInstanceId(instanceDO.getId());
            retriveActionCollector.collectActions(userActions, rtBizKey);
            ApplyReturnActionCollector applyReturnActionCollector = new ApplyReturnActionCollector();
            applyReturnActionCollector.setActor(actor);
            applyReturnActionCollector.setCurrentUserTask(currentUserTask);
            applyReturnActionCollector.collectActions(userActions, rtBizKey);
            return Arrays.asList(new ProcessTask(instanceDO.getCurTaskId(), userActions));
        }
        return Collections.emptyList();
    }

    @Deprecated
    public IBizObjectOperateResult<List<IProcessTask>> batchQueryCurrentTask(IBusinessKeyCollection businessKeys, IActor actor) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        Map<IBusinessObject, ProcessInstanceDO> instances = query.queryInstances(businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_SUMMARY_FIELD);
        BizObjectOperateResult operateResult = new BizObjectOperateResult();
        for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
            ProcessInstanceDO instanceDO = instances.get(businessObject);
            if (instanceDO == null) {
                operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                continue;
            }
            operateResult.appendResult(businessObject, OperateResult.newSuccessResult(Arrays.asList(new ProcessTask(instanceDO.getCurTaskId(), null))));
        }
        return operateResult;
    }

    public IProcessStatus queryStatus(IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = query.queryInstance(businessKey);
        if (instance != null) {
            ProcessDefinition processDefinition = this.processDefinitionService.getProcess(instance.getProcessDefinitionId());
            IProcessStatus status = processDefinition.getStatus(instance.getCurStatus());
            if (status == null) {
                status = new ProcessStatus(instance.getCurStatus(), null);
            }
            return status;
        }
        return null;
    }

    public IBizObjectOperateResult<IProcessStatus> batchQueryStatus(IBusinessKeyCollection businessKeys) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        Map<IBusinessObject, ProcessInstanceDO> instances = query.queryInstances(businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_STATUS_FIELD);
        BizObjectOperateResult operateResult = new BizObjectOperateResult();
        for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
            ProcessInstanceDO instanceDO = instances.get(businessObject);
            if (instanceDO == null) {
                operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                continue;
            }
            ProcessDefinition procDef = (ProcessDefinition)this.processDefinitionService.queryProcess(instanceDO.getProcessDefinitionId());
            if (procDef == null) {
                operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.PROCESSDEFINITION_NOT_FOUND));
                break;
            }
            IProcessStatus status = procDef.getStatus(instanceDO.getCurStatus());
            if (status == null) {
                status = new ProcessStatus(instanceDO.getCurStatus(), null);
            }
            operateResult.appendResult(businessObject, OperateResult.newSuccessResult((Object)status));
        }
        return operateResult;
    }

    public boolean existsInstance(String taskKey) {
        TaskDefine task = this.viewController.getTask(taskKey);
        if (task == null) {
            throw new IllegalArgumentException("task '" + taskKey + "' not found.");
        }
        List formSchemes = this.viewController.listFormSchemeByTask(taskKey);
        for (FormSchemeDefine formScheme : formSchemes) {
            ProcessInstanceQuery query = this.getInstanceQuery(task, formScheme);
            if (query.getQueryModel() == null || !query.existsInstance()) continue;
            return true;
        }
        return false;
    }

    public List<IProcessOperation> queryInstanceHistory(IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = istQuery.queryInstance(businessKey);
        if (instance == null) {
            throw new InstanceNotFoundException(PROCESSENGINEID, businessKey);
        }
        ProcessOperationQuery optQuery = this.getOperationQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        List<ProcessOperationDO> operationDOs = optQuery.queryOperations(instance.getId());
        if (operationDOs == null || operationDOs.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<IProcessOperation> operations = new ArrayList<IProcessOperation>();
        for (ProcessOperationDO operationDO : operationDOs) {
            operations.add(new ProcessOperation(operationDO));
        }
        return operations;
    }

    public List<String> getMatchingActors(IBusinessKey businessKey) {
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery query = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceDO instance = query.queryInstance(businessKey);
        if (instance == null) {
            throw new InstanceNotFoundException(PROCESSENGINEID, businessKey);
        }
        IUserTask userTask = this.processDefinitionService.getUserTask(instance.getProcessDefinitionId(), instance.getCurNode());
        return ActorStrategyUtil.getInstance().getActors(userTask.getActionExecutors(), rtBizKey);
    }

    public Map<DimensionCombination, IProcessStatus> queryEntityStatus(String taskKey, String period, DimensionCollection dimensions) {
        TaskDefine task = this.viewController.getTask(taskKey);
        if (task == null) {
            throw new IllegalArgumentException("\u4efb\u52d9 " + taskKey + " \u4e0d\u5b58\u5728\u3002");
        }
        WorkflowSettingsDO workflowSettings = this.workflowSettingsService.queryWorkflowSettings(taskKey);
        List dimensionList = dimensions.getDimensionCombinations();
        HashMap<DimensionCombination, IProcessStatus> status2Dim = new HashMap<DimensionCombination, IProcessStatus>(dimensionList.size());
        if (workflowSettings != null && !dimensionList.isEmpty()) {
            SchemePeriodLinkDefine schemePeriodLink = null;
            try {
                schemePeriodLink = this.viewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("\u6642\u671f " + period + " \u4e0a\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002");
            }
            if (schemePeriodLink == null) {
                throw new IllegalArgumentException("\u6642\u671f " + period + " \u4e0a\u65e0\u53ef\u7528\u7684\u62a5\u8868\u65b9\u6848\u3002");
            }
            FormSchemeDefine formScheme = this.viewController.getFormScheme(schemePeriodLink.getSchemeKey());
            if (formScheme == null) {
                throw new IllegalArgumentException("\u62a5\u8868\u65b9\u6848 " + schemePeriodLink.getSchemeKey() + " \u4e0d\u5b58\u5728\u3002");
            }
            ProcessInstanceQuery istQuery = this.getInstanceQuery(task, formScheme);
            UnitBusinessObjectCollection unitBusinessObjects = workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM ? UnitBusinessObjectCollection.buildFormObjectCollection(dimensionList) : (workflowSettings.getWorkflowObjectType() == WorkflowObjectType.FORM_GROUP ? UnitBusinessObjectCollection.buildFormGroupObjectCollection(dimensionList) : BusinessObjectCollection.newDimensionObjectCollection((DimensionCollection)dimensions));
            BusinessKeyCollection businessKeys = new BusinessKeyCollection(taskKey, (IBusinessObjectCollection)unitBusinessObjects);
            Map<IBusinessObject, ProcessInstanceDO> status2BusinessObject = istQuery.queryInstances((IBusinessKeyCollection)businessKeys, ProcessInstanceQuery.QueryFieldMode.QUERY_STATUS_FIELD);
            ProcessDefinition processDefinition = this.processDefinitionService.getProcess(workflowSettings.getWorkflowDefine());
            for (Map.Entry<IBusinessObject, ProcessInstanceDO> entry : status2BusinessObject.entrySet()) {
                IProcessStatus status = processDefinition.getEntityStatus(entry.getValue().getCurStatus(), workflowSettings.getWorkflowObjectType());
                if (status == null) {
                    status = new ProcessStatus(entry.getValue().getCurStatus(), null);
                }
                status2Dim.put(entry.getKey().getDimensions(), status);
            }
        }
        return status2Dim;
    }

    public IProcessStatusWithOperation queryStatusWithOperation(IBusinessKey businessKey) {
        ProcessOperationQuery optQuery;
        RuntimeBusinessKey rtBizKey = this.businessKeyUtil.verifyBusinessKey(businessKey);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKey.getTask(), rtBizKey.getFormScheme());
        ProcessInstanceJoinOperationQuery joinQuery = new ProcessInstanceJoinOperationQuery(istQuery, optQuery = this.getOperationQuery(rtBizKey.getTask(), rtBizKey.getFormScheme()));
        ProcessInstanceWithOperation instanceWithOperation = joinQuery.queryInstanceWithOperation(businessKey);
        if (instanceWithOperation != null) {
            ProcessInstanceDO instance = instanceWithOperation.getInstance();
            ProcessDefinition processDefinition = this.processDefinitionService.getProcess(instance.getProcessDefinitionId());
            IProcessStatus status = processDefinition.getStatus(instance.getCurStatus());
            if (status == null) {
                status = new ProcessStatus(instance.getCurStatus(), null);
            }
            ProcessOperation operation = new ProcessOperation(instanceWithOperation.getOperation());
            return new ProcessStatusWithOperation(status, (IProcessOperation)operation);
        }
        return null;
    }

    public IBizObjectOperateResult<IProcessStatusWithOperation> batchQueryStatusWithOperation(IBusinessKeyCollection businessKeys) {
        if (businessKeys.getBusinessObjects() == null || businessKeys.getBusinessObjects().size() == 0) {
            return IBizObjectOperateResult.emptyResult();
        }
        RuntimeBusinessKeyCollection rtBizKeys = this.businessKeyUtil.verifyBusinessKeyCollection(businessKeys);
        ProcessInstanceQuery istQuery = this.getInstanceQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        ProcessOperationQuery optQuery = this.getOperationQuery(rtBizKeys.getTask(), rtBizKeys.getFormScheme());
        ProcessInstanceJoinOperationQuery joinQuery = new ProcessInstanceJoinOperationQuery(istQuery, optQuery);
        Map<IBusinessObject, ProcessInstanceWithOperation> instancesWithOperation = joinQuery.queryInstancesWithOperation(businessKeys);
        BizObjectOperateResult operateResult = new BizObjectOperateResult();
        for (IBusinessObject businessObject : businessKeys.getBusinessObjects()) {
            ProcessInstanceWithOperation instanceWithOperation = instancesWithOperation.get(businessObject);
            if (instanceWithOperation == null) {
                operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.INSTANCE_NOT_FOUND));
                continue;
            }
            ProcessInstanceDO instance = instanceWithOperation.getInstance();
            ProcessDefinition procDef = (ProcessDefinition)this.processDefinitionService.queryProcess(instance.getProcessDefinitionId());
            if (procDef == null) {
                operateResult.appendResult(businessObject, OperateResult.newFailResult((ErrorCode)ErrorCode.PROCESSDEFINITION_NOT_FOUND));
                break;
            }
            IProcessStatus status = procDef.getStatus(instance.getCurStatus());
            if (status == null) {
                status = new ProcessStatus(instance.getCurStatus(), null);
            }
            ProcessOperation operation = new ProcessOperation(instanceWithOperation.getOperation());
            ProcessStatusWithOperation statusWithOperation = new ProcessStatusWithOperation(status, (IProcessOperation)operation);
            operateResult.appendResult(businessObject, OperateResult.newSuccessResult((Object)statusWithOperation));
        }
        return operateResult;
    }

    private static class InstanceIdCancelResult
    implements IInstanceIdOperateResult<Void> {
        private static final IOperateResult<Void> RESULT = OperateResult.newFailResult((ErrorCode)ErrorCode.CANCELD);
        private final Collection<String> instanceIds;

        public InstanceIdCancelResult(Collection<String> instanceIds) {
            this.instanceIds = instanceIds;
        }

        public int size() {
            return this.instanceIds.size();
        }

        public IOperateResult<Void> getResult(String instanceId) {
            return RESULT;
        }

        public Iterable<String> getInstanceKeys() {
            return this.instanceIds;
        }
    }

    private static class BizObjectCancelResult
    implements IBizObjectOperateResult<Void> {
        private static final IOperateResult<Void> RESULT = OperateResult.newFailResult((ErrorCode)ErrorCode.CANCELD);
        private final IBusinessObjectCollection businessObjectCollection;

        public BizObjectCancelResult(IBusinessObjectCollection businessObjectCollection) {
            this.businessObjectCollection = businessObjectCollection;
        }

        public int size() {
            return this.businessObjectCollection.size();
        }

        public IOperateResult<Void> getResult(IBusinessObject businessObject) {
            return RESULT;
        }

        public Iterable<IBusinessObject> getInstanceKeys() {
            return this.businessObjectCollection;
        }
    }
}

