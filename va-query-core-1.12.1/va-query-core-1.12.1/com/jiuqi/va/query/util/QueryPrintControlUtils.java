/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.print.TableCellProp;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public final class QueryPrintControlUtils {
    private QueryPrintControlUtils() {
    }

    public static float calculateCellLeftRightPadding(TableCellProp cellProp) {
        if (Objects.isNull(cellProp)) {
            return 0.0f;
        }
        int leftRightPadding = 0;
        String padding = Optional.ofNullable(cellProp.getPadding()).orElse("0 0 0 0");
        int[] cellPaddingArray = Arrays.stream(padding.split(" ")).mapToInt(Integer::parseInt).toArray();
        return leftRightPadding += cellPaddingArray[1] + cellPaddingArray[3];
    }
}

