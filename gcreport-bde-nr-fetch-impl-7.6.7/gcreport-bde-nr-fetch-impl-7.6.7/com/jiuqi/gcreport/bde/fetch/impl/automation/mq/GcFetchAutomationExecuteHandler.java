/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.mq;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskHandler;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationParameterDTO;
import com.jiuqi.gcreport.bde.fetch.impl.automation.intf.FetchAutomationResult;
import com.jiuqi.gcreport.bde.fetch.impl.automation.service.impl.FetchAutomationServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcFetchAutomationExecuteHandler
implements ITaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcFetchAutomationExecuteHandler.class);
    @Autowired
    private FetchAutomationServiceImpl fetchService;

    public String getName() {
        return "AUTOMATION_FETCH";
    }

    public String getTitle() {
        return "ETL\u53d6\u6570\u6267\u884c";
    }

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.LEVEL;
    }

    public Map<String, String> getHandleParams(String preParam) {
        FetchAutomationParameterDTO parameter = (FetchAutomationParameterDTO)JsonUtils.readValue((String)preParam, FetchAutomationParameterDTO.class);
        String[] unitCodeList = parameter.getUnitCode().split(";");
        HashMap<String, String> params = new HashMap<String, String>();
        FetchAutomationParameterDTO message = null;
        for (String unitCode : unitCodeList) {
            message = (FetchAutomationParameterDTO)BeanConvertUtil.convert((Object)parameter, FetchAutomationParameterDTO.class, (String[])new String[0]);
            message.setUnitCode(unitCode);
            params.put(JsonUtils.writeValueAsString((Object)message), message.getEtlKey() + "," + unitCode);
        }
        return params;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        FetchAutomationParameterDTO parameter = (FetchAutomationParameterDTO)JsonUtils.readValue((String)param, FetchAutomationParameterDTO.class);
        try {
            FetchAutomationResult result = this.fetchService.doFetch(parameter);
            TaskHandleResult handleResult = new TaskHandleResult();
            handleResult.setSuccess(Boolean.valueOf(result.isSuccess()));
            handleResult.setPreParam(param);
            return handleResult;
        }
        catch (Exception e) {
            LOGGER.error("\u81ea\u52a8\u5316\u5bf9\u8c61BDE\u53d6\u6570\u6267\u884c\u5931\u8d25", e);
            throw new BusinessRuntimeException(e.getMessage());
        }
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
                return "ETL\u53d6\u6570\u8bf7\u6c42";
            }

            public String getName() {
                return "ETL_FETCH";
            }
        };
    }

    public boolean enable(String preTaskName, String preParam) {
        return true;
    }

    public TaskHandleResult handleTask(String param) {
        return null;
    }

    public String getSpecialQueueFlag() {
        return null;
    }
}

