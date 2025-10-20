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
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.FinancialCheckOffsetService;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRTOffsetExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.FinancialCheckBaseTaskHandler;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param.RealTimeCheckOrOffsetParam;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FinancialCheckRealTimeOffsetHandler
extends FinancialCheckBaseTaskHandler {
    @Autowired
    private GcRelatedItemQueryService itemQueryService;
    @Autowired
    private FinancialCheckOffsetService financialCheckOffsetService;
    @Autowired
    private FinancialCheckRTOffsetExecutorImpl rtOffsetExecutor;

    public String getName() {
        return "FinancialCheckRealTimeOffsetHandler";
    }

    public String getTitle() {
        return "\u5bf9\u8d26\u4e2d\u5fc3\u5b9e\u65f6\u62b5\u9500\u4efb\u52a1";
    }

    public String getPreTask() {
        return "";
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        RealTimeCheckOrOffsetParam offsetParam = (RealTimeCheckOrOffsetParam)JsonUtils.readValue((String)preParam, RealTimeCheckOrOffsetParam.class);
        HashMap<String, String> handleParams = new HashMap<String, String>(2);
        if (CollectionUtils.isEmpty(offsetParam.getItems())) {
            return handleParams;
        }
        handleParams.put(preParam, this.getName());
        return handleParams;
    }

    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        RealTimeCheckOrOffsetParam realTimeOffsetParam = (RealTimeCheckOrOffsetParam)JsonUtils.readValue((String)param, RealTimeCheckOrOffsetParam.class);
        List<GcRelatedItemEO> items = this.itemQueryService.queryByIds(realTimeOffsetParam.getItems());
        this.financialCheckOffsetService.syncCheckData2OffsetData(items, realTimeOffsetParam.getDataTime());
        items = items.stream().filter(item -> CheckStateEnum.CHECKED.getCode().equals(item.getChkState())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(items)) {
            this.rtOffsetExecutor.realTimeOffset(items, realTimeOffsetParam.getDataTime());
        }
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

