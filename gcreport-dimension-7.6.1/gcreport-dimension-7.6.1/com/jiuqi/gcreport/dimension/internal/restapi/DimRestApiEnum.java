/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.restapi;

public enum DimRestApiEnum {
    BASE("gc:dimension:base", "\u7ba1\u7406");

    private String code;
    private String title;

    private DimRestApiEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

