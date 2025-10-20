/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.missmapping.impl.enums;

public enum UnitTypeEnum {
    ODS_UNITCODE("odsUnitCode", "\u6e90\u5355\u4f4d"),
    UNITCODE("unitCode", "\u4e00\u672c\u8d26\u5355\u4f4d"),
    REPORT_UNITCODE("reportUnitCode", "\u62a5\u8868\u5355\u4f4d");

    private String code;
    private String name;

    private UnitTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static UnitTypeEnum getCode(String code) {
        for (UnitTypeEnum unitType : UnitTypeEnum.values()) {
            if (!unitType.getCode().equals(code)) continue;
            return unitType;
        }
        return null;
    }

    public static UnitTypeEnum getName(String name) {
        for (UnitTypeEnum unitType : UnitTypeEnum.values()) {
            if (!unitType.getName().equals(name)) continue;
            return unitType;
        }
        return null;
    }
}

