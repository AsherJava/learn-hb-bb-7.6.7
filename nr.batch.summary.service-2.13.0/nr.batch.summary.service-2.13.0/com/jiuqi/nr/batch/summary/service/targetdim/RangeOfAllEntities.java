/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.targetdim;

import com.jiuqi.nr.batch.summary.service.targetdim.TargetRangeUnitProvider;
import java.util.ArrayList;
import java.util.List;

public class RangeOfAllEntities
implements TargetRangeUnitProvider {
    @Override
    public List<String> getRangeEntities(String period) {
        return new ArrayList<String>();
    }

    @Override
    public List<String> retainAll(String period, List<String> list) {
        return list;
    }
}

