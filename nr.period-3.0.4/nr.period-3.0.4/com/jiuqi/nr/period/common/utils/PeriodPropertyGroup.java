/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PeriodPropertyGroup {
    PERIOD_GROUP_BY_YEAR(1, "\u6309\u5e74\u5206\u7ec4", "\u5e74");

    private int type;
    private String title;
    private String groupName;
    private static Map<Integer, PeriodPropertyGroup> mappings;

    private PeriodPropertyGroup() {
    }

    private PeriodPropertyGroup(int type, String title, String groupName) {
        this.type = type;
        this.title = title;
        this.groupName = groupName;
    }

    private static Map<Integer, PeriodPropertyGroup> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(PeriodPropertyGroup.values()).collect(Collectors.toMap(PeriodPropertyGroup::getType, f -> f));
        }
        return mappings;
    }

    public int getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public static PeriodPropertyGroup forType(int type) {
        return PeriodPropertyGroup.getMappings().get(type);
    }
}

