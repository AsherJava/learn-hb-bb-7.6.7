/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.metabase;

import com.jiuqi.va.bizmeta.domain.dimension.MetaBaseDim;
import java.util.List;

public class MetaBaseInfoVO {
    private List<MetaBaseDim> baseInfos;

    public List<MetaBaseDim> getBaseInfos() {
        return this.baseInfos;
    }

    public void setBaseInfos(List<MetaBaseDim> baseInfos) {
        this.baseInfos = baseInfos;
    }
}

