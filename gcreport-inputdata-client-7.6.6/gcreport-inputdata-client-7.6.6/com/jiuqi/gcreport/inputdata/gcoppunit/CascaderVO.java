/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.inputdata.gcoppunit;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

public class CascaderVO {
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private String key;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private String value;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private String label;
    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    private List<CascaderVO> children;

    public CascaderVO() {
    }

    public CascaderVO(String value, String label) {
        this(null, value, label);
    }

    public CascaderVO(String key, String value, String label) {
        this.setLabel(label);
        this.setValue(value);
        this.setKey(key);
    }

    public String getKey() {
        return this.key;
    }

    public CascaderVO setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public CascaderVO setValue(String value) {
        this.value = value;
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public CascaderVO setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<CascaderVO> getChildren() {
        return this.children;
    }

    public CascaderVO setChildren(List<CascaderVO> children) {
        this.children = children;
        return this;
    }

    public CascaderVO addChildren(CascaderVO vo) {
        if (this.children == null) {
            this.children = new ArrayList<CascaderVO>();
        }
        this.children.add(vo);
        return this;
    }
}

