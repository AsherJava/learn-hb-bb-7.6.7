/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

public class DimInfoParam {
    private String code;
    private String title;
    private String id;

    public DimInfoParam() {
    }

    public DimInfoParam(String id, String code, String title) {
        this.code = code;
        this.title = title;
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

