/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.reportTag.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nr.period.util.JacksonUtils;

public enum ReportTagType {
    NONE(0, ""),
    FORMULA(1, "\u516c\u5f0f"),
    DIMENSION(3, "\u4e3b\u7ef4\u5ea6\u5c5e\u6027"),
    FORM(5, "\u62a5\u8868"),
    CHART(7, "\u56fe\u8868"),
    QUICK_REPORT(9, "\u5206\u6790\u8868");

    private final int key;
    private final String value;

    private ReportTagType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public static String getValueByKey(int key) {
        ReportTagType[] enums;
        for (ReportTagType e : enums = ReportTagType.values()) {
            if (e.getKey() != key) continue;
            return e.getValue();
        }
        return "";
    }

    public static int getKeyByValue(String value) {
        ReportTagType[] enums;
        for (ReportTagType e : enums = ReportTagType.values()) {
            if (!e.getValue().equals(value)) continue;
            return e.getKey();
        }
        return 0;
    }

    @JsonValue
    public String toValue() {
        return JacksonUtils.enumToJson((Enum)this);
    }

    @JsonCreator
    public ReportTagType toEnum(String content) {
        return (ReportTagType)((Object)JacksonUtils.jsonToEnum((String)content, ReportTagType.class));
    }
}

