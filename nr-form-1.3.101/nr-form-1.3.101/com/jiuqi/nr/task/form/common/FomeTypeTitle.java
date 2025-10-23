/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 */
package com.jiuqi.nr.task.form.common;

import com.jiuqi.nr.definition.common.FormType;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FomeTypeTitle {
    FORM_TYPE_FIX(FormType.FORM_TYPE_FIX.getValue(), "\u56fa\u5b9a\u8868"),
    FORM_TYPE_FLOAT(FormType.FORM_TYPE_FLOAT.getValue(), "\u6d6e\u52a8\u8868"),
    FORM_TYPE_SURVEY(FormType.FORM_TYPE_SURVEY.getValue(), "\u95ee\u5377"),
    FORM_TYPE_NEWFMDM(FormType.FORM_TYPE_NEWFMDM.getValue(), "\u5c01\u9762\u4ee3\u7801"),
    FORM_TYPE_ACCOUNT(FormType.FORM_TYPE_ACCOUNT.getValue(), "\u53f0\u8d26"),
    FORM_TYPE_INSERTANALYSIS(FormType.FORM_TYPE_INSERTANALYSIS.getValue(), "\u5206\u6790\u8868");

    private int value;
    private String title;
    private static Map<Integer, String> mappings;

    private FomeTypeTitle(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    private static Map<Integer, String> getMappings() {
        if (mappings == null) {
            mappings = Arrays.stream(FomeTypeTitle.values()).collect(Collectors.toMap(FomeTypeTitle::getValue, FomeTypeTitle::getTitle));
        }
        return mappings;
    }

    public static String forTitle(int value) {
        return FomeTypeTitle.getMappings().get(value);
    }
}

