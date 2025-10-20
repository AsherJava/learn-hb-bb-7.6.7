/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;

public class DirectInvestmentDTO
extends AbstractInvestmentRule {
    @Override
    public String getRuleType() {
        return RuleTypeEnum.DIRECT_INVESTMENT.getCode();
    }

    @Override
    @JsonIgnore
    public boolean isSegment() {
        return false;
    }
}

