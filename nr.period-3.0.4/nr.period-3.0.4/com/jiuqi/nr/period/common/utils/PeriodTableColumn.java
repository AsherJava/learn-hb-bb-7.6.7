/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.period.common.utils;

import com.jiuqi.nvwa.definition.common.ColumnModelType;

public enum PeriodTableColumn {
    KEY("\u65f6\u671f\u4e3b\u952e", "P_KEY", 40, 1, false, ColumnModelType.STRING),
    CODE("\u65f6\u671f\u7f16\u7801", "P_CODE", 9, 2, true, ColumnModelType.STRING),
    ALIAS("\u65f6\u671f\u522b\u540d", "P_ALIAS", 50, 3, true, ColumnModelType.STRING),
    TITLE("\u65f6\u671f\u540d\u79f0", "P_TITLE", 50, 4, true, ColumnModelType.STRING),
    STARTDATE("\u65f6\u671f\u5f00\u59cb\u65f6\u95f4", "P_STARTDATE", 0, 5, true, ColumnModelType.DATETIME),
    ENDDATE("\u65f6\u671f\u7ed3\u675f\u65f6\u95f4", "P_ENDDATE", 0, 6, true, ColumnModelType.DATETIME),
    YEAR("\u65f6\u671f\u6240\u5c5e\u5e74", "P_YEAR", 4, 7, true, ColumnModelType.INTEGER),
    QUARTER("\u65f6\u671f\u6240\u5c5e\u5b63", "P_QUARTER", 1, 8, true, ColumnModelType.INTEGER),
    MONTH("\u65f6\u671f\u6240\u5c5e\u6708", "P_MONTH", 2, 9, true, ColumnModelType.INTEGER),
    DAY("\u65f6\u671f\u6240\u5c5e\u6708\u5185\u5929", "P_DAY", 2, 10, true, ColumnModelType.INTEGER),
    TIMEKEY("\u65f6\u671f\u5185\u7b2c\u4e00\u5929", "P_TIMEKEY", 8, 11, true, ColumnModelType.STRING),
    DAYS("\u65f6\u671f\u5305\u542b\u5929\u6570", "P_DAYS", 3, 12, true, ColumnModelType.INTEGER),
    CREATETIME("\u521b\u5efa\u65f6\u95f4", "P_CREATETIME", 0, 13, true, ColumnModelType.DATETIME),
    CREATEUSER("\u521b\u5efa\u4eba", "P_CREATEUSER", 50, 14, true, ColumnModelType.STRING),
    UPDATETIME("\u6700\u540e\u66f4\u65b0\u65f6\u95f4", "P_UPDATETIME", 0, 15, true, ColumnModelType.DATETIME),
    UPDATEUSER("\u6700\u540e\u66f4\u65b0\u4eba", "P_UPDATEUSER", 50, 16, true, ColumnModelType.STRING),
    SIMPLETITLE("\u7b80\u79f0", "P_SIMPLETITLE", 50, 17, true, ColumnModelType.STRING);

    private String title;
    private String code;
    private int lenght;
    private int order;
    private boolean nullable;
    private ColumnModelType type;

    private PeriodTableColumn(String title, String code, int lenght, int order, boolean nullable, ColumnModelType type) {
        this.title = title;
        this.code = code;
        this.lenght = lenght;
        this.order = order;
        this.nullable = nullable;
        this.type = type;
    }

    private PeriodTableColumn() {
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public int getLenght() {
        return this.lenght;
    }

    public int getOrder() {
        return this.order;
    }

    public boolean getNullable() {
        return this.nullable;
    }

    public ColumnModelType getType() {
        return this.type;
    }
}

