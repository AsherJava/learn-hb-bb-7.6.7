/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.output;

import com.jiuqi.nr.snapshot.message.DimInfo;
import java.util.List;

public class QueryDimResult {
    private boolean enable = false;
    private List<DimInfo> dimInfos;

    public QueryDimResult() {
    }

    public QueryDimResult(boolean enable, List<DimInfo> dimInfos) {
        this.enable = enable;
        this.dimInfos = dimInfos;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<DimInfo> getDimInfos() {
        return this.dimInfos;
    }

    public void setDimInfos(List<DimInfo> dimInfos) {
        this.dimInfos = dimInfos;
    }
}

