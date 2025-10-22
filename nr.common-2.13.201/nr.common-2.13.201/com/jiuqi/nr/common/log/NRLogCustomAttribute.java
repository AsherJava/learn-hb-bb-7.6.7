/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.log;

import org.springframework.util.Assert;

public class NRLogCustomAttribute {
    private final String title;
    private final String code;

    public NRLogCustomAttribute(String code, String title) {
        Assert.hasText(code, () -> "code must not be null");
        Assert.hasText(title, () -> "title must not be null");
        this.code = code;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }

    public String toString() {
        return this.title + '(' + this.code + ')';
    }
}

