/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.FetchTaskState
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerClient
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq;

import com.jiuqi.bde.common.constant.FetchTaskState;
import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.dc.taskscheduling.api.vo.TaskParamVO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FinancialFetchInitTaskDTO;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.utils.FinancialAutoFetchUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialAutoFetchTaskExecuteHandler
implements ITaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialAutoFetchTaskExecuteHandler.class);
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    public String getName() {
        return "FINANCIAL_AUTO_FETCH_EXECUTE";
    }

    public String getTitle() {
        return "\u3010\u591a\u7ef4\u81ea\u52a8\u53d6\u6570\u3011\u6267\u884c\u53d6\u6570";
    }

    public String getPreTask() {
        StringJoiner joiner = new StringJoiner(";");
        joiner.add("FINANCIAL_COLLECTION_AUTO_FETCH_GC_FINCUBES_AGING");
        joiner.add("FINANCIAL_COLLECTION_AUTO_FETCH_GC_FINCUBES_CF");
        joiner.add("FINANCIAL_COLLECTION_AUTO_FETCH_GC_FINCUBES_DIM");
        joiner.add("FINANCIAL_REBUILD_AUTO_FETCH");
        return joiner.toString();
    }

    public Map<String, String> getHandleParams(String preParam) {
        FinancialFetchInitTaskDTO fetchInitTask = (FinancialFetchInitTaskDTO)((Object)JsonUtils.readValue((String)preParam, FinancialFetchInitTaskDTO.class));
        if (fetchInitTask == null || CollectionUtils.isEmpty((Collection)fetchInitTask.getFetchForms())) {
            return Collections.emptyMap();
        }
        HashMap<String, String> params = new HashMap<String, String>(1);
        params.put(CompressUtil.compress((String)JsonUtils.writeValueAsString((Object)((Object)fetchInitTask))), fetchInitTask.getGcTaskId());
        return params;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        FetchInitTaskDTO fetchInitTask = (FetchInitTaskDTO)JsonUtils.readValue((String)CompressUtil.deCompress((String)param), FetchInitTaskDTO.class);
        TaskParamVO taskParam = new TaskParamVO();
        taskParam.setPreParam(JsonUtils.writeValueAsString((Object)fetchInitTask));
        taskParam.setExt_1(UUIDUtils.emptyHalfGUIDStr());
        taskParam.setExt_2(fetchInitTask.getUnitCode());
        taskParam.setExt_3(fetchInitTask.getUsername());
        taskParam.setExt_5(FetchTaskState.UNEXECUTE.getCode());
        String fetchFormInfoStr = FinancialAutoFetchUtil.fetchFormInfoStr(fetchInitTask);
        TaskHandleResult result = new TaskHandleResult();
        TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
        try {
            String runnerId = (String)BdeClientUtil.parseResponse((BusinessResponseEntity)taskHandlerClient.startTaskWithExtInfo(RequestSourceTypeEnum.FINANCIAL_AUTO_FETCH.getCode(), taskParam));
            result.appendLog(String.format("\u3010\u591a\u7ef4\u81ea\u53d6\u53d6\u6570\u3011\u4efb\u52a1\u6267\u884c\u6210\u529f\uff0c\u4efb\u52a1ID\uff1a%s\n", runnerId));
            result.appendLog(fetchFormInfoStr);
            result.setSuccess(Boolean.valueOf(true));
        }
        catch (BusinessRuntimeException e) {
            result.appendLog(String.format("\u3010\u591a\u7ef4\u81ea\u53d6\u53d6\u6570\u3011\u51fa\u73b0\u9519\u8bef\uff1a%s", e.getMessage()));
            result.appendLog(fetchFormInfoStr);
            result.setSuccess(Boolean.valueOf(false));
            LOGGER.error("\u3010\u591a\u7ef4\u81ea\u52a8\u53d6\u6570\u3011\u51fa\u73b0\u9519\u8bef\uff1a{}\uff0c\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        return result;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public String getModule() {
        return "GC";
    }

    public IDimType getDimType() {
        return new IDimType(){

            public String getTitle() {
                return "\u591a\u7ef4\u81ea\u52a8\u53d6\u6570\u4efb\u52a1";
            }

            public String getName() {
                return "GC_TASK_ID";
            }
        };
    }

    public boolean sendPostTaskMsgWhileHandleTask() {
        return true;
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public String getSpecialQueueFlag() {
        return null;
    }

    public TaskHandleResult handleTask(String param) {
        return this.handleTask(param, null);
    }
}

