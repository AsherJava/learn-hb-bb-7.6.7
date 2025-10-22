/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.common;

import java.util.HashMap;

@Deprecated
public enum SystemOptionEditMode {
    OPTION_EDIT_MODE_DEFAULT(0),
    OPTION_EDIT_MODE_INPUT(1),
    OPTION_EDIT_MODE_DROP_DOWN(2),
    OPTION_EDIT_MODE_CHECK_BOX(3),
    OPTION_EDIT_MODE_COLOR_PICKER(4),
    OPTION_EDIT_MODE_RADIO_BUTTON(5),
    OPTION_EDIT_MODE_SLIDE_MENU(6),
    OPTION_EDIT_MODE_INPUT_TEXT_AREA(7);

    private int intValue;
    private static HashMap<Integer, SystemOptionEditMode> mappings;

    private static synchronized HashMap<Integer, SystemOptionEditMode> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private SystemOptionEditMode(int value) {
        this.intValue = value;
        SystemOptionEditMode.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static SystemOptionEditMode forValue(int value) {
        return SystemOptionEditMode.getMappings().get(value);
    }
}

