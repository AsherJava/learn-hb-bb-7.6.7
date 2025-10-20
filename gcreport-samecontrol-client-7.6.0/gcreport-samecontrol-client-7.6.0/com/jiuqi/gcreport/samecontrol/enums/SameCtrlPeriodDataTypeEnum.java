/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum SameCtrlPeriodDataTypeEnum {
    YEAR(1, "\u5e74", 10),
    HALF_YEAR(2, "\u534a\u5e74", 11),
    SEASON(3, "\u5b63", 12),
    MONTH(4, "\u6708", 9),
    TEN_DAY(5, "\u65ec", 13),
    DAY(6, "\u65e5", 14),
    WEEK(7, "\u5468", 15),
    PERIOD(8, "\u671f", 16);

    private Integer dataId;
    private String dataValue;
    private Integer sortOrder;

    private SameCtrlPeriodDataTypeEnum(Integer dataId, String dataValue, Integer sortOrder) {
        this.dataId = dataId;
        this.dataValue = dataValue;
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public Integer getDataId() {
        return this.dataId;
    }

    public String getDataValue() {
        return this.dataValue;
    }

    public static String getTitle(String dataId) {
        SameCtrlPeriodDataTypeEnum[] periods;
        for (SameCtrlPeriodDataTypeEnum tmpPeriod : periods = SameCtrlPeriodDataTypeEnum.values()) {
            if (!String.valueOf(tmpPeriod.getDataId()).equals(dataId)) continue;
            return tmpPeriod.getDataValue();
        }
        return dataId;
    }

    public static SameCtrlPeriodDataTypeEnum getEnumByDataId(Integer dataId) {
        for (SameCtrlPeriodDataTypeEnum typeEnum : SameCtrlPeriodDataTypeEnum.values()) {
            if (typeEnum.getDataId() != dataId) continue;
            return typeEnum;
        }
        return null;
    }

    public static SameCtrlPeriodDataTypeEnum getEnumByDataValue(String periodTitle) {
        SameCtrlPeriodDataTypeEnum[] periods;
        for (SameCtrlPeriodDataTypeEnum tmpPeriod : periods = SameCtrlPeriodDataTypeEnum.values()) {
            if (!tmpPeriod.getDataValue().equals(periodTitle)) continue;
            return tmpPeriod;
        }
        return null;
    }

    public static String getId(String periodTitle) {
        SameCtrlPeriodDataTypeEnum[] periods;
        for (SameCtrlPeriodDataTypeEnum tmpPeriod : periods = SameCtrlPeriodDataTypeEnum.values()) {
            if (!tmpPeriod.getDataValue().equals(periodTitle)) continue;
            return String.valueOf(tmpPeriod.getDataId());
        }
        return periodTitle;
    }
}

