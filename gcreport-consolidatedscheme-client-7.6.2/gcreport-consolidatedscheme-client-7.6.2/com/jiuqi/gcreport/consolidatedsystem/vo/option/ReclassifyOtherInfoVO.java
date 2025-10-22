/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import java.util.Collections;
import java.util.List;

public class ReclassifyOtherInfoVO {
    private List<String> ruleIds;
    private List<Integer> elmModes;
    private List<String> dimensionIds;
    private Boolean containPhs;

    public List<String> getRuleIds() {
        if (null == this.ruleIds) {
            return Collections.emptyList();
        }
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public List<Integer> getElmModes() {
        if (null == this.elmModes) {
            return Collections.emptyList();
        }
        return this.elmModes;
    }

    public void setElmModes(List<Integer> elmModes) {
        this.elmModes = elmModes;
    }

    public List<String> getDimensionIds() {
        if (null == this.dimensionIds) {
            return Collections.emptyList();
        }
        return this.dimensionIds;
    }

    public void setDimensionIds(List<String> dimensionIds) {
        this.dimensionIds = dimensionIds;
    }

    public Boolean isContainPhs() {
        if (null == this.containPhs) {
            this.containPhs = Boolean.TRUE;
        }
        return this.containPhs;
    }

    public void setContainPhs(Boolean containPhs) {
        this.containPhs = containPhs;
    }
}

