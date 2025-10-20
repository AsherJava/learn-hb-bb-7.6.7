/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.enums;

import java.util.Arrays;

public enum UnitTypeEnum {
    ODS_UNITCODE("odsUnitCode", "\u6e90\u5355\u4f4d"),
    UNITCODE("unitCode", "\u4e00\u672c\u8d26\u5355\u4f4d"),
    REPORT_UNITCODE("reportUnitCode,reportUnit", "\u62a5\u8868\u5355\u4f4d");

    private String codeRange;
    private String name;

    private UnitTypeEnum(String codeRange, String name) {
        this.codeRange = codeRange;
        this.name = name;
    }

    public String getCodeRange() {
        return this.codeRange;
    }

    public String getName() {
        return this.name;
    }

    public static Boolean isReportUnit(String unitTypeCode) {
        return Arrays.asList(REPORT_UNITCODE.getCodeRange().split(",")).contains(unitTypeCode);
    }
}

