/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

public class TransZbValue {
    private String zbCode;
    private int idx;
    private int zbType;
    private Object value;

    public TransZbValue() {
    }

    public TransZbValue(String zbCode, int idx, int zbType, Object value) {
        this.zbCode = zbCode;
        this.idx = idx;
        this.zbType = zbType;
        this.value = value;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public int getIdx() {
        return this.idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getZbType() {
        return this.zbType;
    }

    public void setZbType(int zbType) {
        this.zbType = zbType;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

