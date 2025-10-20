/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import com.jiuqi.dc.mappingscheme.client.common.RuleTypeClass;

public class RuleTypeShowVO {
    private String code;
    private String label;
    private String name;
    private Boolean item2ItemFlag;
    private RuleTypeClass ruleTypeClass;

    public RuleTypeShowVO(String code, String label, Boolean item2ItemFlag, RuleTypeClass ruleTypeClass) {
        this.code = code;
        this.label = label;
        this.name = label;
        this.item2ItemFlag = item2ItemFlag;
        this.ruleTypeClass = ruleTypeClass;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getItem2ItemFlag() {
        return this.item2ItemFlag;
    }

    public void setItem2ItemFlag(Boolean item2ItemFlag) {
        this.item2ItemFlag = item2ItemFlag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RuleTypeClass getRuleTypeClass() {
        return this.ruleTypeClass;
    }

    public void setRuleTypeClass(RuleTypeClass ruleTypeClass) {
        this.ruleTypeClass = ruleTypeClass;
    }
}

