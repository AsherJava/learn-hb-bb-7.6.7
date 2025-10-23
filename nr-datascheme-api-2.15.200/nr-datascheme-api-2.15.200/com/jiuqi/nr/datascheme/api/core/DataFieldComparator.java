/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import java.util.Comparator;
import java.util.Objects;

public class DataFieldComparator
implements Comparator<DataField> {
    @Override
    public int compare(DataField first, DataField second) {
        String secondOrder;
        DataFieldKind secondKind;
        if (first == second) {
            return 0;
        }
        if (first == null) {
            return 1;
        }
        if (second == null) {
            return -1;
        }
        DataFieldKind firstKind = first.getDataFieldKind();
        if (firstKind != (secondKind = second.getDataFieldKind())) {
            if (firstKind == null) {
                return 1;
            }
            if (secondKind == null) {
                return -1;
            }
            return secondKind.getValue() - firstKind.getValue();
        }
        String firstOrder = first.getOrder();
        if (Objects.equals(firstOrder, secondOrder = second.getOrder())) {
            return 0;
        }
        if (firstOrder == null) {
            return 1;
        }
        if (secondOrder == null) {
            return -1;
        }
        return firstOrder.compareTo(secondOrder);
    }
}

