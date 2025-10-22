/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.gather.exception.DataGatherException
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.formtype.common.EntityUnitNatureGetter
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.nvwa.login.domain.NvwaContextOrg
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.plantask;

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
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.gather.exception.DataGatherException;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.dataentry.plantask.PlanTaskUtil;
import com.jiuqi.nr.dataentry.service.IBatchCaclSingleService;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectDataGatherPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "3892a995-07dd-4bf9-a882-95064be79e9c";
    private static final String JOB_TITLE = "\u9009\u62e9\u6c47\u603b";
    private static final String JOB_CONFIG_PAGE = "job-selectDataGather";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IBatchDataSumService batchDataSumService;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceSession;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired(required=false)
    IBatchCaclSingleService iBatchCaclSingleService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private PlanTaskUtil planTaskUtil;

    private String getPeriod(String period, int periondConfig, int periodType, FormSchemeDefine formSchemeDefine, String entityViewKey) {
        int customPeriod = 2;
        String periodValue = null;
        int offset = 0;
        if (periondConfig != customPeriod) {
            offset = periondConfig == 3 ? 2 : (periondConfig == -3 ? -2 : periondConfig);
        } else {
            return period;
        }
        if (periodType == 8) {
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityViewKey);
            List periodItems = periodProvider.getPeriodItems();
            Date nowDate = new Date();
            for (int i = 0; i < periodItems.size(); ++i) {
                if (!nowDate.after(((IPeriodRow)periodItems.get(i)).getStartDate()) || !nowDate.before(((IPeriodRow)periodItems.get(i)).getEndDate())) continue;
                if (offset + i < 0 || offset + i > periodItems.size() - 1) {
                    StringBuffer buffer = new StringBuffer("\u81ea\u5b9a\u4e49\u65f6\u671f\u504f\u79fb\u91cf\u8bbe\u7f6e\u9519\u8bef\uff1a").append("\u5f53\u524d\u671f\u7684\u5f00\u59cb\u65f6\u95f4\u4e3a:").append(((IPeriodRow)periodItems.get(i)).getStartDate()).append("\u7ed3\u675f\u65f6\u95f4\u4e3a:").append(((IPeriodRow)periodItems.get(i)).getEndDate()).append(",").append("\u53d6\u6570\u671f'").append(offset == -1 ? "\u4e0a\u4e00\u671f'," : "\u4e0b\u4e00\u671f',").append(offset == -1 ? "\u5c0f\u4e8e\u6700\u5c0f\u81ea\u5b9a\u4e49\u671f" : "\u8d85\u51fa\u6700\u5927\u81ea\u5b9a\u671f");
                    throw new IllegalArgumentException(buffer.toString());
                }
                periodValue = ((IPeriodRow)periodItems.get(offset + i)).getCode();
                break;
            }
        } else {
            PeriodWrapper periodWrapper = this.getCurrPeriod(formSchemeDefine);
            periodValue = PeriodUtil.currentPeriod((GregorianCalendar)PeriodUtil.getCurrentCalendar(), (int)periodWrapper.getType(), (int)offset).toString();
        }
        return periodValue;
    }

    private PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        }
        catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
        PeriodType periodType = taskDefine.getPeriodType();
        int periodOffset = taskDefine.getTaskPeriodOffset();
        String fromPeriod = taskDefine.getFromPeriod();
        String toPeriod = taskDefine.getToPeriod();
        if (null == fromPeriod || null == toPeriod) {
            char typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
            fromPeriod = "1970" + typeToCode + "0001";
            toPeriod = "9999" + typeToCode + "0001";
        }
        return this.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        PeriodWrapper fromPeriodWrapper = null;
        PeriodWrapper toPeriodWrapper = null;
        try {
            fromPeriodWrapper = new PeriodWrapper(fromPeriod);
            toPeriodWrapper = new PeriodWrapper(toPeriod);
        }
        catch (Exception e) {
            throw new JSONException(e.getMessage());
        }
        int fromYear = fromPeriodWrapper.getYear();
        int toYear = toPeriodWrapper.getYear();
        return PeriodUtil.currentPeriod((int)fromYear, (int)toYear, (int)periodType, (int)periodOffset);
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new ActionsExecutor();
    }

    public IJobListener getJobListener() {
        return new ActionsJobListener();
    }

    public IJobClassifier getJobClassifier() {
        return new ActionsJobClassifier();
    }

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    class ActionsJobListener
    implements IJobListener {
        ActionsJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class ActionsJobClassifier
    implements IJobClassifier {
        ActionsJobClassifier() {
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

    class ActionsExecutor
    extends JobExecutor {
        private StringBuffer logs;

        ActionsExecutor() {
        }

        public void execute(JobContext jobContext) throws JobExecutionException {
            this.logs = new StringBuffer();
            NpContextImpl npContext = null;
            String user = jobContext.getJob().getUser();
            String errorInfo = "task_error_info";
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(taskId, SelectDataGatherPlanTask.this.cacheObjectResourceSession);
            String runnerParameter = jobContext.getJob().getExtendedConfig();
            if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)user)) {
                jobContext.getDefaultLogger().error("\u6267\u884c\u7528\u6237\u672a\u6307\u5b9a\uff0c\u8bf7\u6307\u5b9a\u6267\u884c\u7528\u6237\u3002");
                throw new JobExecutionException("\u6267\u884c\u7528\u6237\u672a\u6307\u5b9a\uff0c\u8bf7\u6307\u5b9a\u6267\u884c\u7528\u6237\u3002");
            }
            try {
                npContext = this.buildContext(user);
                NpContextHolder.setContext((NpContext)npContext);
            }
            catch (JQException e) {
                LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
            }
            try {
                BatchDataSumInfo batchDataSumInfo = this.buildDataGatherInfo(runnerParameter, jobContext);
                jobContext.getDefaultLogger().info(this.logs.toString());
                String calAfterDataSumSettings = SelectDataGatherPlanTask.this.iTaskOptionController.getValue(batchDataSumInfo.getContext().getTaskKey(), "AUTOCALCULATE_AFTER_DATASUM");
                boolean calAfterDataSum = false;
                if (!StringUtils.isEmpty((String)calAfterDataSumSettings)) {
                    calAfterDataSum = calAfterDataSumSettings.equals("1");
                }
                if (calAfterDataSum) {
                    DataEntryAsyncProgressMonitor asyncProgressMonitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.8, 0.0);
                    SelectDataGatherPlanTask.this.batchDataSumService.batchDataSumForm(batchDataSumInfo, asyncProgressMonitor, 1.0f);
                    jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u9009\u62e9\u6c47\u603b\u6267\u884c\u5b8c\u6bd5\u3002\r\n");
                    DataEntryAsyncProgressMonitor dataEntryAsyncProgressMonitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.2, 0.8);
                    this.ExecuteCal(batchDataSumInfo, dataEntryAsyncProgressMonitor);
                    asyncTaskMonitor.finish("summary_success_info", null);
                } else {
                    SelectDataGatherPlanTask.this.batchDataSumService.batchDataSumForm(batchDataSumInfo, asyncTaskMonitor, 1.0f);
                    jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u9009\u62e9\u6c47\u603b\u6267\u884c\u5b8c\u6bd5\u3002\r\n");
                }
            }
            catch (Exception e) {
                jobContext.getDefaultLogger().error(errorInfo, (Throwable)e);
                asyncTaskMonitor.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                jobContext.setResult(-100, "\u4efb\u52a1\u5931\u8d25");
                throw new RuntimeException(e);
            }
            finally {
                if (npContext != null) {
                    NpContextHolder.clearContext();
                }
            }
        }

        private void ExecuteCal(BatchDataSumInfo batchDataSumInfo, AsyncTaskMonitor asyncTaskMonitor) {
            JtableContext jtableContext = batchDataSumInfo.getContext();
            if (batchDataSumInfo.isRecursive()) {
                IEntityTable entityTable;
                ExecutorContext executorContext = new ExecutorContext(SelectDataGatherPlanTask.this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(SelectDataGatherPlanTask.this.iRunTimeViewController, SelectDataGatherPlanTask.this.dataDefinitionRuntimeController, SelectDataGatherPlanTask.this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                String periodCode = String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
                FormSchemeDefine formScheme = null;
                try {
                    formScheme = SelectDataGatherPlanTask.this.iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
                }
                catch (Exception e) {
                    throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
                }
                String entityId = formScheme.getDw();
                EntityViewDefine entityViewDefine = SelectDataGatherPlanTask.this.iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
                try {
                    entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, jtableContext.getFormSchemeKey());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                EntityViewData masterEntity = SelectDataGatherPlanTask.this.jtableParamService.getDwEntity(formScheme.getKey());
                String MD_ORG = masterEntity.getDimensionName();
                List iEntityRowList = entityTable.getAllChildRows(String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()));
                Iterator it = iEntityRowList.iterator();
                StringBuffer mergeDimension = new StringBuffer();
                mergeDimension.append(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()).append(";");
                LinkedHashSet<String> dimensionSet = new LinkedHashSet<String>();
                if (batchDataSumInfo.isDifference()) {
                    dimensionSet.clear();
                    IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
                    EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(masterEntity.getKey());
                    while (it.hasNext()) {
                        IEntityRow iEntityRow = (IEntityRow)it.next();
                        if (!this.isMinus(entityFormGather, iEntityRow)) continue;
                        dimensionSet.add(iEntityRow.getEntityKeyData());
                    }
                } else {
                    while (it.hasNext()) {
                        IEntityRow iEntityRow = (IEntityRow)it.next();
                        if (entityTable.getChildRows(iEntityRow.getEntityKeyData()).size() <= 0) continue;
                        dimensionSet.add(iEntityRow.getEntityKeyData());
                    }
                }
                for (String dimensionString : dimensionSet) {
                    mergeDimension.append(dimensionString + ";");
                }
                if (!mergeDimension.toString().equals("")) {
                    mergeDimension.deleteCharAt(mergeDimension.length() - 1);
                    ((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).setValue(mergeDimension.toString());
                }
                BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
                batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                if (SelectDataGatherPlanTask.this.iBatchCaclSingleService == null || StringUtils.isEmpty((String)SelectDataGatherPlanTask.this.iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()))) {
                    batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                } else {
                    batchCalculateInfo.setFormulaSchemeKey(SelectDataGatherPlanTask.this.iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()));
                }
                batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
                batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
                SelectDataGatherPlanTask.this.batchCalculateService.batchCalculateForm(batchCalculateInfo, asyncTaskMonitor);
            } else {
                if (batchDataSumInfo.isDifference()) {
                    IEntityTable entityTable;
                    ExecutorContext executorContext = new ExecutorContext(SelectDataGatherPlanTask.this.dataDefinitionRuntimeController);
                    ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(SelectDataGatherPlanTask.this.iRunTimeViewController, SelectDataGatherPlanTask.this.dataDefinitionRuntimeController, SelectDataGatherPlanTask.this.entityViewRunTimeController, jtableContext.getFormSchemeKey());
                    executorContext.setEnv((IFmlExecEnvironment)environment);
                    String periodCode = String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get("DATATIME")).getValue());
                    FormSchemeDefine formScheme = null;
                    try {
                        formScheme = SelectDataGatherPlanTask.this.iRunTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
                    }
                    catch (Exception e) {
                        throw new DataGatherException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{jtableContext.getFormSchemeKey()});
                    }
                    EntityViewDefine entityViewDefine = SelectDataGatherPlanTask.this.iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
                    try {
                        entityTable = this.getEntityTable((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext, entityViewDefine, periodCode, jtableContext.getFormSchemeKey());
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    EntityViewData masterEntity = SelectDataGatherPlanTask.this.jtableParamService.getDwEntity(formScheme.getKey());
                    String MD_ORG = masterEntity.getDimensionName();
                    List iEntityRowList = entityTable.getAllChildRows(String.valueOf(((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).getValue()));
                    IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
                    EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(masterEntity.getKey());
                    Iterator it = iEntityRowList.iterator();
                    LinkedHashSet<String> dimensionSet = new LinkedHashSet<String>();
                    while (it.hasNext()) {
                        IEntityRow iEntityRow = (IEntityRow)it.next();
                        if (!this.isMinus(entityFormGather, iEntityRow)) continue;
                        dimensionSet.add(iEntityRow.getEntityKeyData());
                    }
                    StringBuffer mergeDimension = new StringBuffer();
                    for (String dimensionString : dimensionSet) {
                        mergeDimension.append(dimensionString + ";");
                    }
                    if (!mergeDimension.toString().equals("")) {
                        mergeDimension.deleteCharAt(mergeDimension.length() - 1);
                        ((DimensionValue)jtableContext.getDimensionSet().get(MD_ORG)).setValue(mergeDimension.toString());
                    }
                }
                BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
                batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                if (SelectDataGatherPlanTask.this.iBatchCaclSingleService == null || StringUtils.isEmpty((String)SelectDataGatherPlanTask.this.iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()))) {
                    batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                } else {
                    batchCalculateInfo.setFormulaSchemeKey(SelectDataGatherPlanTask.this.iBatchCaclSingleService.getFormulaIdByFormSchemeKey(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey()));
                }
                batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
                batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
                SelectDataGatherPlanTask.this.batchCalculateService.batchCalculateForm(batchCalculateInfo, asyncTaskMonitor);
            }
        }

        private boolean isMinus(EntityUnitNatureGetter gather, IEntityRow row) {
            UnitNature unitNature = gather.getUnitNature(row);
            if (null != unitNature) {
                return unitNature.equals((Object)UnitNature.JTCEB);
            }
            return false;
        }

        public NpContextImpl buildContext(String userName) throws JQException {
            NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
            npContext.setTenant("__default_tenant__");
            NpContextUser contextUser = this.buildUserContext(userName);
            npContext.setUser((ContextUser)contextUser);
            NpContextIdentity identity = this.buildIdentityContext(contextUser);
            npContext.setIdentity((ContextIdentity)identity);
            if (org.springframework.util.StringUtils.hasText(contextUser.getOrgCode())) {
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setCode(contextUser.getOrgCode());
                OrgDO orgDO = SelectDataGatherPlanTask.this.orgDataClient.get(orgDTO);
                NvwaContextOrg nvwaContextOrg = new NvwaContextOrg();
                nvwaContextOrg.setCode(orgDO.getCode());
                nvwaContextOrg.setName(orgDO.getName());
                npContext.setOrganization((ContextOrganization)nvwaContextOrg);
            }
            return npContext;
        }

        private IEntityTable getEntityTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, EntityViewDefine unitView, String periodCode, String formSchemeKey) throws Exception {
            IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
            IEntityQuery entityQuery = entityDataService.newEntityQuery();
            entityQuery.setEntityView(unitView);
            DimensionValueSet masterKeys = new DimensionValueSet();
            if (!StringUtils.isEmpty((String)periodCode)) {
                masterKeys.setValue("DATATIME", (Object)periodCode);
            }
            entityQuery.setMasterKeys(masterKeys);
            executorContext.setVarDimensionValueSet(masterKeys);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeKey);
            executorContext.setPeriodView(formScheme.getDateTime());
            ExecutorContext executorContextInfo = new ExecutorContext(executorContext.getRuntimeController());
            executorContext.setDefaultGroupName(executorContext.getDefaultGroupName());
            executorContext.setJQReportModel(executorContext.isJQReportModel());
            executorContext.setVarDimensionValueSet(executorContext.getVarDimensionValueSet());
            executorContext.setEnv(executorContext.getEnv());
            IEntityTable entityTable = SelectDataGatherPlanTask.this.dataEntityFullService.executeEntityReader(entityQuery, executorContextInfo, unitView, formSchemeKey).getEntityTable();
            return entityTable;
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
            userContext.setOrgCode(user.getOrgCode());
            userContext.setDescription(user.getDescription());
            return userContext;
        }

        private User getUserByUserName(String userName) {
            if (org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)userName)) {
                return null;
            }
            Optional user = SelectDataGatherPlanTask.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = SelectDataGatherPlanTask.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
        }

        private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(contextUser.getId());
            identity.setOrgCode(contextUser.getOrgCode());
            identity.setTitle(contextUser.getFullname() + "\u3010\u5f53\u524d\u7528\u6237\u3011");
            return identity;
        }

        private BatchDataSumInfo buildDataGatherInfo(String runnerParameter, JobContext jobContext) throws Exception {
            BatchDataSumInfo batchDataSumInfo = new BatchDataSumInfo();
            JSONObject object = new JSONObject(runnerParameter);
            JtableContext context = new JtableContext();
            String task = object.getString("task");
            TaskDefine taskDefine = SelectDataGatherPlanTask.this.runTimeViewController.queryTaskDefine(task);
            if (null == taskDefine) {
                throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u4efb\u52a1:" + task);
            }
            context.setTaskKey(task);
            this.logs.append("\r\n\u6c47\u603b\u4efb\u52a1\u4e3a\u3010").append(taskDefine.getTitle()).append("\u3011\r\n");
            String formScheme = object.getString("formScheme");
            FormSchemeDefine formSchemeDefine = SelectDataGatherPlanTask.this.runTimeViewController.getFormScheme(formScheme);
            if (null == formSchemeDefine) {
                throw new RuntimeException("\u6ca1\u6709\u5728\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u4e0b\u627e\u5230\u62a5\u8868\u65b9\u6848\uff1a" + formScheme);
            }
            this.logs.append("\u6c47\u603b\u62a5\u8868\u65b9\u6848\u4e3a\u3010").append(formSchemeDefine.getTitle()).append("\u3011\r\n");
            List<TaskOrgData> taskOrgDataList = SelectDataGatherPlanTask.this.planTaskUtil.queryTaskorgDataList(task);
            if (object.has("unitCorporate")) {
                List unitCorporate;
                DsContext dScontext = DsContextHolder.getDsContext();
                DsContextImpl dsContext = (DsContextImpl)dScontext;
                dsContext.setEntityId(object.getString("unitCorporate"));
                if (taskOrgDataList.size() > 1 && (unitCorporate = taskOrgDataList.stream().filter(item -> item.getId().equals(object.getString("unitCorporate"))).collect(Collectors.toList())).size() > 0) {
                    jobContext.getDefaultLogger().info("\u6c47\u603b\u53d6\u6570\u5355\u4f4d\u53e3\u5f84\u4e3a\uff1a\u3010" + ((TaskOrgData)unitCorporate.get(0)).getTitle() + "\u3011");
                }
            }
            context.setFormSchemeKey(formScheme);
            String sumRange = object.getString("sumRange");
            batchDataSumInfo.setRecursive("all".equalsIgnoreCase(sumRange));
            Boolean diffrence = object.getBoolean("balanceSum");
            batchDataSumInfo.setDifference(diffrence);
            ArrayList<String> sourceKeys = new ArrayList<String>();
            for (int i = 0; i < object.getJSONArray("haveChooseRange").length(); ++i) {
                sourceKeys.add((String)object.getJSONArray("haveChooseRange").get(i));
            }
            batchDataSumInfo.setSourceKeys(sourceKeys);
            Map map = object.getJSONObject("dimensionSet").toMap();
            HashMap dimesionSet = new HashMap();
            for (Map.Entry stringMapEntry : map.entrySet()) {
                DimensionValue dimensionValue = new DimensionValue();
                HashMap dimesionMap = (HashMap)stringMapEntry.getValue();
                dimensionValue.setValue((String)dimesionMap.get("value"));
                dimensionValue.setName((String)dimesionMap.get("name"));
                dimensionValue.setType(((Integer)dimesionMap.get("type")).intValue());
                dimesionSet.put(stringMapEntry.getKey(), dimensionValue);
            }
            String periodDimension = object.getString("periodDimension");
            String period = object.getString("period");
            DataEntryInitParam param = new DataEntryInitParam();
            param.setTaskKey(task);
            FuncExecResult dataEntryEnv = SelectDataGatherPlanTask.this.iFuncExecuteService.getDataEntryEnv(param);
            Optional<FormSchemeResult> formSchemeDefineOptional = dataEntryEnv.getSchemes().stream().filter(e -> e.getScheme().getKey().equals(formScheme)).findFirst();
            FormSchemeResult formSchemeResult = formSchemeDefineOptional.get();
            Map<String, EntityViewData> keyToEntity = formSchemeResult.getScheme().getEntitys().stream().collect(Collectors.toMap(EntityViewData::getKey, e -> e, (key1, key2) -> key2));
            int periodConfig = object.getInt("periodConfig");
            int periodType = object.getInt("periodType");
            String entityViewKey = null;
            for (String key : keyToEntity.keySet()) {
                if (!keyToEntity.get(key).getDimensionName().equals("DATATIME")) continue;
                entityViewKey = keyToEntity.get(key).getKey();
            }
            period = SelectDataGatherPlanTask.this.planTaskUtil.getPeriod(task, period, periodConfig);
            DimensionValue dateTimeDimension = (DimensionValue)dimesionSet.get(periodDimension);
            dateTimeDimension.setValue(period);
            context.setDimensionSet(dimesionSet);
            String formChooseType = object.getString("formChooseType");
            String formTip = "\u5168\u8868";
            if (!"all".equals(formChooseType)) {
                StringBuffer formKeys = new StringBuffer();
                JSONArray forms = object.getJSONArray("forms");
                StringBuffer title = new StringBuffer();
                boolean add = false;
                for (Object form : forms) {
                    if (!(form instanceof JSONObject)) continue;
                    JSONObject formObj = (JSONObject)form;
                    String formKey = formObj.getString("formKey");
                    if (add) {
                        formKeys.append(";");
                    }
                    formKeys.append(formKey);
                    FormDefine formDefine = SelectDataGatherPlanTask.this.runTimeViewController.queryFormById(formKey);
                    title.append(formDefine.getTitle()).append(",");
                    add = true;
                }
                batchDataSumInfo.setFormKeys(formKeys.toString());
                if (title.length() > 0) {
                    formTip = title.substring(0, title.length() - 1);
                }
            }
            this.logs.append("\u6c47\u603b\u62a5\u8868\u4e3a:").append(formTip).append("\u3002\r\n");
            batchDataSumInfo.setContext(context);
            return batchDataSumInfo;
        }
    }
}

