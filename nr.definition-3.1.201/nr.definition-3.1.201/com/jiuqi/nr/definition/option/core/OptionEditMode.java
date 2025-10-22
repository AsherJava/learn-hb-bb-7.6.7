/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.core;

public enum OptionEditMode {
    OPTION_INPUT(1),
    OPTION_DROP_DOWN(2),
    OPTION_CHECK_BOX(3),
    OPTION_PICKER(4),
    OPTION_RADIO_BUTTON(5),
    OPTION_SWITCH(6),
    OPTION_CUSTOM(100);

    private final int value;

    private OptionEditMode(int intValue) {
        this.value = intValue;
    }

    public int getValue() {
        return this.value;
    }
}

