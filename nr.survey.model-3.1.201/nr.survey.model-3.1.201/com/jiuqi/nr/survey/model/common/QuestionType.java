/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.common;

import java.util.HashMap;
import java.util.Map;

public enum QuestionType {
    BLANK("blank", "\u586b\u7a7a"),
    NUMBER("number", "\u6570\u503c"),
    PERIOD("period", "\u65f6\u671f"),
    TEXT("text", "\u6587\u672c"),
    COMMENT("comment", "\u591a\u884c\u6587\u672c"),
    BOOLEAN("boolean", "\u5e03\u5c14"),
    RADIOGROUP("radiogroup", "\u5355\u9009"),
    TAGBOX("tagbox", "\u591a\u9009\u4e0b\u62c9"),
    CHECKBOX("checkbox", "\u591a\u9009"),
    DROPDOWN("dropdown", "\u4e0b\u62c9"),
    PICTURE("picture", "\u56fe\u7247"),
    FILE("file", "\u6587\u4ef6"),
    FILEPOOL("filepool", "\u6587\u4ef6\u6c60"),
    MATRIXDYNAMIC("matrixdynamic", "\u52a8\u6001\u77e9\u9635"),
    MATRIXFLOAT("matrixfloat", "\u4ea4\u53c9\u77e9\u9635\uff08\u6d6e\u52a8\u884c\uff09"),
    PANEL("panel", "\u9762\u677f"),
    PANELDYNAMIC("paneldynamic", "\u52a8\u6001\u9762\u677f"),
    MULTIPLETEXT("multipletext", "\u591a\u884c\u6587\u672c"),
    RATING("rating", "\u8bc4\u5206"),
    EXPRESSION("expression", "\u8868\u8fbe\u5f0f"),
    IMAGE("image", "\u56fe\u7247");

    private String value;
    private String title;
    private static final Map<String, QuestionType> VALUE_MAP;

    private QuestionType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static QuestionType fromValue(String value) {
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = new HashMap<String, QuestionType>();
        for (int i = 0; i < QuestionType.values().length; ++i) {
            QuestionType sex = QuestionType.values()[i];
            VALUE_MAP.put(sex.value(), sex);
        }
    }
}

