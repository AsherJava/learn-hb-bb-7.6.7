/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.gcreport.unionrule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.util.List;

public class UnionGroupRuleDTO
extends AbstractUnionRule {
    @Override
    @JsonIgnore
    public List<String> getSrcDebitSubjectCodeList() {
        return null;
    }

    @Override
    @JsonIgnore
    public List<String> getSrcCreditSubjectCodeList() {
        return null;
    }
}

