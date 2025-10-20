/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO
 *  com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum
 *  com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum
 *  com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.invest.datatrace.rulecondtioncheck;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.OffsetCheckInfoEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoMatchChangeScenario
implements RuleCondtionCheck {
    public static final String RULE_CONDITION_CODE = GcDataTraceTypeEnum.INVEST.getType() + "_NOMATCH_CHANGESCENARIO";
    @Autowired
    private ConsolidatedOptionCacheService consolidatedOptionCacheService;

    public String getRuleContionType() {
        return GcDataTraceTypeEnum.INVEST.getType();
    }

    public String getRuleContionCode() {
        return RULE_CONDITION_CODE;
    }

    public String getRuleContionTitle() {
        return "\u53f0\u8d26\u65e0\u8be5\u89c4\u5219\u5bf9\u5e94\u7684\u53d8\u52a8\u573a\u666f\u6570\u636e";
    }

    public OffsetCheckResultDTO check(AbstractUnionRule unionRule, GcDataTraceCondi gcDataTraceCondi, boolean existOriginOffsetItem) {
        List<String> investRuleTypeList = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode());
        if (!investRuleTypeList.contains(unionRule.getRuleType())) {
            return null;
        }
        ConsolidatedOptionVO consolidatedOptionVO = this.consolidatedOptionCacheService.getConOptionBySystemId(unionRule.getReportSystem());
        Boolean monthlyIncrement = consolidatedOptionVO.getMonthlyIncrement();
        if (!monthlyIncrement.booleanValue()) {
            return null;
        }
        List monthlyIncrementRuleIds = consolidatedOptionVO.getMonthlyIncrementRuleIds();
        if (CollectionUtils.isEmpty((Collection)monthlyIncrementRuleIds) || !CollectionUtils.isEmpty((Collection)monthlyIncrementRuleIds) && monthlyIncrementRuleIds.contains(unionRule.getId())) {
            AbstractInvestmentRule investmentRule = (AbstractInvestmentRule)unionRule;
            List changeScenarioList = investmentRule.getInitialMerge();
            changeScenarioList.addAll(investmentRule.getGoingConcern());
            changeScenarioList.addAll(investmentRule.getDealWith());
            DefaultTableEntity masterData = InvestBillTool.getEntityByBillCode((String)gcDataTraceCondi.getBillCode(), (String)"GC_INVESTBILL");
            List subItems = InvestBillTool.listItemByMasterId(Arrays.asList(masterData.getId()), (String)"GC_INVESTBILLITEM");
            ArrayList<DefaultTableEntity> itemList = new ArrayList<DefaultTableEntity>();
            boolean matchedSubtemFlag = this.changeScenarioMatchSubtem(changeScenarioList, subItems, itemList);
            if (!matchedSubtemFlag) {
                return new OffsetCheckResultDTO(OffsetCheckInfoEnum.NO_SCENARIO_DATA.getOffsetCheckSceneTypeName(), CheckStatusEnum.UNGENERATED.getCode());
            }
        }
        return null;
    }

    private boolean changeScenarioMatchSubtem(List<String> changeScenarioList, List<DefaultTableEntity> subItems, List<DefaultTableEntity> itemList) {
        if (CollectionUtils.isEmpty(changeScenarioList)) {
            return false;
        }
        for (DefaultTableEntity item : subItems) {
            String changeScenarioObj = (String)item.getFieldValue("CHANGESCENARIO");
            if (!changeScenarioList.contains(changeScenarioObj)) continue;
            return true;
        }
        return false;
    }
}

