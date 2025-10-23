/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngine
 *  com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory
 *  com.jiuqi.nr.workflow2.engine.core.IProcessIOService
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType
 *  com.jiuqi.nr.workflow2.engine.core.process.io.Version
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileOutputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowEngineConfigSaveExecutor
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig
 *  com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.DefaultEngineProcessTransferDataInputStream
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil
 *  com.jiuqi.nr.workflow2.events.enumeration.CurrencyType
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.workflow2.settings.service;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFlowSettingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.workflow2.engine.core.IProcessConfigChangeImportExecutor;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputOptions;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileOutputStream;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowEngineConfigSaveExecutor;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.DefaultEngineProcessTransferDataInputStream;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.OperateBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.TransactionUtil;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsManipulationContext;
import com.jiuqi.nr.workflow2.settings.extend.WorkflowSettingsExtend;
import com.jiuqi.nr.workflow2.settings.factory.IConfigSaveExecutorFactory;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsPersistService;
import com.jiuqi.nr.workflow2.settings.utils.EngineProcessDataTransferHelper;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WorkflowSettingsPersistServiceImpl
implements WorkflowSettingsPersistService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WorkflowSettingsManipulationService workflowSettingsManipulationService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IProcessEngineFactory processEngineFactory;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IProcessQueryService processQueryService;
    @Autowired
    private EngineProcessDataTransferHelper transferDataHelper;
    @Autowired
    private TransactionUtil transactionUtil;
    @Autowired
    private IConfigSaveExecutorFactory configSaveExecutorFactory;

    @Override
    public boolean persistConfig(WorkflowSettingsManipulationContext context) {
        WorkflowSettingsDO originalSettingsDO = this.workflowSettingsService.queryWorkflowSettings(context.getTaskId());
        WorkflowSettingsDO targetSettingsDO = this.transferDataHelper.buildTargetSettingsDO(originalSettingsDO.getId(), context);
        boolean isSaveSuccess = this.saveConfig(context, targetSettingsDO.getWorkflowDefine(), null);
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new WorkflowSettingsSaveEvent(context.getTaskId(), originalSettingsDO, targetSettingsDO));
        return isSaveSuccess;
    }

    @Override
    public void persistConfig(WorkflowSettingsManipulationContext context, IProgressMonitor monitor) throws RuntimeException {
        WorkflowSettingsDO targetSettingsDO;
        WorkflowSettingsDO originalSettingsDO = this.workflowSettingsService.queryWorkflowSettings(context.getTaskId());
        if (this.transferDataHelper.isExecuteDataTransfer(originalSettingsDO, targetSettingsDO = this.transferDataHelper.buildTargetSettingsDO(originalSettingsDO.getId(), context))) {
            this.transferDataAndSaveConfig(context, monitor, originalSettingsDO, targetSettingsDO);
        } else {
            this.justSaveConfig(context, monitor, originalSettingsDO, targetSettingsDO);
        }
    }

    private void justSaveConfig(WorkflowSettingsManipulationContext context, IProgressMonitor monitor, WorkflowSettingsDO originalSettingsDO, WorkflowSettingsDO targetSettingsDO) {
        String saveConfigTask = "workflow_setting_save";
        monitor.startTask("workflow_setting_save", new int[]{10, 40, 50});
        monitor.stepIn();
        monitor.prompt("\u5f00\u59cb\u4fdd\u5b58\u914d\u7f6e\u4fe1\u606f... ...");
        AtomicBoolean isSaveSuccess = new AtomicBoolean(false);
        this.transactionUtil.runWithinTransaction(() -> isSaveSuccess.set(this.saveConfig(context, targetSettingsDO.getWorkflowDefine(), monitor)));
        monitor.stepIn();
        monitor.prompt("\u914d\u7f6e\u4fdd\u5b58\u72b6\u6001: " + (isSaveSuccess.get() ? "\u6210\u529f" : "\u5931\u8d25") + " \u6267\u884c\u4fdd\u5b58\u540e\u4e8b\u4ef6... ...");
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new WorkflowSettingsSaveEvent(context.getTaskId(), originalSettingsDO, targetSettingsDO));
        monitor.stepIn();
        monitor.prompt("\u4fdd\u5b58\u540e\u4e8b\u4ef6\u6267\u884c\u5b8c\u6bd5 \u914d\u7f6e\u4fdd\u5b58\u5b8c\u6bd5");
        monitor.finishTask("workflow_setting_save");
    }

    private void transferDataAndSaveConfig(WorkflowSettingsManipulationContext context, IProgressMonitor monitor, WorkflowSettingsDO originalSettingsDO, WorkflowSettingsDO targetSettingsDO) {
        String saveConfigTask = "workflow_setting_save";
        monitor.startTask("workflow_setting_save", new int[]{50, 20, 30});
        monitor.prompt("\u68c0\u6d4b\u5230\u6d41\u7a0b\u914d\u7f6e\u53d8\u5316 \u5f00\u59cb\u6267\u884c\u6570\u636e\u8fc1\u79fb");
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        Path outputDirPath = Paths.get(System.getProperty("java.io.tmpdir"), "workflow_transfer_temp", context.getTaskId());
        IProcessEngine originalProcessEngine = this.processEngineFactory.getProcessEngine(originalSettingsDO.getWorkflowEngine());
        IProcessEngine targetProcessEngine = this.processEngineFactory.getProcessEngine(targetSettingsDO.getWorkflowEngine());
        IProcessIOService originalEngineIOService = originalProcessEngine.getProcessIOService();
        IProcessIOService targetProcessEngineIOService = targetProcessEngine.getProcessIOService();
        IProcessConfigChangeImportExecutor configChangeImportExecutor = null;
        try {
            try (PorcessDataFileOutputStream outputStream = new PorcessDataFileOutputStream(taskDefine, outputDirPath.toString());){
                ProcessDataOutputOptions options = new ProcessDataOutputOptions(Version.V1_0_0, ProcessDataType.values());
                String dividePoint = "\u5386\u53f2\u671f \u8fd0\u884c\u671f \u65f6\u671f\u5212\u5206\u70b9";
                List rangePeriods = this.runTimeViewController.listSchemePeriodLinkByTask(context.getTaskId()).stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toList());
                for (String period : rangePeriods) {
                    HashSet<IBusinessObject> deDuplicateSet = new HashSet<IBusinessObject>();
                    List taskOrgLinkDefines = this.runTimeViewController.listTaskOrgLinkByTask(context.getTaskId());
                    for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
                        ArrayList<IBusinessObject> rangeBusinessObjects = new ArrayList<IBusinessObject>();
                        String entityCaliber = taskOrgLinkDefine.getEntity();
                        DsContextImpl dsContext = (DsContextImpl)DsContextHolder.getDsContext();
                        dsContext.setEntityId(entityCaliber);
                        IEntityTable entityTable = this.transferDataHelper.getEntityTable(entityCaliber, period, taskDefine.getDateTime(), taskDefine.getFilterExpression());
                        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
                        batchRunPara.setTaskKey(context.getTaskId());
                        batchRunPara.setPeriod(period);
                        batchRunPara.setReportDimensions(this.transferDataHelper.buildUnitWithAllFormOrFormGroupReportDimension(taskDefine, originalSettingsDO.getWorkflowObjectType(), entityTable.getAllRows()));
                        IBusinessKeyCollection businessKeyCollection = this.processDimensionsBuilder.buildBusinessKeyCollection(batchRunPara);
                        for (IBusinessObject businessObject : businessKeyCollection.getBusinessObjects()) {
                            if (deDuplicateSet.contains(businessObject)) continue;
                            rangeBusinessObjects.add(businessObject);
                            deDuplicateSet.add(businessObject);
                        }
                        originalEngineIOService.exportProcessData((IBusinessKeyCollection)new OperateBusinessKeyCollection(context.getTaskId(), rangeBusinessObjects), (IPorcessDataOutputStream)outputStream, options, monitor);
                    }
                }
            }
            var14_15 = null;
            try (DefaultEngineProcessTransferDataInputStream inputStream = new DefaultEngineProcessTransferDataInputStream(outputDirPath.toString(), targetSettingsDO);){
                configChangeImportExecutor = targetProcessEngineIOService.getConfigChangeImportExecutor((IPorcessDataInputStream)inputStream, targetSettingsDO);
                configChangeImportExecutor.executeDataImport(originalSettingsDO, targetSettingsDO, (IPorcessDataInputStream)inputStream);
            }
            catch (Throwable throwable) {
                var14_15 = throwable;
                throw throwable;
            }
            monitor.stepIn();
            monitor.prompt("\u6b63\u5728\u4fdd\u5b58\u914d\u7f6e\u4fe1\u606f... ...");
            AtomicBoolean isSaveSuccess = new AtomicBoolean(false);
            this.transactionUtil.runWithinTransaction(() -> isSaveSuccess.set(this.saveConfig(context, targetSettingsDO.getWorkflowDefine(), monitor)));
            monitor.stepIn();
            monitor.prompt("\u914d\u7f6e\u4fdd\u5b58\u72b6\u6001: " + (isSaveSuccess.get() ? "\u6210\u529f" : "\u5931\u8d25") + "\u6267\u884c\u4fdd\u5b58\u540e\u4e8b\u4ef6... ...");
            configChangeImportExecutor.commit();
            this.applicationEventPublisher.publishEvent((ApplicationEvent)new WorkflowSettingsSaveEvent(context.getTaskId(), originalSettingsDO, targetSettingsDO));
            monitor.stepIn();
            monitor.prompt("\u4fdd\u5b58\u540e\u4e8b\u4ef6\u6267\u884c\u5b8c\u6bd5 \u914d\u7f6e\u4fdd\u5b58\u7ed3\u675f");
            monitor.finishTask("workflow_setting_save");
        }
        catch (IOException | RuntimeException e) {
            monitor.prompt("\u6570\u636e\u8fc1\u79fb\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38 \u539f\u56e0\u5982\u4e0b\uff1a" + e.getMessage());
            this.logger.error("\u6570\u636e\u8fc1\u79fb\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38 \u539f\u56e0\u5982\u4e0b\uff1a{}", (Object)e.getMessage(), (Object)e);
            if (configChangeImportExecutor != null) {
                this.logger.info("\u76ee\u6807\u5f15\u64ce\u7684\u914d\u7f6e\u53d8\u66f4\u5bfc\u5165\u670d\u52a1\u5f00\u59cb\u56de\u6eda\u2026\u2026");
                configChangeImportExecutor.rollback();
                monitor.prompt("\u76ee\u6807\u5f15\u64ce\u7684\u914d\u7f6e\u53d8\u66f4\u5bfc\u5165\u670d\u52a1\u5f00\u59cb\u56de\u6eda\u2026\u2026");
            }
            monitor.finishTask("workflow_setting_save");
            throw new RuntimeException(e);
        }
        finally {
            File tempDir;
            File outputDir = outputDirPath.toFile();
            if (outputDir.exists()) {
                EngineProcessDataTransferHelper.deleteDirectory(outputDir);
            }
            if ((tempDir = new File(outputDirPath + "_temp")).exists()) {
                EngineProcessDataTransferHelper.deleteDirectory(tempDir);
            }
        }
    }

    private boolean saveConfig(WorkflowSettingsManipulationContext context, String targetWorkflowDefine, IProgressMonitor monitor) {
        boolean isOurEngine;
        Object config = context.getWorkflowSettings();
        JSONObject workflowSettings = new JSONObject((Map)((HashMap)config));
        boolean isWorkFlowEnabled = workflowSettings.getBoolean("isWorkFlowEnabled");
        JSONObject todoSetting = workflowSettings.getJSONObject("todoSetting");
        boolean isTodoEnabled = todoSetting.getBoolean("isTodoEnabled");
        JSONObject basicSettings = workflowSettings.getJSONObject("basicSettings");
        String workflowObject = basicSettings.getString("submitMode");
        String workflowEngine = basicSettings.getString("workflowType");
        boolean bl = isOurEngine = workflowEngine.equals("jiuqi.nr.default-1.0") || workflowEngine.equals("jiuqi.nr.default") || workflowEngine.equals("jiuqi.nr.customprocessengine");
        if (!isOurEngine) {
            String configJSONStr;
            WorkflowSettingsDO originalSettingsDO = this.workflowSettingsService.queryWorkflowSettings(context.getTaskId());
            WorkflowSettingsDO targetSettingsDO = this.transferDataHelper.buildTargetSettingsDO(originalSettingsDO.getId(), context);
            WorkflowEngineConfigSaveExecutor configSaveExecutor = this.configSaveExecutorFactory.getConfigSaveExecutor(workflowEngine);
            try {
                JSONObject workflow = basicSettings.getJSONObject("workflow");
                configJSONStr = workflow.toString();
            }
            catch (JSONException e) {
                configJSONStr = basicSettings.get("workflow").toString();
            }
            if (monitor != null) {
                configSaveExecutor.asyncExecuteSave(targetSettingsDO, configJSONStr, monitor);
                return true;
            }
            return configSaveExecutor.executeSave(targetSettingsDO, configJSONStr);
        }
        if (workflowEngine.equals("jiuqi.nr.default-1.0")) {
            return this.saveDefaultEngine_1_0_Config(context, targetWorkflowDefine);
        }
        JSONObject workflow = basicSettings.getJSONObject("workflow");
        this.buildUserRoleList(workflow);
        this.buildMessageReceiverUserRoleList(workflow);
        WorkflowSettingsDTO workflowSettingsDTO = new WorkflowSettingsDTO();
        workflowSettingsDTO.setTaskId(context.getTaskId());
        workflowSettingsDTO.setWorkflowEngine(workflowEngine);
        workflowSettingsDTO.setWorkflowDefine(targetWorkflowDefine);
        workflowSettingsDTO.setWorkflowEnable(isWorkFlowEnabled);
        workflowSettingsDTO.setTodoEnable(isTodoEnabled);
        workflowSettingsDTO.setWorkflowObjectType(WorkflowObjectType.valueOf((String)workflowObject));
        JSONObject otherConfig = new JSONObject((Map)((HashMap)context.getOtherSettings()));
        workflowSettingsDTO.setOtherConfig(otherConfig.toString());
        WorkflowOtherSettings workflowOtherSettings = WorkflowSettingsServiceImpl.parseJSONObjectToWorkflowOtherSettings((JSONObject)otherConfig);
        ManualTerminationConfig manualTerminationConfig = workflowOtherSettings.getManualTerminationConfig();
        this.taskOptionController.setValue(context.getTaskId(), "ALLOW_STOP_FILING", manualTerminationConfig.isEnable() ? "1" : "0");
        this.taskOptionController.setValue(context.getTaskId(), "ALLOW_STOP_FILING_ROLE", manualTerminationConfig.isEnable() ? manualTerminationConfig.getRole() : null);
        if (workflowEngine.equals("jiuqi.nr.default")) {
            DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(targetWorkflowDefine);
            boolean isConfigSuccess = defaultProcessConfig == null ? this.defaultProcessDesignService.addDefaultProcessConfig(targetWorkflowDefine, workflow.toString()) : this.defaultProcessDesignService.updateDefaultProcessConfig(targetWorkflowDefine, workflow.toString());
            boolean isSettingsSuccess = this.workflowSettingsManipulationService.updateWorkflowSettings(workflowSettingsDTO);
            return isConfigSuccess && isSettingsSuccess;
        }
        String previousWorkflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(context.getTaskId());
        this.defaultProcessDesignService.deleteDefaultProcessConfig(previousWorkflowDefine);
        return this.workflowSettingsManipulationService.updateWorkflowSettings(workflowSettingsDTO);
    }

    private void buildUserRoleList(JSONObject workflow) {
        JSONObject workflowNodes = workflow.isNull("workflowNodes") ? new JSONObject() : workflow.getJSONObject("workflowNodes");
        for (String workflowNode : workflowNodes.keySet()) {
            JSONObject nodeConfigJson = workflowNodes.getJSONObject(workflowNode);
            JSONObject participant = nodeConfigJson.getJSONObject("participant");
            JSONObject actionExecuter = participant.getJSONObject("actionExecuter");
            JSONObject actionExecuterParam = actionExecuter.getJSONObject("param");
            actionExecuter.put("param", this.parseUserRoleList(actionExecuterParam.toMap()));
            JSONObject todoReceiver = participant.getJSONObject("todoReceiver");
            String type = todoReceiver.getString("type");
            if (!type.equals(TodoReceiverStrategy.CUSTOM.name())) continue;
            ArrayList<JSONObject> transferResult = new ArrayList<JSONObject>();
            List customParticipant = todoReceiver.getJSONArray("customParticipant").toList().stream().map(item -> (HashMap)item).collect(Collectors.toList());
            for (Map item2 : customParticipant) {
                String strategy = item2.get("strategy").toString();
                if (strategy == null || strategy.isEmpty()) continue;
                HashMap customParticipantParam = (HashMap)item2.get("param");
                Map<String, Object> param = this.parseUserRoleList(customParticipantParam);
                JSONObject customItem = new JSONObject();
                customItem.put("strategy", (Object)strategy);
                customItem.put("param", param);
                transferResult.add(customItem);
            }
            todoReceiver.put("customParticipant", transferResult);
        }
    }

    private void buildMessageReceiverUserRoleList(JSONObject workflow) {
        JSONObject workflowNodes = workflow.isNull("workflowNodes") ? new JSONObject() : workflow.getJSONObject("workflowNodes");
        for (String workflowNode : workflowNodes.keySet()) {
            JSONObject nodeConfigJson = workflowNodes.getJSONObject(workflowNode);
            JSONObject events = nodeConfigJson.getJSONObject("events");
            for (String actionCode : events.keySet()) {
                JSONObject actionEventJson = events.getJSONObject(actionCode);
                if (!actionEventJson.has("send-message-notice-event")) continue;
                JSONObject messageEventJson = actionEventJson.getJSONObject("send-message-notice-event");
                for (String type : messageEventJson.keySet()) {
                    JSONObject messageJson = messageEventJson.getJSONObject(type);
                    ArrayList<JSONObject> transferResult = new ArrayList<JSONObject>();
                    List receiver = messageJson.getJSONArray("receiver").toList().stream().map(item -> (HashMap)item).collect(Collectors.toList());
                    for (Map item2 : receiver) {
                        String strategy = item2.get("strategy").toString();
                        if (strategy == null || strategy.isEmpty()) continue;
                        HashMap receiverParam = (HashMap)item2.get("param");
                        Map<String, Object> param = this.parseUserRoleList(receiverParam);
                        JSONObject receiverItem = new JSONObject();
                        receiverItem.put("strategy", (Object)strategy);
                        receiverItem.put("param", param);
                        transferResult.add(receiverItem);
                    }
                    messageJson.put("receiver", transferResult);
                }
            }
        }
    }

    private Map<String, Object> parseUserRoleList(Map<String, Object> userRoleParam) {
        List<String> userRoleList = WorkflowSettingsUtil.objectToList(userRoleParam.get("userRoleList"), String.class);
        if (userRoleList == null || userRoleList.isEmpty()) {
            return null;
        }
        ArrayList<String> user = new ArrayList<String>();
        ArrayList<String> role = new ArrayList<String>();
        for (String userRole : userRoleList) {
            String[] splitArray = userRole.split(":");
            String key = splitArray[0];
            String type = splitArray[2];
            if (type.equals("0")) {
                user.add(key);
                continue;
            }
            if (!type.equals("1")) continue;
            role.add(key);
        }
        userRoleParam.put("user", user);
        userRoleParam.put("role", role);
        userRoleParam.remove("userRoleList");
        return userRoleParam;
    }

    @Deprecated
    private void verifyConfig(WorkflowSettingsManipulationContext context) {
        WorkflowSettingsService workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        WorkflowSettingsExtend workflowSettingsExtend = (WorkflowSettingsExtend)SpringBeanUtils.getBean(WorkflowSettingsExtend.class);
        Object config = context.getWorkflowSettings();
        JSONObject workflowSettings = new JSONObject((Map)((HashMap)config));
        JSONObject basicSettings = workflowSettings.getJSONObject("basicSettings");
        WorkflowObjectType nowType = WorkflowObjectType.valueOf((String)basicSettings.getString("submitMode"));
        WorkflowObjectType previousType = workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskId());
        String nowWorkflowDefine = basicSettings.getString("workflowDefine");
        String previousWorkflowDefine = workflowSettingsService.queryTaskWorkflowDefine(context.getTaskId());
        if (!previousType.equals((Object)nowType) || previousWorkflowDefine != null && !previousWorkflowDefine.equals(nowWorkflowDefine)) {
            workflowSettingsExtend.clearWorkflowInstanceByTask(context.getTaskId(), previousType);
        }
    }

    private boolean saveDefaultEngine_1_0_Config(WorkflowSettingsManipulationContext context, String targetWorkflowDefine) {
        StartTimeStrategy strategy;
        WorkflowSettingsManipulationService workflowSettingsManipulationService = (WorkflowSettingsManipulationService)SpringBeanUtils.getBean(WorkflowSettingsManipulationService.class);
        WorkflowSettingsService workflowSettingsService = (WorkflowSettingsService)SpringBeanUtils.getBean(WorkflowSettingsService.class);
        IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringBeanUtils.getBean(IDesignTimeViewController.class);
        ITaskOptionController taskOptionController = (ITaskOptionController)SpringBeanUtils.getBean(ITaskOptionController.class);
        IRuntimeTaskService runtimeTaskService = (IRuntimeTaskService)SpringBeanUtils.getBean(IRuntimeTaskService.class);
        Object config = context.getWorkflowSettings();
        JSONObject workflowSettings = new JSONObject((Map)((HashMap)config));
        boolean isWorkFlowEnabled = workflowSettings.getBoolean("isWorkFlowEnabled");
        JSONObject todoSetting = workflowSettings.getJSONObject("todoSetting");
        boolean isTodoEnabled = todoSetting.getBoolean("isTodoEnabled");
        JSONObject basicSettings = workflowSettings.getJSONObject("basicSettings");
        WorkflowObjectType workflowObject = WorkflowObjectType.valueOf((String)basicSettings.getString("submitMode"));
        String workflowEngine = basicSettings.getString("workflowType");
        WorkflowSettingsDTO workflowSettingsDTO = new WorkflowSettingsDTO();
        workflowSettingsDTO.setTaskId(context.getTaskId());
        workflowSettingsDTO.setWorkflowEngine(workflowEngine);
        workflowSettingsDTO.setWorkflowDefine(targetWorkflowDefine);
        workflowSettingsDTO.setWorkflowEnable(isWorkFlowEnabled);
        workflowSettingsDTO.setTodoEnable(isTodoEnabled);
        workflowSettingsDTO.setWorkflowObjectType(workflowObject);
        JSONObject otherConfig = new JSONObject((Map)((HashMap)context.getOtherSettings()));
        workflowSettingsDTO.setOtherConfig(otherConfig.toString());
        WorkflowOtherSettings workflowOtherSettings = WorkflowSettingsServiceImpl.parseJSONObjectToWorkflowOtherSettings((JSONObject)otherConfig);
        FillInStartTimeConfig fillInStartTimeConfig = workflowOtherSettings.getFillInStartTimeConfig();
        FillInEndTimeConfig fillInEndTimeConfig = workflowOtherSettings.getFillInEndTimeConfig();
        ManualTerminationConfig manualTerminationConfig = workflowOtherSettings.getManualTerminationConfig();
        DesignTaskDefine designTaskDefine = designTimeViewController.getTask(context.getTaskId());
        FillInAutomaticallyDue convertDue = new FillInAutomaticallyDue();
        convertDue.setAutomaticTermination(true);
        if (fillInEndTimeConfig.isEnable()) {
            FillInDaysConfig fillInDaysConfig = fillInEndTimeConfig.getFillInDaysConfig();
            int fillInDueType = fillInDaysConfig.getType().equals((Object)TimeControlType.NATURAL_DAY) ? FillInAutomaticallyDue.Type.NATURAL.getValue() : FillInAutomaticallyDue.Type.WORKING.getValue();
            convertDue.setType(fillInDueType);
            convertDue.setDays(fillInDaysConfig.getDayNum());
        } else {
            convertDue.setType(FillInAutomaticallyDue.Type.CLOSE.getValue());
            convertDue.setDays(0);
        }
        designTaskDefine.setFillInAutomaticallyDue(convertDue);
        FillDateType convertFillDateType = FillDateType.NONE;
        if (fillInStartTimeConfig.isEnable()) {
            FillInDaysConfig fillInDaysConfig;
            StartTimeStrategy strategy2 = fillInStartTimeConfig.getType();
            convertFillDateType = strategy2.equals((Object)StartTimeStrategy.IDENTICAL_TO_TASK) ? FillDateType.NATURAL_DAY : ((fillInDaysConfig = fillInStartTimeConfig.getFillInDaysConfig()).getType().equals((Object)TimeControlType.NATURAL_DAY) ? FillDateType.NATURAL_DAY : FillDateType.WORK_DAY);
        }
        designTaskDefine.setFillingDateType(convertFillDateType);
        int convertFillingDateDays = 0;
        if (fillInStartTimeConfig.isEnable() && (strategy = fillInStartTimeConfig.getType()).equals((Object)StartTimeStrategy.CUSTOM)) {
            convertFillingDateDays = fillInStartTimeConfig.getFillInDaysConfig().getDayNum();
        }
        designTaskDefine.setFillingDateDays(convertFillingDateDays);
        taskOptionController.setValue(context.getTaskId(), "ALLOW_STOP_FILING", manualTerminationConfig.isEnable() ? "1" : "0");
        taskOptionController.setValue(context.getTaskId(), "ALLOW_STOP_FILING_ROLE", manualTerminationConfig.isEnable() ? manualTerminationConfig.getRole() : null);
        designTimeViewController.updateTask(designTaskDefine);
        WorkflowSettingsDO workflowSettingsDO = workflowSettingsService.queryWorkflowSettings(context.getTaskId());
        boolean isWorkflowSettingUpdate = workflowSettingsDO == null ? workflowSettingsManipulationService.addWorkflowSettings(workflowSettingsDTO) : workflowSettingsManipulationService.updateWorkflowSettings(workflowSettingsDTO);
        JSONObject workflow = basicSettings.getJSONObject("workflow");
        JSONObject flowSetting = workflow.getJSONObject("flowSetting");
        boolean submitExplain = flowSetting.getBoolean("submitExplain");
        boolean forceSubmitExplain = flowSetting.getBoolean("forceSubmitExplain");
        boolean stepByStepReport = flowSetting.getBoolean("stepByStepReport");
        String stepReportType = flowSetting.getString("stepReportType");
        boolean checkBeforeReporting = flowSetting.getBoolean("checkBeforeReporting");
        ReportAuditType checkBeforeReportingType = this.transferToReportAuditType(CurrencyType.valueOf((String)flowSetting.getString("checkBeforeReportingType")));
        String checkBeforeReportingCustom = this.transferListToMultiCheckValueStr(flowSetting.getJSONArray("checkBeforeReportingCustom").toList().stream().map(Object::toString).collect(Collectors.toList()));
        boolean unitSubmitForForce = flowSetting.getBoolean("unitSubmitForForce");
        String selectedRoleForceKey = flowSetting.getString("selectedRoleForceKey");
        boolean allowTakeBack = flowSetting.getBoolean("allowTakeBack");
        boolean applyReturn = flowSetting.getBoolean("applyReturn");
        boolean unitSubmitForCensorship = flowSetting.getBoolean("unitSubmitForCensorship");
        boolean returnExplain = flowSetting.getBoolean("returnExplain");
        boolean allowTakeBackForSubmit = flowSetting.getBoolean("allowTakeBackForSubmit");
        boolean dataConfirm = flowSetting.getBoolean("dataConfirm");
        boolean backDescriptionNeedWrite = flowSetting.getBoolean("backDescriptionNeedWrite");
        boolean openBackType = flowSetting.getBoolean("openBackType");
        String backTypeEntity = flowSetting.getString("backTypeEntity");
        boolean returnVersion = flowSetting.getBoolean("returnVersion");
        boolean allowFormBack = workflowObject.equals((Object)WorkflowObjectType.MD_WITH_SFR);
        boolean stepByStepBack = flowSetting.getBoolean("stepByStepBack");
        boolean goBackAllSup = flowSetting.getBoolean("goBackAllSup");
        boolean isOpenForceControl = flowSetting.getBoolean("isOpenForceControl");
        boolean defaultButtonName = flowSetting.getBoolean("defaultButtonName");
        JSONObject filerButtonNameConfig = this.filterDefaultButtonNameConfig(flowSetting.getJSONObject("defaultButtonNameConfig"), unitSubmitForCensorship, allowTakeBack, dataConfirm);
        String defaultButtonNameConfig = filerButtonNameConfig.toString();
        boolean defaultNodeName = flowSetting.getBoolean("defaultNodeName");
        JSONObject filterNodeNameConfig = this.filterDefaultNodeNameConfig(flowSetting.getJSONObject("defaultNodeNameConfig"), unitSubmitForCensorship);
        String defaultNodeNameConfig = filterNodeNameConfig.toString();
        boolean sendMessageMail = flowSetting.getBoolean("sendMessageMail");
        String messageTemplate = flowSetting.getString("messageTemplate");
        boolean mulCheckBeforeCheck = flowSetting.getBoolean("mulCheckBeforeCheck");
        boolean reportBeforeAudit = flowSetting.getBoolean("reportBeforeAudit");
        boolean specialAudit = flowSetting.getBoolean("specialAudit");
        ReportAuditType reportBeforeAuditType = this.transferToReportAuditType(CurrencyType.valueOf((String)flowSetting.getString("reportBeforeAuditType")));
        String reportBeforeAuditCustom = this.transferListToMultiCheckValueStr(flowSetting.getJSONArray("reportBeforeAuditCustom").toList().stream().map(Object::toString).collect(Collectors.toList()));
        String reportBeforeAuditValue = this.transferListToMultiCheckValueStr(flowSetting.getJSONArray("reportBeforeAuditValue").toList().stream().map(Object::toString).collect(Collectors.toList()));
        String[] errorStatusAndPromptStatus = this.transferErrorHandleToErrorStatusAndPromptStatus(flowSetting.getJSONArray("errorHandle"));
        String errorStatus = errorStatusAndPromptStatus[0];
        String promptStatus = errorStatusAndPromptStatus[1];
        boolean reportBeforeOperation = flowSetting.getBoolean("reportBeforeOperation");
        String reportBeforeOperationValue = this.transferListToMultiCheckValueStr(flowSetting.getJSONArray("reportBeforeOperationValue").toList().stream().map(Object::toString).collect(Collectors.toList()));
        boolean submitAfterFormula = flowSetting.getBoolean("submitAfterFormula");
        String submitAfterFormulaValue = this.transferListToMultiCheckValueStr(flowSetting.getJSONArray("submitAfterFormulaValue").toList().stream().map(Object::toString).collect(Collectors.toList()));
        String filterCondition = flowSetting.getString("filterCondition");
        DesignTaskFlowsDefine designTaskFlowsDefine = new DesignTaskFlowsDefine();
        DesignFlowSettingDefine designFlowSettingDefine = new DesignFlowSettingDefine();
        designTaskFlowsDefine.setDesignTableDefines(designTaskDefine.getDw() + ";" + designTaskDefine.getDateTime());
        designTaskFlowsDefine.setFlowsType(isWorkFlowEnabled ? FlowsType.DEFAULT : FlowsType.NOSTARTUP);
        designTaskFlowsDefine.setWordFlowType(String.valueOf(this.transferToWorkFlowType(workflowObject).getValue()));
        designFlowSettingDefine.setWordFlowType(this.transferToWorkFlowType(workflowObject));
        designTaskFlowsDefine.setSubmitExplain(submitExplain);
        designFlowSettingDefine.setSubmitExplain(submitExplain);
        designTaskFlowsDefine.setForceSubmitExplain(forceSubmitExplain);
        designFlowSettingDefine.setForceSubmitExplain(forceSubmitExplain);
        designTaskFlowsDefine.setStepByStepReport(stepByStepReport);
        designFlowSettingDefine.setStepByStepReport(stepByStepReport);
        designTaskFlowsDefine.setStepReportType(stepReportType);
        designFlowSettingDefine.setStepReportType(stepReportType);
        designTaskFlowsDefine.setCheckBeforeReporting(checkBeforeReporting);
        designFlowSettingDefine.setCheckBeforeReporting(checkBeforeReporting);
        designTaskFlowsDefine.setCheckBeforeReportingType(checkBeforeReportingType);
        designFlowSettingDefine.setCheckBeforeReportingType(checkBeforeReportingType);
        designTaskFlowsDefine.setCheckBeforeReportingCustom(checkBeforeReportingCustom);
        designFlowSettingDefine.setCheckBeforeReportingCustom(checkBeforeReportingCustom);
        designTaskFlowsDefine.setUnitSubmitForForce(unitSubmitForForce);
        designFlowSettingDefine.setUnitSubmitForForce(unitSubmitForForce);
        designTaskFlowsDefine.setSelectedRoleForceKey(selectedRoleForceKey);
        designFlowSettingDefine.setSelectedRoleForceKey(selectedRoleForceKey);
        designTaskFlowsDefine.setAllowTakeBack(allowTakeBack);
        designFlowSettingDefine.setAllowTakeBack(allowTakeBack);
        designTaskFlowsDefine.setApplyReturn(applyReturn);
        designFlowSettingDefine.setApplyReturn(applyReturn);
        designTaskFlowsDefine.setUnitSubmitForCensorship(unitSubmitForCensorship);
        designFlowSettingDefine.setUnitSubmitForCensorship(unitSubmitForCensorship);
        designTaskFlowsDefine.setReturnExplain(returnExplain);
        designFlowSettingDefine.setReturnExplain(returnExplain);
        designTaskFlowsDefine.setAllowTakeBackForSubmit(allowTakeBackForSubmit);
        designFlowSettingDefine.setAllowTakeBackForSubmit(allowTakeBackForSubmit);
        designTaskFlowsDefine.setDataConfirm(dataConfirm);
        designFlowSettingDefine.setDataConfirm(dataConfirm);
        designTaskFlowsDefine.setBackDescriptionNeedWrite(backDescriptionNeedWrite);
        designFlowSettingDefine.setBackDescriptionNeedWrite(backDescriptionNeedWrite);
        designTaskFlowsDefine.setOpenBackType(openBackType);
        designFlowSettingDefine.setOpenBackType(openBackType);
        designTaskFlowsDefine.setBackTypeEntity(backTypeEntity);
        designFlowSettingDefine.setBackTypeEntity(backTypeEntity);
        designTaskFlowsDefine.setReturnVersion(returnVersion);
        designFlowSettingDefine.setReturnVersion(returnVersion);
        designTaskFlowsDefine.setAllowFormBack(allowFormBack);
        designFlowSettingDefine.setAllowFormBack(allowFormBack);
        designTaskFlowsDefine.setStepByStepBack(stepByStepBack);
        designFlowSettingDefine.setStepByStepBack(stepByStepBack);
        designTaskFlowsDefine.setGoBackAllSup(goBackAllSup);
        designFlowSettingDefine.setGoBackAllSup(goBackAllSup);
        designTaskFlowsDefine.setOpenForceControl(isOpenForceControl);
        designFlowSettingDefine.setOpenForceControl(isOpenForceControl);
        designTaskFlowsDefine.setDefaultButtonName(defaultButtonName);
        designFlowSettingDefine.setDefaultButtonName(defaultButtonName);
        designTaskFlowsDefine.setDefaultButtonNameConfig(defaultButtonNameConfig);
        designFlowSettingDefine.setDefaultButtonNameConfig(defaultButtonNameConfig);
        designTaskFlowsDefine.setDefaultNodeName(defaultNodeName);
        designFlowSettingDefine.setDefaultNodeName(defaultNodeName);
        designTaskFlowsDefine.setDefaultNodeNameConfig(defaultNodeNameConfig);
        designFlowSettingDefine.setDefaultNodeNameConfig(defaultNodeNameConfig);
        String sendMessageMailStr = "";
        if (sendMessageMail) {
            JSONObject param = new JSONObject();
            param.put("act_reject_notice", true);
            param.put("act_confirm_notice", true);
            sendMessageMailStr = param.toString();
        }
        designTaskFlowsDefine.setSendMessageMail(sendMessageMailStr);
        designFlowSettingDefine.setSendMessageMail(sendMessageMailStr);
        designTaskFlowsDefine.setMessageTemplate(messageTemplate);
        designFlowSettingDefine.setMessageTemplate(messageTemplate);
        designTaskFlowsDefine.setRealMulCheckBeforeCheck(mulCheckBeforeCheck);
        designFlowSettingDefine.setMulCheckBeforeCheck(mulCheckBeforeCheck);
        designTaskFlowsDefine.setReportBeforeAudit(reportBeforeAudit);
        designFlowSettingDefine.setReportBeforeAudit(reportBeforeAudit);
        designTaskFlowsDefine.setSpecialAudit(specialAudit);
        designFlowSettingDefine.setSpecialAudit(specialAudit);
        designTaskFlowsDefine.setReportBeforeAuditType(reportBeforeAuditType);
        designFlowSettingDefine.setReportBeforeAuditType(reportBeforeAuditType);
        designTaskFlowsDefine.setReportBeforeAuditCustom(reportBeforeAuditCustom);
        designFlowSettingDefine.setReportBeforeAuditCustom(reportBeforeAuditCustom);
        designTaskFlowsDefine.setReportBeforeAuditValue(reportBeforeAuditValue);
        designFlowSettingDefine.setReportBeforeAuditValue(reportBeforeAuditValue);
        designTaskFlowsDefine.setErroStatus(errorStatus);
        designFlowSettingDefine.setErroStatus(errorStatus);
        designTaskFlowsDefine.setPromptStatus(promptStatus);
        designFlowSettingDefine.setPromptStatus(promptStatus);
        designTaskFlowsDefine.setReportBeforeOperation(reportBeforeOperation);
        designFlowSettingDefine.setReportBeforeOperation(reportBeforeOperation);
        designTaskFlowsDefine.setReportBeforeOperationValue(reportBeforeOperationValue);
        designFlowSettingDefine.setReportBeforeOperationValue(reportBeforeOperationValue);
        designTaskFlowsDefine.setSubmitAfterFormula(submitAfterFormula);
        designFlowSettingDefine.setSubmitAfterFormula(submitAfterFormula);
        designTaskFlowsDefine.setSubmitAfterFormulaValue(submitAfterFormulaValue);
        designFlowSettingDefine.setSubmitAfterFormulaValue(submitAfterFormulaValue);
        designTaskFlowsDefine.setFilterCondition(filterCondition);
        designTaskFlowsDefine.setDesignFlowSettingDefine(designFlowSettingDefine);
        byte[] flowSettingByteArray = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes((TaskFlowsDefine)designTaskFlowsDefine);
        boolean isDataUpdate = this.isDataExist(context.getTaskId()) ? this.updateData(context.getTaskId(), flowSettingByteArray) : this.insertData(context.getTaskId(), flowSettingByteArray);
        boolean isDesignDataUpdate = this.isDesignDataExist(context.getTaskId()) ? this.updateDesignData(context.getTaskId(), flowSettingByteArray) : this.insertDesignData(context.getTaskId(), flowSettingByteArray);
        runtimeTaskService.refreshTaskCache();
        return isWorkflowSettingUpdate && isDataUpdate && isDesignDataUpdate;
    }

    private boolean isDataExist(String taskId) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = String.format("select BD_KEY from nr_param_bigdatatable where BD_KEY = '%s'", taskId);
        List resultSet = jdbcTemplate.queryForList(sql);
        return !resultSet.isEmpty();
    }

    private boolean isDesignDataExist(String taskId) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = String.format("select BD_KEY from nr_param_bigdatatable_des where BD_KEY = '%s'", taskId);
        List resultSet = jdbcTemplate.queryForList(sql);
        return !resultSet.isEmpty();
    }

    private boolean insertData(String taskId, byte[] flowSetting) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = "INSERT INTO NR_PARAM_BIGDATATABLE(BD_KEY, BD_CODE, BD_LANG, BD_DATA, BD_VERSION, BD_UPDATETIME) VALUES(?, ?, ?, ?, ?, ?)";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, ps -> {
            ps.setString(1, taskId);
            ps.setString(2, "FLOWSETTING");
            ps.setInt(3, 1);
            ps.setObject(4, flowSetting);
            ps.setString(5, "1.0");
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate() == 1;
        }));
    }

    private boolean insertDesignData(String taskId, byte[] flowSetting) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = "INSERT INTO NR_PARAM_BIGDATATABLE_DES(BD_KEY, BD_CODE, BD_LANG, BD_DATA, BD_VERSION, BD_UPDATETIME) VALUES(?, ?, ?, ?, ?, ?)";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, ps -> {
            ps.setString(1, taskId);
            ps.setString(2, "FLOWSETTING");
            ps.setInt(3, 1);
            ps.setObject(4, flowSetting);
            ps.setString(5, "1.0");
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate() == 1;
        }));
    }

    private boolean updateData(String taskId, byte[] flowSetting) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = "UPDATE NR_PARAM_BIGDATATABLE SET BD_DATA = ? WHERE BD_KEY = ? AND BD_CODE = ?";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, ps -> {
            ps.setBytes(1, flowSetting);
            ps.setString(2, taskId);
            ps.setString(3, "FLOWSETTING");
            return ps.executeUpdate() == 1;
        }));
    }

    private boolean updateDesignData(String taskId, byte[] flowSetting) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringBeanUtils.getBean(JdbcTemplate.class);
        String sql = "UPDATE NR_PARAM_BIGDATATABLE_DES SET BD_DATA = ? WHERE BD_KEY = ? AND BD_CODE = ?";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, ps -> {
            ps.setBytes(1, flowSetting);
            ps.setString(2, taskId);
            ps.setString(3, "FLOWSETTING");
            return ps.executeUpdate() == 1;
        }));
    }

    private ReportAuditType transferToReportAuditType(CurrencyType currencyType) {
        switch (currencyType) {
            case ALL: {
                return ReportAuditType.NONE;
            }
            case SUPERIOR: {
                return ReportAuditType.CONVERSION;
            }
            case SELF: {
                return ReportAuditType.ESCALATION;
            }
            case CUSTOM: {
                return ReportAuditType.CUSTOM;
            }
        }
        return ReportAuditType.NONE;
    }

    private String transferListToMultiCheckValueStr(List<String> valueList) {
        return String.join((CharSequence)";", valueList);
    }

    private String[] transferErrorHandleToErrorStatusAndPromptStatus(JSONArray errorHandle) {
        List auditItems = errorHandle.toList().stream().map(e -> (HashMap)e).collect(Collectors.toList());
        Object[] errorStatusArr = new String[2 + auditItems.size()];
        Object[] promptStatusArr = new String[2 + auditItems.size()];
        Arrays.fill(errorStatusArr, "");
        Arrays.fill(promptStatusArr, "");
        for (HashMap auditItem : auditItems) {
            String code = (String)auditItem.get("code");
            String value = (String)auditItem.get("value");
            if (value.equals("0")) {
                errorStatusArr[Integer.parseInt((String)code)] = code;
                continue;
            }
            if (!value.equals("1")) continue;
            promptStatusArr[Integer.parseInt((String)code)] = code;
        }
        String errorStatus = String.join((CharSequence)";", (CharSequence[])errorStatusArr);
        String promptStatus = String.join((CharSequence)";", (CharSequence[])promptStatusArr);
        return new String[]{errorStatus, promptStatus};
    }

    private WorkFlowType transferToWorkFlowType(WorkflowObjectType workflowObjectType) {
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
        return WorkFlowType.ENTITY;
    }

    private RunTimeTaskDefineImpl transferToRunTimeTaskDefine(DesignTaskDefine designTaskDefine, DesignTaskFlowsDefine designTaskFlowsDefine) {
        RunTimeTaskDefineImpl runTimeTaskDefine = new RunTimeTaskDefineImpl();
        runTimeTaskDefine.setFlowsSetting((TaskFlowsDefine)designTaskFlowsDefine);
        runTimeTaskDefine.setTaskCode(designTaskDefine.getTaskCode());
        runTimeTaskDefine.setTaskExtension(designTaskDefine.getTaskExtension());
        runTimeTaskDefine.setTaskType(designTaskDefine.getTaskType());
        runTimeTaskDefine.setUpdateTime(designTaskDefine.getUpdateTime());
        runTimeTaskDefine.setBigDataChanged(designTaskDefine.getBigDataChanged());
        runTimeTaskDefine.setCommitEntitiesKey(designTaskDefine.getCommitEntitiesKey());
        runTimeTaskDefine.setDataScheme(designTaskDefine.getDataScheme());
        runTimeTaskDefine.setDatetime(designTaskDefine.getDateTime());
        runTimeTaskDefine.setDescription(designTaskDefine.getDescription());
        runTimeTaskDefine.setDims(designTaskDefine.getDims());
        runTimeTaskDefine.setDueDateOffset(designTaskDefine.getDueDateOffset());
        runTimeTaskDefine.setDw(designTaskDefine.getDw());
        runTimeTaskDefine.setEfdcSwitch(designTaskDefine.getEfdcSwitch());
        runTimeTaskDefine.setEntityViewsInEFDC(designTaskDefine.getEntityViewsInEFDC());
        runTimeTaskDefine.setFillInAutomaticallyDue(designTaskDefine.getFillInAutomaticallyDue());
        runTimeTaskDefine.setFillingDateDays(designTaskDefine.getFillingDateDays());
        runTimeTaskDefine.setFillingDateType(designTaskDefine.getFillingDateType());
        runTimeTaskDefine.setFilterExpression(designTaskDefine.getFilterExpression());
        runTimeTaskDefine.setFilterTemplateID(designTaskDefine.getFilterTemplate());
        runTimeTaskDefine.setFormulaSyntaxStyle(designTaskDefine.getFormulaSyntaxStyle());
        runTimeTaskDefine.setFromPeriod(designTaskDefine.getFromPeriod());
        runTimeTaskDefine.setGroupName(designTaskDefine.getGroupName());
        runTimeTaskDefine.setKey(designTaskDefine.getKey());
        runTimeTaskDefine.setMasterEntitiesKey(designTaskDefine.getMasterEntitiesKey());
        runTimeTaskDefine.setMasterEntityCount(designTaskDefine.getMasterEntityCount());
        runTimeTaskDefine.setMeasureUnit(designTaskDefine.getMeasureUnit());
        runTimeTaskDefine.setOrder(designTaskDefine.getOrder());
        runTimeTaskDefine.setOwnerLevelAndId(designTaskDefine.getOwnerLevelAndId());
        runTimeTaskDefine.setPeriodSetting(designTaskDefine.getPeriodSetting());
        runTimeTaskDefine.setPeriodType(designTaskDefine.getPeriodType());
        runTimeTaskDefine.setTaskFilePrefix(designTaskDefine.getTaskFilePrefix());
        runTimeTaskDefine.setTaskGatherType(designTaskDefine.getTaskGatherType());
        runTimeTaskDefine.setTaskGatherType(designTaskDefine.getTaskGatherType());
        runTimeTaskDefine.setTitle(designTaskDefine.getTitle());
        runTimeTaskDefine.setTaskPeriodOffset(designTaskDefine.getTaskPeriodOffset());
        runTimeTaskDefine.setToPeriod(designTaskDefine.getToPeriod());
        runTimeTaskDefine.setVersion(designTaskDefine.getVersion());
        runTimeTaskDefine.setPeriodBeginOffset(designTaskDefine.getPeriodBeginOffset());
        runTimeTaskDefine.setPeriodEndOffset(designTaskDefine.getPeriodEndOffset());
        return runTimeTaskDefine;
    }

    private JSONObject filterDefaultButtonNameConfig(JSONObject defaultButtonConfig, boolean unitSubmitForCensorship, boolean allowTakeBack, boolean dataConfirm) {
        if (!unitSubmitForCensorship) {
            defaultButtonConfig.remove("act_submit");
            defaultButtonConfig.remove("act_return");
        }
        if (!allowTakeBack) {
            defaultButtonConfig.remove("act_retrieve");
        }
        if (!dataConfirm) {
            defaultButtonConfig.remove("act_confirm");
            defaultButtonConfig.remove("act_cancel_confirm");
        }
        return defaultButtonConfig;
    }

    private JSONObject filterDefaultNodeNameConfig(JSONObject defaultNodeName, boolean unitSubmitForCensorship) {
        if (!unitSubmitForCensorship) {
            defaultNodeName.remove("tsk_submit");
        }
        return defaultNodeName;
    }
}

