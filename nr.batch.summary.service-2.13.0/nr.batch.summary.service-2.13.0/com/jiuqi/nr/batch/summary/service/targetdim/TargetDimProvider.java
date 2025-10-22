/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import java.util.List;

public interface TargetDimProvider {
    default public boolean isEmptyTargetDim(String period) {
        List<String> targetDims = this.getTargetDims(period);
        return targetDims == null || targetDims.isEmpty();
    }

    public List<String> getTargetDims(String var1);

    public List<String> getEntityRowKeys(String var1, String var2);
}

