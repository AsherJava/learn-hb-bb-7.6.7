/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.assistdim.enums;

public enum AssistDimEffectTableEnum {
    DC_PREASSBALANCE("\u5168\u7ef4\u5ea6\u8f85\u52a9\u4f59\u989d\u8868"),
    DC_CFBALANCE("\u73b0\u91d1\u6d41\u91cf\u4f59\u989d\u8868"),
    DC_CFVOUCHERITEMASS("\u73b0\u91d1\u6d41\u91cf\u5206\u5f55\u8868"),
    DC_VOUCHERITEMASS("\u51ed\u8bc1\u5206\u5f55\u8868"),
    DC_ADJUSTVCHRITEM("\u8c03\u6574\u51ed\u8bc1\u5206\u5f55\u8868"),
    DC_AGINGBALANCE("\u8d26\u9f84\u4f59\u989d\u8868"),
    DC_DUEAGINGBALANCE("\u5230\u671f\u6216\u903e\u671f\u8d26\u9f84\u4f59\u989d\u8868"),
    DC_INIT_AGINGBALANCE("\u8d26\u9f84\u521d\u59cb\u4f59\u989d\u8868"),
    DC_UNCLEAREDITEM("\u8d26\u9f84\u672a\u6e05\u9879\u6807\u51c6\u8868"),
    DC_BILL_AGEUNCLEAREDITEM("\u5355\u636e\u8d26\u9f84\u672a\u6e05\u9879\u5b50\u8868");

    private String title;

    private AssistDimEffectTableEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

