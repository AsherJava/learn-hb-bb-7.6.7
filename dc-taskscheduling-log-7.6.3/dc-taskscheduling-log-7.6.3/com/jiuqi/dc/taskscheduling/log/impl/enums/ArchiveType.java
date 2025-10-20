/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.enums;

import java.util.Objects;
import java.util.stream.Stream;

public enum ArchiveType {
    THREE_DAY("THREEDAY", "\u4e09\u5929", 3, 5),
    ONE_WEEK("ONEWEEK", "\u4e00\u5468", 1, 3),
    HALF_MONTH("HALFMONTH", "\u534a\u4e2a\u6708", 15, 5),
    ONE_MONTH("ONEMONTH", "\u4e00\u4e2a\u6708", 1, 2),
    TWO_MONTH("TWOMONTH", "\u4e24\u4e2a\u6708", 2, 2),
    THREE_MONTH("THREEMONTH", "\u4e09\u4e2a\u6708", 3, 2);

    private String code;
    private String name;
    private int value;
    private int type;

    private ArchiveType(String code, String name, int value, int type) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public int getType() {
        return this.type;
    }

    public static ArchiveType fromCode(String code) {
        return Stream.of(ArchiveType.values()).filter(e -> Objects.equals(e.getCode(), code)).findFirst().orElse(null);
    }
}

