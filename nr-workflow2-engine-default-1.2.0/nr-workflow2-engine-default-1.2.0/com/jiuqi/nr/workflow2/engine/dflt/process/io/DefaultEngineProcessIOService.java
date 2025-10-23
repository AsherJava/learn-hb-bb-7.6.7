/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor
 *  com.jiuqi.nr.workflow2.engine.core.IProcessIOService
 *  com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataIntputOptions
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription$ProcessDataOutputDescriptionBuilder
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions
 *  com.jiuqi.nr.workflow2.engine.core.process.io.Version
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataIntputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.DefaultEngineConfigChangeImportExecutor;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.template.ImportExecuteTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.util.ProcessIOUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.QueryModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessInstanceQuery;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationQuery;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DefaultEngineProcessIOService
implements IProcessIOService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ITempTableManager tempTableManager;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private ProcessIOUtil ioUtil;

    public void exportProcessData(IBusinessKeyCollection businessKeys, IPorcessDataOutputStream outputStream, ProcessDataOutputOptions options, IProgressMonitor monitor) {
        Map<String, IBusinessKeyCollection> businessKeyCollectionSplitByPeriod = this.splitByPeriod(businessKeys);
        String exportTask = "default_engine_export";
        monitor.startTask("default_engine_export", businessKeyCollectionSplitByPeriod.size());
        this.logger.info("\u5f00\u59cb\u6267\u884c\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa \u9884\u8ba1\u5171\u5bfc\u51fa{}\u4e2a\u65f6\u671f\u4e0b\u7684\u6d41\u7a0b\u6570\u636e", (Object)businessKeyCollectionSplitByPeriod.size());
        TaskDefine taskDefine = this.runTimeViewController.getTask(businessKeys.getTask());
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(taskDefine.getKey());
        List<DataDimension> allReportDimensions = this.ioUtil.getAllReportDimensions(taskDefine.getKey());
        String[] businessObjectDimensionNames = (String[])allReportDimensions.stream().map(dimension -> this.ioUtil.getDimensionName((DataDimension)dimension)).toArray(String[]::new);
        ProcessDataOutputDescription build = new ProcessDataOutputDescription.ProcessDataOutputDescriptionBuilder().workflowObjectType(workflowObjectType).dataTypes((Collection)options.getOutputDataTypes()).version(Version.V1_0_0).businessObjectDimensionNames(businessObjectDimensionNames).build();
        outputStream.writeDescription(build);
        for (Map.Entry<String, IBusinessKeyCollection> businessKeyCollectionEntry : businessKeyCollectionSplitByPeriod.entrySet()) {
            String period = businessKeyCollectionEntry.getKey();
            IBusinessKeyCollection businessKeyCollection = businessKeyCollectionEntry.getValue();
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
            if (schemePeriodLinkByPeriodAndTask == null) {
                this.logger.error("\u672a\u627e\u5230\u4e0e taskKey:" + taskDefine.getKey() + " period:" + period + " \u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
                throw new FormSchemeNotFoundException(taskDefine.getKey(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            ProcessInstanceQuery instanceQuery = this.getInstanceQuery(taskDefine, formSchemeDefine);
            ProcessOperationQuery operationQuery = this.getOperationQuery(taskDefine, formSchemeDefine);
            Map<IBusinessObject, ProcessInstanceDO> instanceDOMap = instanceQuery.queryInstances(businessKeyCollection, ProcessInstanceQuery.QueryFieldMode.QUERY_ALL_FIELD);
            String taskByPeriod = "write_data_" + period;
            monitor.startTask(taskByPeriod, instanceDOMap.size());
            this.logger.info("\u5f00\u59cb\u5bfc\u51fa\u65f6\u671f\u4e3a{}\u7684\u6d41\u7a0b\u6570\u636e \u5171{}\u6761\u6d41\u7a0b\u5b9e\u4f8b\u6570\u636e", (Object)taskByPeriod, (Object)instanceDOMap.size());
            IProcessDataWriter writer = outputStream.getProcessDataWriter(period);
            for (Map.Entry<IBusinessObject, ProcessInstanceDO> instanceEntry : instanceDOMap.entrySet()) {
                IBusinessObject businessObject = instanceEntry.getKey();
                ProcessInstanceDO instanceDO = instanceEntry.getValue();
                writer.writeInstance(this.transferToProcessIOInstance(businessObject, instanceDO));
                List<ProcessOperationDO> processOperationDOs = operationQuery.queryOperations(instanceDO.getId());
                for (ProcessOperationDO operationDO : processOperationDOs) {
                    writer.writeOperation(this.transferToProcessIOOperation(operationDO));
                }
                monitor.stepIn();
            }
            monitor.finishTask(taskByPeriod);
            monitor.stepIn();
        }
        monitor.finishTask("default_engine_export");
    }

    public IProcessDataImportResult importProcessData(IPorcessDataInputStream inputStream, ProcessDataIntputOptions options, IProgressMonitor monitor) {
        TaskDefine taskDefine = this.runTimeViewController.getTaskByCode(inputStream.getTaskCode());
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskDefine.getKey());
        List periods = inputStream.getPeriods();
        String importTask = "default_engine_export";
        monitor.startTask("default_engine_export", periods.size());
        this.logger.info("\u5f00\u59cb\u6267\u884c\u6d41\u7a0b\u6570\u636e\u5bfc\u5165 \u9884\u8ba1\u5171\u5bfc\u5165{}\u4e2a\u65f6\u671f\u4e0b\u7684\u6d41\u7a0b\u6570\u636e", (Object)periods.size());
        for (String period : periods) {
            SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskDefine.getKey());
            if (schemePeriodLinkByPeriodAndTask == null) {
                this.logger.error("\u672a\u627e\u5230\u4e0e taskKey:" + taskDefine.getKey() + " period:" + period + " \u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
                throw new FormSchemeNotFoundException(taskDefine.getKey(), period);
            }
            FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
            ProcessInstanceQuery instanceQuery = this.getInstanceQuery(taskDefine, formSchemeDefine);
            ProcessOperationQuery operationQuery = this.getOperationQuery(taskDefine, formSchemeDefine);
            IProcessDataReader reader = inputStream.getProcessDataReaders(period);
            IProcessIOInstance processIOInstance = reader.readNextInstance();
            while (processIOInstance != null) {
                instanceQuery.insertInstance(this.transferToProcessInstanceDO(taskDefine.getKey(), workflowDefine, processIOInstance));
                processIOInstance = reader.readNextInstance();
            }
            IProcessIOOperation processIOOperation = reader.readNextOperation();
            while (processIOOperation != null) {
                operationQuery.insertOperation(this.transferToProcessOperationDO(processIOOperation));
                processIOOperation = reader.readNextOperation();
            }
            monitor.stepIn();
        }
        monitor.finishTask("default_engine_export");
        return new IProcessDataImportResult(){

            public int getInstancJumpErrorCount() {
                return 0;
            }

            public String getInstanceJumpErrorInfos() {
                return "";
            }
        };
    }

    public IProcessConfigChangeImportExecutor getConfigChangeImportExecutor(IPorcessDataInputStream inputStream, WorkflowSettingsDO targetSettingsDO) {
        ImportExecuteTemplate importExecuteTemplate = new ImportExecuteTemplate(inputStream, targetSettingsDO);
        return new DefaultEngineConfigChangeImportExecutor(importExecuteTemplate);
    }

    private ProcessInstanceQuery getInstanceQuery(TaskDefine task, FormSchemeDefine formScheme) {
        QueryModel.ProcessInstanceQueryModel istQueryModel = QueryModel.ProcessInstanceQueryModel.build(this.dataModelService, this.entityMetaService, this.runtimeDataSchemeService, task, formScheme);
        return new ProcessInstanceQuery(this.jdbcTemplate, this.tempTableManager, istQueryModel);
    }

    private ProcessOperationQuery getOperationQuery(TaskDefine task, FormSchemeDefine formScheme) {
        QueryModel.ProcessOperationQueryModel optQueryModel = QueryModel.ProcessOperationQueryModel.build(this.dataModelService, formScheme);
        return new ProcessOperationQuery(this.jdbcTemplate, optQueryModel);
    }

    private Map<String, IBusinessKeyCollection> splitByPeriod(IBusinessKeyCollection businessKeys) {
        String period;
        HashMap<String, IBusinessKeyCollection> businessKeyMap = new HashMap<String, IBusinessKeyCollection>();
        HashMap<String, List> businessObjectMap = new HashMap<String, List>();
        IBusinessObjectCollection businessObjects = businessKeys.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            period = businessObject.getDimensions().getPeriodDimensionValue().getValue().toString();
            businessObjectMap.computeIfAbsent(period, key -> new ArrayList()).add(businessObject);
        }
        for (Map.Entry entry : businessObjectMap.entrySet()) {
            period = (String)entry.getKey();
            List businessObjectList = (List)entry.getValue();
            businessKeyMap.put(period, new OperateBusinessKeyCollection(businessKeys.getTask(), businessObjectList));
        }
        return businessKeyMap;
    }

    private IProcessIOInstance transferToProcessIOInstance(IBusinessObject businessObject, ProcessInstanceDO instanceDO) {
        ProcessIOInstance processIOInstance = new ProcessIOInstance();
        processIOInstance.setId(instanceDO.getId());
        processIOInstance.setBusinessObject(businessObject);
        processIOInstance.setCurrentUserTask(instanceDO.getCurNode());
        processIOInstance.setStatus(instanceDO.getCurStatus());
        processIOInstance.setCurrentUserTaskCode(instanceDO.getCurNode());
        processIOInstance.setStartTime(instanceDO.getStartTime());
        processIOInstance.setUpdateTime(instanceDO.getUpdateTime());
        return processIOInstance;
    }

    private ProcessInstanceDO transferToProcessInstanceDO(String taskKey, String workflowDefine, IProcessIOInstance processIOInstance) {
        ProcessInstanceDO processInstanceDO = new ProcessInstanceDO();
        processInstanceDO.setId(processIOInstance.getId());
        processInstanceDO.setBusinessKey((IBusinessKey)new BusinessKey(taskKey, processIOInstance.getBusinessObject()));
        processInstanceDO.setProcessDefinitionId(workflowDefine);
        processInstanceDO.setCurTaskId(UUID.randomUUID().toString());
        processInstanceDO.setCurNode(processIOInstance.getCurrentUserTask());
        processInstanceDO.setCurStatus(processIOInstance.getStatus());
        processInstanceDO.setStartTime(processIOInstance.getStartTime());
        processInstanceDO.setUpdateTime(processIOInstance.getUpdateTime());
        return processInstanceDO;
    }

    private IProcessIOOperation transferToProcessIOOperation(ProcessOperationDO operationDO) {
        ProcessIOOperation processIOOperation = new ProcessIOOperation();
        processIOOperation.setInstanceId(operationDO.getInstanceId());
        processIOOperation.setSourceUserTask(operationDO.getFromNode());
        processIOOperation.setTargetUserTask(operationDO.getToNode());
        processIOOperation.setAction(operationDO.getAction());
        processIOOperation.setOperateTime(operationDO.getOperateTime());
        processIOOperation.setOperator(operationDO.getOperate_user());
        processIOOperation.setComment(operationDO.getComment());
        processIOOperation.setForceReport(operationDO.isForceReport());
        return processIOOperation;
    }

    private ProcessOperationDO transferToProcessOperationDO(IProcessIOOperation processIOOperation) {
        ProcessOperationDO processOperationDO = new ProcessOperationDO();
        processOperationDO.setId(UUID.randomUUID().toString());
        processOperationDO.setInstanceId(processIOOperation.getInstanceId());
        processOperationDO.setFromNode(processIOOperation.getSourceUserTask());
        processOperationDO.setAction(processIOOperation.getAction());
        processOperationDO.setToNode(processIOOperation.getTargetUserTask());
        processOperationDO.setOperateTime(processIOOperation.getOperateTime());
        processOperationDO.setOperate_user(processIOOperation.getOperator());
        processOperationDO.setComment(processIOOperation.getComment());
        processOperationDO.setForceReport(processIOOperation.isForceReport());
        return processOperationDO;
    }
}

