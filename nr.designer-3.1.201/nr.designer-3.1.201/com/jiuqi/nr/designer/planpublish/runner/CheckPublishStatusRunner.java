/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.internal.service.DesignTaskDefineService
 *  com.jiuqi.nr.definition.planpublish.dao.impl.TaskPlanPublishDaoImpl
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 *  com.jiuqi.nr.definition.util.NrDefinitionHelper
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLock
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.designer.planpublish.runner;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.planpublish.dao.impl.TaskPlanPublishDaoImpl;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.definition.util.NrDefinitionHelper;
import com.jiuqi.nr.designer.planpublish.common.PublishStatus;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckPublishStatusRunner
implements ModuleInitiator {
    private static final Logger logger = LoggerFactory.getLogger(CheckPublishStatusRunner.class);
    @Autowired
    private TaskPlanPublishDaoImpl TaskPlanPublishDaoImpl;
    @Autowired
    private DesignTaskDefineService taskService;
    @Autowired
    private DatabaseLock databaseLock;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;

    public void init(ServletContext context) throws Exception {
        try {
            List taskPlans = this.TaskPlanPublishDaoImpl.queryAllPublishing();
            if (taskPlans == null || taskPlans.isEmpty()) {
                logger.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u72b6\u6001\u68c0\u67e5\uff0c\u6ca1\u6709\u5f02\u5e38\u53d1\u5e03\u8bb0\u5f55");
                return;
            }
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u72b6\u6001\u68c0\u67e5", e);
        }
        new Thread(() -> {
            try {
                Thread.sleep(60000L);
            }
            catch (InterruptedException e) {
                logger.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u72b6\u6001\u68c0\u67e5", e);
                Thread.currentThread().interrupt();
            }
            try {
                this.fixDeployStatus();
            }
            catch (Exception e) {
                logger.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u72b6\u6001\u68c0\u67e5", e);
            }
        }).start();
    }

    private void fixDeployStatus() throws Exception {
        List taskPlans = this.TaskPlanPublishDaoImpl.queryAllPublishing();
        if (taskPlans == null || taskPlans.isEmpty()) {
            logger.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u72b6\u6001\u68c0\u67e5\uff0c\u6ca1\u6709\u5f02\u5e38\u53d1\u5e03\u8bb0\u5f55");
            return;
        }
        for (int i = taskPlans.size() - 1; i == 0; --i) {
            TaskPlanPublish plan = (TaskPlanPublish)taskPlans.get(i);
            String lockName = this.nrDefinitionHelper.getLockName(ParamResourceType.TASK, plan.getTaskKey());
            boolean locked = this.databaseLock.isLocked(lockName);
            if (locked) {
                taskPlans.remove(i);
                continue;
            }
            plan.setPublishStatus(PublishStatus.PUBLISH_INTERRUPT.toString());
        }
        if (taskPlans.isEmpty()) {
            return;
        }
        this.TaskPlanPublishDaoImpl.update(taskPlans.toArray());
        List taskDefines = this.taskService.queryTaskDefines((String[])taskPlans.stream().map(TaskPlanPublish::getTaskKey).toArray(String[]::new));
        List taskTitles = Optional.ofNullable(taskDefines).orElse(Collections.emptyList()).stream().map(IBaseMetaItem::getTitle).collect(Collectors.toList());
        if (!taskTitles.isEmpty()) {
            logger.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u72b6\u6001\u68c0\u67e5\uff0c\u5171\u4fee\u590d\u4e86{}\u4e2a\u4efb\u52a1\uff1a{}", (Object)taskPlans.size(), (Object)taskTitles);
        }
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

