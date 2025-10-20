/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum FetchTypeEnum {
    UNKNOWN("UNKNOWN", "\u672a\u77e5"),
    NC("NC", "\u5e74\u521d\u4f59\u989d"),
    C("C", "\u671f\u521d\u4f59\u989d"),
    CJ("CJ", "\u671f\u521d\u501f\u65b9\u7d2f\u8ba1"),
    CD("CD", "\u671f\u521d\u8d37\u65b9\u7d2f\u8ba1"),
    JL("JL", "\u501f\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d"),
    DL("DL", "\u8d37\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d"),
    JF("JF", "\u501f\u65b9\u53d1\u751f\u989d"),
    DF("DF", "\u8d37\u65b9\u53d1\u751f\u989d"),
    YE("YE", "\u671f\u672b\u4f59\u989d"),
    ZSC("ZSC", "\u6298\u7b97\u5dee"),
    WNC("WNC", "\u5916\u5e01\u5e74\u521d\u4f59\u989d"),
    WC("WC", "\u5916\u5e01\u671f\u521d\u4f59\u989d"),
    WJC("WJC", "\u5916\u5e01\u671f\u521d\u501f\u65b9\u4f59\u989d"),
    WDC("WDC", "\u5916\u5e01\u671f\u521d\u8d37\u65b9\u4f59\u989d"),
    WJL("WJL", "\u5916\u5e01\u501f\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d"),
    WDL("WDL", "\u5916\u5e01\u8d37\u65b9\u7d2f\u8ba1\u53d1\u751f\u989d"),
    WJF("WJF", "\u5916\u5e01\u501f\u65b9\u53d1\u751f\u989d"),
    WDF("WDF", "\u5916\u5e01\u8d37\u65b9\u53d1\u751f\u989d"),
    WYE("WYE", "\u5916\u5e01\u671f\u672b\u4f59\u989d"),
    LFS("LFS", "\u672c\u5e74\u7d2f\u8ba1\u53d1\u751f\u989d"),
    KM("KM", "\u79d1\u76ee"),
    HXYE("HXYE", "\u672c\u5e01\u6838\u9500\u4f59\u989d"),
    HXZSC("HXZSC", "\u672c\u5e01\u6298\u7b97\u5dee"),
    WHXYE("WHXYE", "\u539f\u5e01\u6838\u9500\u4f59\u989d"),
    HXNC("HXNC", "\u672c\u5e01\u6838\u9500\u5e74\u521d\u4f59\u989d"),
    WHXNC("WHXNC", "\u539f\u5e01\u6838\u9500\u5e74\u521d\u4f59\u989d"),
    JNC("JNC", "\u501f\u65b9\u5e74\u521d\u5408\u8ba1"),
    DNC("DNC", "\u8d37\u65b9\u5e74\u521d\u5408\u8ba1"),
    JYH("JYH", "\u501f\u65b9\u4f59\u989d\u5408\u8ba1"),
    DYH("DYH", "\u8d37\u65b9\u4f59\u989d\u5408\u8ba1"),
    WJNC("WJNC", "\u5916\u5e01\u501f\u65b9\u5e74\u521d\u5408\u8ba1"),
    WDNC("WDNC", "\u5916\u5e01\u8d37\u65b9\u5e74\u521d\u5408\u8ba1"),
    WJYH("WJYH", "\u5916\u5e01\u501f\u65b9\u4f59\u989d\u5408\u8ba1"),
    WDYH("WDYH", "\u5916\u5e01\u8d37\u65b9\u4f59\u989d\u5408\u8ba1"),
    DXYE("DXYE", "\u62b5\u9500\u4f59\u989d"),
    DXNC("DXNC", "\u62b5\u9500\u5e74\u521d"),
    DXJNC("DXJNC", "\u62b5\u9500\u501f\u65b9\u5e74\u521d"),
    DXDNC("DXDNC", "\u62b5\u9500\u8d37\u65b9\u5e74\u521d"),
    DXJYH("DXJYH", "\u62b5\u9500\u501f\u65b9\u4f59\u989d\u5408\u8ba1"),
    DXDYH("DXDYH", "\u62b5\u9500\u8d37\u65b9\u4f59\u989d\u5408\u8ba1"),
    BQNUM("BQNUM", "\u672c\u671f\u53d1\u751f\u989d"),
    LJNUM("LJNUM", "\u7d2f\u8ba1\u53d1\u751f\u989d"),
    WBQNUM("WBQNUM", "\u5916\u5e01\u672c\u671f\u53d1\u751f\u989d"),
    WLJNUM("WLJNUM", "\u5916\u5e01\u7d2f\u8ba1\u53d1\u751f\u989d"),
    VCHRUNIQUECODE("VCHRUNIQUECODE", "\u51ed\u8bc1\u552f\u4e00\u6807\u8bc6"),
    VCHRDATE("VCHRDATE", "\u51ed\u8bc1\u65e5\u671f"),
    CHANGESCENARIO("CHANGESCENARIO", "\u53d8\u52a8\u573a\u666f"),
    VCHRTYPECODE("VCHRTYPECODE", "\u51ed\u8bc1\u7c7b\u522b"),
    VCHRNUM("VCHRNUM", "\u51ed\u8bc1\u53f7"),
    INVESTEDUNIT("INVESTEDUNIT", "\u88ab\u6295\u8d44\u5355\u4f4d");

    private String code;
    private String name;

    private FetchTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FetchTypeEnum fromName(String name) {
        for (FetchTypeEnum type : FetchTypeEnum.values()) {
            if (!type.name().equals(name)) continue;
            return type;
        }
        return UNKNOWN;
    }

    public static FetchTypeEnum getEnumByCode(String code) {
        for (FetchTypeEnum fetchTypeEnum : FetchTypeEnum.values()) {
            if (!fetchTypeEnum.getCode().equals(code)) continue;
            return fetchTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684FetchTypeEnum\u679a\u4e3e code=" + code);
    }

    public static FetchTypeEnum getEnumByName(String name) {
        for (FetchTypeEnum fetchTypeEnum : FetchTypeEnum.values()) {
            if (!fetchTypeEnum.getName().equals(name)) continue;
            return fetchTypeEnum;
        }
        return null;
    }
}

