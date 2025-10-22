/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobContextHolder
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bpm.common.ConcurrentTaskContext
 *  com.jiuqi.nr.bpm.common.TaskContext
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.step.StepByStep
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.util.DataentryWorkflowUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.service.SingleFormRejectService
 *  com.jiuqi.nr.bpm.upload.utils.ObtainCustomName
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CustomQueryCondition
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultData
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckSchemeRecordService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataentity.entity.DataEntityType
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.param.DataEntityContext
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.JTableException
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormulaSchemeData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo
 *  com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.ExternalBatchUploadResult
 *  com.jiuqi.nr.jtable.params.output.Info
 *  com.jiuqi.nr.jtable.params.output.LevelUploadInfo
 *  com.jiuqi.nr.jtable.params.output.LevelUploadObj
 *  com.jiuqi.nr.jtable.params.output.MultCheckResult
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeCheck
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck
 *  com.jiuqi.nr.jtable.params.output.UploadReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 *  com.jiuqi.nr.state.common.StateConst
 *  com.jiuqi.nr.state.pojo.StateEntites
 *  com.jiuqi.nr.state.service.IStateSevice
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  org.apache.commons.beanutils.BeanUtils
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobContextHolder;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.step.StepByStep;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchNoOperate;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.util.DataentryWorkflowUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.bpm.upload.utils.ObtainCustomName;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.CustomQueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckSchemeRecordService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.param.DataEntityContext;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.dataentry.asynctask.BatchNodeCheckAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.bean.MultCheckLabel;
import com.jiuqi.nr.dataentry.bean.MultCheckReturnResult;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultItem;
import com.jiuqi.nr.dataentry.bean.UploadVerifyType;
import com.jiuqi.nr.dataentry.internal.listener.BatchOptEvent;
import com.jiuqi.nr.dataentry.internal.service.util.CheckResultParamForReportUtil;
import com.jiuqi.nr.dataentry.internal.service.util.QueryLastOperateUtil;
import com.jiuqi.nr.dataentry.internal.service.util.UploadCheckFliterUtil;
import com.jiuqi.nr.dataentry.monitor.WorkFlowCheckProgressMonitor;
import com.jiuqi.nr.dataentry.monitor.WorkflowAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchWorkFlowInfo;
import com.jiuqi.nr.dataentry.provider.DimensionValueProvider;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchWorkflowService;
import com.jiuqi.nr.dataentry.service.IExternalUploadFliter;
import com.jiuqi.nr.dataentry.service.IFinalaccountsAuditService;
import com.jiuqi.nr.dataentry.service.IForceUpload;
import com.jiuqi.nr.dataentry.service.IMultcheckService;
import com.jiuqi.nr.dataentry.service.IWorkFlowHandler;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.build.DWLeafNodeBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.ExternalBatchUploadResult;
import com.jiuqi.nr.jtable.params.output.Info;
import com.jiuqi.nr.jtable.params.output.LevelUploadInfo;
import com.jiuqi.nr.jtable.params.output.LevelUploadObj;
import com.jiuqi.nr.jtable.params.output.MultCheckResult;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.UploadBeforeCheck;
import com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck;
import com.jiuqi.nr.jtable.params.output.UploadReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import com.jiuqi.nr.state.common.StateConst;
import com.jiuqi.nr.state.pojo.StateEntites;
import com.jiuqi.nr.state.service.IStateSevice;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class BatchWorkflowServiceImpl
implements IBatchWorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(BatchWorkflowServiceImpl.class);
    private static final String TSK = "tsk_";
    private static final String TASK = "Task_";
    private static final String CHINESE = "zh";
    private static final String MULTCHEK = "mulcheck";
    private static final String VERSION = "1.0";
    private static final String VERSION2 = "2.0";
    private static final String TASK_STATE = "1";
    @Value(value="${jiuqi.nr.workflow.open-audit:false}")
    private boolean openAudit;
    @Value(value="${jiuqi.nr.workflow.mul-check.batchexecute:false}")
    private boolean mulCheckBatchExecute;
    @Value(value="${jiuqi.nr.workflow.mul-check.version:2.0}")
    private String mulCheckVersion;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private DimensionValueProvider dimensionValueProvider;
    @Autowired
    private IDataStateCheckService dataStateCheckService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private StepByStep stepByStep;
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IStateSevice stateService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired(required=false)
    private List<IWorkFlowHandler> workflowHandlers;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IEntityViewRunTimeController viewRtCtl;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Autowired(required=false)
    private IExternalUploadFliter batchWorkflowHandlers;
    @Autowired
    private ICheckService iCheckService;
    @Autowired
    private ICheckResultService iCheckResultService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CheckResultParamForReportUtil checkResultParamForReportUtil;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private UploadCheckFliterUtil uploadCheckFliterUtil;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private ICheckSchemeRecordService checkSchemeRecordService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    private static final String[] FILTER_STR = new String[]{"\u5bfc\u5165\u6570\u636e", "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", "\u6267\u884c\u4e0a\u62a5", "\u6267\u884c\u6279\u91cf\u4e0a\u62a5", "\u6dfb\u52a0\u6279\u6ce8", "\u4fee\u6539\u6279\u6ce8"};
    @Autowired(required=false)
    private IForceUpload forceUpload;
    @Autowired(required=false)
    private IMultcheckService multcheckService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private IFinalaccountsAuditService finalaccountsAuditService;
    @Autowired
    private QueryLastOperateUtil queryLastOperateUtil;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    @Autowired
    private DataentryWorkflowUtil dataentryWorkflowUtil;
    @Autowired
    private ObtainCustomName obtainCustomName;

    @Override
    public LogInfo batchExecuteTask(BatchExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        ReturnInfo returnInfo;
        Map<String, EntityData> entitys;
        Set<String> keySet;
        Object returnInfoList;
        ExternalBatchUploadResult uploadResult;
        String finalaccountsAudit;
        Date startOptTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = sdf.format(startOptTime);
        ArrayList<BatchWorkFlowInfo> needCalcList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> needCheckList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> needNodeCheckList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> multCheckList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> allDimensionList = new ArrayList<BatchWorkFlowInfo>();
        BatchUploadRetrunInfo batchUploadReturnInfo = new BatchUploadRetrunInfo();
        ArrayList<ReturnInfo> warning = new ArrayList<ReturnInfo>();
        UploadReturnInfo uploadReturnInfo = new UploadReturnInfo();
        UploadBeforeCheck uploadBeforeCheck = new UploadBeforeCheck();
        UploadBeforeNodeCheck uploadBeforeNodeCheck = new UploadBeforeNodeCheck();
        String actionName = this.setActionMsg(param.getActionId());
        LogInfo logInfo = new LogInfo();
        logInfo.setActionName(actionName);
        logInfo.setBatchUploadRetrunInfo(batchUploadReturnInfo);
        WorkFlowType startType = this.dataentryFlowService.queryStartType(param.getContext().getFormSchemeKey());
        StringBuilder actionLogInfo = this.buildMessing(param.getContext(), param.getFormKeys(), param.getFormGroupKeys(), actionName, startType, param.getActionId());
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getContext().getFormSchemeKey());
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(param.getContext().getTaskKey());
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        Map dimensionSet = param.getContext().getDimensionSet();
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(param.getContext().getFormSchemeKey());
        List dimEntityList = this.jtableParamService.getDimEntityList(param.getContext().getFormSchemeKey());
        WorkflowConfig workflowConfig = this.dataentryFlowService.queryWorkflowConfig(param.getContext().getFormSchemeKey());
        String mainDim = targetEntityInfo.getDimensionName();
        String mainKey = targetEntityInfo.getKey();
        boolean isMulut = !dimEntityList.isEmpty();
        String isSetDefaultValue = this.iNvwaSystemOptionService.get("other-group", "IS_SET_DEFAULT_DIMENSION_VALUE");
        if (isMulut && isSetDefaultValue.equals(TASK_STATE)) {
            this.setDefaultValue(dimensionSet, startType);
        }
        DimensionCollection buildDimensionCollection = this.workFlowDimensionBuilder.buildDimensionCollection(param.getContext().getTaskKey(), dimensionSet);
        List dimensionCombinations = buildDimensionCollection.getDimensionCombinations();
        List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition = this.getFormKeysCondition(param.getContext(), startType, param.getFormGroupKeys(), param.getFormKeys());
        Map<String, Map<String, Boolean>> formOrGroupKeysByDuty = this.queryLastOperateUtil.getDutyAuthDatas(formKeysCondition, startType, param.getActionId(), mainKey, mainDim);
        this.groupReadAuthData(dimensionCombinations, formKeysCondition, startType, uploadReturnInfo, param.getContext().getFormSchemeKey(), mainDim, param, startType, mainKey, mainDim);
        this.groupNoAuthData(dimensionCombinations, formKeysCondition, startType, uploadReturnInfo, param.getContext().getFormSchemeKey(), mainDim, formOrGroupKeysByDuty);
        List<WorkflowDataBean> filterForm = this.getFilterForm(param, uploadReturnInfo, warning, startType, mainKey, mainDim, dimensionCombinations, formKeysCondition, formOrGroupKeysByDuty);
        BatchWorkflowDataBean batchWorkflowDataBean = new BatchWorkflowDataBean();
        batchWorkflowDataBean.setFormSchemeKey(param.getContext().getFormSchemeKey());
        batchWorkflowDataBean.setWorkflowDataList(filterForm);
        Map batchWorkflowDataInfo = this.dataentryFlowService.batchWorkflowDataInfo(batchWorkflowDataBean);
        String taskCode = null;
        taskCode = param.getTaskCode() != null && (param.getTaskCode().startsWith(TSK) || param.getTaskCode().startsWith(TASK)) ? param.getTaskCode() : this.taskCode(param.getContext().getFormSchemeKey(), param.getActionId(), batchWorkflowDataInfo, param.getTaskId());
        HashSet<String> unitKeysByCurrentUser = new HashSet();
        boolean fliter = this.dataentryWorkflowUtil.isFliter(formScheme, param.getTaskCode(), param.getActionId());
        if (fliter && param.isForceCommit() && ("act_upload".equals(param.getActionId()) || "cus_upload".equals(param.getActionId()) || "act_submit".equals(param.getActionId()) || "cus_submit".equals(param.getActionId()) || "single_form_upload".equals(param.getActionId()))) {
            unitKeysByCurrentUser = this.fliterCurrentUnit(param, buildDimensionCollection, filterForm, uploadReturnInfo, warning, mainKey, mainDim, startType, taskCode);
        }
        HashMap<String, List<String>> groupToForm = new HashMap();
        if (WorkFlowType.GROUP.equals((Object)startType)) {
            groupToForm = this.creatGroupToForm(batchWorkflowDataInfo, param.getContext());
        }
        WorkflowAction tempaction = null;
        String title = "\u6279\u91cf\u4e0a\u62a5";
        block10: for (DimensionValueSet dim : batchWorkflowDataInfo.keySet()) {
            LinkedHashMap linkedHashMap = (LinkedHashMap)batchWorkflowDataInfo.get(dim);
            for (String key : linkedHashMap.keySet()) {
                List actions;
                List list = (List)linkedHashMap.get(key);
                if (list == null || list.size() <= 0 || (actions = ((WorkflowDataInfo)list.get(0)).getActions()).isEmpty()) continue;
                for (WorkflowAction action : actions) {
                    if (!action.getCode().equals(param.getActionId())) continue;
                    tempaction = action;
                    title = ((WorkflowAction)actions.get(0)).getTitle();
                    break block10;
                }
            }
        }
        Map<String, List<BatchWorkFlowInfo>> splitGrouping = this.splitGrouping(batchWorkflowDataInfo, param, uploadReturnInfo, warning, mainDim, mainKey, startType, groupToForm, workflowConfig, taskDefine, unitKeysByCurrentUser);
        needCalcList.addAll((Collection)splitGrouping.get("needCalcList"));
        needCheckList.addAll((Collection)splitGrouping.get("needCheckList"));
        needNodeCheckList.addAll((Collection)splitGrouping.get("needNodeCheckList"));
        multCheckList.addAll((Collection)splitGrouping.get(MULTCHEK));
        allDimensionList.addAll((Collection)splitGrouping.get("allDimensionList"));
        asyncTaskMonitor.progressAndMessage(0.1, "");
        ArrayList<String> multCheckFailUnits = new ArrayList<String>();
        if (this.mulCheckVersion.equals(VERSION) && (this.openAudit || this.mulCheckBatchExecute) && multCheckList != null && multCheckList.size() > 0 && ("act_upload".equals(param.getActionId()) || "cus_upload".equals(param.getActionId()) || "act_submit".equals(param.getActionId()) || "cus_submit".equals(param.getActionId()) || "single_form_upload".equals(param.getActionId())) && flowsSetting.getMulCheckBeforeCheck() && !param.isForceCommit() && (finalaccountsAudit = this.finalaccountsAudit(param, multCheckList, asyncTaskMonitor, batchUploadReturnInfo, multCheckFailUnits, uploadReturnInfo)) != null) {
            if (finalaccountsAudit.equals("error")) {
                asyncTaskMonitor.error("finalaccountsaudit1", null, "\u7efc\u5408\u5ba1\u6838\u6269\u5c55\u63a5\u53e3\u6267\u884c\u5f02\u5e38");
                actionLogInfo.append("\u4e0a\u62a5\u524d\u7efc\u5408\u5ba1\u6838\u6821\u9a8c\u4e0d\u901a\u8fc7");
                logInfo.setLogInfo(actionLogInfo.toString());
                return logInfo;
            }
            batchUploadReturnInfo.setFinalaccountsAudit(finalaccountsAudit);
            ObjectMapper mapper = new ObjectMapper();
            String writeValueAsString = null;
            try {
                writeValueAsString = mapper.writeValueAsString((Object)batchUploadReturnInfo);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            asyncTaskMonitor.error("finalaccountsaudit", null, writeValueAsString);
            actionLogInfo.append("\u4e0a\u62a5\u524d\u7efc\u5408\u5ba1\u6838\u6821\u9a8c\u4e0d\u901a\u8fc7");
            logInfo.setLogInfo(actionLogInfo.toString());
            return logInfo;
        }
        if (this.mulCheckVersion.equals(VERSION2) && multCheckList != null && multCheckList.size() > 0 && ("act_upload".equals(param.getActionId()) || "cus_upload".equals(param.getActionId()) || "act_submit".equals(param.getActionId()) || "cus_submit".equals(param.getActionId()) || "single_form_upload".equals(param.getActionId())) && flowsSetting.getMulCheckBeforeCheck() && !param.isForceCommit() && (finalaccountsAudit = this.finalaccountsAudit(param, multCheckList, asyncTaskMonitor, batchUploadReturnInfo, multCheckFailUnits, uploadReturnInfo)) != null) {
            asyncTaskMonitor.error(finalaccountsAudit, null, null);
            actionLogInfo.append("\u4e0a\u62a5\u524d\u7efc\u5408\u5ba1\u6838\u6821\u9a8c\u4e0d\u901a\u8fc7");
            logInfo.setLogInfo(actionLogInfo.toString());
            return logInfo;
        }
        if (needCalcList.size() > 0) {
            this.batchCalculate(param, needCalcList, workflowConfig, startType, asyncTaskMonitor);
        }
        HashMap<String, String> haveCheckResultMap = new HashMap();
        HashMap<String, List<String>> haveCheckFormResult = new HashMap();
        HashMap<String, List<String>> unPassFormGroupMap = new HashMap();
        if (needCheckList.size() > 0) {
            boolean needAutoCheckAll = param.getUserActionParam() != null ? param.getUserActionParam().isNeedAutoCheckAll() : false;
            this.batchCheck(param, needCheckList, groupToForm, uploadBeforeCheck, workflowConfig, startType, asyncTaskMonitor, needCalcList.size() > 0, tempaction, filterForm, mainDim, mainKey, needAutoCheckAll, taskCode);
            haveCheckResultMap = uploadBeforeCheck.getResultEntity();
            haveCheckFormResult = uploadBeforeCheck.getResultForms();
            unPassFormGroupMap = uploadBeforeCheck.getResultFormGroup();
        }
        Map<String, String> haveNodeCheckResultMap = new HashMap<String, String>();
        Map<String, List<String>> haveNodeCheckFormResultMap = new HashMap<String, List<String>>();
        Map<String, List<String>> unCheckNodeFormGroupMap = new HashMap<String, List<String>>();
        if (needNodeCheckList.size() > 0) {
            double nodeCheckProgress = 0.0;
            double currTaskProgress = 0.1;
            String nodeCheckId = "";
            this.batchNeedNodeBeforeUpload(param.getContext(), needNodeCheckList, uploadBeforeNodeCheck, startType, param.getUserActionParam());
            nodeCheckId = uploadBeforeNodeCheck.getAsyncTaskId();
            TaskState queryNodeState = null;
            while (nodeCheckProgress < 1.0 && StringUtils.isNotEmpty((String)nodeCheckId)) {
                if (nodeCheckProgress < 1.0 && StringUtils.isNotEmpty((String)nodeCheckId)) {
                    nodeCheckProgress = this.asyncTaskManager.queryProcess(nodeCheckId);
                }
                queryNodeState = this.asyncTaskManager.queryTaskState(uploadBeforeNodeCheck.getAsyncTaskId());
                asyncTaskMonitor.progressAndMessage(currTaskProgress, "");
                ObjectMapper mapper = new ObjectMapper();
                String retStr = "";
                try {
                    retStr = mapper.writeValueAsString((Object)batchUploadReturnInfo);
                }
                catch (Exception e) {
                    logInfo.setLogInfo(actionLogInfo.toString() + e.getMessage());
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                String nodeCheckNoPass = "node_check_nopass_info";
                if (StringUtils.isNotEmpty((String)nodeCheckId) && (queryNodeState.equals((Object)TaskState.OVERTIME) || queryNodeState.equals((Object)TaskState.NONE))) {
                    asyncTaskMonitor.error(title + nodeCheckNoPass, null, retStr);
                    actionLogInfo.append(title + "\u8fdb\u884c\u8282\u70b9\u68c0\u67e5\u4e0d\u901a\u8fc7\uff01");
                    actionLogInfo.append(this.batchOpttime(startTime));
                    logInfo.setLogInfo(actionLogInfo.toString());
                    return logInfo;
                }
                if (queryNodeState.equals((Object)TaskState.WAITING)) {
                    try {
                        Thread.sleep(3000L);
                    }
                    catch (InterruptedException e) {
                        logInfo.setLogInfo(actionLogInfo.toString() + e.getMessage());
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    continue;
                }
                if (queryNodeState.equals((Object)TaskState.PROCESSING)) continue;
                break;
            }
            if (queryNodeState != null && queryNodeState.equals((Object)TaskState.ERROR)) {
                this.haveNodeCheckResults(needNodeCheckList, uploadBeforeNodeCheck, mainDim, groupToForm);
                haveNodeCheckResultMap = uploadBeforeNodeCheck.getUnPassEntity();
                haveNodeCheckFormResultMap = uploadBeforeNodeCheck.getUnPassForms();
                unCheckNodeFormGroupMap = uploadBeforeNodeCheck.getUnPassFormGroup();
            }
        }
        List<String> queryEndFillUnits = this.queryEndFill(param.getContext(), allDimensionList, mainDim, mainKey, uploadReturnInfo, warning);
        List<String> timeSetting = this.timeSetting(param.getContext(), allDimensionList, mainKey, uploadReturnInfo, warning);
        List<BatchWorkFlowInfo> canUploadList = this.getUploadUnits(allDimensionList, unCheckNodeFormGroupMap, unPassFormGroupMap, haveCheckFormResult, haveNodeCheckFormResultMap, haveCheckResultMap, haveNodeCheckResultMap, queryEndFillUnits, timeSetting, uploadReturnInfo, mainDim, startType, multCheckFailUnits);
        if (this.workflowHandlers != null && this.workflowHandlers.size() > 0) {
            for (IWorkFlowHandler workflowHandler : this.workflowHandlers) {
                workflowHandler.beforeBatchExecuteTask(canUploadList, warning, uploadReturnInfo, param, asyncTaskMonitor, batchWorkflowDataInfo, startType, mainDim, mainKey);
            }
        }
        if ((uploadResult = this.canUpload(param, allDimensionList)) != null) {
            this.setReturnInfo(uploadResult, canUploadList, mainDim, uploadReturnInfo, batchUploadReturnInfo);
        }
        this.setUploadBeforeUnPassNum(uploadBeforeCheck, uploadBeforeNodeCheck, haveCheckFormResult, unPassFormGroupMap, haveNodeCheckFormResultMap, unCheckNodeFormGroupMap, haveCheckResultMap, haveNodeCheckResultMap);
        BatchStepByStepParam stepByOptParam = null;
        stepByOptParam = new BatchStepByStepParam();
        stepByOptParam.setActionId(param.getActionId());
        String taskId = this.taskId(param.getContext().getFormSchemeKey(), param.getActionId(), batchWorkflowDataInfo, param.getTaskId());
        param.setTaskCode(taskId);
        stepByOptParam.setTaskId(taskId);
        stepByOptParam.setNodeId(taskCode);
        stepByOptParam.setForceUpload(param.isForceCommit());
        stepByOptParam.setSendEmail(param.isSendEmail());
        stepByOptParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
        ArrayList<DimensionValueSet> stepUnits = new ArrayList<DimensionValueSet>();
        ArrayList<String> unitList = new ArrayList<String>();
        LinkedHashMap<String, List<String>> unit2Forms = new LinkedHashMap<String, List<String>>();
        for (BatchWorkFlowInfo batchWorkFlowInfo : canUploadList) {
            DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet(batchWorkFlowInfo.getDimensionValue());
            DimensionValue dimensionValue = batchWorkFlowInfo.getDimensionValue().get(mainDim);
            String value = dimensionValue.getValue();
            if (unitList.contains(value)) continue;
            unitList.add(value);
            if (multCheckFailUnits.contains(value)) continue;
            stepUnits.add(dimensionValueSet);
            DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
            LinkedHashMap map = (LinkedHashMap)batchWorkflowDataInfo.get(filterDims);
            ArrayList<String> forms = new ArrayList<String>();
            block16: for (String formKey : map.keySet()) {
                List actions = ((WorkflowDataInfo)((List)map.get(formKey)).get(0)).getActions();
                Iterator<Object> iterator = actions.iterator();
                while (iterator.hasNext()) {
                    WorkflowAction workflowAction = (WorkflowAction)iterator.next();
                    if (!workflowAction.getCode().equals(param.getActionId()) || !workflowAction.getActionParam().isNeedbuildVersion()) continue;
                    forms.add(formKey);
                    param.setActionTitle(workflowAction.getTitle());
                    continue block16;
                }
            }
            if (forms.size() <= 0) continue;
            unit2Forms.put(value, forms);
        }
        if (unit2Forms.size() > 0) {
            StringBuffer units = new StringBuffer();
            for (String string : unitList) {
                units.append(string + ";");
            }
            if (units.length() != 0) {
                units.deleteCharAt(units.length() - 1);
            }
            HashMap<String, DimensionValue> hashMap = new HashMap<String, DimensionValue>();
            for (String key : canUploadList.get(0).getDimensionValue().keySet()) {
                DimensionValue dimensionValue = new DimensionValue(canUploadList.get(0).getDimensionValue().get(key));
                hashMap.put(key, dimensionValue);
            }
            ((DimensionValue)hashMap.get(mainDim)).setValue(units.toString());
            this.dataVersion(param, hashMap, unit2Forms);
        }
        stepByOptParam.setStepUnit(stepUnits);
        if (!WorkFlowType.ENTITY.equals((Object)startType)) {
            Map<String, LinkedHashSet<String>> entityToForm = this.getEntityToForm(param, startType, canUploadList, mainDim, mainKey, formOrGroupKeysByDuty, formKeysCondition);
            Map<String, LinkedHashSet<String>> map = this.getEntityToForm(canUploadList, startType, mainDim);
            stepByOptParam.setFormKeys(map);
            stepByOptParam.setGroupKeys(map);
            stepByOptParam.setStepFormKeyMap(entityToForm);
            stepByOptParam.setStepGroupKeyMap(entityToForm);
        }
        stepByOptParam.setContent(param.getComment());
        stepByOptParam.setMessageIds(param.getMessageIds());
        stepByOptParam.setForceUpload(param.isForceCommit());
        ConcurrentTaskContext taskContext = new ConcurrentTaskContext();
        String string = param.getReturnType();
        taskContext.put("returnType", (Object)(string == null ? "" : string));
        stepByOptParam.setContext((TaskContext)taskContext);
        stepByOptParam.setTaskKey(param.getContext().getTaskKey());
        DimensionValue dimensionValue = (DimensionValue)dimensionSet.get("DATATIME");
        stepByOptParam.setPeriod(dimensionValue.getValue());
        BatchStepByStepResult result = new BatchStepByStepResult();
        result = stepUnits.size() > 0 && ("single_form_reject".equals(param.getActionId()) || "single_form_upload".equals(param.getActionId())) ? this.batchSingleOpt(param, stepUnits, startType, mainDim, asyncTaskMonitor, logInfo, actionLogInfo, formKeysCondition) : this.stepByStep.batchStepByOpt(stepByOptParam);
        Map batchStepByOpt = result.getNoOperateUnitMap();
        Map noOperateGroupOrFormMap = result.getNoOperateGroupOrFormMap();
        CompleteMsg completeMsg = result.getCompleteMsg();
        if (completeMsg != null && !completeMsg.isSucceed() && completeMsg.getMsg() != null) {
            batchUploadReturnInfo.setStatus(TASK_STATE);
            batchUploadReturnInfo.setCompleteMsg(completeMsg.getMsg());
            try {
                if (param.isChangeMonitorState()) {
                    asyncTaskMonitor.error(completeMsg.getMsg(), (Throwable)new Exception());
                }
                actionLogInfo.append(completeMsg.getMsg());
                actionLogInfo.append(this.batchOpttime(startTime));
                logInfo.setLogInfo(actionLogInfo.toString());
                return logInfo;
            }
            catch (Exception e) {
                actionLogInfo.append(e.getMessage());
                actionLogInfo.append(this.batchOpttime(startTime));
                logInfo.setLogInfo(actionLogInfo.toString());
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            Set operateUnits = result.getOperateUnits();
            returnInfoList = new ArrayList<Object>();
            if (operateUnits != null && !operateUnits.isEmpty()) {
                Map<String, EntityData> entitys2 = this.getEntitys(param.getContext(), operateUnits, mainKey);
                for (String id : operateUnits) {
                    ReturnInfo returnInfo2 = new ReturnInfo();
                    returnInfo2.setEntity(entitys2.get(id));
                    returnInfoList.add(returnInfo2);
                    uploadReturnInfo.addSuccessNum();
                }
                uploadReturnInfo.setSuccessEntity(returnInfoList);
            }
            Map operateUnitAndForms = result.getOperateUnitAndForms();
            if (("single_form_reject".equals(param.getActionId()) || "single_form_upload".equals(param.getActionId())) && operateUnitAndForms != null && !operateUnitAndForms.isEmpty()) {
                Set<String> keySet2 = operateUnitAndForms.keySet();
                Map<String, EntityData> entitys3 = this.getEntitys(param.getContext(), keySet2, mainKey);
                for (String unit : operateUnitAndForms.keySet()) {
                    ReturnInfo returnInfo3 = new ReturnInfo();
                    returnInfo3.setEntity((EntityData)entitys3.get(unit));
                    Iterator<FormDefine> formDefine = new ArrayList();
                    for (String formKey : (LinkedHashSet)operateUnitAndForms.get(unit)) {
                        FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
                        formDefine.add(queryFormById);
                        uploadReturnInfo.addSuccessFormNums();
                    }
                    returnInfo3.setFormDefine(formDefine);
                    returnInfoList.add(returnInfo3);
                    uploadReturnInfo.addSuccessNum();
                    uploadReturnInfo.setSuccessEntity((List)returnInfoList);
                }
            }
        } else if (WorkFlowType.FORM.equals((Object)startType)) {
            ArrayList<ReturnInfo> returnInfoList2 = new ArrayList<ReturnInfo>();
            Map operateUnitAndForms = result.getOperateUnitAndForms();
            if (operateUnitAndForms != null && !operateUnitAndForms.isEmpty()) {
                keySet = operateUnitAndForms.keySet();
                entitys = this.getEntitys(param.getContext(), keySet, mainKey);
                for (Object unit : operateUnitAndForms.keySet()) {
                    returnInfo = new ReturnInfo();
                    returnInfo.setEntity(entitys.get(unit));
                    ArrayList<FormDefine> formDefine = new ArrayList<FormDefine>();
                    for (String formKey : (LinkedHashSet)operateUnitAndForms.get(unit)) {
                        FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
                        formDefine.add(queryFormById);
                        uploadReturnInfo.addSuccessFormNums();
                    }
                    returnInfo.setFormDefine(formDefine);
                    uploadReturnInfo.setSuccessEntity(returnInfoList2);
                    returnInfoList2.add(returnInfo);
                    uploadReturnInfo.addSuccessNum();
                }
            }
            uploadReturnInfo.setSuccessEntity(returnInfoList2);
        } else {
            Map operateUnitAndGroups = result.getOperateUnitAndGroups();
            returnInfoList = new ArrayList();
            if (operateUnitAndGroups != null && !operateUnitAndGroups.isEmpty()) {
                keySet = operateUnitAndGroups.keySet();
                entitys = this.getEntitys(param.getContext(), keySet, mainKey);
                for (Object unit : operateUnitAndGroups.keySet()) {
                    returnInfo = new ReturnInfo();
                    returnInfo.setEntity(entitys.get(unit));
                    ArrayList<FormGroupDefine> formGroupDefine = new ArrayList<FormGroupDefine>();
                    for (String formGroupKey : (LinkedHashSet)operateUnitAndGroups.get(unit)) {
                        FormGroupDefine formGroupsByFormKey = this.runtimeView.queryFormGroup(formGroupKey);
                        formGroupDefine.add(formGroupsByFormKey);
                        uploadReturnInfo.addSuccessFormGroupNums();
                    }
                    returnInfo.setFormGroupDefine(formGroupDefine);
                    uploadReturnInfo.setSuccessEntity((List)returnInfoList);
                    returnInfoList.add(returnInfo);
                    uploadReturnInfo.addSuccessNum();
                }
            }
            uploadReturnInfo.setSuccessEntity(returnInfoList);
        }
        uploadReturnInfo.setMessage(warning);
        if (uploadBeforeCheck.getUnPassEntityNum() > 0) {
            batchUploadReturnInfo.setUploadBeforeCheck(uploadBeforeCheck);
            batchUploadReturnInfo.setStatus(TASK_STATE);
        }
        batchUploadReturnInfo.setUploadBeforeNodeCheck(uploadBeforeNodeCheck);
        ArrayList<LevelUploadInfo> LevelUploadInfoList = new ArrayList<LevelUploadInfo>();
        if (batchStepByOpt != null && !batchStepByOpt.isEmpty() && WorkFlowType.ENTITY.equals((Object)startType)) {
            for (BatchNoOperate unitKey : batchStepByOpt.keySet()) {
                List list = (List)batchStepByOpt.get(unitKey);
                LevelUploadInfo levelUploadInfo = new LevelUploadInfo();
                levelUploadInfo.setTotal(batchStepByOpt.size());
                levelUploadInfo.setAction(this.setActionMsg(param.getActionId()));
                uploadReturnInfo.addErrorNum();
                levelUploadInfo.setParId(unitKey.getId());
                levelUploadInfo.setParCode(unitKey.getCode());
                levelUploadInfo.setParName(unitKey.getName());
                if (list != null && list.size() > 0) {
                    for (BatchNoOperate batchNoOperate : list) {
                        Info info = new Info();
                        info.setCode(batchNoOperate.getCode());
                        info.setName(batchNoOperate.getName());
                        levelUploadInfo.getUnitInfo().add(info);
                    }
                }
                LevelUploadInfoList.add(levelUploadInfo);
            }
        }
        batchUploadReturnInfo.setLevelUploadInfo(LevelUploadInfoList);
        if (noOperateGroupOrFormMap != null && !noOperateGroupOrFormMap.isEmpty() && !WorkFlowType.ENTITY.equals((Object)startType)) {
            LevelUploadObj levelUploadObj = new LevelUploadObj();
            int total = 0;
            levelUploadObj.setAction(this.setActionMsg(param.getActionId()));
            Map<String, Map<String, List<String>>> leveObj = this.unitToGroupOrForm(noOperateGroupOrFormMap);
            for (String key : leveObj.keySet()) {
                Map<String, List<String>> stringListMap = leveObj.get(key);
                if (stringListMap == null || stringListMap.size() <= 0) continue;
                for (String formOrGroupKey : stringListMap.keySet()) {
                    ++total;
                    if (WorkFlowType.FORM.equals((Object)startType)) {
                        uploadReturnInfo.addErrorFormNums();
                        continue;
                    }
                    if (!WorkFlowType.GROUP.equals((Object)startType)) continue;
                    uploadReturnInfo.addErrorFormGroupNums();
                }
            }
            levelUploadObj.setTotal(total);
            levelUploadObj.setNoOperateGroupOrFormMap(leveObj);
            batchUploadReturnInfo.setLevelUploadObj(levelUploadObj);
        }
        if (uploadReturnInfo.getErrorEntityNum() > 0 || uploadReturnInfo.getSuccessEntityNum() > 0 || uploadReturnInfo.getSuccessFormNums() > 0 || uploadReturnInfo.getErrorFormNums() > 0 || uploadReturnInfo.getErrorFromGroupNums() > 0 || uploadReturnInfo.getSuccessFromGroupNums() > 0 || uploadReturnInfo.getOtherErrorFromGroupNum() > 0 || uploadReturnInfo.getOtherErrorFromNum() > 0 || uploadReturnInfo.getOtherErrorNum() > 0) {
            batchUploadReturnInfo.setUploadReturnInfo(uploadReturnInfo);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (batchUploadReturnInfo != null && batchUploadReturnInfo.getUploadReturnInfo() != null) {
                List successEntity = batchUploadReturnInfo.getUploadReturnInfo().getSuccessEntity();
                ArrayList<ReturnInfo> successEntityDel = new ArrayList<ReturnInfo>();
                if (successEntity != null) {
                    for (ReturnInfo returnInfo2 : successEntity) {
                        if (returnInfo2.getFormGroupDefine() != null && returnInfo2.getFormGroupDefine().isEmpty()) {
                            successEntityDel.add(returnInfo2);
                            continue;
                        }
                        if (returnInfo2.getFormDefine() == null || !returnInfo2.getFormDefine().isEmpty()) continue;
                        successEntityDel.add(returnInfo2);
                    }
                    if (!successEntityDel.isEmpty()) {
                        successEntity.removeAll(successEntityDel);
                    }
                }
            }
            String retStr = mapper.writeValueAsString((Object)batchUploadReturnInfo);
            if (!(uploadReturnInfo.getErrorEntityNum() != 0 || uploadReturnInfo.getErrorFormNums() != 0 || uploadReturnInfo.getErrorFromGroupNums() != 0 || uploadReturnInfo.getOtherErrorFromGroupNum() != 0 || uploadReturnInfo.getOtherErrorNum() != 0 || uploadReturnInfo.getOtherErrorFromNum() != 0 || batchUploadReturnInfo.getExternalBatchUploadResult() != null || batchUploadReturnInfo.getMultCheckResult() != null && batchUploadReturnInfo.getMultCheckResult().getErrorNum() != 0 || batchUploadReturnInfo.getExternalBatchUploadResult() != null && batchUploadReturnInfo.getExternalBatchUploadResult().getNoUploadResultItem() != null)) {
                if (param.isChangeMonitorState()) {
                    if ("act_upload".equals(param.getActionId()) || "cus_upload".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("upload_success_info", (Object)retStr);
                    } else if ("act_reject".equals(param.getActionId()) || "cus_reject".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("reject_success_info", (Object)retStr);
                    } else if ("act_confirm".equals(param.getActionId()) || "cus_confirm".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("confirm_success_info", (Object)retStr);
                    } else if ("act_submit".equals(param.getActionId()) || "cus_submit".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("submit_success_info", (Object)retStr);
                    } else if ("act_return".equals(param.getActionId()) || "cus_return".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("return_success_info", (Object)retStr);
                    } else if ("act_cancel_confirm".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("cancel_confirm_success_info", (Object)retStr);
                    } else if ("act_retrieve".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("retrieve_success_info", (Object)retStr);
                    } else if ("act_apply_return".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("apply_return_success_info", (Object)retStr);
                    } else if ("single_form_reject".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("action_form_reject_info", (Object)"\u6279\u91cf\u5355\u8868\u9a73\u56de\u5b8c\u6210");
                    } else if ("single_form_upload".equals(param.getActionId())) {
                        asyncTaskMonitor.finish("action_form_upload_info", (Object)"\u6279\u91cf\u91cd\u65b0\u63d0\u4ea4\u5b8c\u6210");
                    }
                }
                if (uploadReturnInfo != null) {
                    actionLogInfo.append("\n");
                    if (WorkFlowType.ENTITY.equals((Object)startType)) {
                        actionLogInfo.append("\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d,\u6267\u884c\u6279\u91cf" + actionName + ",\u6210\u529f" + uploadReturnInfo.getSuccessEntityNum() + "\u5bb6\u5355\u4f4d");
                    } else if (WorkFlowType.FORM.equals((Object)startType)) {
                        actionLogInfo.append("\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d\u7684" + uploadReturnInfo.getAllFormNums() + "\u5f20\u62a5\u8868,\u6267\u884c\u6279\u91cf" + actionName + ",");
                    } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                        actionLogInfo.append("\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d," + uploadReturnInfo.getSuccessFromGroupNums() + uploadReturnInfo.getOtherErrorFromGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4,\u6267\u884c\u6279\u91cf" + actionName + ",\u6210\u529f");
                    }
                }
                actionLogInfo.append(this.batchOpttime(startTime));
                logInfo.setLogInfo(actionLogInfo.toString());
            } else {
                actionLogInfo.append("\n");
                if (uploadReturnInfo.getErrorEntityNum() > 0) {
                    actionLogInfo.append(";\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d,\u6267\u884c\u6279\u91cf" + actionName + ",\u6210\u529f" + uploadReturnInfo.getSuccessEntityNum() + "\u5bb6\u5355\u4f4d");
                    if (uploadReturnInfo.getErrorEntityNum() + uploadReturnInfo.getOtherErrorNum() > 0) {
                        actionLogInfo.append("\u5931\u8d25" + (uploadReturnInfo.getErrorEntityNum() + uploadReturnInfo.getOtherErrorNum()) + "\u5bb6");
                        if (batchUploadReturnInfo.getUploadBeforeCheck() != null && batchUploadReturnInfo.getUploadBeforeCheck().getUnPassEntityNum() > 0) {
                            actionLogInfo.append("; \u5176\u4e2d\u5ba1\u6838\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getUploadBeforeCheck().getUnPassEntityNum() + "\u5bb6");
                        }
                        if (batchUploadReturnInfo.getUploadBeforeNodeCheck() != null && batchUploadReturnInfo.getUploadBeforeNodeCheck().getUnPassEntityNum() > 0) {
                            actionLogInfo.append("; \u8282\u70b9\u68c0\u67e5\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getUploadBeforeNodeCheck().getUnPassEntityNum() + "\u5bb6");
                        }
                        if (batchUploadReturnInfo.getMultCheckResult() != null && batchUploadReturnInfo.getMultCheckResult().getErrorNum() > 0) {
                            actionLogInfo.append("; \u7efc\u5408\u5ba1\u6838\u68c0\u67e5\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getMultCheckResult().getErrorNum() + "\u5bb6");
                        }
                        if (batchUploadReturnInfo.getLevelUploadInfo() != null && batchUploadReturnInfo.getLevelUploadInfo().size() > 0) {
                            actionLogInfo.append("\u5c42\u5c42" + actionName + "\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getLevelUploadInfo().size() + "\u5bb6;");
                        }
                        if (uploadReturnInfo.getOtherErrorNum() > 0) {
                            actionLogInfo.append("; \u5176\u4ed6\u539f\u56e0\u5bfc\u81f4\u4e0d\u901a\u8fc7" + uploadReturnInfo.getOtherErrorNum() + "\u5bb6");
                        }
                    }
                } else if (uploadReturnInfo.getErrorFormNums() > 0 || uploadReturnInfo.getErrorFormNums() > 0) {
                    actionLogInfo.append(";\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d\u7684" + uploadReturnInfo.getAllFormNums() + "\u5f20\u62a5\u8868,\u6267\u884c\u6279\u91cf" + actionName + ",");
                    if (uploadReturnInfo.getErrorFormNums() + uploadReturnInfo.getOtherErrorNum() > 0) {
                        actionLogInfo.append("\u5931\u8d25" + uploadReturnInfo.getErrorFormNums() + uploadReturnInfo.getOtherErrorNum() + "\u5f20\u62a5\u8868");
                        if (batchUploadReturnInfo.getUploadBeforeCheck() != null && batchUploadReturnInfo.getUploadBeforeCheck().getUnPassFormsNum() > 0) {
                            actionLogInfo.append("; \u5176\u4e2d\u5ba1\u6838\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getUploadBeforeCheck().getUnPassFormsNum() + "\u5f20\u62a5\u8868");
                        }
                        if (batchUploadReturnInfo.getUploadBeforeNodeCheck() != null && batchUploadReturnInfo.getUploadBeforeNodeCheck().getUnPassFormNum() > 0) {
                            actionLogInfo.append("; \u8282\u70b9\u68c0\u67e5\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getUploadBeforeNodeCheck().getUnPassFormNum() + "\u5f20\u62a5\u8868");
                        }
                        if (batchUploadReturnInfo.getLevelUploadInfo() != null && batchUploadReturnInfo.getLevelUploadInfo().size() > 0) {
                            actionLogInfo.append("\u5c42\u5c42" + actionName + "\u4e0d\u901a\u8fc7" + batchUploadReturnInfo.getLevelUploadInfo().size() + "\u5f20\u62a5\u8868;");
                        }
                        if (uploadReturnInfo.getOtherErrorFromNum() > 0) {
                            actionLogInfo.append("; \u5176\u4ed6\u539f\u56e0\u5bfc\u81f4\u4e0d\u901a\u8fc7" + uploadReturnInfo.getOtherErrorFromNum() + "\u5f20\u62a5\u8868");
                        }
                    }
                } else if (uploadReturnInfo.getErrorFromGroupNums() > 0 || uploadReturnInfo.getOtherErrorFromGroupNum() > 0) {
                    actionLogInfo.append(";\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d\u7684" + uploadReturnInfo.getAllFormGroupNums() + "\u4e2a\u62a5\u8868\u5206\u7ec4,\u6267\u884c\u6279\u91cf" + actionName + "\u6210\u529f" + uploadReturnInfo.getSuccessFromGroupNums() + "\u4e2a\u62a5\u8868\u5206\u7ec4,\u5931\u8d25" + (uploadReturnInfo.getErrorFromGroupNums() + uploadReturnInfo.getOtherErrorFromGroupNum()) + "\u4e2a\u62a5\u8868\u5206\u7ec4,");
                    if (batchUploadReturnInfo.getUploadBeforeCheck() != null && batchUploadReturnInfo.getUploadBeforeCheck().getUnPassFormGroupNum() > 0) {
                        actionLogInfo.append("\u5176\u4e2d" + batchUploadReturnInfo.getUploadBeforeCheck().getUnPassFormGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4\u5ba1\u6838\u4e0d\u901a\u8fc7");
                    }
                    if (batchUploadReturnInfo.getUploadBeforeNodeCheck() != null && batchUploadReturnInfo.getUploadBeforeNodeCheck().getUnPassFormGroupNum() > 0) {
                        actionLogInfo.append(batchUploadReturnInfo.getUploadBeforeNodeCheck().getUnPassFormGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4\u8282\u70b9\u68c0\u67e5\u4e0d\u901a\u8fc7,");
                    }
                    if (batchUploadReturnInfo.getLevelUploadInfo() != null && batchUploadReturnInfo.getLevelUploadInfo().size() > 0) {
                        actionLogInfo.append(batchUploadReturnInfo.getLevelUploadInfo().size() + "\u4e2a\u62a5\u8868\u5206\u7ec4\u5c42\u5c42" + actionName + "\u4e0d\u901a\u8fc7,");
                    }
                    if (uploadReturnInfo.getOtherErrorFromGroupNum() > 0) {
                        actionLogInfo.append("; \u5176\u4ed6\u539f\u56e0\u5bfc\u81f4\u4e0d\u901a\u8fc7" + uploadReturnInfo.getOtherErrorFromGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4");
                    }
                } else if (uploadReturnInfo.getOtherErrorFromGroupNum() > 0) {
                    actionLogInfo.append(";\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d," + uploadReturnInfo.getSuccessFromGroupNums() + uploadReturnInfo.getOtherErrorFromGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4,\u6267\u884c\u6279\u91cf" + actionName + ",\u6210\u529f");
                    actionLogInfo.append(uploadReturnInfo.getSuccessFromGroupNums() + "\u4e2a\u62a5\u8868\u5206\u7ec4,\u5931\u8d25" + uploadReturnInfo.getOtherErrorFromGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4,\u5176\u4ed6\u539f\u56e0\u5bfc\u81f4" + uploadReturnInfo.getOtherErrorFromGroupNum() + "\u4e2a\u62a5\u8868\u5206\u7ec4\u4e0d\u901a\u8fc7,");
                } else if (uploadReturnInfo.getOtherErrorFromNum() > 0) {
                    actionLogInfo.append(";\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d," + uploadReturnInfo.getAllFormNums() + "\u5f20\u62a5\u8868,\u6267\u884c\u6279\u91cf" + actionName + ",\u6210\u529f");
                    actionLogInfo.append(uploadReturnInfo.getSuccessFormNums() + "\u5f20\u62a5\u8868,\u5931\u8d25" + uploadReturnInfo.getOtherErrorFromNum() + "\u5f20\u62a5\u8868,\u5176\u4ed6\u539f\u56e0\u5bfc\u81f4" + uploadReturnInfo.getOtherErrorFromNum() + "\u5f20\u62a5\u8868\u4e0d\u901a\u8fc7,");
                } else {
                    actionLogInfo.append(";\u5171" + uploadReturnInfo.getAllEntityNums() + "\u5bb6\u5355\u4f4d,\u6267\u884c\u6279\u91cf" + actionName + ",\u6210\u529f" + uploadReturnInfo.getSuccessEntityNum() + "\u5bb6\u5355\u4f4d");
                    actionLogInfo.append(",\u5931\u8d25" + uploadReturnInfo.getOtherErrorNum() + "\u5bb6\u5355\u4f4d,\u5176\u4ed6\u539f\u56e0\u5bfc\u81f4" + uploadReturnInfo.getOtherErrorNum() + "\u5bb6\u5355\u4f4d\u4e0d\u901a\u8fc7,");
                }
                actionLogInfo.append(this.batchOpttime(startTime));
                logInfo.setLogInfo(actionLogInfo.toString());
                String errorResult = this.getErrorResult(param.getActionId(), startType);
                if (param.isChangeMonitorState()) {
                    asyncTaskMonitor.error(errorResult, null, retStr);
                }
            }
            ContextUser user = NpContextHolder.getContext().getUser();
            this.sendOptEvent(param, user.getId(), result, startType);
        }
        catch (Exception e) {
            logInfo.setLogInfo(actionLogInfo + e.getMessage());
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return logInfo;
    }

    private Map<String, EntityData> getEntitys(JtableContext context, Set<String> unitIds, String mainKey) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        entityQueryByKeysInfo.setEntityKeys(new ArrayList<String>(unitIds));
        entityQueryByKeysInfo.setContext(context);
        entityQueryByKeysInfo.setEntityViewKey(mainKey);
        EntityByKeysReturnInfo entityDataByKeys = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
        return entityDataByKeys.getEntitys();
    }

    private String getErrorResult(String actionCode, WorkFlowType workflowType) {
        String errorResult = "";
        if (WorkFlowType.ENTITY.equals((Object)workflowType)) {
            if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
                errorResult = "upload_unit_fail";
            } else if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
                errorResult = "submit_unit_fail";
            } else if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
                errorResult = "reject_unit_fail";
            } else if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
                errorResult = "return_unit_fail";
            } else if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
                errorResult = "confrim_unit_fail";
            } else if ("act_cancel_confirm".equals(actionCode)) {
                errorResult = "cancel_confrim_unit_fail";
            } else if ("act_retrieve".equals(actionCode)) {
                errorResult = "retrieve_unit_info";
            } else if ("act_apply_return".equals(actionCode)) {
                errorResult = "apply_return_unit_info";
            } else if ("single_form_reject".equals(actionCode)) {
                errorResult = "batch_action_form_reject_info";
            } else if ("single_form_upload".equals(actionCode)) {
                errorResult = "batch_action_form_upload_info";
            }
        } else if (WorkFlowType.FORM.equals((Object)workflowType)) {
            if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
                errorResult = "upload_report_fail";
            } else if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
                errorResult = "submit_report_fail";
            } else if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
                errorResult = "reject_report_fail";
            } else if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
                errorResult = "return_report_fail";
            } else if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
                errorResult = "confrim_report_fail";
            } else if ("act_cancel_confirm".equals(actionCode)) {
                errorResult = "cancel_confrim_report_fail";
            } else if ("act_retrieve".equals(actionCode)) {
                errorResult = "retrieve_report_info";
            } else if ("act_apply_return".equals(actionCode)) {
                errorResult = "apply_return_report_info";
            }
        } else if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
                errorResult = "upload_group_fail";
            } else if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
                errorResult = "submit_group_fail";
            } else if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
                errorResult = "reject_group_fail";
            } else if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
                errorResult = "return_group_fail";
            } else if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
                errorResult = "confrim_group_fail";
            } else if ("act_cancel_confirm".equals(actionCode)) {
                errorResult = "cancel_confrim_group_fail";
            } else if ("act_retrieve".equals(actionCode)) {
                errorResult = "retrieve_group_info";
            } else if ("act_apply_return".equals(actionCode)) {
                errorResult = "apply_return_group_info";
            }
        } else if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
            errorResult = "upload_other_fail";
        } else if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
            errorResult = "submit_other_fail";
        } else if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
            errorResult = "reject_other_fail";
        } else if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
            errorResult = "return_other_fail";
        } else if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
            errorResult = "confrim_other_fail";
        } else if ("act_cancel_confirm".equals(actionCode)) {
            errorResult = "cancel_confrim_other_fail";
        } else if ("act_retrieve".equals(actionCode)) {
            errorResult = "retrieve_other_info";
        } else if ("act_apply_return".equals(actionCode)) {
            errorResult = "apply_return_other_info";
        }
        return errorResult;
    }

    private void batchCalculate(BatchExecuteTaskParam param, List<BatchWorkFlowInfo> needCalcList, WorkflowConfig workflowConfig, WorkFlowType startType, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormKey(param.getContext().getFormKey());
        jtableContext.setFormSchemeKey(param.getContext().getFormSchemeKey());
        DUserActionParam userActionParam = param.getUserActionParam();
        jtableContext.setFormulaSchemeKey(userActionParam.getCalculateFormulaValue());
        jtableContext.setTaskKey(param.getContext().getTaskKey());
        jtableContext.setFormGroupKey(param.getContext().getFormGroupKey());
        jtableContext.setFormKey(param.getContext().getFormKey());
        HashMap<String, List<String>> calcFormula = new HashMap<String, List<String>>();
        if (needCalcList.size() > 0) {
            String calculateFormulaSchemeKey = userActionParam.getCalculateFormulaValue();
            if (StringUtils.isEmpty((String)calculateFormulaSchemeKey)) {
                calculateFormulaSchemeKey = param.getContext().getFormulaSchemeKey();
            }
            Map<String, DimensionValue> mergeDimension = this.getMergeDimension(needCalcList);
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            batchCalculateInfo.setDimensionSet(mergeDimension);
            if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                List<String> needForms = this.getNeedForms(needCalcList, startType);
                for (String form : needForms) {
                    calcFormula.put(form, new ArrayList());
                }
                batchCalculateInfo.setFormulas(calcFormula);
            }
            batchCalculateInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
            batchCalculateInfo.setFormulaSchemeKey(calculateFormulaSchemeKey);
            batchCalculateInfo.setTaskKey(param.getContext().getTaskKey());
            batchCalculateInfo.setVariableMap(param.getContext().getVariableMap());
            batchCalculateInfo.setContext(jtableContext);
            double progress = 0.0;
            WorkflowAsyncProgressMonitor workflowAsyncProgressMonitor = new WorkflowAsyncProgressMonitor(asyncTaskMonitor, 0.3, progress);
            this.batchCalculateService.batchCalculateForm(batchCalculateInfo, workflowAsyncProgressMonitor);
            if (asyncTaskMonitor.isCancel() && param.isChangeMonitorState()) {
                String retStr = "\u4efb\u52a1\u53d6\u6d88";
                asyncTaskMonitor.canceled(retStr, (Object)retStr);
            }
        }
    }

    private void batchCheck(BatchExecuteTaskParam param, List<BatchWorkFlowInfo> needCheckList, Map<String, List<String>> groupToForm, UploadBeforeCheck uploadBeforeCheck, WorkflowConfig workflowConfig, WorkFlowType startType, AsyncTaskMonitor asyncTaskMonitor, boolean enableCalc, WorkflowAction workflowAction, List<WorkflowDataBean> filterForm, String mainDim, String mainKey, boolean specialAudit, String taskCode) {
        HashMap<String, List<String>> checkFormula = new HashMap<String, List<String>>();
        DUserActionParam userActionParam = param.getUserActionParam();
        String checkFormulaSchemeKey = userActionParam.getCheckFormulaValue();
        if (StringUtils.isEmpty((String)checkFormulaSchemeKey)) {
            checkFormulaSchemeKey = param.getContext().getFormulaSchemeKey();
        }
        BatchWorkFlowInfo batchWorkFlowInfo = needCheckList.get(0);
        String checkFilter = batchWorkFlowInfo.getCheckFilter();
        Map<String, DimensionValue> dimensionSet = this.getMergeDimension(needCheckList);
        boolean judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(param.getContext().getFormSchemeKey(), dimensionSet);
        if (!judgeCurrentcyType) {
            int checkCurrencyType = userActionParam.getCheckCurrencyType();
            String checkCurrencyValue = userActionParam.getCheckCurrencyValue();
            this.uploadCheckFliterUtil.setCheckConditions(param.getContext().getTaskKey(), dimensionSet, checkCurrencyType, checkCurrencyValue);
        }
        DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, param.getContext().getFormSchemeKey());
        if (specialAudit) {
            this.queryLastOperateUtil.batchCheckForSpecialCheck(param, needCheckList, groupToForm, uploadBeforeCheck, workflowConfig, startType, asyncTaskMonitor, workflowAction, filterForm, mainDim, mainKey, dimensionSet, checkFilter, enableCalc, checkFormulaSchemeKey, taskCode);
        } else {
            CheckParam checkParam = new CheckParam();
            checkParam.setActionId(asyncTaskMonitor.getTaskId());
            checkParam.setFilterCondition(checkFilter);
            checkParam.setDimensionCollection(dimensionCollection);
            checkParam.setVariableMap(param.getContext().getVariableMap());
            checkParam.setMode(Mode.FORM);
            if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                List<String> needCheckForms = this.getNeedForms(needCheckList, startType);
                for (String form : needCheckForms) {
                    checkFormula.put(form, new ArrayList());
                }
                checkParam.setRangeKeys(needCheckForms);
                uploadBeforeCheck.setNeedCheckForms(needCheckForms);
            }
            Double progress = 0.0;
            if (enableCalc) {
                progress = 0.3;
            }
            double scale = 0.4;
            WorkFlowCheckProgressMonitor workFlowCheckProgressMonitor = new WorkFlowCheckProgressMonitor(asyncTaskMonitor, scale, progress);
            String[] formulaSchemeKeys = checkFormulaSchemeKey.split(";");
            workFlowCheckProgressMonitor.setCount(formulaSchemeKeys.length);
            workFlowCheckProgressMonitor.setCoefficient(scale /= (double)formulaSchemeKeys.length);
            for (String formulaSchemeKey : formulaSchemeKeys) {
                workFlowCheckProgressMonitor.setProgress(progress);
                checkParam.setFormulaSchemeKey(formulaSchemeKey);
                this.iCheckService.batchCheck(checkParam, (IFmlMonitor)workFlowCheckProgressMonitor);
                progress = progress + scale;
            }
        }
        this.haveCheckResults(needCheckList, param, uploadBeforeCheck, startType, groupToForm, checkFormula, workflowConfig, asyncTaskMonitor.getTaskId(), workflowAction);
        this.insertBatchCheckResult(needCheckList, param, checkFormula, workflowConfig, asyncTaskMonitor.getTaskId(), workflowAction);
    }

    private void batchNeedNodeBeforeUpload(JtableContext contex, List<BatchWorkFlowInfo> needNodeCheckList, UploadBeforeNodeCheck uploadBeforeNodeCheck, WorkFlowType startType, DUserActionParam dUserActionParam) {
        String nodeCheckId = "";
        String formKeys = "";
        NodeCheckInfo nodeCheckInfo = new NodeCheckInfo();
        JtableContext nodeCheckContext = new JtableContext(contex);
        Map<String, DimensionValue> mergeDimension = this.getMergeDimension(needNodeCheckList);
        boolean judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(nodeCheckContext.getFormSchemeKey(), mergeDimension);
        if (!judgeCurrentcyType) {
            int nodeCheckCurrencyType = dUserActionParam.getNodeCheckCurrencyType();
            String nodeCheckCurrencyValue = dUserActionParam.getNodeCheckCurrencyValue();
            this.uploadCheckFliterUtil.setNodecheckConditions(nodeCheckContext.getTaskKey(), mergeDimension, nodeCheckCurrencyType, nodeCheckCurrencyValue);
        }
        nodeCheckContext.setDimensionSet(mergeDimension);
        nodeCheckInfo.setContext(nodeCheckContext);
        if (!WorkFlowType.ENTITY.equals((Object)startType)) {
            List<String> needCheckForms = this.getNeedForms(needNodeCheckList, startType);
            for (String form : needCheckForms) {
                formKeys = formKeys + form + ";";
            }
            nodeCheckInfo.setFormKeys(formKeys.length() > 0 ? formKeys.substring(0, formKeys.length() - 1) : "");
        }
        BatchNodeCheckAsyncTaskExecutor subJob = new BatchNodeCheckAsyncTaskExecutor();
        Map params = subJob.getParams();
        params.put("NR_ARGS", SimpleParamConverter.SerializationUtils.serializeToString((Object)nodeCheckInfo));
        try {
            JobContext jobContext = JobContextHolder.getJobContext();
            if (jobContext != null) {
                nodeCheckId = JobContextHolder.getJobContext().executeRealTimeSubJob((AbstractRealTimeJob)subJob);
            } else {
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)nodeCheckInfo));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchNodeCheckAsyncTaskExecutor());
                nodeCheckId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            }
        }
        catch (JobExecutionException e) {
            logger.error(e.getMessage());
        }
        uploadBeforeNodeCheck.setAsyncTaskId(nodeCheckId);
    }

    private List<String> queryEndFill(JtableContext context, List<BatchWorkFlowInfo> allDimensionList, String mainDim, String mainKey, UploadReturnInfo uploadReturnInfo, List<ReturnInfo> warning) {
        ArrayList<String> endFillUnits = new ArrayList<String>();
        boolean b = this.allowStoped(context.getTaskKey());
        if (!b) {
            return endFillUnits;
        }
        ContextUser user = NpContextHolder.getContext().getUser();
        StateEntites stateEntites = new StateEntites();
        stateEntites.setUserId(user.getId());
        Map<String, DimensionValue> mergeDimension = this.getMergeDimension(allDimensionList);
        DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet(mergeDimension);
        stateEntites.setDims(dimensionValueSet);
        stateEntites.setFormSchemeKey(context.getFormSchemeKey());
        Map stateInfo = this.stateService.getStateInfo(stateEntites);
        if (stateInfo != null && stateInfo.size() > 0) {
            Set keySet = stateInfo.keySet();
            Set<String> unitIds = keySet.stream().map(e -> e.getValue(mainDim).toString()).collect(Collectors.toSet());
            Map<String, EntityData> entitys = this.getEntitys(context, unitIds, mainKey);
            for (DimensionValueSet state : stateInfo.keySet()) {
                StateConst stateConst = (StateConst)stateInfo.get(state);
                if (!StateConst.ENDFILL.equals((Object)stateConst)) continue;
                String value = state.getValue(mainDim).toString();
                endFillUnits.add(value);
                ReturnInfo returnInfo = new ReturnInfo();
                EntityData entityData = entitys.get(value);
                returnInfo.setEntity(entityData);
                returnInfo.setMessage("\u5355\u4f4d\u5df2\u7ec8\u6b62\u586b\u62a5");
                uploadReturnInfo.addOtherErrorNum();
                warning.add(returnInfo);
            }
        }
        return endFillUnits;
    }

    private List<String> timeSetting(JtableContext context, List<BatchWorkFlowInfo> allDimensionList, String mainKey, UploadReturnInfo uploadReturnInfo, List<ReturnInfo> warning) {
        ArrayList<String> timeUnitKeys = new ArrayList<String>();
        List collect = allDimensionList.stream().map(e -> e.getDimensionValue()).collect(Collectors.toList());
        Map mergeDimensionSetMap = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionSetMap(collect);
        List dimensionValueSetList = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSetList((Map)mergeDimensionSetMap);
        Map unitKeys = this.deSetTimeProvide.queryUnits(context.getFormSchemeKey(), dimensionValueSetList);
        if (unitKeys != null && unitKeys.size() > 0) {
            Set<String> unitkeyList = unitKeys.keySet();
            Map<String, EntityData> entitys = this.getEntitys(context, unitkeyList, mainKey);
            for (String key : unitkeyList) {
                timeUnitKeys.add(key);
                ReturnInfo returnInfo = new ReturnInfo();
                EntityData entityData = entitys.get(key);
                returnInfo.setEntity(entityData);
                returnInfo.setMessage((String)unitKeys.get(key));
                uploadReturnInfo.addOtherErrorNum();
                warning.add(returnInfo);
            }
        }
        return timeUnitKeys;
    }

    private Map<String, Map<String, List<String>>> unitToGroupOrForm(Map<BatchNoOperate, Map<BatchNoOperate, List<BatchNoOperate>>> noOperateGroupOrFormMap) {
        HashMap<String, Map<String, List<String>>> leveObj = new HashMap<String, Map<String, List<String>>>();
        for (BatchNoOperate parUnit : noOperateGroupOrFormMap.keySet()) {
            Map<BatchNoOperate, List<BatchNoOperate>> batchNoOperateListMap = noOperateGroupOrFormMap.get(parUnit);
            String parUnitStr = this.formatStr(parUnit);
            HashMap formUnitMap = new HashMap();
            for (BatchNoOperate formOrGroupKey : batchNoOperateListMap.keySet()) {
                ArrayList<String> noOperateList = new ArrayList<String>();
                String formOrGroupStr = this.formatStr(formOrGroupKey);
                List<BatchNoOperate> batchNoOperates = batchNoOperateListMap.get(formOrGroupKey);
                for (BatchNoOperate entity : batchNoOperates) {
                    String sonEntityStr = this.formatStr(entity);
                    noOperateList.add(sonEntityStr);
                }
                formUnitMap.put(formOrGroupStr, noOperateList);
            }
            leveObj.put(parUnitStr, formUnitMap);
        }
        return leveObj;
    }

    private String formatStr(BatchNoOperate batchNoOperate) {
        return batchNoOperate.getCode() + "|" + batchNoOperate.getName();
    }

    private void setDefaultValue(Map<String, DimensionValue> newDimensionSet, WorkFlowType queryStartType) {
        Iterator<String> iterator = newDimensionSet.keySet().iterator();
        while (iterator.hasNext()) {
            String key;
            switch (key = iterator.next()) {
                case "MD_CURRENCY_CODE": {
                    newDimensionSet.get(key).setValue("CNY");
                    break;
                }
                case "MD_GCADJTYPE_CODE": {
                    newDimensionSet.get(key).setValue("BEFOREADJ");
                    break;
                }
                case "MD_GCORGTYPE_CODE": {
                    if (queryStartType.equals((Object)WorkFlowType.ENTITY)) break;
                    newDimensionSet.get(key).setValue("MD_ORG_CORPORATE");
                }
            }
        }
    }

    private List<WorkflowDataBean> getFilterForm(BatchExecuteTaskParam param, UploadReturnInfo uploadReturnInfo, List<ReturnInfo> warning, WorkFlowType queryStartType, String mainKey, String mainDim, List<DimensionCombination> dimensionCombinations, List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty) {
        ArrayList<WorkflowDataBean> workflowList = new ArrayList<WorkflowDataBean>();
        Map<String, Set<String>> unitToFormKeys = this.queryLastOperateUtil.unitToFormKeys(formKeysCondition, mainDim, queryStartType);
        String message = "";
        if (WorkFlowType.ENTITY.equals((Object)queryStartType)) {
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                WorkflowDataBean workflowData = new WorkflowDataBean();
                workflowData.setDimSet(dimensionValueSet);
                workflowData.setFormSchemeKey(param.getContext().getFormSchemeKey());
                workflowData.setFormKey(param.getContext().getFormKey());
                workflowData.setFormKeys(param.getFormKeys());
                workflowList.add(workflowData);
            }
        } else {
            HashMap unitToReportKeysMap = new HashMap();
            for (Map.Entry<String, Map<String, Boolean>> formOrGroupKey : formOrGroupKeysByDuty.entrySet()) {
                String title;
                LinkedHashSet<String> noAuthFormOrGroupKeys = new LinkedHashSet<String>();
                LinkedHashSet<String> hasAuthFormOrGroupKeys = new LinkedHashSet<String>();
                String unitKey = formOrGroupKey.getKey();
                Set<String> fliterFormKeys = unitToFormKeys.get(unitKey);
                Map<String, Boolean> formOrGroupKeyValue = formOrGroupKey.getValue();
                for (Map.Entry<String, Boolean> formOrGroup : formOrGroupKeyValue.entrySet()) {
                    String key = formOrGroup.getKey();
                    Boolean authValue = formOrGroup.getValue();
                    if (authValue.booleanValue() && fliterFormKeys.contains(key)) {
                        hasAuthFormOrGroupKeys.add(key);
                    } else {
                        noAuthFormOrGroupKeys.add(key);
                    }
                    unitToReportKeysMap.put(unitKey, hasAuthFormOrGroupKeys);
                }
                if (noAuthFormOrGroupKeys == null || noAuthFormOrGroupKeys.size() <= 0) continue;
                ReturnInfo returnInfo = new ReturnInfo();
                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                entityQueryByKeyInfo.setEntityViewKey(mainKey);
                entityQueryByKeyInfo.setEntityKey(unitKey);
                EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                returnInfo.setEntity(queryEntityDataByKey.getEntity());
                List queryFormsById = this.runtimeView.queryFormsById(new ArrayList(noAuthFormOrGroupKeys));
                returnInfo.setFormDefine(queryFormsById);
                returnInfo.setMessage(message);
                ArrayList<FormGroupDefine> groupDefines = new ArrayList<FormGroupDefine>();
                for (String value : noAuthFormOrGroupKeys) {
                    FormGroupDefine formGroupDefine = this.runtimeView.queryFormGroup(value);
                    if (formGroupDefine == null) continue;
                    groupDefines.add(formGroupDefine);
                }
                returnInfo.setFormGroupDefine(groupDefines);
                ArrayList<String> titles = new ArrayList<String>();
                String msg = ",\u5f53\u524d\u7528\u6237\u65e0\u6743\u9650\u8fdb\u884c" + this.setActionMsg(param.getActionId()) + "\u64cd\u4f5c";
                if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                    for (FormGroupDefine groupDefine : groupDefines) {
                        title = groupDefine.getTitle();
                        titles.add(title + msg);
                    }
                } else if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                    for (FormDefine formDefine : queryFormsById) {
                        title = formDefine.getTitle();
                        titles.add(title + msg);
                    }
                }
                returnInfo.setStateMessage(titles);
                warning.add(returnInfo);
            }
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
                WorkflowDataBean workflowData = new WorkflowDataBean();
                Set set = (Set)unitToReportKeysMap.get(dimensionValueSet.getValue(mainDim));
                workflowData.setFormGroupKeys(set != null ? new ArrayList(set) : new ArrayList());
                workflowData.setFormKeys(set != null ? new ArrayList(set) : new ArrayList());
                workflowData.setDimSet(dimensionValueSet);
                workflowData.setFormSchemeKey(param.getContext().getFormSchemeKey());
                workflowList.add(workflowData);
            }
        }
        return workflowList;
    }

    private List<DimensionAccessFormInfo.AccessFormInfo> getFormKeysCondition(JtableContext context, WorkFlowType startType, List<String> groupKeys, List<String> formKeys) {
        if (WorkFlowType.GROUP.equals((Object)startType)) {
            LinkedHashSet<String> formKeySet = new LinkedHashSet<String>();
            ArrayList formGroupKeys = groupKeys == null ? new ArrayList() : groupKeys;
            for (String groupKey : formGroupKeys) {
                List allFormsInGroup = new ArrayList();
                try {
                    allFormsInGroup = this.runtimeView.getAllFormsInGroup(groupKey);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                for (FormDefine formDefine : allFormsInGroup) {
                    formKeySet.add(formDefine.getKey());
                }
            }
            if (formKeys == null) {
                formKeys = new ArrayList();
            }
            if (formKeySet.size() > 0) {
                formKeys.addAll(formKeySet);
            }
        } else {
            formKeys = formKeys == null ? new ArrayList<String>() : formKeys;
        }
        return this.getAccessFormInfos(context, formKeys);
    }

    private List<DimensionAccessFormInfo.AccessFormInfo> getAccessFormInfos(JtableContext context, List<String> formKeys) {
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setTaskKey(context.getTaskKey());
        accessFormParam.setFormSchemeKey(context.getFormSchemeKey());
        if (formKeys == null) {
            accessFormParam.setFormKeys(new ArrayList());
        } else {
            accessFormParam.setFormKeys(formKeys);
        }
        Map dimensionSetMap = context.getDimensionSet();
        Map<String, DimensionValue> recombineDims = this.recombineDims(context.getFormSchemeKey(), dimensionSetMap);
        DimensionCollection dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(recombineDims, (String)context.getFormSchemeKey());
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        DimensionAccessFormInfo batchAccessForms = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessForms = batchAccessForms.getAccessForms();
        return accessForms;
    }

    private Map<String, DimensionValue> recombineDims(String formSchemeKey, Map<String, DimensionValue> dimensionSetMap) {
        HashMap<String, DimensionValue> dimensionSetMapTemp = new HashMap<String, DimensionValue>();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String dw = formScheme.getDw();
        IEntityDefine queryDimisionByView = this.queryDimisionByView(dw);
        String dimensionName = null;
        if (queryDimisionByView != null) {
            dimensionName = queryDimisionByView.getDimensionName();
            dimensionSetMapTemp.put(dimensionName, dimensionSetMap.get(dimensionName));
        }
        dimensionSetMapTemp.put("DATATIME", dimensionSetMap.get("DATATIME"));
        String dims = formScheme.getDims();
        String dimName = null;
        if (dims != null) {
            String[] dimKeys;
            for (String key : dimKeys = dims.split(";")) {
                if (!StringUtils.isNotEmpty((String)key)) continue;
                IEntityDefine dimisionByView = this.queryDimisionByView(key);
                if (dimisionByView != null) {
                    dimName = dimisionByView.getDimensionName();
                }
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimName);
                dimensionValue.setValue("");
                dimensionSetMapTemp.put(dimName, dimensionValue);
            }
        }
        return dimensionSetMapTemp;
    }

    private IEntityDefine queryDimisionByView(String entityViewKey) {
        IEntityDefine entity = null;
        try {
            EntityViewDefine entityView = this.viewRtCtl.buildEntityView(entityViewKey);
            if (entityView != null && entityView.getEntityId() != null) {
                entity = this.iEntityMetaService.queryEntity(entityView.getEntityId());
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u7ef4\u5ea6\u51fa\u9519", e);
        }
        return entity;
    }

    private List<String> getGroupKeys(BatchExecuteTaskParam param, List<String> forms, UploadReturnInfo uploadReturnInfo, List<FormGroupDefine> queryFormGroupsDefine, String message, String unitKey, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty) {
        ArrayList<String> groupKeys = new ArrayList();
        ArrayList<FormGroupDefine> formGroupsByFormKey = new ArrayList<FormGroupDefine>();
        ArrayList<String> haveFormGroup = new ArrayList<String>();
        for (String form : forms) {
            List allFormGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(form);
            for (FormGroupDefine formGroupDefine : allFormGroupsByFormKey) {
                if (haveFormGroup.contains(formGroupDefine.getKey()) || !formGroupDefine.getFormSchemeKey().equals(param.getContext().getFormSchemeKey())) continue;
                haveFormGroup.add(formGroupDefine.getKey());
                List<String> formGroupKeys = param.getFormGroupKeys();
                if (formGroupKeys != null && formGroupKeys.size() > 0 && !formGroupKeys.contains(formGroupDefine.getKey())) continue;
                Map<String, Boolean> keys = formOrGroupKeysByDuty.get(unitKey);
                if (keys != null) {
                    Boolean hasAuth = keys.get(formGroupDefine.getKey());
                    if (!hasAuth.booleanValue()) {
                        message = "\u62a5\u8868\u5206\u7ec4\u6ca1\u6709\u4e0a\u62a5\u6743\u9650";
                        queryFormGroupsDefine.add(this.runtimeView.queryFormGroup(formGroupDefine.getKey()));
                        continue;
                    }
                    formGroupsByFormKey.add(formGroupDefine);
                    continue;
                }
                formGroupsByFormKey.add(formGroupDefine);
            }
        }
        groupKeys = formGroupsByFormKey.stream().distinct().map(m -> m.getKey()).collect(Collectors.toList());
        return groupKeys;
    }

    private LinkedHashSet<String> getGroupKeys(BatchExecuteTaskParam param, List<String> forms, List<String> sourceGroupKeys, String unitKey, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty) {
        LinkedHashSet<String> groupKeys = new LinkedHashSet<String>();
        LinkedHashSet<String> groupKeyTemps = new LinkedHashSet<String>();
        ArrayList<String> haveFormGroup = new ArrayList<String>();
        for (String form : forms) {
            List allFormGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(form);
            for (FormGroupDefine formGroupDefine : allFormGroupsByFormKey) {
                if (haveFormGroup.contains(formGroupDefine.getKey()) || !formGroupDefine.getFormSchemeKey().equals(param.getContext().getFormSchemeKey())) continue;
                haveFormGroup.add(formGroupDefine.getKey());
                List<String> formGroupKeys = param.getFormGroupKeys();
                if (formGroupKeys != null && formGroupKeys.size() > 0 && !formGroupKeys.contains(formGroupDefine.getKey())) continue;
                Map<String, Boolean> keys = formOrGroupKeysByDuty.get(unitKey);
                if (keys != null) {
                    Boolean hasAuth = keys.get(formGroupDefine.getKey());
                    if (!hasAuth.booleanValue()) continue;
                    groupKeyTemps.add(formGroupDefine.getKey());
                    continue;
                }
                groupKeyTemps.add(formGroupDefine.getKey());
            }
        }
        if (sourceGroupKeys != null && sourceGroupKeys.size() > 0) {
            for (String key : groupKeyTemps) {
                if (!sourceGroupKeys.contains(key)) continue;
                groupKeys.add(key);
            }
        } else {
            groupKeys = groupKeyTemps;
        }
        return groupKeys;
    }

    private List<String> getFormKeys(List<String> forms, String actionCode, UploadReturnInfo uploadReturnInfo, List<String> noAuthForm, String message, String unitKey, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty) {
        ArrayList<String> formKeys = new ArrayList<String>();
        for (String form : forms) {
            Map<String, Boolean> keys = formOrGroupKeysByDuty.get(unitKey);
            if (keys != null) {
                Boolean hasAuth = keys.get(form);
                if (!hasAuth.booleanValue()) {
                    message = "\u8868\u6ca1\u6709\u4e0a\u62a5\u6743\u9650";
                    noAuthForm.add(form);
                    continue;
                }
                formKeys.add(form);
                continue;
            }
            formKeys.add(form);
        }
        return formKeys;
    }

    private LinkedHashSet<String> getFormKeys(List<String> forms, String actionCode, List<String> sourceFormKeys, String unitKey, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty) {
        LinkedHashSet<String> formKeys = new LinkedHashSet<String>();
        LinkedHashSet<String> formKeyTemps = new LinkedHashSet<String>();
        for (String form : forms) {
            Map<String, Boolean> keys = formOrGroupKeysByDuty.get(unitKey);
            if (keys != null) {
                Boolean hasAuth = keys.get(form);
                if (!hasAuth.booleanValue()) continue;
                formKeyTemps.add(form);
                continue;
            }
            formKeyTemps.add(form);
        }
        if (sourceFormKeys != null && sourceFormKeys.size() > 0) {
            for (String key : formKeyTemps) {
                if (!sourceFormKeys.contains(key)) continue;
                formKeys.add(key);
            }
        } else {
            formKeys = formKeyTemps;
        }
        return formKeys;
    }

    private Map<String, List<String>> creatGroupToForm(Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo, JtableContext context) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        try {
            for (DimensionValueSet dimension : batchWorkflowDataInfo.keySet()) {
                Map map = batchWorkflowDataInfo.get(dimension);
                for (String formGroupKey : map.keySet()) {
                    List allFormsInGroup = this.runtimeView.getAllFormsInGroup(formGroupKey);
                    ArrayList<String> forms = new ArrayList<String>();
                    for (FormDefine form : allFormsInGroup) {
                        forms.add(form.getKey());
                    }
                    List<String> formKeys = forms.stream().map(r -> r.toString()).collect(Collectors.toList());
                    List<DimensionAccessFormInfo.AccessFormInfo> accessFormInfos = this.getAccessFormInfos(context, formKeys);
                    List forms2 = new ArrayList();
                    for (DimensionAccessFormInfo.AccessFormInfo access : accessFormInfos) {
                        forms2 = access.getFormKeys();
                    }
                    result.put(formGroupKey, forms2);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u5206\u7ec4\u548c\u62a5\u8868\u5bf9\u6620\u5c04\u62a5\u9519", e);
        }
        return result;
    }

    private String setActionMsg(String actionId) {
        if (actionId.equals("act_upload") || actionId.equals("cus_upload")) {
            return "\u4e0a\u62a5";
        }
        if (actionId.equals("act_reject") || actionId.equals("cus_reject")) {
            return "\u9000\u56de";
        }
        if (actionId.equals("act_confirm") || actionId.equals("cus_confirm")) {
            return "\u786e\u8ba4";
        }
        if (actionId.equals("act_submit") || actionId.equals("cus_submit")) {
            return "\u9001\u5ba1";
        }
        if (actionId.equals("act_return") || actionId.equals("cus_return")) {
            return "\u9000\u5ba1";
        }
        if (actionId.equals("batch_act_upload")) {
            return "\u6279\u91cf\u4e0a\u62a5";
        }
        if (actionId.equals("batch_act_reject")) {
            return "\u6279\u91cf\u9000\u56de";
        }
        if (actionId.equals("batch_act_confirm")) {
            return "\u6279\u91cf\u786e\u8ba4";
        }
        if (actionId.equals("batch_act_submit")) {
            return "\u6279\u91cf\u9001\u5ba1";
        }
        if (actionId.equals("batch_act_return")) {
            return "\u6279\u91cf\u9000\u5ba1";
        }
        if (actionId.equals("act_cancel_confirm")) {
            return "\u53d6\u6d88\u786e\u8ba4";
        }
        if (actionId.equals("act_retrieve")) {
            return "\u53d6\u56de";
        }
        if (actionId.equals("act_apply_return")) {
            return "\u7533\u8bf7\u9000\u56de";
        }
        if (actionId.equals("single_form_reject")) {
            return "\u6309\u8868\u9a73\u56de";
        }
        if (actionId.equals("single_form_upload")) {
            return "\u91cd\u65b0\u63d0\u4ea4";
        }
        return null;
    }

    private Map<String, DimensionValue> getMergeDimension(List<BatchWorkFlowInfo> sourceDimension) {
        HashMap<String, DimensionValue> mergeDimension = new HashMap<String, DimensionValue>();
        HashMap dimensionValue = new HashMap();
        for (BatchWorkFlowInfo batchWorkFlowInfo : sourceDimension) {
            Map<String, DimensionValue> dimensionSourceValue = batchWorkFlowInfo.getDimensionValue();
            for (String key : dimensionSourceValue.keySet()) {
                if (dimensionValue.containsKey(key)) {
                    ((Set)dimensionValue.get(key)).add(dimensionSourceValue.get(key).getValue());
                    continue;
                }
                HashSet<String> dimensionValueSet = new HashSet<String>();
                dimensionValueSet.add(dimensionSourceValue.get(key).getValue());
                dimensionValue.put(key, dimensionValueSet);
            }
        }
        for (String key : dimensionValue.keySet()) {
            ArrayList list = new ArrayList((Collection)dimensionValue.get(key));
            String str = StringUtils.join(list.iterator(), (String)";");
            DimensionValue dimensionVal = new DimensionValue();
            dimensionVal.setName(key);
            dimensionVal.setValue(str);
            mergeDimension.put(key, dimensionVal);
        }
        return mergeDimension;
    }

    private List<String> getNeedForms(List<BatchWorkFlowInfo> sourceDimension, WorkFlowType queryStartType) {
        ArrayList<String> allForms = new ArrayList<String>();
        ArrayList<String> resultForms = new ArrayList<String>();
        for (BatchWorkFlowInfo workFlowInfo : sourceDimension) {
            if (allForms.contains(workFlowInfo.getFormKey())) continue;
            allForms.add(workFlowInfo.getFormKey());
        }
        if (allForms.size() > 0) {
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                ArrayList<String> forms = new ArrayList<String>();
                for (String formGroup : allForms) {
                    try {
                        for (int i = 0; i < formGroup.split(";").length; ++i) {
                            forms.add(formGroup.split(";")[i]);
                        }
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                resultForms.addAll(forms);
            } else if (WorkFlowType.FORM.equals((Object)queryStartType)) {
                resultForms.addAll(allForms);
            }
        }
        return resultForms;
    }

    private Map<String, LinkedHashSet<String>> getEntityToForm(BatchExecuteTaskParam param, WorkFlowType workflowType, List<BatchWorkFlowInfo> sourceDimension, String mainDim, String mainKey, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty, List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition) {
        HashMap<String, LinkedHashSet<String>> result = new HashMap<String, LinkedHashSet<String>>();
        if (sourceDimension.size() == 0) {
            return result;
        }
        String formSchemeKey = param.getContext().getFormSchemeKey();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : formKeysCondition) {
            Map dimensions = accessFormInfo.getDimensions();
            List dimensionValueSetList = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimension : dimensionValueSetList) {
                String unitKey = dimension.getValue(mainDim).toString();
                for (BatchWorkFlowInfo workflowInfo : sourceDimension) {
                    List forms = accessFormInfo.getFormKeys();
                    if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                        String formGroupKey = workflowInfo.getFormGroupKey();
                        ArrayList<String> formGroupKeys = new ArrayList<String>();
                        formGroupKeys.add(formGroupKey);
                        LinkedHashSet<String> groupKeys = this.getGroupKeys(param, forms, formGroupKeys, unitKey, formOrGroupKeysByDuty);
                        result.put(unitKey, groupKeys);
                        continue;
                    }
                    if (!WorkFlowType.FORM.equals((Object)workflowType)) continue;
                    String formKey = workflowInfo.getFormKey();
                    ArrayList<String> formKeyTemps = new ArrayList<String>();
                    formKeyTemps.add(formKey);
                    LinkedHashSet<String> formKeys = this.getFormKeys(forms, param.getActionId(), formKeyTemps, unitKey, formOrGroupKeysByDuty);
                    result.put(unitKey, formKeys);
                }
            }
        }
        return result;
    }

    private List<String> getUnitKeys(JtableContext jtableContext, String mainDim) {
        List<String> allUnit = new ArrayList<String>();
        String formSchemeKey = jtableContext.getFormSchemeKey();
        Map dimensionSet = jtableContext.getDimensionSet();
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        String unit = ((DimensionValue)dimensionSet.get(mainDim)).getValue();
        if (!StringUtils.isEmpty((String)unit)) {
            String[] unitArr = unit.split(";");
            try {
                DataEntityType type;
                IDataEntity iEntityTable = this.getEntityDataList(jtableContext, dimensionSet, formScheme);
                if (iEntityTable != null && DataEntityType.DataEntity.equals((Object)(type = iEntityTable.type())) && unitArr != null && unitArr.length > 0) {
                    for (String key : unitArr) {
                        IDataEntityRow allChildRows;
                        List rowList;
                        allUnit.add(key);
                        IDataEntityRow selfData = iEntityTable.findByEntityKey(key);
                        List rowList2 = selfData.getRowList();
                        if (rowList2 != null && rowList2.size() > 0) {
                            for (IEntityRow entityRow : rowList2) {
                                String parentEntityKey;
                                if (entityRow == null || "-".equals(parentEntityKey = entityRow.getParentEntityKey())) continue;
                                allUnit.add(parentEntityKey);
                            }
                        }
                        if ((rowList = (allChildRows = iEntityTable.getChildRows(key)).getRowList()) == null || rowList.size() <= 0) continue;
                        List keys = rowList.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
                        allUnit.addAll(keys);
                    }
                }
            }
            catch (Exception e1) {
                logger.error("\u83b7\u53d6\u5355\u4f4d\u96c6\u5408\u5931\u8d25", e1);
            }
        }
        if (allUnit != null && allUnit.size() > 0) {
            allUnit = allUnit.stream().distinct().collect(Collectors.toList());
        }
        return allUnit;
    }

    public IDataEntity getEntityDataList(JtableContext jtableContext, Map<String, DimensionValue> dimensionValueSet, FormSchemeDefine formScheme) {
        IDataEntity iDataEntity = null;
        try {
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(formScheme.getKey());
            EntityViewDefine entityViewDefine = dwEntity.getEntityViewDefine();
            DataEntityContext dataEntityContext = new DataEntityContext();
            dataEntityContext.setSorted(false);
            dataEntityContext.setTaskKey(jtableContext.getTaskKey());
            dataEntityContext.setFormSchemeKey(jtableContext.getFormSchemeKey());
            dataEntityContext.setQueryDim(false);
            dataEntityContext.setQueryByKey(false);
            iDataEntity = this.dataEntityService.queryEntityWithDimVal(entityViewDefine, dataEntityContext, true, DimensionValueSetUtil.getDimensionValueSet((Map)jtableContext.getDimensionSet()), null);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return iDataEntity;
    }

    private Map<String, LinkedHashSet<String>> getEntityToForm(List<BatchWorkFlowInfo> sourceDimension, WorkFlowType queryStartType, String mainDim) {
        HashMap unitToForm = new HashMap();
        HashMap<String, LinkedHashSet<String>> result = new HashMap<String, LinkedHashSet<String>>();
        HashMap unitToFormGroup = new HashMap();
        for (BatchWorkFlowInfo workFlowInfo : sourceDimension) {
            String unit = workFlowInfo.getDimensionValue().get(mainDim).getValue();
            if (WorkFlowType.GROUP.equals((Object)queryStartType)) {
                if (unitToFormGroup.containsKey(unit)) {
                    ((LinkedHashSet)unitToFormGroup.get(unit)).add(workFlowInfo.getFormGroupKey());
                } else {
                    LinkedHashSet<String> formGroupList = new LinkedHashSet<String>();
                    formGroupList.add(workFlowInfo.getFormGroupKey());
                    unitToFormGroup.put(unit, formGroupList);
                }
                result = unitToFormGroup;
                continue;
            }
            if (!WorkFlowType.FORM.equals((Object)queryStartType)) continue;
            if (unitToForm.containsKey(unit)) {
                ((LinkedHashSet)unitToForm.get(unit)).add(workFlowInfo.getFormKey());
            } else {
                LinkedHashSet<String> formList = new LinkedHashSet<String>();
                formList.add(workFlowInfo.getFormKey());
                unitToForm.put(unit, formList);
            }
            result = unitToForm;
        }
        return result;
    }

    private void haveCheckResults(List<BatchWorkFlowInfo> allBatchWorkFlowInfo, BatchExecuteTaskParam param, UploadBeforeCheck uploadBeforeCheck, WorkFlowType queryStartType, Map<String, List<String>> groupToForm, Map<String, List<String>> checkFormula, WorkflowConfig workflowConfig, String asyncTaskId, WorkflowAction workflowAction) {
        HashMap<String, String> resultEntity = new HashMap<String, String>();
        HashMap<String, List<String>> resultForm = new HashMap<String, List<String>>();
        HashMap resultFormGroup = new HashMap();
        HashMap<String, List<Object>> checkType = new HashMap<String, List<Object>>();
        HashMap<String, List<Object>> needCommit = new HashMap<String, List<Object>>();
        JtableContext context = param.getContext();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        String dwDimName = dwEntity.getDimensionName();
        List formKeys = checkFormula.keySet().stream().collect(Collectors.toList());
        String checkFormulaSchemeKey = workflowAction.getActionParam().getCheckFormulaValue();
        if (StringUtils.isEmpty((String)checkFormulaSchemeKey)) {
            checkFormulaSchemeKey = param.getContext().getFormulaSchemeKey();
        }
        String[] formulaSchemeKeys = checkFormulaSchemeKey.split(";");
        Map<String, List<Integer>> customFormulaTypeMap = this.checkResultParamForReportUtil.getCustomFormulaTypeMap(context.getTaskKey());
        ActionParam actionParam = workflowAction.getActionParam();
        List ignoreErrorStatus = actionParam.getIgnoreErrorStatus();
        List needCommentErrorStatus = actionParam.getNeedCommentErrorStatus();
        Map<String, List<Integer>> flowFormulaTypeMap = this.checkResultParamForReportUtil.getFlowFormulaTypeMap(context.getFormSchemeKey(), needCommentErrorStatus, ignoreErrorStatus);
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setBatchId(asyncTaskId);
        checkResultQueryParam.setVariableMap(context.getVariableMap());
        checkResultQueryParam.setMode(Mode.FORM);
        checkResultQueryParam.setRangeKeys(formKeys);
        checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(formulaSchemeKeys));
        for (BatchWorkFlowInfo batchWorkFlowInfo : allBatchWorkFlowInfo) {
            Map<String, DimensionValue> dimensionSet = batchWorkFlowInfo.getDimensionValue();
            DimensionValueSet mergeDimension = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
            Map dimensionSetMap = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)mergeDimension);
            boolean judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(param.getContext().getFormSchemeKey(), dimensionSetMap);
            if (!judgeCurrentcyType) {
                int checkCurrencyType = actionParam.getCheckCurrencyType();
                String calcuteFormulaValue = actionParam.getCheckCurrencyValue();
                this.uploadCheckFliterUtil.setCheckConditions(param.getContext().getTaskKey(), dimensionSetMap, checkCurrencyType, calcuteFormulaValue);
            }
            DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSetMap);
            String currUnitKey = (String)dimensionValueSet.getValue(dwDimName);
            List<Object> erroStatus = new ArrayList();
            List<Object> needErrorComment = new ArrayList();
            boolean enableCustomConfig = this.checkResultParamForReportUtil.enableCustomConfig(dwEntity, currUnitKey, context.getTaskKey(), context.getFormSchemeKey());
            if (enableCustomConfig) {
                erroStatus = customFormulaTypeMap.get("AFFECT");
                needErrorComment = customFormulaTypeMap.get("NEEDEXPLAIN");
            } else {
                erroStatus = flowFormulaTypeMap.get("AFFECT");
                needErrorComment = flowFormulaTypeMap.get("NEEDEXPLAIN");
            }
            if (erroStatus == null || erroStatus.size() <= 0) continue;
            HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
            for (Integer n : erroStatus) {
                checkTypes.put(n, null);
            }
            for (Integer n : needErrorComment) {
                checkTypes.put(n, false);
            }
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, context.getFormSchemeKey());
            checkResultQueryParam.setFilterCondition(batchWorkFlowInfo.getCheckFilter());
            checkResultQueryParam.setDimensionCollection(dimensionCollection);
            checkResultQueryParam.setQueryDimension(dimensionCollection);
            checkResultQueryParam.setCheckTypes(checkTypes);
            CheckResult checkResult = this.iCheckResultService.queryBatchCheckResult(checkResultQueryParam);
            if (checkResult == null) continue;
            List resultDatas = checkResult.getResultData();
            if (!(resultDatas == null || resultDatas.size() <= 0 || checkType.containsKey(currUnitKey) && needCommit.containsKey(currUnitKey))) {
                checkType.put(currUnitKey, erroStatus);
                needCommit.put(currUnitKey, needErrorComment);
            }
            for (CheckResultData result : resultDatas) {
                List<String> list;
                String unitKey = result.getUnitKey();
                if (!resultEntity.containsKey(unitKey)) {
                    resultEntity.put(unitKey, result.getUnitTitle());
                }
                if (!resultForm.containsKey(unitKey)) {
                    list = new ArrayList<String>();
                    list.add(result.getFormulaData().getFormKey());
                    resultForm.put(unitKey, list);
                    continue;
                }
                list = (List)resultForm.get(unitKey);
                if (list.contains(result.getFormulaData().getFormKey())) continue;
                list.add(result.getFormulaData().getFormKey());
                resultForm.put(unitKey, list);
            }
        }
        for (String unitKey : resultForm.keySet()) {
            ArrayList<String> groupList = new ArrayList<String>();
            for (String group : groupToForm.keySet()) {
                List list = (List)resultForm.get(unitKey);
                List<String> oneGroupForm = groupToForm.get(group);
                for (String onForm : oneGroupForm) {
                    if (list.contains(onForm) && !groupList.contains(group)) {
                        groupList.add(group);
                    }
                    if (!list.contains("00000000-0000-0000-0000-000000000000") || groupList.contains("00000000-0000-0000-0000-000000000000")) continue;
                    groupList.add("00000000-0000-0000-0000-000000000000");
                }
            }
            resultFormGroup.put(unitKey, groupList);
        }
        uploadBeforeCheck.setAsyncTaskId(asyncTaskId);
        uploadBeforeCheck.setNeedCommentErrorStatus(needCommit);
        uploadBeforeCheck.setCanIgnoreErrorStatus(checkType);
        uploadBeforeCheck.setResultEntity(resultEntity);
        if (WorkFlowType.FORM.equals((Object)queryStartType)) {
            uploadBeforeCheck.setResultForms(resultForm);
        } else {
            uploadBeforeCheck.setResultForms(new HashMap());
        }
        uploadBeforeCheck.setResultFormGroup(resultFormGroup);
    }

    private void haveNodeCheckResults(List<BatchWorkFlowInfo> allBatchWorkFlowInfo, UploadBeforeNodeCheck uploadBeforeNodeCheck, String mainDim, Map<String, List<String>> groupToForm) {
        NodeCheckResultInfo haveNodeCheckResults = new NodeCheckResultInfo();
        try {
            haveNodeCheckResults = (NodeCheckResultInfo)JsonUtil.toObject((String)this.asyncTaskManager.queryDetail(uploadBeforeNodeCheck.getAsyncTaskId()).toString(), NodeCheckResultInfo.class);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        HashMap<String, String> unPassEntity = new HashMap<String, String>();
        HashMap<String, List<String>> unPassForm = new HashMap<String, List<String>>();
        HashMap resultFormGroup = new HashMap();
        if (null == haveNodeCheckResults || null == haveNodeCheckResults.getNodeCheckResult() || haveNodeCheckResults.getNodeCheckResult().size() == 0) {
            return;
        }
        for (BatchWorkFlowInfo batchWorkFlowInfo : allBatchWorkFlowInfo) {
            String value = batchWorkFlowInfo.getDimensionValue().get(mainDim).getValue();
            for (String key : haveNodeCheckResults.getNodeCheckResult().keySet()) {
                List<NodeCheckResultItem> NodeCheckResultItemList = haveNodeCheckResults.getNodeCheckResult().get(key);
                for (NodeCheckResultItem nodeCheckResultItem : NodeCheckResultItemList) {
                    List<String> list;
                    if (!nodeCheckResultItem.getUnitKey().equals(value)) continue;
                    unPassEntity.put(value, nodeCheckResultItem.getUnitTitle());
                    if (unPassForm.containsKey(value)) {
                        list = (List)unPassForm.get(value);
                        if (list.contains(nodeCheckResultItem.getNodeCheckFieldMessage().getFormKey())) continue;
                        list.add(nodeCheckResultItem.getNodeCheckFieldMessage().getFormKey());
                        unPassForm.put(value, list);
                        continue;
                    }
                    list = new ArrayList();
                    list.add(nodeCheckResultItem.getNodeCheckFieldMessage().getFormKey());
                    unPassForm.put(value, list);
                }
            }
        }
        for (String unitKey : unPassForm.keySet()) {
            HashSet<String> groupList = new HashSet<String>();
            List list = (List)unPassForm.get(unitKey);
            for (String formKey : list) {
                for (String group : groupToForm.keySet()) {
                    if (groupList.contains(group) || !groupToForm.get(group).contains(formKey)) continue;
                    groupList.add(group);
                }
            }
            resultFormGroup.put(unitKey, new ArrayList(groupList));
        }
        uploadBeforeNodeCheck.setUnPassForms(unPassForm);
        uploadBeforeNodeCheck.setUnPassEntity(unPassEntity);
        uploadBeforeNodeCheck.setUnPassFormGroup(resultFormGroup);
    }

    private Map<String, List<BatchWorkFlowInfo>> splitGrouping(Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo, BatchExecuteTaskParam param, UploadReturnInfo uploadReturnInfo, List<ReturnInfo> warning, String mainDim, String mainKey, WorkFlowType startType, Map<String, List<String>> groupKeyToForm, WorkflowConfig workflowConfig, TaskDefine taskDefine, Set<String> fliterUnitKeys) {
        ArrayList<BatchWorkFlowInfo> needCalcList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> needCheckList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> needNodeCheckList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> allDimensionList = new ArrayList<BatchWorkFlowInfo>();
        ArrayList<BatchWorkFlowInfo> multCheckList = new ArrayList<BatchWorkFlowInfo>();
        this.forceUpload(param, batchWorkflowDataInfo, mainDim);
        for (DimensionValueSet uploadDimension : batchWorkflowDataInfo.keySet()) {
            String unitKey = uploadDimension.getValue(mainDim).toString();
            if (fliterUnitKeys != null && fliterUnitKeys.contains(unitKey)) continue;
            Map workFlowList = batchWorkflowDataInfo.get(uploadDimension);
            ArrayList<FormDefine> forms = new ArrayList<FormDefine>();
            ArrayList<FormGroupDefine> formGroups = new ArrayList<FormGroupDefine>();
            boolean isCurrAction = true;
            boolean isCurrUnit = true;
            for (Map.Entry flowInfo : workFlowList.entrySet()) {
                String key = (String)flowInfo.getKey();
                List value = (List)flowInfo.getValue();
                for (WorkflowDataInfo workflowDataInfo : value) {
                    List actions;
                    BatchWorkFlowInfo batchWorkFlowInfo = new BatchWorkFlowInfo();
                    if (param.getActionId().equals("act_retrieve")) {
                        // empty if block
                    }
                    if ((actions = workflowDataInfo.getActions()) != null && actions.size() > 0) {
                        Optional<WorkflowAction> findAny = actions.stream().filter(e -> e.getCode().equals(param.getActionId())).findAny();
                        if (findAny.isPresent()) {
                            WorkflowAction workflowAction = findAny.get();
                            if (workflowAction.getActionParam().isForceCommit() && param.isForceCommit()) {
                                isCurrUnit = true;
                                isCurrAction = true;
                            } else {
                                this.split(param, workflowAction, workflowConfig, startType, uploadDimension, key, groupKeyToForm, mainDim, mainKey, needCalcList, needCheckList, needNodeCheckList, multCheckList, taskDefine);
                                isCurrUnit = true;
                                isCurrAction = true;
                            }
                        } else {
                            isCurrUnit = false;
                            isCurrAction = false;
                        }
                    } else {
                        isCurrUnit = false;
                        isCurrAction = false;
                    }
                    if (isCurrUnit) {
                        Map dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimension);
                        Map dimensionSetParam = param.getContext().getDimensionSet();
                        for (String diemesionName : dimensionSetParam.keySet()) {
                            if (dimensionSet.containsKey(diemesionName)) continue;
                            dimensionSet.put(diemesionName, dimensionSetParam.get(diemesionName));
                        }
                        if (WorkFlowType.FORM.equals((Object)startType)) {
                            batchWorkFlowInfo.setFormKey(key);
                        } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                            batchWorkFlowInfo.setFormGroupKey(key);
                            List<String> formkeyList = groupKeyToForm.get(key);
                            StringBuffer groupTypeFormKey = new StringBuffer();
                            for (String form : formkeyList) {
                                groupTypeFormKey.append(form + ";");
                            }
                            batchWorkFlowInfo.setFormKey(groupTypeFormKey.toString().substring(0, groupTypeFormKey.length() - 1));
                        }
                        batchWorkFlowInfo.setDimensionValue(dimensionSet);
                        batchWorkFlowInfo.setTaskId(workflowDataInfo.getTaskId());
                        allDimensionList.add(batchWorkFlowInfo);
                    }
                    if (isCurrAction || !StringUtils.isNotEmpty((String)key)) continue;
                    if (WorkFlowType.FORM.equals((Object)startType)) {
                        FormDefine queryFormById = this.runtimeView.queryFormById(key);
                        forms.add(queryFormById);
                        uploadReturnInfo.addotherErrorFromNum();
                        continue;
                    }
                    if (!WorkFlowType.GROUP.equals((Object)startType)) continue;
                    FormGroupDefine formGroupsById = this.runtimeView.queryFormGroup(key);
                    formGroups.add(formGroupsById);
                    uploadReturnInfo.addOtherErrorFromGroupNum();
                }
            }
            boolean otherError = false;
            if (!isCurrAction && WorkFlowType.ENTITY.equals((Object)startType)) {
                if (param.getActionId().equals("single_form_reject") || param.getActionId().equals("single_form_upload")) continue;
                uploadReturnInfo.addOtherErrorNum();
                otherError = true;
            }
            ReturnInfo returnInfo = new ReturnInfo();
            if (!(forms != null && forms.size() > 0 || formGroups != null && formGroups.size() > 0) && !otherError) continue;
            Map dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimension);
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityViewKey(mainKey);
            entityQueryByKeyInfo.setEntityKey(((DimensionValue)dimensionSet.get(mainDim)).getValue());
            entityQueryByKeyInfo.setContext(param.getContext());
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            EntityData entityData = queryEntityDataByKey.getEntity();
            returnInfo.setEntity(queryEntityDataByKey.getEntity());
            returnInfo.setFormDefine(forms);
            returnInfo.setFormGroupDefine(formGroups);
            List<String> buildMessage = this.buildMessage(param.getContext().getFormSchemeKey(), uploadDimension, entityData.getTitle(), forms, formGroups, param.getActionId());
            returnInfo.setStateMessage(buildMessage);
            if (buildMessage != null && buildMessage.size() > 0) {
                returnInfo.setMessage(buildMessage.get(0));
            }
            warning.add(returnInfo);
        }
        HashMap<String, List<BatchWorkFlowInfo>> splitGroup = new HashMap<String, List<BatchWorkFlowInfo>>();
        splitGroup.put("needCalcList", needCalcList);
        splitGroup.put("needCheckList", needCheckList);
        splitGroup.put("needNodeCheckList", needNodeCheckList);
        splitGroup.put(MULTCHEK, multCheckList);
        splitGroup.put("allDimensionList", allDimensionList);
        return splitGroup;
    }

    private void split(BatchExecuteTaskParam param, WorkflowAction action, WorkflowConfig workflowConfig, WorkFlowType startType, DimensionValueSet uploadDimension, String key, Map<String, List<String>> groupKeyToForm, String mainDim, String mainKey, List<BatchWorkFlowInfo> needCalcList, List<BatchWorkFlowInfo> needCheckList, List<BatchWorkFlowInfo> needNodeCheckList, List<BatchWorkFlowInfo> multCheckList, TaskDefine taskDefine) {
        Object batchCheckWorkFlowInfo;
        Map dimensionSetParam;
        Map dimensionSet;
        DimensionValue dimensionValue;
        StringBuffer groupTypeFormKey;
        List<String> formkeyList;
        boolean isUpload;
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        List<String> unitKeys = this.getDimensionCollection(taskDefine, param.getContext().getDimensionSet(), param.getContext().getFormSchemeKey(), mainDim);
        boolean bl = isUpload = action.getCode().equals("act_upload") || action.getCode().equals("act_submit") || action.getCode().equals("cus_upload") || action.getCode().equals("cus_submit") || action.getCode().equals("single_form_upload");
        if (action.getActionParam().isNeedAutoCalculate() || workflowConfig.isCalculateBefor() && isUpload) {
            BatchWorkFlowInfo batchCalcWorkFlowInfo = new BatchWorkFlowInfo();
            Map dimensionSet2 = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimension);
            Map dimensionSetParam2 = param.getContext().getDimensionSet();
            for (String dimension : dimensionSetParam2.keySet()) {
                if (dimensionSet2.containsKey(dimension)) continue;
                dimensionSet2.put(dimension, dimensionSetParam2.get(dimension));
            }
            if (WorkFlowType.FORM.equals((Object)startType)) {
                batchCalcWorkFlowInfo.setFormKey(key);
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                batchCalcWorkFlowInfo.setFormGroupKey(key);
                formkeyList = groupKeyToForm.get(key);
                groupTypeFormKey = new StringBuffer();
                for (String form : formkeyList) {
                    groupTypeFormKey.append(form + ";");
                }
                batchCalcWorkFlowInfo.setFormKey(groupTypeFormKey.toString().substring(0, groupTypeFormKey.length() - 1));
            }
            batchCalcWorkFlowInfo.setDimensionValue(dimensionSet2);
            dimensionValue = (DimensionValue)dimensionSet2.get(mainDim);
            if (unitKeys != null && unitKeys.contains(dimensionValue.getValue().toString())) {
                needCalcList.add(batchCalcWorkFlowInfo);
            } else {
                needCalcList.add(batchCalcWorkFlowInfo);
            }
        }
        if (this.enableMultCheck(flowsSetting) && isUpload) {
            dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimension);
            dimensionSetParam = param.getContext().getDimensionSet();
            for (String dimension : dimensionSetParam.keySet()) {
                if (dimensionSet.containsKey(dimension)) continue;
                dimensionSet.put(dimension, dimensionSetParam.get(dimension));
            }
            batchCheckWorkFlowInfo = new BatchWorkFlowInfo();
            if (WorkFlowType.FORM.equals((Object)startType)) {
                ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setFormKey(key);
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                formkeyList = groupKeyToForm.get(key);
                groupTypeFormKey = new StringBuffer();
                for (String form : formkeyList) {
                    groupTypeFormKey.append(form + ";");
                }
                ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setFormKey(groupTypeFormKey.toString().substring(0, groupTypeFormKey.length() - 1));
            }
            ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setDimensionValue(dimensionSet);
            ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setIgnoreErrorStatus(action.getActionParam().getIgnoreErrorStatus());
            ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setNeedCommentErrorStatus(action.getActionParam().getNeedCommentErrorStatus());
            ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setCheckFilter(action.getActionParam().getCheckFilter());
            multCheckList.add((BatchWorkFlowInfo)batchCheckWorkFlowInfo);
        } else {
            if (action.getActionParam().isNeedAutoCheck() && !this.enableMultCheck(flowsSetting) && isUpload) {
                dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimension);
                dimensionSetParam = param.getContext().getDimensionSet();
                for (String dimension : dimensionSetParam.keySet()) {
                    if (dimensionSet.containsKey(dimension)) continue;
                    dimensionSet.put(dimension, dimensionSetParam.get(dimension));
                }
                batchCheckWorkFlowInfo = new BatchWorkFlowInfo();
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setFormKey(key);
                } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                    formkeyList = groupKeyToForm.get(key);
                    groupTypeFormKey = new StringBuffer();
                    for (String form : formkeyList) {
                        groupTypeFormKey.append(form + ";");
                    }
                    ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setFormKey(groupTypeFormKey.toString().substring(0, groupTypeFormKey.length() - 1));
                }
                ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setDimensionValue(dimensionSet);
                ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setIgnoreErrorStatus(action.getActionParam().getIgnoreErrorStatus());
                ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setNeedCommentErrorStatus(action.getActionParam().getNeedCommentErrorStatus());
                ((BatchWorkFlowInfo)batchCheckWorkFlowInfo).setCheckFilter(action.getActionParam().getCheckFilter());
                dimensionValue = (DimensionValue)dimensionSet.get(mainDim);
                if (unitKeys != null && unitKeys.contains(dimensionValue.getValue().toString())) {
                    needCheckList.add((BatchWorkFlowInfo)batchCheckWorkFlowInfo);
                } else {
                    needCheckList.add((BatchWorkFlowInfo)batchCheckWorkFlowInfo);
                }
            }
            if (action.getActionParam().isNodeCheck()) {
                dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)uploadDimension);
                dimensionSetParam = param.getContext().getDimensionSet();
                for (String dimension : dimensionSetParam.keySet()) {
                    if (dimensionSet.containsKey(dimension)) continue;
                    dimensionSet.put(dimension, dimensionSetParam.get(dimension));
                }
                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                entityQueryByKeyInfo.setEntityViewKey(mainKey);
                entityQueryByKeyInfo.setEntityKey(((DimensionValue)dimensionSet.get(mainDim)).getValue());
                EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                if (!queryEntityDataByKey.getEntity().isLeaf()) {
                    BatchWorkFlowInfo batchNodeCheckWorkFlowInfo = new BatchWorkFlowInfo();
                    batchNodeCheckWorkFlowInfo.setDimensionValue(dimensionSet);
                    if (WorkFlowType.FORM.equals((Object)startType)) {
                        batchNodeCheckWorkFlowInfo.setFormKey(key);
                    } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                        batchNodeCheckWorkFlowInfo.setFormGroupKey(key);
                        List<String> formkeyList2 = groupKeyToForm.get(key);
                        StringBuffer groupTypeFormKey2 = new StringBuffer();
                        for (String form : formkeyList2) {
                            groupTypeFormKey2.append(form + ";");
                        }
                        batchNodeCheckWorkFlowInfo.setFormKey(groupTypeFormKey2.toString().substring(0, groupTypeFormKey2.length() - 1));
                    }
                    DimensionValue dimensionValue2 = (DimensionValue)dimensionSet.get(mainDim);
                    if (unitKeys != null && unitKeys.contains(dimensionValue2.getValue().toString())) {
                        needNodeCheckList.add(batchNodeCheckWorkFlowInfo);
                    } else {
                        needNodeCheckList.add(batchNodeCheckWorkFlowInfo);
                    }
                }
            }
        }
    }

    private List<String> buildMessage(String fromSchemeKey, DimensionValueSet dimensionValueSet, String entityTitle, List<FormDefine> forms, List<FormGroupDefine> formGroups, String actionId) {
        ArrayList<String> messages = new ArrayList<String>();
        Map<Object, String> stateMap = this.queryState(fromSchemeKey, dimensionValueSet, forms, formGroups, actionId);
        if (forms != null && forms.size() > 0) {
            for (FormDefine formDefine : forms) {
                StringBuffer sb = new StringBuffer();
                if (BatchWorkflowServiceImpl.isChinese()) {
                    sb.append(formDefine.getTitle() + ", \u72b6\u6001\u4e3a" + stateMap.get(formDefine.getKey()) + ",");
                } else {
                    sb.append(formDefine.getTitle() + ", In the state of " + stateMap.get(formDefine.getKey()) + ",");
                }
                String message = this.message(actionId);
                sb.append(message);
                messages.add(sb.toString());
            }
        } else if (formGroups != null && formGroups.size() > 0) {
            for (FormGroupDefine formGroupDefine : formGroups) {
                StringBuffer sb = new StringBuffer();
                if (BatchWorkflowServiceImpl.isChinese()) {
                    sb.append(formGroupDefine.getTitle() + ", \u72b6\u6001\u4e3a" + stateMap.get(formGroupDefine.getKey()) + ",");
                } else {
                    sb.append(formGroupDefine.getTitle() + ", In the state of " + stateMap.get(formGroupDefine.getKey()) + ",");
                }
                String message = this.message(actionId);
                sb.append(message);
                messages.add(sb.toString());
            }
        } else {
            StringBuffer sb = new StringBuffer();
            if (BatchWorkflowServiceImpl.isChinese()) {
                sb.append(", \u72b6\u6001\u4e3a" + stateMap.get(dimensionValueSet) + ",");
            } else {
                sb.append(", In the state of " + stateMap.get(dimensionValueSet) + ",");
            }
            String message = this.message(actionId);
            sb.append(message);
            messages.add(sb.toString());
        }
        return messages;
    }

    private Map<Object, String> queryState(String fromSchemeKey, DimensionValueSet dimensionValueSet, List<FormDefine> forms, List<FormGroupDefine> formGroups, String actionId) {
        HashMap<Object, String> stateMap = new HashMap<Object, String>();
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setFormSchemeKey(fromSchemeKey);
        dataEntryParam.setDim(dimensionValueSet);
        if (forms != null && forms.size() > 0) {
            for (FormDefine formDefine : forms) {
                dataEntryParam.setFormKey(formDefine.getKey());
                ActionStateBean actionState = this.dataentryFlowService.queryReportState(dataEntryParam);
                String titile = actionState.getTitile();
                stateMap.put(formDefine.getKey(), titile);
            }
        } else if (formGroups != null && formGroups.size() > 0) {
            for (FormGroupDefine formGroupDefine : formGroups) {
                dataEntryParam.setGroupKey(formGroupDefine.getKey());
                ActionStateBean actionState = this.dataentryFlowService.queryReportState(dataEntryParam);
                String titile = actionState.getTitile();
                stateMap.put(formGroupDefine.getKey(), titile);
            }
        } else {
            ActionStateBean actionState = this.dataentryFlowService.queryReportState(dataEntryParam);
            String titile = actionState.getTitile();
            stateMap.put(dimensionValueSet, titile);
        }
        return stateMap;
    }

    private String message(String actionId) {
        if (actionId.equals("cus_submit") || actionId.equals("act_submit")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u9001\u5ba1\u64cd\u4f5c";
            }
            return " Cannot perform the approval operation";
        }
        if (actionId.equals("cus_return") || actionId.equals("act_return")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u9000\u5ba1\u64cd\u4f5c";
            }
            return " The exit operation cannot be performed";
        }
        if (actionId.equals("cus_reject") || actionId.equals("act_reject")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u9000\u56de\u64cd\u4f5c";
            }
            return " The back operation cannot be performed";
        }
        if (actionId.equals("cus_confirm") || actionId.equals("act_confirm")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u786e\u8ba4\u64cd\u4f5c";
            }
            return " The confirmation operation cannot be performed";
        }
        if (actionId.equals("cus_upload") || actionId.equals("act_upload")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u4e0a\u62a5\u64cd\u4f5c";
            }
            return " The report operation cannot be performed";
        }
        if (actionId.equals("act_cancel_confirm")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u53d6\u6d88\u786e\u8ba4\u64cd\u4f5c";
            }
            return " The unconfirmation operation cannot be performed";
        }
        if (actionId.equals("act_retrieve")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u53d6\u56de\u64cd\u4f5c";
            }
            return " The fetch operation cannot be performed";
        }
        if (actionId.equals("act_apply_return")) {
            if (BatchWorkflowServiceImpl.isChinese()) {
                return " \u4e0d\u80fd\u6267\u884c\u7533\u8bf7\u9000\u56de\u64cd\u4f5c";
            }
            return " The request return operation cannot be performed";
        }
        return null;
    }

    private void dataVersion(BatchExecuteTaskParam param, Map<String, DimensionValue> dimensionValues, Map<String, List<String>> unit2Forms) {
        HashMap<String, DimensionValue> dataVersionDimensionSet = new HashMap<String, DimensionValue>();
        for (Map.Entry<String, DimensionValue> oneEntry : dimensionValues.entrySet()) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(oneEntry.getKey());
            dimensionValue.setValue(oneEntry.getValue().getValue());
            dataVersionDimensionSet.put(oneEntry.getKey(), dimensionValue);
        }
        try {
            ArrayList<String> errorDimensionList = new ArrayList<String>();
            JtableContext jtableContextInfo = new JtableContext();
            jtableContextInfo.setDimensionSet(dataVersionDimensionSet);
            jtableContextInfo.setFormSchemeKey(param.getContext().getFormSchemeKey());
            jtableContextInfo.setTaskKey(param.getContext().getTaskKey());
            List<Map<String, DimensionValue>> splitDimensionValueList = this.dimensionValueProvider.splitDimensionValueList(jtableContextInfo, errorDimensionList, true);
            this.dataStateCheckService.batchUpdateDimensionCache(param.getContext(), splitDimensionValueList);
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u6d41\u7a0b\u64cd\u4f5c\u66f4\u65b0\u7ef4\u5ea6\u7ea7\u522b\u7f13\u5b58\u503c\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        try {
            BatchCreateSnapshotContext batchCreateSnapshotContext = DataEntryUtil.getDataSnapshotBatchParam(this.snapshotService, dataVersionDimensionSet, param, unit2Forms, this.jtableParamService, this.jtableEntityService, this.runtimeView, this.dimCollectionBuildUtil);
            if (batchCreateSnapshotContext.getCreateSnapshotInfos().size() == 0) {
                return;
            }
            try {
                this.snapshotService.batchCreateSnapshot(batchCreateSnapshotContext, null);
            }
            catch (Exception e) {
                logger.error("\u6279\u91cf\u751f\u6210\u7cfb\u7edf\u5feb\u7167\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        catch (JTableException e) {
            logger.error("\u6279\u91cf\u9000\u56de\u751f\u6210\u6570\u636e\u7248\u672c\u51fa\u73b0\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    private boolean isOpenReturnVersion(String taskKey, String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (null == formScheme) {
            throw new NotFoundFormSchemeException(new String[]{formSchemeKey + "\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230"});
        }
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if (null != flowsSetting) {
            return flowsSetting.isReturnVersion();
        }
        try {
            TaskDefine queryTaskDefine = this.runtimeView.queryTaskDefine(taskKey);
            flowsSetting = queryTaskDefine.getFlowsSetting();
            if (null != flowsSetting) {
                return flowsSetting.isReturnVersion();
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return false;
    }

    private String taskId(String formSchemeKey, String actionId, Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo, String taskId) {
        String taskCode = null;
        try {
            if (this.workflow.isDefaultWorkflow(formSchemeKey)) {
                if ("act_submit".equals(actionId)) {
                    taskCode = "tsk_submit";
                } else if ("act_upload".equals(actionId) || "act_return".equals(actionId)) {
                    taskCode = "tsk_upload";
                } else if ("act_confirm".equals(actionId) || "act_reject".equals(actionId)) {
                    if (taskId != null && taskId.equals("tsk_audit_after_confirm")) {
                        return "tsk_audit_after_confirm";
                    }
                    taskCode = "tsk_audit";
                } else if ("act_cancel_confirm".equals(actionId)) {
                    taskCode = "tsk_audit_after_confirm";
                }
            } else {
                for (DimensionValueSet key : batchWorkflowDataInfo.keySet()) {
                    Map info = batchWorkflowDataInfo.get(key);
                    block3: for (String formKey : info.keySet()) {
                        List list = (List)info.get(formKey);
                        if (list.isEmpty()) continue;
                        for (WorkflowDataInfo workflowDataInfo : list) {
                            if (workflowDataInfo.getTaskCode() == null) continue;
                            taskCode = workflowDataInfo.getTaskId();
                            continue block3;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return taskCode;
    }

    private String taskCode(String formSchemeKey, String actionId, Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo, String taskId) {
        String taskCode = null;
        try {
            if (this.workflow.isDefaultWorkflow(formSchemeKey)) {
                if ("act_submit".equals(actionId)) {
                    taskCode = "tsk_submit";
                } else if ("act_upload".equals(actionId) || "act_return".equals(actionId)) {
                    taskCode = "tsk_upload";
                } else if ("act_confirm".equals(actionId) || "act_reject".equals(actionId)) {
                    if (taskId != null && taskId.equals("tsk_audit_after_confirm")) {
                        return "tsk_audit_after_confirm";
                    }
                    taskCode = "tsk_audit";
                } else if ("act_cancel_confirm".equals(actionId)) {
                    taskCode = "tsk_audit_after_confirm";
                }
            } else {
                for (DimensionValueSet key : batchWorkflowDataInfo.keySet()) {
                    Map info = batchWorkflowDataInfo.get(key);
                    block3: for (String formKey : info.keySet()) {
                        List list = (List)info.get(formKey);
                        if (list.isEmpty()) continue;
                        for (WorkflowDataInfo workflowDataInfo : list) {
                            Set collect = workflowDataInfo.getActions().stream().map(e -> e.getCode()).collect(Collectors.toSet());
                            if (workflowDataInfo.getTaskCode() == null || !collect.contains(actionId)) continue;
                            taskCode = workflowDataInfo.getTaskCode();
                            continue block3;
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return taskCode;
    }

    private StringBuilder buildMessing(JtableContext jtableContext, List<String> formKeys, List<String> fromGroupKeys, String title, WorkFlowType workFlowType, String actionCode) {
        try {
            LogParam logParam = new LogParam();
            StringBuilder valueBuilder = new StringBuilder();
            if (null != jtableContext) {
                FormDefine formDefine;
                String taskKey = jtableContext.getTaskKey();
                if (!StringUtils.isEmpty((String)taskKey)) {
                    String[] splits = taskKey.split(";");
                    String taskTitle = "";
                    for (String oneTaskKey : splits) {
                        TaskDefine queryTaskDefine = null;
                        try {
                            queryTaskDefine = this.runtimeView.queryTaskDefine(oneTaskKey);
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (null == queryTaskDefine) continue;
                        taskTitle = "".equals(taskTitle) ? queryTaskDefine.getTitle() : taskTitle + ";" + queryTaskDefine.getTitle();
                    }
                    taskKey = taskTitle;
                } else {
                    taskKey = "\u6240\u6709\u4efb\u52a1";
                }
                valueBuilder.append("\u4efb\u52a1\u540d\u79f0:").append(taskKey).append(", ");
                String formSchemeKey = jtableContext.getFormSchemeKey();
                if (!StringUtils.isEmpty((String)formSchemeKey)) {
                    String[] splits = formSchemeKey.split(";");
                    String formSchemeKeyTitle = "";
                    for (String oneFormSchemeKey : splits) {
                        FormSchemeDefine formSchemeDefine = null;
                        try {
                            formSchemeDefine = this.runtimeView.getFormScheme(oneFormSchemeKey);
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (null == formSchemeDefine) continue;
                        formSchemeKeyTitle = "".equals(formSchemeKeyTitle) ? formSchemeDefine.getTitle() : formSchemeKeyTitle + ";" + formSchemeDefine.getTitle();
                    }
                    formSchemeKey = formSchemeKeyTitle;
                } else {
                    formSchemeKey = "\u6240\u6709\u62a5\u8868\u65b9\u6848";
                }
                valueBuilder.append("\u62a5\u8868\u65b9\u6848\u540d\u79f0:").append(formSchemeKey).append(", ");
                List<String> filterStr = Arrays.asList(FILTER_STR);
                if (!filterStr.contains(title)) {
                    String formulaSchemeKey = jtableContext.getFormulaSchemeKey();
                    if (!StringUtils.isEmpty((String)formulaSchemeKey)) {
                        String[] splits = formulaSchemeKey.split(";");
                        String formulaSchemeTitle = "";
                        for (String oneFormSchemeKey : splits) {
                            FormulaSchemeData formulaSchemeData = null;
                            try {
                                formulaSchemeData = this.jtableParamService.getFormulaScheme(oneFormSchemeKey);
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                            if (null == formulaSchemeData) continue;
                            formulaSchemeTitle = "".equals(formulaSchemeTitle) ? formulaSchemeData.getTitle() : formulaSchemeTitle + ";" + formulaSchemeData.getTitle();
                        }
                        formulaSchemeKey = formulaSchemeTitle;
                    } else {
                        formulaSchemeKey = "\u6240\u6709\u516c\u5f0f\u65b9\u6848";
                    }
                    valueBuilder.append("\u516c\u5f0f\u65b9\u6848\u540d\u79f0:").append(formulaSchemeKey).append(", ");
                }
                EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
                EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
                String unilType = targetEntityInfo.getDimensionName();
                String dateType = periodEntityInfo.getDimensionName();
                HashMap<String, String> entityViewsMap = new HashMap<String, String>();
                for (EntityViewData entityViewData : this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey())) {
                    entityViewsMap.put(entityViewData.getDimensionName(), entityViewData.getKey());
                }
                Map dimensionSet = jtableContext.getDimensionSet();
                if (dimensionSet == null) {
                    return valueBuilder;
                }
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
                for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                    String value;
                    if (((String)entry.getKey()).equals(dateType)) {
                        String periodTitle = periodProvider.getPeriodTitle(((DimensionValue)entry.getValue()).getValue());
                        valueBuilder.append("\u65f6\u671f:").append((String)entry.getKey()).append("|").append(periodTitle).append(", ");
                        continue;
                    }
                    if (((String)entry.getKey()).equals(unilType)) {
                        value = ((DimensionValue)entry.getValue()).getValue();
                        String unitTitle = null;
                        unitTitle = logParam != null && logParam.getKeyInfo() != null ? "\u76ee\u6807\u5355\u4f4d:" : "\u5355\u4f4d:";
                        if (StringUtils.isEmpty((String)value)) {
                            valueBuilder.append(unitTitle).append(entry.getKey()).append("|").append("\u6240\u6709\u5355\u4f4d").append(", ");
                            this.appendUnitInfo(jtableContext, dimensionSet, valueBuilder);
                            continue;
                        }
                        valueBuilder.append(unitTitle).append(entry.getKey()).append(", ");
                        this.appendSelectUnitInfo(jtableContext, dimensionSet, valueBuilder, value);
                        continue;
                    }
                    value = ((DimensionValue)entry.getValue()).getValue();
                    if (StringUtils.isEmpty((String)value)) {
                        valueBuilder.append("\u7ef4\u5ea6\u540d:").append((String)entry.getKey()).append("|").append("\u6240\u6709\u503c").append(",");
                        continue;
                    }
                    this.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, "\u7ef4\u5ea6");
                }
                if (title != null && (title.equals("\u5bfc\u5165\u6570\u636e") || title.equals("\u6267\u884c\u4e0a\u62a5") || title.equals("\u6267\u884c\u6279\u91cf\u4e0a\u62a5"))) {
                    return new StringBuilder(valueBuilder.substring(0, valueBuilder.lastIndexOf(",")));
                }
                StringBuilder rang = new StringBuilder();
                if (logParam != null && "\u6c47\u603b".equals(logParam.getKeyInfo())) {
                    rang.setLength(0);
                    rang.append("\u6c47\u603b\u8303\u56f4:");
                    rang.append(logParam.getOrherMsg().get("recursive")).append(";");
                }
                if (logParam != null && "\u9009\u62e9\u6c47\u603b".equals(logParam.getKeyInfo())) {
                    rang.setLength(0);
                    rang.append("\u6c47\u603b\u8303\u56f4:");
                    String string = (String)logParam.getOrherMsg().get("sourceKeys");
                    this.appendEntity(jtableContext, rang, entityViewsMap, unilType, string);
                    rang.append(";");
                }
                valueBuilder.append((CharSequence)rang);
                if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                    valueBuilder.append("\n\u62a5\u8868:");
                    if (formKeys != null && formKeys.size() > 0) {
                        boolean bl = false;
                        for (String oneFormKey : formKeys) {
                            boolean bl2;
                            formDefine = null;
                            try {
                                formDefine = this.runtimeView.queryFormById(oneFormKey);
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                            if (null == formDefine) continue;
                            if (bl2) {
                                valueBuilder.append(",");
                            }
                            valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                            bl2 = true;
                        }
                    } else {
                        valueBuilder.append("\u6240\u6709\u62a5\u8868");
                    }
                }
                if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                    valueBuilder.append("\u62a5\u8868\u5206\u7ec4");
                    if (fromGroupKeys != null && fromGroupKeys.size() > 0) {
                        boolean bl = false;
                        for (String formGroupKey : fromGroupKeys) {
                            boolean bl3;
                            FormGroupDefine formGroup = null;
                            try {
                                formGroup = this.runtimeView.queryFormGroup(formGroupKey);
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                            if (null == formGroup) continue;
                            if (bl3) {
                                valueBuilder.append(",");
                            }
                            valueBuilder.append(formGroup.getCode()).append("|").append(formGroup.getTitle());
                            bl3 = true;
                        }
                    } else {
                        valueBuilder.append("\u6240\u6709\u62a5\u8868\u5206\u7ec4");
                    }
                }
                if (WorkFlowType.ENTITY.equals((Object)workFlowType) && ("single_form_reject".equals(actionCode) || "single_form_upload".equals(actionCode))) {
                    if (formKeys != null && formKeys.size() > 0) {
                        boolean bl = false;
                        for (String oneFormKey : formKeys) {
                            boolean bl4;
                            formDefine = null;
                            try {
                                formDefine = this.runtimeView.queryFormById(oneFormKey);
                            }
                            catch (Exception e) {
                                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            }
                            if (null == formDefine) continue;
                            if (bl4) {
                                valueBuilder.append(",");
                            }
                            valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                            bl4 = true;
                        }
                    } else {
                        valueBuilder.append("\u6240\u6709\u62a5\u8868");
                    }
                }
                if (logParam != null && logParam.getKeyInfo() != null && logParam.getKeyInfo().contains("\u8282\u70b9\u68c0\u67e5")) {
                    valueBuilder.append(", \u8bef\u5dee\u8303\u56f4:").append(logParam.getOrherMsg().get("errorRange")).append("");
                }
            } else {
                valueBuilder.append("\u672a\u8bb0\u5f55\u73af\u5883\u4fe1\u606f").append("");
            }
            return valueBuilder;
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u64cd\u4f5c\u8bb0\u5f55\u65e5\u5fd7\u4fe1\u606f\u62a5\u9519");
            return null;
        }
    }

    private void appendEntity(JtableContext jtableContext, StringBuilder valueBuilder, Map<String, String> entityViewsMap, Map.Entry<String, DimensionValue> entry, String title) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(entry.getKey());
        Map entitys = null;
        if (null != entityViewKey && !"".equals(entityViewKey)) {
            entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
            String[] split = entry.getValue().getValue().split(";");
            entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
            entityQueryByKeysInfo.setContext(jtableContext);
            EntityByKeysReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
            entitys = entityByKeyReturnInfo.getEntitys();
        }
        valueBuilder.append(title);
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    valueBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    valueBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        } else {
            valueBuilder.append(title).append("\u503c:").append(entry.getValue().getValue());
        }
        valueBuilder.append(", ");
    }

    private void appendEntity(JtableContext jtableContext, StringBuilder stringBuilder, Map<String, String> entityViewsMap, String unilType, String units) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(unilType);
        entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
        String[] split = units.split(";");
        entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
        entityQueryByKeysInfo.setContext(jtableContext);
        EntityByKeysReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
        Map entitys = entityByKeyReturnInfo.getEntitys();
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    stringBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    stringBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        }
    }

    private String batchOpttime(String startTime) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n\u5f00\u59cb\u65f6\u95f4\uff1a" + startTime);
        Date endOptTime = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = sdformat.format(endOptTime);
        sb.append("; \u7ed3\u675f\u65f6\u95f4\uff1a" + endTime);
        return sb.toString();
    }

    private void sendOptEvent(BatchExecuteTaskParam param, String userId, BatchStepByStepResult result, WorkFlowType workflowType) {
        try {
            Map operateUnitAndGroups;
            ArrayList<String> units;
            BatchOptEvent batchOptEvent = new BatchOptEvent();
            batchOptEvent.setTaskKey(param.getContext().getTaskKey());
            batchOptEvent.setFromSchemeKey(param.getContext().getFormSchemeKey());
            Map dimensionSet = param.getContext().getDimensionSet();
            DimensionValue dimensionValue = (DimensionValue)dimensionSet.get("DATATIME");
            String period = dimensionValue.getValue();
            batchOptEvent.setPeriod(period);
            if (WorkFlowType.ENTITY.equals((Object)workflowType)) {
                Set operateUnits = result.getOperateUnits();
                if (operateUnits != null && operateUnits.size() > 0) {
                    batchOptEvent.setUnits(new ArrayList<String>(operateUnits));
                }
            } else if (WorkFlowType.FORM.equals((Object)workflowType)) {
                Map operateUnitAndForms = result.getOperateUnitAndForms();
                if (operateUnitAndForms != null && operateUnitAndForms.size() > 0) {
                    units = new ArrayList<String>();
                    ArrayList<String> forms = new ArrayList<String>();
                    for (Map.Entry formKeys : operateUnitAndForms.entrySet()) {
                        units.add((String)formKeys.getKey());
                        forms.addAll((Collection)formKeys.getValue());
                    }
                    batchOptEvent.setUnits(units);
                    batchOptEvent.setFormKeys(forms);
                }
            } else if (WorkFlowType.GROUP.equals((Object)workflowType) && (operateUnitAndGroups = result.getOperateUnitAndGroups()) != null && operateUnitAndGroups.size() > 0) {
                units = new ArrayList();
                ArrayList<String> groups = new ArrayList<String>();
                for (Map.Entry groupKeys : operateUnitAndGroups.entrySet()) {
                    units.add((String)groupKeys.getKey());
                    groups.addAll((Collection)groupKeys.getValue());
                }
                batchOptEvent.setUnits(units);
                batchOptEvent.setFromGroupKeys(groups);
            }
            batchOptEvent.setOperator(userId);
            batchOptEvent.setActionCode(param.getActionId());
            batchOptEvent.setContent(param.getComment());
            if (dimensionSet.containsKey("ADJUST")) {
                DimensionValue dimension = (DimensionValue)dimensionSet.get("ADJUST");
                String adjust = dimension.getValue();
                batchOptEvent.setAdjustPeriod(adjust);
            }
            this.applicationEventPublisher.publishEvent(batchOptEvent);
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u64cd\u4f5c\u4e8b\u4ef6\u672a\u53d1\u9001\u6210\u529f");
        }
    }

    public void appendUnitInfo(JtableContext jtableContext, Map<String, DimensionValue> dimensionSet, StringBuilder valueBuilder) {
        DimensionValue dimensionValue = dimensionSet.get("DATATIME");
        String period = dimensionValue.getValue();
        List<IEntityRow> entityRow = this.getEntityRow(jtableContext, period, dimensionSet);
        if (entityRow != null && entityRow.size() > 0) {
            boolean addFlag = true;
            for (IEntityRow iEntityRow : entityRow) {
                String code = iEntityRow.getCode();
                String title = iEntityRow.getTitle();
                if (addFlag) {
                    valueBuilder.append(code).append("|").append(title);
                } else {
                    valueBuilder.append(",").append(code).append("|").append(title);
                }
                addFlag = false;
            }
        }
    }

    public void appendSelectUnitInfo(JtableContext jtableContext, Map<String, DimensionValue> dimensionSet, StringBuilder valueBuilder, String value) {
        DimensionValue dimensionValue = dimensionSet.get("DATATIME");
        String period = dimensionValue.getValue();
        List<IEntityRow> entityRow = this.getEntityRow(jtableContext, period, dimensionSet);
        List<String> values = Arrays.asList(value.split(";"));
        if (entityRow != null && entityRow.size() > 0) {
            boolean addFlag = true;
            for (IEntityRow iEntityRow : entityRow) {
                String code = iEntityRow.getCode();
                String title = iEntityRow.getTitle();
                if (!value.contains(iEntityRow.getEntityKeyData())) continue;
                if (addFlag) {
                    valueBuilder.append(code).append("|").append(title);
                } else {
                    valueBuilder.append(",").append(code).append("|").append(title);
                }
                addFlag = false;
            }
        }
    }

    public List<IEntityRow> getEntityRow(JtableContext jtableContext, String period, Map<String, DimensionValue> dimensionSet) {
        FormSchemeDefine formScheme;
        IDataEntity entityDataList;
        String formSchemeKey = jtableContext.getFormSchemeKey();
        EntityViewDefine entityViewDefine = this.runtimeView.getViewByFormSchemeKey(formSchemeKey);
        String mainDimName = this.getMainDimName(entityViewDefine);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        DimensionValue dimensionValue = dimensionSet.get(mainDimName);
        String value = dimensionValue.getValue();
        if (com.jiuqi.util.StringUtils.isNotEmpty((String)value)) {
            String[] strArray = value.split(";");
            ArrayList arrayList = new ArrayList(strArray.length);
            Collections.addAll(arrayList, strArray);
            dimensionValueSet.setValue(mainDimName, arrayList);
        }
        if ((entityDataList = this.getEntityDataList(jtableContext, dimensionSet, formScheme = this.runtimeView.getFormScheme(formSchemeKey))) != null) {
            IDataEntityRow allRow = entityDataList.getAllRow();
            DataEntityType type = entityDataList.type();
            if (DataEntityType.DataEntity.equals((Object)type)) {
                List rowList = allRow.getRowList();
                return rowList;
            }
        }
        return null;
    }

    public String getMainDimName(EntityViewDefine entityViewDefine) {
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((com.jiuqi.np.dataengine.executors.ExecutorContext)context);
        return dataAssist.getDimensionName(entityViewDefine);
    }

    private void setReturnInfo(ExternalBatchUploadResult uploadResult, List<BatchWorkFlowInfo> canUploadList, String mainDim, UploadReturnInfo uploadReturnInfo, BatchUploadRetrunInfo batchUploadReturnInfo) {
        List noUploadResultItem = uploadResult.getNoUploadResultItem();
        if (noUploadResultItem != null) {
            uploadReturnInfo.setErrorEntityNum(noUploadResultItem.size());
            if (canUploadList != null) {
                List noUpload = noUploadResultItem.stream().map(e -> e.getKey()).collect(Collectors.toList());
                Iterator<BatchWorkFlowInfo> iterator = canUploadList.iterator();
                while (iterator.hasNext()) {
                    BatchWorkFlowInfo workFlowInfo = iterator.next();
                    Map<String, DimensionValue> dimensionValue = workFlowInfo.getDimensionValue();
                    DimensionValue dimension = dimensionValue.get(mainDim);
                    String unitId = dimension.getValue();
                    if (!noUpload.contains(unitId)) continue;
                    iterator.remove();
                }
            }
        }
        batchUploadReturnInfo.setExternalBatchUploadResult(uploadResult);
    }

    private ExternalBatchUploadResult canUpload(BatchExecuteTaskParam param, List<BatchWorkFlowInfo> allDimensionList) {
        try {
            if (this.batchWorkflowHandlers != null) {
                Map<String, DimensionValue> mergeDimension1 = this.getMergeDimension(allDimensionList);
                JtableContext context = param.getContext();
                context.setDimensionSet(mergeDimension1);
                param.setContext(context);
                return this.batchWorkflowHandlers.batchUpload(param);
            }
        }
        catch (Exception e) {
            logger.error("\u653f\u5e9c\u6269\u5c55\u63a5\u53e3\u6267\u884c\u5f02\u5e38");
        }
        return null;
    }

    private List<BatchWorkFlowInfo> getUploadUnits(List<BatchWorkFlowInfo> allDimensionList, Map<String, List<String>> unCheckNodeFormGroupMap, Map<String, List<String>> unPassFormGroupMap, Map<String, List<String>> haveCheckFormResult, Map<String, List<String>> haveNodeCheckFormResultMap, Map<String, String> haveCheckResultMap, Map<String, String> haveNodeCheckResultMap, List<String> queryEndFillUnits, List<String> timeSetting, UploadReturnInfo uploadReturnInfo, String mainDim, WorkFlowType startType, List<String> multCheckFailUnits) {
        ArrayList<BatchWorkFlowInfo> canUploadList = new ArrayList<BatchWorkFlowInfo>();
        for (BatchWorkFlowInfo workFlowInfo : allDimensionList) {
            int i;
            boolean isPassNodeCheck;
            boolean isPassCheck;
            String unitKey = workFlowInfo.getDimensionValue().get(mainDim).getValue();
            if (WorkFlowType.GROUP.equals((Object)startType)) {
                isPassCheck = false;
                isPassNodeCheck = false;
                if (unPassFormGroupMap != null && unPassFormGroupMap.size() > 0) {
                    if (!unPassFormGroupMap.containsKey(unitKey)) {
                        isPassCheck = true;
                    } else {
                        if (unPassFormGroupMap.get(unitKey) != null && unPassFormGroupMap.get(unitKey).contains(workFlowInfo.getFormGroupKey()) || unPassFormGroupMap.get(unitKey).contains("00000000-0000-0000-0000-000000000000")) {
                            uploadReturnInfo.addErrorFormGroupNums();
                            continue;
                        }
                        isPassCheck = true;
                    }
                } else {
                    isPassCheck = true;
                }
                if (unCheckNodeFormGroupMap != null && unCheckNodeFormGroupMap.size() > 0) {
                    if (!unCheckNodeFormGroupMap.containsKey(unitKey)) {
                        isPassNodeCheck = true;
                    } else if (unCheckNodeFormGroupMap.get(unitKey) != null) {
                        String formGroupKey = workFlowInfo.getFormGroupKey();
                        String[] split = formGroupKey.split(";");
                        if (split != null && split.length > 0) {
                            for (i = 0; i < split.length; ++i) {
                                if (unCheckNodeFormGroupMap.get(unitKey) != null && unCheckNodeFormGroupMap.get(unitKey).contains(split[i]) || unPassFormGroupMap.get(unitKey) != null && unPassFormGroupMap.get(unitKey).contains("00000000-0000-0000-0000-000000000000")) {
                                    uploadReturnInfo.addErrorFormGroupNums();
                                    continue;
                                }
                                isPassNodeCheck = true;
                            }
                        } else {
                            isPassNodeCheck = true;
                        }
                    } else {
                        isPassNodeCheck = true;
                    }
                } else {
                    isPassNodeCheck = true;
                }
                if ((!isPassNodeCheck || !isPassCheck) && (unCheckNodeFormGroupMap != null || unPassFormGroupMap != null) || queryEndFillUnits != null && queryEndFillUnits.size() > 0 && queryEndFillUnits.contains(unitKey) || timeSetting != null && timeSetting.size() > 0 && timeSetting.contains(unitKey)) continue;
                canUploadList.add(workFlowInfo);
                continue;
            }
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                if (haveNodeCheckResultMap != null && !haveNodeCheckResultMap.containsKey(unitKey) && haveCheckResultMap != null && !haveCheckResultMap.containsKey(unitKey)) {
                    if (queryEndFillUnits != null && queryEndFillUnits.size() > 0 && queryEndFillUnits.contains(unitKey) || timeSetting != null && timeSetting.size() > 0 && timeSetting.contains(unitKey)) continue;
                    canUploadList.add(workFlowInfo);
                    continue;
                }
                uploadReturnInfo.addErrorNum();
                continue;
            }
            if (!WorkFlowType.FORM.equals((Object)startType)) continue;
            isPassCheck = false;
            isPassNodeCheck = false;
            if (haveCheckFormResult != null && haveCheckFormResult.size() > 0) {
                if (!haveCheckFormResult.containsKey(unitKey)) {
                    isPassCheck = true;
                } else {
                    if (haveCheckFormResult.get(unitKey) != null && haveCheckFormResult.get(unitKey).contains(workFlowInfo.getFormKey()) || haveCheckFormResult.get(unitKey).contains("00000000-0000-0000-0000-000000000000")) {
                        uploadReturnInfo.addErrorFormNums();
                        continue;
                    }
                    isPassCheck = true;
                }
            } else {
                isPassCheck = true;
            }
            if (haveNodeCheckFormResultMap != null && haveNodeCheckFormResultMap.size() > 0) {
                if (!haveNodeCheckFormResultMap.containsKey(unitKey)) {
                    isPassNodeCheck = true;
                } else if (haveNodeCheckFormResultMap.get(unitKey) != null) {
                    String formKey = workFlowInfo.getFormKey();
                    String[] formKeyArr = formKey.split(";");
                    if (formKeyArr != null && formKeyArr.length > 0) {
                        for (i = 0; i < formKeyArr.length; ++i) {
                            if (haveNodeCheckFormResultMap.get(unitKey).contains(formKeyArr[i])) {
                                uploadReturnInfo.addErrorFormNums();
                                continue;
                            }
                            isPassNodeCheck = true;
                        }
                    } else {
                        isPassNodeCheck = true;
                    }
                } else {
                    isPassNodeCheck = true;
                }
            } else {
                isPassNodeCheck = true;
            }
            if ((!isPassCheck || !isPassNodeCheck) && (haveNodeCheckFormResultMap != null || haveCheckFormResult != null) || queryEndFillUnits != null && queryEndFillUnits.size() > 0 && queryEndFillUnits.contains(unitKey) || timeSetting != null && timeSetting.size() > 0 && timeSetting.contains(unitKey)) continue;
            canUploadList.add(workFlowInfo);
        }
        return canUploadList;
    }

    private void setUploadBeforeUnPassNum(UploadBeforeCheck uploadBeforeCheck, UploadBeforeNodeCheck uploadBeforeNodeCheck, Map<String, List<String>> haveCheckFormResult, Map<String, List<String>> unPassFormGroupMap, Map<String, List<String>> haveNodeCheckFormResultMap, Map<String, List<String>> unCheckNodeFormGroupMap, Map<String, String> haveCheckResultMap, Map<String, String> haveNodeCheckResultMap) {
        int checkUnPassNum = 0;
        int nodeCheckUnPassNum = 0;
        int unPassFormGroupNum = 0;
        int unPassNodeCheckGroupNum = 0;
        if (haveCheckFormResult.size() > 0) {
            for (String key : haveCheckFormResult.keySet()) {
                checkUnPassNum += haveCheckFormResult.get(key).size();
            }
        }
        if (unPassFormGroupMap.size() > 0) {
            for (String key : unPassFormGroupMap.keySet()) {
                unPassFormGroupNum += unPassFormGroupMap.get(key).size();
            }
        }
        if (haveNodeCheckFormResultMap != null && haveNodeCheckFormResultMap.size() > 0) {
            for (String key : haveNodeCheckFormResultMap.keySet()) {
                nodeCheckUnPassNum += haveNodeCheckFormResultMap.get(key).size();
            }
        }
        if (unCheckNodeFormGroupMap != null && unCheckNodeFormGroupMap.size() > 0) {
            for (String key : unCheckNodeFormGroupMap.keySet()) {
                unPassNodeCheckGroupNum += unCheckNodeFormGroupMap.get(key).size();
            }
        }
        uploadBeforeCheck.setUnPassEntityNum(haveCheckResultMap.size());
        uploadBeforeCheck.setUnPassFormsNum(checkUnPassNum);
        uploadBeforeCheck.setUnPassFormGroupNum(unPassFormGroupNum);
        uploadBeforeNodeCheck.setUnPassEntityNum(haveNodeCheckResultMap != null ? haveNodeCheckResultMap.size() : 0);
        uploadBeforeNodeCheck.setUnPassFormNum(nodeCheckUnPassNum);
        uploadBeforeNodeCheck.setUnPassFormGroupNum(unPassNodeCheckGroupNum);
    }

    private BatchStepByStepResult batchSingleOpt(BatchExecuteTaskParam param, List<DimensionValueSet> stepUnits, WorkFlowType startType, String mainDim, AsyncTaskMonitor asyncTaskMonitor, LogInfo logInfo, StringBuilder actionLogInfo, List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition) {
        BatchStepByStepResult result = new BatchStepByStepResult();
        try {
            HashMap operateUnitAndForms = new HashMap();
            CompleteMsg completeMsg = new CompleteMsg();
            asyncTaskMonitor.progressAndMessage(0.1, "");
            ArrayList formKeys = new ArrayList();
            for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : formKeysCondition) {
                formKeys.addAll(accessFormInfo.getFormKeys());
            }
            String formSchemeKey = param.getContext().getFormSchemeKey();
            String actionId = param.getActionId();
            DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionValueSet(stepUnits);
            asyncTaskMonitor.progressAndMessage(0.3, "");
            CompleteMsg singleFormCompleteMsg = this.singleFormRejectService.execute(new HashSet(formKeys), dimensionValueSet, formSchemeKey, actionId);
            asyncTaskMonitor.progressAndMessage(0.8, "");
            if (singleFormCompleteMsg != null && singleFormCompleteMsg.isSucceed()) {
                completeMsg.setSucceed(true);
                if ("single_form_reject".equals(actionId)) {
                    completeMsg.setMsg("\u6279\u91cf\u5355\u8868\u9000\u56de\u5b8c\u6210");
                } else if ("single_form_upload".equals(actionId)) {
                    completeMsg.setMsg("\u6279\u91cf\u91cd\u65b0\u63d0\u4ea4\u5b8c\u6210");
                }
                HashSet<String> operateUnits = new HashSet<String>();
                Object value = dimensionValueSet.getValue(mainDim);
                if (value instanceof List) {
                    operateUnits.addAll((List)value);
                } else {
                    operateUnits.add(value.toString());
                }
                LinkedHashSet forms = new LinkedHashSet(formKeys);
                for (DimensionValueSet dimension : stepUnits) {
                    String unitKey = dimension.getValue(mainDim).toString();
                    operateUnitAndForms.put(unitKey, forms);
                }
                result.setCompleteMsg(completeMsg);
                result.setOperateUnitAndForms(operateUnitAndForms);
            } else {
                completeMsg.setSucceed(false);
                completeMsg.setMsg(singleFormCompleteMsg.getMsg());
                result.setCompleteMsg(completeMsg);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private void insertBatchCheckResult(List<BatchWorkFlowInfo> allBatchWorkFlowInfo, BatchExecuteTaskParam param, Map<String, List<String>> checkFormula, WorkflowConfig workflowConfig, String asyncTaskId, WorkflowAction workflowAction) {
        try {
            JtableContext context = param.getContext();
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            String dwDimName = dwEntity.getDimensionName();
            List formKeys = checkFormula.keySet().stream().collect(Collectors.toList());
            String checkFormulaSchemeKey = workflowConfig.getCheckFormulaSchemeKey();
            if (StringUtils.isEmpty((String)checkFormulaSchemeKey)) {
                checkFormulaSchemeKey = param.getContext().getFormulaSchemeKey();
            }
            String[] formulaSchemeKeys = checkFormulaSchemeKey.split(";");
            CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
            checkResultQueryParam.setBatchId(asyncTaskId);
            checkResultQueryParam.setVariableMap(context.getVariableMap());
            checkResultQueryParam.setMode(Mode.FORM);
            checkResultQueryParam.setRangeKeys(formKeys);
            checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(formulaSchemeKeys));
            List<String> unitKeys = new ArrayList<String>();
            if (allBatchWorkFlowInfo != null && allBatchWorkFlowInfo.size() > 0) {
                List list = allBatchWorkFlowInfo.stream().map(e -> e.getDimensionValue()).collect(Collectors.toList());
                Map mergeDimensionSetMap = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionSetMap(list);
                DimensionValueSet dimension = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)mergeDimensionSetMap);
                Object value = dimension.getValue(dwDimName);
                if (value instanceof String) {
                    unitKeys.add(value.toString());
                } else {
                    unitKeys = (List)value;
                }
            }
            ActionParam actionParam = workflowAction.getActionParam();
            List ignoreErrorStatus = actionParam.getIgnoreErrorStatus();
            List needCommentErrorStatus = actionParam.getNeedCommentErrorStatus();
            Map<Boolean, Set<String>> fliterUnitKeys = this.checkResultParamForReportUtil.enableCustomConfigs(dwEntity, unitKeys, param.getContext().getTaskKey(), param.getContext().getFormSchemeKey());
            if (fliterUnitKeys != null && fliterUnitKeys.size() > 0) {
                Set<String> customUnitKeys = fliterUnitKeys.get(Boolean.TRUE);
                Map<Integer, Boolean> customCheckTypes = this.getCustomCheckTypes(context.getTaskKey());
                CustomQueryCondition customCondition = new CustomQueryCondition();
                if (customUnitKeys != null && customCheckTypes != null) {
                    customCondition.setCheckTypes(customCheckTypes);
                    DimensionCollection dimensionCollection = this.buildDimesion(context, new ArrayList<String>(customUnitKeys), dwDimName, workflowAction);
                    customCondition.setDimensionCollection(dimensionCollection);
                    checkResultQueryParam.setCustomCondition(customCondition);
                }
                Set<String> normalUnitKeys = fliterUnitKeys.get(Boolean.FALSE);
                Map<Integer, Boolean> normalCheckTypes = this.getNormalCheckTypes(context.getFormSchemeKey(), needCommentErrorStatus, ignoreErrorStatus);
                DimensionCollection dimensionCollection = this.buildDimesion(context, new ArrayList<String>(normalUnitKeys), dwDimName, workflowAction);
                if (allBatchWorkFlowInfo != null) {
                    checkResultQueryParam.setFilterCondition(allBatchWorkFlowInfo.get(0).getCheckFilter());
                }
                checkResultQueryParam.setDimensionCollection(dimensionCollection);
                checkResultQueryParam.setCheckTypes(normalCheckTypes);
                this.checkSchemeRecordService.saveCheckSchemeRecord(checkResultQueryParam);
            } else {
                Map<Integer, Boolean> normalCheckTypes = this.getNormalCheckTypes(context.getFormSchemeKey(), needCommentErrorStatus, ignoreErrorStatus);
                DimensionCollection dimensionCollection = this.buildDimesion(context, unitKeys, dwDimName, workflowAction);
                if (allBatchWorkFlowInfo != null) {
                    checkResultQueryParam.setFilterCondition(allBatchWorkFlowInfo.get(0).getCheckFilter());
                }
                checkResultQueryParam.setDimensionCollection(dimensionCollection);
                checkResultQueryParam.setCheckTypes(normalCheckTypes);
                this.checkSchemeRecordService.saveCheckSchemeRecord(checkResultQueryParam);
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
    }

    private DimensionCollection buildDimesion(JtableContext context, List<String> unitKeys, String dimName, WorkflowAction workflowAction) {
        Map dimensionSet2 = context.getDimensionSet();
        DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet2);
        dimensionValueSet.setValue(dimName, unitKeys);
        Map dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        boolean judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(context.getFormSchemeKey(), dimensionSet);
        if (!judgeCurrentcyType) {
            ActionParam actionParam = workflowAction.getActionParam();
            int checkCurrencyType = actionParam.getCheckCurrencyType();
            String checkCurrencyValue = actionParam.getCheckCurrencyValue();
            this.uploadCheckFliterUtil.setCheckConditions(context.getTaskKey(), dimensionSet, checkCurrencyType, checkCurrencyValue);
        }
        DimensionValueSet dimension = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimension, context.getFormSchemeKey());
        return dimensionCollection;
    }

    private Map<Integer, Boolean> getCustomCheckTypes(String taskKey) {
        Map<String, List<Integer>> customFormulaTypeMap = this.checkResultParamForReportUtil.getCustomFormulaTypeMap(taskKey);
        List<Integer> erroStatus = customFormulaTypeMap.get("AFFECT");
        List<Integer> needErrorComment = customFormulaTypeMap.get("NEEDEXPLAIN");
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        if (erroStatus != null && erroStatus.size() > 0) {
            for (Integer type : erroStatus) {
                checkTypes.put(type, null);
            }
            for (Integer type : needErrorComment) {
                checkTypes.put(type, false);
            }
        }
        return checkTypes;
    }

    private Map<Integer, Boolean> getNormalCheckTypes(String fromSchemeKey, List<Integer> needExplain, List<Integer> canIgnore) {
        Map<String, List<Integer>> flowFormulaTypeMap = this.checkResultParamForReportUtil.getFlowFormulaTypeMap(fromSchemeKey, needExplain, canIgnore);
        List<Integer> erroStatus = flowFormulaTypeMap.get("AFFECT");
        List<Integer> needErrorComment = flowFormulaTypeMap.get("NEEDEXPLAIN");
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        if (erroStatus != null && erroStatus.size() > 0) {
            for (Integer type : erroStatus) {
                checkTypes.put(type, null);
            }
            for (Integer type : needErrorComment) {
                checkTypes.put(type, false);
            }
        }
        return checkTypes;
    }

    private void forceUpload(BatchExecuteTaskParam param, Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo, String dwDim) {
        try {
            boolean forceCommit = param.isForceCommit();
            if (forceCommit && this.forceUpload != null) {
                Map<String, List<UploadVerifyType>> uploadVerifyTypes = this.forceUpload.isUpload(param);
                for (DimensionValueSet uploadDimension : batchWorkflowDataInfo.keySet()) {
                    String unitKey = uploadDimension.getValue(dwDim).toString();
                    List<UploadVerifyType> list = uploadVerifyTypes.get(unitKey);
                    if (list == null || list.size() <= 0) continue;
                    List types = list.stream().map(e -> e.getCode()).collect(Collectors.toList());
                    LinkedHashMap<String, List<WorkflowDataInfo>> linkedHashMap = batchWorkflowDataInfo.get(uploadDimension);
                    for (Map.Entry<String, List<WorkflowDataInfo>> datainfo : linkedHashMap.entrySet()) {
                        List<WorkflowDataInfo> workflowDataInfo = datainfo.getValue();
                        for (WorkflowDataInfo info : workflowDataInfo) {
                            List actions = info.getActions();
                            if (actions == null) continue;
                            for (WorkflowAction action : actions) {
                                if (action.getActionParam() == null) continue;
                                if (types.contains(UploadVerifyType.CALCUTE.getCode())) {
                                    action.getActionParam().setNeedAutoCalculate(true);
                                } else {
                                    action.getActionParam().setNeedAutoCalculate(false);
                                }
                                if (types.contains(UploadVerifyType.CHECK.getCode())) {
                                    action.getActionParam().setNeedAutoCheck(true);
                                } else {
                                    action.getActionParam().setNeedAutoCheck(false);
                                }
                                if (types.contains(UploadVerifyType.NODECHECK.getCode())) {
                                    action.getActionParam().setNodeCheck(true);
                                    continue;
                                }
                                action.getActionParam().setNodeCheck(false);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e2) {
            logger.error("\u653f\u5e9c\u6269\u5c55\u63a5\u53e3\u6269\u5c55\u5f02\u5e38\uff0c\u5f3a\u5236\u4e0a\u62a5");
        }
    }

    private String finalaccountsAudit(BatchExecuteTaskParam param, List<BatchWorkFlowInfo> multCheckList, AsyncTaskMonitor asyncTaskMonitor, BatchUploadRetrunInfo batchUploadReturnInfo, List<String> multCheckFailUnits, UploadReturnInfo uploadReturnInfo) {
        try {
            JtableContext context = param.getContext();
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(context.getTaskKey());
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            Map<String, DimensionValue> dimensionSet = this.getMergeDimension(multCheckList);
            BatchExecuteTaskParam batchExecuteTaskParam = new BatchExecuteTaskParam();
            BeanUtils.copyProperties((Object)((Object)batchExecuteTaskParam), (Object)((Object)param));
            batchExecuteTaskParam.getContext().setDimensionSet(dimensionSet);
            if (flowsSetting.getMulCheckBeforeCheck() && !param.isForceCommit() && this.multcheckService != null && ("act_upload".equals(param.getActionId()) || "cus_upload".equals(param.getActionId()) || "act_submit".equals(param.getActionId()) || "cus_submit".equals(param.getActionId()) || "single_form_upload".equals(param.getActionId()))) {
                if (this.mulCheckVersion.equals(VERSION)) {
                    return this.finalaccountsAuditService.bathComprehensiveAudit(param, asyncTaskMonitor);
                }
                if (this.mulCheckVersion.equals(VERSION2)) {
                    MultCheckResult multCheckResult = new MultCheckResult();
                    ObjectMapper mapper = new ObjectMapper();
                    String bathComprehensiveAudit = this.multcheckService.bathComprehensiveAudit(param, asyncTaskMonitor);
                    MultCheckReturnResult multCheckReturnResult = (MultCheckReturnResult)mapper.readValue(bathComprehensiveAudit, MultCheckReturnResult.class);
                    String errorMsg = multCheckReturnResult.getErrorMsg();
                    if (errorMsg != null) {
                        return errorMsg;
                    }
                    ArrayList<String> failTitles = new ArrayList<String>();
                    List<MultCheckLabel> failedList = multCheckReturnResult.getFailedList();
                    for (MultCheckLabel multCheckLabel : failedList) {
                        failTitles.add(multCheckLabel.getTitle());
                        multCheckFailUnits.add(multCheckLabel.getCode());
                        uploadReturnInfo.addErrorNum();
                    }
                    multCheckResult.setErrorNum(failTitles.size());
                    multCheckResult.setTitle(failTitles);
                    multCheckResult.setResultStr(bathComprehensiveAudit);
                    batchUploadReturnInfo.setMultCheckResult(multCheckResult);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\u6269\u5c55\u63a5\u53e3\u6267\u884c\u5f02\u5e38");
        }
        return null;
    }

    public static String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (StringUtils.isEmpty((String)language) || language.equals(CHINESE)) {
            return CHINESE;
        }
        return language;
    }

    public static boolean isChinese() {
        return BatchWorkflowServiceImpl.getLanguage().equals(CHINESE);
    }

    private void groupReadAuthData(List<DimensionCombination> dimensionCombinations, List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, WorkFlowType startType, UploadReturnInfo uploadReturnInfo, String formSchemeKey, String dwDim, BatchExecuteTaskParam param, WorkFlowType workflowType, String mainKey, String mainDim) {
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            uploadReturnInfo.setAllEntityNums(dimensionCombinations.size());
            if (param.getActionId().equals("single_form_reject") || param.getActionId().equals("single_form_upload")) {
                int formNums = 0;
                if (param.getFormKeys() != null && param.getFormKeys().size() > 0) {
                    formNums = param.getFormKeys().size();
                } else if (formKeysCondition != null && formKeysCondition.size() > 0) {
                    for (DimensionAccessFormInfo.AccessFormInfo formKeyCondition : formKeysCondition) {
                        List formKeys = formKeyCondition.getFormKeys();
                        formNums += formKeys.size();
                    }
                }
                uploadReturnInfo.setAllFormNums(formNums);
            }
        } else if (WorkFlowType.GROUP.equals((Object)startType) || WorkFlowType.FORM.equals((Object)startType)) {
            HashSet<String> unitKeys = new HashSet<String>();
            int num = 0;
            Map<String, List<String>> hasReadFormOrGroupKeys = this.queryLastOperateUtil.getDutyReadAuthDatas(param, formKeysCondition, workflowType, mainKey, mainDim);
            for (Map.Entry<String, List<String>> key : hasReadFormOrGroupKeys.entrySet()) {
                String unitKey = key.getKey();
                List<String> formOrGroupKeys = key.getValue();
                unitKeys.add(unitKey);
                num += formOrGroupKeys.size();
            }
            if (WorkFlowType.FORM.equals((Object)startType)) {
                uploadReturnInfo.setAllFormNums(num);
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                uploadReturnInfo.setAllFormGroupNums(num);
            }
            uploadReturnInfo.setAllEntityNums(unitKeys.size());
        }
    }

    private void groupNoAuthData(List<DimensionCombination> dimensionCombinations, List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, WorkFlowType startType, UploadReturnInfo uploadReturnInfo, String formSchemeKey, String dwDim, Map<String, Map<String, Boolean>> formOrGroupKeysByDuty) {
        if (!WorkFlowType.ENTITY.equals((Object)startType) && (WorkFlowType.GROUP.equals((Object)startType) || WorkFlowType.FORM.equals((Object)startType))) {
            HashMap noAuthUnitToReportKeysMap = new HashMap();
            for (DimensionAccessFormInfo.AccessFormInfo acessFormInfo : formKeysCondition) {
                List forms = acessFormInfo.getFormKeys();
                Map dimensionValue = acessFormInfo.getDimensions();
                DimensionValueSet dimension = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet((Map)dimensionValue);
                List dimensionSetList = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimension);
                for (DimensionValueSet dimensionValueSet : dimensionSetList) {
                    Set noAuthList;
                    String unitKey = dimensionValueSet.getValue(dwDim).toString();
                    HashSet<String> noAuthkeys = new HashSet<String>();
                    if (WorkFlowType.GROUP.equals((Object)startType)) {
                        for (String formKey : forms) {
                            List allFormGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(formKey);
                            for (FormGroupDefine formGroupDefine : allFormGroupsByFormKey) {
                                Boolean hasAuth;
                                Map<String, Boolean> map;
                                boolean canReadForm = this.definitionAuthorityProvider.canReadFormGroup(formGroupDefine.getKey());
                                if (!formGroupDefine.getFormSchemeKey().equals(formSchemeKey) || !canReadForm || (map = formOrGroupKeysByDuty.get(unitKey)) == null || map.size() <= 0 || (hasAuth = map.get(formGroupDefine.getKey())).booleanValue()) continue;
                                noAuthkeys.add(formGroupDefine.getKey());
                            }
                        }
                    } else if (WorkFlowType.FORM.equals((Object)startType)) {
                        for (String formKey : forms) {
                            Boolean hasAuth;
                            Map<String, Boolean> map;
                            boolean canReadForm = this.definitionAuthorityProvider.canReadForm(formKey);
                            if (!canReadForm || (map = formOrGroupKeysByDuty.get(unitKey)) == null || map.size() <= 0 || (hasAuth = map.get(formKey)).booleanValue()) continue;
                            noAuthkeys.add(formKey);
                        }
                    }
                    if ((noAuthList = (Set)noAuthUnitToReportKeysMap.get(unitKey)) != null) {
                        noAuthList.addAll(noAuthkeys);
                        continue;
                    }
                    noAuthUnitToReportKeysMap.put(unitKey, noAuthkeys);
                }
            }
            int noAuthNum = 0;
            int noAuthEntityNum = 0;
            for (Map.Entry keys : noAuthUnitToReportKeysMap.entrySet()) {
                Set value = (Set)keys.getValue();
                noAuthNum += value.size();
                if (value == null || value.size() <= 0) continue;
                ++noAuthEntityNum;
            }
            if (WorkFlowType.FORM.equals((Object)startType)) {
                uploadReturnInfo.setOtherErrorFromNum(noAuthNum);
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                uploadReturnInfo.setOtherErrorFromGroupNum(noAuthNum);
            }
            uploadReturnInfo.setOtherErrorNum(noAuthEntityNum);
            uploadReturnInfo.setErrorEntityNum(noAuthEntityNum);
        }
    }

    private boolean enableMultCheck(TaskFlowsDefine flowsSetting) {
        boolean enableMultCheck = false;
        if (VERSION.equals(this.mulCheckVersion)) {
            boolean mulCheckBeforeCheck = flowsSetting.getMulCheckBeforeCheck();
            return (this.mulCheckBatchExecute || this.openAudit) && mulCheckBeforeCheck;
        }
        if (VERSION2.equals(this.mulCheckVersion)) {
            return flowsSetting.getMulCheckBeforeCheck();
        }
        return enableMultCheck;
    }

    private List<String> getDimensionCollection(TaskDefine taskDefine, Map<String, DimensionValue> dimMap, String formSchemeKey, String dwDimName) {
        List<String> unitKeys = new ArrayList<String>();
        TaskGatherType taskGatherType = taskDefine.getTaskGatherType();
        if (TaskGatherType.TASK_GATHER_AUTO.equals((Object)taskGatherType)) {
            DWLeafNodeBuilder dWLeafNodeBuilder = new DWLeafNodeBuilder();
            DWLeafNodeBuilder instance = dWLeafNodeBuilder.getInstance();
            DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet(dimMap);
            DimensionCollection dimensionCollection = this.dimensionBuildUtil.getDimensionCollection(dimensionValueSet, formSchemeKey, (SpecificDimBuilder)instance);
            DimensionValueSet combineDim = dimensionCollection.combineDim();
            Object value = combineDim.getValue(dwDimName);
            if (value != null) {
                if (value instanceof List) {
                    unitKeys = (List)value;
                } else {
                    unitKeys.add(value.toString());
                }
            }
            return unitKeys;
        }
        return null;
    }

    private Set<String> fliterCurrentUnit(BatchExecuteTaskParam param, DimensionCollection buildDimensionCollection, List<WorkflowDataBean> filterForm, UploadReturnInfo uploadReturnInfo, List<ReturnInfo> warning, String mainKey, String mainDim, WorkFlowType startType, String taskCode) {
        Set unitKeysByCurrentUser = this.dataentryWorkflowUtil.getFliterUnitKeys(param.getContext().getFormSchemeKey(), param.getContext().getDimensionSet());
        if (unitKeysByCurrentUser != null && unitKeysByCurrentUser.size() > 0) {
            ArrayList otherInfo = new ArrayList();
            Map<String, EntityData> entitys = this.getEntitys(param.getContext(), unitKeysByCurrentUser, mainKey);
            List unitKeys = new ArrayList<String>();
            for (String unitKey : unitKeysByCurrentUser) {
                Object value;
                DimensionValueSet dimSet;
                ArrayList<String> noAuthMsg;
                DimensionValueSet dimensionValueSet = buildDimensionCollection.combineDim();
                Object unitObj = dimensionValueSet.getValue(mainDim);
                if (unitObj instanceof ArrayList) {
                    unitKeys = (List)unitObj;
                } else {
                    unitKeys.add(unitKey.toString());
                }
                if (!unitKeys.contains(unitKey)) continue;
                EntityData entityData = entitys.get(unitKey);
                ReturnInfo ruturnInfo = new ReturnInfo();
                if (entityData == null) continue;
                ruturnInfo.setEntity(entityData);
                String actionNameAlias = this.obtainCustomName.getActionNameByActionCode(param.getContext().getFormSchemeKey(), param.getActionId(), taskCode);
                String msg = "\u7528\u6237\u6240\u5c5e\u5355\u4f4d\u548c\u76d1\u7ba1\u5355\u4f4d\u4e0d\u5141\u8bb8\u5f3a\u5236" + actionNameAlias;
                ruturnInfo.setMessage(msg);
                uploadReturnInfo.setMessage(otherInfo);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    noAuthMsg = new ArrayList<String>();
                    for (WorkflowDataBean workflowDataBean : filterForm) {
                        dimSet = workflowDataBean.getDimSet();
                        if (dimSet == null || !(value = dimSet.getValue(mainDim)).toString().equals(unitKey)) continue;
                        List formKeys = workflowDataBean.getFormKeys();
                        List formDefines = this.runtimeView.queryFormsById(formKeys);
                        ruturnInfo.setFormDefine(formDefines);
                        for (FormDefine formDefine : formDefines) {
                            String title = formDefine.getTitle();
                            noAuthMsg.add(title + "," + msg);
                        }
                        ruturnInfo.setStateMessage(noAuthMsg);
                        int otherErrorFromNum = uploadReturnInfo.getOtherErrorFromNum();
                        uploadReturnInfo.setOtherErrorFromNum(otherErrorFromNum + formKeys.size());
                        break;
                    }
                } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                    noAuthMsg = new ArrayList();
                    for (WorkflowDataBean workflowDataBean : filterForm) {
                        dimSet = workflowDataBean.getDimSet();
                        if (dimSet == null || !(value = dimSet.getValue(mainDim)).toString().equals(unitKey)) continue;
                        List formGroupKeys = workflowDataBean.getFormGroupKeys();
                        int otherErrorFromGroupNum = uploadReturnInfo.getOtherErrorFromGroupNum();
                        uploadReturnInfo.setOtherErrorFromGroupNum(otherErrorFromGroupNum + formGroupKeys.size());
                        ArrayList<FormGroupDefine> groupDefines = new ArrayList<FormGroupDefine>();
                        for (String formGroupKey : formGroupKeys) {
                            FormGroupDefine formGroupDefine = this.runtimeView.queryFormGroup(formGroupKey);
                            String title = formGroupDefine.getTitle();
                            noAuthMsg.add(title + "," + msg);
                            groupDefines.add(formGroupDefine);
                        }
                        ruturnInfo.setStateMessage(noAuthMsg);
                        ruturnInfo.setFormGroupDefine(groupDefines);
                        break;
                    }
                } else {
                    uploadReturnInfo.addOtherErrorNum();
                }
                warning.add(ruturnInfo);
            }
        }
        return unitKeysByCurrentUser;
    }

    private boolean allowStoped(String taskKey) {
        ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String allowStoped = taskOptionController.getValue(taskKey, "ALLOW_STOP_FILING");
        return TASK_STATE.equals(allowStoped);
    }
}

