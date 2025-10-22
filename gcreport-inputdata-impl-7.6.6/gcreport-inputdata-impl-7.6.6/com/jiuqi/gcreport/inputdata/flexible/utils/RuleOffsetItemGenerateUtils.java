/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 */
package com.jiuqi.gcreport.inputdata.flexible.utils;

import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.FlexibleRuleExecutorImpl;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import java.util.List;

public class RuleOffsetItemGenerateUtils {
    public static List<GcOffSetVchrItemDTO> generateOffsetItem(String ruleId, List<InputDataEO> sameGroupList, GcCalcArgmentsDTO arg) {
        AbstractUnionRule rule = UnionRuleUtils.getAbstractUnionRuleById((String)ruleId);
        if (!(rule instanceof FlexibleRuleDTO)) {
            throw new UnsupportedOperationException();
        }
        FlexibleRuleExecutorImpl flexRuleExecutor = new FlexibleRuleExecutorImpl();
        return flexRuleExecutor.createManualOffsetItems((FlexibleRuleDTO)rule, sameGroupList, arg);
    }
}

