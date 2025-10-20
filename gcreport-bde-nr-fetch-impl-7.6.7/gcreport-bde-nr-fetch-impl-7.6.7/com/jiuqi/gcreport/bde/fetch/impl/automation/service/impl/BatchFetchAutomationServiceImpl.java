/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationParameterDTO;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationResult;
import com.jiuqi.gcreport.bde.fetch.impl.automation.service.FetchAutomationService;
import com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BatchFetchAutomationServiceImpl
implements FetchAutomationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchFetchAutomationServiceImpl.class);
    private Integer fetchSleepTime;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    @Override
    public FetchAutomationResult doFetch(FetchAutomationParameterDTO parameter) {
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        BusinessResponseEntity startTask = null;
        TaskParamVO taskParam = new TaskParamVO();
        taskParam.setPreParam(JsonUtils.writeValueAsString((Object)parameter));
        taskParam.setExt_1(parameter.getUser());
        taskParam.setExt_2(parameter.getEtlKey());
        try {
            startTask = taskHandlerClient.startTaskWithExtInfo("AUTOMATION_FETCH", taskParam);
        }
        catch (Exception e) {
            if (e.getMessage().contains("Feign - 500 Read Out Time")) {
                throw new BusinessRuntimeException("BDE\u5730\u5740\u8fde\u63a5\u8d85\u65f6\uff0c\u8bf7\u68c0\u67e5");
            }
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (!startTask.isSuccess()) {
            throw new BusinessRuntimeException(startTask.getErrorMessage());
        }
        double successVal = 1.0;
        long timeOutTime = this.getTimeOutTimeMillis();
        while (!NumberUtils.compareDouble((double)this.getTaskProcess(taskHandlerClient, (String)startTask.getData()), (double)1.0)) {
            if (timeOutTime != 0L && System.currentTimeMillis() >= timeOutTime) {
                return new FetchAutomationResult().failure("ETL\u81ea\u52a8\u5316\u5bf9\u8c61\u6279\u91cf\u63a5\u53e3\u53d6\u6570\u8d85\u65f6");
            }
            try {
                Thread.sleep(this.fetchSleepTime.intValue());
            }
            catch (InterruptedException e) {
                LOGGER.error("\u51fa\u73b0\u4e2d\u65ad\u5f02\u5e38:{}", (Object)e.getMessage(), (Object)e);
                Thread.currentThread().interrupt();
                return new FetchAutomationResult().failure("ETL\u81ea\u52a8\u5316\u5bf9\u8c61\u53d6\u6570\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:\u51fa\u73b0\u4e2d\u65ad\u5f02\u5e38\uff1a" + e.getMessage());
            }
        }
        return new FetchAutomationResult().success();
    }

    private Double getTaskProcess(TaskHandlerClient taskHandlerClient, String runnerId) {
        BusinessResponseEntity taskProcess = taskHandlerClient.getTaskProgress(runnerId);
        if (!taskProcess.isSuccess()) {
            throw new BusinessRuntimeException(taskProcess.getErrorMessage());
        }
        return (Double)taskProcess.getData();
    }

    private long getTimeOutTimeMillis() {
        Integer timeOutOptionValue = ConverterUtils.getAsInteger((Object)BdeSystemOptionTool.getOptionValue((String)"AUTOMATION_BATCH_FETCH_TIMEOUT"));
        if (timeOutOptionValue == null || timeOutOptionValue <= 0) {
            return 0L;
        }
        return System.currentTimeMillis() + (long)(timeOutOptionValue * 60 * 1000);
    }

    @Value(value="${bde.fetch.sleep-time:500}")
    public void setFetchSleepTime(Integer fetchSleepTime) {
        if (fetchSleepTime != null && fetchSleepTime >= 150 && fetchSleepTime <= 5000) {
            this.fetchSleepTime = fetchSleepTime;
        }
    }
}

