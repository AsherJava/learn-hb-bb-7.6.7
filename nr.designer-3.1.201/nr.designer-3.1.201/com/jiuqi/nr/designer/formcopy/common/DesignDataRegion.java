/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 */
package com.jiuqi.nr.designer.formcopy.common;

import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;

public class DesignDataRegion {
    private DesignDataRegionDefine region;
    private DesignRegionSettingDefine regionSetting;

    public DesignDataRegion(DesignDataRegionDefine region, DesignRegionSettingDefine regionSetting) {
        this.region = region;
        this.regionSetting = regionSetting;
    }

    public String getKey() {
        return this.region.getKey();
    }

    public DesignDataRegionDefine getRegion() {
        return this.region;
    }

    public DesignRegionSettingDefine getRegionSetting() {
        return this.regionSetting;
    }
}

