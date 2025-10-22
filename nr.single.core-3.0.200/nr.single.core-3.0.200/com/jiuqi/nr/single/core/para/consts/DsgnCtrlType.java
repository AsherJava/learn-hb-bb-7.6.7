/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum DsgnCtrlType {
    DCS_CUSTOM(0),
    DCS_LABLE(1),
    DCS_CHECKBOX(2),
    DCS_RADIO_BUTTON(3),
    DCS_EDIT(4),
    DCS_COMBOBOX(5),
    DCS_IMAGE(6),
    DCS_RICHEDIT(7);

    private int intValue;
    private static HashMap<Integer, DsgnCtrlType> mappings;

    private static synchronized HashMap<Integer, DsgnCtrlType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private DsgnCtrlType(int value) {
        this.intValue = value;
        DsgnCtrlType.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static DsgnCtrlType forValue(int value) {
        return DsgnCtrlType.getMappings().get(value);
    }
}

