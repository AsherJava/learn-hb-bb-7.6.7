/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.common;

import java.util.HashMap;
import java.util.Map;

public enum QuestionCellType {
    DROPDOWN("dropdown", "\u4e0b\u62c9"),
    CHECKBOX("checkbox", "\u591a\u9009"),
    RADIOGROUP("radiogroup", "\u5355\u9009"),
    TAGBOX("tagbox", "\u591a\u9009\u4e0b\u62c9"),
    TEXT("text", "\u6587\u672c"),
    COMMENT("comment", "\u591a\u884c\u6587\u672c"),
    BOOLEAN("boolean", "\u5e03\u5c14"),
    EXPRESSION("expression", "\u8868\u8fbe\u5f0f"),
    RATING("rating", "\u8bc4\u5206"),
    NUMBER("number", "\u6570\u503c"),
    PERIOD("period", "\u65f6\u671f"),
    FILE("file", "\u6587\u4ef6"),
    FILEPOOL("filepool", "\u9644\u4ef6\u6c60");

    private String value;
    private String title;
    private static final Map<String, QuestionCellType> VALUE_MAP;

    private QuestionCellType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return this.value;
    }

    public String title() {
        return this.title;
    }

    public static QuestionCellType fromValue(String value) {
        return VALUE_MAP.get(value);
    }

    static {
        VALUE_MAP = new HashMap<String, QuestionCellType>();
        for (int i = 0; i < QuestionCellType.values().length; ++i) {
            QuestionCellType sex = QuestionCellType.values()[i];
            VALUE_MAP.put(sex.value(), sex);
        }
    }
}

