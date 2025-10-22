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
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
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
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
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
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
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
import com.jiuqi.nr.dataentry.bean.DataEntryInitParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.dataentry.plantask.PlanTaskUtil;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.dataentry.util.entityUtil.DataentryEntityUtils;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationalPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "453d0529-03e7-4450-9225-f9f82fb8d4c2";
    private static final String JOB_TITLE = "\u6279\u91cf\u8fd0\u7b97";
    private static final String JOB_CONFIG_PAGE = "job-batchOperational";
    private static final Logger logger = LoggerFactory.getLogger(OperationalPlanTask.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    private IFuncExecuteService iFuncExecuteService;
    @Autowired
    private IEntityViewRunTimeController runtimeController;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private DataentryEntityUtils dataentryEntityUtils;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    IBatchCalculateService batchCalculateService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceSession;
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

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void execute(JobContext jobContext) throws JobExecutionException {
            this.jobContext = jobContext;
            this.logs = new StringBuffer();
            NpContextImpl npContext = null;
            String user = jobContext.getJob().getUser();
            if (StringUtils.isEmpty((CharSequence)user)) {
                try {
                    npContext = this.buildContext(user);
                    NpContextHolder.setContext((NpContext)npContext);
                }
                catch (JQException e) {
                    String message = String.format("\u8bbe\u7f6e\u6267\u884c\u4eba\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a%s", e.getMessage());
                    this.jobContext.getDefaultLogger().info(message);
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u8ba1\u5212\u4efb\u52a1", (String)message);
                }
            }
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
            BatchCalculateInfo batchCalculateInfo;
            String runnerParameter = this.jobContext.getJob().getExtendedConfig();
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u6279\u91cf\u8fd0\u7b97:\r\n");
            if (StringUtils.isBlank((CharSequence)runnerParameter)) {
                this.jobContext.getDefaultLogger().error("\u9ad8\u7ea7\u914d\u7f6e\u53c2\u6570\u4e3a\u7a7a\uff01");
                return false;
            }
            try {
                batchCalculateInfo = this.buildCalculateInfo(runnerParameter);
                JSONObject object = new JSONObject(runnerParameter);
                List<TaskOrgData> taskOrgDataList = OperationalPlanTask.this.planTaskUtil.queryTaskorgDataList(batchCalculateInfo.getTaskKey());
                if (object.has("unitCorporate")) {
                    List unitCorporate;
                    DsContext dScontext = DsContextHolder.getDsContext();
                    DsContextImpl dsContext = (DsContextImpl)dScontext;
                    dsContext.setEntityId(object.getString("unitCorporate"));
                    if (taskOrgDataList.size() > 1 && (unitCorporate = taskOrgDataList.stream().filter(item -> item.getId().equals(object.getString("unitCorporate"))).collect(Collectors.toList())).size() > 0) {
                        this.jobContext.getDefaultLogger().info("\u6279\u91cf\u8fd0\u7b97\u53d6\u6570\u5355\u4f4d\u53e3\u5f84\u4e3a\uff1a\u3010" + ((TaskOrgData)unitCorporate.get(0)).getTitle() + "\u3011");
                    }
                }
            }
            catch (Exception e) {
                this.jobContext.getDefaultLogger().error(e.getMessage());
                return false;
            }
            String asyncTaskId = UUID.randomUUID().toString();
            SimpleAsyncProgressMonitor monitor = new SimpleAsyncProgressMonitor(asyncTaskId, OperationalPlanTask.this.cacheObjectResourceSession);
            OperationalPlanTask.this.batchCalculateService.batchCalculateForm(batchCalculateInfo, monitor);
            this.jobContext.getDefaultLogger().info(this.logs.toString());
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u4efb\u52a1\u6279\u91cf\u8fd0\u7b97\u6267\u884c\u5b8c\u6bd5\u3002\r\n");
            return true;
        }

        private BatchCalculateInfo buildCalculateInfo(String runnerParameter) throws Exception {
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            JSONObject object = new JSONObject(runnerParameter);
            String task = object.getString("task");
            TaskDefine taskDefine = OperationalPlanTask.this.runTimeViewController.queryTaskDefine(task);
            if (null == taskDefine) {
                throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u4efb\u52a1:" + task);
            }
            this.logs.append("\u8fd0\u7b97\u4efb\u52a1\u4e3a\u3010").append(taskDefine.getTitle()).append("\u3011\r\n");
            batchCalculateInfo.setTaskKey(task);
            String formScheme = object.getString("formScheme");
            FormSchemeDefine formSchemeDefine = OperationalPlanTask.this.runTimeViewController.getFormScheme(formScheme);
            if (null == formSchemeDefine) {
                throw new RuntimeException("\u6ca1\u6709\u5728\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u4e0b\u627e\u5230\u62a5\u8868\u65b9\u6848\uff1a" + formScheme);
            }
            this.logs.append("\u8fd0\u7b97\u62a5\u8868\u65b9\u6848\u4e3a\u3010").append(formSchemeDefine.getTitle()).append("\u3011\r\n");
            batchCalculateInfo.setFormSchemeKey(formScheme);
            JSONArray formulaScheme = object.getJSONArray("formulaScheme");
            StringBuffer formulaSchemeBuffer = new StringBuffer();
            StringBuffer formulaSchemeTitle = new StringBuffer();
            formulaSchemeTitle.append("\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u4e3a\uff1a");
            for (Object formulaSchemeDefine : formulaScheme) {
                String fsd = (String)formulaSchemeDefine;
                FormulaSchemeDefine formulaSchemeDefine1 = OperationalPlanTask.this.iFormulaRunTimeController.queryFormulaSchemeDefine(fsd);
                formulaSchemeTitle.append("\u3010").append(formulaSchemeDefine1.getTitle()).append("\u3011\uff0c");
                formulaSchemeBuffer.append(fsd).append(";");
            }
            this.logs.append(formulaSchemeTitle.substring(0, formulaSchemeTitle.length() - 1) + "\r\n");
            if (formulaSchemeBuffer.length() > 0) {
                String formulaSchemeDefine = formulaSchemeBuffer.substring(0, formulaSchemeBuffer.length() - 1);
                batchCalculateInfo.setFormulaSchemeKey(formulaSchemeDefine);
            }
            String periodDimension = object.getString("periodDimension");
            String period = object.getString("period");
            DataEntryInitParam param = new DataEntryInitParam();
            param.setTaskKey(task);
            FuncExecResult dataEntryEnv = OperationalPlanTask.this.iFuncExecuteService.getDataEntryEnv(param);
            Optional<FormSchemeResult> formSchemeDefineOptional = dataEntryEnv.getSchemes().stream().filter(e -> e.getScheme().getKey().equals(formScheme)).findFirst();
            FormSchemeResult formSchemeResult = formSchemeDefineOptional.get();
            Map<String, DimensionValue> dimensionSet = formSchemeResult.getDimensionSet();
            Map<String, EntityViewData> keyToEntity = formSchemeResult.getScheme().getEntitys().stream().collect(Collectors.toMap(EntityViewData::getKey, e -> e, (key1, key2) -> key2));
            int periodConfig = object.getInt("periodConfig");
            int periodType = object.getInt("periodType");
            String entityViewKey = null;
            for (String key3 : keyToEntity.keySet()) {
                if (!keyToEntity.get(key3).getDimensionName().equals("DATATIME")) continue;
                entityViewKey = keyToEntity.get(key3).getKey();
            }
            period = OperationalPlanTask.this.planTaskUtil.getPeriod(task, period, periodConfig);
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
            JSONObject entity = object.getJSONObject("entity");
            Map entityJsonObject = entity.toMap();
            entityJsonObject.forEach((key, obj) -> {
                EntityViewData entityViewData = (EntityViewData)keyToEntity.get(key);
                if (entityViewData != null) {
                    DimensionValue dimensionValue = (DimensionValue)dimensionSet.get(entityViewData.getDimensionName());
                    if (obj instanceof HashMap) {
                        Map entityData = (Map)obj;
                        String entityType = (String)entityData.get("entityType");
                        StringBuffer entityBuffer = new StringBuffer();
                        StringBuffer titleBuffer = new StringBuffer();
                        String tip = "\u5168\u91cf";
                        EntityViewDefine viewDefine = entityViewData.getEntityViewDefine();
                        List iEntityRows = new ArrayList();
                        try {
                            IEntityTable iEntityTable = OperationalPlanTask.this.dataentryEntityUtils.entDataQuerySet(formSchemeDefine, viewDefine, new DimensionValueSet(), AuthorityType.None);
                            iEntityRows = iEntityTable.getAllRows();
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        Map<String, String> entityMap = iEntityRows.stream().collect(Collectors.toMap(IEntityItem::getEntityKeyData, IEntityItem::getTitle, (i1, i2) -> i2));
                        if ("custom".equals(entityType)) {
                            List selected = (List)entityData.get("selected");
                            for (String entityKey : selected) {
                                entityBuffer.append(entityKey).append(";");
                                String title = entityMap.get(entity);
                                if (StringUtils.isBlank((CharSequence)title)) {
                                    title = entityKey;
                                }
                                titleBuffer.append(title).append(",");
                            }
                            if (entityBuffer.length() > 0) {
                                dimensionValue.setValue(entityBuffer.substring(0, entityBuffer.length() - 1));
                                tip = titleBuffer.substring(0, titleBuffer.length() - 1);
                            }
                        } else if (!"all".equals(entityType)) {
                            dimensionValue.setValue(entityType);
                        }
                        this.logs.append("\u53d6\u6570\u7ef4\u5ea6").append(entityViewData.getTitle()).append("\u3010").append(entityViewData.getDimensionName()).append("\u3011\u7684\u503c\u4e3a\uff1a").append(tip).append("\u3002\r\n");
                    }
                }
            });
            batchCalculateInfo.setDimensionSet(dimensionSet);
            String formChooseType = object.getString("formChooseType");
            String formTip = "\u5168\u8868";
            if (!"all".equals(formChooseType)) {
                JSONObject formulas = object.getJSONObject("formulas");
                HashMap<String, List<String>> formulaMap = new HashMap<String, List<String>>(formulas.length());
                StringBuffer title = new StringBuffer();
                for (Map.Entry formula : formulas.toMap().entrySet()) {
                    List formulaKeys = (List)formula.getValue();
                    formulaMap.put((String)formula.getKey(), formulaKeys);
                    FormDefine formDefine = OperationalPlanTask.this.runTimeAuthViewController.queryFormById((String)formula.getKey());
                    title.append(formDefine.getTitle()).append(",");
                }
                batchCalculateInfo.setFormulas(formulaMap);
                if (title.length() > 0) {
                    formTip = title.substring(0, title.length() - 1);
                }
            }
            this.logs.append("\u53d6\u6570\u62a5\u8868\u4e3a:").append(formTip).append("\u3002\r\n");
            return batchCalculateInfo;
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
                IPeriodProvider periodProvider = OperationalPlanTask.this.periodEntityAdapter.getPeriodProvider(entityViewKey);
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
                taskDefine = OperationalPlanTask.this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
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

        private boolean executeLogsQuery(String taskId) {
            boolean executeResult = true;
            boolean flag = false;
            while (!flag) {
                TaskState taskState = OperationalPlanTask.this.asyncTaskManager.queryTaskState(taskId);
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
            Object obj = OperationalPlanTask.this.asyncTaskManager.queryDetail(taskId);
            return executeResult;
        }

        private User getUserByUserName(String userName) {
            if (StringUtils.isEmpty((CharSequence)userName)) {
                return null;
            }
            Optional user = OperationalPlanTask.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = OperationalPlanTask.this.systemUserService.findByUsername(userName);
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
    }
}

