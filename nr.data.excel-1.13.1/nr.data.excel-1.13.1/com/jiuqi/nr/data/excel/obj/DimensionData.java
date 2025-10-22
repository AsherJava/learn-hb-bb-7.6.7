/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

public class DimensionData {
    private String keyData;
    private String code;
    private String title;

    public DimensionData() {
    }

    public DimensionData(String keyData, String code, String title) {
        this.keyData = keyData;
        this.code = code;
        this.title = title;
    }

    public String getKeyData() {
        return this.keyData;
    }

    public void setKeyData(String keyData) {
        this.keyData = keyData;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

