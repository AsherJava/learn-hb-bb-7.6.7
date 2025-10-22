/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 */
package com.jiuqi.nr.dataentry.readwrite;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccessBase;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.util.List;
import java.util.Map;

public interface IBatchDimensionReadWriteAccess
extends IReadWriteAccessBase {
    public static final String FORM = "FORM";
    public static final String UNIT = "UNIT";

    public Object initCache(ReadWriteAccessCacheParams var1) throws Exception;

    public ReadWriteAccessDesc matchingAccessLevel(Object var1, Consts.FormAccessLevel var2, DimensionCacheKey var3, String var4, EntityViewData var5);

    public String getCacheLevel();

    public String getStatusFormKey(Map<String, DimensionValue> var1, List<EntityViewData> var2, String var3, Consts.FormAccessLevel var4, String var5, ReadWriteAccessCacheManager var6);

    default public boolean canUseReadTempTable(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        return true;
    }
}

