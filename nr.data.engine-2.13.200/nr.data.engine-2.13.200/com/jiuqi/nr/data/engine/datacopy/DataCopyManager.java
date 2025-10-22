/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.engine.datacopy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.nr.data.engine.datacopy.param.DataCopyParam;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.List;

public interface DataCopyManager {
    public void dataCopy(List<String> var1, DimensionValueSet var2, String var3, String var4, IMonitor var5);

    public void dataCopy(DataCopyParam var1, IMonitor var2);

    public void dataCopy(DataCopyParam var1, List<DataRegionDefine> var2, IMonitor var3, double var4, double var6);
}

