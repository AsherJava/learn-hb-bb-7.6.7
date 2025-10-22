/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.io.tz.dataset;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import java.util.List;

public interface ITzRegionDataSet {
    public RegionData getRegionData();

    public boolean isFloatRegion();

    public List<ExportFieldDefine> getFieldDataList();

    public DimensionValueSet importDatas(List<Object> var1) throws Exception;

    public void commit() throws Exception;

    public List<FieldDefine> getBizFieldDefList();

    public FieldDefine getUnitFieldDefine() throws Exception;
}

