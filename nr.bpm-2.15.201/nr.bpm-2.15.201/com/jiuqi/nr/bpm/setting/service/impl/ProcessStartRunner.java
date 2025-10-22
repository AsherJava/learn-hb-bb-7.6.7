/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
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
 *  com.jiuqi.np.asynctask.AsyncTaskManager
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
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.jiuqi.np.asynctask.AsyncTaskManager;
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
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.setting.bean.ProcessEntityParam;
import com.jiuqi.nr.bpm.setting.bean.ProcessParam;
import com.jiuqi.nr.bpm.setting.pojo.StartState;
import com.jiuqi.nr.bpm.setting.pojo.StateChangeObj;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.jobmanager.config.IJobAdvanceConfiguration;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessStartRunner
extends JobFactory
implements IJobAdvanceConfiguration {
    private static final String JOB_ID = "3ebd5ca5-58e0-4cf6-abf0-a97156229635";
    private static final String JOB_TITLE = "\u6d41\u7a0b\u542f\u52a8";
    private static final String JOB_CONFIG_PAGE = "job-process";
    private static final int CURRENT_PERIOD = 0;
    private static final int OFFSET_PERIOD = 1;
    private static final int ASSIGN_PERIOD = 2;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IEntityMetaService entityMetaService;

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
        return new ProcessExecutor();
    }

    public IJobClassifier getJobClassifier() {
        return new ProcessRunnerJobClassifier();
    }

    public IJobListener getJobListener() {
        return new ProcessRunnerJobListener();
    }

    class ProcessRunnerJobListener
    implements IJobListener {
        ProcessRunnerJobListener() {
        }

        public void beforeJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }

        public void afterJobDelete(JobListenerContext jobListenerContext) throws Exception {
        }
    }

    class ProcessRunnerJobClassifier
    implements IJobClassifier {
        ProcessRunnerJobClassifier() {
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

    class ProcessExecutor
    extends JobExecutor {
        private final Logger logger = LoggerFactory.getLogger(ProcessExecutor.class);
        private JobContext jobContext;

        ProcessExecutor() {
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
            this.jobContext.getDefaultLogger().info("\u8ba1\u5212\u6267\u884c\u6d41\u7a0b\u542f\u52a8:\r\n");
            String runnerParameter = this.jobContext.getJob().getExtendedConfig();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (runnerParameter != null) {
                    ProcessParam params = (ProcessParam)objectMapper.readValue(runnerParameter, ProcessParam.class);
                    DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
                    newContext.setEntityId(params.getContextEntityId());
                    DsContextHolder.setDsContext((DsContext)newContext);
                    String period = this.period(params);
                    this.log(params, period);
                    StateChangeObj stateChange = new StateChangeObj();
                    stateChange.setFormSchemeId(params.getFormScheme());
                    if (period != null) {
                        stateChange.setPeriod(period);
                        Map<String, ProcessEntityParam> entity = params.getEntity();
                        HashSet<String> entities = new HashSet<String>();
                        List processEntitys = entity.values().stream().collect(Collectors.toList());
                        for (ProcessEntityParam processEntityParam : processEntitys) {
                            if (processEntityParam.getSelected() == null) continue;
                            entities.addAll(processEntityParam.getSelected());
                        }
                        stateChange.setDataObj(entities);
                        if (entities.size() > 0) {
                            stateChange.setSelectAll(false);
                        } else {
                            stateChange.setSelectAll(true);
                        }
                        HashSet<String> forms = new HashSet<String>();
                        forms.addAll(params.getForms());
                        stateChange.setReportList(forms);
                        String formChooseType = params.getFormChooseType();
                        if ("all".equals(formChooseType)) {
                            stateChange.setReportAll(true);
                        }
                        stateChange.setStart(true);
                        stateChange.setContextEntityId(params.getContextEntityId());
                        AsyncTaskMonitor asyncTaskMonitor = this.buildAsyncTaskMonitor();
                        StartState startDataObjs = ProcessStartRunner.this.workflowSettingService.startDataObjs(stateChange, asyncTaskMonitor);
                        if (startDataObjs.getStarted().booleanValue()) {
                            this.jobContext.getDefaultLogger().info("\u6d41\u7a0b\u542f\u52a8\u5f02\u6b65\u4efb\u52a1\u6210\u529f\u3002\r\n");
                            return true;
                        }
                        this.jobContext.getDefaultLogger().info("\u6d41\u7a0b\u542f\u52a8\u5f02\u6b65\u4efb\u52a1\u62a5\u9519\u3002\r\n");
                        return false;
                    }
                    this.jobContext.getDefaultLogger().info("\u542f\u52a8\u65f6\u671f\u4e0d\u5728\u4efb\u52a1\u8d77\u59cb\u548c\u7ed3\u675f\u65f6\u95f4\u4e4b\u5185\u3002\r\n");
                    this.jobContext.getDefaultLogger().info("\u6d41\u7a0b\u542f\u52a8\u6267\u884c\u5b8c\u6bd5\u3002\r\n");
                    return true;
                }
                this.logger.info("\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u53c2\u6570\u4e3a\u7a7a,\u8bf7\u68c0\u67e5\u8ba1\u5212\u4efb\u52a1\u8bbe\u7f6e\uff01");
                return false;
            }
            catch (JsonProcessingException e) {
                this.logger.error(e.getMessage(), e);
                throw new RuntimeException();
            }
        }

        private String period(ProcessParam params) {
            String period = null;
            String taskKey = params.getTask();
            try {
                TaskDefine taskDefine = ProcessStartRunner.this.runTimeViewController.queryTaskDefine(taskKey);
                String fromPeriod = taskDefine.getFromPeriod();
                String toPeriod = taskDefine.getToPeriod();
                String periodTemp = params.getPeriod();
                int periodSort = params.getPeriodSort();
                int offsetValue = params.getOffsetValue();
                IPeriodProvider periodProvider = ProcessStartRunner.this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
                IPeriodRow periodRow = periodProvider.getCurPeriod();
                String curPeriod = periodRow.getCode();
                PeriodModifier periodModifier = new PeriodModifier();
                periodModifier.setPeriodModifier(offsetValue);
                String modifyPeriod = periodProvider.modify(curPeriod, periodModifier);
                if (0 == periodSort || 1 == periodSort) {
                    period = modifyPeriod;
                } else if (2 == periodSort) {
                    period = periodTemp;
                }
                PeriodWrapper currentPeriod = PeriodUtil.getPeriodWrapper((String)period);
                PeriodWrapper formPeriodWrapper = PeriodUtil.getPeriodWrapper((String)fromPeriod);
                PeriodWrapper toPeriodWrapper = PeriodUtil.getPeriodWrapper((String)toPeriod);
                if (formPeriodWrapper.getYear() < currentPeriod.getYear() && currentPeriod.getYear() < toPeriodWrapper.getYear()) {
                    return period;
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
            return period;
        }

        private boolean log(ProcessParam params, String period) {
            TaskDefine taskDefine = null;
            try {
                taskDefine = ProcessStartRunner.this.runTimeViewController.queryTaskDefine(params.getTask());
            }
            catch (Exception e) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u6d41\u7a0b\u542f\u52a8\u4efb\u52a1\u5f02\u5e38\uff0c\u7ec8\u6b62\u64cd\u4f5c\u3002");
                return false;
            }
            FormSchemeDefine formSchemeDefine = null;
            try {
                formSchemeDefine = ProcessStartRunner.this.runTimeViewController.getFormScheme(params.getFormScheme());
            }
            catch (Exception e) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c\u7ec8\u6b62\u64cd\u4f5c\u3002");
                return false;
            }
            if (taskDefine == null) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u4efb\u52a1\u4e3a\u7a7a\uff0c\u7ec8\u6b62\u64cd\u4f5c\u3002");
                return false;
            }
            if (formSchemeDefine == null) {
                this.jobContext.getDefaultLogger().error("\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3a\u7a7a\uff0c\u7ec8\u6b62\u64cd\u4f5c\u3002");
                return false;
            }
            this.jobContext.getDefaultLogger().info("\u4efb\u52a1\u4e3a\uff1a" + taskDefine.getTitle() + "\u3010" + taskDefine.getKey() + "\u3011\u3002\r\n");
            String contextEntityId = params.getContextEntityId();
            if (contextEntityId != null) {
                IEntityDefine iEntityDefine = ProcessStartRunner.this.entityMetaService.queryEntity(contextEntityId);
                this.jobContext.getDefaultLogger().info("\u5355\u4f4d\u53e3\u5f84\u4e3a: " + iEntityDefine.getTitle() + "\u3010" + contextEntityId + "\u3011\u3002\r\n");
            }
            this.jobContext.getDefaultLogger().info("\u62a5\u8868\u65b9\u6848\u4e3a\uff1a" + formSchemeDefine.getTitle() + "\u3010" + formSchemeDefine.getKey() + "\u3011\u3002\r\n");
            String periodTitle = ProcessStartRunner.this.periodEntityAdapter.getPeriodProvider(formSchemeDefine.getDateTime()).getPeriodTitle(period);
            this.jobContext.getDefaultLogger().info("\u65f6\u671f\u4e3a\uff1a" + periodTitle + "\u3002\r\n");
            return true;
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
            Optional user = ProcessStartRunner.this.userService.findByUsername(userName);
            if (user.isPresent()) {
                return (User)user.get();
            }
            Optional sysUser = ProcessStartRunner.this.systemUserService.findByUsername(userName);
            if (sysUser.isPresent()) {
                return (User)sysUser.get();
            }
            return null;
        }

        private AsyncTaskMonitor buildAsyncTaskMonitor() {
            AsyncTaskMonitor asyncTaskMonitor = new AsyncTaskMonitor(){
                private boolean finish = false;

                public void progressAndMessage(double progress, String message) {
                }

                public boolean isFinish() {
                    return this.finish;
                }

                public boolean isCancel() {
                    return this.finish;
                }

                public String getTaskPoolTask() {
                    return null;
                }

                public String getTaskId() {
                    return "job-plan-upload-start-2023";
                }

                public void finish(String result, Object detail) {
                    this.finish = true;
                }

                public void error(String result, Throwable t) {
                    this.finish = true;
                }

                public void canceling(String result, Object detail) {
                    this.finish = true;
                }

                public void canceled(String result, Object detail) {
                    this.finish = true;
                }
            };
            return asyncTaskMonitor;
        }
    }
}

