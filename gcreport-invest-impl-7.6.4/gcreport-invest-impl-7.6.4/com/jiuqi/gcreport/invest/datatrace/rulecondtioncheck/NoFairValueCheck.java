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
import com.jiuqi.gcreport.invest.investbill.dao.FairValueBillDao;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoFairValueCheck
implements RuleCondtionCheck {
    public static final String RULE_CONDITION_CODE = GcDataTraceTypeEnum.INVEST.getType() + "_NO_FAIRVALUE";
    @Autowired
    private FairValueBillDao fairValueBillDao;

    public String getRuleContionType() {
        return GcDataTraceTypeEnum.INVEST.getType();
    }

    public String getRuleContionCode() {
        return RULE_CONDITION_CODE;
    }

    public String getRuleContionTitle() {
        return "\u65e0\u516c\u5141\u4ef7\u503c\u53f0\u8d26\uff0c\u4e0d\u751f\u6210\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219\u5206\u5f55";
    }

    public OffsetCheckResultDTO check(AbstractUnionRule unionRule, GcDataTraceCondi gcDataTraceCondi, boolean existOriginOffsetItem) {
        if (!RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode().equals(unionRule.getRuleType())) {
            return null;
        }
        String billCode = gcDataTraceCondi.getBillCode();
        DefaultTableEntity investMaster = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_INVESTBILL");
        String mergeType = (String)investMaster.getFieldValue("MERGETYPE");
        if (InvestInfoEnum.INDIRECT.getCode().equals(mergeType)) {
            return new OffsetCheckResultDTO(OffsetCheckInfoEnum.TYPE_MISMATCH.getOffsetCheckSceneTypeName(), CheckStatusEnum.UNGENERATED.getCode());
        }
        String srcId = (String)investMaster.getFieldValue("SRCID");
        Map<String, Object> fvchMasterData = this.fairValueBillDao.getMasterByYearAndSrcId(gcDataTraceCondi.getAcctYear(), srcId);
        if (fvchMasterData == null || fvchMasterData.isEmpty()) {
            if (existOriginOffsetItem) {
                return new OffsetCheckResultDTO(OffsetCheckInfoEnum.NO_FAIR_VALUE_LEDGER_AFTER_CHANGE.getOffsetCheckSceneTypeName(), CheckStatusEnum.CHECK_INCONSISTENT.getCode());
            }
            return new OffsetCheckResultDTO(OffsetCheckInfoEnum.NO_FAIR_VALUE_LEDGER.getOffsetCheckSceneTypeName(), CheckStatusEnum.UNGENERATED.getCode());
        }
        return null;
    }
}

