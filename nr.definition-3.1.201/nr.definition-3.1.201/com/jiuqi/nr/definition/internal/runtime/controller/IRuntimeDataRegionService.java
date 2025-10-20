/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.RegionPartitionDefine;
import java.util.List;

public interface IRuntimeDataRegionService {
    public DataRegionDefine queryDataRegion(String var1);

    public List<DataRegionDefine> getDataRegionsInForm(String var1);

    public List<RegionPartitionDefine> getRegionPartitionDefines(String var1);

    default public DataRegionDefine getDataRegion(String dataRegionKey, String formSchemeKey) {
        return this.queryDataRegion(dataRegionKey);
    }

    default public List<DataRegionDefine> listDataRegionByForm(String formKey, String formSchemKey) {
        return this.getDataRegionsInForm(formKey);
    }

    public DataRegionDefine getDataRegion(String var1, String var2, String var3);
}

