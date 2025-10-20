/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.unionrule.vo;

import java.util.List;

public class SelectFloatLineOptionTreeVO {
    private Object value;
    private String label;
    private String code;
    private List<SelectFloatLineOptionTreeVO> children;

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

    public List<SelectFloatLineOptionTreeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<SelectFloatLineOptionTreeVO> children) {
        this.children = children;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString() {
        return "SelectFloatLineOptionTreeVO{value=" + this.value + ", label='" + this.label + '\'' + ", code='" + this.code + '\'' + ", children=" + this.children + '}';
    }
}

