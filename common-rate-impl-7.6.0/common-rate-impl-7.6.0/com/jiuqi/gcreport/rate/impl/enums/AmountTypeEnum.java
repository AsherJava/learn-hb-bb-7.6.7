/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.enums;

import java.util.UUID;

public enum AmountTypeEnum {
    TYPE_NC(1, "\u5e74\u521d\u91d1\u989d"),
    TYPE_QC(2, "\u671f\u521d\u91d1\u989d"),
    TYPE_BQ(3, "\u672c\u671f\u53d1\u751f"),
    TYPE_BN(4, "\u672c\u5e74\u7d2f\u8ba1"),
    TYPE_QM(5, "\u671f\u672b\u4f59\u989d");

    private static final long ID = 1L;
    private String id;
    private Integer code;
    private String name;

    private AmountTypeEnum(Integer code, String name) {
        this.id = new UUID(1L, code.intValue()).toString().toUpperCase();
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}

