/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.storage.enumeration;

public enum EstimationFormType {
    inputForm("\u8f93\u5165\u8868", "W"),
    outputForm("\u8f93\u51fa\u8868", "R");

    public String name;
    public String value;

    private EstimationFormType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static EstimationFormType toType(String value) {
        for (EstimationFormType type : EstimationFormType.values()) {
            if (!type.value.equals(value)) continue;
            return type;
        }
        return null;
    }
}

