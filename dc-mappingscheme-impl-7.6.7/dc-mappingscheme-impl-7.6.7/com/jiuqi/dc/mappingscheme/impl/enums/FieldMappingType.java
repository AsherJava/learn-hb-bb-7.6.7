/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.enums;

public enum FieldMappingType {
    SOURCE_FIELD("SOURCE", "\u6e90\u8868\u5b57\u6bb5"),
    CONSTANT("CONSTANT", "\u5e38\u91cf"),
    CUSTOM_FIELD("CUSTOM", "\u81ea\u5b9a\u4e49\u5b57\u6bb5");

    private String code;
    private String name;

    private FieldMappingType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FieldMappingType getTypeCode(String code) {
        for (FieldMappingType fieldMappingType : FieldMappingType.values()) {
            if (!fieldMappingType.getCode().equals(code)) continue;
            return fieldMappingType;
        }
        return null;
    }

    public static FieldMappingType getTypeName(String name) {
        for (FieldMappingType fieldMappingType : FieldMappingType.values()) {
            if (!fieldMappingType.getName().equals(name)) continue;
            return fieldMappingType;
        }
        return null;
    }
}

