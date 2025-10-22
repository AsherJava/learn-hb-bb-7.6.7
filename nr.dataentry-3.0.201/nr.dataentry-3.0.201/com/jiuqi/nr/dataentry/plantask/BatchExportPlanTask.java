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
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.apache.commons.lang3.StringUtils
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
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.bean.BatchExportInfo;
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.dataentry.plantask.PlanTaskUtil;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class BatchExportPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "f8fa0efa-1034-41bf-9592-5c15ff18d37b";
    private static final String JOB_TITLE = "\u6279\u91cf\u5bfc\u51fa";
    private static final String JOB_CONFIG_PAGE = "job-batchExport";
    private final IBatchExportService exportService;
    private CacheObjectResourceRemote cacheObjectResourceSession;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private PlanTaskUtil planTaskUtil;

    public User getUserByUserName(String userName) {
        if (StringUtils.isEmpty((CharSequence)userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        if (sysUser.isPresent()) {
            return (User)sysUser.get();
        }
        return null;
    }

    public BatchExportPlanTask(IBatchExportService exportService, CacheObjectResourceRemote cacheObjectResourceSession) {
        this.exportService = exportService;
        this.cacheObjectResourceSession = cacheObjectResourceSession;
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

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new BatchExportPlanTaskExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new BatchExportJobClassifier();
    }

    public IJobListener getJobListener() {
        return new BatchExportJobListener();
    }

    private String getPeriod(String period, int periondConfig, int periodType, FormSchemeDefine formSchemeDefine, String entityViewKey) {
        int customPeriod = 2;
        String periodValue = null;
        int offset = 0;
        if (periondConfig == customPeriod) {
            return period;
        }
        offset = periondConfig;
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
            taskDefine = this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
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

    class BatchExportJobListener
    implements IJobListener {
        BatchExportJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class BatchExportJobClassifier
    implements IJobClassifier {
        BatchExportJobClassifier() {
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

    class BatchExportPlanTaskExecutor
    extends JobExecutor {
        BatchExportPlanTaskExecutor() {
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
                catch (JQException e2) {
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e2.getMessage()));
                }
            }
            String runnerParameter = jobContext.getJob().getExtendedConfig();
            BatchExportInfo exportInfo = new BatchExportInfo();
            JtableContext jtableContext = new JtableContext();
            try {
                JSONObject jsonObject = new JSONObject(runnerParameter);
                String taskKey = jsonObject.getString("taskKey");
                String formSchemeKey = jsonObject.getString("formSchemeKey");
                String entity = jsonObject.getString("entityName");
                String period = jsonObject.getString("period");
                String url = jsonObject.getString("saveUrl");
                List<TaskOrgData> taskOrgDataList = BatchExportPlanTask.this.planTaskUtil.queryTaskorgDataList(taskKey);
                if (jsonObject.has("unitCorporate")) {
                    List unitCorporate;
                    DsContext context = DsContextHolder.getDsContext();
                    DsContextImpl dsContext = (DsContextImpl)context;
                    dsContext.setEntityId(jsonObject.getString("unitCorporate"));
                    if (taskOrgDataList.size() > 1 && (unitCorporate = taskOrgDataList.stream().filter(item -> item.getId().equals(jsonObject.getString("unitCorporate"))).collect(Collectors.toList())).size() > 0) {
                        jobContext.getDefaultLogger().info("\u6279\u91cf\u5bfc\u51fa\u53d6\u6570\u5355\u4f4d\u53e3\u5f84\u4e3a\uff1a\u3010" + ((TaskOrgData)unitCorporate.get(0)).getTitle() + "\u3011");
                    }
                }
                JSONObject entityJsonObject = jsonObject.getJSONObject("entity");
                Map entityMap = entityJsonObject.toMap();
                HashMap<String, DimensionValue> tempDimensionSet = new HashMap<String, DimensionValue>();
                for (String s : entityMap.keySet()) {
                    Map s1 = (Map)entityMap.get(s);
                    DimensionValue tempdim = new DimensionValue();
                    tempdim.setName(s);
                    tempdim.setType(0);
                    if (s1.get("entityType").equals("choose")) {
                        String tempSelect = "";
                        ArrayList selected = (ArrayList)s1.get("selected");
                        for (int i = 0; i < selected.size(); ++i) {
                            tempSelect = tempSelect + (String)selected.get(i);
                            if (i >= selected.size() - 1) continue;
                            tempSelect = tempSelect + ";";
                        }
                        tempdim.setValue(tempSelect);
                    } else {
                        tempdim.setValue((String)s1.get("entityType"));
                        if (((String)s1.get("entityType")).equals("all")) {
                            tempdim.setValue("");
                        }
                    }
                    tempDimensionSet.put(s, tempdim);
                }
                jtableContext.setTaskKey(taskKey);
                jtableContext.setFormSchemeKey(formSchemeKey);
                if (jsonObject.has("formKeys")) {
                    String formKey = jsonObject.getString("formKeys");
                    jtableContext.setFormKey(formKey);
                } else {
                    jtableContext.setFormKey("");
                }
                String type = jsonObject.getString("type");
                exportInfo.setFileType(type);
                if ("EXPORT_BATCH_EXCEL".equals(type)) {
                    exportInfo.setExcelType("zip");
                    exportInfo.setCompressionType("zip");
                } else if ("EXPORT_BATCH_JIO".equals(type)) {
                    exportInfo.setCompressionType("zip");
                    String configKey = jsonObject.getString("configKey");
                    exportInfo.setConfigKey(configKey);
                } else {
                    throw new JSONException("Not supported export type");
                }
                int periodConfig = jsonObject.getInt("periodConfig");
                int periodType = jsonObject.getInt("periodType");
                FormSchemeDefine formSchemeDefine = null;
                try {
                    formSchemeDefine = BatchExportPlanTask.this.runtimeView.getFormScheme(formSchemeKey);
                }
                catch (Exception e3) {
                    jobContext.getDefaultLogger().error("\u83b7\u53d6\u53d6\u6570\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c\u7ec8\u6b62\u53d6\u6570\u3002");
                    if (npContext != null) {
                        NpContextHolder.clearContext();
                    }
                    return;
                }
                DataEntryInitParam param = new DataEntryInitParam();
                param.setTaskKey(taskKey);
                FuncExecResult dataEntryEnv = null;
                try {
                    dataEntryEnv = BatchExportPlanTask.this.iFuncExecuteService.getDataEntryEnv(param);
                }
                catch (Exception e4) {
                    throw new JSONException(e4.getMessage());
                }
                Optional<FormSchemeResult> formScheme = dataEntryEnv.getSchemes().stream().filter(e -> e.getScheme().getKey().equals(formSchemeKey)).findFirst();
                if (!formScheme.isPresent()) {
                    jobContext.getDefaultLogger().error("\u53d6\u6570\u53c2\u6570\u4e2d\u7684\u62a5\u8868\u65b9\u6848" + formSchemeKey + "\u4e0d\u5b58\u5728\u3002\r\n");
                    return;
                }
                FormSchemeResult formSchemeResult = formScheme.get();
                Map<String, EntityViewData> keyToEntity = formSchemeResult.getScheme().getEntitys().stream().collect(Collectors.toMap(EntityViewData::getKey, e -> e, (key1, key2) -> key2));
                String entityViewKey = null;
                for (String key : keyToEntity.keySet()) {
                    if (!keyToEntity.get(key).getDimensionName().equals("DATATIME")) continue;
                    entityViewKey = keyToEntity.get(key).getKey();
                }
                if (period != null) {
                    DimensionValue dimensionValue = new DimensionValue();
                    dimensionValue.setName("DATATIME");
                    period = BatchExportPlanTask.this.planTaskUtil.getPeriod(taskKey, period, periodConfig);
                    dimensionValue.setValue(period);
                    if (jsonObject.has("periodType")) {
                        dimensionValue.setType(((Integer)jsonObject.get("periodType")).intValue());
                    }
                    tempDimensionSet.put("DATATIME", dimensionValue);
                }
                if (formSchemeResult.isOpenAdJustPeriod()) {
                    DimensionValue adjustValue = new DimensionValue();
                    adjustValue.setName("ADJUST");
                    if (jsonObject.has("adjustDate") && !jsonObject.get("adjustDate").equals(null)) {
                        adjustValue.setValue(jsonObject.getString("adjustDate"));
                    } else {
                        adjustValue.setValue("0");
                    }
                    tempDimensionSet.put("ADJUST", adjustValue);
                }
                period = BatchExportPlanTask.this.planTaskUtil.getPeriod(taskKey, period, periodConfig);
                jobContext.getDefaultLogger().info("\u53d6\u6570\u65f6\u671f\u4e3a\uff1a" + period);
                jtableContext.setDimensionSet(tempDimensionSet);
                exportInfo.setContext(jtableContext);
                if (jsonObject.has("excelSettings")) {
                    List excelSettings = jsonObject.getJSONArray("excelSettings").toList();
                    exportInfo.setCellBackGround(excelSettings.contains("cellBackGround"));
                    exportInfo.setEmptyTable(excelSettings.contains("emptyTable"));
                    exportInfo.setCreateFileByReport(excelSettings.contains("createFileByReport"));
                    exportInfo.setArithmeticBackground(excelSettings.contains("arithmeticBackground"));
                    exportInfo.setExportEmptyTable(excelSettings.contains("exportEmptyTable"));
                    exportInfo.setExportZero(excelSettings.contains("exportZero"));
                }
                if (StringUtils.isNotBlank((CharSequence)url)) {
                    exportInfo.setLocation(url);
                }
                exportInfo.setDownLoadKey(UUID.randomUUID().toString());
                exportInfo.setSpecifyPath(true);
                this.batchExport(exportInfo);
            }
            catch (Exception e5) {
                jobContext.getDefaultLogger().debug("\u8ba1\u5212\u4efb\u52a1\u6279\u91cf\u5bfc\u51fa\u6267\u884c\u5931\u8d25", (Throwable)e5);
            }
            finally {
                if (npContext != null) {
                    NpContextHolder.clearContext();
                }
            }
        }

        public NpContextImpl buildContext(String userName) throws JQException {
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

        protected NpContextUser buildUserContext(String userName) throws JQException {
            NpContextUser userContext = new NpContextUser();
            User user = BatchExportPlanTask.this.getUserByUserName(userName);
            if (user == null) {
                throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
            }
            userContext.setId(user.getId());
            userContext.setName(user.getName());
            userContext.setNickname(user.getNickname());
            userContext.setDescription(user.getDescription());
            return userContext;
        }

        private void batchExport(BatchExportInfo context) throws Exception {
            String taskId = UUID.randomUUID().toString();
            SimpleAsyncProgressMonitor monitor = new SimpleAsyncProgressMonitor(taskId, BatchExportPlanTask.this.cacheObjectResourceSession);
            BatchExportPlanTask.this.exportService.export(context, monitor);
        }
    }
}

