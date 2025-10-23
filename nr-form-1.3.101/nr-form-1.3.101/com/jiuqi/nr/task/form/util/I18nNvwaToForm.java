/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.util;

public enum I18nNvwaToForm {
    CN("cn", 1),
    EN("en", 2);

    private final int value;
    private final String code;
    private static I18nNvwaToForm[] TYPES;

    private I18nNvwaToForm(String code, int value) {
        this.value = value;
        this.code = code;
    }

    public int getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }

    public static I18nNvwaToForm fromValue(int value) {
        for (I18nNvwaToForm i18nNvwaToForm : TYPES) {
            if (i18nNvwaToForm.getValue() != value) continue;
            return i18nNvwaToForm;
        }
        return TYPES[0];
    }

    public static I18nNvwaToForm fromCode(String code) {
        for (I18nNvwaToForm i18nNvwaToForm : TYPES) {
            if (!i18nNvwaToForm.getCode().equals(code)) continue;
            return i18nNvwaToForm;
        }
        return TYPES[0];
    }

    static {
        TYPES = new I18nNvwaToForm[]{CN, EN};
    }
}

