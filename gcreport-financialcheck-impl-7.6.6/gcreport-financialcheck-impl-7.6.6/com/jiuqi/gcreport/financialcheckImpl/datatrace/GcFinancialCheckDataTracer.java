/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.datatrace.context.GcDataTracerContext
 *  com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 */
package com.jiuqi.gcreport.financialcheckImpl.datatrace;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckImpl.offset.util.FinancialCheckOffsetUtils;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcFinancialCheckDataTracer
implements GcDataTracer {
    @Autowired
    private OffsetRelatedItemDao offsetRelatedItemDao;
    @Autowired
    private UnionRuleService unionRuleService;

    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext context) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        String srcGroupId = gcOffSetVchrItemDTO.getSrcOffsetGroupId();
        List<GcOffsetRelatedItemEO> offsetRelatedItemS = this.offsetRelatedItemDao.listByOffsetGroupId(Arrays.asList(srcGroupId));
        if (CollectionUtils.isEmpty(offsetRelatedItemS)) {
            return null;
        }
        GcDataTraceCondi gcDataTraceCondi = new GcDataTraceCondi();
        gcDataTraceCondi.setGcDataTraceType(GcDataTraceTypeEnum.FINANCIALCHECK.getType());
        gcDataTraceCondi.setAcctPeriod(gcOffSetVchrItemDTO.getAcctPeriod());
        gcDataTraceCondi.setAcctYear(gcOffSetVchrItemDTO.getAcctYear());
        gcDataTraceCondi.setCurrency(gcOffSetVchrItemDTO.getOffSetCurr());
        gcDataTraceCondi.setInputUnitId(context.getCondition().getOrgId());
        gcDataTraceCondi.setOppUnitId(gcOffSetVchrItemDTO.getOppUnitId());
        gcDataTraceCondi.setUnitId(gcOffSetVchrItemDTO.getUnitId());
        gcDataTraceCondi.setOrgType(context.getCondition().getOrgType());
        gcDataTraceCondi.setPeriodStr(gcOffSetVchrItemDTO.getDefaultPeriod());
        gcDataTraceCondi.setSrcId(offsetRelatedItemS.get(0).getRelatedItemId());
        gcDataTraceCondi.setRuleIds(Arrays.asList(gcOffSetVchrItemDTO.getRuleId()));
        gcDataTraceCondi.setTaskId(gcOffSetVchrItemDTO.getTaskId());
        String fcServerUrl = FinancialCheckOffsetUtils.getFcServerUrl();
        if (!StringUtils.isEmpty((String)fcServerUrl)) {
            gcDataTraceCondi.addExtendParams("appCode", (Object)"GLJY");
        }
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
        return RuleTypeEnum.FINANCIAL_CHECK.equals((Object)ruleTypeEnum);
    }

    public int order() {
        return 0x7FFFFFFE;
    }
}

