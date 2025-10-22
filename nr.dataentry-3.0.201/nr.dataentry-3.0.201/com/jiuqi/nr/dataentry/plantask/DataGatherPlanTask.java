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
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
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
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.asynctask.BatchDataSumAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.dataentry.plantask.PlanTaskUtil;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGatherPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "3ebd5ca5-03e7-4450-9225-f9f82fb8d4c2";
    private static final String JOB_TITLE = "\u8282\u70b9\u6c47\u603b";
    private static final String JOB_CONFIG_PAGE = "job-dataGather";
    private static final Logger logger = LoggerFactory.getLogger(DataGatherPlanTask.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private PlanTaskUtil planTaskUtil;

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new OperationalExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new OperationalPlanTaskJobClassifier();
    }

    public IJobListener getJobListener() {
        return new OperationalPlanTaskJobListener();
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    class OperationalPlanTaskJobListener
    implements IJobListener {
        OperationalPlanTaskJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class OperationalPlanTaskJobClassifier
    implements IJobClassifier {
        OperationalPlanTaskJobClassifier() {
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

    class OperationalExecutor
    extends JobExecutor {
        private JobContext jobContext;
        private StringBuffer logs;

        OperationalExecutor() {
        }

        public void execute(JobContext jobContext) throws JobExecutionException {
            this.jobContext = jobContext;
            this.logs = new StringBuffer();
            this.excute();
        }

        public boolean excute() {
            BatchDataSumInfo batchDataSumInfo;
            String runnerParameter = this.jobContext.getJob().getExtendedConfig();
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6279\u91cf\u6c47\u603b:\r\n");
            if (StringUtils.isBlank((CharSequence)runnerParameter)) {
                this.jobContext.getDefaultLogger().error("\u9ad8\u7ea7\u914d\u7f6e\u53c2\u6570\u4e3a\u7a7a\uff01");
                return false;
            }
            try {
                batchDataSumInfo = this.buildDataGatherInfo(runnerParameter);
            }
            catch (Exception e) {
                if (e instanceof NotFoundFormSchemeException) {
                    this.jobContext.getDefaultLogger().error("\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848\u6216\u8005\u6267\u884c\u7528\u6237\u5bf9\u62a5\u8868\u65b9\u6848\u6ca1\u6709\u6743\u9650\uff01");
                } else {
                    this.jobContext.getDefaultLogger().error(e.getMessage());
                }
                return false;
            }
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setTaskKey(batchDataSumInfo.getContext().getTaskKey());
            npRealTimeTaskInfo.setFormSchemeKey(batchDataSumInfo.getContext().getFormSchemeKey());
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchDataSumInfo)));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchDataSumAsyncTaskExecutor());
            String asyncTaskId = DataGatherPlanTask.this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_BATCHDATASUM.getName());
            boolean flag = this.executeLogsQuery(asyncTaskId);
            this.jobContext.getDefaultLogger().info(this.logs.toString());
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u6279\u91cf\u6c47\u603b\u6267\u884c\u5b8c\u6bd5\u3002\r\n");
            return flag;
        }

        private BatchDataSumInfo buildDataGatherInfo(String runnerParameter) throws Exception {
            BatchDataSumInfo batchDataSumInfo = new BatchDataSumInfo();
            JSONObject object = new JSONObject(runnerParameter);
            JtableContext context = new JtableContext();
            String task = object.getString("task");
            TaskDefine taskDefine = DataGatherPlanTask.this.runTimeViewController.queryTaskDefine(task);
            if (null == taskDefine) {
                throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u4efb\u52a1:" + task);
            }
            context.setTaskKey(task);
            this.logs.append("\u6c47\u603b\u4efb\u52a1\u4e3a\u3010").append(taskDefine.getTitle()).append("\u3011\r\n");
            List<TaskOrgData> taskOrgDataList = DataGatherPlanTask.this.planTaskUtil.queryTaskorgDataList(task);
            if (object.has("unitCorporate")) {
                List unitCorporate;
                DsContext dScontext = DsContextHolder.getDsContext();
                DsContextImpl dsContext = (DsContextImpl)dScontext;
                dsContext.setEntityId(object.getString("unitCorporate"));
                if (taskOrgDataList.size() > 1 && (unitCorporate = taskOrgDataList.stream().filter(item -> item.getId().equals(object.getString("unitCorporate"))).collect(Collectors.toList())).size() > 0) {
                    this.jobContext.getDefaultLogger().info("\u6c47\u603b\u53d6\u6570\u5355\u4f4d\u53e3\u5f84\u4e3a\uff1a\u3010" + ((TaskOrgData)unitCorporate.get(0)).getTitle() + "\u3011");
                }
            }
            String formScheme = object.getString("formScheme");
            FormSchemeDefine formSchemeDefine = DataGatherPlanTask.this.runTimeViewController.getFormScheme(formScheme);
            if (null == formSchemeDefine) {
                throw new RuntimeException("\u6ca1\u6709\u5728\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u4e0b\u627e\u5230\u62a5\u8868\u65b9\u6848\uff1a" + formScheme);
            }
            this.logs.append("\u6c47\u603b\u62a5\u8868\u65b9\u6848\u4e3a\u3010").append(formSchemeDefine.getTitle()).append("\u3011\r\n");
            context.setFormSchemeKey(formScheme);
            String sumRange = object.getString("sumRange");
            batchDataSumInfo.setRecursive("all".equalsIgnoreCase(sumRange));
            Boolean diffrence = object.getBoolean("balanceSum");
            batchDataSumInfo.setDifference(diffrence);
            String periodDimension = object.getString("periodDimension");
            String period = object.getString("period");
            DataEntryInitParam param = new DataEntryInitParam();
            param.setTaskKey(task);
            FuncExecResult dataEntryEnv = DataGatherPlanTask.this.iFuncExecuteService.getDataEntryEnv(param);
            Optional<FormSchemeResult> formSchemeDefineOptional = dataEntryEnv.getSchemes().stream().filter(e -> e.getScheme().getKey().equals(formScheme)).findFirst();
            FormSchemeResult formSchemeResult = formSchemeDefineOptional.get();
            Map<String, DimensionValue> dimensionSet = formSchemeResult.getDimensionSet();
            Map<String, EntityViewData> keyToEntity = formSchemeResult.getScheme().getEntitys().stream().collect(Collectors.toMap(EntityViewData::getKey, e -> e, (key1, key2) -> key2));
            int periodConfig = object.getInt("periodConfig");
            int periodType = object.getInt("periodType");
            String entityViewKey = null;
            for (String key : keyToEntity.keySet()) {
                if (!keyToEntity.get(key).getDimensionName().equals("DATATIME")) continue;
                entityViewKey = keyToEntity.get(key).getKey();
            }
            period = DataGatherPlanTask.this.planTaskUtil.getPeriod(task, period, periodConfig);
            DimensionValue dateTimeDimension = dimensionSet.get(periodDimension);
            dateTimeDimension.setValue(period);
            if (formSchemeResult.isOpenAdJustPeriod()) {
                DimensionValue adjustValue = new DimensionValue();
                adjustValue.setName("ADJUST");
                if (object.has("adjustDate") && !object.get("adjustDate").equals(null)) {
                    adjustValue.setValue(object.getString("adjustDate"));
                } else {
                    adjustValue.setValue("0");
                }
                dimensionSet.put("ADJUST", adjustValue);
            }
            JSONObject dimension = object.getJSONObject("dimensionSet");
            Map dimensionMap = dimension.toMap();
            for (Map.Entry entryMap : dimensionMap.entrySet()) {
                String key = (String)entryMap.getKey();
                Object value = entryMap.getValue();
                DimensionValue dimensionValue = dimensionSet.get(key);
                dimensionValue.setValue(String.valueOf(value));
            }
            context.setDimensionSet(dimensionSet);
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
                    FormDefine formDefine = DataGatherPlanTask.this.runTimeAuthViewController.queryFormById(formKey);
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

        private boolean executeLogsQuery(String taskId) {
            boolean executeResult = true;
            boolean flag = false;
            while (!flag) {
                TaskState taskState = DataGatherPlanTask.this.asyncTaskManager.queryTaskState(taskId);
                if (TaskState.FINISHED.equals((Object)taskState) || TaskState.ERROR.equals((Object)taskState)) {
                    flag = true;
                }
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    break;
                }
            }
            Object obj = DataGatherPlanTask.this.asyncTaskManager.queryDetail(taskId);
            return executeResult;
        }

        private User getUserByUserName(String userName) {
            if (StringUtils.isEmpty((CharSequence)userName)) {
                return null;
            }
            Optional user = DataGatherPlanTask.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = DataGatherPlanTask.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
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
                IPeriodProvider periodProvider = DataGatherPlanTask.this.periodEntityAdapter.getPeriodProvider(entityViewKey);
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
                taskDefine = DataGatherPlanTask.this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
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
    }
}

