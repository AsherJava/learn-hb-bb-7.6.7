/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService
 */
package com.jiuqi.bde.log.fetch.service.impl;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.fetch.dao.FetchTaskLogDao;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.dto.FetchLogDTO;
import com.jiuqi.bde.log.fetch.service.FetchTaskLogService;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.service.TaskLogService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class FetchTaskLogServiceImpl
implements FetchTaskLogService {
    private Logger logger = LoggerFactory.getLogger(FetchTaskLogServiceImpl.class);
    @Autowired
    private FetchTaskLogDao fetchTaskLogDao;
    @Autowired
    @Lazy
    private TaskLogService taskLogService;

    @Override
    public FetchInitTaskDTO getLogVo(String runnerId) {
        DcTaskLogEO log = this.fetchTaskLogDao.selectLog(runnerId);
        if (log == null || StringUtils.isEmpty((String)log.getMessage())) {
            return null;
        }
        return (FetchInitTaskDTO)JsonUtils.readValue((String)log.getMessage(), FetchInitTaskDTO.class);
    }

    @Override
    public int countUnFinishBatchTask(String runnerId) {
        return this.fetchTaskLogDao.countUnFinishBatchTask(runnerId);
    }

    @Override
    public boolean existExecutingTask(String dimKey) {
        return this.fetchTaskLogDao.existExecutingTask(dimKey);
    }

    @Override
    public int getUnFinishTaskItemCount(String runnerId, String taskId) {
        return this.fetchTaskLogDao.getUnFinishTaskItemCount(runnerId, taskId);
    }

    @Override
    public List<DcTaskItemLogEO> getFailedTaskItem(String runnerId, String taskId) {
        return this.fetchTaskLogDao.getFailedTaskItem(runnerId, taskId);
    }

    @Override
    @OuterTransaction
    public void insertTaskLog(DcTaskLogEO taskInfo) {
        this.taskLogService.insertTaskLogs(taskInfo, (List)CollectionUtils.newArrayList());
    }

    @Override
    @OuterTransaction
    public void insertTaskItemLog(DcTaskItemLogEO taskItemInfo) {
        ArrayList itemInfoList = CollectionUtils.newArrayList();
        itemInfoList.add(taskItemInfo);
        this.taskLogService.insertTaskItemLogs((List)itemInfoList);
    }

    @Override
    @OuterTransaction
    public void updateTaskItemResultById(String requestTaskId, DataHandleState handleState, String resultLog) {
        this.taskLogService.updateTaskItemResultById(requestTaskId, handleState, resultLog);
    }

    @Override
    public FetchTaskInfoDTO queryTaskInfo(FetchTaskInfoQueryDTO taskInfoQueryDTO) {
        Assert.isNotNull((Object)taskInfoQueryDTO);
        Assert.isNotEmpty((String)taskInfoQueryDTO.getAsyncTaskId(), (String)"\u53d6\u6570\u65e5\u5fd7\u5df2\u7ecf\u88ab\u6e05\u9664", (Object[])new Object[0]);
        FetchTaskInfoDTO taskInfo = new FetchTaskInfoDTO();
        String runnerId = taskInfoQueryDTO.getAsyncTaskId();
        if (StringUtils.isEmpty((String)runnerId)) {
            return taskInfo;
        }
        taskInfo.setRunnerId(runnerId);
        if (!StringUtils.isEmpty((String)taskInfoQueryDTO.getFormId()) && !StringUtils.isEmpty((String)taskInfoQueryDTO.getRegionId())) {
            String instanceId = this.fetchTaskLogDao.selectInstanceIdByCondi(runnerId, taskInfoQueryDTO);
            taskInfo.setInstanceId(instanceId);
        }
        return taskInfo;
    }

    @Override
    public FetchItemLogDTO queryFailTask(String runnerId) {
        return this.fetchTaskLogDao.queryFailTask(runnerId);
    }

    @Override
    public FetchLogDTO queryFailBatchTask(String batchRunnerId) {
        return this.fetchTaskLogDao.queryFailBatchTask(batchRunnerId);
    }

    @Override
    public void waitFetchTaskFinished(String runnerId) {
        Integer unExecuteCount;
        while ((unExecuteCount = this.taskLogService.getUnFinishTaskItemCount(runnerId)) != null && unExecuteCount != 0) {
            try {
                Thread.sleep(BdeCommonUtil.getTaskSleepTime());
            }
            catch (InterruptedException e) {
                this.logger.error("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void waitFetchTaskFinishedChkTimeout(String runnerId, Long duration) {
    }

    @Override
    public int getUnFinishTaskItemCount(String runnerId) {
        return this.taskLogService.getUnFinishTaskItemCount(runnerId);
    }

    @Override
    @OuterTransaction
    public void updateTaskFinished(String runnerId) {
        this.fetchTaskLogDao.updateTaskFinished(runnerId);
    }
}

