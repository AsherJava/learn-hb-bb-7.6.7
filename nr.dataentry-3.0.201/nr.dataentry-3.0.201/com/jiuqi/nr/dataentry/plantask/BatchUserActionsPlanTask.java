/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
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
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.exception.NrCommonException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.jtable.exception.NotFoundTaskException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  com.jiuqi.nvwa.login.domain.NvwaContextOrg
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.apache.commons.lang3.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.plantask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.exception.NrCommonException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IBatchWorkflowService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.web.WorkFlowExecuteParamTransfer;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.jtable.exception.NotFoundTaskException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchUserActionsPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceSession;
    @Autowired
    IBatchWorkflowService batchCommitFlowService;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    private static final String JOB_ID = "8fwf5df5-58e0-4rf8-abf0-a97156229935";
    private static final String JOB_TITLE = "\u6279\u91cf\u4e0a\u62a5";
    private static final String JOB_CONFIG_PAGE = "job-batchActions";
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private WorkFlowExecuteParamTransfer workFlowExecuteParamTransfer;

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new ActionsExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new ActionsJobClassifier();
    }

    public IJobListener getJobListener() {
        return new ActionsJobListener();
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

    private String getPeriod(BatchExecuteTaskParam batchExecuteTaskParam) throws Exception {
        List schemePeriodLinkDefineList;
        IPeriodProvider periodProvider;
        PeriodWrapper currentPeriod;
        TaskData taskData;
        block13: {
            Date dateAfterFormat;
            String beginPeriodModify;
            block14: {
                JtableContext context = batchExecuteTaskParam.getContext();
                taskData = this.dataEntryParamService.getRuntimeTaskByKey(context.getTaskKey());
                if (taskData == null) {
                    throw new NotFoundTaskException(new String[]{context.getTaskKey()});
                }
                currentPeriod = null;
                periodProvider = null;
                String periodViewKey = null;
                for (EntityViewData view : taskData.getEntitys()) {
                    if (!this.periodEntityAdapter.isPeriodEntity(view.getKey())) continue;
                    periodViewKey = view.getKey();
                    periodProvider = this.periodEntityAdapter.getPeriodProvider(view.getKey());
                }
                schemePeriodLinkDefineList = null;
                HashMap periodSchemeMap = new HashMap();
                schemePeriodLinkDefineList = this.iRunTimeViewController.querySchemePeriodLinkByTask(context.getTaskKey());
                Date date = DateTimeUtil.getDay();
                FillDateType fillingDateType = taskData.getFillingDateType();
                beginPeriodModify = null;
                if (fillingDateType.equals((Object)FillDateType.NONE) || taskData.getFillingDateDays() < 0) break block13;
                dateAfterFormat = date;
                if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                    dateAfterFormat = DateTimeUtil.getDateOfBeforeDay(date, taskData.getFillingDateDays() - 1);
                } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                    dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay(date, taskData.getFillingDateDays() - 1);
                }
                if (taskData.getPeriodType() == PeriodType.CUSTOM.type()) break block14;
                String periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskData.getPeriodType(), (Date)dateAfterFormat);
                beginPeriodModify = periodOfFormate;
                if (taskData.getPeriodOffset() == 0) break block13;
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(taskData.getPeriodOffset());
                beginPeriodModify = periodProvider.modify(beginPeriodModify, periodModifier);
                break block13;
            }
            if (taskData.getPeriodOffset() != 0) {
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                    String modify = schemePeriodLinkDefine.getPeriodKey();
                    if (taskData.getPeriodOffset() != 0) {
                        PeriodModifier periodModifier = new PeriodModifier();
                        periodModifier.setPeriodModifier(~taskData.getPeriodOffset());
                        modify = periodProvider.modify(modify, periodModifier);
                    }
                    FormSchemeDefine formScheme = this.runtimeView.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                    Date[] periodRegion = new Date[]{};
                    try {
                        periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(modify);
                    }
                    catch (ParseException e) {
                        throw new Exception("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    Date beginDate = periodRegion[0];
                    Date endDate = periodRegion[1];
                    if (!DateTimeUtil.isEffectiveDate(dateAfterFormat, beginDate, endDate)) continue;
                    beginPeriodModify = schemePeriodLinkDefine.getPeriodKey();
                    break;
                }
            }
        }
        List schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList.stream().sorted((periodOne, periodTwo) -> PeriodUtils.comparePeriod((String)periodOne.getPeriodKey(), (String)periodTwo.getPeriodKey())).collect(Collectors.toList());
        if (taskData.getPeriodType() == PeriodType.CUSTOM.type()) {
            ArrayList<CustomPeriodData> customPeriodDatas = new ArrayList<CustomPeriodData>();
            for (IPeriodRow periodRow : periodProvider.getPeriodItems()) {
                CustomPeriodData data = new CustomPeriodData();
                data.setCode(periodRow.getCode());
                data.setTitle(periodRow.getTitle());
                customPeriodDatas.add(data);
            }
            IPeriodRow curPeriod = periodProvider.getCurPeriod();
            PeriodModifier periodModifier = new PeriodModifier();
            periodModifier.setPeriodModifier(taskData.getPeriodOffset());
            String modify = periodProvider.modify(curPeriod.getCode(), periodModifier);
            currentPeriod = new PeriodWrapper(modify);
        } else {
            currentPeriod = DataEntryUtil.getCurrPeriod(taskData.getPeriodType(), taskData.getPeriodOffset(), taskData.getFromPeriod(), taskData.getToPeriod());
        }
        String currPeriodString = currentPeriod.toString();
        return currPeriodString;
    }

    private String getPeriod(String period, int periondConfig, int periodType, FormSchemeDefine formSchemeDefine, String periodEntityKey, String currentPeriod) {
        int customPeriod = 2;
        String periodValue = null;
        int offset = 0;
        if (periondConfig == customPeriod) {
            return period;
        }
        offset = periondConfig;
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(periodEntityKey);
        List periodItems = periodProvider.getPeriodItems();
        for (int i = 0; i < periodItems.size(); ++i) {
            if (!currentPeriod.equals(((IPeriodRow)periodItems.get(i)).getCode())) continue;
            if (offset + i < 0 || offset + i > periodItems.size() - 1) {
                StringBuffer buffer = new StringBuffer("\u81ea\u5b9a\u4e49\u65f6\u671f\u504f\u79fb\u91cf\u8bbe\u7f6e\u9519\u8bef\uff1a").append("\u5f53\u524d\u671f\u7684\u5f00\u59cb\u65f6\u95f4\u4e3a:").append(((IPeriodRow)periodItems.get(i)).getStartDate()).append("\u7ed3\u675f\u65f6\u95f4\u4e3a:").append(((IPeriodRow)periodItems.get(i)).getEndDate()).append(",").append("\u53d6\u6570\u671f'").append(offset == -1 ? "\u4e0a\u4e00\u671f'," : "\u4e0b\u4e00\u671f',").append(offset == -1 ? "\u5c0f\u4e8e\u6700\u5c0f\u81ea\u5b9a\u4e49\u671f" : "\u8d85\u51fa\u6700\u5927\u81ea\u5b9a\u671f");
                throw new IllegalArgumentException(buffer.toString());
            }
            periodValue = ((IPeriodRow)periodItems.get(offset + i)).getCode();
            break;
        }
        return periodValue;
    }

    private PeriodWrapper getCurrPeriod(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine = null;
        try {
            taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
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
        ActionsExecutor() {
        }

        public User getUserByUserName(String userName) {
            if (StringUtils.isEmpty((CharSequence)userName)) {
                return null;
            }
            Optional user = BatchUserActionsPlanTask.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = BatchUserActionsPlanTask.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
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
                OrgDO orgDO = BatchUserActionsPlanTask.this.orgDataClient.get(orgDTO);
                NvwaContextOrg nvwaContextOrg = new NvwaContextOrg();
                nvwaContextOrg.setCode(orgDO.getCode());
                nvwaContextOrg.setName(orgDO.getName());
                npContext.setOrganization((ContextOrganization)nvwaContextOrg);
            }
            return npContext;
        }

        private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(contextUser.getId());
            identity.setOrgCode(contextUser.getOrgCode());
            identity.setTitle(contextUser.getFullname() + "\u3010\u5f53\u524d\u7528\u6237\u3011");
            return identity;
        }

        protected NpContextUser buildUserContext(String userName) throws JQException {
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void execute(JobContext jobContext) throws JobExecutionException {
            String user = jobContext.getJob().getUser();
            NpContextImpl npContext = null;
            if (StringUtils.isEmpty((CharSequence)user)) {
                jobContext.getDefaultLogger().error("\u6267\u884c\u7528\u6237\u672a\u6307\u5b9a\uff0c\u8bf7\u6307\u5b9a\u6267\u884c\u7528\u6237\u3002");
                throw new JobExecutionException("\u6267\u884c\u7528\u6237\u672a\u6307\u5b9a\uff0c\u8bf7\u6307\u5b9a\u6267\u884c\u7528\u6237\u3002");
            }
            if (!StringUtils.isEmpty((CharSequence)user)) {
                try {
                    npContext = this.buildContext(user);
                    NpContextHolder.setContext((NpContext)npContext);
                }
                catch (JQException e) {
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
                }
            }
            String errorInfo = "task_error_info";
            String cancelInfo = "task_cancel_info";
            LogInfo logInfo = null;
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncProgressMonitor monitor = new SimpleAsyncProgressMonitor(taskId, BatchUserActionsPlanTask.this.cacheObjectResourceSession);
            String runnerParameter = jobContext.getJob().getExtendedConfig();
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                BatchExecuteTaskParam batchExecuteTaskParam = (BatchExecuteTaskParam)((Object)mapper.readValue(runnerParameter, (TypeReference)new TypeReference<BatchExecuteTaskParam>(){}));
                JSONObject jsonObject = new JSONObject(runnerParameter);
                int periodConfig = jsonObject.getInt("periodConfig");
                int periodType = jsonObject.getInt("periodType");
                int periodOffset = jsonObject.getInt("periodOffset");
                FormSchemeDefine formSchemeDefine = null;
                try {
                    formSchemeDefine = BatchUserActionsPlanTask.this.runtimeView.getFormScheme(batchExecuteTaskParam.getContext().getFormSchemeKey());
                }
                catch (Exception e) {
                    jobContext.getDefaultLogger().error("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                    throw new JobExecutionException("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                }
                String currentPeriod = BatchUserActionsPlanTask.this.getPeriod(batchExecuteTaskParam);
                jobContext.getDefaultLogger().info("\u5f53\u524d\u65e5\u671f" + currentPeriod);
                String period = BatchUserActionsPlanTask.this.getPeriod(((DimensionValue)batchExecuteTaskParam.getContext().getDimensionSet().get("DATATIME")).getValue(), periodConfig, periodType, formSchemeDefine, formSchemeDefine.getDateTime(), currentPeriod);
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName("DATATIME");
                dimensionValue.setValue(period);
                if (jsonObject.has("periodType")) {
                    dimensionValue.setType(((Integer)jsonObject.get("periodType")).intValue());
                }
                batchExecuteTaskParam.getContext().getDimensionSet().put("DATATIME", dimensionValue);
                BatchExecuteParam executeParam = new BatchExecuteParam();
                executeParam.setActionId(batchExecuteTaskParam.getActionId());
                executeParam.setFormSchemeKey(batchExecuteTaskParam.getContext().getFormSchemeKey());
                executeParam.setFormKey(batchExecuteTaskParam.getContext().getFormKey());
                executeParam.setGroupKey(batchExecuteTaskParam.getContext().getFormGroupKey());
                Map dimensionSet = batchExecuteTaskParam.getContext().getDimensionSet();
                ActionParam actionParam = BatchUserActionsPlanTask.this.dataFlowService.actionParam(executeParam, dimensionSet);
                if (batchExecuteTaskParam.isForceCommit() && !actionParam.isForceCommit()) {
                    jobContext.getDefaultLogger().warn("\u6279\u91cf\u4e0a\u62a5\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f,\u8be5\u89d2\u8272\u6ca1\u6709\u5f3a\u5236\u4e0a\u62a5\u6743\u9650\u6216\u4efb\u52a1\u5c5e\u6027\u5df2\u5173\u95ed\u5f3a\u5236\u4e0a\u62a5,\u5df2\u81ea\u52a8\u53d6\u6d88\u5f3a\u5236\u4e0a\u62a5");
                    batchExecuteTaskParam.setForceCommit(false);
                }
                if (!batchExecuteTaskParam.isForceCommit() && actionParam.isForceCommit()) {
                    batchExecuteTaskParam.setForceCommit(false);
                }
                if (batchExecuteTaskParam.getComment() != null && batchExecuteTaskParam.getComment().length() > 0 && !actionParam.isNeedOptDesc()) {
                    jobContext.getDefaultLogger().warn("\u4efb\u52a1\u5c5e\u6027\u672a\u914d\u7f6e\u586b\u5199\u4e0a\u62a5\u8bf4\u660e,\u4e0e\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u4e0d\u7b26,\u5df2\u53d6\u6d88\u4e0a\u62a5\u8bf4\u660e");
                    batchExecuteTaskParam.setComment("");
                }
                if ((batchExecuteTaskParam.getComment() == null || "".equals(batchExecuteTaskParam.getComment())) && actionParam.isNeedOptDesc()) {
                    throw new JobExecutionException("\u4efb\u52a1\u5c5e\u6027\u5df2\u914d\u7f6e\u586b\u5199\u4e0a\u62a5\u8bf4\u660e,\u8bf7\u5728\u8ba1\u5212\u4efb\u52a1\u914d\u7f6e\u4e0a\u62a5\u8bf4\u660e\u91cd\u65b0\u6267\u884c");
                }
                String planTaskWorkflowType = jsonObject.getString("workflowType");
                String actionWorkflowType = null;
                switch (formSchemeDefine.getFlowsSetting().getWordFlowType()) {
                    case ENTITY: {
                        actionWorkflowType = "ENTITY";
                        break;
                    }
                    case FORM: {
                        actionWorkflowType = "FORM";
                        break;
                    }
                    case GROUP: {
                        actionWorkflowType = "GROUP";
                    }
                }
                if (!planTaskWorkflowType.equals(actionWorkflowType)) {
                    throw new JobExecutionException("\u4efb\u52a1\u5c5e\u6027\u4e0a\u62a5\u4e3b\u4f53\u5df2\u6539\u4e3a " + actionWorkflowType + " ,\u4e0e\u8ba1\u5212\u4efb\u52a1 " + planTaskWorkflowType + " \u4e0d\u540c,\u8bf7\u91cd\u65b0\u914d\u7f6e\u8ba1\u5212\u4efb\u52a1\u5e76\u6267\u884c");
                }
                if (BatchUserActionsPlanTask.this.workflow.isDefaultWorkflow(batchExecuteTaskParam.getContext().getFormSchemeKey())) {
                    batchExecuteTaskParam.setActionId("act_upload");
                } else {
                    batchExecuteTaskParam.setActionId("cus_upload");
                }
                BatchUserActionsPlanTask.this.workFlowExecuteParamTransfer.checkBatchExecuteTaskParam(batchExecuteTaskParam);
                logInfo = BatchUserActionsPlanTask.this.batchCommitFlowService.batchExecuteTask(batchExecuteTaskParam, monitor);
                jobContext.getDefaultLogger().info(logInfo.getLogInfo());
                if (monitor.isCancel()) {
                    monitor.canceled(cancelInfo, cancelInfo);
                }
            }
            catch (NrCommonException nrCommonException) {
                monitor.error(errorInfo, nrCommonException);
                jobContext.getDefaultLogger().error("\u51fa\u9519\u539f\u56e0\uff1a" + nrCommonException.getMessage(), (Throwable)nrCommonException);
            }
            catch (Exception e) {
                jobContext.getDefaultLogger().error(errorInfo, (Throwable)e);
                monitor.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                jobContext.setResult(-100, "\u4efb\u52a1\u5931\u8d25");
            }
            finally {
                if (logInfo != null) {
                    LogHelper.info((String)"\u8ba1\u5212\u4efb\u52a1", (String)("\u6267\u884c\u6279\u91cf" + logInfo.getActionName()), (String)logInfo.getLogInfo());
                }
                if (npContext != null) {
                    NpContextHolder.clearContext();
                }
            }
        }
    }
}

