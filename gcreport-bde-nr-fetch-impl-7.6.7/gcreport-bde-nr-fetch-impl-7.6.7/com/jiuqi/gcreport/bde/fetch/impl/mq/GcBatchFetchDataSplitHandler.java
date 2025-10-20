/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.common.constant.FetchTaskState
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO
 *  com.jiuqi.bde.log.fetch.service.FetchTaskLogService
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil
 *  com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.gcreport.bde.fetch.impl.mq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.common.constant.FetchTaskState;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.service.FetchTaskLogService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.core.msg.TaskHandleMsg;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.service.GcFetchDataExecuteService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.utils.FetchTaskUtil;
import com.jiuqi.gcreport.sdk.util.BdeSystemOptionTool;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GcBatchFetchDataSplitHandler
implements ITaskHandler {
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private FetchTaskLogService fetchLogService;
    @Autowired
    private GcFetchDataExecuteService executeService;
    private Logger logger = LoggerFactory.getLogger(GcBatchFetchDataSplitHandler.class);
    private static final String SPECIAL_QUEUE_FLAG = "BATCH_FETCH_SPLIT";

    public String getName() {
        return FetchTaskType.NR_BATCH_FETCH_UNIT.getCode();
    }

    public String getTitle() {
        return FetchTaskType.NR_BATCH_FETCH_UNIT.getTitle();
    }

    public String getPreTask() {
        return FetchTaskType.NR_BATCH_FETCH.getCode();
    }

    public Map<String, String> getHandleParams(String preParam) {
        List fetchInitTaskList = (List)JsonUtils.readValue((String)preParam, (TypeReference)new TypeReference<List<FetchInitTaskDTO>>(){});
        HashMap<String, String> params = new HashMap<String, String>();
        for (FetchInitTaskDTO fetchInitTask : fetchInitTaskList) {
            params.put(JsonUtils.writeValueAsString((Object)fetchInitTask), fetchInitTask.getUnitCode());
        }
        return params;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        TaskHandleResult result = new TaskHandleResult();
        result.setPreParam(param);
        result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
        try {
            FetchInitTaskDTO fetchInitTask = (FetchInitTaskDTO)JsonUtils.readValue((String)param, FetchInitTaskDTO.class);
            BdeCommonUtil.initNpUser((String)fetchInitTask.getUserName());
            FetchTaskUtil.buildNrCtxByOrgType((String)fetchInitTask.getRpUnitType());
            TaskHandleMsg handleMsg = (TaskHandleMsg)ThreadContext.get((Object)"TASKHANDLEMSG_KEY");
            String batchId = handleMsg.getRunnerId();
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            TaskParamVO taskParam = new TaskParamVO();
            taskParam.setPreParam(JsonUtils.writeValueAsString((Object)fetchInitTask));
            taskParam.setExt_1(batchId);
            taskParam.setExt_2(fetchInitTask.getUnitCode());
            taskParam.setExt_3(fetchInitTask.getUsername());
            taskParam.setExt_4(UUIDUtils.newHalfGUIDStr());
            taskParam.setExt_5(FetchTaskState.UNEXECUTE.getCode());
            String runnerId = (String)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.startTaskWithExtInfo(FetchTaskType.NR_FETCH.getCode(), taskParam));
            if (!StringUtils.hasText(runnerId)) {
                result.setSuccess(Boolean.valueOf(false));
                result.appendLog("\u6ca1\u6709\u627e\u5230\u9700\u8981\u53d6\u6570\u7684\u62a5\u8868\u3002");
                TaskHandleResult taskHandleResult = result;
                return taskHandleResult;
            }
            long timeOutTime = this.getTimeOutTimeMillis();
            while (true) {
                if (timeOutTime != 0L && System.currentTimeMillis() >= timeOutTime) {
                    result.setSuccess(Boolean.valueOf(false));
                    result.appendLog("\u53d6\u6570\u4efb\u52a1\u8d85\u65f6\uff01");
                    TaskHandleResult taskHandleResult = result;
                    return taskHandleResult;
                }
                Integer unExecuteCount = this.fetchLogService.getUnFinishTaskItemCount(runnerId);
                if (unExecuteCount == null || unExecuteCount == 0) break;
                try {
                    Thread.sleep(BdeCommonUtil.getTaskSleepTime());
                }
                catch (InterruptedException e) {
                    this.logger.error("\u7ebf\u7a0b\u7b49\u5f85\u5931\u8d25", e);
                    Thread.currentThread().interrupt();
                }
            }
            this.fetchLogService.updateTaskFinished(runnerId);
            FetchItemLogDTO fetchLog = this.fetchLogService.queryFailTask(runnerId);
            Assert.isNotNull((Object)fetchLog);
            if (!StringUtils.hasText(fetchLog.getLog())) {
                this.executeService.doCalculate(fetchInitTask);
                result.setSuccess(Boolean.valueOf(true));
                result.appendLog("\u53d6\u6570\u6210\u529f");
                TaskHandleResult taskHandleResult = result;
                return taskHandleResult;
            }
            result.setSuccess(Boolean.valueOf(false));
            result.appendLog(fetchLog.getLog());
            TaskHandleResult taskHandleResult = result;
            return taskHandleResult;
        }
        catch (Exception e) {
            result.setSuccess(Boolean.valueOf(false));
            result.appendLog(e.getMessage());
            TaskHandleResult taskHandleResult = result;
            return taskHandleResult;
        }
        finally {
            NpContextHolder.clearContext();
        }
    }

    private long getTimeOutTimeMillis() {
        Integer timeOutOptionValue = ConverterUtils.getAsInteger((Object)BdeSystemOptionTool.getOptionValue((String)"BDE_TIMEOUT_TIME"));
        if (timeOutOptionValue == null || timeOutOptionValue <= 0) {
            return 0L;
        }
        return System.currentTimeMillis() + (long)(timeOutOptionValue * 1000);
    }

    public String getModule() {
        return "GC";
    }

    public IDimType getDimType() {
        return FetchDimType.UNITCODE;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public String getSpecialQueueFlag() {
        return SPECIAL_QUEUE_FLAG;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }
}

