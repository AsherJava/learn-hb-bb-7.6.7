/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Optional;

public interface IDataAccessExtraResultService<R> {
    public String name();

    public Optional<R> getExtraResult(AccessItem var1, String var2, DimensionCombination var3, String var4);
}

