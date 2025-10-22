/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.planpublish.service.impl;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.definition.planpublish.service.DesignTimePlanPublishService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class DesignTimePlanPublishServiceImpl
implements DesignTimePlanPublishService {
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    @Override
    public boolean taskIsProtectIng(String taskKey) throws Exception {
        if (StringUtils.isEmpty((String)taskKey)) {
            return false;
        }
        TaskPlanPublish taskPlanPublish = this.taskPlanPublishDao.queryWorkPlan(taskKey);
        if (null == taskPlanPublish) {
            return false;
        }
        if ("protecting".equals(taskPlanPublish.getPublishStatus())) {
            return true;
        }
        if ("publishing".equals(taskPlanPublish.getPublishStatus())) {
            if (this.isTaskDeployLock()) {
                return true;
            }
            TaskDefine task = this.runTimeViewController.getTask(taskKey);
            if (null == task) {
                task = this.designTimeViewController.getTask(taskKey);
            }
            if (null == task) {
                return false;
            }
            DeployStatusEnum deployStatus = this.runtimeDataSchemeService.getDataSchemeDeployStatus(task.getDataScheme());
            return DeployStatusEnum.DEPLOY == deployStatus || DeployStatusEnum.PARAM_LOCKING == deployStatus;
        }
        return false;
    }

    private boolean isTaskDeployLock() {
        String value = this.systemOptionService.get("nr-data-entry-group", "TASK_DEPLOY_UNLOCK");
        return !"1".equals(value);
    }

    @Override
    public List<String> getAllProtectIngTaskKey() throws Exception {
        List<TaskPlanPublish> allProtectingWorkPlan = this.taskPlanPublishDao.queryAllProtectingWorkPlan();
        return Optional.ofNullable(allProtectingWorkPlan).orElse(Collections.emptyList()).stream().map(workPlan -> workPlan.getTaskKey()).collect(Collectors.toList());
    }
}

