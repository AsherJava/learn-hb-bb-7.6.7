/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jiuqi.nr.period.util.JacksonUtils;

public enum FormCopyOptions {
    PASS(0, "\u8df3\u8fc7"),
    UPDATE(1, "\u66f4\u65b0"),
    NEWCOPY(2, "\u65b0\u589e\u590d\u5236");

    private final int key;
    private final String value;

    private FormCopyOptions(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static FormCopyOptions toEnum(String value) {
        return (FormCopyOptions)((Object)JacksonUtils.jsonToEnum((String)value, FormCopyOptions.class));
    }

    @JsonValue
    public String toValue() {
        return JacksonUtils.enumToJson((Enum)this);
    }
}

