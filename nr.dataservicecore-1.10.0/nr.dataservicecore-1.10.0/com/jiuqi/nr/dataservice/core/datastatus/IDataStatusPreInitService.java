/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.datastatus;

import com.jiuqi.nr.dataservice.core.datastatus.obj.DataStatusPresetInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;

public interface IDataStatusPreInitService {
    public DataStatusPresetInfo initInfo(DimensionCombination var1, String var2, String var3, Collection<String> var4) throws Exception;

    public DataStatusPresetInfo initInfo(DimensionCombination var1, String var2, Collection<String> var3) throws Exception;
}

