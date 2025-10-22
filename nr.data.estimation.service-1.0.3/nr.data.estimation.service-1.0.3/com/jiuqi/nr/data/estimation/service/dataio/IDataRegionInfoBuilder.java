/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoSub;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.definition.facade.DataRegionDefine;

public interface IDataRegionInfoBuilder {
    public IDataRegionInfo buildDataRegionInfo(String var1);

    public IDataRegionInfo buildDataRegionInfo(DataRegionDefine var1);

    public IDataRegionInfoSub buildDataRegionInfo(IDataRegionInfo var1, IDataSchemeSubDatabase var2);

    public DimensionValueSet buildPubColumnValueSet(String var1, DimensionValueSet var2);
}

