/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.temptable.common;

public enum BaseDynamicTypeEnum {
    THREE_COLUMN("threeColumn", 3),
    FIVE_COLUMN("fiveColumn", 5),
    EIGHT_COLUMN("eightColumn", 8),
    TEN_COLUMN("tenColumn", 10),
    TWENTY_COLUMN("twentyColumn", 20);

    private String type;
    private int columnCount;

    private BaseDynamicTypeEnum(String type, int columnCount) {
        this.type = type;
        this.columnCount = columnCount;
    }

    public String getType() {
        return this.type;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public static BaseDynamicTypeEnum getType(String type) {
        for (BaseDynamicTypeEnum baseDynamicTypeEnum : BaseDynamicTypeEnum.values()) {
            if (!baseDynamicTypeEnum.type.equals(type)) continue;
            return baseDynamicTypeEnum;
        }
        return null;
    }
}

