/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl
 *  com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.designer.planpublish.common.PublishStatus;
import com.jiuqi.nr.designer.planpublish.common.WorkStatus;
import com.jiuqi.nr.designer.planpublish.job.JobCommon;
import com.jiuqi.nr.designer.service.DDLDeployService;
import com.jiuqi.nr.designer.util.JQExceptionWrapper;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SpecialDeployService
implements DDLDeployService {
    @Autowired
    private DataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Autowired
    private List<NODDLDeployExecutor> deployExecutors;
    @Autowired
    private TaskPlanPublishDao innerTaskPlanPublishDao;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;
    private static final RuntimeException EXCEPTION = new RuntimeException("\u672a\u5f00\u542fDDL\u6a21\u5f0f\uff01");
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialDeployService.class);
    private static final String UPDATE_SQL = "UPDATE NR_TASK_PLANPUBLISH SET TPP_PUBLISHSTATUS = '%s', TPP_DDL_STATUS_BIT = %d WHERE TPP_TASKKEY = '%s';";

    @Override
    public boolean isEnableDDL() {
        return this.dataBaseLimitModeProvider.databaseLimitMode();
    }

    @Override
    public List<String> preDeploy(String taskKey) {
        this.checkDDLModel();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        TaskPlanPublish byTask = this.innerTaskPlanPublishDao.getByTask(taskKey);
        this.checkPrePublishRecord(byTask);
        this.checkDataSchemeStatus(task);
        ArrayList<String> ddlSqls = new ArrayList<String>();
        if (this.canGenerateSql(byTask)) {
            this.generateSql(taskKey, byTask, ddlSqls);
            this.lockDataScheme(task, ddlSqls);
            if (byTask == null) {
                this.insertPlan(this.instanceTaskPlan(taskKey), ddlSqls);
            } else {
                this.updatePlan(byTask, ddlSqls);
            }
            return ddlSqls;
        }
        return Collections.emptyList();
    }

    private void checkDataSchemeStatus(DesignTaskDefine task) {
        StatusBit statusBit;
        DataSchemeDeployStatusDO deployStatusDO = this.dataSchemeDeployStatusDao.getByDataSchemeKey(task.getDataScheme());
        if (deployStatusDO != null && (statusBit = DDLStatusBitUtils.getStatusBit(deployStatusDO.getDdlStatusBit())) != StatusBit.NEVER_GENERATE_SQL) {
            throw new RuntimeException("\u6570\u636e\u65b9\u6848\u9501\u5b9a\u4e2d\uff0c\u6682\u4e0d\u80fd\u751f\u6210SQL!");
        }
    }

    private void updatePlan(TaskPlanPublish byTask, List<String> ddlSqls) {
        PublishStatus publishStatus = DDLStatusBitUtils.getPublishStatus(byTask.getDdlStatusBit());
        int bit = CollectionUtils.isEmpty(ddlSqls) ? DDLStatusBitUtils.parseDDLStatusBit(publishStatus, StatusBit.DIRECT_RELEASE) : DDLStatusBitUtils.parseDDLStatusBit(publishStatus, StatusBit.GENERATE_SQL);
        byTask.setDdlStatusBit(bit);
        byTask.setPublishStatus(PublishStatus.PARAM_LOCKING.toString());
        try {
            this.innerTaskPlanPublishDao.update(byTask);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void insertPlan(TaskPlanPublish taskPlanPublish, List<String> ddlSqls) {
        int bit = CollectionUtils.isEmpty(ddlSqls) ? DDLStatusBitUtils.parseDDLStatusBit(PublishStatus.NEVER_PUBLISH, StatusBit.DIRECT_RELEASE) : DDLStatusBitUtils.parseDDLStatusBit(PublishStatus.NEVER_PUBLISH, StatusBit.GENERATE_SQL);
        taskPlanPublish.setDdlStatusBit(bit);
        taskPlanPublish.setPublishStatus(PublishStatus.PARAM_LOCKING.toString());
        try {
            this.innerTaskPlanPublishDao.insert(taskPlanPublish);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkPrePublishRecord(TaskPlanPublish taskPlanPublish) {
        if (taskPlanPublish != null) {
            if (StatusBit.isDirectRelease(DDLStatusBitUtils.getStatusBit(taskPlanPublish.getDdlStatusBit()))) {
                throw new RuntimeException("\u65e0\u9700\u751f\u6210SQL\uff0c\u8bf7\u76f4\u63a5\u53d1\u5e03");
            }
            if (StatusBit.isExecuteSql(DDLStatusBitUtils.getStatusBit(taskPlanPublish.getDdlStatusBit()))) {
                throw new RuntimeException("\u5df2\u6267\u884c\u8fc7SQL\uff0c\u8bf7\u6267\u884c\u53d1\u5e03");
            }
        }
    }

    private void lockDataScheme(DesignTaskDefine task, List<String> ddlSqls) {
        DataSchemeDeployStatusDO statusDO = this.dataSchemeDeployStatusDao.getByDataSchemeKey(task.getDataScheme());
        if (statusDO == null) {
            return;
        }
        if (statusDO.getDdlStatusBit() == 0) {
            if (ddlSqls.isEmpty()) {
                statusDO.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(DeployStatusEnum.NEVER_DEPLOY, StatusBit.DIRECT_RELEASE));
            } else {
                statusDO.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(DeployStatusEnum.NEVER_DEPLOY, StatusBit.GENERATE_SQL));
            }
        } else if (ddlSqls.isEmpty()) {
            statusDO.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(statusDO.getDeployStatus(), StatusBit.DIRECT_RELEASE));
        } else {
            statusDO.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(statusDO.getDeployStatus(), StatusBit.GENERATE_SQL));
        }
        statusDO.setDeployStatus(DeployStatusEnum.PARAM_LOCKING);
        statusDO.setUpdateTime(Instant.now());
        this.dataSchemeDeployStatusDao.update(statusDO);
    }

    private void unlockDataScheme(String taskKey) {
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        DataSchemeDeployStatusDO statusDO = this.dataSchemeDeployStatusDao.getByDataSchemeKey(task.getDataScheme());
        if (statusDO != null) {
            DeployStatusEnum deployStatusEnum = DDLStatusBitUtils.getDeployStatusEnum(statusDO.getDdlStatusBit());
            if (deployStatusEnum == DeployStatusEnum.NEVER_DEPLOY) {
                this.dataSchemeDeployStatusDao.delete(task.getDataScheme());
            } else {
                statusDO.setDeployStatus(deployStatusEnum);
                statusDO.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(deployStatusEnum, StatusBit.NEVER_GENERATE_SQL));
            }
            this.dataSchemeDeployStatusDao.update(statusDO);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deploy(String taskKey) {
        this.checkDDLModel();
        TaskPlanPublish byTask = this.innerTaskPlanPublishDao.getByTask(taskKey);
        boolean success = true;
        this.checkPublishRecord(byTask);
        byTask.setPublishStatus(PublishStatus.PUBLISHING.toString());
        this.update(byTask);
        try {
            this.doDeploy(byTask);
        }
        catch (Exception e) {
            success = false;
            LOGGER.error("\u53d1\u5e03\u5931\u8d25", e);
        }
        finally {
            if (success) {
                byTask.setPublishStatus(PublishStatus.PUBLISH_SUCCESS.toString());
                byTask.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(PublishStatus.PUBLISH_SUCCESS, StatusBit.NEVER_GENERATE_SQL));
                this.updateDataSchemeStatus(byTask.getTaskKey(), DeployStatusEnum.SUCCESS);
            } else {
                byTask.setPublishStatus(PublishStatus.PUBLISH_FAIL.toString());
                byTask.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(PublishStatus.PUBLISH_FAIL, StatusBit.NEVER_GENERATE_SQL));
                this.updateDataSchemeStatus(byTask.getTaskKey(), DeployStatusEnum.FAIL);
            }
            this.update(byTask);
        }
    }

    private void update(TaskPlanPublish last) {
        try {
            this.innerTaskPlanPublishDao.update(last);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkPublishRecord(TaskPlanPublish plan) {
        if (plan == null) {
            throw new RuntimeException("\u672a\u751f\u6210SQL\uff0c\u8bf7\u751f\u6210SQL");
        }
        if (StatusBit.isNeverGenerateSql(DDLStatusBitUtils.getStatusBit(plan.getDdlStatusBit()))) {
            throw new RuntimeException("\u672a\u751f\u6210SQL\uff0c\u8bf7\u751f\u6210SQL");
        }
        if (StatusBit.isGenerateSql(DDLStatusBitUtils.getStatusBit(plan.getDdlStatusBit()))) {
            throw new RuntimeException("\u672a\u6267\u884cSQL\uff0c\u8bf7\u6267\u884cSQL");
        }
    }

    private void doDeploy(TaskPlanPublish taskPlanPublish) {
        this.deployExecutors.sort(Comparator.comparingDouble(NODDLDeployExecutor::getOrder));
        for (NODDLDeployExecutor executor : this.deployExecutors) {
            executor.doDeploy(taskPlanPublish.getTaskKey());
        }
    }

    private void updateDataSchemeStatus(String taskKey, DeployStatusEnum statusEnum) {
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        DataSchemeDeployStatusDO statusDO = this.dataSchemeDeployStatusDao.getByDataSchemeKey(task.getDataScheme());
        if (statusDO != null) {
            statusDO.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(statusDO.getDeployStatus(), StatusBit.NEVER_GENERATE_SQL));
            statusDO.setDeployStatus(statusEnum);
            this.dataSchemeDeployStatusDao.update(statusDO);
        }
    }

    private void generateSql(String taskKey, TaskPlanPublish taskPlanPublish, List<String> ddlSqls) {
        this.deployExecutors.sort(Comparator.comparingDouble(NODDLDeployExecutor::getOrder));
        for (NODDLDeployExecutor executor : this.deployExecutors) {
            List strings = executor.preDeploy(taskKey);
            if (CollectionUtils.isEmpty(strings)) continue;
            ddlSqls.addAll(strings);
        }
        if (!ddlSqls.isEmpty()) {
            if (taskPlanPublish == null) {
                ddlSqls.add(String.format(UPDATE_SQL, new Object[]{PublishStatus.PUBLISHING, DDLStatusBitUtils.parseDDLStatusBit(PublishStatus.NEVER_PUBLISH, StatusBit.EXECUTE_SQL), taskKey}));
            } else {
                PublishStatus publishStatus = DDLStatusBitUtils.getPublishStatus(taskPlanPublish.getDdlStatusBit());
                ddlSqls.add(String.format(UPDATE_SQL, new Object[]{PublishStatus.PUBLISHING, DDLStatusBitUtils.parseDDLStatusBit(publishStatus, StatusBit.EXECUTE_SQL), taskKey}));
            }
        }
    }

    @Override
    public void cancelDeploy(String taskKey) {
        this.checkDDLModel();
        TaskPlanPublish taskPlanByTask = this.innerTaskPlanPublishDao.getByTask(taskKey);
        if (this.notExecuteDDL(taskPlanByTask)) {
            this.unlockDataScheme(taskKey);
            PublishStatus publishStatus = DDLStatusBitUtils.getPublishStatus(taskPlanByTask.getDdlStatusBit());
            taskPlanByTask.setPublishStatus(publishStatus.toString());
            taskPlanByTask.setDdlStatusBit(DDLStatusBitUtils.parseDDLStatusBit(publishStatus, StatusBit.NEVER_GENERATE_SQL));
            try {
                this.innerTaskPlanPublishDao.update(taskPlanByTask);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("\u5df2\u6267\u884cDDL\u8bed\u53e5\uff0c\u7981\u6b62\u64a4\u6d88\u53d1\u5e03\uff01");
        }
    }

    private boolean canGenerateSql(TaskPlanPublish byTask) {
        return byTask == null || !StatusBit.isExecuteSql(DDLStatusBitUtils.getStatusBit(byTask.getDdlStatusBit()));
    }

    private boolean notExecuteDDL(TaskPlanPublish byTask) {
        return !this.executeDDL(byTask);
    }

    @Override
    public boolean executeDDL(String taskKey) {
        return this.executeDDL(this.innerTaskPlanPublishDao.getByTask(taskKey));
    }

    public boolean executeDDL(TaskPlanPublish byTask) {
        if (Objects.isNull(byTask)) {
            throw new RuntimeException("\u672a\u751f\u6210SQL\uff0c\u8bf7\u751f\u6210SQL");
        }
        return StatusBit.isExecuteSql(DDLStatusBitUtils.getStatusBit(byTask.getDdlStatusBit()));
    }

    @Override
    public TaskPlanPublish getTaskPlanByTask(String taskKey) {
        return this.innerTaskPlanPublishDao.getByTask(taskKey);
    }

    @Override
    public boolean paramLocking(String taskKey) {
        TaskPlanPublish byTask = this.innerTaskPlanPublishDao.getByTask(taskKey);
        return byTask != null && PublishStatus.PARAM_LOCKING.toString().equals(byTask.getPublishStatus());
    }

    private TaskPlanPublish instanceTaskPlan(String taskKey) {
        NpContext context = NpContextHolder.getContext();
        TaskPlanPublish taskPlanPublish = new TaskPlanPublish();
        String userName = context.getUserName();
        String userKey = context.getUserId();
        taskPlanPublish.setKey(taskKey);
        taskPlanPublish.setTaskKey(taskKey);
        taskPlanPublish.setJobKey(JobCommon.getJobId());
        taskPlanPublish.setPublishDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        taskPlanPublish.setPublishStatus(PublishStatus.PUBLISHING.toString());
        taskPlanPublish.setWorkStatus(WorkStatus.DO.toString());
        taskPlanPublish.setUserKey(userKey);
        taskPlanPublish.setUserName(userName);
        return taskPlanPublish;
    }

    private void checkDDLModel() {
        if (!this.isEnableDDL()) {
            throw EXCEPTION;
        }
    }

    @Override
    public void checkByTask(String taskId) throws JQException {
        if (this.paramLocking(taskId)) {
            throw JQExceptionWrapper.wrapper("\u53c2\u6570\u9501\u5b9a\uff0c\u6682\u4e0d\u652f\u6301\u8c03\u6574\u53c2\u6570");
        }
    }

    @Override
    public boolean canCancel(String taskKey) {
        TaskPlanPublish taskPlanByTask = this.getTaskPlanByTask(taskKey);
        if (taskPlanByTask != null) {
            StatusBit statusBit = DDLStatusBitUtils.getStatusBit(taskPlanByTask.getDdlStatusBit());
            return statusBit != StatusBit.NEVER_GENERATE_SQL;
        }
        return false;
    }

    private static class DDLStatusBitUtils {
        private static final int SLAT = 100;

        private DDLStatusBitUtils() {
        }

        private static int parseDDLStatusBit(PublishStatus arg1, StatusBit arg2) {
            return (arg1.ordinal() + 1) * 100 + arg2.ordinal() + 1;
        }

        private static int parseDDLStatusBit(DeployStatusEnum arg1, StatusBit arg2) {
            return (arg1.ordinal() + 1) * 100 + arg2.ordinal() + 1;
        }

        private static StatusBit getStatusBit(int value) {
            return value > 0 ? StatusBit.values()[value % 100 - 1] : StatusBit.values()[0];
        }

        private static PublishStatus getPublishStatus(int value) {
            return value > 0 ? PublishStatus.values()[value / 100 - 1] : PublishStatus.values()[0];
        }

        private static DeployStatusEnum getDeployStatusEnum(int value) {
            return value > 0 ? DeployStatusEnum.values()[value / 100 - 1] : DeployStatusEnum.values()[4];
        }
    }

    private static enum StatusBit {
        NEVER_GENERATE_SQL,
        GENERATE_SQL,
        EXECUTE_SQL,
        DIRECT_RELEASE;


        private static boolean isNeverGenerateSql(StatusBit value) {
            return NEVER_GENERATE_SQL == value;
        }

        private static boolean isGenerateSql(StatusBit value) {
            return GENERATE_SQL == value;
        }

        private static boolean isExecuteSql(StatusBit value) {
            return EXECUTE_SQL == value;
        }

        private static boolean isDirectRelease(StatusBit value) {
            return DIRECT_RELEASE == value;
        }
    }
}

