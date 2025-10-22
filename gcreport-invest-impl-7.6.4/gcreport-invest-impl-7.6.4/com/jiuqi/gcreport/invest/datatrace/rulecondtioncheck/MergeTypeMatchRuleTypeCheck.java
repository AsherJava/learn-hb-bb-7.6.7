/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO
 *  com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum
 *  com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum
 *  com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.invest.datatrace.rulecondtioncheck;

import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MergeTypeMatchRuleTypeCheck
implements RuleCondtionCheck {
    public static final String RULE_CONDITION_CODE = GcDataTraceTypeEnum.INVEST.getType() + "_MERGETYPE_MATCH_RULETYPE";

    public String getRuleContionType() {
        return GcDataTraceTypeEnum.INVEST.getType();
    }

    public String getRuleContionCode() {
        return RULE_CONDITION_CODE;
    }

    public String getRuleContionTitle() {
        return "\u53f0\u8d26\u5408\u5e76\u7c7b\u578b\u548c\u89c4\u5219\u7c7b\u578b\u4e0d\u5339\u914d";
    }

    public OffsetCheckResultDTO check(AbstractUnionRule unionRule, GcDataTraceCondi gcDataTraceCondi, boolean existOriginOffsetItem) {
        List<String> investRuleTypeList = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode());
        if (!investRuleTypeList.contains(unionRule.getRuleType())) {
            return null;
        }
        String billCode = gcDataTraceCondi.getBillCode();
        DefaultTableEntity master = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_INVESTBILL");
        String mergeType = (String)master.getFieldValue("MERGETYPE");
        String ruleType = unionRule.getRuleType();
        if (!ruleType.startsWith(mergeType)) {
            if (existOriginOffsetItem) {
                return new OffsetCheckResultDTO(OffsetCheckInfoEnum.TYPE_MISMATCH_AFTER_CHANGE.getOffsetCheckSceneTypeName(), CheckStatusEnum.CHECK_INCONSISTENT.getCode());
            }
            return new OffsetCheckResultDTO(OffsetCheckInfoEnum.TYPE_MISMATCH.getOffsetCheckSceneTypeName(), CheckStatusEnum.UNGENERATED.getCode());
        }
        return null;
    }
}

