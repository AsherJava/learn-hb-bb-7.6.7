/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.carryover.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverTaskProcessDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverTaskProcessEO;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.core.context.NpContextHolder;
import java.lang.ref.SoftReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcCarryOverProcessServiceImpl
implements GcCarryOverProcessService {
    private static ConcurrentHashMap<String, SoftReference<TaskLog>> taskLogMap = new ConcurrentHashMap();
    private Date nextRemovedDate = new Date();
    @Autowired
    private CarryOverTaskProcessDao carryOverTaskProcessDao;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void createTaskProcess(QueryParamsVO queryParamsVO) {
        CarryOverTaskProcessEO taskProcessEO = this.buildCarryOverProcessEO(queryParamsVO);
        this.carryOverTaskProcessDao.add((BaseEntity)taskProcessEO);
    }

    @Override
    public CarryOverTaskProcessVO queryTaskProcess(String taskId) {
        CarryOverTaskProcessEO taskProcessEO = new CarryOverTaskProcessEO();
        taskProcessEO.setId(taskId);
        CarryOverTaskProcessEO eo = (CarryOverTaskProcessEO)this.carryOverTaskProcessDao.selectByEntity((BaseEntity)taskProcessEO);
        return this.convertProcessEO2VO(eo);
    }

    @Override
    public void setTaskProcess(String index, TaskLog taskLog) {
        SoftReference<TaskLog> taskLogSoftReference = new SoftReference<TaskLog>(taskLog);
        taskLogMap.put(index, taskLogSoftReference);
    }

    @Override
    public TaskLog getTaskProcess(String index) {
        SoftReference<TaskLog> taskLogSoftReference = taskLogMap.get(index);
        if (null == taskLogSoftReference) {
            return null;
        }
        return taskLogSoftReference.get();
    }

    @Override
    public void removeTaskLog(String index) {
        taskLogMap.remove(index);
        Date currDate = new Date();
        if (this.nextRemovedDate.before(currDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(5, -2);
            Date beforeTwoDay = calendar.getTime();
            for (Map.Entry<String, SoftReference<TaskLog>> uuidSoftReferenceEntry : taskLogMap.entrySet()) {
                SoftReference<TaskLog> taskLogSoftReference = uuidSoftReferenceEntry.getValue();
                TaskLog taskLog = taskLogSoftReference.get();
                if (null == taskLog) {
                    taskLogMap.remove(uuidSoftReferenceEntry.getKey());
                    continue;
                }
                if (null == taskLog || null == taskLog.getCreateDate() || !taskLog.getCreateDate().before(beforeTwoDay)) continue;
                taskLogMap.remove(uuidSoftReferenceEntry.getKey());
            }
            this.nextRemovedDate = currDate;
        }
    }

    @Override
    public TaskLog getTaskLog(String taskLogId) {
        Assert.isNotNull((Object)taskLogId, (String)"\u83b7\u53d6\u5373\u65f6\u65e5\u5fd7\u7684ID\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        TaskLog taskProcess = this.getTaskProcess(taskLogId);
        if (null == taskProcess) {
            return new TaskLog();
        }
        if (taskProcess.isFinish()) {
            this.removeTaskLog(taskLogId);
        }
        return taskProcess;
    }

    private CarryOverTaskProcessEO buildCarryOverProcessEO(QueryParamsVO queryParamsVO) {
        CarryOverTaskProcessEO eo = new CarryOverTaskProcessEO();
        eo.setId(queryParamsVO.getTaskLogId());
        eo.setAcctYear(ConverterUtils.getAsString((Object)queryParamsVO.getAcctYear()));
        eo.setCreateTime(new Date());
        eo.setProcess(0.0);
        eo.setTargetSystemId(queryParamsVO.getConsSystemId());
        eo.setCarryOverSchemeId(queryParamsVO.getCarryOverSchemeId());
        eo.setNrTaskId(queryParamsVO.getTaskId());
        eo.setUserName(NpContextHolder.getContext().getUserName());
        eo.setTaskState(TaskStateEnum.EXECUTING.getCode());
        List orgIds = queryParamsVO.getOrgList().stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        String orgIdStr = String.join((CharSequence)",", orgIds);
        eo.setOrgId(orgIdStr);
        return eo;
    }

    private CarryOverTaskProcessVO convertProcessEO2VO(CarryOverTaskProcessEO eo) {
        CarryOverTaskProcessVO vo = new CarryOverTaskProcessVO();
        BeanUtils.copyProperties((Object)eo, vo);
        return vo;
    }
}

