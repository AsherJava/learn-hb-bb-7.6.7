/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.event.GcCalcExecuteInitOffsetItemEvent
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.samecontrol.calculate.listener;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.event.GcCalcExecuteInitOffsetItemEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.calculate.service.GcCalcCarryOverOffsetCopyService;
import com.jiuqi.gcreport.samecontrol.calculate.service.GcCalcInitOffSetItemCopyService;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=-2147483646)
public class GcCalcDisposeOffsetEventListener
implements ApplicationListener<GcCalcExecuteInitOffsetItemEvent> {
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;
    @Autowired
    private GcCalcInitOffSetItemCopyService initOffSetItemCopyService;
    @Autowired
    private GcCalcCarryOverOffsetCopyService carryOverOffsetCopyService;

    @Override
    public void onApplicationEvent(GcCalcExecuteInitOffsetItemEvent event) {
        GcCalcEnvContext env = event.getEnv();
        this.handleDisposeOffset(env);
    }

    private void handleDisposeOffset(GcCalcEnvContext env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(calcArgments.getSchemeId(), calcArgments.getPeriodStr());
        if (reportSystemId == null) {
            return;
        }
        ConsolidatedOptionVO conOptionBySystemId = ((ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class)).getOptionData(reportSystemId);
        if (conOptionBySystemId == null || !conOptionBySystemId.getMonthlyIncrement().booleanValue()) {
            return;
        }
        String tempPeriod = calcArgments.getPeriodStr();
        List<SameCtrlChgOrgEO> sameCtrlChgOrgEOList = this.sameCtrlChgOrgService.listCurrYearChgOrgsByDisposOrg(calcArgments.getOrgId(), calcArgments.getPeriodStr());
        if (CollectionUtils.isEmpty(sameCtrlChgOrgEOList)) {
            return;
        }
        Map<String, SameCtrlChgOrgEO> sameCtrlChangedCode2EOMap = sameCtrlChgOrgEOList.stream().collect(Collectors.toMap(SameCtrlChgOrgEO::getChangedCode, Function.identity(), (v1, v2) -> v2));
        YearPeriodObject yp = new YearPeriodObject(null, tempPeriod);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String comBaseUnit = tool.getDeepestBaseUnitId(calcArgments.getOrgId());
        if (StringUtils.isEmpty((String)comBaseUnit)) {
            return;
        }
        this.initOffSetItemCopyService.executeDisposeInitToAdjustOffSetItem(env, comBaseUnit, sameCtrlChangedCode2EOMap);
        this.carryOverOffsetCopyService.executeDisposeInitToAdjustOffSetItem(env, comBaseUnit, sameCtrlChangedCode2EOMap);
    }
}

