/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.planpublish.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.model.ChangePlanPublishDate;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObj;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"taskplanpublish"})
public class TaskPlanPublishController {
    private static final Logger logger = LoggerFactory.getLogger(TaskPlanPublishController.class);
    @Autowired
    private TaskPlanPublishService taskPlanPublishService;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IDesignTimeViewController designTimeViewService;

    @GetMapping(value={"publish-status-by-taskkey/{task-key}"})
    public TaskPlanPublishObject queryPublishStatusByTaskKey(@PathVariable(value="task-key") String taskKey) throws JQException {
        try {
            return this.taskPlanPublishService.queryPublishStatusByTaskKey(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_033, (Throwable)e);
        }
    }

    @PostMapping(value={"directpublishwithoutprotect"})
    public String directPublishWithOutProtect(@RequestBody TaskPlanPublishObj taskPlanPublishObj) throws JQException {
        String logTitle = "\u53d1\u5e03\u4efb\u52a1";
        String taskTitle = "\u672a\u77e5";
        try {
            taskTitle = this.designTimeViewService.queryTaskDefine(taskPlanPublishObj.getTaskID()).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskPlanPublishObj.getTaskID());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String taskID = taskPlanPublishObj.getTaskID();
            String deployTaskID = taskPlanPublishObj.getDeployTaskID();
            String language = taskPlanPublishObj.getLanguageType();
            String activeFormId = taskPlanPublishObj.getActivedFormId();
            String ownGroupId = taskPlanPublishObj.getOwnGroupId();
            String activedSchemeId = taskPlanPublishObj.getActivedSchemeId();
            String res = this.taskPlanPublishService.publishTask(taskID, deployTaskID, language, activeFormId, ownGroupId, activedSchemeId);
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return res;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_028, (Throwable)e);
        }
    }

    @PostMapping(value={"directpublishwithprotect"})
    public String directPublishWithProtect(@RequestBody TaskPlanPublishObj taskPlanPublishObj) throws JQException {
        String logTitle = "\u53d1\u5e03\u4efb\u52a1";
        String taskTitle = "\u672a\u77e5";
        try {
            taskTitle = this.designTimeViewService.queryTaskDefine(taskPlanPublishObj.getTaskID()).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskPlanPublishObj.getTaskID());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String res = this.taskPlanPublishService.directProtectedPublish(taskPlanPublishObj);
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return res;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_025, (Throwable)e);
        }
    }

    @PostMapping(value={"planpublishwithprotect"})
    public void planPublishWithProtect(@RequestBody TaskPlanPublishObj taskPlanPublishObj) throws JQException {
        try {
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskPlanPublishObj.getTaskID());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.taskPlanPublishService.planProtectedPublish(taskPlanPublishObj);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_026, (Throwable)e);
        }
    }

    @PostMapping(value={"changeplanpublishdate"})
    public void changePlanPublishDate(@RequestBody ChangePlanPublishDate changePlanPublishDate) throws JQException {
        try {
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(changePlanPublishDate.getTaskKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.taskPlanPublishService.changePlanPublishDate(changePlanPublishDate);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_027, (Throwable)e);
        }
    }

    @PostMapping(value={"cancelplanpublish"})
    public void cancelPlanPublish(@RequestBody ChangePlanPublishDate changePlanPublishDate) throws JQException {
        try {
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(changePlanPublishDate.getTaskKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.taskPlanPublishService.cancelPlanPublishDate(changePlanPublishDate);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_029, (Throwable)e);
        }
    }

    @GetMapping(value={"protecttask/{taskkey}"})
    public void protectTask(@PathVariable(value="taskkey") String taskKey) throws JQException {
        try {
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.taskPlanPublishService.protectTask(taskKey);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_031, (Throwable)e);
        }
    }

    @PostMapping(value={"cancelprotecttask"})
    public void cancelprotectTask(@RequestBody TaskPlanPublishObject taskPlanPublishObject) throws JQException {
        try {
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskPlanPublishObject.getTaskKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.taskPlanPublishService.cancelProtectTask(taskPlanPublishObject);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_032, (Throwable)e);
        }
    }

    @PostMapping(value={"tasklistpublishtask"})
    public String taskListPublishTask(@RequestBody TaskPlanPublishObj taskPlanPublishObj) throws JQException {
        String logTitle = "\u53d1\u5e03\u4efb\u52a1";
        String taskTitle = "\u672a\u77e5";
        try {
            taskTitle = this.designTimeViewService.queryTaskDefine(taskPlanPublishObj.getTaskID()).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskPlanPublishObj.getTaskID());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String taskID = taskPlanPublishObj.getTaskID();
            String deployTaskID = taskPlanPublishObj.getDeployTaskID();
            this.taskPlanPublishService.planPublishTask(taskID, deployTaskID);
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return "\u53d1\u5e03\u6210\u529f";
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            logger.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_028, e.getMessage());
        }
    }
}

