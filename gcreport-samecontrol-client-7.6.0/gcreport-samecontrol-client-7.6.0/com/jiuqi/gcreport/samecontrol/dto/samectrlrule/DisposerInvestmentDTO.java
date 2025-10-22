/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.dto.samectrlrule;

import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractSameCtrlRule;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;

public class DisposerInvestmentDTO
extends AbstractSameCtrlRule {
    @Override
    public SameCtrlRuleTypeEnum getRuleType() {
        return SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT;
    }
}

