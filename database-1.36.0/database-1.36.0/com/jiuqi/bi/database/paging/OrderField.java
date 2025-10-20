/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.paging;

import com.jiuqi.bi.util.StringUtils;

public final class OrderField {
    private String fieldName;
    private String orderMode;

    public OrderField() {
    }

    public OrderField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getOrderMode() {
        return this.orderMode;
    }

    public void setOrderMode(String orderMode) {
        this.orderMode = orderMode;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(this.fieldName);
        if (!StringUtils.isEmpty((String)this.orderMode)) {
            buffer.append(' ').append(this.orderMode);
        }
        return buffer.toString();
    }
}

