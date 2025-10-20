/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.carryover.enums.CarryOverAsyncTaskPoolType
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.exception.OutOfQueueException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.carryover.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogDao;
import com.jiuqi.gcreport.carryover.dao.CarryOverTaskProcessDao;
import com.jiuqi.gcreport.carryover.dao.CarryOverTaskRelDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.entity.CarryOverTaskProcessEO;
import com.jiuqi.gcreport.carryover.entity.CarryOverTaskRelEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverAsyncTaskPoolType;
import com.jiuqi.gcreport.carryover.service.GcCarryOverTaskService;
import com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor;
import com.jiuqi.gcreport.carryover.utils.CarryOverLogUtil;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.exception.OutOfQueueException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcCarryOverTaskServiceImpl
implements GcCarryOverTaskService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CarryOverLogDao carryOverLogDao;
    @Autowired
    private CarryOverTaskRelDao carryOverTaskRelDao;
    @Autowired
    private CarryOverTaskProcessDao carryOverTaskProcessDao;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private AsyncTaskTypeCollecter asyncTaskTypeCollecter;

    @Override
    public CarryOverTaskProcessEO getProcess(String processId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void createAsyncTask(GcCarryOverTaskExecutor taskExecutor, QueryParamsVO queryParamsVO) {
        List<CarryOverLogEO> tasks = taskExecutor.createTasks(queryParamsVO);
        this.carryOverLogDao.addBatch(tasks);
        CarryOverTaskProcessEO updateTaskProcessEO = new CarryOverTaskProcessEO();
        updateTaskProcessEO.setId(queryParamsVO.getTaskLogId());
        updateTaskProcessEO.setProcess(0.1);
        updateTaskProcessEO.setTotalTaskCount(Long.valueOf(tasks.size()));
        updateTaskProcessEO.setFinishedTaskCount(0L);
        this.carryOverTaskProcessDao.updateSelective((BaseEntity)updateTaskProcessEO);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Async
    public void publishAsyncTask(GcCarryOverTaskExecutor taskExecutor, QueryParamsVO queryParamsVO, NpContext npContext) {
        try {
            NpContextHolder.setContext((NpContext)npContext);
            String taskGroupId = queryParamsVO.getTaskLogId();
            GcCarryOverTaskServiceImpl taskService = (GcCarryOverTaskServiceImpl)SpringBeanUtils.getBean(GcCarryOverTaskServiceImpl.class);
            int totalCount = this.carryOverLogDao.countByGroupId(taskGroupId);
            while (true) {
                try {
                    taskService.updateTaskState(taskGroupId, totalCount);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
                CarryOverLogEO taskParam = new CarryOverLogEO();
                taskParam.setGroupId(taskGroupId);
                taskParam.setTaskState(TaskStateEnum.WAITTING.getCode());
                List waitTasks = this.carryOverLogDao.selectList((BaseEntity)taskParam);
                if (waitTasks.size() <= 0) break;
                taskService.publishAsyncTask(taskExecutor, waitTasks, queryParamsVO);
                try {
                    TimeUnit.SECONDS.sleep(2L);
                }
                catch (InterruptedException interruptedException) {}
            }
            while (true) {
                boolean existExecutingTaskFlag;
                if (!(existExecutingTaskFlag = taskService.updateTaskState(taskGroupId, totalCount))) {
                    CarryOverTaskProcessEO processEO = new CarryOverTaskProcessEO();
                    processEO.setId(taskGroupId);
                    processEO.setFinishTime(new Date());
                    processEO.setProcess(1.0);
                    processEO.setTaskState(TaskStateEnum.SUCCESS.getCode());
                    this.carryOverTaskProcessDao.updateSelective((BaseEntity)processEO);
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void publishAsyncTask(GcCarryOverTaskExecutor taskExecutor, List<CarryOverLogEO> tasks, QueryParamsVO queryParamsVO) {
        ArrayList<CarryOverTaskRelEO> insertTaskRelEOs = new ArrayList<CarryOverTaskRelEO>();
        ArrayList<CarryOverLogEO> updateTaskEOs = new ArrayList<CarryOverLogEO>();
        Map<String, GcOrgCacheVO> orgId2SelfMap = queryParamsVO.getOrgList().stream().collect(Collectors.toMap(GcOrgCacheVO::getCode, item -> item));
        CarryOverAsyncTaskPoolType asyncTaskPoolType = CarryOverAsyncTaskPoolType.getEnumByCode((String)queryParamsVO.getTypeCode());
        Assert.isNotNull((Object)asyncTaskPoolType, (String)("\u672a\u521b\u5efa\u4efb\u52a1" + queryParamsVO.getTypeCode() + "\u7684\u6267\u884c\u5668"), (Object[])new Object[0]);
        Integer freePoolCount = this.getFreeTaskPoolSize(asyncTaskPoolType.getName());
        if (freePoolCount == 0) {
            this.logger.info("\u4efb\u52a1\uff1a" + asyncTaskPoolType.getName() + " \u5f02\u6b65\u4efb\u52a1\u961f\u5217\u5df2\u6ee1\uff0c\u6682\u65f6\u505c\u6b62\u521b\u5efa\u4efb\u52a1\u3002");
            return;
        }
        if (tasks.size() > freePoolCount) {
            tasks = tasks.subList(0, freePoolCount);
        }
        for (CarryOverLogEO taskEO : tasks) {
            QueryParamsVO newQueryParamsVO = (QueryParamsVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)queryParamsVO), QueryParamsVO.class);
            GcOrgCacheVO orgCacheVO = orgId2SelfMap.get(CarryOverLogUtil.getOrgInfo(taskEO).get(0).getCode());
            newQueryParamsVO.setOrgList(Collections.singletonList(orgCacheVO));
            newQueryParamsVO.setTaskLogId(taskEO.getId());
            try {
                String asyncTaskId = taskExecutor.publishTask(newQueryParamsVO);
                CarryOverTaskRelEO taskRelEO = this.buildMergeTaskRelEO(asyncTaskId, taskEO);
                insertTaskRelEOs.add(taskRelEO);
                taskEO.setTaskState(TaskStateEnum.EXECUTING.getCode());
                taskEO.setStartTime(new Date());
                updateTaskEOs.add(taskEO);
            }
            catch (OutOfQueueException e) {
                break;
            }
        }
        if (!CollectionUtils.isEmpty(insertTaskRelEOs)) {
            this.carryOverTaskRelDao.addBatch(insertTaskRelEOs);
        }
        if (!CollectionUtils.isEmpty(updateTaskEOs)) {
            this.carryOverLogDao.updateBatch(updateTaskEOs);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public boolean updateTaskState(String groupId, int totalCount) {
        HashSet<String> updateSuccessTaskIds = new HashSet<String>();
        HashSet<String> updateErrorTaskIds = new HashSet<String>();
        HashSet<String> updateProcessingTaskIds = new HashSet<String>();
        ArrayList<CarryOverTaskRelEO> updateTaskRels = new ArrayList<CarryOverTaskRelEO>();
        HashMap<String, String> taskId2AsyncTaskIdMap = new HashMap<String, String>();
        ArrayList<String> asyncTaskIds = new ArrayList<String>();
        List<CarryOverTaskRelEO> taskRelEOS = this.carryOverTaskRelDao.listByGroupIdAndState(groupId, TaskStateEnum.EXECUTING.getCode());
        if (CollectionUtils.isEmpty(taskRelEOS)) {
            return false;
        }
        Map<String, Integer> asyncTaskId2State = this.carryOverTaskRelDao.getAsyncTaskId2State(groupId);
        for (CarryOverTaskRelEO taskRelEO : taskRelEOS) {
            Integer asyncTaskState = asyncTaskId2State.get(taskRelEO.getAsyncTaskId());
            if (null == asyncTaskState) continue;
            if (TaskState.FINISHED.getValue() == asyncTaskState.intValue()) {
                taskRelEO.setTaskState(TaskStateEnum.SUCCESS.getCode());
                updateTaskRels.add(taskRelEO);
                updateSuccessTaskIds.add(taskRelEO.getCarryoverTaskId());
                taskId2AsyncTaskIdMap.put(taskRelEO.getCarryoverTaskId(), taskRelEO.getAsyncTaskId());
                asyncTaskIds.add(taskRelEO.getAsyncTaskId());
                continue;
            }
            if (TaskState.ERROR.getValue() == asyncTaskState.intValue()) {
                taskRelEO.setTaskState(TaskStateEnum.ERROR.getCode());
                updateTaskRels.add(taskRelEO);
                updateErrorTaskIds.add(taskRelEO.getCarryoverTaskId());
                taskId2AsyncTaskIdMap.put(taskRelEO.getCarryoverTaskId(), taskRelEO.getAsyncTaskId());
                asyncTaskIds.add(taskRelEO.getAsyncTaskId());
                continue;
            }
            if (TaskState.PROCESSING.getValue() != asyncTaskState.intValue()) continue;
            updateProcessingTaskIds.add(taskRelEO.getCarryoverTaskId());
            taskId2AsyncTaskIdMap.put(taskRelEO.getCarryoverTaskId(), taskRelEO.getAsyncTaskId());
            asyncTaskIds.add(taskRelEO.getAsyncTaskId());
        }
        ArrayList<CarryOverLogEO> updateTaskEOs = new ArrayList<CarryOverLogEO>();
        if (!CollectionUtils.isEmpty(updateProcessingTaskIds)) {
            List<CarryOverLogEO> processingTaskEOs = this.carryOverLogDao.listByIds(updateProcessingTaskIds);
            for (CarryOverLogEO taskEO : processingTaskEOs) {
                Double process = this.asyncTaskManager.queryProcess(taskId2AsyncTaskIdMap.getOrDefault(taskEO.getId(), ""));
                taskEO.setProcess(process);
                updateTaskEOs.add(taskEO);
            }
        }
        if (!CollectionUtils.isEmpty(updateTaskRels)) {
            HashMap<String, Object> queryDetailsMap = new HashMap<String, Object>(asyncTaskIds.size());
            for (String asyncTaskId : asyncTaskIds) {
                Object detail = this.asyncTaskManager.queryDetail(asyncTaskId);
                if (ObjectUtils.isEmpty((Object)detail)) {
                    detail = this.asyncTaskManager.queryResult(asyncTaskId);
                }
                queryDetailsMap.put(asyncTaskId, detail);
            }
            this.carryOverTaskRelDao.updateBatch(taskRelEOS);
            HashSet<String> totalUpdateMergeTaskIds = new HashSet<String>(updateSuccessTaskIds);
            totalUpdateMergeTaskIds.addAll(updateErrorTaskIds);
            List<CarryOverLogEO> taskEOList = this.carryOverLogDao.listByIds(totalUpdateMergeTaskIds);
            for (CarryOverLogEO taskEO : taskEOList) {
                if (updateSuccessTaskIds.contains(taskEO.getId())) {
                    taskEO.setTaskState(TaskStateEnum.SUCCESS.getCode());
                } else {
                    taskEO.setTaskState(TaskStateEnum.ERROR.getCode());
                }
                taskEO.setEndTime(new Date());
                taskEO.setProcess(1.0);
                String detail = queryDetailsMap.getOrDefault(taskId2AsyncTaskIdMap.getOrDefault(taskEO.getId(), ""), "");
                if (Objects.nonNull(detail)) {
                    taskEO.setInfo(ConverterUtils.getAsString((Object)detail));
                } else {
                    taskEO.setInfo("\u4efb\u52a1\u6267\u884c\u5931\u8d25\u3002");
                }
                updateTaskEOs.add(taskEO);
            }
        }
        if (!CollectionUtils.isEmpty(updateTaskEOs)) {
            this.carryOverLogDao.updateBatch(updateTaskEOs);
        }
        long finishedCount = updateSuccessTaskIds.size() + updateErrorTaskIds.size();
        double process = NumberUtils.round((double)((double)finishedCount / (double)totalCount * 0.9), (int)2, (int)1);
        if (finishedCount > 0L && process > 0.0) {
            this.carryOverTaskProcessDao.updateProcess(finishedCount, process, groupId);
        }
        return true;
    }

    private Integer getFreeTaskPoolSize(String taskPoolType) {
        Integer queueSize = this.asyncTaskTypeCollecter.getQueueSize(taskPoolType);
        if (queueSize < 0) {
            return -1;
        }
        List taskList = this.asyncTaskDao.queryByTaskPool(taskPoolType, TaskState.WAITING);
        return Math.max(0, queueSize - taskList.size());
    }

    private CarryOverTaskRelEO buildMergeTaskRelEO(String asyncTaskId, CarryOverLogEO taskEO) {
        CarryOverTaskRelEO mergeTaskRelEO = new CarryOverTaskRelEO();
        mergeTaskRelEO.setId(UUIDUtils.newUUIDStr());
        mergeTaskRelEO.setCarryoverTaskId(taskEO.getId());
        mergeTaskRelEO.setAsyncTaskId(asyncTaskId);
        mergeTaskRelEO.setGroupId(taskEO.getGroupId());
        mergeTaskRelEO.setTaskState(TaskStateEnum.EXECUTING.getCode());
        return mergeTaskRelEO;
    }
}

