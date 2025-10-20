/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.constants;

public enum DistributionTypeEnum {
    ADAPT_CONDITION(0),
    WORKFLOW_PARAM(1),
    ACTION(2),
    EDITABLE_ATTRIBUTE(3),
    INTERFACE_SCHEMA(4);

    private final int value;

    private DistributionTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static DistributionTypeEnum findByValue(int value) {
        for (DistributionTypeEnum distributionTypeEnum : DistributionTypeEnum.values()) {
            if (distributionTypeEnum.getValue() != value) continue;
            return distributionTypeEnum;
        }
        return null;
    }
}

