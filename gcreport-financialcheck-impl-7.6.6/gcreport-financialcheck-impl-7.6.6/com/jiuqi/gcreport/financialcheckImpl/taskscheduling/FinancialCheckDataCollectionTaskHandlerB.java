/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gc.financialcubes.mergesummary.dto.FinancialCubesMergeSummaryTaskDto
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gc.financialcubes.mergesummary.dto.FinancialCubesMergeSummaryTaskDto;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl.GcBalanceCheckDataCollectionServiceImpl;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.FinancialCheckBaseTaskHandler;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.UserInfoUtil;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FinancialCheckDataCollectionTaskHandlerB
extends FinancialCheckBaseTaskHandler {
    public static final String COLLECTION_TASK_NAME = "FinancialCheckDataCollectionTaskHandlerB";
    @Autowired
    private TaskManageService taskService;
    @Autowired
    GcBalanceCheckDataCollectionServiceImpl dataCollectionService;
    @Autowired
    private UserInfoUtil userInfoUtil;

    public String getName() {
        return COLLECTION_TASK_NAME;
    }

    public String getTitle() {
        return "\u5bf9\u8d26\u4e2d\u5fc3\u6570\u636e\u91c7\u96c6\u4efb\u52a1-\u4f59\u989d\u5bf9\u8d26\u6a21\u5f0f";
    }

    public String getPreTask() {
        return "FinancialCubesDimCalcHandler;FinancialCubesAdjustDimCalcHandler";
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        List params = (List)JsonUtils.readValue((String)preParam, (TypeReference)new TypeReference<List<FinancialCubesMergeSummaryTaskDto>>(){});
        HashMap<String, String> handleParams = new HashMap<String, String>(2);
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            params.forEach(param -> {
                handleParams.put(JsonUtils.writeValueAsString((Object)param), param.getUnitCode() + "|" + param.getDataTime());
                this.taskService.initTaskManageByUnitCodes(this.getName(), Collections.singletonList(param.getUnitCode() + "|" + param.getDataTime()), new Date());
            });
        }
        return handleParams;
    }

    @Transactional(rollbackFor={Exception.class})
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        this.userInfoUtil.putUserInfoToNpContext();
        StringBuilder log = new StringBuilder();
        FinancialCubesMergeSummaryTaskDto taskDto = (FinancialCubesMergeSummaryTaskDto)JsonUtils.readValue((String)param, FinancialCubesMergeSummaryTaskDto.class);
        this.taskService.updateBeginHandle(this.getName(), taskDto.getUnitCode() + "|" + taskDto.getDataTime(), new Date());
        log.append(new Date()).append("-\u5f00\u59cb\u672c\u6b21\u6570\u636e\u91c7\u96c6,\u53c2\u6570\u4e3a\uff1a").append(param).append("\n");
        TaskHandleResult result = new TaskHandleResult();
        TaskManageDO task = this.taskService.getTaskManageByName(this.getName(), taskDto.getUnitCode() + "|" + taskDto.getDataTime());
        Integer beginBatchId = task.getBatchNum();
        Integer endBatchId = taskDto.getBatchNum();
        if (beginBatchId >= endBatchId) {
            log.append("\u5f00\u59cb\u6279\u6b21\u53f7").append(beginBatchId).append("\u5927\u4e8e\u6216\u7b49\u4e8e\u7ed3\u675f\u6279\u6b21\u53f7").append(endBatchId).append("\u8df3\u8fc7\u672c\u6b21\u91c7\u96c6").append("\uff09\n");
            result.appendLog(log.toString());
            return result;
        }
        this.dataCollectionService.dataCollection(this.getName(), beginBatchId, endBatchId, taskDto.getUnitCode(), taskDto.getDataTime(), log);
        this.taskService.updateEndHandle(this.getName(), taskDto.getUnitCode() + "|" + taskDto.getDataTime(), endBatchId.intValue());
        log.append(new Date()).append("-\u672c\u6b21\u6570\u636e\u91c7\u96c6\u7ed3\u675f").append("\n");
        result.appendLog(log.toString());
        return result;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public IDimType getDimType() {
        return DimType.UNITCODE;
    }
}

