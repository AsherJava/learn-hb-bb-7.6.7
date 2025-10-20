/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum ColumnEnum {
    ROWTYPE("ROWTYPE", "\u884c\u7c7b\u578b"),
    VCHRSRCTYPE("VCHRSRCTYPE", "\u5ba1\u8ba1\u7ebf\u7d22"),
    UNITCODE("UNITCODE", "\u7ec4\u7ec7\u673a\u6784"),
    ACCTYEAR("ACCTYEAR", "\u5e74"),
    ACCTPERIOD("ACCTPERIOD", "\u6708"),
    ACCTDAY("ACCTDAY", "\u65e5"),
    ACCTORGCODE("ACCTORGCODE", "\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801"),
    ACCTORGNAME("ACCTORGNAME", "\u7ec4\u7ec7\u673a\u6784\u540d\u79f0"),
    ASSISTCODE("ASSISTCODE", "\u8f85\u52a9\u6838\u7b97\u4ee3\u7801"),
    ASSISTNAME("ASSISTNAME", "\u8f85\u52a9\u6838\u7b97\u540d\u79f0"),
    SUBJECTCODE("SUBJECTCODE", "\u79d1\u76ee\u4ee3\u7801"),
    SUBJECTNAME("SUBJECTNAME", "\u79d1\u76ee\u540d\u79f0"),
    CURRENCYCODE("CURRENCYCODE", "\u5e01\u522b\u4ee3\u7801"),
    CURRENCYNAME("CURRENCYNAME", "\u5e01\u522b\u540d\u79f0"),
    ORIENT("ORIENT", "\u65b9\u5411"),
    AMNT("AMNT", "\u672c\u5e01"),
    ORGN("ORGN", "\u539f\u5e01"),
    NC("NC", "\u5e74\u521d"),
    NCORIENT("NCORIENT", "\u5e74\u521d\u65b9\u5411"),
    ORGNNC("ORGNNC", "\u539f\u5e01\u5e74\u521d"),
    QC("QC", "\u671f\u521d"),
    QCORIENT("QCORIENT", "\u671f\u521d\u65b9\u5411"),
    ORGNQC("ORGNQC", "\u539f\u5e01\u671f\u521d"),
    BQ("BQ", "\u672c\u671f"),
    DEBIT("DEBIT", "\u501f\u65b9\u672c\u5e01"),
    CREDIT("CREDIT", "\u8d37\u65b9\u672c\u5e01"),
    ORGND("ORGND", "\u501f\u65b9\u539f\u5e01"),
    ORGNC("ORGNC", "\u8d37\u65b9\u539f\u5e01"),
    CASHCODE("CASHCODE", "\u73b0\u6d41\u4ee3\u7801"),
    CASHNAME("CASHNAME", "\u73b0\u6d41\u540d\u79f0"),
    LJ("LJ", "\u7d2f\u8ba1"),
    DSUM("DSUM", "\u501f\u65b9\u7d2f\u8ba1"),
    CSUM("CSUM", "\u8d37\u65b9\u7d2f\u8ba1"),
    ORGNDSUM("ORGNDSUM", "\u501f\u65b9\u539f\u5e01"),
    ORGNCSUM("ORGNCSUM", "\u8d37\u65b9\u539f\u5e01"),
    YE("YE", "\u4f59\u989d"),
    DIFFAMT("DIFFAMT", "\u6298\u7b97\u5dee"),
    YEORIENT("YE_ORIENT", "\u4f59\u989d\u65b9\u5411"),
    ORGNYE("ORGNYE", "\u539f\u5e01\u4f59\u989d"),
    BQNUM("BQNUM", "\u671f\u95f4\u6570"),
    LJNUM("LJNUM", "\u7d2f\u8ba1\u6570"),
    WBQNUM("WBQNUM", "\u5916\u5e01\u671f\u95f4\u6570"),
    WLJNUM("WLJNUM", "\u5916\u5e01\u7d2f\u8ba1\u6570"),
    JNC("JNC", "\u672c\u4f4d\u5e01\u501f\u65b9\u5e74\u521d\u4f59\u989d\u5408\u8ba1"),
    DNC("DNC", "\u672c\u4f4d\u5e01\u8d37\u65b9\u5e74\u521d\u4f59\u989d\u5408\u8ba1"),
    JYH("JYH", "\u672c\u4f4d\u5e01\u501f\u65b9\u4f59\u989d\u5408\u8ba1"),
    DYH("DYH", "\u672c\u4f4d\u5e01\u8d37\u65b9\u4f59\u989d\u5408\u8ba1"),
    WJNC("WJNC", "\u5916\u5e01\u501f\u65b9\u5e74\u521d\u4f59\u989d\u5408\u8ba1"),
    WDNC("WDNC", "\u5916\u5e01\u8d37\u65b9\u5e74\u521d\u4f59\u989d\u5408\u8ba1"),
    WJYH("WJYH", "\u5916\u5e01\u501f\u65b9\u4f59\u989d\u5408\u8ba1"),
    WDYH("WDYH", "\u5916\u5e01\u8d37\u65b9\u4f59\u989d\u5408\u8ba1"),
    VCHRTYPE("VCHRTYPE", "\u51ed\u8bc1\u5b57"),
    VCHRID("VCHRID", "\u51ed\u8bc1ID"),
    DIGEST("DIGEST", "\u6458\u8981"),
    ASSISTCOMB("ASSISTCOMB", "\u8f85\u52a9\u9879"),
    REPCURRCODE("REPCURRCODE", "\u62a5\u8868\u5e01\u79cd");

    private final String code;
    private final String title;

    private ColumnEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static ColumnEnum getEnumByCode(String code) {
        for (ColumnEnum columnEnum : ColumnEnum.values()) {
            if (!columnEnum.getCode().equals(code)) continue;
            return columnEnum;
        }
        return null;
    }

    public static ColumnEnum getEnumByName(String name) {
        for (ColumnEnum columnEnum : ColumnEnum.values()) {
            if (!columnEnum.getTitle().equals(name)) continue;
            return columnEnum;
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

