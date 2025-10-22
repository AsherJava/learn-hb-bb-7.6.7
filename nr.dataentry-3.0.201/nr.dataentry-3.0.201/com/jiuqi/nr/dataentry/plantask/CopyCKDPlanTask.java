/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
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
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.dataentry.plantask;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.dataentry.copydes.CheckDesCopyParam;
import com.jiuqi.nr.dataentry.copydes.CopyDesResult;
import com.jiuqi.nr.dataentry.copydes.ICopyDesService;
import com.jiuqi.nr.dataentry.paramInfo.TaskOrgData;
import com.jiuqi.nr.dataentry.plantask.PlanTaskUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
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
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class CopyCKDPlanTask
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "6ff4cf59-d744-3e9a-266e-825235133286";
    private static final String JOB_TITLE = "\u540c\u6b65\u5ba1\u6838\u51fa\u9519\u8bf4\u660e";
    private static final String JOB_CONFIG_PAGE = "job-copyCkd";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ICopyDesService copyDesService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private PlanTaskUtil planTaskUtil;
    private static final Logger logger = LoggerFactory.getLogger(CopyCKDPlanTask.class);

    public String getJobCategoryId() {
        return JOB_ID;
    }

    public String getJobCategoryTitle() {
        return JOB_TITLE;
    }

    public JobExecutor createJobExecutor(String s) throws JobsException {
        return new CopyCKDExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new CopyCKDJobClassifier();
    }

    public IJobListener getJobListener() {
        return new CopyCKDJobListener();
    }

    public String getModelName() {
        return JOB_CONFIG_PAGE;
    }

    static class CopyCKDJobListener
    implements IJobListener {
        CopyCKDJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    static class CopyCKDJobClassifier
    implements IJobClassifier {
        CopyCKDJobClassifier() {
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

    static class EntityDim {
        private String entityType;
        private List<String> selected;

        EntityDim() {
        }

        public List<String> getSelected() {
            return this.selected;
        }

        public void setSelected(List<String> selected) {
            this.selected = selected;
        }

        public String getEntityType() {
            return this.entityType;
        }

        public void setEntityType(String entityType) {
            this.entityType = entityType;
        }
    }

    static class JobParam {
        private String taskKey;
        private String formSchemeKey;
        private String formulaSchemeKey;
        private String srcFormulaSchemeKey;
        private String adjustDate;
        private int periodConfig;
        private int periodType;
        private String period;
        private Map<String, EntityDim> entity;

        JobParam() {
        }

        public Map<String, EntityDim> getEntity() {
            return this.entity;
        }

        public void setEntity(Map<String, EntityDim> entity) {
            this.entity = entity;
        }

        public String getPeriod() {
            return this.period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getTaskKey() {
            return this.taskKey;
        }

        public void setTaskKey(String taskKey) {
            this.taskKey = taskKey;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public void setFormSchemeKey(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }

        public String getFormulaSchemeKey() {
            return this.formulaSchemeKey;
        }

        public void setFormulaSchemeKey(String formulaSchemeKey) {
            this.formulaSchemeKey = formulaSchemeKey;
        }

        public String getAdjustDate() {
            return this.adjustDate;
        }

        public void setAdjustDate(String adjustDate) {
            this.adjustDate = adjustDate;
        }

        public int getPeriodConfig() {
            return this.periodConfig;
        }

        public void setPeriodConfig(int periodConfig) {
            this.periodConfig = periodConfig;
        }

        public int getPeriodType() {
            return this.periodType;
        }

        public void setPeriodType(int periodType) {
            this.periodType = periodType;
        }

        public String getSrcFormulaSchemeKey() {
            return this.srcFormulaSchemeKey;
        }

        public void setSrcFormulaSchemeKey(String srcFormulaSchemeKey) {
            this.srcFormulaSchemeKey = srcFormulaSchemeKey;
        }
    }

    class CopyCKDExecutor
    extends JobExecutor {
        CopyCKDExecutor() {
        }

        public void execute(JobContext jobContext) throws JobExecutionException {
            JobParam jobParam;
            String user = jobContext.getJob().getUser();
            if (StringUtils.hasText(user)) {
                try {
                    NpContextImpl npContext = this.buildContext(user);
                    NpContextHolder.setContext((NpContext)npContext);
                }
                catch (JQException e) {
                    LogHelper.warn((String)"\u8ba1\u5212\u4efb\u52a1", (String)"\u6267\u884c\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u8ba1\u5212\u4efb\u52a1", (String)("\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage()));
                    jobContext.getDefaultLogger().error("\u8ba1\u5212\u4efb\u52a1", new Object[]{"\u6267\u884c\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u8ba1\u5212\u4efb\u52a1", "\u8bbe\u7f6e\u6267\u884c\u4eba\u2018" + user + "\u2019\u7684\u4fe1\u606f\u65f6\u53d1\u751f\u4e86\u5f02\u5e38\uff1a" + e.getMessage(), e});
                }
            }
            String runnerParameter = jobContext.getJob().getExtendedConfig();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                jobParam = (JobParam)objectMapper.readValue(runnerParameter, JobParam.class);
            }
            catch (JsonProcessingException e) {
                logger.error("\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:" + e.getMessage(), e);
                jobContext.getDefaultLogger().error("\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u8ba1\u5212\u4efb\u52a1\u53cd\u5e8f\u5217\u5316\u53c2\u6570\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
                return;
            }
            try {
                FormSchemeDefine formScheme = CopyCKDPlanTask.this.runTimeViewController.getFormScheme(jobParam.getFormSchemeKey());
                String period = CopyCKDPlanTask.this.planTaskUtil.getPeriod(jobParam.getTaskKey(), jobParam.getPeriod(), jobParam.getPeriodConfig());
                String periodDimensionName = CopyCKDPlanTask.this.periodEntityAdapter.getPeriodDimensionName();
                DimensionValue periodDimension = new DimensionValue();
                periodDimension.setName(periodDimensionName);
                periodDimension.setValue(period);
                CheckDesCopyParam checkDesCopyParam = new CheckDesCopyParam();
                checkDesCopyParam.setTargetFormSchemeKey(jobParam.getFormSchemeKey());
                checkDesCopyParam.setTargetFormulaSchemeKey(jobParam.getFormulaSchemeKey());
                HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
                dimensionValueMap.put(periodDimensionName, periodDimension);
                if (!CollectionUtils.isEmpty(jobParam.getEntity())) {
                    for (Map.Entry<String, EntityDim> e : jobParam.getEntity().entrySet()) {
                        DimensionValue dimensionValue = new DimensionValue();
                        dimensionValue.setName(e.getKey());
                        if ("all".equals(e.getValue().getEntityType())) {
                            dimensionValue.setValue("");
                        } else if ("choose".equals(e.getValue().getEntityType())) {
                            ArrayList selected = (ArrayList)e.getValue().getSelected();
                            if (!CollectionUtils.isEmpty(selected)) {
                                dimensionValue.setValue(String.join((CharSequence)";", selected));
                            }
                        } else {
                            dimensionValue.setValue(e.getValue().getEntityType());
                        }
                        dimensionValueMap.put(e.getKey(), dimensionValue);
                    }
                }
                if (CopyCKDPlanTask.this.formSchemeService.enableAdjustPeriod(jobParam.getFormSchemeKey())) {
                    DimensionValue adjustValue = new DimensionValue();
                    adjustValue.setName("ADJUST");
                    adjustValue.setValue(StringUtils.hasText(jobParam.getAdjustDate()) ? jobParam.getAdjustDate() : "0");
                    dimensionValueMap.put("ADJUST", adjustValue);
                }
                checkDesCopyParam.setTargetDimensionSet(dimensionValueMap);
                checkDesCopyParam.setSrcFormulaSchemeKey(jobParam.getSrcFormulaSchemeKey());
                JSONObject object = new JSONObject(runnerParameter);
                List<TaskOrgData> taskOrgDataList = CopyCKDPlanTask.this.planTaskUtil.queryTaskorgDataList(jobParam.getTaskKey());
                if (object.has("unitCorporate")) {
                    List unitCorporate;
                    DsContext dScontext = DsContextHolder.getDsContext();
                    DsContextImpl dsContext = (DsContextImpl)dScontext;
                    dsContext.setEntityId(object.getString("unitCorporate"));
                    if (taskOrgDataList.size() > 1 && (unitCorporate = taskOrgDataList.stream().filter(item -> item.getId().equals(object.getString("unitCorporate"))).collect(Collectors.toList())).size() > 0) {
                        jobContext.getDefaultLogger().info("\u540c\u6b65\u51fa\u9519\u8bf4\u660e\u53d6\u6570\u5355\u4f4d\u53e3\u5f84\u4e3a\uff1a\u3010" + ((TaskOrgData)unitCorporate.get(0)).getTitle() + "\u3011");
                    }
                }
                CopyDesResult copyDesResult = CopyCKDPlanTask.this.copyDesService.copy(checkDesCopyParam);
                if (logger.isDebugEnabled()) {
                    logger.debug("\u7cfb\u7edf\u65e5\u5fd7\uff1a");
                    logger.debug(copyDesResult.getSysLogText());
                    logger.debug("\u7528\u6237\u65e5\u5fd7\uff1a");
                    logger.debug(copyDesResult.getUsrLogText());
                }
                jobContext.getDefaultLogger().info(copyDesResult.getUsrLogText());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                jobContext.getDefaultLogger().error(e.getMessage(), (Throwable)e);
            }
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
            if (!StringUtils.hasText(userName)) {
                return null;
            }
            Optional user = CopyCKDPlanTask.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = CopyCKDPlanTask.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
        }

        private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
            NpContextIdentity identity = new NpContextIdentity();
            identity.setId(contextUser.getId());
            identity.setTitle(contextUser.getFullname());
            return identity;
        }

        private String getPeriod(String period, int periondConfig, int periodType, FormSchemeDefine formSchemeDefine, String periodEntityKey) {
            int customPeriod = 2;
            String periodValue = null;
            int offset = 0;
            if (periondConfig == customPeriod) {
                return period;
            }
            offset = periondConfig;
            if (periodType == 8) {
                IPeriodProvider periodProvider = CopyCKDPlanTask.this.periodEntityAdapter.getPeriodProvider(periodEntityKey);
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
                taskDefine = CopyCKDPlanTask.this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
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

