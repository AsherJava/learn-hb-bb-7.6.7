/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.vo;

public class FetchSettingExtInfoVo {
    private String optimizeRuleGroup;
    private String memo;

    public String getOptimizeRuleGroup() {
        return this.optimizeRuleGroup;
    }

    public void setOptimizeRuleGroup(String optimizeRuleGroup) {
        this.optimizeRuleGroup = optimizeRuleGroup;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String describeInfo) {
        this.memo = describeInfo;
    }

    public String toString() {
        return "FetchSettingExtInfoVo [optimizeRuleGroup=" + this.optimizeRuleGroup + ", describeInfo=" + this.memo + "]";
    }
}

