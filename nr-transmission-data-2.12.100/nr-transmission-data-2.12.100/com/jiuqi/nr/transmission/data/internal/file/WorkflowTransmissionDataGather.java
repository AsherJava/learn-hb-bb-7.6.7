/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.bpm.IO.BusinessKeyObjectMapper
 *  com.jiuqi.nr.bpm.IO.IOProgressMontior
 *  com.jiuqi.nr.bpm.IO.ProcessIODimensionsBuilder
 *  com.jiuqi.nr.bpm.IO.ProcessIOService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils
 *  com.jiuqi.nr.datascheme.common.io.ZipUtils$ZipSubFile
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataIntputOptions
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType
 *  com.jiuqi.nr.workflow2.engine.core.process.io.Version
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 */
package com.jiuqi.nr.transmission.data.internal.file;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.bpm.IO.BusinessKeyObjectMapper;
import com.jiuqi.nr.bpm.IO.IOProgressMontior;
import com.jiuqi.nr.bpm.IO.ProcessIODimensionsBuilder;
import com.jiuqi.nr.bpm.IO.ProcessIOService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.common.io.ZipUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.api.ITransmissionDataGather;
import com.jiuqi.nr.transmission.data.common.FileHelper;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.internal.file.FormulaCheck;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataIntputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowTransmissionDataGather
implements ITransmissionDataGather {
    private static final Logger logger = LoggerFactory.getLogger(FormulaCheck.class);
    private static final String WORKFLOW_TRANSMISSION_TITLE = "\u6d41\u7a0b\u6570\u636e";

    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public String getCode() {
        return "WORKFLOW_TRANSMISSION_DATA";
    }

    @Override
    public String getTitle() {
        return WORKFLOW_TRANSMISSION_TITLE;
    }

    @Override
    public DataImportResult dataImport(InputStream inputStream, ITransmissionContext context) throws Exception {
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        ILogHelper logHelper = context.getLogHelper();
        logHelper.appendLog("\u591a\u7ea7\u90e8\u7f72\u5f00\u59cb\u88c5\u5165\u6d41\u7a0b\u6570\u636e");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u5f00\u59cb\u88c5\u5165\u6d41\u7a0b\u6570\u636e");
        monitor.progressAndMessage(0.0, "\u6b63\u5728\u51c6\u5907\u6d41\u7a0b\u6570\u636e");
        IExecuteParam executeParam = context.getExecuteParam();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        String tempDir = ZipUtils.newTempDir();
        try {
            Map<String, ZipUtils.ZipSubFile> zipFiles = ZipUtils.unZip((InputStream)inputStream).stream().collect(Collectors.toMap(ZipUtils.ZipSubFile::getSubFilePath, f -> f));
            if (zipFiles != null && zipFiles.size() > 0) {
                for (Map.Entry<String, ZipUtils.ZipSubFile> stringZipSubFileEntry : zipFiles.entrySet()) {
                    ZipUtils.ZipSubFile subFile = stringZipSubFileEntry.getValue();
                    FileHelper.getTempPathFile(subFile, tempDir);
                }
            } else {
                String message = MultilingualLog.WorkflowTransmissionDataGatherMessage(4);
                logHelper.appendLog(message);
                logger.info(message);
            }
            ProcessIOService processIOService = new ProcessIOService();
            ProcessDataIntputOptions options = new ProcessDataIntputOptions(Version.V1_0_0, new ProcessDataType[]{ProcessDataType.INSTANCE, ProcessDataType.OPERATION});
            PorcessDataFileInputStream processDataInputStream = new PorcessDataFileInputStream(tempDir);
            IOProgressMontior progressMonitor = new IOProgressMontior(monitor);
            IProcessDataImportResult processDataImportResult = processIOService.importProcessData((IPorcessDataInputStream)processDataInputStream, options, (IProgressMonitor)progressMonitor);
            logHelper.appendLog("\u6d41\u7a0b\u72b6\u6001\u5b9e\u4f8b\u4e0d\u6210\u529f\u7684\u6570\u91cf" + processDataImportResult.getInstancJumpErrorCount() + ";");
            logHelper.appendLog("\u6d41\u7a0b\u72b6\u6001\u5b9e\u4f8b\u4e0d\u6210\u529f\u7684\u8be6\u60c5" + processDataImportResult.getInstanceJumpErrorInfos() + ";");
        }
        catch (Exception e) {
            String message = MultilingualLog.WorkflowTransmissionDataGatherMessage(2);
            logHelper.appendLog(message + e.getMessage());
            logger.error(message + e.getMessage(), e);
            throw new RuntimeException(message + e.getMessage(), e);
        }
        finally {
            Utils.deleteAllFilesOfDirByPath(tempDir);
        }
        logHelper.appendLog("\u591a\u7ea7\u90e8\u7f72\u88c5\u5165\u6d41\u7a0b\u6570\u636e\u5b8c\u6210");
        monitor.finish("\u6d41\u7a0b\u6570\u636e\u5bfc\u5165\u5b8c\u6210\uff01", (Object)"\u6d41\u7a0b\u6570\u636e\u5bfc\u5165\u5b8c\u6210\uff01");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u88c5\u5165\u6d41\u7a0b\u6570\u636e\u5b8c\u6210");
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dataExport(OutputStream outputStream, ITransmissionContext context) throws Exception {
        IExecuteParam executeParam = context.getExecuteParam();
        ILogHelper logHelper = context.getLogHelper();
        logHelper.appendLog("\u591a\u7ea7\u90e8\u7f72\u5f00\u59cb\u5bfc\u51fa\u6d41\u7a0b\u6570\u636e");
        String taskKey = executeParam.getTaskKey();
        String formSchemeKey = executeParam.getFormSchemeKey();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        WorkflowObjectType workflowObjectType = this.queryWorkflowObjectType(formSchemeKey);
        String[] businessObjectDimensionNames = this.getDimensionNames(dimensionValueSet);
        AsyncTaskMonitor monitor = context.getTransmissionMonitor();
        try {
            ProcessIOService processIOService = new ProcessIOService();
            ProcessDataOutputOptions options = new ProcessDataOutputOptions(Version.V1_0_0, new ProcessDataType[]{ProcessDataType.INSTANCE, ProcessDataType.OPERATION});
            String rootPath = ZipUtils.newTempDir();
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(RunTimeViewController.class);
            TaskDefine task = runTimeViewController.getTask(taskKey);
            PorcessDataFileOutputStream processDataOutputStream = new PorcessDataFileOutputStream(task, rootPath, businessObjectDimensionNames, workflowObjectType);
            IBusinessKeyCollection businessKeyCollection = this.buildBusinessKeyCollection(executeParam, workflowObjectType);
            if (businessKeyCollection == null) {
                logger.info("\u8f6c\u6362\u540e\u7684IBusinessKeyCollection\u4e3a\u7a7a,\u6570\u636e\u88c5\u5165\u64cd\u4f5c\u7ed3\u675f");
                logHelper.appendLog("\u8f6c\u6362\u540e\u7684IBusinessKeyCollection\u4e3a\u7a7a,\u6570\u636e\u88c5\u5165\u64cd\u4f5c\u7ed3\u675f");
                return;
            }
            monitor.progressAndMessage(0.2, "\u5bfc\u51fa\u6570\u636e\u7ec4\u88c5\u5b8c\u6210,\u5f00\u59cb\u6267\u884c\u5bfc\u51fa");
            IOProgressMontior progressMonitor = new IOProgressMontior(monitor);
            processIOService.exportProcessData(businessKeyCollection, (IPorcessDataOutputStream)processDataOutputStream, options, (IProgressMonitor)progressMonitor);
            ZipOutputStream zipOut = new ZipOutputStream(outputStream);
            Path sourcePath = Paths.get(rootPath, new String[0]);
            try (Stream<Path> paths = Files.walk(sourcePath, new FileVisitOption[0]);){
                paths.filter(path -> !Files.isDirectory(path, new LinkOption[0])).forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(sourcePath.relativize((Path)path).toString());
                    try {
                        zipOut.putNextEntry(zipEntry);
                        Files.copy(path, zipOut);
                        zipOut.closeEntry();
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                });
                zipOut.finish();
            }
            catch (IOException e) {
                logger.error("\u538b\u7f29\u5199\u5165\u65f6\u53d1\u751f\u5f02\u5e38", e);
            }
            finally {
                Utils.deleteAllFilesOfDirByPath(rootPath);
            }
        }
        catch (Exception e) {
            String errorMsg = "\u6d41\u7a0b\u6570\u636e\u6253\u5305\u5f02\u5e38";
            String errorMessage = MultilingualLog.WorkflowTransmissionDataGatherMessage(1) + e.getMessage();
            logHelper.appendLog(errorMessage + "\r\n");
            logger.error(errorMsg + e.getMessage(), e);
            throw new RuntimeException(errorMsg + e.getMessage(), e);
        }
        logHelper.appendLog("\u591a\u7ea7\u90e8\u7f72\u5bfc\u51fa\u6d41\u7a0b\u6570\u636e\u5b8c\u6210");
        monitor.finish("\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01", (Object)"\u6d41\u7a0b\u6570\u636e\u5bfc\u51fa\u5b8c\u6210\uff01");
        logger.info("\u591a\u7ea7\u90e8\u7f72\u6253\u5305\u6d41\u7a0b\u6570\u636e\u5b8c\u6210\uff01");
    }

    private IBusinessKeyCollection buildBusinessKeyCollection(IExecuteParam executeParam, WorkflowObjectType workflowObjectType) {
        if (WorkflowObjectType.MAIN_DIMENSION.equals((Object)workflowObjectType)) {
            DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
            ProcessIODimensionsBuilder dimensionsBuilder = (ProcessIODimensionsBuilder)BeanUtil.getBean(ProcessIODimensionsBuilder.class);
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            DimensionCollection dimensionCollection = dimensionsBuilder.buildDimensionCollection(executeParam.getTaskKey(), dimensionSet);
            BusinessObjectCollection bizObjectCollection = BusinessObjectCollection.newDimensionObjectCollection((DimensionCollection)dimensionCollection);
            BusinessKeyCollection businessKeys = new BusinessKeyCollection(executeParam.getTaskKey(), (IBusinessObjectCollection)bizObjectCollection);
            return businessKeys;
        }
        if (WorkflowObjectType.FORM.equals((Object)workflowObjectType)) {
            DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
            ProcessIODimensionsBuilder dimensionsBuilder = (ProcessIODimensionsBuilder)BeanUtil.getBean(ProcessIODimensionsBuilder.class);
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            DimensionCollection dimensionCollection = dimensionsBuilder.buildDimensionCollection(executeParam.getTaskKey(), dimensionSet);
            HashMap keyObjectMapper = new HashMap();
            HashSet<String> formKeys = new HashSet<String>();
            formKeys.addAll(executeParam.getForms());
            List dimensionCombinations = dimensionCollection.getDimensionCombinations();
            for (DimensionCombination dimensionValue : dimensionCombinations) {
                keyObjectMapper.put(dimensionValue, formKeys);
            }
            BusinessKeyObjectMapper businessKeyObjectMapper = new BusinessKeyObjectMapper(keyObjectMapper);
            BusinessObjectCollection businessObjectCollection = BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)businessKeyObjectMapper);
            BusinessKeyCollection businessKeys = new BusinessKeyCollection(executeParam.getTaskKey(), (IBusinessObjectCollection)businessObjectCollection);
            return businessKeys;
        }
        if (WorkflowObjectType.FORM_GROUP.equals((Object)workflowObjectType)) {
            // empty if block
        }
        return null;
    }

    private String[] getDimensionNames(DimensionValueSet dimensionValueSet) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        ENameSet dimensions = dimensionValueSet.getDimensionSet().getDimensions();
        for (int i = 0; i < dimensions.size(); ++i) {
            dimensionNames.add(dimensions.get(i));
        }
        return dimensionNames.toArray(new String[dimensionNames.size()]);
    }

    private WorkflowObjectType queryWorkflowObjectType(String formSchemeKey) {
        IWorkflow workflow = (IWorkflow)BeanUtil.getBean(IWorkflow.class);
        WorkFlowType workFlowType = workflow.queryStartType(formSchemeKey);
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
}

