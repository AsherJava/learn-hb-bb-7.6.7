/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.taskscheduling.lockmgr.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.taskscheduling.lockmgr.common.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.lockmgr.dao.TaskManageDao;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskManageServiceImpl
implements TaskManageService {
    private Logger logger = LoggerFactory.getLogger(TaskManageServiceImpl.class);
    @Autowired
    private TaskManageDao dao;

    @Override
    @Deprecated
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void initRunnerLock() {
    }

    @Override
    @Deprecated
    @Transactional(rollbackFor={Exception.class})
    public void updateRunnerLock(String taskName) {
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void initTaskManageByUnitCodes(List<String> unitCodes) {
        ArrayList<TaskManageDO> newTasks = new ArrayList<TaskManageDO>();
        Date beginTime = new Date();
        for (TaskTypeEnum taskType : TaskTypeEnum.values()) {
            TaskManageDO condi = new TaskManageDO();
            condi.setTaskName(taskType.getName());
            List<String> existUnitCodes = this.dao.getUnitCodesByTask(condi);
            Set existUnitCodeSet = existUnitCodes.stream().collect(Collectors.toSet());
            for (String unitCode : unitCodes) {
                if (existUnitCodeSet.contains(unitCode)) continue;
                newTasks.add(this.createTask(taskType.getName(), unitCode, beginTime));
            }
        }
        if (!newTasks.isEmpty()) {
            try {
                this.dao.insert(newTasks);
            }
            catch (Exception e) {
                this.logger.error("\u4efb\u52a1\u63d2\u5165\u5931\u8d25", e);
            }
        }
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void initTaskManageByUnitCodes(String taskName, List<String> unitCodes, Date beginTime) {
        ArrayList<TaskManageDO> newTasks = new ArrayList<TaskManageDO>();
        TaskManageDO condi = new TaskManageDO();
        condi.setTaskName(taskName);
        List<String> existUnitCodes = this.dao.getUnitCodesByTask(condi);
        Set existUnitCodeSet = existUnitCodes.stream().collect(Collectors.toSet());
        for (String unitCode : unitCodes) {
            if (existUnitCodeSet.contains(unitCode)) continue;
            newTasks.add(this.createTask(taskName, unitCode, beginTime));
        }
        if (!newTasks.isEmpty()) {
            try {
                this.dao.insert(newTasks);
            }
            catch (Exception e) {
                this.logger.error("\u4efb\u52a1\u63d2\u5165\u5931\u8d25", e);
            }
        }
    }

    private TaskManageDO createTask(String taskName, String unitCode, Date beginTime) {
        TaskManageDO task = new TaskManageDO(taskName, unitCode, new Timestamp(beginTime.getTime()), 0);
        task.setId(UUIDUtils.newUUIDStr());
        task.setVer(System.currentTimeMillis());
        return task;
    }

    @Override
    public TaskManageDO getTaskManageByName(TaskTypeEnum taskType, String unitCode) {
        return this.getTaskManageByName(taskType.getName(), unitCode);
    }

    @Override
    public TaskManageDO getTaskManageByName(String taskType, String unitCode) {
        return this.dao.getTaskManageByName(taskType, unitCode);
    }

    @Override
    public List<TaskManageDO> getTaskMangeListByName(TaskTypeEnum taskType) {
        return this.dao.getTaskMangeListByName(taskType.getName());
    }

    @Override
    public void updateBeginHandle(TaskTypeEnum taskType, String unitCode) {
        this.updateBeginHandle(taskType.getName(), unitCode, new Date());
    }

    @Override
    public void updateBeginHandle(String taskType, String unitCode, Date beginTime) {
        TaskManageDO condi = new TaskManageDO(taskType, unitCode, new Timestamp(beginTime.getTime()), null);
        this.dao.updateBeginHandle(condi);
    }

    @Override
    public void updateEndHandle(String taskName, String unitCode, int batchNum) {
        TaskManageDO condi = new TaskManageDO(taskName, unitCode, null, batchNum);
        this.dao.updateEndHandle(condi);
    }

    @Override
    public void updateEndHandle(TaskTypeEnum taskType, String unitCode, int batchNum) {
        TaskManageDO condi = new TaskManageDO(taskType.getName(), unitCode, null, batchNum);
        this.dao.updateEndHandle(condi);
    }

    @Override
    @Deprecated
    public void updateVer(String taskType, String unitCode) {
    }
}

