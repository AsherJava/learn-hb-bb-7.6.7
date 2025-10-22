/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.data.engine.gather.util;

import com.jiuqi.nvwa.definition.common.AggrType;

public class AggrTypeConvert {
    public static int gatherTypeToStatKind(AggrType gatherType) {
        if (gatherType == null) {
            return 0;
        }
        switch (gatherType) {
            case NONE: {
                return 6;
            }
            case SUM: {
                return 1;
            }
            case COUNT: {
                return 2;
            }
            case AVERAGE: {
                return 3;
            }
            case MIN: {
                return 5;
            }
            case MAX: {
                return 4;
            }
        }
        return 0;
    }
}

