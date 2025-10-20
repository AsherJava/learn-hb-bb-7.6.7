/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.datamapping.impl.enums;

import java.util.Objects;

public enum RefDynamicField {
    DC_UNITCODE("DC_UNITCODE", "UNITCODE", "\u4e00\u672c\u8d26\u7ec4\u7ec7\u673a\u6784", 0),
    ODS_BOOKCODE("ODS_BOOKCODE", "ODS_BOOKCODE".replace("ODS_", ""), "\u8d26\u7c3f", 0),
    ODS_ACCTYEAR("ODS_ACCTYEAR", "ODS_ACCTYEAR".replace("ODS_", ""), "\u5e74\u5ea6", 0),
    REMARK("REMARK", "REMARK", "\u5907\u6ce8", 1),
    ODS_ASSISTCODE("ODS_ASSISTCODE", "ODS_ASSISTCODE".replace("ODS_", ""), "\u5355\u4f4d\u6269\u5c55\u7ef4\u5ea6\u4ee3\u7801", 1),
    ODS_ASSISTNAME("ODS_ASSISTNAME", "ODS_ASSISTNAME".replace("ODS_", ""), "\u5355\u4f4d\u6269\u5c55\u7ef4\u5ea6\u540d\u79f0", 1),
    ODS_CUSTOM_CODE("ODS_CUSTOM_CODE", "ODS_CUSTOM_CODE".replace("ODS_", ""), "\u81ea\u5b9a\u4e49\u4ee3\u7801", 1);

    private final String fieldName;
    private final String refFieldName;
    private final String fieldTitle;
    private final Integer fieldType;

    public String getFieldName() {
        return this.fieldName;
    }

    public String getRefFieldName() {
        return this.refFieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    private RefDynamicField(String fieldName, String refFieldName, String fieldTitle, Integer fieldType) {
        this.fieldName = fieldName;
        this.refFieldName = refFieldName;
        this.fieldTitle = fieldTitle;
        this.fieldType = fieldType;
    }

    public static String getRefFieldName(String fieldName) {
        return RefDynamicField.valueOf(fieldName).getRefFieldName();
    }

    public static String getFieldTitle(String fieldName) {
        return RefDynamicField.valueOf(fieldName).getFieldTitle();
    }

    public static boolean containsRefFieldName(String refFieldName, Integer fieldType) {
        for (RefDynamicField field : RefDynamicField.values()) {
            if (!field.getRefFieldName().equals(refFieldName)) continue;
            return Objects.isNull(fieldType) || field.fieldType.equals(fieldType);
        }
        return false;
    }

    public static String getFieldName(String refFieldName) {
        for (RefDynamicField field : RefDynamicField.values()) {
            if (!field.refFieldName.equals(refFieldName)) continue;
            return field.getFieldName();
        }
        return refFieldName;
    }
}

