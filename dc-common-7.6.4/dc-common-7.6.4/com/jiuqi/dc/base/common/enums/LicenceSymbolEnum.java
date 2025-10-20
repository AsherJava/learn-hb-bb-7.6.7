/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum LicenceSymbolEnum {
    SAP("SAP", "SAP\u7cfb\u5217"),
    YONYOU("YONYOU", "\u7528\u53cb\u6838\u7b97\u7cfb\u5217"),
    KINGDEE("KINGDEE", "\u91d1\u8776\u6838\u7b97\u7cfb\u5217"),
    GS("GS", "\u6d6a\u6f6e\u6838\u7b97\u7cfb\u5217"),
    EBS("EBS", "ORACLE\u7cfb\u5217"),
    STANDARD("STANDARD", "\u6807\u51c6\u4e2d\u95f4\u5e93"),
    JIUQI("JIUQI", "\u4e45\u5176\u4e91\u6838\u7b97\u3001VA\u6838\u7b97\u7b49\u7cfb\u5217"),
    NBRJ("NBRJ", "\u5357\u5317\u8f6f\u4ef6\u7cfb\u5217"),
    PROJECT("PROJECT", "\u5176\u4ed6\u9879\u76ee\u5316");

    private final String symbol;
    private final String title;

    private LicenceSymbolEnum(String symbol, String title) {
        this.symbol = symbol;
        this.title = title;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getTitle() {
        return this.title;
    }
}

