/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.service.impl;

import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationParameterDTO;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationResult;
import com.jiuqi.gcreport.bde.fetch.impl.automation.service.FetchAutomationService;
import com.jiuqi.gcreport.bde.fetch.impl.automation.utils.BdeFetchAutomationUtil;
import com.jiuqi.gcreport.bde.fetch.impl.config.FetchTimeConfig;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchService;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchAutomationServiceImpl
implements FetchAutomationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchAutomationServiceImpl.class);
    @Autowired
    private GcFetchService fetchService;
    @Autowired
    private FetchTimeConfig fetchTimeConfig;

    @Override
    public FetchAutomationResult doFetch(FetchAutomationParameterDTO parameter) {
        BdeCommonUtil.initNpUser((String)parameter.getUser());
        EfdcInfo efdcInfo = BdeFetchAutomationUtil.buildEfdcInfoByParam(parameter);
        try {
            AsyncTaskInfo taskInfo = this.fetchService.fetchData(efdcInfo);
            if (TaskState.PROCESSING != taskInfo.getState() && TaskState.WAITING != taskInfo.getState()) {
                throw new BusinessRuntimeException(taskInfo.getResult());
            }
            boolean flag = false;
            AsyncTaskInfo taskState = null;
            while (!flag) {
                taskState = this.fetchService.queryFetchTask(taskInfo.getId());
                if (TaskState.FINISHED == taskState.getState() || TaskState.ERROR == taskState.getState()) {
                    flag = true;
                }
                try {
                    Thread.sleep(this.fetchTimeConfig.getPlanTaskFetchSleepTime());
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u5931\u8d25: {}", (Object)e.getMessage(), (Object)e);
                    throw new BdeRuntimeException("\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u5931\u8d25");
                }
            }
            String resultLog = (String)taskState.getDetail();
            String string = resultLog = StringUtils.isEmpty((String)resultLog) ? "{}" : resultLog;
            if (TaskState.FINISHED == taskState.getState()) {
                return new FetchAutomationResult().success();
            }
            return new FetchAutomationResult().failure(resultLog);
        }
        catch (Exception e) {
            LOGGER.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u51fa\u73b0\u9519\u8bef,\u8be6\u7ec6\u4fe1\u606f\uff1a{}", (Object)e.getMessage(), (Object)e);
            return new FetchAutomationResult().failure("\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u51fa\u73b0\u9519\u8bef,\u8be6\u7ec6\u4fe1\u606f\uff1a" + e.getMessage());
        }
    }
}

