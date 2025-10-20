/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.datatrace.context.GcDataTracerContext
 *  com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 */
package com.jiuqi.gcreport.asset.datatrace;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.datatracer.GcDataTracer;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcAssetBillTracer
implements GcDataTracer {
    @Autowired
    private UnionRuleService unionRuleService;

    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTracerContext context) {
        AbstractUnionRule unionRule = (AbstractUnionRule)context.getExtendDataMap().get("unionRule");
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.valueOf((String)unionRule.getRuleType());
        GcDataTraceCondi gcDataTraceCondi = null;
        switch (ruleTypeEnum) {
            case FIXED_ASSETS: {
                gcDataTraceCondi = this.queryGcDataTraceCondiByAssets(context, unionRule);
                break;
            }
        }
        return gcDataTraceCondi;
    }

    private GcDataTraceCondi queryGcDataTraceCondiByAssets(GcDataTracerContext context, AbstractUnionRule unionRule) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = context.getGcOffSetVchrItemDTO();
        DefaultTableEntity assetBill = InvestBillTool.getEntityById((String)gcOffSetVchrItemDTO.getSrcOffsetGroupId(), (String)"GC_COMMONASSETBILL");
        if (assetBill == null) {
            assetBill = InvestBillTool.getEntityById((String)gcOffSetVchrItemDTO.getSrcOffsetGroupId(), (String)"GC_COMBINEDASSETBILL");
            return null;
        }
        if (assetBill == null) {
            return null;
        }
        GcDataTraceCondi gcDataTraceCondi = new GcDataTraceCondi();
        gcDataTraceCondi.setAcctPeriod(context.getCondition().getAcctPeriod());
        gcDataTraceCondi.setAcctYear(context.getCondition().getAcctYear());
        gcDataTraceCondi.setBillCode(ConverterUtils.getAsString((Object)assetBill.getFieldValue("BILLCODE")));
        gcDataTraceCondi.setDefineCode(ConverterUtils.getAsString((Object)assetBill.getFieldValue("DEFINECODE")));
        gcDataTraceCondi.setBillTitle("\u5355\u636e\u4fe1\u606f");
        gcDataTraceCondi.setCurrency(gcOffSetVchrItemDTO.getOffSetCurr());
        gcDataTraceCondi.setGcDataTraceType(GcDataTraceTypeEnum.ZC.getType());
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
        boolean isMatch;
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
        switch (ruleTypeEnum) {
            case FIXED_ASSETS: {
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

