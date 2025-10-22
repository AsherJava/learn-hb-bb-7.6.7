/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.api.param.FmlMappingObj;

public interface IFmlMappingProvider {
    public String getMappedFmlSchemeKey(String var1);

    public FmlMappingObj getMappedFml(FmlMappingObj var1);
}

