/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationService
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io.template;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskTemplate;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.template.ImportExecuteTemplateInterface;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIOUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.action.UnitStatusBatchMaintainer;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.QueryModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceLockManager;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ImportExecuteTemplate
implements ImportExecuteTemplateInterface {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String SUFFIX_ORIGIN = "_origin";
    public static final String LAST_OPERATION_SUFFIX = "_refer";
    public static final String UNKNOWN_USER_TASK = "UNKNOWN_USER_TASK";
    private final IPorcessDataInputStream processDataInputStream;
    private final WorkflowSettingsDO targetSettingDO;
    private final TaskDefine taskDefine;
    private final List<String> rangeInstanceTable = new ArrayList<String>();
    private final List<String> rangeOperationTable = new ArrayList<String>();
    private final Map<String, String> periodInstanceTempTableMap = new HashMap<String, String>();
    private final Map<String, String> periodOperationTempTableMap = new HashMap<String, String>();
    private final IRunTimeViewController runTimeViewController;
    private final DesignDataModelService designDataModelService;
    private final DataModelDeployService dataModelDeployService;
    private final PeriodEngineService periodEngineService;
    private final IEntityMetaService entityMetaService;
    private final IRuntimeDataSchemeService dataSchemeService;
    private final DataModelService dataModelService;
    private final JdbcTemplate jdbcTemplate;
    private final ITempTableManager tempTableManager;
    private final TodoManipulationService todoManipulationService;
    private final DefaultProcessDefinitionService processDefinitionService;
    private final ProcessInstanceLockManager instanceLockManager;
    private final IFormConditionAccessService formAccessService;
    private final TransactionUtil transactionUtil;

    public ImportExecuteTemplate(IPorcessDataInputStream processDataInputStream, WorkflowSettingsDO targetSettingDO) {
        this.processDataInputStream = processDataInputStream;
        this.targetSettingDO = targetSettingDO;
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        this.designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        this.dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
        this.periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
        this.entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        this.dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        this.dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
        this.jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        this.tempTableManager = (ITempTableManager)SpringBeanUtils.getBean(ITempTableManager.class);
        this.todoManipulationService = (TodoManipulationService)SpringBeanUtils.getBean(TodoManipulationService.class);
        this.processDefinitionService = (DefaultProcessDefinitionService)SpringBeanUtils.getBean(DefaultProcessDefinitionService.class);
        this.instanceLockManager = (ProcessInstanceLockManager)SpringBeanUtils.getBean(ProcessInstanceLockManager.class);
        this.formAccessService = (IFormConditionAccessService)SpringBeanUtils.getBean(IFormConditionAccessService.class);
        this.transactionUtil = (TransactionUtil)SpringBeanUtils.getBean(TransactionUtil.class);
        this.taskDefine = this.runTimeViewController.getTaskByCode(processDataInputStream.getTaskCode());
    }

    @Override
    public void createTempTable() {
        List periods = this.processDataInputStream.getPeriods();
        for (String period : periods) {
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, this.taskDefine.getKey());
            if (schemePeriodLinkByPeriodAndTask == null) {
                this.logger.error("\u672a\u627e\u5230\u4e0e taskKey:{} period:{} \u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848 \u6d41\u7a0b\u6570\u636e\u5f71\u5b50\u8868\u521b\u5efa\u7ec8\u6b62\uff01", (Object)this.taskDefine.getKey(), (Object)period);
                throw new FormSchemeNotFoundException(this.taskDefine.getKey(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            String originalInstanceTableName = DataModelConstant.getInstanceTableName(formSchemeDefine.getFormSchemeCode());
            String instanceTempTableName = originalInstanceTableName + "_temp";
            DesignTableModelDefine instanceTableModelDefine = this.designDataModelService.getTableModelDefineByCode(instanceTempTableName);
            if (instanceTableModelDefine == null) {
                this.createTempInstanceTable(this.taskDefine, formSchemeDefine, this.targetSettingDO, instanceTempTableName);
                this.rangeInstanceTable.add(originalInstanceTableName);
            }
            this.periodInstanceTempTableMap.put(period, instanceTempTableName);
            String originalOperationTableName = DataModelConstant.getHistoryTableName(formSchemeDefine.getFormSchemeCode());
            String operationTempTableName = originalOperationTableName + "_temp";
            DesignTableModelDefine operationTableModelDefine = this.designDataModelService.getTableModelDefineByCode(operationTempTableName);
            if (operationTableModelDefine == null) {
                this.createTempOperationTable(this.taskDefine, formSchemeDefine, this.targetSettingDO, operationTempTableName);
                this.rangeOperationTable.add(originalOperationTableName);
            }
            this.periodOperationTempTableMap.put(period, operationTempTableName);
        }
    }

    @Override
    public void executeImport() {
        List periods = this.processDataInputStream.getPeriods();
        for (String period : periods) {
            IProcessIOInstance processIOInstance;
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, this.taskDefine.getKey());
            if (schemePeriodLinkByPeriodAndTask == null) {
                this.logger.error("\u672a\u627e\u5230\u4e0e taskKey:{} period:{} \u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848 \u5411\u5f71\u5b50\u8868{}\u7684\u6570\u636e\u5bfc\u5165\u7ec8\u6b62\uff01", this.taskDefine.getKey(), period, "tableName");
                throw new FormSchemeNotFoundException(this.taskDefine.getKey(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            ProcessInstanceQuery tempInstanceQuery = this.createTempInstanceQuery(this.taskDefine, formSchemeDefine, this.periodInstanceTempTableMap.get(period));
            ProcessOperationQuery tempOperationQuery = this.createTempOperationQuery(this.taskDefine, formSchemeDefine, this.periodOperationTempTableMap.get(period));
            IProcessDataReader reader = this.processDataInputStream.getProcessDataReaders(period);
            ArrayList<IBusinessKey> allInstanceBusinessKey = new ArrayList<IBusinessKey>();
            HashSet<String> allInstanceId = new HashSet<String>();
            ArrayList<ProcessInstanceDO> instanceBuffer = new ArrayList<ProcessInstanceDO>();
            ArrayList<ProcessOperationDO> operationBuffer = new ArrayList<ProcessOperationDO>();
            boolean isImportOperation = true;
            while ((processIOInstance = reader.readNextInstance()) != null) {
                if (ProcessIOUtil.isUnitInstance(processIOInstance, this.targetSettingDO.getWorkflowObjectType())) continue;
                if (WorkflowObjectType.FORM.equals((Object)this.targetSettingDO.getWorkflowObjectType()) || WorkflowObjectType.FORM_GROUP.equals((Object)this.targetSettingDO.getWorkflowObjectType())) {
                    allInstanceBusinessKey.add((IBusinessKey)new BusinessKey(this.taskDefine.getKey(), processIOInstance.getBusinessObject()));
                }
                if (isImportOperation && UserTaskTemplate.tryGet(processIOInstance.getCurrentUserTask()) == null) {
                    isImportOperation = false;
                }
                instanceBuffer.add(this.transferToProcessInstanceDO(this.taskDefine.getKey(), this.targetSettingDO.getWorkflowDefine(), processIOInstance));
                allInstanceId.add(processIOInstance.getId());
                if (instanceBuffer.size() < 1000) continue;
                tempInstanceQuery.insertInstances(instanceBuffer);
                instanceBuffer.clear();
            }
            if (!instanceBuffer.isEmpty()) {
                tempInstanceQuery.insertInstances(instanceBuffer);
                instanceBuffer.clear();
            }
            if (isImportOperation) {
                Optional earliestOperation;
                IProcessIOOperation operation;
                int i;
                boolean isContainStart;
                IProcessIOOperation processIOOperation;
                ArrayList<IProcessIOOperation> operationGroupByInstanceId = new ArrayList<IProcessIOOperation>();
                String preInstanceId = null;
                while ((processIOOperation = reader.readNextOperation()) != null) {
                    if (preInstanceId != null && !processIOOperation.getInstanceId().equals(preInstanceId)) {
                        isContainStart = false;
                        operationGroupByInstanceId.sort(Comparator.comparing(IProcessIOOperation::getOperateTime));
                        for (i = 0; i < operationGroupByInstanceId.size(); ++i) {
                            operation = (IProcessIOOperation)operationGroupByInstanceId.get(i);
                            if (!isContainStart && operation.getAction().equals("start")) {
                                isContainStart = true;
                            }
                            operationBuffer.add(this.transferToProcessOperationDO(operation, i == operationGroupByInstanceId.size() - 1));
                        }
                        if (!isContainStart) {
                            ProcessInstanceDO processInstanceDO = tempInstanceQuery.queryInstance(preInstanceId);
                            earliestOperation = operationGroupByInstanceId.stream().findFirst();
                            operationBuffer.add(this.generateStartOperationDO(processInstanceDO, earliestOperation.orElse(null)));
                        }
                        if (operationBuffer.size() >= 1000) {
                            tempOperationQuery.insertOperations(operationBuffer);
                            operationBuffer.clear();
                        }
                        allInstanceId.remove(preInstanceId);
                        operationGroupByInstanceId.clear();
                    }
                    preInstanceId = processIOOperation.getInstanceId();
                    operationGroupByInstanceId.add(processIOOperation);
                }
                if (!operationGroupByInstanceId.isEmpty()) {
                    isContainStart = false;
                    operationGroupByInstanceId.sort(Comparator.comparing(IProcessIOOperation::getOperateTime));
                    for (i = 0; i < operationGroupByInstanceId.size(); ++i) {
                        operation = (IProcessIOOperation)operationGroupByInstanceId.get(i);
                        if (!isContainStart && operation.getAction().equals("start")) {
                            isContainStart = true;
                        }
                        operationBuffer.add(this.transferToProcessOperationDO(operation, i == operationGroupByInstanceId.size() - 1));
                    }
                    if (!isContainStart) {
                        ProcessInstanceDO processInstanceDO = tempInstanceQuery.queryInstance(preInstanceId);
                        earliestOperation = operationGroupByInstanceId.stream().findFirst();
                        operationBuffer.add(this.generateStartOperationDO(processInstanceDO, earliestOperation.orElse(null)));
                    }
                    allInstanceId.remove(preInstanceId);
                    operationGroupByInstanceId.clear();
                }
                if (!allInstanceId.isEmpty()) {
                    for (String instanceId : allInstanceId) {
                        ProcessInstanceDO remainInstance = tempInstanceQuery.queryInstance(instanceId);
                        operationBuffer.add(this.generateStartOperationDO(remainInstance, null));
                    }
                }
                if (!operationBuffer.isEmpty()) {
                    tempOperationQuery.insertOperations(operationBuffer);
                    operationBuffer.clear();
                }
            }
            if (!WorkflowObjectType.FORM.equals((Object)this.targetSettingDO.getWorkflowObjectType()) && !WorkflowObjectType.FORM_GROUP.equals((Object)this.targetSettingDO.getWorkflowObjectType())) continue;
            UnitStatusBatchMaintainer maintainer = new UnitStatusBatchMaintainer();
            Throwable throwable = null;
            try {
                maintainer.setFormOrGroupbusinessKeys(allInstanceBusinessKey);
                maintainer.setActor(IActor.fromContext());
                maintainer.setTaskKey(this.taskDefine.getKey());
                maintainer.setFormSchemeKey(formSchemeDefine.getKey());
                maintainer.setWorkflowSettings(this.targetSettingDO);
                maintainer.setInstanceQuery(tempInstanceQuery);
                maintainer.setProcessInstanceLockManager(this.instanceLockManager);
                maintainer.setFormConditionAccessService(this.formAccessService);
                maintainer.setRunTimeViewController(this.runTimeViewController);
                maintainer.setTransactionUtil(this.transactionUtil);
                maintainer.run();
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (maintainer == null) continue;
                if (throwable != null) {
                    try {
                        maintainer.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                maintainer.close();
            }
        }
    }

    @Override
    public void swapTable() {
        try {
            DesignTableModelDefine tempTableModelDefine;
            DesignTableModelDefine originTableModelDefine;
            ArrayList<String> originTableIds = new ArrayList<String>();
            ArrayList<String> tempTableIds = new ArrayList<String>();
            for (String istTableName : this.rangeInstanceTable) {
                originTableModelDefine = this.designDataModelService.getTableModelDefineByCode(istTableName);
                if (originTableModelDefine != null) {
                    originTableModelDefine.setName(istTableName + SUFFIX_ORIGIN);
                    originTableModelDefine.setCode(istTableName + SUFFIX_ORIGIN);
                    this.designDataModelService.updateTableModelDefine(originTableModelDefine);
                    originTableIds.add(originTableModelDefine.getID());
                }
                tempTableModelDefine = this.designDataModelService.getTableModelDefineByCode(istTableName + "_temp");
                tempTableModelDefine.setName(istTableName);
                tempTableModelDefine.setCode(istTableName);
                this.designDataModelService.updateTableModelDefine(tempTableModelDefine);
                tempTableIds.add(tempTableModelDefine.getID());
            }
            for (String optTableName : this.rangeOperationTable) {
                originTableModelDefine = this.designDataModelService.getTableModelDefineByCode(optTableName);
                if (originTableModelDefine != null) {
                    originTableModelDefine.setName(optTableName + SUFFIX_ORIGIN);
                    originTableModelDefine.setCode(optTableName + SUFFIX_ORIGIN);
                    this.designDataModelService.updateTableModelDefine(originTableModelDefine);
                    originTableIds.add(originTableModelDefine.getID());
                }
                tempTableModelDefine = this.designDataModelService.getTableModelDefineByCode(optTableName + "_temp");
                tempTableModelDefine.setName(optTableName);
                tempTableModelDefine.setCode(optTableName);
                this.designDataModelService.updateTableModelDefine(tempTableModelDefine);
                tempTableIds.add(tempTableModelDefine.getID());
            }
            for (String tableId : originTableIds) {
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
            for (String tableId : tempTableIds) {
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5728\u76ee\u6807\u8868\u4e0e\u5f71\u5b50\u4e34\u65f6\u8868\u4e92\u6362\u8868\u540d\u65f6\u53d1\u751f\u5f02\u5e38 \u5f02\u5e38\u4fe1\u606f\u4e3a\uff1a{}", (Object)e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTempTable() {
        try {
            DesignTableModelDefine tempTableModelDefine;
            ArrayList<String> tableIds = new ArrayList<String>();
            for (String istTableName : this.rangeInstanceTable) {
                tempTableModelDefine = this.designDataModelService.getTableModelDefineByCode(istTableName + "_temp");
                if (tempTableModelDefine == null) continue;
                this.designDataModelService.deleteTableModelDefine(tempTableModelDefine.getID());
                tableIds.add(tempTableModelDefine.getID());
            }
            for (String optTableName : this.rangeOperationTable) {
                tempTableModelDefine = this.designDataModelService.getTableModelDefineByCode(optTableName + "_temp");
                if (tempTableModelDefine == null) continue;
                this.designDataModelService.deleteTableModelDefine(tempTableModelDefine.getID());
                tableIds.add(tempTableModelDefine.getID());
            }
            for (String tableId : tableIds) {
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5728\u6e05\u9664\u4e34\u65f6\u8868\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38 \u5f02\u5e38\u4fe1\u606f\u4e3a\uff1a{}", (Object)e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropOriginTable() {
        try {
            DesignTableModelDefine tempTableModelDefine;
            ArrayList<String> tableIds = new ArrayList<String>();
            for (String istTableName : this.rangeInstanceTable) {
                tempTableModelDefine = this.designDataModelService.getTableModelDefineByCode(istTableName + SUFFIX_ORIGIN);
                if (tempTableModelDefine == null) continue;
                this.designDataModelService.deleteTableModelDefine(tempTableModelDefine.getID());
                tableIds.add(tempTableModelDefine.getID());
            }
            for (String optTableName : this.rangeOperationTable) {
                tempTableModelDefine = this.designDataModelService.getTableModelDefineByCode(optTableName + SUFFIX_ORIGIN);
                if (tempTableModelDefine == null) continue;
                this.designDataModelService.deleteTableModelDefine(tempTableModelDefine.getID());
                tableIds.add(tempTableModelDefine.getID());
            }
            for (String tableId : tableIds) {
                this.dataModelDeployService.deployTableUnCheck(tableId);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5728\u6e05\u7a7a\u53d8\u66f4\u540d\u79f0\u540e\u7684\u76ee\u6807\u8868\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38 \u5f02\u5e38\u4fe1\u606f\u4e3a\uff1a{}", (Object)e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterRelevantOperation() {
        this.processDefinitionService.onProcessChanged(this.targetSettingDO.getWorkflowDefine());
        this.todoManipulationService.deleteTodoMessageByTaskId(this.taskDefine.getKey());
    }

    public void createTempInstanceTable(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings, String tableName) {
        DataModelService.ProcessInstanceModelService instanceModelService = new DataModelService.ProcessInstanceModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
        instanceModelService.create(task, formScheme, workflowSettings, tableName);
    }

    public ProcessInstanceQuery createTempInstanceQuery(TaskDefine task, FormSchemeDefine formScheme, String tableName) {
        QueryModel.ProcessInstanceQueryModel istQueryModel = QueryModel.ProcessInstanceQueryModel.build(this.dataModelService, this.entityMetaService, this.dataSchemeService, task, formScheme, tableName);
        return new ProcessInstanceQuery(this.jdbcTemplate, this.tempTableManager, istQueryModel);
    }

    public void createTempOperationTable(TaskDefine task, FormSchemeDefine formScheme, WorkflowSettingsDO workflowSettings, String tableName) {
        DataModelService.ProcessOperationModelService operationModelService = new DataModelService.ProcessOperationModelService(this.designDataModelService, this.periodEngineService, this.entityMetaService, this.dataModelDeployService, this.dataSchemeService);
        operationModelService.create(task, formScheme, workflowSettings, tableName);
    }

    public ProcessOperationQuery createTempOperationQuery(TaskDefine task, FormSchemeDefine formScheme, String tableName) {
        QueryModel.ProcessOperationQueryModel optQueryModel = QueryModel.ProcessOperationQueryModel.build(this.dataModelService, formScheme, tableName);
        return new ProcessOperationQuery(this.jdbcTemplate, optQueryModel);
    }

    private ProcessInstanceDO transferToProcessInstanceDO(String taskKey, String workflowDefine, IProcessIOInstance processIOInstance) {
        boolean isUnKnowUserTask = UserTaskTemplate.tryGet(processIOInstance.getCurrentUserTask()) == null;
        ProcessInstanceDO processInstanceDO = new ProcessInstanceDO();
        processInstanceDO.setId(processIOInstance.getId());
        processInstanceDO.setBusinessKey((IBusinessKey)new BusinessKey(taskKey, processIOInstance.getBusinessObject()));
        processInstanceDO.setProcessDefinitionId(workflowDefine);
        processInstanceDO.setStartTime(processIOInstance.getStartTime());
        processInstanceDO.setUpdateTime(processIOInstance.getUpdateTime());
        processInstanceDO.setStartUser(NpContextHolder.getContext().getUserId());
        processInstanceDO.setCurTaskId(UUID.randomUUID().toString());
        processInstanceDO.setCurNode(isUnKnowUserTask ? UNKNOWN_USER_TASK : processIOInstance.getCurrentUserTask());
        processInstanceDO.setCurStatus(ProcessIOUtil.transferOldStateCodeToNew(processIOInstance.getStatus()));
        processInstanceDO.setLastOperationId(processIOInstance.getId() + LAST_OPERATION_SUFFIX);
        return processInstanceDO;
    }

    private ProcessOperationDO transferToProcessOperationDO(IProcessIOOperation processIOOperation, boolean isLatestOperation) {
        ProcessOperationDO processOperationDO = new ProcessOperationDO();
        processOperationDO.setId(isLatestOperation ? processIOOperation.getInstanceId() + LAST_OPERATION_SUFFIX : UUID.randomUUID().toString());
        processOperationDO.setInstanceId(processIOOperation.getInstanceId());
        processOperationDO.setFromNode(processIOOperation.getSourceUserTask());
        processOperationDO.setAction(processIOOperation.getAction());
        processOperationDO.setToNode(processIOOperation.getTargetUserTask());
        processOperationDO.setNewStatus(this.calculateStatus(processIOOperation.getSourceUserTask(), processIOOperation.getTargetUserTask()));
        processOperationDO.setOperateTime(processIOOperation.getOperateTime());
        processOperationDO.setOperate_user(processIOOperation.getOperator());
        processOperationDO.setComment(processIOOperation.getComment());
        processOperationDO.setForceReport(processIOOperation.isForceReport());
        return processOperationDO;
    }

    private ProcessOperationDO generateStartOperationDO(ProcessInstanceDO relevantInstance, IProcessIOOperation earliestOperation) {
        boolean isSubmit = false;
        boolean isStartTimeRight = true;
        if (earliestOperation != null) {
            if (earliestOperation.getAction().equals("act_submit")) {
                isSubmit = true;
            }
            if (relevantInstance.getStartTime().after(earliestOperation.getOperateTime())) {
                isStartTimeRight = false;
            }
        } else if (relevantInstance.getCurNode().equals("tsk_submit")) {
            isSubmit = true;
        }
        ProcessOperationDO startOperationDO = new ProcessOperationDO();
        startOperationDO.setId(UUID.randomUUID().toString());
        startOperationDO.setInstanceId(relevantInstance.getId());
        startOperationDO.setFromNode("tsk_start");
        startOperationDO.setAction("start");
        startOperationDO.setToNode(isSubmit ? "tsk_submit" : "tsk_upload");
        startOperationDO.setNewStatus(isSubmit ? "unsubmited" : "unreported");
        if (isStartTimeRight) {
            startOperationDO.setOperateTime(relevantInstance.getStartTime());
        } else {
            Calendar errorStartTime = Calendar.getInstance();
            errorStartTime.setTimeInMillis(0L);
            startOperationDO.setOperateTime(errorStartTime);
        }
        startOperationDO.setOperate_user(NpContextHolder.getContext().getUserId());
        return startOperationDO;
    }

    private String calculateStatus(String fromNode, String toNode) {
        if (fromNode.equals("tsk_start")) {
            if (toNode.equals("tsk_submit")) {
                return "unsubmited";
            }
            if (toNode.equals("tsk_upload")) {
                return "unreported";
            }
        } else if (fromNode.equals("tsk_submit")) {
            if (toNode.equals("tsk_upload")) {
                return "submited";
            }
        } else if (fromNode.equals("tsk_upload")) {
            if (toNode.equals("tsk_submit")) {
                return "backed";
            }
            if (toNode.equals("tsk_audit")) {
                return "reported";
            }
        } else if (fromNode.equals("tsk_audit")) {
            if (toNode.equals("tsk_audit_after_confirm")) {
                return "confirmed";
            }
            if (toNode.equals("tsk_upload") || toNode.equals("tsk_submit")) {
                return "rejected";
            }
        } else if (fromNode.equals("tsk_audit_after_confirm") && toNode.equals("tsk_audit")) {
            return "reported";
        }
        return null;
    }
}

