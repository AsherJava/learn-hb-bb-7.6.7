/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor
 *  com.jiuqi.nr.workflow2.engine.core.IProcessIOService
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult
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
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.bpm.IO.ProcessExportServiceImpl;
import com.jiuqi.nr.bpm.IO.ProcessIODimensionsBuilder;
import com.jiuqi.nr.bpm.IO.ProcessImportServiceImpl;
import com.jiuqi.nr.bpm.IO.QueryTableModel;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataIntputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessIOService
implements IProcessIOService {
    private static final Logger logger = LoggerFactory.getLogger(ProcessIOService.class);
    private QueryTableModel queryTableModel;
    private ProcessIODimensionsBuilder processIODimensionsBuilder;
    private IWorkflow workflow;
    private IRunTimeViewController runTimeViewController;
    private WorkflowSettingService workflowSettingService;
    private CustomWorkFolwService customWorkFolwService;

    public void exportProcessData(IBusinessKeyCollection businessKeys, IPorcessDataOutputStream outputStream, ProcessDataOutputOptions options, IProgressMonitor monitor) {
        try {
            this.init();
            FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(businessKeys);
            WorkFlowType workFlowType = this.workflow.queryStartType(formSchemeDefine.getKey());
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeDefine.getKey());
            ProcessExportServiceImpl exportData = new ProcessExportServiceImpl(this.queryTableModel, this.processIODimensionsBuilder, this.runTimeViewController, this.workflowSettingService, this.customWorkFolwService, workFlowType, defaultWorkflow);
            exportData.processData(businessKeys, options.getOutputDataTypes(), monitor);
            List<String> periods = exportData.getPeriods();
            ProcessDataOutputDescription build = new ProcessDataOutputDescription.ProcessDataOutputDescriptionBuilder().workflowObjectType(this.convertToWorkflowObjectType(workFlowType)).dataTypes((Collection)options.getOutputDataTypes()).version(Version.V1_0_0).businessObjectDimensionNames(this.getDimensionNames(businessKeys)).build();
            outputStream.writeDescription(build);
            StringBuffer exportLog = new StringBuffer();
            for (String period : periods) {
                IProcessDataWriter processDataWriter = outputStream.getProcessDataWriter(period);
                List<ProcessIOInstance> processIOInstances = exportData.getProcessIOInstances(period);
                List<ProcessIOOperation> processIOOperations = exportData.getProcessIOOperations(period);
                exportLog.append("\u65f6\u671f\uff1a").append(period).append(",\u6d41\u7a0b\u5b9e\u4f8b\u6570\u91cf\uff1a").append(processIOInstances == null ? 0 : processIOInstances.size()).append(";");
                exportLog.append("\u65f6\u671f\uff1a").append(period).append(",\u6d41\u7a0b\u5386\u53f2\u64cd\u4f5c\u6570\u91cf\uff1a").append(processIOOperations == null ? 0 : processIOOperations.size()).append("\n");
                if (processIOInstances != null && processIOInstances.size() > 0) {
                    for (ProcessIOInstance processIOInstance : processIOInstances) {
                        processDataWriter.writeInstance((IProcessIOInstance)processIOInstance);
                    }
                }
                if (processIOOperations == null || processIOOperations.size() <= 0) continue;
                for (ProcessIOOperation processIOOperation : processIOOperations) {
                    processDataWriter.writeOperation((IProcessIOOperation)processIOOperation);
                }
            }
            logger.info("\u5bfc\u51fa\u6570\u91cf\u7edf\u8ba1:" + exportLog.toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public IProcessDataImportResult importProcessData(IPorcessDataInputStream inputStream, ProcessDataIntputOptions options, IProgressMonitor monitor) {
        this.init();
        ProcessImportServiceImpl processImportService = new ProcessImportServiceImpl(this.queryTableModel, this.processIODimensionsBuilder, this.workflow, this.workflowSettingService, this.customWorkFolwService, this.runTimeViewController);
        IProcessDataImportResult iProcessDataImportResult = processImportService.processData(inputStream, monitor);
        return iProcessDataImportResult;
    }

    private void init() {
        this.processIODimensionsBuilder = (ProcessIODimensionsBuilder)BeanUtil.getBean(ProcessIODimensionsBuilder.class);
        this.queryTableModel = (QueryTableModel)BeanUtil.getBean(QueryTableModel.class);
        this.workflow = (IWorkflow)BeanUtil.getBean(IWorkflow.class);
        this.runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.workflowSettingService = (WorkflowSettingService)BeanUtil.getBean(WorkflowSettingService.class);
        this.customWorkFolwService = (CustomWorkFolwService)BeanUtil.getBean(CustomWorkFolwService.class);
    }

    public IProcessConfigChangeImportExecutor getConfigChangeImportExecutor(IPorcessDataInputStream inputStream, WorkflowSettingsDO targetSettingsDO) {
        throw new UnsupportedOperationException("\u9ed8\u8ba4\u6d41\u7a0b1.0\u5f15\u64ce \u5c1a\u672a\u652f\u6301\u914d\u7f6e\u53d8\u66f4\u5bfc\u5165\uff01");
    }

    private String[] getDimensionNames(IBusinessKeyCollection businessKeys) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        IBusinessObjectCollection businessObjects = businessKeys.getBusinessObjects();
        DimensionCollection valueSets = businessObjects.getDimensions();
        DimensionValueSet dimensionValueSet = valueSets.combineDim();
        ENameSet dimensions = dimensionValueSet.getDimensionSet().getDimensions();
        for (int i = 0; i < dimensions.size(); ++i) {
            dimensionNames.add(dimensions.get(i));
        }
        return dimensionNames.toArray(new String[dimensionNames.size()]);
    }

    private FormSchemeDefine getFormSchemeDefine(IBusinessKeyCollection businessKeys) {
        String task = businessKeys.getTask();
        IBusinessObjectCollection businessObjects = businessKeys.getBusinessObjects();
        DimensionCollection valueSets = businessObjects.getDimensions();
        List dimensionCombinations = valueSets.getDimensionCombinations();
        DimensionCombination dimensionCombination = (DimensionCombination)dimensionCombinations.stream().findAny().get();
        String period = dimensionCombination.getPeriodDimensionValue().getValue().toString();
        return this.getFormScheme(task, period);
    }

    private WorkflowObjectType convertToWorkflowObjectType(WorkFlowType workFlowType) {
        if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
            return WorkflowObjectType.MAIN_DIMENSION;
        }
        if (WorkFlowType.FORM.equals((Object)workFlowType)) {
            return WorkflowObjectType.FORM;
        }
        if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            return WorkflowObjectType.FORM_GROUP;
        }
        return WorkflowObjectType.MAIN_DIMENSION;
    }

    private FormSchemeDefine getFormScheme(String taskKey, String period) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        SchemePeriodLinkDefine schemePeriodLinkDefine = runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (schemePeriodLinkDefine == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728,\u8bf7\u6838\u5b9e\u8be5\u4efb\u52a1:" + taskKey + ",\u8be5\u65f6\u671f:" + period + "\u4e0b,\u662f\u5426\u5b58\u5728\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
        }
        return runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }
}

