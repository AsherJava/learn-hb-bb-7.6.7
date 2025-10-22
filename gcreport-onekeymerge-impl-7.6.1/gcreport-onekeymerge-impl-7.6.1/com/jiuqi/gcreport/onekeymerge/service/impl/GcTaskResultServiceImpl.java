/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.gcreport.onekeymerge.dao.TaskResultDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.service.GcTaskResultService;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcTaskResultServiceImpl
implements GcTaskResultService {
    @Autowired
    private TaskResultDao taskResultDao;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public GcTaskResultEO saveTaskResult(GcTaskResultEO taskResultEO) {
        this.taskResultDao.save(taskResultEO);
        return taskResultEO;
    }

    @Override
    public String getResultByTaskCodeAndGroupId(String taskCode, String taskLogId) {
        return this.taskResultDao.getResultEOByTaskCodeAndGroupId(taskCode, taskLogId);
    }

    @Override
    public Map<String, GcTaskResultVO> getResultByGroupId(String taskLogId) {
        return this.taskResultDao.getResultEOByGroupId(taskLogId);
    }
}

