/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.gcreport.samecontrol.dto.samectrlrule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import java.util.List;

public class SameCtrlGroupRuleDTO
extends AbstractCommonRule {
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

