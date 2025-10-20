/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.RegionSettingDefine;

public interface IRuntimeDataRegionSettingService {
    public RegionSettingDefine getRegionSetting(String var1);

    default public RegionSettingDefine getRegionSettingByRegion(String dataRegionKey, String formSchemeKey) {
        return this.getRegionSetting(dataRegionKey);
    }
}

