/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.enums;

public enum ImportModuleTypeEnum {
    SKIP_REPEAT("skipRepeat"),
    OVERWRITE_REPEAT("overwriteRepeat");

    private final String code;

    private ImportModuleTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

