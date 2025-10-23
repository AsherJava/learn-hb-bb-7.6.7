/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.bean;

public class ZBMapping {
    private String key;
    private String msKey;
    private String form;
    private String table;
    private String regionCode;
    private String zbCode;
    private String mapping;

    public ZBMapping() {
    }

    public ZBMapping(String key, String msKey, String form, String table, String regionCode, String zbCode, String mapping) {
        this.key = key;
        this.msKey = msKey;
        this.form = form;
        this.table = table;
        this.regionCode = regionCode;
        this.zbCode = zbCode;
        this.mapping = mapping;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public String getForm() {
        return this.form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
}

