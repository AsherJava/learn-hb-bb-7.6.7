/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckService;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.FinancialCheckBaseTaskHandler;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.UserInfoUtil;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param.RealTimeCheckOrOffsetParam;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FinancialCheckRealTimeCheckHandler
extends FinancialCheckBaseTaskHandler {
    @Autowired
    private FinancialCheckService financialCheckService;
    @Autowired
    private UserInfoUtil userInfoUtil;

    public String getName() {
        return "FinancialCheckRealTimeCheckHandler";
    }

    public String getTitle() {
        return "\u5bf9\u8d26\u4e2d\u5fc3\u5b9e\u65f6\u5bf9\u8d26\u4efb\u52a1";
    }

    public String getPreTask() {
        return "";
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        RealTimeCheckOrOffsetParam checkparam = (RealTimeCheckOrOffsetParam)JsonUtils.readValue((String)preParam, RealTimeCheckOrOffsetParam.class);
        HashMap<String, String> handleParams = new HashMap<String, String>(2);
        if (CollectionUtils.isEmpty(checkparam.getItems())) {
            return handleParams;
        }
        handleParams.put(preParam, this.getName());
        return handleParams;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        this.userInfoUtil.putUserInfoToNpContext();
        RealTimeCheckOrOffsetParam realTimeCheckOrOffsetParam = (RealTimeCheckOrOffsetParam)JsonUtils.readValue((String)param, RealTimeCheckOrOffsetParam.class);
        this.financialCheckService.realTimeCheck(realTimeCheckOrOffsetParam);
        TaskHandleResult result = new TaskHandleResult();
        return result;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public IDimType getDimType() {
        return DimType.UNITCODE;
    }
}

