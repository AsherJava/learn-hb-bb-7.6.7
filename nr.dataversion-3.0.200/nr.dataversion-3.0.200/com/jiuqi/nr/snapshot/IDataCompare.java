/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.snapshot;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.snapshot.bean.FormCompareDifference;
import java.util.List;
import java.util.Map;

public interface IDataCompare {
    @Deprecated
    public List<FormCompareDifference> comparePeriodData(Map<String, DimensionValue> var1, String var2, List<String> var3, String var4, String var5);

    @Deprecated
    public List<FormCompareDifference> compareDWData(Map<String, DimensionValue> var1, String var2, List<String> var3, String var4, String var5, String var6);
}

