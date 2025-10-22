/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tsd.dto;

import java.math.BigDecimal;

public class Unit {
    private String entityDataKey;
    private String parentKey;
    private String code;
    private String title;
    private String path;
    private BigDecimal order;

    public String getEntityDataKey() {
        return this.entityDataKey;
    }

    public void setEntityDataKey(String entityDataKey) {
        this.entityDataKey = entityDataKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
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

    public BigDecimal getOrder() {
        return this.order;
    }

    public void setOrder(BigDecimal order) {
        this.order = order;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

