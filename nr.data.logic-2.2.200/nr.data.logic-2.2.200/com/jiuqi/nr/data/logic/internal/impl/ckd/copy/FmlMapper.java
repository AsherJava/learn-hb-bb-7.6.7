/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.impl.ckd.copy;

import com.jiuqi.nr.data.logic.api.param.FmlMappingObj;
import com.jiuqi.nr.data.logic.spi.IFmlMappingProvider;
import java.util.Map;

public class FmlMapper
implements IFmlMappingProvider {
    private final Map<FmlMappingObj, FmlMappingObj> srcFmlMap;
    private final Map<String, String> srcFmlSchemeMap;

    public FmlMapper(Map<FmlMappingObj, FmlMappingObj> srcFmlMap, Map<String, String> srcFmlSchemeMap) {
        this.srcFmlMap = srcFmlMap;
        this.srcFmlSchemeMap = srcFmlSchemeMap;
    }

    @Override
    public String getMappedFmlSchemeKey(String curFmlSchemeKey) {
        return this.srcFmlSchemeMap.get(curFmlSchemeKey);
    }

    @Override
    public FmlMappingObj getMappedFml(FmlMappingObj curFml) {
        return this.srcFmlMap.get(curFml);
    }
}

