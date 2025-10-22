/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package nr.midstore2.core.dataset.clear;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public interface IMidstoreDataSetClearService {
    public void clearTableData(String var1, String var2, DimensionValueSet var3) throws Exception;

    public void clearRegionData(String var1, String var2, DimensionValueSet var3) throws Exception;
}

