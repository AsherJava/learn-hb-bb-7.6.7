/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.invest.calculate.rule.processor.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;

public class CheckAllowCalcUtil {
    public static boolean checkAllowCalcFlag(@NotNull AbstractInvestmentRule rule, GcInvestBillGroupDTO investBillGroupDTO, int currentMonth) {
        if (rule == null || investBillGroupDTO == null) {
            throw new BusinessRuntimeException("\u89c4\u5219\u672a\u7a7a\u6216\u62b5\u9500\u6570\u636e\u4e3a\u7a7a");
        }
        List changeRatioOfCurrYear = rule.getChangeRatioOfCurrYear();
        List changeRatioOfCurrMonth = rule.getChangeRatioOfCurrMonth();
        List<DefaultTableEntity> currYearItems = investBillGroupDTO.getCurrYearItems();
        boolean currYearAllowCalcFlag = CheckAllowCalcUtil.checkYearAllowCalcFlag(changeRatioOfCurrYear, currYearItems);
        boolean currMonthAllowCalcFlag = CheckAllowCalcUtil.checkMonthAllowCalcFlag(changeRatioOfCurrMonth, currYearItems, currentMonth);
        return currYearAllowCalcFlag && currMonthAllowCalcFlag;
    }

    private static boolean checkYearAllowCalcFlag(List<String> changeRatio, List<DefaultTableEntity> currYearItems) {
        if (CollectionUtils.isEmpty(changeRatio)) {
            return true;
        }
        return currYearItems.stream().anyMatch(item -> changeRatio.contains(item.getFieldValue("CHANGESCENARIO")));
    }

    private static boolean checkMonthAllowCalcFlag(List<String> changeRatio, List<DefaultTableEntity> currYearItems, int currentMonthValue) {
        if (CollectionUtils.isEmpty(changeRatio) || currYearItems == null) {
            return true;
        }
        HashSet<String> changeRatioSet = new HashSet<String>(changeRatio);
        int currentMonth = currentMonthValue;
        return currYearItems.stream().anyMatch(item -> {
            Object changeDateObj = item.getFieldValue("CHANGEDATE");
            if (!(changeDateObj instanceof Date)) {
                return false;
            }
            Date changeDate = (Date)changeDateObj;
            Object changeScenarioObj = item.getFieldValue("CHANGESCENARIO");
            if (!(changeScenarioObj instanceof String)) {
                return false;
            }
            String changeScenario = (String)changeScenarioObj;
            return currentMonth == DateUtils.getDateFieldValue((Date)changeDate, (int)2) && changeRatioSet.contains(changeScenario);
        });
    }
}

