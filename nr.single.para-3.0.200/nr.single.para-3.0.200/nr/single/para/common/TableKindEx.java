/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.common;

import java.util.HashMap;

public enum TableKindEx {
    TABLE_KIND_UNDEFINED(0),
    TABLE_KIND_SYSTEM(1),
    TABLE_KIND_ENTITY(2),
    TABLE_KIND_DICTIONARY(3),
    TABLE_KIND_BIZDATA(4),
    TABLE_KIND_USER_PROFILE(5),
    TABLE_KIND_ENTITY_PERIOD(6),
    TABLE_KIND_MEASUREMENT_UNIT(7),
    TABLE_KIND_CALIBER(8),
    TABLE_KIND_EXTERNAL(9),
    TABLE_KIND_ENTITY_VERSION(10);

    private int intValue;
    private static HashMap<Integer, TableKindEx> mappings;

    private static synchronized HashMap<Integer, TableKindEx> getMappings() {
        if (mappings == null) {
            mappings = new HashMap();
        }
        return mappings;
    }

    private TableKindEx(int value) {
        this.intValue = value;
        TableKindEx.getMappings().put(value, this);
    }

    public int getValue() {
        return this.intValue;
    }

    public static TableKindEx forValue(int value) {
        return TableKindEx.getMappings().get(value);
    }
}

