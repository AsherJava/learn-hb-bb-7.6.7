/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

public class ObjectOrderField {
    private final String fieldName;
    private final String orderType;

    public ObjectOrderField(String fieldName) {
        this(fieldName, "ASC");
    }

    public ObjectOrderField(String fieldName, String orderType) {
        this.fieldName = fieldName;
        this.orderType = orderType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getOrderType() {
        return this.orderType;
    }
}

