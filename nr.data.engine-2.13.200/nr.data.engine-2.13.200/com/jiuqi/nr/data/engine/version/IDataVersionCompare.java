/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.engine.version;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.bean.FormCompareDifference;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IDataVersionCompare {
    public List<FormCompareDifference> compareVersionData(Map<String, DimensionValue> var1, String var2, List<String> var3, UUID var4, UUID var5);
}

