/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.IGcCalcInitOffSetItemCopyFilter
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 */
package com.jiuqi.gcreport.invest.calculate;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.IGcCalcInitOffSetItemCopyFilter;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.enums.InvestInfoEnum;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCalcInitOffSetItemCopyFilter
implements IGcCalcInitOffSetItemCopyFilter {
    @Autowired
    private InvestBillDao investBillDao;
    @Autowired
    private ConsolidatedOptionService optionCacheService;
    @Autowired
    private ConsolidatedTaskService taskCacheService;

    public List<GcOffSetVchrDTO> filter(GcCalcEnvContext env, List<GcOffSetVchrDTO> groups, QueryParamsVO paramsVO) {
        String reportSystemId = this.taskCacheService.getSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        ConsolidatedOptionVO conOptionBySystemId = this.optionCacheService.getOptionData(reportSystemId);
        if (Boolean.TRUE.equals(conOptionBySystemId.getMonthlyIncrement())) {
            return groups;
        }
        List<Map<String, Object>> disposedInvestData = this.investBillDao.listByWhere(new String[]{"DISPOSEFLAG", "ACCTYEAR"}, new Object[]{InvestInfoEnum.DISPOSE_DONE.getCode(), paramsVO.getAcctYear()});
        if (CollectionUtils.isEmpty(disposedInvestData)) {
            return groups;
        }
        int currentMonth = DateUtils.getDateFieldValue((Date)new YearPeriodObject(null, paramsVO.getPeriodStr()).formatYP().getEndDate(), (int)2);
        Set disposeConcatUnitStrSet = disposedInvestData.stream().filter(item -> {
            Object disposeDate = item.get("DISPOSEDATE");
            if (disposeDate == null) {
                return false;
            }
            int changeMonth = DateUtils.getDateFieldValue((Date)((Date)disposeDate), (int)2);
            return currentMonth >= changeMonth;
        }).map(item -> this.getConcatUnitString((String)item.get("UNITCODE"), (String)item.get("INVESTEDUNIT"))).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(disposeConcatUnitStrSet)) {
            return groups;
        }
        List ruleIds = ((UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class)).findAllRuleIdsBySystemIdAndRuleTypes(reportSystemId, Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode(), RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT_SEGMENT.getCode(), RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode()));
        if (CollectionUtils.isEmpty((Collection)ruleIds)) {
            return groups;
        }
        env.getCalcContextExpandVariableCenter().getExistsDisposedLedgers().set(true);
        HashSet ruleSet = new HashSet(ruleIds);
        groups = groups.stream().filter(offSetVchrDTO -> {
            GcOffSetVchrItemDTO item = (GcOffSetVchrItemDTO)offSetVchrDTO.getItems().get(0);
            if (!ruleSet.contains(item.getRuleId())) {
                return true;
            }
            return !disposeConcatUnitStrSet.contains(this.getConcatUnitString(item.getUnitId(), item.getOppUnitId()));
        }).collect(Collectors.toList());
        return groups;
    }

    private String getConcatUnitString(String investUnit, String investedUnit) {
        if (investUnit.compareTo(investedUnit) > 0) {
            return investedUnit + "|" + investUnit;
        }
        return investUnit + "|" + investedUnit;
    }
}

