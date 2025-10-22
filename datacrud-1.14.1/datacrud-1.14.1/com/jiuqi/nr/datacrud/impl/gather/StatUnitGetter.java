/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.datacrud.impl.gather;

import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.datacrud.impl.gather.DistinctCountStatUnit;
import com.jiuqi.nr.datacrud.impl.gather.NoneStatUnit;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;

public class StatUnitGetter {
    public static StatUnit createStatUnit(int dataType, int gatherType) {
        int statKind = 0;
        if (gatherType == DataFieldGatherType.SUM.getValue()) {
            statKind = 1;
        } else if (gatherType == DataFieldGatherType.COUNT.getValue()) {
            statKind = 2;
        } else if (gatherType == DataFieldGatherType.AVERAGE.getValue()) {
            statKind = 3;
        } else if (gatherType == DataFieldGatherType.MIN.getValue()) {
            statKind = 5;
        } else if (gatherType == DataFieldGatherType.MAX.getValue()) {
            statKind = 4;
        } else if (gatherType == DataFieldGatherType.DISTINCT_COUNT.getValue()) {
            statKind = 9;
        }
        if (statKind == 0) {
            return new NoneStatUnit();
        }
        if (statKind == 9) {
            return new DistinctCountStatUnit();
        }
        return StatItem.createStatUnit((int)statKind, (int)dataType);
    }

    public static StatUnit createStatUnitByStat(int dataType, int statKind) {
        if (statKind == 0) {
            return new NoneStatUnit();
        }
        if (statKind == 9) {
            return new DistinctCountStatUnit();
        }
        return StatItem.createStatUnit((int)statKind, (int)dataType);
    }
}

