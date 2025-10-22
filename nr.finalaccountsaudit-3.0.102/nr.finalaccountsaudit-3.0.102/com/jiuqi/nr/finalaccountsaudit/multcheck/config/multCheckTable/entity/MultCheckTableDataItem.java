/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity;

import org.apache.poi.ss.formula.functions.T;

public class MultCheckTableDataItem {
    private int index;
    private String key;
    private String multCheckKey;
    private String multCheckType;
    private String multCheckName;
    private T auditScope;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMultCheckKey() {
        return this.multCheckKey;
    }

    public void setMultCheckKey(String multCheckKey) {
        this.multCheckKey = multCheckKey;
    }

    public String getMultCheckType() {
        return this.multCheckType;
    }

    public void setMultCheckType(String multCheckType) {
        this.multCheckType = multCheckType;
    }

    public String getMultCheckName() {
        return this.multCheckName;
    }

    public void setMultCheckName(String multCheckName) {
        this.multCheckName = multCheckName;
    }

    public void setAuditScope(T auditScope) {
        this.auditScope = auditScope;
    }

    public T getAuditScope() {
        return this.auditScope;
    }

    public void setAuditScope(Object auditScope) {
        this.auditScope = (T)auditScope;
    }
}

