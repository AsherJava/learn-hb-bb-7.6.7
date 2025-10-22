/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DataLinkEditMode {
    DATA_LINK_DEFAULT(0),
    DATA_LINK_INPUT(1),
    DATA_LINK_NONE(2),
    DATA_LINK_DROP_DOWN(3),
    DATA_LINK_DROP_DWON_EDIT(4),
    DATA_LINK_POP_DIALOG(5),
    DATA_LINK_CHECK_BOX(6),
    DATA_LINK_READ_ONLY(7),
    DATA_LINK_SCORE_EDIT(8),
    DATA_LINK_FIELD_READ_ONLY(9),
    DATA_LINK_MULTILINETEXT(10);

    private int intValue;
    private static Map<Integer, DataLinkEditMode> mappings;

    private static Map<Integer, DataLinkEditMode> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(DataLinkEditMode.values()).collect(Collectors.toMap(DataLinkEditMode::getValue, f -> f));
        }
        return mappings;
    }

    private DataLinkEditMode(int value) {
        this.intValue = value;
    }

    public int getValue() {
        return this.intValue;
    }

    public static DataLinkEditMode forValue(int value) {
        return DataLinkEditMode.getMappings().get(value);
    }
}

