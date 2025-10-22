/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.ReturnObject
 *  com.jiuqi.gcreport.temp.dto.Message$ProgressResult
 *  com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyMergeTaskService;
import com.jiuqi.gcreport.onekeymerge.task.GcCenterTask;
import com.jiuqi.gcreport.onekeymerge.util.OneKeyMergeUtils;
import com.jiuqi.gcreport.onekeymerge.util.OnekeyMergeFactory;
import com.jiuqi.gcreport.onekeymerge.util.TaskTypeEnum;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class GcOnekeyMergeTaskServiceImpl
implements GcOnekeyMergeTaskService {
    @Autowired
    private ProgressService<OnekeyProgressDataImpl, Message.ProgressResult> progressService;

    @Override
    public ReturnObject doTask(GcActionParamsVO paramsVO, TaskTypeEnum taskTypeEnum) {
        this.initTaskProgress(paramsVO, taskTypeEnum);
        GcCenterTask commonTaskService = OnekeyMergeFactory.getTaskByClazz(taskTypeEnum.getClazz());
        return commonTaskService.doTask(paramsVO);
    }

    @Override
    @Async
    public Future<Boolean> doTaskAsync(GcActionParamsVO paramsVO, TaskTypeEnum taskTypeEnum) {
        this.initTaskProgress(paramsVO, taskTypeEnum);
        GcCenterTask commonTaskService = OnekeyMergeFactory.getTaskByClazz(taskTypeEnum.getClazz());
        NpContextHolder.setContext((NpContext)paramsVO.getNpContext());
        GcOrgTypeUtils.setContextEntityId((String)paramsVO.getOrgType());
        commonTaskService.doTask(paramsVO);
        return new AsyncResult<Boolean>(true);
    }

    private void initTaskProgress(GcActionParamsVO paramsVO, TaskTypeEnum taskTypeEnum) {
        OnekeyProgressDataImpl onekeyProgressData = new OnekeyProgressDataImpl(OneKeyMergeUtils.generateSN(taskTypeEnum.getCode(), paramsVO.getTaskLogId()));
        this.progressService.createProgressData((ProgressData)onekeyProgressData);
        paramsVO.setOnekeyProgressData(onekeyProgressData);
    }
}

