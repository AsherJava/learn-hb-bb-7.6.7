/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.common.FinancialCubesRebuildScopeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 */
package com.jiuqi.gc.financialcubes.offset.dim.service;

import com.jiuqi.common.financialcubes.common.FinancialCubesRebuildScopeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.gc.financialcubes.offset.dim.service.FinancialCubesOffsetCalcDimService;
import com.jiuqi.gc.financialcubes.offset.service.FinancialCubesOffsetRebuildPlugin;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetRebuildDimPlugin
implements FinancialCubesOffsetRebuildPlugin {
    @Autowired
    private FinancialCubesOffsetCalcDimService offsetCalcService;
    @Autowired
    private TaskManageService taskService;

    public String pluginName() {
        return "\u62b5\u9500\u5206\u5f55\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f\u91cd\u7b97";
    }

    public FinancialCubesRebuildScopeEnum rebuildScope() {
        return FinancialCubesRebuildScopeEnum.FINCUBES_DIM;
    }

    public String rebuild(FinancialCubesRebuildDTO rebuildDTO) {
        String lockHandleName = "FinancialCubesOffsetDimCalcHandler" + rebuildDTO.getPeriodType();
        String lockCode = rebuildDTO.getUnitCode() + "|" + rebuildDTO.getOrgType();
        this.taskService.updateBeginHandle(lockHandleName, lockCode, new Date());
        TaskManageDO task = this.taskService.getTaskManageByName(lockHandleName, lockCode);
        return this.offsetCalcService.rebuildFinancialCubesDim(rebuildDTO, task == null ? 0 : task.getBatchNum());
    }
}

