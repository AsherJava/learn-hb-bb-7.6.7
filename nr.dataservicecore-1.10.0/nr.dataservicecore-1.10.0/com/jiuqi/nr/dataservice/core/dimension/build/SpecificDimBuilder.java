/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.dimension.build;

import com.jiuqi.nr.dataservice.core.dimension.build.DimColBuildContext;

public interface SpecificDimBuilder {
    public boolean isUse(DimColBuildContext var1, String var2);

    public void setBuildInfo(DimColBuildContext var1, String var2, String var3);
}

