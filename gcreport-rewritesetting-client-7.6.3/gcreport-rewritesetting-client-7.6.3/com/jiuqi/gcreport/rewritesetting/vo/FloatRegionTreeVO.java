/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rewritesetting.vo;

import java.util.List;

public class FloatRegionTreeVO {
    private Object value;
    private String label;
    private String formKey;
    private String tableName;
    private boolean relateZb = false;
    private List<FloatRegionTreeVO> children;

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<FloatRegionTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<FloatRegionTreeVO> children) {
        this.children = children;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isRelateZb() {
        return this.relateZb;
    }

    public void setRelateZb(boolean relateZb) {
        this.relateZb = relateZb;
    }

    public String toString() {
        return "FloatRegionTreeVO{value=" + this.value + ", label='" + this.label + '\'' + ", formKey='" + this.formKey + '\'' + ", tableName='" + this.tableName + '\'' + ", children=" + this.children + '}';
    }
}

