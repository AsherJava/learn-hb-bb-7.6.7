/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check2.zbScheme;

public class ZbSchemeReverseParam {
    private String key;
    private String dataSchemeKey;
    private String zbSchemeKey;
    private String zbSchemeVersionKey;

    public ZbSchemeReverseParam() {
    }

    public ZbSchemeReverseParam(String key, ZbSchemeReverseParam param) {
        this.key = key;
        this.dataSchemeKey = param.getDataSchemeKey();
        this.zbSchemeKey = param.getZbSchemeKey();
        this.zbSchemeVersionKey = param.getZbSchemeVersionKey();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getZbSchemeKey() {
        return this.zbSchemeKey;
    }

    public void setZbSchemeKey(String zbSchemeKey) {
        this.zbSchemeKey = zbSchemeKey;
    }

    public String getZbSchemeVersionKey() {
        return this.zbSchemeVersionKey;
    }

    public void setZbSchemeVersionKey(String zbSchemeVersionKey) {
        this.zbSchemeVersionKey = zbSchemeVersionKey;
    }
}

