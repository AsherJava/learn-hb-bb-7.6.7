/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.enumerate;

public enum GatherTypeEnum {
    SUM("summate"),
    AVG("averge"),
    MAXIMUM("maximum"),
    MINIMUM("minium"),
    NO_AGREGATE("noagregate");

    private String typeName;

    private GatherTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public static GatherTypeEnum getGatherTypeEnum(String typeName) {
        for (GatherTypeEnum gatherTypeEnum : GatherTypeEnum.values()) {
            if (!gatherTypeEnum.getTypeName().equals(typeName)) continue;
            return gatherTypeEnum;
        }
        throw new IllegalArgumentException("typeName is not exist");
    }
}

