/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum VchrHandleStateEnum {
    UNHANDLED(0, "\u672a\u5904\u7406"),
    HANDLED(1, "\u5df2\u5904\u7406"),
    MISS_MAPPING(2, "\u7f3a\u6620\u5c04"),
    HANDLED_UPDATE(3, "\u4fee\u6539\u62b5\u6d88\u5904\u7406"),
    HANDLED_DELETE(4, "\u5220\u9664\u62b5\u6d88\u5904\u7406"),
    HANDLED_MIDDLE(5, "\u5df2\u5904\u7406\u4e2d\u95f4\u6001"),
    MISS_MAPPING_MIDDLE(6, "\u7f3a\u6620\u5c04\u4e2d\u95f4\u6001"),
    UNBALANCED_DEBIT_AND_CREDIT(7, "\u51ed\u8bc1\u501f\u8d37\u4e0d\u5e73\u8861"),
    UNBALANCED_DEBIT_AND_CREDIT_MIDDLE(8, "\u51ed\u8bc1\u501f\u8d37\u4e0d\u5e73\u8861\u4e2d\u95f4\u6001");

    private Integer code;
    private String name;

    private VchrHandleStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

