/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.enums;

public enum InventoryUnitEnum {
    PRIME_CONTRACTOR("PRIME_CONTRACTOR", "\u603b\u5305\u65b9"),
    SUB_CONTRACTOR("SUB_CONTRACTOR", "\u5206\u5305\u65b9\u5355\u4f4d");

    private String code;
    private String name;

    private InventoryUnitEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

