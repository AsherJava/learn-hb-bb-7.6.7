/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO
 *  com.jiuqi.bde.common.dto.FetchDataExecuteContext
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.util.LogUtil
 *  com.jiuqi.bde.log.enums.FetchDimType
 *  com.jiuqi.bde.log.enums.FetchTaskType
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.bde.fetch.impl.handler;

import com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO;
import com.jiuqi.bde.common.dto.FetchDataExecuteContext;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.util.LogUtil;
import com.jiuqi.bde.fetch.impl.request.service.FetchDataSendTaskService;
import com.jiuqi.bde.fetch.impl.request.service.impl.FetchDataRequestServiceImpl;
import com.jiuqi.bde.log.enums.FetchDimType;
import com.jiuqi.bde.log.enums.FetchTaskType;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CompressUtil;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeFetchDataHandler
implements ITaskHandler {
    @Autowired
    private FetchDataRequestServiceImpl requestService;
    @Autowired
    private FetchDataSendTaskService sendTaskService;

    public String getName() {
        return FetchTaskType.NR_FETCH_BDE_EXECUTE.getCode();
    }

    public String getTitle() {
        return FetchTaskType.NR_FETCH_BDE_EXECUTE.getTitle();
    }

    public String getPreTask() {
        return null;
    }

    public Map<String, String> getHandleParams(String preParam) {
        FetchRequestDTO ctx = (FetchRequestDTO)JsonUtils.readValue((String)preParam, FetchRequestDTO.class);
        HashMap<String, String> params = new HashMap<String, String>();
        String compress = CompressUtil.compress((String)preParam);
        params.put(compress, ctx.getFetchContext().getFormId() + "," + ctx.getFetchContext().getRegionId());
        return params;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        FetchRequestDTO orgnFetchRequestDTO = (FetchRequestDTO)JsonUtils.readValue((String)CompressUtil.deCompress((String)param), FetchRequestDTO.class);
        TaskHandleResult result = new TaskHandleResult();
        result.setPreParam(param);
        try {
            FetchDataRequestDTO fetchRequestDTO = null;
            fetchRequestDTO = this.requestService.doBasicCheck(orgnFetchRequestDTO);
            this.requestService.doAnalyzeContext(fetchRequestDTO);
            if (fetchRequestDTO.getFloatSetting() != null && !StringUtils.isEmpty((String)fetchRequestDTO.getFloatSetting().getQueryType())) {
                boolean existsFloatResult = this.requestService.doAnalyzeFloatRegion(fetchRequestDTO);
                if (!existsFloatResult) {
                    result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
                    return result;
                }
                if (CollectionUtils.isEmpty((Collection)fetchRequestDTO.getFixedSetting())) {
                    result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
                    return result;
                }
            }
            if (CollectionUtils.isEmpty((Collection)fetchRequestDTO.getFixedSetting())) {
                result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
                return result;
            }
            result.setSuccess(Boolean.valueOf(true));
            Map<String, FetchDataExecuteContext> fetchCtxMap = this.sendTaskService.buildFetchCtxMap(fetchRequestDTO);
            result.setPreParam(JsonUtils.writeValueAsString(fetchCtxMap));
        }
        catch (Exception e) {
            result.setSuccess(Boolean.valueOf(false));
            result.setSendPostTaskMsgWhileHandleTask(Boolean.valueOf(true));
            BdeLogUtil.forceRecordLog((String)orgnFetchRequestDTO.getRequestTaskId(), (String)"\u533a\u57df\u53d6\u6570", (Object)JsonUtils.writeValueAsString((Object)orgnFetchRequestDTO), (String)LogUtil.getExceptionStackStr((Throwable)e));
            return result;
        }
        return result;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public String getModule() {
        return "BDE";
    }

    public IDimType getDimType() {
        return FetchDimType.REGION;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public String getSpecialQueueFlag() {
        return null;
    }
}

