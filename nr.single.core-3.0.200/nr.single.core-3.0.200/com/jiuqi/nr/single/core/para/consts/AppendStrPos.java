/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.consts;

import java.util.HashMap;

public enum AppendStrPos {
    ASPPAGE_TOPLEFT(0),
    ASPPAGE_TOPCENTER(1),
    ASPPAGE_TOPRIGHT(2),
    ASPGRID_TOPLEFT(3),
    ASPGRID_TOPCENTER(4),
    ASPGRID_TOPRIGHT(5),
    ASPGRID_BOTTOMLEFT(6),
    ASPGRID_BOTTOMCENTER(7),
    ASPGRID_BOTTOMRIGHT(8),
    ASPPAGE_BOTTOMLEFT(9),
    ASPPAGE_BOTTOMCENTER(10),
    ASPPAGE_BOTTOMRIGHT(11);

    private int intValue;
    private static HashMap<Integer, AppendStrPos> mappings;

    private static synchronized HashMap<Integer, AppendStrPos> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private AppendStrPos(int value) {
        this.intValue = value;
        AppendStrPos.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static AppendStrPos forValue(int value) {
        return AppendStrPos.getMappings().get(value);
    }
}

