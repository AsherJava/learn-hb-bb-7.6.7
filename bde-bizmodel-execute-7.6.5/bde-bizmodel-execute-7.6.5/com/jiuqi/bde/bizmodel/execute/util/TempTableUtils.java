/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 */
package com.jiuqi.bde.bizmodel.execute.util;

import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.common.constant.MatchRuleEnum;

public class TempTableUtils {
    public static String buildSubjectJoinSql(BalanceCondition condi, String fieldName) {
        if (!condi.isEnableDirectFilter() || condi.getConditionMatchRule().getMatchRule() == MatchRuleEnum.RANGE || !condi.isEnableTransTable()) {
            return "";
        }
        return String.format("\t INNER JOIN %1$s TEMPKM ON %2$s LIKE TEMPKM.CODE \n", condi.getConditionMatchRule().getTbName(), fieldName);
    }

    public static String buildSubjectCondiSql(BalanceCondition condi, String fieldName) {
        if (!condi.isEnableDirectFilter() || condi.getConditionMatchRule().getMatchRule() != MatchRuleEnum.RANGE) {
            return "";
        }
        return String.format(" AND %1$s >='%2$s' AND %1$s  <= '%3$sZZ' \n", fieldName, condi.getConditionMatchRule().getSubjectCodes().get(0), condi.getConditionMatchRule().getSubjectCodes().get(1));
    }
}

