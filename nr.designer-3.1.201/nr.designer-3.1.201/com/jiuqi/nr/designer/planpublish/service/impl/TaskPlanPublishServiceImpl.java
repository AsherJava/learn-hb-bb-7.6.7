/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent
 *  com.jiuqi.nr.definition.deploy.DeployTaskBeforeEvent
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService
 *  com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.quartz.CronScheduleBuilder
 *  org.quartz.JobBuilder
 *  org.quartz.JobDataMap
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.ScheduleBuilder
 *  org.quartz.Scheduler
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.designer.planpublish.service.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.definition.deploy.DeployRefreshFormulaEvent;
import com.jiuqi.nr.definition.deploy.DeployTaskBeforeEvent;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.planpublish.common.PublishStatus;
import com.jiuqi.nr.designer.planpublish.common.WorkStatus;
import com.jiuqi.nr.designer.planpublish.job.JobCommon;
import com.jiuqi.nr.designer.planpublish.job.TaskPlanPublishJob;
import com.jiuqi.nr.designer.planpublish.model.ChangePlanPublishDate;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;
import com.jiuqi.nr.designer.planpublish.service.SendMessageService;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService;
import com.jiuqi.nr.designer.planpublish.util.DateUtils;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.service.impl.DeployManager;
import com.jiuqi.nr.designer.service.impl.ProgressLoadingServiceImpl;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.FormData;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.rest.vo.DesignRestVO;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskPlanPublishServiceImpl
implements TaskPlanPublishService {
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private ProgressLoadingServiceImpl progressLoadingServiceImpl;
    @Autowired
    private DeployManager deployManager;
    @Autowired
    private IViewDeployController deployController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private IDataDefinitionDesignTimeController iDataDefinitionDesignTimeController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DesignDataLinkMappingDefineService designDataLinkMappingDefineService;
    @Autowired(required=false)
    private List<DeployTaskBeforeEvent> deployTaskBeforeEvents;

    @Override
    public void planPublishTask(String taskID, String deployTaskID) throws Exception {
        String userId = NpContextHolder.getContext().getUserId();
        ProgressItem progress = this.progressCacheService.getProgress(taskID);
        if (progress != null && !progress.isFinished()) {
            this.deployManager.addDeployTask(deployTaskID, taskID);
            throw new Exception("\u8be5\u4efb\u52a1\u6b63\u5728\u53d1\u5e03");
        }
        this.progressCacheService.removeProgress(taskID);
        this.deployManager.addDeployTask(deployTaskID, taskID);
        String publishTime = DateUtils.getCurrDate();
        String publishStatus = PublishStatus.PUBLISHING.toString();
        String planPublishKey = this.beforeTaskPublish(taskID, publishTime, publishStatus, null);
        try {
            this.checkAccountForm(taskID);
            this.deployTaskBrfore(taskID);
            this.deployController.deployTask(taskID);
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskID);
            ArrayList runTimeFormulaSchemeDefines = new ArrayList();
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                runTimeFormulaSchemeDefines.addAll(this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey()));
            }
            ArrayList<String> runTimeFormulaSchemeKey = new ArrayList<String>();
            for (FormulaSchemeDefine formulaScheme : runTimeFormulaSchemeDefines) {
                runTimeFormulaSchemeKey.add(formulaScheme.getKey());
            }
            this.applicationContext.publishEvent((ApplicationEvent)new DeployRefreshFormulaEvent(runTimeFormulaSchemeKey));
            this.progressLoadingServiceImpl.deleteProgressLoading(taskID);
            this.progressLoadingServiceImpl.publishSuccess(taskID, userId);
            this.afterTaskPublish(planPublishKey, taskID, false, null);
        }
        catch (Exception e) {
            String message = e.getMessage();
            ProgressItem progress2 = this.progressCacheService.getProgress(taskID);
            if (progress2 == null) {
                progress2 = new ProgressItem();
            }
            progress2.setFailed(true);
            progress2.setFinished(true);
            progress2.setMessage(e.getMessage());
            progress2.setProgressId(taskID);
            this.progressCacheService.setProgress(taskID, progress2);
            this.progressLoadingServiceImpl.deleteProgressLoading(taskID);
            this.afterTaskPublish(planPublishKey, taskID, true, e.getMessage());
            this.progressLoadingServiceImpl.publishFail(taskID, userId, message == null ? "" : (message.length() > 2000 ? message.substring(0, 2000) : message), CommonHelper.printStackTraceToString(e));
            throw new Exception(e.getMessage(), e);
        }
    }

    private void deployTaskBrfore(String taskID) throws Exception {
        if (null != this.deployTaskBeforeEvents) {
            for (DeployTaskBeforeEvent deployTaskBeforeEvent : this.deployTaskBeforeEvents) {
                deployTaskBeforeEvent.execute(taskID);
            }
        }
    }

    private void checkAccountForm(String taskId) throws Exception {
        List designFormSchemeDefines = this.nrDesignTimeController.queryFormSchemeByTask(taskId);
        for (DesignFormSchemeDefine designFormSchemeDefine : designFormSchemeDefines) {
            List forms = this.nrDesignTimeController.getAllFormDefinesInFormSchemeWithoutBinaryData(designFormSchemeDefine.getKey());
            for (DesignFormDefine form : forms) {
                if (!FormType.FORM_TYPE_ACCOUNT.equals((Object)form.getFormType())) continue;
                List regions = this.nrDesignTimeController.getAllRegionsInForm(form.getKey());
                int floatRegionNum = 0;
                HashSet<String> tableNum = new HashSet<String>();
                boolean hasAllDim = true;
                block2: for (DesignDataRegionDefine region : regions) {
                    if (!DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)region.getRegionKind()) && !DataRegionKind.DATA_REGION_COLUMN_LIST.equals((Object)region.getRegionKind())) continue;
                    ArrayList<String> fieldKeys = new ArrayList<String>();
                    String[] bizKeys = new String[]{};
                    ++floatRegionNum;
                    List links = this.nrDesignTimeController.getAllLinksInRegion(region.getKey());
                    ArrayList bizKeyOrder = new ArrayList();
                    for (DesignDataLinkDefine link : links) {
                        DesignFieldDefine designFieldDefine;
                        if (!DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)link.getType()) || null == (designFieldDefine = this.iDataDefinitionDesignTimeController.queryFieldDefine(link.getLinkExpression()))) continue;
                        tableNum.add(designFieldDefine.getOwnerTableKey());
                        fieldKeys.add(designFieldDefine.getKey());
                        DesignTableDefine tabledefine = this.iDataDefinitionDesignTimeController.queryTableDefine(designFieldDefine.getOwnerTableKey());
                        bizKeys = tabledefine.getBizKeyFieldsID();
                    }
                    List bizFields = this.designDataSchemeService.getDataFields(Arrays.asList(bizKeys)).stream().filter(f -> DataFieldKind.PUBLIC_FIELD_DIM != f.getDataFieldKind() && DataFieldKind.BUILT_IN_FIELD != f.getDataFieldKind()).collect(Collectors.toList());
                    for (DesignDataField field : bizFields) {
                        if (fieldKeys.contains(field.getKey())) continue;
                        hasAllDim = false;
                        continue block2;
                    }
                }
                if (floatRegionNum != 1) {
                    throw new Exception("\u53f0\u8d26\u8868[" + form.getTitle() + "]\u53ea\u80fd\u5b58\u5728\u4e00\u4e2a\u6d6e\u52a8\u533a\u57df");
                }
                if (tableNum.size() != 1) {
                    throw new Exception("\u53f0\u8d26\u8868[" + form.getTitle() + "]\u6d6e\u52a8\u533a\u57df\u4e2d\u5fc5\u987b\u8bbe\u7f6e\u6307\u6807\u4e14\u53ea\u80fd\u6765\u81ea\u540c\u4e00\u4e2a\u53f0\u8d26\u8868");
                }
                if (hasAllDim) continue;
                throw new Exception("\u53f0\u8d26\u8868[" + form.getTitle() + "]\u6d6e\u52a8\u533a\u57df\u4e2d\u5fc5\u987b\u9009\u62e9\u7ef4\u5ea6");
            }
        }
    }

    @Override
    public String publishTask(String taskID, String deployTaskID, String language, String activeFormId, String ownGroupId, String activedSchemeId, boolean deployDataScheme) throws Exception {
        String res = null;
        int languageType = Integer.valueOf(language);
        String userId = NpContextHolder.getContext().getUserId();
        ProgressItem progress = this.progressCacheService.getProgress(taskID);
        if (progress != null && !progress.isFinished()) {
            this.deployManager.addDeployTask(deployTaskID, taskID);
            throw new Exception("\u8be5\u4efb\u52a1\u6b63\u5728\u53d1\u5e03");
        }
        this.progressCacheService.removeProgress(taskID);
        this.deployManager.addDeployTask(deployTaskID, taskID);
        try {
            this.checkAccountForm(taskID);
            this.deployTaskBrfore(taskID);
            this.deployController.deployTask(taskID, deployDataScheme);
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskID);
            ArrayList runTimeFormulaSchemeDefines = new ArrayList();
            for (Object formSchemeDefine : formSchemeDefines) {
                runTimeFormulaSchemeDefines.addAll(this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey()));
            }
            ArrayList<String> runTimeFormulaSchemeKey = new ArrayList<String>();
            for (FormulaSchemeDefine formulaScheme : runTimeFormulaSchemeDefines) {
                runTimeFormulaSchemeKey.add(formulaScheme.getKey());
            }
            this.applicationContext.publishEvent((ApplicationEvent)new DeployRefreshFormulaEvent(runTimeFormulaSchemeKey));
            String mess = "{formId:" + activeFormId + ",taskId:" + taskID + ",ownGroupId:" + ownGroupId + ",activedSchemeId:" + activedSchemeId + ",languageType:" + languageType + "}";
            res = this.getRequestForm(mess);
            this.progressLoadingServiceImpl.deleteProgressLoading(taskID);
            ProgressItem progress2 = this.progressCacheService.getProgress(taskID);
            if (progress2 != null && progress2.getWarnList().size() > 0) {
                this.progressLoadingServiceImpl.publishWarring(taskID, userId, progress2.getWarnList().stream().collect(Collectors.joining(";")), progress2.getWarnList().stream().collect(Collectors.joining(";")));
            } else {
                this.progressLoadingServiceImpl.publishSuccess(taskID, userId);
            }
        }
        catch (Exception e) {
            String message = e.getMessage();
            ProgressItem progress2 = this.progressCacheService.getProgress(taskID);
            if (progress2 == null) {
                progress2 = new ProgressItem();
            }
            progress2.setFailed(true);
            progress2.setFinished(true);
            progress2.setProgressId(taskID);
            progress2.setMessage(e.getMessage());
            this.progressCacheService.setProgress(taskID, progress2);
            this.progressLoadingServiceImpl.deleteProgressLoading(taskID);
            this.progressLoadingServiceImpl.publishFail(taskID, userId, message == null ? "" : (message.length() > 2000 ? message.substring(0, 2000) : message), CommonHelper.printStackTraceToString(e));
            throw new Exception(e);
        }
        return res;
    }

    @Override
    public String directProtectedPublish(TaskPlanPublishObj taskPlanPublishObj) throws Exception {
        String res = null;
        String taskID = taskPlanPublishObj.getTaskID();
        if (!StringUtils.isEmpty((String)taskID)) {
            boolean publishFail = false;
            String failMessage = null;
            String publishTime = DateUtils.getCurrDate();
            String publishStatus = PublishStatus.PUBLISHING.toString();
            String planPublishKey = this.beforeTaskPublish(taskID, publishTime, publishStatus, null);
            try {
                String deployTaskID = taskPlanPublishObj.getDeployTaskID();
                String language = taskPlanPublishObj.getLanguageType();
                String activeFormId = taskPlanPublishObj.getActivedFormId();
                String ownGroupId = taskPlanPublishObj.getOwnGroupId();
                String activedSchemeId = taskPlanPublishObj.getActivedSchemeId();
                res = this.publishTask(taskID, deployTaskID, language, activeFormId, ownGroupId, activedSchemeId, taskPlanPublishObj.isDeployDataScheme());
            }
            catch (Exception e) {
                this.afterTaskPublish(planPublishKey, taskID, true, e.getMessage());
                throw new Exception(e);
            }
            this.afterTaskPublish(planPublishKey, taskID, publishFail, failMessage);
            return res;
        }
        throw new Exception("\u4efb\u52a1key\u4e3a\u7a7a");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void planProtectedPublish(TaskPlanPublishObj taskPlanPublishObj) throws Exception {
        String taskID = taskPlanPublishObj.getTaskID();
        String publishTime = taskPlanPublishObj.getPublishDate();
        if (DateUtils.publishExpired(DateUtils.getDate(publishTime))) {
            this.directProtectedPublish(taskPlanPublishObj);
        } else {
            String deployTaskID = taskPlanPublishObj.getDeployTaskID();
            String publishStatus = PublishStatus.BRFORE_PUBLISH.toString();
            String jobKey = JobCommon.getJobId();
            String planPublishKey = this.beforeTaskPublish(taskID, publishTime, publishStatus, jobKey);
            this.createJob(jobKey, planPublishKey, taskID, deployTaskID, publishTime);
            this.sendMessageService.createSendMessageJobs(planPublishKey, taskPlanPublishObj);
        }
        String message = taskPlanPublishObj.getMessage();
        String taskKey = taskPlanPublishObj.getTaskID();
        this.sendMessageService.sendMessageToUser(message, taskKey);
    }

    @Override
    public String beforeTaskPublish(String taskKey, String publishTime, String publishStatus, String jobKey) throws Exception {
        String key = UUIDUtils.getKey();
        NpContext context = NpContextHolder.getContext();
        String userName = context.getUserName();
        String userKey = context.getUserId();
        TaskPlanPublish taskPlanPublish = new TaskPlanPublish();
        taskPlanPublish.setKey(key);
        taskPlanPublish.setTaskKey(taskKey);
        taskPlanPublish.setJobKey(jobKey);
        taskPlanPublish.setPublishDate(publishTime);
        taskPlanPublish.setPublishStatus(publishStatus);
        taskPlanPublish.setWorkStatus(WorkStatus.DO.toString());
        taskPlanPublish.setUserKey(userKey);
        taskPlanPublish.setUserName(userName);
        this.taskPlanPublishDao.insert(taskPlanPublish);
        this.taskPlanPublishDao.undoOtherPlan(key, taskKey);
        return key;
    }

    @Override
    public void afterTaskPublish(String key, String taskKey, boolean publishFail, String failMessage) throws Exception {
        if (publishFail) {
            TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.query(key);
            taskPlanPublish.setPublishStatus(PublishStatus.PUBLISH_FAIL.toString());
            taskPlanPublish.setComment(failMessage);
            this.taskPlanPublishDao.update(taskPlanPublish);
        } else {
            ProgressItem progress2 = this.progressCacheService.getProgress(taskKey);
            if (progress2 != null && progress2.getWarnList().size() > 0) {
                this.taskPlanPublishDao.updatePublishStatus(key, PublishStatus.PUBLISH_WARRING.toString());
            } else {
                this.taskPlanPublishDao.updatePublishStatus(key, PublishStatus.PUBLISH_SUCCESS.toString());
                this.taskPlanPublishDao.undoPlan(key);
            }
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void changePlanPublishDate(ChangePlanPublishDate changePlanPublishDate) throws Exception {
        TaskPlanPublish taskPlanPublish;
        String planKey = changePlanPublishDate.getPlanKey();
        String taskKey = changePlanPublishDate.getTaskKey();
        String jobKey = changePlanPublishDate.getJobKey();
        if (!StringUtils.isEmpty((String)taskKey) && (taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey)) != null) {
            String oldPlanKey;
            String messageJobKeys = taskPlanPublish.getMessagejobKeys();
            if (messageJobKeys != null && !"".equals(messageJobKeys)) {
                String[] messageJobKeyArray = messageJobKeys.split(";");
                for (int i = 0; i < messageJobKeyArray.length; ++i) {
                    String messageJobKey = messageJobKeyArray[i];
                    JobKey quartzJobKey = new JobKey(messageJobKey, JobCommon.getMessageJobGroupId());
                    this.scheduler.deleteJob(quartzJobKey);
                }
            }
            if (!(oldPlanKey = taskPlanPublish.getKey()).equals(planKey)) {
                this.taskPlanPublishDao.updatePublishStatus(oldPlanKey, PublishStatus.PUBLISH_CANCEL.toString());
                String oldJobKey = taskPlanPublish.getJobKey();
                if (!StringUtils.isEmpty((String)oldJobKey)) {
                    JobKey quartzJobKey = new JobKey(oldJobKey, JobCommon.getJobGroupId());
                    this.scheduler.deleteJob(quartzJobKey);
                }
            }
        }
        if (!StringUtils.isEmpty((String)jobKey)) {
            JobKey quartzJobKey = new JobKey(jobKey, JobCommon.getJobGroupId());
            this.scheduler.deleteJob(quartzJobKey);
        }
        this.taskPlanPublishDao.updatePublishStatus(planKey, PublishStatus.PUBLISH_CANCEL.toString());
        TaskPlanPublishObj taskPlanPublishObj = changePlanPublishDate.getTaskPlanPublishObj();
        this.planProtectedPublish(taskPlanPublishObj);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelPlanPublishDate(ChangePlanPublishDate changePlanPublishDate) throws Exception {
        TaskPlanPublish taskPlanPublish;
        String planKey = changePlanPublishDate.getPlanKey();
        String taskKey = changePlanPublishDate.getTaskKey();
        String jobKey = changePlanPublishDate.getJobKey();
        if (!StringUtils.isEmpty((String)taskKey) && (taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey)) != null) {
            String oldPlanKey;
            String messageJobKeys = taskPlanPublish.getMessagejobKeys();
            if (messageJobKeys != null && !"".equals(messageJobKeys)) {
                String[] messageJobKeyArray = messageJobKeys.split(";");
                for (int i = 0; i < messageJobKeyArray.length; ++i) {
                    String messageJobKey = messageJobKeyArray[i];
                    JobKey quartzJobKey = new JobKey(messageJobKey, JobCommon.getMessageJobGroupId());
                    this.scheduler.deleteJob(quartzJobKey);
                }
            }
            if (!(oldPlanKey = taskPlanPublish.getKey()).equals(planKey)) {
                this.taskPlanPublishDao.updatePublishStatus(oldPlanKey, PublishStatus.PUBLISH_CANCEL.toString());
                String oldJobKey = taskPlanPublish.getJobKey();
                if (!StringUtils.isEmpty((String)oldJobKey)) {
                    JobKey quartzJobKey = new JobKey(oldJobKey, JobCommon.getJobGroupId());
                    this.scheduler.deleteJob(quartzJobKey);
                }
            }
        }
        if (!StringUtils.isEmpty((String)jobKey)) {
            JobKey quartzJobKey = new JobKey(jobKey, JobCommon.getJobGroupId());
            this.scheduler.deleteJob(quartzJobKey);
        }
        this.taskPlanPublishDao.updatePublishStatus(planKey, PublishStatus.PUBLISH_CANCEL.toString());
        this.taskPlanPublishDao.undoPlan(planKey);
    }

    private void createJob(String jobKey, String key, String taskKey, String deployTaskID, String planPublishDateStr) throws Exception {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("planKey", key);
        jobDataMap.put("taskKey", taskKey);
        jobDataMap.put("deployTaskID", deployTaskID);
        jobDataMap.put("userName", NpContextHolder.getContext().getUserName());
        Class<TaskPlanPublishJob> jobClass = TaskPlanPublishJob.class;
        String jobGroupId = JobCommon.getJobGroupId();
        String cronExpression = DateUtils.getCron(planPublishDateStr);
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey, jobGroupId).setJobData(jobDataMap).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKey, jobGroupId).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule((String)cronExpression)).build();
        this.scheduler.scheduleJob(jobDetail, trigger);
        if (!this.scheduler.isShutdown()) {
            this.scheduler.start();
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String protectTask(String taskKey) throws Exception {
        TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
        if (taskPlanPublish == null) {
            return this.beforeTaskPublish(taskKey, null, PublishStatus.PROTECTING.toString(), null);
        }
        String status = taskPlanPublish.getPublishStatus();
        if (!PublishStatus.PUBLISHING.toString().equals(status)) {
            if (PublishStatus.BRFORE_PUBLISH.toString().equals(status)) {
                String publishDate = taskPlanPublish.getPublishDate();
                Date date = DateUtils.getDate(publishDate);
                if (DateUtils.publishExpired(date)) {
                    return this.beforeTaskPublish(taskKey, null, PublishStatus.PROTECTING.toString(), null);
                }
                throw new Exception("\u7ef4\u62a4\u5931\u8d25");
            }
            return this.beforeTaskPublish(taskKey, null, PublishStatus.PROTECTING.toString(), null);
        }
        throw new Exception("\u7ef4\u62a4\u5931\u8d25");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void cancelProtectTask(TaskPlanPublishObject taskPlanPublishObject) throws Exception {
        String planKey = taskPlanPublishObject.getKey();
        String status = this.taskPlanPublishDao.query(planKey).getPublishStatus();
        if (!PublishStatus.PROTECTING.toString().equals(status)) {
            throw new Exception("\u53d6\u6d88\u7ef4\u62a4\u5931\u8d25");
        }
        this.taskPlanPublishDao.updatePublishStatus(planKey, PublishStatus.PROTECT_END.toString());
        this.taskPlanPublishDao.undoPlan(planKey);
    }

    @Override
    public int checkPublishStatusWhenSysStart() throws Exception {
        return 0;
    }

    @Override
    public TaskPlanPublishObject queryPublishStatusByTaskKey(String taskKey) throws Exception {
        TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
        if (taskPlanPublish != null) {
            return new TaskPlanPublishObject(taskPlanPublish);
        }
        return null;
    }

    private String getRequestForm(String jsonData) throws Exception {
        DesignRestVO vo = new DesignRestVO(jsonData);
        DesignFormDefine designFormDefine = this.nrDesignTimeController.queryFormById(vo.getFormKey());
        if (vo.getLanguageType() != this.defaultLanguageService.getDefaultLanguage()) {
            designFormDefine.setBinaryData(this.nrDesignTimeController.getReportDataFromForm(vo.getFormKey(), this.defaultLanguageService.getDefaultLanguage()));
        }
        FormObj formObject = this.initParamObjPropertyUtil.setFormObjProperty(designFormDefine, vo.getTaskKey(), vo.getFormGroupKey(), true, true);
        return this.serializeFormObject(formObject);
    }

    private String serializeFormObject(FormObj formObject) throws Exception {
        FormData formData = new FormData();
        formData.setFormObject(formObject);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)formData);
    }
}

