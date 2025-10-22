/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import java.util.List;
import java.util.stream.Collectors;

public interface TargetRangeUnitProvider {
    public List<String> getRangeEntities(String var1);

    default public List<String> retainAll(String period, List<String> list) {
        List<String> rangeEntities = this.getRangeEntities(period);
        return list.stream().filter(rangeEntities::contains).collect(Collectors.toList());
    }
}

