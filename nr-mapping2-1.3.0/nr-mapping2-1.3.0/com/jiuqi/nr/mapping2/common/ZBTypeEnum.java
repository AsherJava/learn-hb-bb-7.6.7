/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.common;

public enum ZBTypeEnum {
    ZB_NORMAL("01", "\u975e\u6269\u5c55\u533a\u57df\u7684\u6307\u6807\uff0c\u5c55\u5f00\u548c\u6536\u8d77\u90fd\u663e\u793a\u548c\u4fdd\u5b58\u6620\u5c04"),
    ZB_FOLDED_EXTEND("02", "\u6536\u8d77\u72b6\u6001\u4e0b\u7684\u6269\u5c55\u533a\u57df\u6240\u6709\u6307\u6807\uff0c\u6536\u8d77\u65f6\u663e\u793a\u548c\u4fdd\u5b58\u6620\u5c04"),
    ZB_EXTENDED_EXTEND_ENUM("03", "\u5c55\u5f00\u72b6\u6001\u4e0b\u7684\u6269\u5c55\u533a\u57df\u7684\u679a\u4e3e\u6307\u6807\uff0c\u5c55\u5f00\u65f6\u663e\u793a"),
    ZB_EXTENDED_EXTEND_OTHER("04", "\u5c55\u5f00\u72b6\u6001\u4e0b\u7684\u6269\u5c55\u533a\u57df\u7684\u975e\u679a\u4e3e\u6307\u6807 \uff0c\u5c55\u5f00\u65f6\u663e\u793a\u548c\u4fdd\u5b58\u6620\u5c04");

    private String code;
    private String title;

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    private ZBTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

