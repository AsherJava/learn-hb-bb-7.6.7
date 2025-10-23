/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.task.form.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nr.period.util.JacksonUtils;

public enum FormCopyCheck {
    REPEATCOPY(0, "\u91cd\u590d\u590d\u5236"),
    REPEATCODE(1, "\u6807\u8bc6\u91cd\u590d"),
    NORMAL(2, "\u6b63\u5e38"),
    HASFMDM(3, "\u76ee\u6807\u62a5\u8868\u65b9\u6848\u5df2\u6709\u5c01\u9762\u4ee3\u7801\u8868"),
    COPIEDFMDM(4, "\u91cd\u590d\u590d\u5236");

    private final int key;
    private final String value;

    private FormCopyCheck(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    @JsonValue
    public String get() {
        return JacksonUtils.enumToJson((Enum)this);
    }

    @JsonCreator
    public FormCopyCheck set(String s) {
        return (FormCopyCheck)((Object)JacksonUtils.jsonToEnum((String)s, FormCopyCheck.class));
    }
}

