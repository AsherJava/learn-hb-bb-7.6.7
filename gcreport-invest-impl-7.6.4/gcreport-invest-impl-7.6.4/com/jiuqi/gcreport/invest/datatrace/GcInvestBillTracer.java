/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.util.PeriodUtils
 *  com.jiuqi.gcreport.datatrace.context.GcDataTracerContext
 *  com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.va.bill.utils.BillUtils
 */
package com.jiuqi.gcreport.invest.datatrace;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.util.PeriodUtils;
import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.va.bill.utils.BillUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcInvestBillTracer
implements GcDataTracer {
    @Autowired
    private UnionRuleService unionRuleService;

    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext context) {
        AbstractUnionRule unionRule = (AbstractUnionRule)context.getExtendDataMap().get("unionRule");
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)unionRule.getRuleType());
        GcDataTraceCondi gcDataTraceCondi = null;
        switch (ruleTypeEnum) {
            case INDIRECT_INVESTMENT_SEGMENT: 
            case INDIRECT_INVESTMENT: 
            case DIRECT_INVESTMENT_SEGMENT: 
            case DIRECT_INVESTMENT: {
                gcDataTraceCondi = this.queryGcDataTraceCondiByInvestment(context, unionRule);
                break;
            }
            case PUBLIC_VALUE_ADJUSTMENT: {
                gcDataTraceCondi = this.queryGcDataTraceCondiByAdjustment(context, unionRule);
                break;
            }
        }
        return gcDataTraceCondi;
    }

    private GcDataTraceCondi queryGcDataTraceCondiByAdjustment(GcDataTracerContext context, AbstractUnionRule unionRule) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        DefaultTableEntity fvchItemBill = InvestBillTool.getEntityById((String)gcOffSetVchrItemDTO.getSrcOffsetGroupId(), (String)"GC_FVCH_FIXEDITEM");
        if (fvchItemBill == null) {
            fvchItemBill = InvestBillTool.getEntityById((String)gcOffSetVchrItemDTO.getSrcOffsetGroupId(), (String)"GC_FVCH_OTHERITEM");
        }
        if (fvchItemBill == null) {
            return null;
        }
        String billcode = ConverterUtils.getAsString((Object)fvchItemBill.getFieldValue("BILLCODE"));
        String defineCode = ConverterUtils.getAsString((Object)BillUtils.getDefineCodeByBillCode((String)billcode));
        GcDataTraceCondi gcDataTraceCondi = new GcDataTraceCondi();
        gcDataTraceCondi.setAcctPeriod(context.getCondition().getAcctPeriod());
        gcDataTraceCondi.setAcctYear(context.getCondition().getAcctYear());
        gcDataTraceCondi.setBillCode(billcode);
        gcDataTraceCondi.setDefineCode(defineCode);
        gcDataTraceCondi.setBillTitle("\u5355\u636e\u4fe1\u606f");
        gcDataTraceCondi.setCurrency(gcOffSetVchrItemDTO.getOffSetCurr());
        gcDataTraceCondi.setGcDataTraceType(GcDataTraceTypeEnum.INVEST.getType());
        gcDataTraceCondi.setInputUnitId(context.getCondition().getOrgId());
        gcDataTraceCondi.setOppUnitId(gcOffSetVchrItemDTO.getOppUnitId());
        gcDataTraceCondi.setUnitId(gcOffSetVchrItemDTO.getUnitId());
        gcDataTraceCondi.setOrgType(context.getCondition().getOrgType());
        gcDataTraceCondi.setPeriodStr(gcOffSetVchrItemDTO.getDefaultPeriod());
        gcDataTraceCondi.setSrcId(gcOffSetVchrItemDTO.getSrcOffsetGroupId());
        gcDataTraceCondi.setRuleIds(Arrays.asList(gcOffSetVchrItemDTO.getRuleId()));
        gcDataTraceCondi.setTaskId(gcOffSetVchrItemDTO.getTaskId());
        gcDataTraceCondi.addExtendParams("isFvchRule", (Object)true);
        return gcDataTraceCondi;
    }

    private GcDataTraceCondi queryGcDataTraceCondiByInvestment(GcDataTracerContext context, AbstractUnionRule unionRule) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        String[] columnNamesInDB = new String[]{"SRCID", "PERIOD"};
        int period = PeriodUtils.getMonth((int)context.getCondition().getAcctYear(), (int)context.getCondition().getPeriodType(), (int)context.getCondition().getAcctPeriod());
        Object[] values = new Object[]{gcOffSetVchrItemDTO.getSrcOffsetGroupId(), period};
        List list = InvestBillTool.listByWhere((String[])columnNamesInDB, (Object[])values, (String)"GC_INVESTBILL");
        if (CollectionUtils.isEmpty((Collection)list)) {
            return null;
        }
        Map investBill = (Map)list.get(0);
        GcDataTraceCondi gcDataTraceCondi = new GcDataTraceCondi();
        gcDataTraceCondi.setAcctPeriod(Integer.valueOf(period));
        gcDataTraceCondi.setAcctYear(context.getCondition().getAcctYear());
        gcDataTraceCondi.setBillCode(ConverterUtils.getAsString(investBill.get("BILLCODE")));
        gcDataTraceCondi.setDefineCode(ConverterUtils.getAsString(investBill.get("DEFINECODE")));
        gcDataTraceCondi.setBillTitle("\u5355\u636e\u4fe1\u606f");
        gcDataTraceCondi.setCurrency(gcOffSetVchrItemDTO.getOffSetCurr());
        gcDataTraceCondi.setGcDataTraceType(GcDataTraceTypeEnum.INVEST.getType());
        gcDataTraceCondi.setInputUnitId(context.getCondition().getOrgId());
        gcDataTraceCondi.setOppUnitId(gcOffSetVchrItemDTO.getOppUnitId());
        gcDataTraceCondi.setUnitId(gcOffSetVchrItemDTO.getUnitId());
        gcDataTraceCondi.setOrgType(context.getCondition().getOrgType());
        gcDataTraceCondi.setPeriodStr(gcOffSetVchrItemDTO.getDefaultPeriod());
        gcDataTraceCondi.setSrcId(gcOffSetVchrItemDTO.getSrcOffsetGroupId());
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
        if (ruleTypeEnum == null) {
            return false;
        }
        boolean isMatch = false;
        switch (ruleTypeEnum) {
            case INDIRECT_INVESTMENT_SEGMENT: 
            case INDIRECT_INVESTMENT: 
            case DIRECT_INVESTMENT_SEGMENT: 
            case DIRECT_INVESTMENT: 
            case PUBLIC_VALUE_ADJUSTMENT: {
                isMatch = true;
                break;
            }
            default: {
                isMatch = false;
            }
        }
        context.getExtendDataMap().put("unionRule", unionRule);
        return isMatch;
    }

    public int order() {
        return Integer.MIN_VALUE;
    }
}

