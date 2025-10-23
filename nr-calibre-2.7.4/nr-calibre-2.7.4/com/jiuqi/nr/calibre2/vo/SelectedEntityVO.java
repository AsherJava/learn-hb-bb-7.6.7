/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.vo;

import java.math.BigDecimal;

public class SelectedEntityVO {
    private String key;
    private String code;
    private String title;
    private String parent;
    private String parentName;
    private BigDecimal order;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public BigDecimal getOrder() {
        return this.order;
    }

    public void setOrder(BigDecimal order) {
        this.order = order;
    }
}

