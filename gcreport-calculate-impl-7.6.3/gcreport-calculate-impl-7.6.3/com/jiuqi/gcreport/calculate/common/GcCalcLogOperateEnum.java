/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.calculate.common;

public enum GcCalcLogOperateEnum {
    STATR_CALC("start_calc", "\u5f00\u59cb\u5408\u5e76\u8ba1\u7b97"),
    COMPLETE_CALC("complete_calc", "\u5b8c\u6210\u5408\u5e76"),
    RELTOMERGE_CALC("reltomerge_calc", "\u5173\u8054\u8f6c\u5408\u5e76");

    private String name;
    private String title;

    private GcCalcLogOperateEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

