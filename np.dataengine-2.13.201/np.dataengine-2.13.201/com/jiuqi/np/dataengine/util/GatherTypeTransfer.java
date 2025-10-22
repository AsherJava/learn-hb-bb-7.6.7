/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.np.dataengine.util;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.nvwa.definition.common.AggrType;

public class GatherTypeTransfer {
    public static FieldGatherType getGatherType(AggrType aggrType) {
        switch (aggrType) {
            case MAX: {
                return FieldGatherType.FIELD_GATHER_MAX;
            }
            case MIN: {
                return FieldGatherType.FIELD_GATHER_MIN;
            }
            case SUM: {
                return FieldGatherType.FIELD_GATHER_SUM;
            }
            case COUNT: {
                return FieldGatherType.FIELD_GATHER_COUNT;
            }
            case AVERAGE: {
                return FieldGatherType.FIELD_GATHER_AVG;
            }
            case DISTINCT_COUNT: {
                return FieldGatherType.FIELD_GATHER_DISTINCT_COUNT;
            }
            case NONE: {
                return FieldGatherType.FIELD_GATHER_NONE;
            }
        }
        return FieldGatherType.FIELD_GATHER_NONE;
    }
}

