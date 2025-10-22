/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.spi;

import com.jiuqi.nr.fielddatacrud.RegionPO;
import java.util.List;
import java.util.Set;

public interface ParamProvider {
    public Set<RegionPO> getRegions(String var1, List<String> var2);
}

