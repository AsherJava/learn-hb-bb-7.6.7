/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.web.vo;

public class LinkOrderVO {
    private String groupKey;
    private String fieldKey;
    private String order;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String toString() {
        return "LinkOrderVO{groupKey='" + this.groupKey + '\'' + ", fieldKey='" + this.fieldKey + '\'' + ", order='" + this.order + '\'' + '}';
    }
}

