/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesRebuildScopeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gcreport.org.impl.check.enums.BBLXEnum
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.taskscheduling;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesRebuildScopeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.impl.GcBalanceCheckDataRebuildServiceImpl;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.FinancialCheckBaseTaskHandler;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.UserInfoUtil;
import com.jiuqi.gcreport.org.impl.check.enums.BBLXEnum;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FinancialCheckRebuildTaskBHandler
extends FinancialCheckBaseTaskHandler {
    @Autowired
    private UserInfoUtil userInfoUtil;
    @Autowired
    private TaskManageService taskService;
    @Autowired
    private GcBalanceCheckDataRebuildServiceImpl gcBalanceCheckDataRebuildService;

    public String getName() {
        return "FinancialCheckRebuildTaskBHandler";
    }

    public String getTitle() {
        return "\u5bf9\u8d26\u4e2d\u5fc3\u591a\u7ef4\u5e95\u7a3f\u6570\u636e\u91cd\u7b97\u4efb\u52a1";
    }

    public String getPreTask() {
        return "FinancialCubesRebuildHandler";
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public Map<String, String> getHandleParams(String preParam) {
        HashMap<String, String> paramMap = new HashMap<String, String>(16);
        if (StringUtils.isEmpty((String)preParam)) {
            return paramMap;
        }
        FinancialCubesRebuildDTO rebuildDTO = (FinancialCubesRebuildDTO)JsonUtils.readValue((String)preParam, FinancialCubesRebuildDTO.class);
        rebuildDTO.getBblx();
        if (!String.valueOf(BBLXEnum.SINGLE.getId()).equals(rebuildDTO.getBblx())) {
            return paramMap;
        }
        List rebuildScope = rebuildDTO.getRebuildScope();
        if (CollectionUtils.isEmpty((Collection)rebuildScope) || !rebuildScope.contains(FinancialCubesRebuildScopeEnum.FINCUBES_RELATED_ITEM.getCode())) {
            return paramMap;
        }
        paramMap.put(preParam, rebuildDTO.getUnitCode() + "|" + rebuildDTO.getDataTime());
        return paramMap;
    }

    @Transactional(rollbackFor={Exception.class})
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        TaskHandleResult result = new TaskHandleResult();
        FinancialCubesRebuildDTO rebuildDTO = (FinancialCubesRebuildDTO)JsonUtils.readValue((String)param, FinancialCubesRebuildDTO.class);
        StringBuilder log = new StringBuilder();
        log.append(new Date()).append("-\u5f00\u59cb\u672c\u6b21\u6570\u636e\u91cd\u7b97,\u53c2\u6570\u4e3a\uff1a").append(param).append("\n");
        this.taskService.updateBeginHandle("FinancialCheckDataCollectionTaskHandlerB", rebuildDTO.getUnitCode() + "|" + rebuildDTO.getDataTime(), new Date());
        this.userInfoUtil.putUserInfoToNpContext();
        this.gcBalanceCheckDataRebuildService.dataCollection(this.getName(), 0, Integer.MAX_VALUE, rebuildDTO.getUnitCode(), rebuildDTO.getDataTime(), log);
        log.append(new Date()).append("-\u672c\u6b21\u6570\u636e\u91cd\u7b97\u7ed3\u675f").append("\n");
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

