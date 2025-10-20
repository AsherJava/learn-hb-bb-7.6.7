/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum SameCtrlExtractTypeEnum {
    VIRTUAL("VIRTUAL", "\u540c\u63a7\u865a\u62df\u8868\u63d0\u53d6"),
    DISPOSER("DISPOSER", "\u5904\u7f6e\u65b9\u540c\u63a7\u62b5\u9500"),
    DISPOSER_PARENT("DISPOSER_PARENT", "\u5904\u7f6e\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500"),
    ACQUIRER("ACQUIRER", "\u6536\u8d2d\u65b9\u540c\u63a7\u62b5\u9500"),
    ACQUIRER_PARENT("ACQUIRER_PARENT", "\u6536\u8d2d\u65b9\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500"),
    COMMON_UNIT_GO_BACK("COMMON_UNIT_GO_BACK", "\u5171\u540c\u4e0a\u7ea7\u540c\u63a7\u8868\u51b2\u56de"),
    COMMON_UNIT_OFFSET_GO_BACK("COMMON_UNIT_OFFSET_GO_BACK", "\u5171\u540c\u4e0a\u7ea7\u540c\u63a7\u62b5\u9500\u51b2\u56de");

    private String code;
    private String name;

    private SameCtrlExtractTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getExtractTypeNameByCode(String code) {
        for (SameCtrlExtractTypeEnum extractTypeEnum : SameCtrlExtractTypeEnum.values()) {
            if (extractTypeEnum.getCode() != code) continue;
            return extractTypeEnum.getName();
        }
        return "";
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

