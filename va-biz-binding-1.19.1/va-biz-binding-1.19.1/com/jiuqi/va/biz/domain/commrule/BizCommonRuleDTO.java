/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain.commrule;

import com.jiuqi.va.biz.domain.commrule.BizCommonRuleDO;

public class BizCommonRuleDTO
extends BizCommonRuleDO {
    private String ruleinfo;
    private Boolean canDel;

    public Boolean getCanDel() {
        return this.canDel;
    }

    public void setCanDel(Boolean canDel) {
        this.canDel = canDel;
    }

    public String getRuleinfo() {
        return this.ruleinfo;
    }

    public void setRuleinfo(String ruleinfo) {
        this.ruleinfo = ruleinfo;
    }
}

