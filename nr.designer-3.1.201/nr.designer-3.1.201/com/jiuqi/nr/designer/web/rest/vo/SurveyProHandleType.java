/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import java.util.HashMap;
import java.util.Map;

public enum SurveyProHandleType {
    ASS_ZB("ASS_ZB", "\u5173\u8054\u6307\u6807"),
    GEN_ZB("GEN_ZB", "\u751f\u6210\u6307\u6807"),
    GEN_ENUM("GEN_ENUM", "\u751f\u6210\u679a\u4e3e\u5e76\u5173\u8054\u5f53\u524d\u6307\u6807"),
    ASS_ENUM("ASS_ENUM", "\u6307\u6807\u5173\u8054\u679a\u4e3e"),
    ADD_ENUM("ADD_ENUM", "\u65b0\u589e\u679a\u4e3e\u6570\u636e");

    private String value;
    private String title;
    private static final Map<String, SurveyProHandleType> VALUE_MAP;

    private SurveyProHandleType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static SurveyProHandleType fromValue(String value) {
        SurveyProHandleType sex = VALUE_MAP.get(value);
        return sex;
    }

    static {
        VALUE_MAP = new HashMap<String, SurveyProHandleType>();
        for (int i = 0; i < SurveyProHandleType.values().length; ++i) {
            SurveyProHandleType sex = SurveyProHandleType.values()[i];
            VALUE_MAP.put(sex.value(), sex);
        }
    }
}

