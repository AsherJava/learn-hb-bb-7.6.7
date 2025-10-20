/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.enums;

public enum DetailTableColumnEnum {
    SRCTYPE("SRCTYPE", "\u5ba1\u8ba1\u7ebf\u7d22"),
    VCHRID("VCHRID", "\u51ed\u8bc1ID"),
    UNITCODE("UNITCODE", "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801"),
    UNITNAME("UNITNAME", "\u7ec4\u7ec7\u673a\u6784\u540d\u79f0"),
    ACCTYEAR("ACCTYEAR", "\u5e74"),
    ACCTMONTH("ACCTMONTH", "\u6708"),
    ACCTDAY("ACCTDAY", "\u65e5"),
    VOUCHERWORD("VOUCHERWORD", "\u51ed\u8bc1\u5b57\u53f7"),
    SUBJECTCODE("SUBJECTCODE", "\u79d1\u76ee\u4ee3\u7801"),
    SUBJECTNAME("SUBJECTNAME", "\u79d1\u76ee\u540d\u79f0"),
    CFITEMCODE("CFITEMCODE", "\u73b0\u6d41\u9879\u76ee\u4ee3\u7801"),
    CFITEMNAME("CFITEMNAME", "\u73b0\u6d41\u9879\u76ee\u540d\u79f0"),
    DIGEST("DIGEST", "\u6458\u8981"),
    CURRENCYCODE("CURRENCYCODE", "\u5e01\u79cd"),
    JF("JF", "\u672c\u5e01\u501f\u65b9"),
    WJF("WJF", "\u539f\u5e01\u501f\u65b9"),
    DF("DF", "\u672c\u5e01\u8d37\u65b9"),
    WDF("WDF", "\u539f\u5e01\u8d37\u65b9"),
    YEORIENT("YEORIENT", "\u65b9\u5411"),
    YE("YE", "\u671f\u672b\u672c\u5e01\u4f59\u989d"),
    WYE("WYE", "\u671f\u672b\u539f\u5e01\u4f59\u989d");

    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private DetailTableColumnEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DetailTableColumnEnum getPreDataByCode(String code) {
        for (DetailTableColumnEnum detailTableColumnEnum : DetailTableColumnEnum.values()) {
            if (!detailTableColumnEnum.getCode().equals(code)) continue;
            return detailTableColumnEnum;
        }
        return null;
    }

    public static DetailTableColumnEnum getPreDataByName(String name) {
        for (DetailTableColumnEnum detailTableColumnEnum : DetailTableColumnEnum.values()) {
            if (!detailTableColumnEnum.getName().equals(name)) continue;
            return detailTableColumnEnum;
        }
        return null;
    }
}

