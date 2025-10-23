/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.common;

import java.util.HashMap;
import java.util.Map;

public enum I18nResourceType {
    TASK_TITLE(0, "\u4efb\u52a1\u6807\u9898"),
    FORM_SCHEME_TITLE(1, "\u62a5\u8868\u65b9\u6848\u6807\u9898"),
    FORMULA_SCHEME_TITLE(2, "\u516c\u5f0f\u65b9\u6848\u6807\u9898"),
    FORM(4, "\u8868\u5355"),
    FORM_TITLE(401, "\u8868\u5355\u6807\u9898\u591a\u8bed\u8a00(\u5bfc\u5165\u5bfc\u51fa)"),
    FORM_STYLE(402, "\u8868\u6837\u591a\u8bed\u8a00(\u5bfc\u5165\u5bfc\u51fa)"),
    FORM_GROUP_TITLE(5, "\u8868\u5355\u5206\u7ec4\u6807\u9898"),
    FORMULA_DESC(7, "\u516c\u5f0f\u8bf4\u660e"),
    FLOAT_REGION_TAB_TITLE(9, "\u6d6e\u52a8\u533a\u57df\u9875\u7b7e"),
    TASK_ORG_ALIAS(10, "\u5355\u4f4d\u53e3\u5f84\u522b\u540d");

    private final int value;
    private final String title;
    private static final Map<Integer, I18nResourceType> valueMap;
    private static final Map<String, I18nResourceType> titleMap;

    public static I18nResourceType valueOf(int value) {
        return valueMap.get(value);
    }

    public static I18nResourceType titleOf(String title) {
        return titleMap.get(title);
    }

    private I18nResourceType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    static {
        valueMap = new HashMap<Integer, I18nResourceType>();
        titleMap = new HashMap<String, I18nResourceType>();
        for (I18nResourceType type : I18nResourceType.values()) {
            valueMap.put(type.getValue(), type);
            titleMap.put(type.toString(), type);
        }
    }
}

