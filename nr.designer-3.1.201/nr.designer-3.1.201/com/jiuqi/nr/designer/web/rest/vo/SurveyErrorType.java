/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import java.util.HashMap;
import java.util.Map;

public enum SurveyErrorType {
    MISS_ZB("MISS_ZB", "\u7f3a\u5c11\u6307\u6807"),
    MIS_MATCH("MIS_MATCH", "\u95ee\u9898\u7c7b\u578b\u548c\u6307\u6807\u4e0d\u5339\u914d"),
    NO_ENUM("NO_ENUM", "\u5f53\u524d\u6307\u6807\u6ca1\u6709\u5173\u8054\u679a\u4e3e"),
    ADD_ENUM("ADD_ENUM", "\u624b\u52a8\u589e\u52a0\u4e86\u679a\u4e3e"),
    DECIMAL_RANGE("DECIMAL_RANGE", "\u5c0f\u6570\u4f4d\u8d85\u51fa\u6307\u6807\u8303\u56f4"),
    LENGTH_RANGE("LENGTH_RANGE", "\u957f\u5ea6\u8d85\u51fa\u6307\u6807\u8303\u56f4"),
    DELETE_ZB("DELETE_ZB", "\u5173\u8054\u7684\u6307\u6807\u5df2\u7ecf\u4e0d\u5b58\u5728");

    private String value;
    private String title;
    private static final Map<String, SurveyErrorType> VALUE_MAP;

    private SurveyErrorType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SurveyErrorType fromValue(String value) {
        SurveyErrorType sex = VALUE_MAP.get(value);
        return sex;
    }

    static {
        VALUE_MAP = new HashMap<String, SurveyErrorType>();
        for (int i = 0; i < SurveyErrorType.values().length; ++i) {
            SurveyErrorType sex = SurveyErrorType.values()[i];
            VALUE_MAP.put(sex.value(), sex);
        }
    }
}

