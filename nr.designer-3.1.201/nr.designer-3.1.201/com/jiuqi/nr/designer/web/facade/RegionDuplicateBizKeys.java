/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

public class RegionDuplicateBizKeys {
    String regionBizKeyFields;
    Boolean allowDuplicateKey;
    String regionGatherFields;

    public String getRegionBizKeyFields() {
        return this.regionBizKeyFields;
    }

    public Boolean getAllowDuplicateKey() {
        return this.allowDuplicateKey;
    }

    public String getRegionGatherFields() {
        return this.regionGatherFields;
    }

    public void setRegionBizKeyFields(String regionBizKeyFields) {
        this.regionBizKeyFields = regionBizKeyFields;
    }

    public void setAllowDuplicateKey(Boolean allowDuplicateKey) {
        this.allowDuplicateKey = allowDuplicateKey;
    }

    public void setRegionGatherFields(String regionGatherFields) {
        this.regionGatherFields = regionGatherFields;
    }
}

