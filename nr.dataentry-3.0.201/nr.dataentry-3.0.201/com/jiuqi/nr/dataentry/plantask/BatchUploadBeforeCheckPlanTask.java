/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.JobExecutor
 *  com.jiuqi.bi.core.jobs.JobFactory
 *  com.jiuqi.bi.core.jobs.JobsException
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$ClassifierContext
 *  com.jiuqi.bi.core.jobs.extension.IJobClassifier$Path
 *  com.jiuqi.bi.core.jobs.extension.IJobListener
 *  com.jiuqi.bi.core.jobs.extension.JobListenerContext
 *  com.jiuqi.bi.core.jobs.extension.item.FolderItem
 *  com.jiuqi.bi.core.jobs.extension.item.JobItem
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultData
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IEntityViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.dataentry.plantask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobListener;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;
import com.jiuqi.bi.core.jobs.extension.item.FolderItem;
import com.jiuqi.bi.core.jobs.extension.item.JobItem;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.BatchUploadCheckParam;
import com.jiuqi.nr.dataentry.bean.UploadCheckResult;
import com.jiuqi.nr.dataentry.internal.service.util.CheckResultParamForReportUtil;
import com.jiuqi.nr.dataentry.internal.service.util.UploadCheckFliterUtil;
import com.jiuqi.nr.dataentry.monitor.WorkFlowCheckProgressMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IEntityViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchUploadBeforeCheckPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "3ebd5ca5-58e0-4cf6-abf0-a97156229935";
    private static final String JOB_TITLE = "\u4e0a\u62a5\u524d\u5ba1\u6838\u529f\u80fd\u6f0f\u6d1e\u68c0\u67e5";
    private static final String JOB_CONFIG_PAGE = "job-upload-check";
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private IEntityViewRunTimeController npRuntimeEntityController;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityViewController entityCotroller;
    @Autowired
    IDataentryFlowService dataentryFlowService;
    @Autowired
    IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private ICheckService iCheckService;
    @Autowired
    private ICheckResultService iCheckResultService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CheckResultParamForReportUtil checkResultParamForReportUtil;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private UploadCheckFliterUtil uploadCheckFliterUtil;

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    public JobExecutor createJobExecutor(String arg0) throws JobsException {
        return new UploadCheckExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new UploadCheckRunnerJobClassifier();
    }

    public IJobListener getJobListener() {
        return new UploadCheckRunnerJobListener();
    }

    class UploadCheckRunnerJobListener
    implements IJobListener {
        UploadCheckRunnerJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class UploadCheckRunnerJobClassifier
    implements IJobClassifier {
        UploadCheckRunnerJobClassifier() {
        }

        public List<FolderItem> getFolders(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public List<JobItem> getItems(String s, IJobClassifier.ClassifierContext classifierContext) throws Exception {
            return null;
        }

        public JobItem getJobItem(String s) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(FolderItem folderItem) throws Exception {
            return null;
        }

        public IJobClassifier.Path locate(JobItem jobItem) throws Exception {
            return null;
        }
    }

    class UploadCheckExecutor
    extends JobExecutor {
        private final Logger logger = LoggerFactory.getLogger(UploadCheckExecutor.class);
        private JobContext jobContext;

        UploadCheckExecutor() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void execute(JobContext jobContext) throws JobExecutionException {
            String user = jobContext.getJob().getUser();
            NpContextImpl npContext = null;
            if (!StringUtils.isEmpty((CharSequence)user)) {
                try {
                    npContext = this.buildContext(user);
                    NpContextHolder.setContext((NpContext)npContext);
                }
                catch (JQException e) {
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
                }
            }
            this.jobContext = jobContext;
            try {
                this.excute();
            }
            finally {
                if (npContext != null) {
                    NpContextHolder.clearContext();
                }
            }
        }

        public boolean excute() {
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u6267\u884c\u4e0a\u62a5\u5ba1\u6838\u4efb\u52a1:\r\n");
            String runnerParameter = this.jobContext.getJob().getExtendedConfig();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                BatchUploadCheckParam params = (BatchUploadCheckParam)objectMapper.readValue(runnerParameter, BatchUploadCheckParam.class);
                TaskDefine taskData = BatchUploadBeforeCheckPlanTask.this.runtimeViewController.queryTaskDefine(params.getTask());
                FormSchemeDefine formScheme = BatchUploadBeforeCheckPlanTask.this.runtimeViewController.getFormScheme(params.getFormScheme());
                List<DimensionValueSet> uploadDimensionValueSet = this.queryUploadDimensionValueSet(formScheme);
                List<DimensionValueSet> splitDimension = this.splitDimension(uploadDimensionValueSet, formScheme.getKey());
                this.batchCheck(formScheme, splitDimension);
                this.allCheckResult(splitDimension, formScheme, taskData);
                this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u7ed3\u675f\u3002");
            }
            catch (JsonMappingException e1) {
                this.jobContext.getDefaultLogger().info("\u5e8f\u5217\u5316\u95ee\u9898\uff0c\u4e0a\u62a5\u5ba1\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38\u3002\r\n");
                return false;
            }
            catch (JsonProcessingException e1) {
                this.jobContext.getDefaultLogger().info("\u5e8f\u5217\u5316\u95ee\u9898\uff0c\u4e0a\u62a5\u5ba1\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38\u3002\r\n");
                return false;
            }
            catch (Exception e) {
                this.jobContext.getDefaultLogger().info("\u4e0a\u62a5\u5ba1\u6838\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38\u3002\r\n");
                return false;
            }
            return true;
        }

        private List<DimensionValueSet> queryUploadDimensionValueSet(FormSchemeDefine formScheme) {
            ArrayList<DimensionValueSet> unitList = new ArrayList<DimensionValueSet>();
            try {
                String beforeOneDay = this.getBeforeOneDay();
                Date beforeOneDayZero = this.getBeforeOneDayZero(beforeOneDay);
                Date beforeOneDay24h = this.getBeforeOneDay24h(beforeOneDay);
                DimensionValueSet dim = new DimensionValueSet();
                List uploadStateList = BatchUploadBeforeCheckPlanTask.this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dim, new ArrayList());
                if (uploadStateList != null && uploadStateList.size() > 0) {
                    for (UploadStateNew uploadState : uploadStateList) {
                        ActionStateBean actionStateBean = uploadState.getActionStateBean();
                        String code = actionStateBean.getCode();
                        Date updateTime = uploadState.getUpdateTime();
                        if (updateTime == null || !updateTime.after(beforeOneDayZero) || !updateTime.before(beforeOneDay24h) || !UploadState.UPLOADED.toString().equals(code)) continue;
                        DimensionValueSet entities = uploadState.getEntities();
                        unitList.add(entities);
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("\u5f02\u5e38", e);
            }
            return unitList;
        }

        private void batchCheck(FormSchemeDefine formScheme, List<DimensionValueSet> splitDimension) {
            try {
                for (DimensionValueSet dimensionValueSet : splitDimension) {
                    String[] formulaSchemeKeys;
                    CheckParam checkParam = new CheckParam();
                    checkParam.setActionId(UUID.randomUUID().toString());
                    String filterCondition = formScheme.getFlowsSetting().getFilterCondition();
                    checkParam.setFilterCondition(filterCondition);
                    Map dimensionSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
                    boolean judgeCurrentcyType = BatchUploadBeforeCheckPlanTask.this.uploadCheckFliterUtil.judgeCurrentcyType(formScheme.getKey(), dimensionSet);
                    if (!judgeCurrentcyType) {
                        BatchUploadBeforeCheckPlanTask.this.uploadCheckFliterUtil.setCheckConditions(formScheme.getTaskKey(), dimensionSet);
                    }
                    DimensionCollection dimensionCollection = BatchUploadBeforeCheckPlanTask.this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, formScheme.getKey());
                    checkParam.setDimensionCollection(dimensionCollection);
                    checkParam.setMode(Mode.FORM);
                    double progress = 0.0;
                    double scale = 0.0;
                    AsyncTaskMonitor asyncTaskMonitor = this.buildAsyncTaskMonitor();
                    WorkFlowCheckProgressMonitor workflowAsyncProgressMonitor = new WorkFlowCheckProgressMonitor(asyncTaskMonitor, scale, progress);
                    JtableContext jtbContext = this.buildJtableContext(formScheme, dimensionValueSet);
                    String formulaSchemeKey = jtbContext.getFormulaSchemeKey();
                    for (String key : formulaSchemeKeys = formulaSchemeKey.split(";")) {
                        checkParam.setFormulaSchemeKey(key);
                        BatchUploadBeforeCheckPlanTask.this.iCheckService.batchCheck(checkParam, (IFmlMonitor)workflowAsyncProgressMonitor);
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("\u5f02\u5e38", e);
            }
        }

        private List<CheckResultData> haveCheckResults(FormSchemeDefine formScheme, List<DimensionValueSet> splitDimension) {
            ArrayList<CheckResultData> checkResults = new ArrayList<CheckResultData>();
            EntityViewData dwEntity = BatchUploadBeforeCheckPlanTask.this.jtableParamService.getDwEntity(formScheme.getKey());
            String dwDimName = dwEntity.getDimensionName();
            Map<String, List<Integer>> customFormulaTypeMap = BatchUploadBeforeCheckPlanTask.this.checkResultParamForReportUtil.getCustomFormulaTypeMap(formScheme.getTaskKey());
            Map<String, List<Integer>> flowFormulaTypeMap = BatchUploadBeforeCheckPlanTask.this.checkResultParamForReportUtil.getFlowFormulaTypeMap(formScheme.getKey());
            for (DimensionValueSet dimension : splitDimension) {
                JtableContext jtbContext = this.buildJtableContext(formScheme, dimension);
                String formulaSchemeKey = jtbContext.getFormulaSchemeKey();
                String[] formulaSchemeKeys = formulaSchemeKey.split(";");
                CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
                checkResultQueryParam.setMode(Mode.FORM);
                checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(formulaSchemeKeys));
                String currUnitKey = (String)dimension.getValue(dwDimName);
                List<Object> erroStatus = new ArrayList();
                List<Object> needErrorComment = new ArrayList();
                boolean enableCustomConfig = BatchUploadBeforeCheckPlanTask.this.checkResultParamForReportUtil.enableCustomConfig(dwEntity, currUnitKey, formScheme.getTaskKey(), formScheme.getKey());
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
                DimensionCollection dimensionCollection = BatchUploadBeforeCheckPlanTask.this.dimensionCollectionUtil.getDimensionCollection(dimension, formScheme.getKey());
                String string = formScheme.getFlowsSetting().getFilterCondition();
                checkResultQueryParam.setFilterCondition(string);
                checkResultQueryParam.setDimensionCollection(dimensionCollection);
                checkResultQueryParam.setCheckTypes(checkTypes);
                CheckResult batchCheckResult = BatchUploadBeforeCheckPlanTask.this.iCheckResultService.queryBatchCheckResult(checkResultQueryParam);
                checkResults.addAll(batchCheckResult.getResultData());
            }
            return checkResults;
        }

        private void allCheckResult(List<DimensionValueSet> splitDimension, FormSchemeDefine formScheme, TaskDefine taskData) {
            try {
                HashMap<Integer, UploadCheckResult> resultMap = new HashMap<Integer, UploadCheckResult>();
                List<CheckResultData> checkResults = this.haveCheckResults(formScheme, splitDimension);
                for (CheckResultData formulaCheckResultInfo : checkResults) {
                    UploadCheckResult uploadCheckResult = new UploadCheckResult();
                    String unitCode = formulaCheckResultInfo.getUnitCode();
                    String unitTitle = formulaCheckResultInfo.getUnitTitle();
                    Map dimensionTitle = formulaCheckResultInfo.getDimensionTitle();
                    String period = (String)dimensionTitle.get("DATATIME");
                    String periodTitle = this.getPeriodTitle(taskData, period);
                    uploadCheckResult.setUnitCode(unitCode);
                    uploadCheckResult.setUnitTitle(unitTitle);
                    uploadCheckResult.setPeriod(periodTitle);
                    uploadCheckResult.setPeriodTitle(periodTitle);
                    int hashCode = uploadCheckResult.hashCode();
                    if (resultMap.get(hashCode) != null) continue;
                    resultMap.put(hashCode, uploadCheckResult);
                }
                StringBuffer sb = new StringBuffer();
                if (resultMap.size() > 0) {
                    for (Map.Entry map : resultMap.entrySet()) {
                        UploadCheckResult uploadCheckResult = (UploadCheckResult)map.getValue();
                        sb.append("\u65f6\u671f\uff1a" + uploadCheckResult.getPeriodTitle() + ",\u5355\u4f4d\uff1a" + uploadCheckResult.getUnitCode() + "|" + uploadCheckResult.getUnitTitle() + ";\n");
                    }
                } else {
                    sb.append("\u65e0;\n");
                }
                this.jobContext.getDefaultLogger().info("\r\n\u6709\u5ba1\u6838\u9519\u8bef\u7684\u5355\u4f4d\u4fe1\u606f\uff1a\r\n" + sb.toString());
            }
            catch (Exception e) {
                this.jobContext.getDefaultLogger().error("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u51fa\u9519", (Throwable)e);
            }
        }

        public String getPeriodTitle(TaskDefine taskData, String period) {
            IPeriodProvider periodProvider = BatchUploadBeforeCheckPlanTask.this.periodEntityAdapter.getPeriodProvider(taskData.getDateTime());
            return periodProvider.getPeriodTitle(period);
        }

        private List<DimensionValueSet> splitDimension(List<DimensionValueSet> dimensionValueSetList, String fromSchemeKey) {
            ArrayList<DimensionValueSet> dimensionValues = new ArrayList<DimensionValueSet>();
            HashMap<Integer, UploadCheckResult> map = new HashMap<Integer, UploadCheckResult>();
            String mainDimName = this.getMainDimName(fromSchemeKey);
            for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
                String period = dimensionValueSet.getValue("DATATIME").toString();
                String unitId = dimensionValueSet.getValue(mainDimName).toString();
                UploadCheckResult uploadCheckResult = new UploadCheckResult();
                uploadCheckResult.setPeriod(period);
                uploadCheckResult.setUnitCode(unitId);
                UploadCheckResult result = (UploadCheckResult)map.get(uploadCheckResult.hashCode());
                if (result != null) continue;
                map.put(uploadCheckResult.hashCode(), uploadCheckResult);
            }
            DimensionValueSet dimensionValue = null;
            for (Map.Entry periodToUnit : map.entrySet()) {
                dimensionValue = new DimensionValueSet();
                UploadCheckResult value = (UploadCheckResult)periodToUnit.getValue();
                dimensionValue.setValue("DATATIME", (Object)value.getPeriod());
                dimensionValue.setValue(mainDimName, (Object)value.getUnitCode());
                dimensionValues.add(dimensionValue);
            }
            return dimensionValues;
        }

        private JtableContext buildJtableContext(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet) {
            JtableContext jtbContext = new JtableContext();
            jtbContext.setTaskKey(formScheme.getTaskKey());
            jtbContext.setFormSchemeKey(formScheme.getKey());
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            jtbContext.setDimensionSet(dimensionSet);
            WorkflowConfig workflowConfig = BatchUploadBeforeCheckPlanTask.this.dataentryFlowService.queryWorkflowConfig(formScheme.getKey());
            String checkFormulaSchemeKey = workflowConfig.getCheckFormulaSchemeKey();
            if (StringUtils.isNotEmpty((CharSequence)checkFormulaSchemeKey)) {
                jtbContext.setFormulaSchemeKey(checkFormulaSchemeKey);
            } else {
                FormulaSchemeDefine defaultFormulaScheme = BatchUploadBeforeCheckPlanTask.this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formScheme.getKey());
                jtbContext.setFormulaSchemeKey(defaultFormulaScheme.getKey());
            }
            return jtbContext;
        }

        public String getBeforeOneDay() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(5, -1);
            return sdf.format(calendar.getTime());
        }

        public Date getBeforeOneDayZero(String date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = date.substring(0, 10) + " 00:00:00";
            try {
                return sdf.parse(startTime);
            }
            catch (ParseException e) {
                this.logger.error("\u65f6\u95f4\u683c\u5f0f\u8f6c\u6362\u51fa\u9519", e);
                return null;
            }
        }

        public Date getBeforeOneDay24h(String date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = date.substring(0, 10) + " 23:59:59";
            try {
                return sdf.parse(startTime);
            }
            catch (ParseException e) {
                this.logger.error("\u65f6\u95f4\u683c\u5f0f\u8f6c\u6362\u51fa\u9519", e);
                return null;
            }
        }

        private AsyncTaskMonitor buildAsyncTaskMonitor() {
            AsyncTaskMonitor asyncTaskMonitor = new AsyncTaskMonitor(){

                public void progressAndMessage(double progress, String message) {
                }

                public boolean isFinish() {
                    return false;
                }

                public boolean isCancel() {
                    return false;
                }

                public String getTaskPoolTask() {
                    return null;
                }

                public String getTaskId() {
                    return "upload-check-2192";
                }

                public void finish(String result, Object detail) {
                }

                public void error(String result, Throwable t) {
                }

                public void canceling(String result, Object detail) {
                }

                public void canceled(String result, Object detail) {
                }
            };
            return asyncTaskMonitor;
        }

        private NpContextImpl buildContext(String userName) throws JQException {
            NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
            npContext.setTenant("__default_tenant__");
            NpContextUser contextUser = this.buildUserContext(userName);
            npContext.setUser((ContextUser)contextUser);
            NpContextIdentity identity = this.buildIdentityContext(contextUser);
            npContext.setIdentity((ContextIdentity)identity);
            return npContext;
        }

        private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(contextUser.getId());
            identity.setTitle(contextUser.getFullname());
            return identity;
        }

        private NpContextUser buildUserContext(String userName) throws JQException {
            NpContextUser userContext = new NpContextUser();
            User user = this.getUserByUserName(userName);
            if (user == null) {
                throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
            }
            userContext.setId(user.getId());
            userContext.setName(user.getName());
            userContext.setNickname(user.getNickname());
            userContext.setDescription(user.getDescription());
            return userContext;
        }

        private User getUserByUserName(String userName) {
            if (StringUtils.isEmpty((CharSequence)userName)) {
                return null;
            }
            Optional user = BatchUploadBeforeCheckPlanTask.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = BatchUploadBeforeCheckPlanTask.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
        }

        private String getMainDimName(String formSchemeKey) {
            String dimensionName = null;
            try {
                String[] entitiesKeyArr;
                FormSchemeDefine formScheme = BatchUploadBeforeCheckPlanTask.this.runtimeViewController.getFormScheme(formSchemeKey);
                String masterEntitiesKey = formScheme.getMasterEntitiesKey();
                for (String entitiy : entitiesKeyArr = masterEntitiesKey.split(";")) {
                    if (BatchUploadBeforeCheckPlanTask.this.periodEntityAdapter.isPeriodEntity(entitiy)) continue;
                    EntityViewDefine entityView = BatchUploadBeforeCheckPlanTask.this.npRuntimeEntityController.buildEntityView(entitiy);
                    dimensionName = BatchUploadBeforeCheckPlanTask.this.entityCotroller.getDimensionNameByViewKey(entityView.getEntityId());
                }
                return dimensionName;
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                return dimensionName;
            }
        }
    }
}

