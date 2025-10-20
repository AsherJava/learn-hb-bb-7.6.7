/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
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
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gc.financialcubes.offset.rebuild;

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
import com.jiuqi.gc.financialcubes.common.mq.FinancilalCubesBaseTaskHandler;
import com.jiuqi.gc.financialcubes.offset.service.FinancialCubesOffsetRebuildPlugin;
import com.jiuqi.gc.financialcubes.offset.service.FinancialCubesOffsetRebuildPluginGather;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FinancialCubesOffsetRebuildHandler
extends FinancilalCubesBaseTaskHandler {
    private static final String HANDLER_NAME = "FinancialCubesOffsetRebuildHandler";
    private static final String BBLX_DIFFUNIT = "1";
    @Autowired
    private FinancialCubesOffsetRebuildPluginGather pluginGather;
    @Autowired
    private TaskManageService taskService;

    public String getName() {
        return HANDLER_NAME;
    }

    public String getTitle() {
        return "\u62b5\u9500\u5206\u5f55\u91cd\u7b97\u4efb\u52a1";
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
        if (!BBLX_DIFFUNIT.equals(rebuildDTO.getBblx())) {
            return paramMap;
        }
        this.initTaskManageByRebuild(rebuildDTO);
        paramMap.put(preParam, rebuildDTO.getUnitCode() + "|" + rebuildDTO.getOrgType());
        return paramMap;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        FinancialCubesRebuildDTO rebuildDTO = (FinancialCubesRebuildDTO)JsonUtils.readValue((String)param, FinancialCubesRebuildDTO.class);
        StringBuilder log = new StringBuilder();
        List rebuildScopeList = rebuildDTO.getRebuildScope();
        log.append("\u5f00\u59cb\u6267\u884c\u62b5\u9500\u5206\u5f55\u591a\u7ef4\u91cd\u7b97\n");
        for (String rebuildScope : rebuildScopeList) {
            FinancialCubesOffsetRebuildPlugin pluginByScope = this.pluginGather.getPluginByScope(rebuildScope);
            if (pluginByScope == null) {
                log.append("\u6839\u636e\u91cd\u7b97\u8303\u56f4" + rebuildScope + "\u672a\u80fd\u627e\u5230\u5bf9\u5e94\u7684\u91cd\u7b97\u63d2\u4ef6,\u8df3\u8fc7\u5bf9\u5e94\u5904\u7406\uff01\n");
                continue;
            }
            log.append("\u5f00\u59cb\u6267\u884c" + pluginByScope.pluginName() + "\u4efb\u52a1\n");
            log.append(pluginByScope.rebuild(rebuildDTO));
            log.append(pluginByScope.pluginName() + "\u4efb\u52a1\u6267\u884c\u5b8c\u6210\n");
        }
        log.append("\u62b5\u9500\u5206\u5f55\u591a\u7ef4\u91cd\u7b97\u5b8c\u6210");
        TaskHandleResult result = new TaskHandleResult();
        result.setPreParam(param);
        result.appendLog(log.toString());
        return result;
    }

    private void initTaskManageByRebuild(FinancialCubesRebuildDTO rebuildDTO) {
        List rebuildScopeList = rebuildDTO.getRebuildScope();
        for (String rebuildScope : rebuildScopeList) {
            FinancialCubesRebuildScopeEnum rebuildScopeEnum = FinancialCubesRebuildScopeEnum.getEnumByCode((String)rebuildScope);
            String lockHandleName = null;
            switch (rebuildScopeEnum) {
                case FINCUBES_CF: {
                    lockHandleName = "FinancialCubesOffsetCfCalcHandler" + rebuildDTO.getPeriodType();
                    break;
                }
                case FINCUBES_DIM: {
                    lockHandleName = "FinancialCubesOffsetDimCalcHandler" + rebuildDTO.getPeriodType();
                    break;
                }
                case FINCUBES_AGING: {
                    lockHandleName = "FinancialCubesOffsetAgingCalcHandler" + rebuildDTO.getPeriodType();
                }
            }
            if (lockHandleName == null) continue;
            this.taskService.initTaskManageByUnitCodes(lockHandleName, Collections.singletonList(rebuildDTO.getUnitCode() + "|" + rebuildDTO.getOrgType()), new Date());
        }
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public IDimType getDimType() {
        return DimType.UNITCODE;
    }
}

