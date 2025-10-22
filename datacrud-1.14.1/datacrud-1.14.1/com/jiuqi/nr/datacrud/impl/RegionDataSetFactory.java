/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.RegionDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionDataSetFactory {
    @Autowired
    private DataEngineService dataEngineService;

    @Deprecated
    public RegionDataSet getRegionRelation(List<? extends IMetaData> metaData, RegionRelation regionRelation) {
        RegionDataSet regionDataSet = new RegionDataSet(metaData);
        regionDataSet.setDataEngineService(this.dataEngineService);
        regionDataSet.setRegionRelation(regionRelation);
        return regionDataSet;
    }

    public RegionDataSet getRegionDataSet(List<? extends IMetaData> metaData, RegionRelation regionRelation) {
        RegionDataSet regionDataSet = new RegionDataSet(metaData);
        regionDataSet.setDataEngineService(this.dataEngineService);
        regionDataSet.setRegionRelation(regionRelation);
        regionDataSet.setRows(Collections.emptyList());
        return regionDataSet;
    }
}

