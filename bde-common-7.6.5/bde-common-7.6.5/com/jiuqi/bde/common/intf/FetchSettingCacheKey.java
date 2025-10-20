/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.intf;

public class FetchSettingCacheKey {
    private String regionId;
    private String bizModelCode;
    private String optimizeRuleGroup;

    public FetchSettingCacheKey() {
    }

    public FetchSettingCacheKey(String regionId, String bizModelCode, String optimizeRuleGroup) {
        this.regionId = regionId;
        this.bizModelCode = bizModelCode;
        this.optimizeRuleGroup = optimizeRuleGroup;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getOptimizeRuleGroup() {
        return this.optimizeRuleGroup;
    }

    public void setOptimizeRuleGroup(String optimizeRuleGroup) {
        this.optimizeRuleGroup = optimizeRuleGroup;
    }

    public String getBizModelCode() {
        return this.bizModelCode;
    }

    public void setBizModelCode(String bizModelCode) {
        this.bizModelCode = bizModelCode;
    }

    public String toString() {
        return "FetchSettingCacheKey [regionId=" + this.regionId + ", bizModelCode=" + this.bizModelCode + ", optimizeRuleGroup=" + this.optimizeRuleGroup + "]";
    }
}

