/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.dao.impl.TaskPlanPublishDaoImpl
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.deploy.service.compatible;

import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.deploy.dao.ParamDeployStatusDao;
import com.jiuqi.nr.definition.deploy.entity.ParamDeployStatusDO;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.dao.impl.TaskPlanPublishDaoImpl;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Primary
public class TaskDeployStatusServiceImpl
implements TaskPlanPublishDao {
    @Autowired
    private DesignFormSchemeDefineDao formSchemeDefineDao;
    @Autowired
    private TaskPlanPublishDaoImpl taskPlanPublishDao;
    @Autowired
    private ParamDeployStatusDao paramDeployStatusDao;
    private static final List<String> DEPLOY_STATUS = Arrays.asList("publishing", "publish_fail", "publish_warring", "publish_success", "never_publish");
    private static final List<ParamDeployEnum.DeployStatus> NEW_DEPLOY_STATUS = Arrays.asList(ParamDeployEnum.DeployStatus.DEPLOY, ParamDeployEnum.DeployStatus.FAIL, ParamDeployEnum.DeployStatus.WARNING, ParamDeployEnum.DeployStatus.SUCCESS, ParamDeployEnum.DeployStatus.NOT_DEPLOYED);

    private static void updateDeployStatus(TaskPlanPublish plan, List<ParamDeployStatusDO> status) {
        int newIndex;
        if (!DEPLOY_STATUS.contains(plan.getPublishStatus())) {
            return;
        }
        ParamDeployEnum.DeployStatus deployStatus = ParamDeployEnum.DeployStatus.NOT_DEPLOYED;
        for (ParamDeployStatusDO item : status) {
            if (deployStatus.getValue() >= item.getDeployStatus().getValue()) continue;
            deployStatus = item.getDeployStatus();
        }
        int index = DEPLOY_STATUS.indexOf(plan.getPublishStatus());
        if (index > (newIndex = NEW_DEPLOY_STATUS.indexOf(deployStatus))) {
            plan.setPublishStatus(DEPLOY_STATUS.get(newIndex));
        }
        if (ParamDeployEnum.DeployStatus.DEPLOY == deployStatus) {
            return;
        }
        for (ParamDeployStatusDO item : status) {
            switch (item.getParamStatus()) {
                case LOCKED: {
                    plan.setPublishStatus("param_locking");
                    return;
                }
                case READONLY: 
                case MAINTENANCE: {
                    plan.setPublishStatus("protecting");
                    break;
                }
            }
        }
    }

    private TaskPlanPublish updateDeployStatus(TaskPlanPublish plan, String taskKey) {
        List<ParamDeployStatusDO> status = this.paramDeployStatusDao.listDeployStatusByTask(taskKey);
        if (null == plan) {
            if (CollectionUtils.isEmpty(status)) {
                return null;
            }
            plan = new TaskPlanPublish();
            plan.setKey(UUIDUtils.getKey());
            plan.setTaskKey(taskKey);
            plan.setPublishStatus("never_publish");
        }
        TaskDeployStatusServiceImpl.updateDeployStatus(plan, status);
        return plan;
    }

    private void updateDeployStatus(TaskPlanPublish plan) {
        if (!DEPLOY_STATUS.contains(plan.getPublishStatus())) {
            return;
        }
        int index = DEPLOY_STATUS.indexOf(plan.getPublishStatus());
        ParamDeployEnum.DeployStatus deployStatus = NEW_DEPLOY_STATUS.get(index);
        List defines = this.formSchemeDefineDao.listByTask(plan.getTaskKey());
        ArrayList<ParamDeployStatusDO> insert = new ArrayList<ParamDeployStatusDO>();
        ArrayList<ParamDeployStatusDO> update = new ArrayList<ParamDeployStatusDO>();
        for (DesignFormSchemeDefine define : defines) {
            ParamDeployStatusDO item = this.paramDeployStatusDao.getDeployStatus(define.getKey());
            if (null != item) {
                item.setDeployStatus(deployStatus);
                item.setDeployDetail(plan.getComment());
                if (deployStatus == ParamDeployEnum.DeployStatus.DEPLOY) {
                    item.setLastDeployTime(item.getDeployTime());
                    item.setDeployTime(Date.from(Instant.now()));
                }
                update.add(item);
                continue;
            }
            ParamDeployStatusDO obj = new ParamDeployStatusDO();
            obj.setSchemeKey(define.getKey());
            obj.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
            obj.setDeployStatus(deployStatus);
            obj.setDeployDetail(plan.getComment());
            obj.setDeployTime(Date.from(Instant.now()));
            obj.setUserKey(plan.getUserKey());
            obj.setUserName(plan.getUserName());
            insert.add(obj);
        }
        this.paramDeployStatusDao.updateDeployStatus(update);
        this.paramDeployStatusDao.insertDeployStatus(insert);
    }

    public TaskPlanPublish queryWorkPlan(String taskKey) throws Exception {
        TaskPlanPublish plan = this.taskPlanPublishDao.queryWorkPlan(taskKey);
        return this.updateDeployStatus(plan, taskKey);
    }

    public TaskPlanPublish getByTask(String key) {
        TaskPlanPublish plan = this.taskPlanPublishDao.getByTask(key);
        return this.updateDeployStatus(plan, key);
    }

    public List<TaskPlanPublish> listTask(String key) {
        return this.taskPlanPublishDao.listTask(key);
    }

    public TaskPlanPublish query(String key) throws Exception {
        TaskPlanPublish plan = this.taskPlanPublishDao.query(key);
        if (null == plan) {
            return null;
        }
        return this.updateDeployStatus(plan, plan.getTaskKey());
    }

    public List<TaskPlanPublish> queryAllPublishing() throws Exception {
        return this.taskPlanPublishDao.queryAllPublishing();
    }

    public void insert(TaskPlanPublish taskPlanPublish) throws Exception {
        this.taskPlanPublishDao.insert(taskPlanPublish);
        this.updateDeployStatus(taskPlanPublish);
    }

    public void update(TaskPlanPublish taskPlanPublish) throws Exception {
        this.taskPlanPublishDao.update(taskPlanPublish);
        this.updateDeployStatus(taskPlanPublish);
    }

    public List<TaskPlanPublish> queryAllProtectingWorkPlan() throws Exception {
        return this.taskPlanPublishDao.queryAllProtectingWorkPlan();
    }

    public void undoOtherPlan(String planKey, String taskKey) throws Exception {
        this.taskPlanPublishDao.undoOtherPlan(planKey, taskKey);
    }

    public void deleteByTaskKey(String taskKey) {
        this.taskPlanPublishDao.deleteByTaskKey(taskKey);
    }
}

