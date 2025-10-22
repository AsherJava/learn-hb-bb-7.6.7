/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 */
package com.jiuqi.nr.designer.planpublish.service.impl;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.designer.planpublish.common.PublishStatus;
import com.jiuqi.nr.designer.planpublish.common.WorkStatus;
import com.jiuqi.nr.designer.planpublish.model.TaskPlanPublishObject;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.planpublish.util.DateUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskPlanPublishExternalServiceImpl
implements TaskPlanPublishExternalService {
    private static final Logger log = LoggerFactory.getLogger(TaskPlanPublishExternalServiceImpl.class);
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;

    @Override
    public TaskPlanPublish queryPlanPublishByKey(String planKey) throws Exception {
        return this.taskPlanPublishDao.query(planKey);
    }

    @Override
    public boolean taskCanEdit(String taskKey) {
        this.checkParamLockingByTask(taskKey);
        if (!StringUtils.isEmpty((String)taskKey)) {
            TaskPlanPublish taskPlanPublish = null;
            try {
                taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (taskPlanPublish != null) {
                String publishStatus = taskPlanPublish.getPublishStatus();
                return !PublishStatus.PUBLISHING.toString().equals(publishStatus);
            }
        }
        return true;
    }

    @Override
    public void checkParamLockingByTask(String taskKey) {
        TaskPlanPublish taskPlanPublish = null;
        try {
            taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (taskPlanPublish != null && PublishStatus.PARAM_LOCKING.toString().equals(taskPlanPublish.getPublishStatus())) {
            throw new RuntimeException("\u53c2\u6570\u9501\u5b9a\u4e2d\uff0c\u7981\u6b62\u4fee\u6539\uff01");
        }
    }

    @Override
    public boolean schemeCanEdit(String schemeKey) {
        try {
            DesignFormSchemeDefine designFormSchemeDefine;
            if (!StringUtils.isEmpty((String)schemeKey) && (designFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeKey)) != null) {
                String taskKey = designFormSchemeDefine.getTaskKey();
                return this.taskCanEdit(taskKey);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean formCanEdit(String formKey) {
        try {
            List formGroupDefines;
            if (!StringUtils.isEmpty((String)formKey) && (formGroupDefines = this.nrDesignTimeController.getFormGroupsByFormId(formKey)) != null && formGroupDefines.size() > 0) {
                String schemeKey = ((DesignFormGroupDefine)formGroupDefines.get(0)).getFormSchemeKey();
                return this.schemeCanEdit(schemeKey);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean formulaSchemeCanEdit(String formulaSchemeKey) {
        try {
            DesignFormulaSchemeDefine designFormulaSchemeDefine;
            if (!StringUtils.isEmpty((String)formulaSchemeKey) && (designFormulaSchemeDefine = this.nrDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey)) != null) {
                return this.schemeCanEdit(designFormulaSchemeDefine.getFormSchemeKey());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean printSchemeCanEdit(String printSchemeKey) {
        try {
            DesignPrintTemplateSchemeDefine printTemplateSchemeDefine;
            if (!StringUtils.isEmpty((String)printSchemeKey) && (printTemplateSchemeDefine = this.nrDesignTimeController.queryPrintTemplateSchemeDefine(printSchemeKey)) != null) {
                return this.schemeCanEdit(printTemplateSchemeDefine.getFormSchemeKey());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public TaskPlanPublishObject queryPlanPublishOfTask(String taskKey) throws Exception {
        TaskPlanPublishObject taskPlanPublishObject = null;
        if (!StringUtils.isEmpty((String)taskKey)) {
            TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
            if (taskPlanPublish != null) {
                taskPlanPublishObject = new TaskPlanPublishObject(taskPlanPublish);
                if (PublishStatus.BRFORE_PUBLISH.toString().equals(taskPlanPublish.getPublishStatus()) && DateUtils.publishExpired(DateUtils.getDate(taskPlanPublish.getPublishDate()))) {
                    taskPlanPublishObject.setPublishStatus(PublishStatus.PUBLISH_OVERDUE.toString());
                }
            } else {
                taskPlanPublishObject = new TaskPlanPublishObject();
                taskPlanPublishObject.setPublishStatus(PublishStatus.NEVER_PUBLISH.toString());
            }
        }
        return taskPlanPublishObject;
    }

    @Override
    public String beforeTaskPublish(String taskKey) throws Exception {
        if (!StringUtils.isEmpty((String)taskKey)) {
            String publishTime = DateUtils.getCurrDate();
            String publishStatus = PublishStatus.PUBLISHING.toString();
            String planPublishKey = this.beforeTaskPublish(taskKey, publishTime, publishStatus, null);
            return planPublishKey;
        }
        return null;
    }

    @Override
    public void afterTaskPublish(String key, String taskKey, boolean publishFail, String failMessage) throws Exception {
        if (!StringUtils.isEmpty((String)key) && !StringUtils.isEmpty((String)taskKey)) {
            this.afterTaskPublish2(key, taskKey, publishFail, failMessage);
        }
    }

    @Override
    public boolean taskNeverPublish(String taskKey) throws Exception {
        return this.runTimeController.queryTaskDefine(taskKey) == null;
    }

    @Override
    public void createMsgRollBackSuccess(String taskKey) throws Exception {
        String key = UUIDUtils.getKey();
        NpContext context = NpContextHolder.getContext();
        String userName = context.getUserName();
        String userKey = context.getUserId();
        TaskPlanPublish taskPlanPublish = new TaskPlanPublish();
        taskPlanPublish.setKey(key);
        taskPlanPublish.setTaskKey(taskKey);
        taskPlanPublish.setJobKey(null);
        taskPlanPublish.setPublishDate(DateUtils.getCurrDate());
        taskPlanPublish.setPublishStatus(PublishStatus.ROLL_BACK_SUCCESS.toString());
        taskPlanPublish.setWorkStatus(WorkStatus.UNDO.toString());
        taskPlanPublish.setUserKey(userKey);
        taskPlanPublish.setUserName(userName);
        this.taskPlanPublishDao.insert(taskPlanPublish);
        this.taskPlanPublishDao.undoOtherPlan(key, taskKey);
    }

    private String beforeTaskPublish(String taskKey, String publishTime, String publishStatus, String jobKey) throws Exception {
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

    private void afterTaskPublish2(String key, String taskKey, boolean publishFail, String failMessage) throws Exception {
        if (publishFail) {
            TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.query(key);
            taskPlanPublish.setPublishStatus(PublishStatus.PUBLISH_FAIL.toString());
            taskPlanPublish.setComment(failMessage);
            this.taskPlanPublishDao.update(taskPlanPublish);
        } else {
            this.taskPlanPublishDao.updatePublishStatus(key, PublishStatus.PUBLISH_SUCCESS.toString());
            this.taskPlanPublishDao.undoPlan(key);
        }
    }
}

