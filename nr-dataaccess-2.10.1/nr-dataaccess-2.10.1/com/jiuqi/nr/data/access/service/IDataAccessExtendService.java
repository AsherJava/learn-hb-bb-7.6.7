/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Map;
import javax.validation.constraints.NotNull;

public interface IDataAccessExtendService {
    public IAccessResult visible(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public IAccessResult zbVisible(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public IAccessResult readable(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public IAccessResult zbReadable(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public IAccessResult writeable(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public IAccessResult sysWriteable(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public IAccessResult zbWriteable(@NotNull AccessParam var1, DimensionCombination var2, String var3);

    public Map<String, Object> getExtendResult(@NotNull AccessParam var1, DimensionCombination var2, String var3);
}

