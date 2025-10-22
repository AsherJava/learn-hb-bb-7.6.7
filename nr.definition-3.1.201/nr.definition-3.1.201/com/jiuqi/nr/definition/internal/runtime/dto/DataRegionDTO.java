/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import java.util.ArrayList;
import java.util.List;

public class DataRegionDTO {
    private final DataRegionDefine dataRegionDefine;
    private final RegionSettingDefine regionSettingDefine;
    private final List<DataLinkDefine> dataLinks;

    public DataRegionDTO(DataRegionDefine dataRegionDefine, RegionSettingDefine regionSettingDefine) {
        this.dataRegionDefine = dataRegionDefine;
        this.regionSettingDefine = regionSettingDefine;
        this.dataLinks = new ArrayList<DataLinkDefine>();
    }

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public RegionSettingDefine getRegionSettingDefine() {
        return this.regionSettingDefine;
    }

    public List<DataLinkDefine> getDataLinks() {
        return this.dataLinks;
    }
}

