/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.datatrace.context.GcDataTracerContext
 *  com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 */
package com.jiuqi.gcreport.calculate.rule.fixedTable.datatracer;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcFixedRuleDataTracer
implements GcDataTracer {
    @Autowired
    private UnionRuleService unionRuleService;

    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext context) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        String srcGroupId = gcOffSetVchrItemDTO.getSrcOffsetGroupId();
        GcDataTraceCondi gcDataTraceCondi = new GcDataTraceCondi();
        gcDataTraceCondi.setGcDataTraceType(GcDataTraceTypeEnum.FIXED_TABLE.getType());
        gcDataTraceCondi.setAcctPeriod(gcOffSetVchrItemDTO.getAcctPeriod());
        gcDataTraceCondi.setAcctYear(gcOffSetVchrItemDTO.getAcctYear());
        gcDataTraceCondi.setCurrency(gcOffSetVchrItemDTO.getOffSetCurr());
        gcDataTraceCondi.setInputUnitId(context.getCondition().getOrgId());
        gcDataTraceCondi.setOppUnitId(gcOffSetVchrItemDTO.getOppUnitId());
        gcDataTraceCondi.setUnitId(gcOffSetVchrItemDTO.getUnitId());
        gcDataTraceCondi.setOrgType(context.getCondition().getOrgType());
        gcDataTraceCondi.setPeriodStr(gcOffSetVchrItemDTO.getDefaultPeriod());
        gcDataTraceCondi.setSrcId(srcGroupId);
        gcDataTraceCondi.setRuleIds(Arrays.asList(gcOffSetVchrItemDTO.getRuleId()));
        gcDataTraceCondi.setTaskId(gcOffSetVchrItemDTO.getTaskId());
        return gcDataTraceCondi;
    }

    public boolean isMatch(GcDataTracerContext context) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        String ruleId = gcOffSetVchrItemDTO.getRuleId();
        if (StringUtils.isEmpty((String)ruleId)) {
            return false;
        }
        AbstractUnionRule unionRule = this.unionRuleService.selectUnionRuleDTOById(ruleId);
        if (unionRule == null) {
            return false;
        }
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)unionRule.getRuleType());
        return RuleTypeEnum.FIXED_TABLE.equals((Object)ruleTypeEnum);
    }

    public int order() {
        return 0x7FFFFFFE;
    }
}

