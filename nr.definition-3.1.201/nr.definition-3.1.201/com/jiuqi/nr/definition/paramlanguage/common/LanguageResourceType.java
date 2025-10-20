/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.paramlanguage.common;

import java.util.HashMap;
import java.util.Map;

public enum LanguageResourceType {
    TASKTITLE(0),
    SCHEMETITLE(1),
    FORMULASCHEMETITLE(2),
    PRINTSCHEMETITLE(3),
    FORMTITLE(4),
    FORMGROUPTITLE(5),
    FIELDTITLE(6),
    FORMULADESCRIPTION(7),
    FIELDDESCRIPTION(8),
    REGIONTAB(9),
    ORGENTITYALIAS(10);

    private final int value;
    private static final Map<Integer, LanguageResourceType> map;

    private LanguageResourceType(int value) {
        this.value = value;
    }

    public static LanguageResourceType valueOf(int value) {
        return map.get(value);
    }

    public int getValue() {
        return this.value;
    }

    static {
        map = new HashMap<Integer, LanguageResourceType>();
        for (LanguageResourceType type : LanguageResourceType.values()) {
            map.put(type.value, type);
        }
    }
}

