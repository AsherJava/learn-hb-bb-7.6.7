/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.vo;

import java.util.List;
import java.util.Map;

public class FinancialCheckSchemeBaseDataVO {
    private String code;
    private String key;
    private String title;
    private Boolean isLeaf;
    private String parentid;
    private List<FinancialCheckSchemeBaseDataVO> children;
    private Map<String, Object> data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<FinancialCheckSchemeBaseDataVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<FinancialCheckSchemeBaseDataVO> children) {
        this.children = children;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

