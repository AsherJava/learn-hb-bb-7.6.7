/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.entity;

import java.time.Instant;

public class ZbCheckItemDO {
    private String key;
    private String checkKey;
    private String dataFieldKey;
    private String zbInfoKey;
    private String dataFieldPath;
    private int diffType;
    private int operType;
    private String formSchemeKey;
    private String formGroupKey;
    private String formKey;
    private Instant updateTime;
    private String order;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCheckKey() {
        return this.checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }

    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    public String getZbInfoKey() {
        return this.zbInfoKey;
    }

    public void setZbInfoKey(String zbInfoKey) {
        this.zbInfoKey = zbInfoKey;
    }

    public String getDataFieldPath() {
        return this.dataFieldPath;
    }

    public void setDataFieldPath(String dataFieldPath) {
        this.dataFieldPath = dataFieldPath;
    }

    public int getDiffType() {
        return this.diffType;
    }

    public void setDiffType(int diffType) {
        this.diffType = diffType;
    }

    public int getOperType() {
        return this.operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String toString() {
        return "ZbCheckItemDO{key='" + this.key + '\'' + ", checkKey='" + this.checkKey + '\'' + ", dataFieldKey='" + this.dataFieldKey + '\'' + ", zbInfoKey='" + this.zbInfoKey + '\'' + ", dataFieldPath='" + this.dataFieldPath + '\'' + ", diffType=" + this.diffType + ", operType=" + this.operType + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", formGroupKey='" + this.formGroupKey + '\'' + ", formKey='" + this.formKey + '\'' + ", updateTime=" + this.updateTime + '}';
    }
}

